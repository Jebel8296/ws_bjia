package com.chinamcom.framework.api.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/19
 */
public class LocalUserRealm extends AuthorizingRealm{
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		log.info("doGetAuthorizationInfo principals");
		return super.getAuthorizationInfo(principals);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		log.info("doGetAuthenticationInfo token");
		UsernamePasswordToken tk = (UsernamePasswordToken) token;
		return new SimpleAuthenticationInfo(tk.getUsername(), tk.getPassword(), getName());
	}
}
