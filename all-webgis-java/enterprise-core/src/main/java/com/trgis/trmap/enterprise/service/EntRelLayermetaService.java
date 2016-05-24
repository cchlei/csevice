package com.trgis.trmap.enterprise.service;

import java.util.List;

import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.model.EntRelLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 扩展属性表结构(已发布)
 * @author Alice
 *
 * 2015年12月25日
 */
public interface EntRelLayermetaService {
	/**
	 * @author chenhl
	 * 删除EntRelLayermeta
	 */
	public void deleteEntRelLayermeta(Long id);
	
	/**
	 *@author Administrator chenhl
	 *根据 EntUserMap 获得EntLayermeta
	 */
	public EntRelLayermeta findByEntRelUserMap(EntUserMap entUserMap);
	
	/**
	 * 根据图层元数据表id查询扩展属性表结构（列结构）列表
	 * 
	 * @author doushan
	 * @param entLayermeta
	 * @return
	 */
	public List<EntRelAttributemeta> findByEntRelLayermeta(EntLayermeta entLayermeta);
}
