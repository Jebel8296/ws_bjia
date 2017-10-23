package com.chinamcom.framework.api.handler.impl;

import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;

import com.chinamcom.framework.api.handler.LoginAuthHandler;
import com.chinamcom.framework.common.response.RespCode;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/19
 */
public class LoginAuthHandlerImpl extends AuthHandlerImpl implements
		LoginAuthHandler {

	public LoginAuthHandlerImpl(AuthProvider authProvider) {
		super(authProvider);
	}

	@Override
	public void handle(RoutingContext context) {
		Session session = context.session();
		String serial_number = context.getBodyAsJson().getString("sn");
	    if (session != null) {
	      User user = context.user();
	      if (user != null) {
	        // Already logged in, just authorise
	        authorise(user, context);
	      } else {
	        // Now redirect to the login url - we'll get redirected back here after successful login
	        context.response().putHeader("content-type", "application/json");
	        context.response().end(respWriter.toError(serial_number, RespCode.CODE_502).toString());
	      }
	    } else {
	      context.fail(new NullPointerException("No session - did you forget to include a SessionHandler?"));
	      context.response().end(respWriter.toError(serial_number, RespCode.CODE_502).toString());
	    }
	}

}
