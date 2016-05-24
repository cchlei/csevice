package com.trgis.trmap.enterprise.service;

import java.util.List;

import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;

public interface EntRelAttributemetaService {
	/**
	 * 根据图层元数据表id查询扩展属性表结构（列结构）列表
	 * 
	 * @author 2her
	 * @param entLayermeta
	 * @return
	 */
	public List<EntRelAttributemeta> findByEntLayermeta(EntLayermeta entLayermeta);

	/**
	 * 根据id获取列结构对象
	 * @param id
	 * @return
	 */
	public EntRelAttributemeta findById(Long id);
	
	/**
	 * 根据扩展属性表 中的ID 进行级联删除 图层元数据
	 * 
	 * @author chenhl
	 * @param
	 * @return
	 */
	public void deletByEntRelAttributemeta(Long id);
	
	
}
