package com.trgis.trmap.userauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.userauth.dao.SystemUserDAO;
import com.trgis.trmap.userauth.model.SystemUser;
import com.trgis.trmap.userauth.service.SystemUserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 系统管理员服务接口
 * 
 * @author zhangqian
 *
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	private SystemUserDAO systemUserDao;

	@Override
	public SystemUser find(Long id) {
		return systemUserDao.findOne(id);
	}

	@Override
	public SystemUser findSystemUserByUsername(String username) {
		return systemUserDao.findByUsername(username);
	}

	@Override
	public void createSystemUser(SystemUser systemUser) {
		systemUser.setSalt(UserEncrypt.generateSalt());
		systemUser.setPassword(UserEncrypt.md5hash(systemUser.getPassword(), systemUser.getSalt()));
		systemUserDao.save(systemUser);
	}

}
