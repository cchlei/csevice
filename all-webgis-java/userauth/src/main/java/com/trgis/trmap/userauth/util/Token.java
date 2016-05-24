package com.trgis.trmap.userauth.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.trgis.common.util.DateUtil;

/**
 * 系统token
 * 
 * 用于企业
 * 
 * @author zhangqian
 *
 */
public class Token {

	/**
	 * 系统默认token有效期
	 */
	private static final int EXPIRSETIME = 7200;

	/**
	 * token携带加密用户名信息，企业密码信息，有效期信息
	 */
	public static final String TOKEN_TEMP = "{\"user\":\"%s\",\"secret\":\"%s\",\"expirse\":\"%d\"}";

	/**
	 * 有效期 时间毫秒
	 */
	private long expirse;

	private String secret;

	private String user;

	public Token() {
	}

	public Token(String username, String appKey) {
		this.user = username;
		this.secret = appKey;
	}

	public Token(String username, String appKey, long expirse) {
		this.user = username;
		this.secret = appKey;
		this.expirse = expirse;
	}

	public String getSecret() {
		return secret;
	}

	/**
	 * 获取Token
	 * 
	 * @return
	 */
	public String getToken() {
		long validatTime = DateUtils.addSeconds(new Date(), EXPIRSETIME).getTime(); // 有效期2小时
		String token = String.format(Token.TOKEN_TEMP, this.user, this.secret, validatTime);
		try {
			return UserEncrypt.QTAES_Encrypt(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public String getUser() {
		return user;
	}

	/**
	 * 判断当前Token是否过期
	 * 
	 * @return
	 */
	public boolean isOverdue() {
		Date expirse = new Date(this.getExpirse());
		return !DateUtil.getNow().before(expirse);
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getExpirse() {
		return expirse;
	}

	public void setExpirse(long expirse) {
		this.expirse = expirse;
	}

	
}
