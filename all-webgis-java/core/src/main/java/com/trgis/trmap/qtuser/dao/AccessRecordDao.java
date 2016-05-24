package com.trgis.trmap.qtuser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.AccessRecord;
import com.trgis.trmap.qtuser.model.UserMap;
/**
 * 个人地图访问记录dao
 * @author Alice
 *
 * 2015年10月7日
 */
@Repository
public interface AccessRecordDao extends JpaRepository<AccessRecord,Long>,JpaSpecificationExecutor<AccessRecord>{

	
	@Query("select requesttime,count(*) from AccessRecord where userMap = ?1 group by requesttime order by requesttime")
	public List<Object[]> countByDaysAndId(UserMap usermap);

}
