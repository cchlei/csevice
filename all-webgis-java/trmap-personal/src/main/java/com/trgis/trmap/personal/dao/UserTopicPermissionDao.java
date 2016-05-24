package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserTopicPermission;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserTopicPermissionDao
 * @Description: 专题权限dao
 * @author yanpeng
 * @date 2016年5月12日 下午2:01:17
 */
@Repository
public interface UserTopicPermissionDao extends JpaRepository<UserTopicPermission,Long>,JpaSpecificationExecutor<UserTopicPermission>{

	@Query("from UserTopicPermission where topic_id=?1")
	List<UserTopicPermission> queryBytopic(Long id);
	
	@Modifying
	@Query("delete from UserTopicPermission where topic_id=?1")
	void deleteByTopic(Long topicId);

	@Modifying
	@Query("delete from UserTopicPermission where topic_id=?1 and user_id=?2")
	void delete(Long topicId, Long userId);

	@Modifying
	@Query("delete from UserTopicPermission where topic_id=?1 and user_id in(?2)")
	void delete(Long topicId, Long[] userIds);
	
	@Query("select count(*) from UserTopicPermission where topic_id=?1")
	Long countUser(Long topicId);

	@Query("from UserTopicPermission where user=?1")
	List<UserTopicPermission> queryByUser(User user);
}
