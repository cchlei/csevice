package com.trgis.trmap.personal.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.personal.dao.UserIntegralDao;
import com.trgis.trmap.personal.exception.UserIntegralException;
import com.trgis.trmap.personal.model.UserIntegral;
import com.trgis.trmap.personal.service.UserIntegralService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.userauth.model.User;


@Service
@Transactional
public class UserIntegralImpl implements UserIntegralService{

	
	@Autowired
	private UserIntegralDao userIntegralDao;
	private static final Log log = LogFactory.getLog(UserIntegralImpl.class);
	@Override
	public UserIntegral getUserIntegral(User user) throws UserIntegralException {
		try {
			if (BeanUtil.isEmpty(user)) {
				throw new UserIntegralException("用户信息为空!");
			}
			log.debug("获取用户积分信息成功！");
			UserIntegral account = userIntegralDao.query(user);
			if (account == null) {
				account = new UserIntegral();
				account.setUser(user);
				UserIntegral userAccount = userIntegralDao.save(account);
				return userAccount;
			}
			return account;
		} catch (Exception e) {
			log.debug("获取用户积分信息失败!");
			throw new UserIntegralException("获取用户积分信息失败!");
		}
	}

}
