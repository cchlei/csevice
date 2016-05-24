package com.trgis.trmap.enterprise.service;

import com.trgis.trmap.userauth.util.Token;

/**
 * 企业账号服务
 * 
 * @author zhangqian
 *
 */
public interface TrmapEntTokenService {

	/**
	 * 获取
	 * 
	 * @param token
	 * @return
	 */
	public Token getDecryptToken(String token);

	/**
	 * 获取企业用户的访问token
	 * 
	 * @param username
	 * @param password
	 * @param appKey
	 * @return
	 */
	public String getEncryptToken(String username, String password, String appKey);

}
