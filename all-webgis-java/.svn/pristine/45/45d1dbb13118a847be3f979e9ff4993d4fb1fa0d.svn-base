package com.trgis.trmap.personal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.personal.exception.UserAttentionException;
import com.trgis.trmap.personal.model.UserAttention;
import com.trgis.trmap.personal.util.GroupAttentionVo;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserAttentionService
 * @Description: 关注好友接口
 * @author yanpeng
 * @date 2016年3月24日 下午3:38:26
 */
public interface UserAttentionService {
	/**
	 * @Description: 取消关注好友
	 * @author yanpeng
	 * @date 2016年3月24日 下午3:42:53
	 * @param buid 取消关注好友的id
	 * @return
	 * @throws UserAttentionException
	 */
	public void cancleAttention(Long cuid,Long buid) throws UserAttentionException;
	
	/**
	 * @Description: 关注新的好友	
	 * @author yanpeng
	 * @date 2016年3月24日 下午3:43:49
	 * @param cuser 当前用户
	 * @param buser 被关注用户
	 * @return
	 * @throws UserAttentionException
	 */
	public void saveAttention(User cuser,User buser,Long gruopId) throws UserAttentionException;

	/**
	 * @Description: 修改好友所属分组
	 * @author yanpeng
	 * @date 2016年3月25日 下午3:40:22
	 * @param groupId
	 * @param cuid
	 * @param buid
	 * @throws UserAttentionException
	 */
	public void updateGroup(Long groupId,Long cuid,Long buid) throws UserAttentionException;
	
	
	/**
	 * @Description: 分页查询我的好友列表
	 * @author yanpeng
	 * @date 2016年3月28日 上午9:19:31
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public Page<UserAttention> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws  UserAttentionException;
	
	/**
	 * @Description: 获得当前用户的好友数量 
	 * @author yanpeng
	 * @date 2016年3月28日 上午10:08:59
	 * @param group
	 * @return
	 * @throws UserAttentionException
	 */
	public Long getAttentionNumber(ConditionGroup group) throws UserAttentionException;
	
	/**
	 * @Description: 
	 * @author yanpeng
	 * @date 2016年3月30日 下午3:04:28
	 * @param userid 当前用户id
	 * @param size 查询数量
	 * @param name 查询条件
	 * @return
	 * @throws UserAttentionException
	 */
	public List<Object[]> findUsers(Long userid,String name,Integer size) throws UserAttentionException;

	/**
	 * @Description: 获取我关注好友的id
	 * @author yanpeng
	 * @date 2016年4月5日 下午1:36:53
	 * @param user
	 * @return
	 * @throws UserAttentionException
	 */
	public Long[] getAttens(Long userid) throws UserAttentionException;

	public Page<User> findUserByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy order) throws UserAttentionException ;

	public Integer isAttention(User userBean, User user) throws UserAttentionException ;

	/**
	 * @Description: 获取当前用户的分组和好友，并分装
	 * @author yanpeng
	 * @date 2016年5月19日 上午10:00:08
	 * @param userBean
	 * @param topicid
	 * @return
	 * @throws UserAttentionException
	 */
	public List<GroupAttentionVo> getAttensAndGroup(User userBean,Long topicid) throws UserAttentionException;
}
