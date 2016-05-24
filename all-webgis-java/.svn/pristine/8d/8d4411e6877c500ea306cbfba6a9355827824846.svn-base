package com.trgis.trmap.userauth.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 首页单点登录验证
 * 
 * @author zhangqian
 *
 */
public class WebCASRealm extends CasRealm{
	
	/**
	 * 这里只需要进行用户权限设置即可，登录验证部分由cas实现
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
	}

}
