package com.trgis.trmap.personal.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.personal.dao.UserCommentDao;
import com.trgis.trmap.personal.dao.UserMessageDao;
import com.trgis.trmap.personal.exception.UserCommentException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserCommentService;
import com.trgis.trmap.personal.util.BeanUtil;

@Service
@Transactional
public class UserCommentServiceImpl implements UserCommentService{

	private static final Log log = LogFactory.getLog(UserCommentServiceImpl.class);
	
	@Autowired
	private UserCommentDao userCommentDao;
	@Autowired
	private UserMessageDao userMessageDao;
	
	@Override
	public List<UserComment> getUserCommentsByRecord(Long rid ,Long pid ) throws UserCommentException {
		try {
			if (BeanUtil.isEmpty(rid)) {
				throw new UserCommentException("事件对象id不能为空!");
			}
			log.debug("事件相关评论查询成功！");
			if(BeanUtil.isNull(pid)){//pid为空
				return userCommentDao.getCommentsByRecord(rid);
			}else{//pid不为空
				return userCommentDao.getCommentsByRecord(rid, pid);
			}
		} catch (Exception e) {
			log.debug("事件相关评论查寻失败！");
			throw new UserCommentException("事件相关评论查寻失败!");
		}
	}

	@Override
	public void deleteUserCommentsById(Long id) throws UserCommentException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserCommentException("评论id不能为空!");
			}
			userCommentDao.delete(id);
			log.debug("评论删除成功！");
		} catch (Exception e) {
			log.debug("评论删除失败!");
			throw new UserCommentException("评论删除失败!");
		}
	}

	@Override
	public UserComment saveUserComment(UserComment userComment) throws UserCommentException {
		UserComment comment = null;
		try {
			if (BeanUtil.isEmpty(userComment)) {
				throw new UserTopicException("评论對象不能为空!");
			}
			log.debug("评论添加成功！");
			comment = userCommentDao.save(userComment);
		} catch (Exception e) {
			log.debug("评论添加失败!");
			throw new UserCommentException("评论添加失败!");
		}
		return comment;
	}

	@Override
	public Long countByRecord(UserRecord userRecord) throws UserCommentException {
		try {
			if (BeanUtil.isEmpty(userRecord)) {
				throw new UserTopicException("事件对象不能为空!");
			}
			if (BeanUtil.isEmpty(userRecord.getId())) {
				throw new UserTopicException("事件对象id不能为空!");
			}
			return userCommentDao.countByRecord(userRecord.getId());
		} catch (Exception e) {
			log.debug("事件相关评论查询失败！");
			throw new UserCommentException("事件相关评论查询失败!");
		}
	}

	@Override
	public UserComment findComment(Long id) {
		UserComment userComment = new UserComment();
		try {
			userComment = userCommentDao.findOne(id);
			log.debug("根据id查询评论成功！");
		} catch (Exception e) {
			log.debug("根据id查询评论失败！");
		}
		return userComment;
		
	}

	@Override
	public void deleteUserCommentsBypid(Long id) throws UserCommentException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserTopicException("父级评论id不能为空!");
			}
			Object[] comIds = userCommentDao.getIdsBy(id);//根据父级的id获得对应的id数组
			if (comIds!=null&&comIds.length>0) {
				//通过评论的id集合取消消息的级联关系
				userMessageDao.updateMsg(comIds);
			}
			userCommentDao.deleteComentBypid(id);
			log.debug("子集评论删除成功！");
		} catch (Exception e) {
			log.debug("子集评论删除失败!");
			throw new UserCommentException("子集评论删除失败!");
		}
	}

	@Override
	public Boolean findchild(Long id) {
		Boolean boo = false;
		try {
		  Long num = userCommentDao.findchild(id);
		  if(num>0){
			  boo = true;
		  }
			log.debug("查询子节点成功！");
		} catch (Exception e) {
			log.debug("查询子节点失败！");
		}
		
		return boo;
	}
	
}
