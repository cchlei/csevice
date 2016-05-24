package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.enterprise.dao.EntMapGraphicsDAO;
import com.trgis.trmap.enterprise.exception.EntMapGraphicsException;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.hstore.dao.EntMapPropertiesDao;

/**
 * 地图矢量service实现
 * 
 * @author doris
 * @date 2015-12-02
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntMapGraphicsServiceImpl implements EntMapGraphicsService {
	private static final Log log = LogFactory.getLog(EntMapGraphicsServiceImpl.class);
	@Autowired
	private EntMapGraphicsDAO entMapGraphicsDao;
	@Autowired 
	EntMapPropertiesDao entMapPropertiesDao;

	@Override
	public void createEntMapGraphics(EntMapGraphics entMapGraphics) {
		try {
			if (BeanUtil.isEmpty(entMapGraphics)) {
				throw new EntMapGraphicsException("地图矢量对象不能为空！");
			}
			entMapGraphicsDao.save(entMapGraphics);
			log.debug("地图矢量保存成功！");
		} catch (Exception e) {
			log.debug("地图矢量保存失败！");
			throw new EntMapGraphicsException("地图矢量保存失败！");
		}

	}

	@Override
	public void editEntMapGraphics(EntMapGraphics entMap) {
		try {
			if (BeanUtil.isEmpty(entMap)) {
				throw new EntMapGraphicsException("地图矢量对象不能为空！");
			}
			entMapGraphicsDao.saveAndFlush(entMap);
			log.debug("地图矢量修改成功！");
		} catch (Exception e) {
			log.debug("地图矢量修改失败！");
			throw new EntMapGraphicsException("地图矢量修改失败！");
		}

	}

	@Override
	public EntMapGraphics findById(Long id) {
		try {
			return entMapGraphicsDao.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询地图矢量失败！");
			throw new EntMapGraphicsException("按id查询地图矢量失败！");
		}
	}

	@Override
	public List<EntMapGraphics> findByEntUserMap(EntUserMap entUserMap) {
		try {
			if (BeanUtil.isEmpty(entUserMap)) {
				throw new EntMapGraphicsException("企业图层对象不能为空！");
			}
			return entMapGraphicsDao.findByEntUserMap(entUserMap, EnumUtil.DELFLAG.NOMAL.getValue());
		} catch (Exception e) {
			log.debug("按企业图层查询地图矢量失败！");
			throw new EntMapGraphicsException("按企业图层查询地图矢量失败！");
		}
	}

	@Override
	public EntMapGraphics findByIdentifykey(EntUserMap entUserMap, String identifykey) {
		// TODO Auto-generated method stub
		List<EntMapGraphics> emplist = entMapGraphicsDao.findByIdentifykey(entUserMap, identifykey);

		if (BeanUtil.isEmpty(emplist)) {
			log.debug("根据唯一标识列未找到企业地图！");
			throw new EntMapGraphicsException("根据唯一标识列未找到企业地图！");
		} else {
			return emplist.get(0);
		}
	}

	/*
	 * 获得
	 */
	@Override
	public List<EntMapGraphics> findAllMapGraphics() {
		List<EntMapGraphics> emplist = entMapGraphicsDao.findAll();
		if (BeanUtil.isEmpty(emplist)) {
			log.debug("未找到企业地图数据！");
			throw new EntMapGraphicsException("未找到企业地图数据！");
		} else {
			return emplist;
		}
	}

	@Override
	public Page<EntMapGraphics> findByEntUserMapConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,OrderBy... order) {
		Specification<EntMapGraphics> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = entMapGraphicsDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return entMapGraphicsDao.findAll(specifications,page);
	}
	
	@Override
	public void delete(Long id) {
		try {
			EntMapGraphics mapGraphics = entMapGraphicsDao.findOne(id);
			if (BeanUtil.isEmpty(mapGraphics)) {
				throw new EntMapGraphicsException("地图矢量未找到！");
			}	
			entMapPropertiesDao.deleteByFeatureId(id);
			log.debug("地图属性扩展表删除成功！");
			entMapGraphicsDao.delete(id);
			log.debug("地图矢量删除成功！");
		} catch (Exception e) {
			log.debug("地图矢量删除失败！");
			throw new EntMapGraphicsException("地图矢量删除失败！");
		}		
	}

	@Override
	public List<EntMapGraphics> findByEntUserMap(String keyword,String geom, Long mapid, Integer pageSize,Integer pageNum) {
		if(BeanUtil.isEmpty(keyword)){
			keyword = "";
		}
		List<EntMapGraphics> emplist = entMapGraphicsDao.findByEntUserMap(geom, keyword, mapid, pageSize, pageNum);
		return emplist;
	}

	@Override
	public Long findCountByMap(String keyword,String geom, Long mapid) {
		if(BeanUtil.isEmpty(keyword)){
			keyword = "";
		}
		Long count = entMapGraphicsDao.findCountEntMapGraphics(geom, mapid, keyword);
		return count;
	}

	@Override
	public void deleteByEntUserMap(EntUserMap usermap) {
		//删除关联扩展属性值
		entMapPropertiesDao.deleteByMapId(usermap.getId());
		//删除矢量
		entMapGraphicsDao.deleteByEntUserMap(usermap);
	}

}
