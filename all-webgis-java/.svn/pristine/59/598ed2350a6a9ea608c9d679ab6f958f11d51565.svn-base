package com.trgis.trmap.enterprise.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.service.TrmapEntTokenService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.Token;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 天润云企业账号服务
 * 
 * @author zhangqian
 *
 */
//@Service
//@Transactional
public class TrmapEntTokenServiceImpl implements TrmapEntTokenService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EntCainfoService entCaInfoService;

	/*
	 * 获取解密用户信息
	 */
	private Token decryptToken(String tokenStr) {
		String decryptToken = null;
		try {
			decryptToken = UserEncrypt.QTAES_Decrypt(tokenStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Token token = JSONObject.parseObject(decryptToken, Token.class);
		return token;
	}

	/*
	 * 加密用户Token
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @param appKey
	 * 
	 * @return
	 */
	private String encryptToken(String username, String password, String appKey) {
		// 检查参数
		if (StringUtils.isAnyBlank(username, password, appKey)) {
			return StringUtils.EMPTY;
		}
		// 查询用户
		final User user = userService.findUserByUsername(username);
		if (user == null) {
			return StringUtils.EMPTY;
		}
		// 验证用户密码
		String dbPass = user.getPassword();
		String salt = user.getSalt();
		String encryptPass = UserEncrypt.md5hash(password, salt);
		if (StringUtils.equals(dbPass, encryptPass)) {
			// 验证用户类型和状态
			if (!UserStatus.ACTIVE.equals(user.getStatus())) {
				return StringUtils.EMPTY;
			}
			if (user.getEntity() != null && UserEntity.ENTERPRISE.equals(user.getEntity())) {
				EntCainfo epInfo = entCaInfoService.findByUser(user);
				// 验证企业信息是否存在，并且验证企业密码是否正确
				if (epInfo != null && StringUtils.isNotBlank(epInfo.getSecretKey())
						&& StringUtils.equals(epInfo.getSecretKey(), appKey)) {
					// 验证成功
					Token token = new Token(username, appKey);
					return token.getToken();
				}
			} else {
				return StringUtils.EMPTY;
			}
		}
		return StringUtils.EMPTY;
	}

	@Override
	public Token getDecryptToken(String token) {
		return decryptToken(token);
	}

	@Override
	public String getEncryptToken(String username, String password, String appKey) {
		return encryptToken(username, password, appKey);
	}

}
