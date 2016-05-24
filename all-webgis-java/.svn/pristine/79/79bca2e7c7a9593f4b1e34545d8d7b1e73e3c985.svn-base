package com.trgis.trmap.qtuser.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.userauth.model.User;

/**
 * 用户地图服务类
 * @author doris
 * @date 2015-09-11
 */
public interface UserMapService {
	
	/**
	 * 创建地图
	 * @code MapManage:CREATE
	 * @param userMap
	 */
	public void createUserMap(UserMap userMap);
	/**
	 * 完善分享地址链接
	 */
	public void setShareUrl(Long id);
	/**
	 * 分享、审核地图
	 * @code MapManage:SHARE
	 * @param id 个人地图id
	 * @param isshare 分享状态
	 */
	public void shareUserMap(Long id,Integer isshare);
	/**
	 * 删除地图
	 * @code MapManage:DELETE
	 * @param id
	 */
	public void deleteUserMap(Long id);
	/**
	 * 修改地图
	 * @code MapManage:EDIT
	 * @param id
	 */
	public void editUserMap(UserMap userMap);
	/**
	 * 获取所有个人地图
	 * @code MapManage:EDIT
	 * @param id
	 */
	public List<UserMap> findAllUserMap(); 
	/**
	 * 获取所有未删除个人地图
	 * @code MapManage:EDIT
	 * @param List<UserMap>
	 */
	public List<UserMap> findUserMap(); 
	/**
	 * 根据获取个人地图
	 * @code MapManage:EDIT
	 * @param id
	 */
	public UserMap findUserMapById(Long id); 
	/**
	 * 根据用户获取移动专属地图
	 * @param id
	 */
	public UserMap findUserAppMap(User user, Boolean app); 
	/**
	 * 分页查询 .
	 * @param page .
	 * @param hql .
	 * @return Page .
	 */
	public Page<UserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
}
