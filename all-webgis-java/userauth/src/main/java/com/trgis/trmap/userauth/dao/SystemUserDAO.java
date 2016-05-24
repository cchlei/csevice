package com.trgis.trmap.userauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.userauth.model.SystemUser;

/**
 * 
 * @ClassName: SystemUserDAO
 * @Description: 系统用户数据访问层
 * @author zhangqian
 * @date 2016年1月8日 上午9:13:43
 *
 */
@Repository
public interface SystemUserDAO extends JpaRepository<SystemUser, Long>,JpaSpecificationExecutor<SystemUser> {

	SystemUser findByUsername(String username);

}
 