package com.trgis.trmap.personal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.userauth.model.User;

/**
 * @author liuyan
 * 关于记录附件的dao
 */
@Repository
public interface UserAttachDao extends JpaRepository<UserAttach,Long>,JpaSpecificationExecutor<UserAttach>{
	
	/**
	 * 根据记录查所属附件
	 */
	@Query("from UserAttach where userRecord = ?1 and delflag= ?2")
	public List<UserAttach> findByUserRecord(UserRecord userRecord,Integer delflag);
	/**
	 * 根据记录及类型查询附件
	 */
	@Query(nativeQuery=true,value="select * from trmap_user_attach where rid = ?1 and delflag= ?2 and ?3 like '%,'||attach_suffix||',%' limit ?4")
	public List<UserAttach> findByUserRecord(Long rid,Integer delflag,String ext, Integer length);

	public List<UserAttach> findUserAttachByOssid(String ossid);

	/**
	 * 清除事件的附件信息	
	 * @param userRecord
	 */
	@Modifying 
	@Query("update UserAttach set userRecord = null where userRecord =?1") 
	public void clearAttachfileById(UserRecord userRecord);


	/**
	 * @Description: 保存记录时修改附件表
	 * @author yanpeng
	 * @date 2016年3月10日 下午4:04:57
	 * @param id 记录id
	 * @param ossid 附件云端id
	 */
	@Modifying
	@Query("update UserAttach set rid = ?1 where ossid =?2")
	public void updateRecord(Long id,String ossid); 
	/**
	 * @Description:根据oosid删除附件
	 * @Author liuyan 
	 * @Date 2016年3月16日 下午1:12:25
	 * @param delflag
	 * @param ossid
	 */
	@Modifying
	@Query("update UserAttach set delflag = ?1 where ossid =?2")
	public void deleteAttach(Integer delflag,String ossid); 
	/**
	 * 清除事件的附件信息 只改变专题态	
	 * @param userRecord
	 */
	@Modifying
	@Query("update UserAttach set delflag =?1 where userRecord =?2") 
	public void clearAttachfileById(Integer delflag ,UserRecord userRecord);
	
	/**
	 * @Description: 批量删除
	 * @author yanpeng
	 * @date 2016年4月11日 下午1:00:27
	 * @param delflag
	 * @param ids 数组
	 */
	@Modifying
	@Query(nativeQuery=true,value="update trmap_user_attach set delflag =?1 where rid in (?2)") 
	public void clearAttachfileByIds(Integer delflag ,Long[] ids);


	/**
	 * @Description: 查询一个未删除的附件
	 * @author yanpeng
	 * @date 2016年3月18日 下午5:05:40
	 * @param id
	 * @param value
	 * @return
	 */
	@Query(nativeQuery=true , value="select ossid from trmap_user_attach WHERE rid= ?1 and delflag = ?2 limit 1")
	public String findOneByRecord(Long rid, Integer delflag);


	/**
	 * @Description: 查询用户所占用的空间大小
	 * @author yanpeng
	 * @date 2016年3月29日 上午11:47:06
	 * @param user
	 * @param delflag
	 * @return 以MB 为单位返回
	 */
	@Query("select sum(attachSize) from UserAttach where user = ?1 and delflag= ?2")
	public Long querySpaceSize(User user,Integer delflag);

}
