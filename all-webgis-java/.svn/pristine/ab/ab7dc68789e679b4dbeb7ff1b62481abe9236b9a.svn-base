package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
/**
 * 
 * @author chlei
 *
 * @2015年12月2日下午5:45:08
 */
@Repository
public interface EntAttributemetaDao extends JpaRepository<EntAttributemeta, Long>,JpaSpecificationExecutor<EntAttributemeta>{
	/**
	 * @param entLayermeta
	 * @return
	 */
	public  List<EntAttributemeta> findByLayermeta(EntLayermeta layermeta);
	
	@Query("from EntAttributemeta  where attralias =?1 and layermeta = ?2") 
	public List<EntAttributemeta> findByAttralias(String attralias,EntLayermeta layermeta);
}
