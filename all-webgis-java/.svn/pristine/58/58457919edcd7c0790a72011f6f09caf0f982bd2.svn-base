package com.trgis.trmap.enterprise.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
@Repository
public interface EntLayermetaDao  extends JpaRepository<EntLayermeta,Long>,JpaSpecificationExecutor<EntLayermeta>{
	
	@Query("from EntLayermeta  where map = ?1")
	public EntLayermeta findByEntUserMap(EntUserMap entUserMap);
	
	public List<EntLayermeta> findByMap(EntUserMap map);
}
