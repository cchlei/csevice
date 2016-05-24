package com.trgis.trmap.userauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
/**
 * 企业用户DAO
 * @author doris
 * @date 2015-11-26
 */
@Repository
public interface EnterpriseUserDao  extends JpaRepository<User, Long> ,JpaSpecificationExecutor<User>{

	/**
	 * 根据用户邮箱查找账号
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	User findByMobile(String mobile);
	/**
	 * @author Alice
	 * 根据企业名称查找企业
	 * @param enterprisename
	 * @param entity
	 * @return
	 */
	@Query("from User where name =?1 and entity =?2")
	List<User> findByEnterprisename(String enterprisename, UserEntity entity);
}
