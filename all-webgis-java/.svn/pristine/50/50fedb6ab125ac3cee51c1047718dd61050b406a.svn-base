package com.trgis.trmap.personal.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.personal.dao.UserMessageDao;
import com.trgis.trmap.personal.exception.UserMessageException;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;

@Transactional
@Service
public class UserMessageServiceImp implements UserMessageService {

	@Autowired
	private UserMessageDao userMessageDao;
	private static final Log log = LogFactory.getLog(UserRecordServiceImpl.class);

	@Override
	public void addMessage(UserMessage message) throws UserMessageException {
		try {
			userMessageDao.save(message);
			log.debug("消息发送成功！");
		} catch (Exception e) {
			log.debug("消息发送失败！");
		}
	}

	@Override
	public void deleteMessage(Long id) throws UserMessageException {
		try {
			userMessageDao.deleteMessage(EnumUtil.DELFLAG.DEL.getValue(), id);
			log.debug("消息删除成功！");
		} catch (Exception e) {
			log.debug("消息删除失败！");
		}

	}

	@Override
	public void updateMessage(UserMessage message) throws UserMessageException {
		try {
			userMessageDao.changeReadType(EnumUtil.READ.READ.getValue(), message.getId());
			log.debug("消息已阅读！");
		} catch (Exception e) {
			log.debug("消息修改失败！");
		}
	}
    
	@Override
	public void findMessageByUser(User user, Integer readflag) throws UserMessageException {
		try {
			userMessageDao.findMessageByUser(user, readflag, EnumUtil.DELFLAG.NOMAL.getValue());
			log.debug("成功！");
		} catch (Exception e) {
			log.debug("失败！");
		}

	}

	@Override
	public Page<UserMessage> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) {
		Specification<UserMessage> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = userMessageDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userMessageDao.findAll(specifications, page);

	}

	@Override
	public Long getReadCount(User user,Integer readflag) throws UserMessageException {
		Long count = null;
		try {
			if (BeanUtil.isEmpty(user)) {
				throw new UserMessageException("用户为空");
			}
			if (!BeanUtil.isEmpty(readflag)) {
				count = userMessageDao.getReadCount(EnumUtil.DELFLAG.NOMAL.getValue(),readflag, user);
			}
			log.debug("查询成功！");
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserMessageException("查询失败!");
		}
		return count;
	}

	@Override
	public UserMessage findMessageById(Long id) throws UserMessageException {
		UserMessage userMessage = null;
		try {
			log.debug("findmessage成功！");
			userMessage =userMessageDao.findOne(id);
		} catch (Exception e) {
			log.debug("findmessage失败！");
		}
		return userMessage;
		
	}

	@Override
	public void updateMeg(Long cid) throws UserMessageException {
		try {
			Object[] cids = {cid};
			userMessageDao.updateMsg(cids);
			log.debug("修改信息成功！");
		} catch (Exception e) {
			log.debug("修改信息失败！");
		}
	}

}
