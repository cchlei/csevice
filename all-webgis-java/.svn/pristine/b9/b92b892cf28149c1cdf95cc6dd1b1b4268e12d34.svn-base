package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.enterprise.dao.EntRelAttributemetaDAO;
import com.trgis.trmap.enterprise.dao.EntRelLayermetaDAO;
import com.trgis.trmap.enterprise.exception.EntAttributemetaException;
import com.trgis.trmap.enterprise.exception.EntLayermetaException;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.model.EntRelLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntRelLayermetaService;
import com.trgis.trmap.enterprise.util.BeanUtil;
/**
 * 元数据（已发布）
 * @author Alice
 *
 * 2015年12月25日
 */
@Service
@Transactional
public class EntRelLayermetaServiceImpl implements EntRelLayermetaService{
	private static final Log log = LogFactory.getLog(EntLayermetaService.class);
	
	@Autowired
	private EntRelAttributemetaDAO entRelAttributemetaDAO;
	@Autowired
	private EntRelLayermetaDAO entRelLayermetaDAO;
	/**
	 * @author chenhl
	 * 根据id（这个id是通过 与 EntAttributemeta关） 删除 EntLayermeta
	 */
	@Override
	public void deleteEntRelLayermeta(Long id) {
		// TODO Auto-generated method stub
		try {
			entRelLayermetaDAO.delete(id);
		} catch (Exception e) {
			log.debug("按id查询图层元数据表失败！");
			throw new EntLayermetaException("按id查询图层元数据表失败！");
		}
	}
	
	/**
	 * @author Administrator chenhl
	 * 根据 EntLayermeta对象获得 EntLayermeta
	 */
	@Override
	public EntRelLayermeta findByEntRelUserMap(EntUserMap entUserMap) {
		// TODO Auto-generated method stub
		try {
			return  entRelLayermetaDAO.findByEntRelUserMap(entUserMap);
		} catch (Exception e) {
			log.debug("根据地图数据表 查询 扩展属性表结构（表名）失败！");
			throw new EntLayermetaException("根据地图数据表 查询 扩展属性表结构（表名）失败！");
		}
		
	}
	@Override
	public List<EntRelAttributemeta> findByEntRelLayermeta(EntLayermeta entLayermeta) {
		try {
			if (BeanUtil.isEmpty(entLayermeta)) {
				throw new EntAttributemetaException("图层元数据表信息不能为空！");
			}
			return entRelAttributemetaDAO.findByLayermeta(entLayermeta);
		} catch (Exception e) {
			log.debug("查询扩展属性表结构（列结构） 信息失败！");
			throw new EntAttributemetaException("查询扩展属性表结构（列结构） 信息失败！");
		}
	}
}
