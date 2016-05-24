package com.trgis.trmap.personal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserIntegral;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserIntegralDao
 * @Description: 积分等级dao
 * @author yanpeng
 * @date 2016年3月29日 上午11:47:56
 */
@Repository
public interface UserIntegralDao extends JpaRepository<UserIntegral,Long>,JpaSpecificationExecutor<UserIntegral>{

	@Query("from UserIntegral where user = ?1")
	public UserIntegral query(User user);

}
