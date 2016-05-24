package com.trgis.trmap.enterprise.service;

import java.util.List;

import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;

public interface EntAttributemetaService {
	/**
	 * 根据EntUserMap创建layermeta对应的结构
	 * 
	 * @param enterpriselayermeta
	 * @return
	 */
	public String createEnterpriseAttributemeta(EntAttributemeta entAttributemeta);

	/**
	 * 维护企业地图扩展属性表结构
	 * 
	 * @param thList
	 * @param emapid
	 */
	public void editAttriButeMeta(List<String[]> thList, Long emapid);

	/**
	 * 根据图层元数据表id查询扩展属性表结构（列结构）列表
	 * 
	 * @author doushan
	 * @param entLayermeta
	 * @return
	 */
	public List<EntAttributemeta> findByEntLayermeta(EntLayermeta entLayermeta);

	/**
	 * 根据扩展属性表 中的ID 进行级联删除 图层元数据
	 * 
	 * @author chenhl
	 * @param
	 * @return
	 */
	public void deletByEntAttributemeta(Long id);

	/**
	 * 根据标题及entLayermeta获取
	 * 
	 * @author Alice
	 * @param
	 * @return
	 */
	public EntAttributemeta findByAttralias(String attralias, EntLayermeta entLayermeta);
}
