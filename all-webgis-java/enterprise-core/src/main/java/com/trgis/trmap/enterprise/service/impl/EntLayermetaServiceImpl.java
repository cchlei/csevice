package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.enterprise.dao.EntLayermetaDao;
import com.trgis.trmap.enterprise.exception.EntLayermetaException;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.util.BeanUtil;
/**
 * 扩展属性表结构（表名）
 * 
 * @author chlei
 *
 * @2015年12月2日下午3:51:51
 */
@Service
@Transactional
public class EntLayermetaServiceImpl implements EntLayermetaService{
	
	private static final Log log = LogFactory.getLog(EntLayermetaService.class);
	@Autowired
	private EntLayermetaDao enterpriseLayermetaDao;
	
	@Override
	public void createEnterpriseLayermeta(EntLayermeta entLayermeta) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(entLayermeta)) {
				throw new EntLayermetaException("扩展属性表结构（表名）默认信息不能为空！");
			}
			enterpriseLayermetaDao.save(entLayermeta);
			log.debug("扩展属性表结构（表名）信息保存成功！");
		} catch (Exception e) {
			log.debug("扩展属性表结构（表名）信息保存失败！");
			throw new EntLayermetaException("扩展属性表结构（表名）信息保存失败！");
		}
//		return log.toString();
	}

	@Override
	public List<EntLayermeta> findLayermetasByMap(EntUserMap map){
		// TODO Auto-generated method stub
		return enterpriseLayermetaDao.findByMap(map);
	}

	@Override
	public EntLayermeta findById(Long id) {
		try {
			return enterpriseLayermetaDao.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询图层元数据表失败！");
			throw new EntLayermetaException("按id查询图层元数据表失败！");
		}
	}
	/**
	 * @author Administrator chenhl
	 * 根据id（这个id是通过 与 EntAttributemeta关） 删除 EntLayermeta
	 */
	@Override
	public void deleteEntLayermeta(Long id) {
		// TODO Auto-generated method stub
		try {
			 enterpriseLayermetaDao.delete(id);
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
	public EntLayermeta findByEntUserMap(EntUserMap entUserMap) {
		// TODO Auto-generated method stub
		try {
			return  enterpriseLayermetaDao.findByEntUserMap(entUserMap);
		} catch (Exception e) {
			log.debug("根据地图数据表 查询 扩展属性表结构（表名）失败！");
			throw new EntLayermetaException("根据地图数据表 查询 扩展属性表结构（表名）失败！");
		}
		
	}

}
