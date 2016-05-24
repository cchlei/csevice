package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;

/**
 * 企业地图扩展属性结构（已发布）
 * 
 * @author Alice
 *
 * 2015年12月10日
 */
@Repository
public interface EntRelAttributemetaDAO  extends JpaRepository<EntRelAttributemeta,Long>,JpaSpecificationExecutor<EntRelAttributemeta>{
	
	public  List<EntRelAttributemeta> findByLayermeta(EntLayermeta layermeta);
}
