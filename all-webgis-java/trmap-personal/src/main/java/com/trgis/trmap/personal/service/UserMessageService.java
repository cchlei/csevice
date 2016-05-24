package com.trgis.trmap.personal.service;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.personal.exception.UserMessageException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.userauth.model.User;

/**
 * 
 * @Title: UserMessageService
 * @Description:消息接口
 * @author liuyan
 * @date 2016年3月25日 下午1:53:10
 *
 */
public interface UserMessageService {
	/**
	 * @Description:添加消息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午4:28:28
	 * @param message
	 * @throws UserMessageException
	 */
	public void addMessage(UserMessage message) throws UserMessageException;

	/**
	 * @Description:根据id删除消息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午4:28:40
	 * @param id
	 * @throws UserMessageException
	 */
	public void deleteMessage(Long id) throws UserMessageException;

	/**
	 * @Description:修改消息阅读状态
	 * @Author liuyan
	 * @Date 2016年3月26日 下午4:29:01
	 * @param message
	 * @throws UserMessageException
	 */
	public void updateMessage(UserMessage message) throws UserMessageException;
	/**
	 * @Description:修改消息的评论信息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午4:29:01
	 * @param message
	 * @throws UserMessageException
	 */
	public void updateMeg(Long cid) throws UserMessageException;

	/**
	 * @Description:根据接收者id阅读状态，消息类型查找消息     目前不用
	 * @Author liuyan
	 * @Date 2016年3月26日 下午4:29:17
	 * @param id
	 * @param readflag
	 * @param msgType
	 * @throws UserMessageException
	 */
	public void findMessageByUser(User user, Integer readflag) throws UserMessageException;
	/**
	 * @Description:根据id查message
	 * @Author liuyan 
	 * @Date 2016年3月28日 上午10:13:46
	 * @param id
	 * @return
	 * @throws UserMessageException
	 */
	public UserMessage findMessageById (Long id) throws UserMessageException;
	/**
	 * 分页查询
	 * 
	 * @Description:
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:53:00
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public Page<UserMessage> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order);

	/**
	 * @Author liuyan 根据用户id 阅读状态统计
	 * @Date 2016年3月26日 下午6:07:14
	 * @param id
	 * @return
	 * @throws UserTopicException
	 */
	public Long getReadCount(User user, Integer readflag) throws UserMessageException;

}
