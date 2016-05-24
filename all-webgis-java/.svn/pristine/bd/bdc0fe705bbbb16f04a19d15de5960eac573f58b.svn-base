/**
 * 
 */
package com.trgis.trmap.userauth.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trgis.trmap.userauth.model.SystemUser;
import com.trgis.trmap.userauth.service.SystemUserService;
import com.trgis.trmap.userauth.util.QTManager;

/**
 * 实现自定义验证用户登陆认证的realm
 * 
 * @author zhangqian
 *
 */
@Component
public class SystemUserRealm extends AuthorizingRealm {

	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * 为当前登陆用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		QTManager systemUser = (QTManager) super.getAvailablePrincipal(principals);// 获取用户
		systemUserService.findSystemUserByUsername(systemUser.getUsername());
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRole("SYSTEM_MANAGER"); //系统管理员
		return simpleAuthorInfo;
	}

	/**
	 * 验证当前登陆的subject
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SystemUser systemUser = systemUserService.findSystemUserByUsername(token.getUsername());
		if (systemUser != null) {
			QTManager shiroUser = new QTManager(systemUser.getId(),systemUser.getUsername(),systemUser.getSalt());
			return new SimpleAuthenticationInfo(shiroUser,systemUser.getPassword(),systemUser.getUsername());
		} else {
			return null;
		}
	}

}
