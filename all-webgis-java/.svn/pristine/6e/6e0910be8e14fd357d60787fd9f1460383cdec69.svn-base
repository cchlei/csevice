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
import com.trgis.trmap.enterprise.dao.EntRelMapGraphicsDAO;
import com.trgis.trmap.enterprise.exception.EntMapGraphicsException;
import com.trgis.trmap.enterprise.exception.EntUserMapException;
import com.trgis.trmap.enterprise.model.EntRelMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntRelGraphicsService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.hstore.dao.EntRelMapPropertiesDao;

/**
 * 企业地图矢量服务(已发布)
 * @author doris
 * @date 2015-12-25
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntRelGraphicslServiceImpl implements EntRelGraphicsService {
	private static final Log log = LogFactory.getLog(EntRelGraphicslServiceImpl.class);
	@Autowired
	private EntRelMapGraphicsDAO entRelMapGraphicsDAO;
	@Autowired
	private EntRelMapPropertiesDao relPropertiesDao;
	@Override
	public Page<EntRelMapGraphics> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
			try {
			Specification<EntRelMapGraphics> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
			entRelMapGraphicsDAO.count(specifications);
			long count = entRelMapGraphicsDAO.count(specifications);
			if(count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			return entRelMapGraphicsDAO.findAll(specifications,page);
			} catch (Exception e) {
				log.debug("按条件查询企业矢量列表（已发布）失败！");
				throw new EntUserMapException("按条件查询企业矢量列表（已发布）失败！");
			}
		}
	@Override
	public List<EntRelMapGraphics> findByEntRelUserMap(EntUserMap entUserMap) {
		try {
			if (BeanUtil.isEmpty(entUserMap)) {
				throw new EntMapGraphicsException("企业图层对象不能为空！");
			}
			return entRelMapGraphicsDAO.findByEntRelUserMap(entUserMap, EnumUtil.DELFLAG.NOMAL.getValue());/**/
		} catch (Exception e) {
			log.debug("按企业图层查询地图矢量失败！");
			throw new EntMapGraphicsException("按企业图层查询地图矢量失败！");
		}
	}
	
	@Override
	public void delete(Long id) {
		try {
			EntRelMapGraphics mapRelGraphics = entRelMapGraphicsDAO.findOne(id);
			if (BeanUtil.isEmpty(mapRelGraphics)) {
				throw new EntMapGraphicsException("地图矢量未找到！");
			}	
			relPropertiesDao.deleteByFeatureId(id);
			log.debug("地图矢量扩展属性值删除成功！");
			entRelMapGraphicsDAO.delete(id);
			log.debug("地图矢量删除成功！");
		} catch (Exception e) {
			log.debug("地图矢量删除失败！");
			throw new EntMapGraphicsException("地图矢量删除失败！");
		}		
	}
	
	@Override
	public void deleteByEntRelUserMap(EntUserMap entUserMap) {
		relPropertiesDao.deleteByMapId(entUserMap.getId());
		entRelMapGraphicsDAO.deleteByEntRelUserMap(entUserMap);
	}
	
}
