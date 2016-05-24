package com.trgis.trmap.userauth.service.impl;

import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.userauth.dao.AccountConfineDao;
import com.trgis.trmap.userauth.model.AccountConfine;
import com.trgis.trmap.userauth.service.AccountConfineService;

/**
 * @author zhangqian
 *
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountConfineServiceImpl implements AccountConfineService{
	
	@Autowired
	private AccountConfineDao accountConfineDao;
	// 运行日志记录
	private static final Logger logger = LoggerFactory.getLogger(AccountConfineServiceImpl.class);
	
	@Override
	public List<AccountConfine> findAccountConfineByIp(String recordip,Integer rtype,String strdate) {
		logger.debug("===find user by recordip and rtype===");
		return accountConfineDao.findByIp(recordip,rtype,strdate);
	}

	@Override
	public void addAccountConfine(AccountConfine accountConfine) {
		logger.debug("===add user by accountConfine.===");
		
		accountConfineDao.save(accountConfine);
	}

	@Override
	public List<AccountConfine> findByEmail(String recordip, String email,String strdates) {
		// TODO Auto-generated method stub
		return accountConfineDao.findByEmail(recordip,email , strdates);
	}

}
