/**
 * 
 */
package com.trgis.trmap.userauth.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;

/**
 * @author zhangqian
 *	
 *JpaSpecificationExecutor 具备分页条件查询方法
 *
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> ,JpaSpecificationExecutor<User>{

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
	 * 根据name查询用户
	 * @param name
	 * @return
	 */
	User findByname(String name);

	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	User findByMobile(String mobile);
	
	/**
	 * 根据状态查询用户
	 * @author chenhl
	 * @param Status
	 * @return
	 */
	List<User> findByStatus(UserStatus Status);

	@Query("select count(*) from User where createDate between ?1 and ?2 and status=?3 and entity =?4")
	Long countByStatus(Date begindate, Date enddate, UserStatus status, UserEntity entity);
}
