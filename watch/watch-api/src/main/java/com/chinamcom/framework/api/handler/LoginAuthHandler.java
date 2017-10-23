package com.chinamcom.framework.api.handler;

import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.handler.AuthHandler;

import com.chinamcom.framework.api.handler.impl.LoginAuthHandlerImpl;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/19
 */
public interface LoginAuthHandler extends AuthHandler,BaseHandler {

	/**
	 * Create a handler
	 * @param authProvider  the auth service to use
	 * @return the handler
	 */
	static AuthHandler create(AuthProvider authProvider) {
	    return new LoginAuthHandlerImpl(authProvider);
	}
}
