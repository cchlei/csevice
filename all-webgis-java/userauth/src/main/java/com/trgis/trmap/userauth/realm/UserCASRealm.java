package com.trgis.trmap.userauth.realm;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 用户登录交给cas单点登录系统授权
 * 
 * @author zhangqian
 *
 */
public class UserCASRealm extends CasRealm{
	
	private static final Logger logger = LoggerFactory.getLogger(UserCASRealm.class);

	@Autowired
	private UserService userService;
	
	/**
	 * 这里只需要进行用户权限设置即可，登录验证部分由cas实现
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.debug("已登录，开始授权");
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);
        logger.debug("查询用户在系统内的信息");
        if(user == null) {
        	throw new AuthorizationException("该用户不存在本系统");
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        logger.debug("查询用户状态...");
    	String accountRole = user.getEntity().getName();
    	logger.debug("用户账号角色:"+accountRole);
    	simpleAuthorizationInfo.addRole(accountRole);
        return simpleAuthorizationInfo;
	}

}
