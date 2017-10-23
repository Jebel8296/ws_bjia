package com.chinamcom.framework.api.handler.impl;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

import com.chinamcom.framework.api.handler.LoginHandler;
import com.chinamcom.framework.common.response.RespCode;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/19
 */
public class LoginHandlerImpl implements LoginHandler {

	private static final Logger log = LoggerFactory
			.getLogger(LoginHandlerImpl.class);

	private final AuthProvider authProvider;
	
	private String usernameParam;
	private String passwordParam;
	private String returnURLParam;
	private String directLoggedInOKURL;

	@Override
	public LoginHandler setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
		return this;
	}

	@Override
	public LoginHandler setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
		return this;
	}

	@Override
	public LoginHandler setReturnURLParam(String returnURLParam) {
		this.returnURLParam = returnURLParam;
		return this;
	}

	@Override
	public LoginHandler setDirectLoggedInOKURL(String directLoggedInOKURL) {
		this.directLoggedInOKURL = directLoggedInOKURL;
		return this;
	}

	public LoginHandlerImpl(AuthProvider authProvider, String usernameParam,
			String passwordParam, String returnURLParam,
			String directLoggedInOKURL) {
		this.authProvider = authProvider;
		this.usernameParam = usernameParam;
		this.passwordParam = passwordParam;
		this.returnURLParam = returnURLParam;
		this.directLoggedInOKURL = directLoggedInOKURL;
	}

	@Override
	public void handle(RoutingContext context) {
		HttpServerRequest req = context.request();
		if (req.method() != HttpMethod.POST) {
			context.fail(405); // Must be a POST
		} else {
			JsonObject reqData = context.getBodyAsJson().getJsonObject("request_data");
			String username = reqData.getString(usernameParam);
			String password = reqData.getString(passwordParam);
			if (username == null || password == null) {
				log.warn("No username or password provided in form - did you forget to include a BodyHandler?");
				context.fail(400);
			} else {
				Session session = context.session();
				req.response().putHeader("content-type", "application/json");
				if(session.data().get("userinfo") != null){
					req.response().end(respWriter.toSuccess("userinfo", session.get("userinfo")).toString());
					return;
				}
				if(!"123".equals(password)){
					String serial_number = reqData.getString("serial_number"); 
					req.response().end(respWriter.toError(serial_number, RespCode.CODE_1000).toString());
					return;
				}
				JsonObject authInfo = new JsonObject().put("username", username).put("password", password);
				authProvider.authenticate(authInfo, res -> {
					if (res.succeeded()) {
						User user = res.result();
						context.setUser(user);
						if (session != null) {
							String returnURL = session.remove(returnURLParam);
							if (returnURL != null) {
								doRedirect(req.response(), returnURL);
								return;
							}
							JsonObject userJson = new JsonObject();
							userJson.put("userName", "fattiger");
							userJson.put("userCode", "fattiger");
							userJson.put("phoneNo", "17098888888");
							session.put("userinfo", userJson.toString());
							req.response().end(respWriter.toSuccess("userinfo", userJson).toString());
						}
						// Either no session or no return url
						if (directLoggedInOKURL != null) {
							// Redirect to the default logged in OK page - this
							// would occur
							// if the user logged in directly at this URL
							// without being redirected here first from another
							// url
							doRedirect(req.response(), directLoggedInOKURL);
						} else {
							// Just show a basic page
//							req.response().end(DEFAULT_DIRECT_LOGGED_IN_OK_PAGE);
						}
					} else {
						context.fail(403); // Failed login
					}
				});
			}
		}
	}

	private void doRedirect(HttpServerResponse response, String url) {
		response.putHeader("location", url).setStatusCode(302).end();
	}

}
