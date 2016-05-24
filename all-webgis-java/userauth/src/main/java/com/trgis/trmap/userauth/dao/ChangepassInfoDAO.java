/**
 * 
 */
package com.trgis.trmap.userauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.userauth.model.ChangepassInfo;

/**
 * 用户重置密码业务
 * 
 * @author zhangqian
 *
 */
@Repository
public interface ChangepassInfoDAO extends JpaRepository<ChangepassInfo, Long>,JpaSpecificationExecutor<ChangepassInfo> {

	List<ChangepassInfo> findByUserId(Long userId);

	ChangepassInfo findBySecurityuserAndSecuritykey(String securityuser, String securitykey);


}
