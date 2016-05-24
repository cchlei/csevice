package com.trgis.trmap.userauth.util;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class UserEncryptMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		String textPassword = new String(authcToken.getPassword());
		String salt = ((QTManager)info.getPrincipals().getPrimaryPrincipal()).getSalt();
		String entryptPasswd = UserEncrypt.md5hash(textPassword, salt);
		return equals(entryptPasswd, info.getCredentials());
	}
	
}
