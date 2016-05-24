package com.trgis.trmap.enterprise.service;

import java.util.List;

import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 *  扩展属性表结构（表名） 
 * 
 * @author chlei
 *
 * @2015年12月2日下午3:02:33
 */
public interface EntLayermetaService {
	/**
	 * 根据EntUserMap创建layermeta对应的结构
	 * 
	 * @param enterpriselayermeta
	 * @return
	 */
	public void createEnterpriseLayermeta(EntLayermeta entLayermeta);
	
	/**
	 * 根据地图查询layermeta
	 * 目前关系1：1,以后可能扩展为1：n
	 * @param map
	 * @return
	 */
	public List<EntLayermeta> findLayermetasByMap(EntUserMap map);
	
	/**
	 * 查询EntLayermeta
	 * @param id
	 * @return
	 */
	public EntLayermeta findById(Long id);
	
	/**
	 * @author Administrator chenhl
	 * 删除EntLayermeta
	 */
	public void deleteEntLayermeta(Long id);
	
	/**
	 *@author Administrator chenhl
	 *根据 EntUserMap 获得EntLayermeta
	 */
	public EntLayermeta findByEntUserMap(EntUserMap entUserMap);

}
