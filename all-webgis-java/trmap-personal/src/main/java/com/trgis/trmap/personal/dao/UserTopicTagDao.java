package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserTopicTag;

/**
 * 
 * @Title: UserTopicTagDao
 * @Description: 专题备选表
 * @author liuyan
 * @date 2016年3月11日 上午11:46:41
 *
 */
@Repository
public interface UserTopicTagDao extends JpaRepository<UserTopicTag, Long>, JpaSpecificationExecutor<UserTopicTag> {

	/**
	 * 查询出所有专题名称
	 * 
	 * @author liuyan
	 * @return
	 */
	@Query("select name from UserTopicTag order by sort asc")
	public List<String> findAllName();

}
