package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.userauth.model.User;
/**
 * 服务申请Dao
 * @author Alice
 *
 * 2015年12月28日
 */
@Repository
public interface EntApplicationDao extends JpaRepository<EntApplication, Long>,JpaSpecificationExecutor<EntApplication>{
	/**
	 * 当天最大单号
	 * @param date
	 * @return
	 */
	@Query("select number from EntApplication where number like ?1% order by number desc")
	public List<String> findMaxNumber(String date); 
	/**
	 * 查找所有正常状态的订单，定时器接口查赋过期时使用
	 * @param isapproved
	 * @return
	 */
	@Query("from EntApplication where isapproved = ?1")
	public List<EntApplication> findByPass(Integer isapproved);
	/**
	 * 根据当前用户以及被申请企业获取地图服务列表
	 * @param getter
	 * @param relUserMap
	 * @return
	 */
	public List<EntApplication> findByGetterAndRelUserMap(User getter, EntRelUserMap relUserMap);
	
	/**
	 * 根据订单编号查找申请记录
	 * @param number
	 * @return
	 */
	public EntApplication findByNumber(String number);
	
	/**
	 * 查找续约订单
	 * @param getter
	 * @param relUserMap
	 * @param isapproved
	 * @return
	 */
	@Query("from EntApplication where getter = ?1 and relUserMap = ?2 and renumber is not null")
	public List<EntApplication> findRepaly(User getter, EntRelUserMap relUserMap);
	/**
	 * 修改申请这个服务的申请记录为异常
	 * @param relUserMap
	 * @return
	 */
	@Modifying 
	@Query("update EntApplication set isapproved = ?1, suggestion = ?2 where relUserMap = ?3 and isapproved = ?4") 
	public void updateExcepByRelUserMap(Integer isapproved, String reason, EntRelUserMap relUserMap, Integer nowapproved);
	/**
	 * 恢复申请这个服务的的状态为正常
	 * @param relUserMap
	 * @return
	 */
	@Modifying 
	@Query("update EntApplication set isapproved = ?1, suggestion = ?2 where id = ?3") 
	public void updatePassById(Integer isapproved, String reason, Long id);
	/**
	 * 先记录所有正常使用服务的申请id
	 * 之所以写两个方法，是为了计算快速，减少服务器交互
	 * @param date
	 * @return
	 */
	@Query("select id from EntApplication where relUserMap = ?1 and isapproved = ?2")
	public List<Long> getIdsByPassmap(EntRelUserMap relUserMap, Integer isapproved); 
	@Query("from EntApplication where relUserMap = ?1 and isapproved = ?2")
	public List<EntApplication> getByPassmap(EntRelUserMap relUserMap, Integer isapproved); 
	
	/**
	 * 统计待审批的订单数量
	 * @param userid
	 * @param isapproved
	 * @return
	 */
	@Query("select count(*) from EntApplication where setter_id = ?1 and isapproved = ?2")
	public Long getuntreated(Long userid, Integer isapproved);
	
	/**
	 * 统计服务获取的订单数量
	 * @param userid
	 * @param isapproved
	 * @return
	 */
	@Query("select count(*) from EntApplication where getter_id = ?1 and isapproved = ?2")
	public Long getpass(Long userid, Integer isapproved);
}
