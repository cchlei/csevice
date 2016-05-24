package com.trgis.trmap.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.userauth.model.User;

/**
 * 企业认证信息服务
 * @author doris
 * @date 2015-11-27
 */
public interface EntCainfoService {
	/**
	 * 绘制企业认证信息
	 * @param cainfo
	 */
	public void save(EntCainfo cainfo);
	/**
	 * 修改企业认证信息
	 * @param cainfo
	 */
	public void edit(EntCainfo cainfo);
	/**
	 * 删除企业认证信息
	 * @param id
	 */
	public void delete(Long id);
	/**
	 * 获取所有企业认证信息
	 * @return
	 */	
	public List<EntCainfo> findAll(); 
	
	/**
	 * 根据id获取企业认证信息
	 * @param id
	 */
	public EntCainfo findById(Long id); 
	/**
	 * 根据用户信息获取企业认证信息
	 * @param id
	 */
	public  EntCainfo findByUser(User user);
	/**
	 * @param group
	 * @param pageNo
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public Page<EntCainfo> findByConditions(ConditionGroup group, int pageNo, int pageSize, OrderBy... order);
	/**
	 * 撤销企业认证信息
	 * @param castatus
	 * @param id
	 */
	public void revocationApply(Long id);
	
	/**
	 * 根据用户名查询企业信息
	 * @param username
	 * @return
	 */
	public EntCainfo findByUsername(String username);
	
	/**
	 * 根据企业名查找企业信息
	 * @param enterpriseName
	 * @return
	 */
	public EntCainfo findByEnterpriseName(String enterpriseName);
	
	/**
	 * 根据用户和状态查找认证信息
	 * @param id
	 * @param castatus
	 * @return
	 */
	public EntCainfo findByUserAndCastatus(User user, String castatus);
	
	/**
	 * 根据用户状态查找认证信息
	 * @author mm
	 * @param castatus
	 * @return
	 */
	public List<EntCainfo> findByCastatus(String castatus);
}
