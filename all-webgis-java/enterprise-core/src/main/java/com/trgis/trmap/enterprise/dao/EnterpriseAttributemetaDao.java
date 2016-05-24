package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
/**
 * 
 * @author chlei
 *
 * @2015年12月2日下午5:45:08
 */
@Repository
public interface EnterpriseAttributemetaDao extends JpaRepository<EntAttributemeta, Long>,JpaSpecificationExecutor<EntAttributemeta>{
	/**
	 * 这样就能执行查询了 是一种什么定义？
	 * 答：是jpa 自带的一种查询 规范
	 * @param entLayermeta
	 * @return
	 */
	public  List<EntAttributemeta> findByLayermeta(EntLayermeta layermeta);
	/** 
	 * 根据 EntLayermeta 对象查询出对应相关（映射关系问 多（EntAttributemeta）对一（EntLayermeta））的  属性结构
	 */
	@Query("from EntAttributemeta  where layermeta = ?1") 
	public EntAttributemeta findByEntAttributemetaSql(EntLayermeta layermeta);
	
	public List<EntAttributemeta> findByAttralias(String attralias);
}
