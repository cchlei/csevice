package com.trgis.trmap.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.userauth.model.User;

/**
 * 服务申请
 * @author Alice
 *
 * 2015年12月28日
 */
public interface EntApplicationService {
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public void delete(Long id);
	/**
	 * 按ID查找
	 * @param id
	 * @return
	 */
	public EntApplication findById(Long id);
	/**
	 * 按编号查找
	 * @param id
	 * @return
	 */
	public EntApplication findByNumber(String number);
	/**
	 * 服务申请
	 * @param application
	 */
	public void save(EntApplication application);
	
	/**
	 * 查询当日最大编号
	 * @return
	 */
	public String findMaxNumber();
	
	/**
	 * 查询是否此用户已经申请过改服务
	 * @param getter
	 * @param relUserMap
	 * @return
	 */
	public List<EntApplication> findByGetterAndRelUserMap(User getter, EntRelUserMap relUserMap);
	
	/**
	 * 按条件获取申请的地图服务
	 * @param getter
	 * @return
	 */
	public Page<EntApplication> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
	/**
	 * 修改申请这个服务的所有记录的状态为异常
	 * @param relUserMap
	 * @return
	 */
	public void updateExcepByRelUserMap(Integer isapproved, String reason, EntRelUserMap relUserMap, Integer nowapproved);
	
	/**
	 * 恢复这个服务的状态为正常
	 * @param relUserMap
	 * @return
	 */
	public void updatePassById(Integer isapproved, String reason, Long id);
	
	/**
	 * 所有正常使用服务的申请id
	 * @param relUserMap
	 * @param isapproved
	 * @return
	 */
	public List<Long> getIdsByPassmap(EntRelUserMap relUserMap, Integer isapproved);
	public List<EntApplication> getByPassmap(EntRelUserMap relUserMap, Integer isapproved);
	/**
	 * 查找某个服务的续约订单
	 * @param getter
	 * @param relUserMap
	 * @param isapproved
	 * @return
	 */
	public List<EntApplication> findRepaly(User getter, EntRelUserMap relUserMap);
	
	/**
	 * 查找所有正常状态的订单，定时器接口查赋过期时使用
	 * @return
	 */
	public List<EntApplication> findByPass(Integer isapproved);
	
	/**
	 * 统计待审批的订单数量
	 * @return
	 */
	public Long finduntreated(Long userid, Integer isapproved);
	
	/**
	 * 统计服务获取的订单数量
	 * @return
	 */
	public Long findpass(Long userid, Integer isapproved);
	
	
}
