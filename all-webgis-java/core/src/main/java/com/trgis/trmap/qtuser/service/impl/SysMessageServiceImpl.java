package com.trgis.trmap.qtuser.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.qtuser.dao.SysMessageDao;
import com.trgis.trmap.qtuser.exception.SysMessageException;
import com.trgis.trmap.qtuser.model.SysMessage;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.ISysMessageService;
import com.trgis.trmap.qtuser.utils.BeanUtil;

/**
 * @author doris
 * @date 2015-09-11
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysMessageServiceImpl implements ISysMessageService {
	private static final Log log = LogFactory.getLog(SysMessageServiceImpl.class);
	@Autowired
	private SysMessageDao sysMessageDao;
	@Override
	public void save(SysMessage sysMessage) {
		try {
			if (BeanUtil.isEmpty(sysMessage)) {
				throw new SysMessageException("用户消息对象不能为空!");
			}	
			sysMessageDao.save(sysMessage);
			log.debug("用户消息保存成功！");
		} catch (Exception e) {
			log.debug("用户消息保存失败！");
			throw new SysMessageException("用户消息保存失败！");
		}
	}

	@Override
	public void edit(SysMessage sysMessage) {
		try {
			if (BeanUtil.isEmpty(sysMessage)) {
				throw new SysMessageException("用户消息对象不能为空!");
			}	
			sysMessageDao.saveAndFlush(sysMessage);
			log.debug("用户消息修改成功！");
		} catch (Exception e) {
			log.debug("用户消息修改失败！");
			throw new SysMessageException("用户消息修改失败！");
		}
		
	}

	@Override
	public void delete(Long id) {
		
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new SysMessageException("用户消息id不能为空!");
			}
			sysMessageDao.delete(id);
			log.debug("用户消息删除成功！");
		} catch (Exception e) {
			log.debug("用户消息删除失败！");
			throw new SysMessageException("用户消息删除失败！");
		}
	}

	@Override
	public List<SysMessage> findAll() {
		
		try {
			return sysMessageDao.findAll();
		} catch (Exception e) {
			log.debug("用户消息列表查询失败！");
			throw new SysMessageException("用户消息列表查询失败！");
		}
	}

	@Override
	public SysMessage findById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new SysMessageException("用户消息id不能为空!");
			}
			return sysMessageDao.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询用户消息失败！");
			throw new SysMessageException("按id查询用户消息失败！");
		}
	}

	@Override
	public List<SysMessage> findByUserMap(UserMap userMap) {
		try {
			if (BeanUtil.isEmpty(userMap)) {
				throw new SysMessageException("个人地图对象不能为空!");
			}	
			return sysMessageDao.findSysMessageByUserMap(userMap);
		} catch (Exception e) {
			log.debug("按个人地图查询用户消息失败！");
			throw new SysMessageException("按个人地图查询用户消息失败！");
		}
	}

	@Override
	public List<SysMessage> findSysMessages(Integer mdelflag, UserMap userMap) {
		try {
			if (BeanUtil.isEmpty(userMap)) {
				throw new SysMessageException("个人地图对象不能为空!");
			}
			if (BeanUtil.isEmpty(mdelflag)) {
				throw new SysMessageException("用户消息删除标志不能为空!");
			}	
			return sysMessageDao.findSysMessageByMdelflagAndUserMap(mdelflag, userMap);
		} catch (Exception e) {
			log.debug("按条件查询用户消息失败！");
			throw new SysMessageException("按条件查询用户消息失败！");
		}
	}


}
