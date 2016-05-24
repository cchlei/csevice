package com.trgis.trmap.hstore.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.hstore.BeanUtil;
import com.trgis.trmap.hstore.dao.EntMapPropertiesDao;
import com.trgis.trmap.hstore.exception.MapPropertiesException;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.SQLUtil;
import com.trgis.trmap.hstore.service.query.Sort;
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntMapPropertiesServiceImpl implements EntMapPropertiesService{
	private static final Log log = LogFactory.getLog(EntMapPropertiesServiceImpl.class);
	
	@Autowired
	private EntMapPropertiesDao entMapPropertiesDao;

	public EntMapPropertiesServiceImpl() {
		super();
		log.debug("MapPropertiesServiceImpl初始化！");
	}
	@PersistenceContext
	private EntityManager em;
	@Override
	public void save(EntMapProperties mapProperties) throws MapPropertiesException {
		
		try {
			if (BeanUtil.isEmpty(mapProperties)) {
				throw new MapPropertiesException("地图属性扩展对象不能为空");
			}
			em.persist(mapProperties);
			log.debug("地图属性扩展成功！");
		} catch (Exception e) {
			log.debug("地图属性扩展失败！");
			throw new MapPropertiesException("地图属性扩展失败！");
		}
		
	}

	@Override
	public void edit(EntMapProperties mapProperties) {
		try {
			if (BeanUtil.isEmpty(mapProperties)) {
				throw new MapPropertiesException("地图属性扩展对象不能为空");
			}
			em.merge(mapProperties);
			log.debug("地图属性编辑成功！");
		} catch (Exception e) {
			log.debug("地图属性编辑失败！");
			throw new MapPropertiesException("地图属性编辑失败！");
		}
		
	}

	@Override
	public EntMapProperties findById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				new MapPropertiesException("地图属性扩展id不能为空！");
			}
			EntMapProperties mapProperties = em.find(EntMapProperties.class, id);
			Hibernate.initialize(mapProperties);
			return mapProperties;
		} catch (HibernateException e) {
			log.debug("按id查询地图属性失败！");
			throw new MapPropertiesException("按id查询地图属性失败！");
		}
	}

	@Override
	public EntMapProperties findByFeature(Long featureid) {
		try {
			if (BeanUtil.isEmpty(featureid)) {
				throw new MapPropertiesException("矢量地图id为空");
			}
			String sql = "select * from qtmap_enterprise_properties where featureid = %s";
			sql = String.format(sql, featureid);
			TypedQuery<EntMapProperties> query = (TypedQuery<EntMapProperties>) em.createNativeQuery(sql,EntMapProperties.class);
			List<EntMapProperties> list = query.getResultList();
			if (BeanUtil.isEmpty(list)) {
				new MapPropertiesException("地图属性扩展为空！");
				return null; 
			}
			return list.get(0); 
		} catch (HibernateException e) {
			log.debug("按矢量地图查询地图属性失败！");
			throw new MapPropertiesException("按矢量地图查询地图属性失败！");
		}
	}

	@Override
	public Page queryPage(Page page, Sort sort, List<Conditions> conditions, String relation) {
		try {
			String countSql = "select count(*) from qtmap_enterprise_properties ";
			String sql = "select * from qtmap_enterprise_properties ";
			
			// 根据条件过滤
			if (conditions != null && conditions.size() > 0) {
				String condSql = SQLUtil.convertConditions(conditions, relation);
				countSql+=condSql;
				sql += condSql;
			}
			if(sort != null) {
				sql += sort.sortSQL();
			}
			if(page != null) {
				// 先获取分页总记录数
				int recordCount = ((BigInteger) em.createNativeQuery(countSql).getSingleResult()).intValue();
				page.setRecordCount(recordCount);
				sql += page.pageSql();
			}
			TypedQuery<EntMapProperties> query = (TypedQuery<EntMapProperties>) em.createNativeQuery(sql,EntMapProperties.class);
			List<EntMapProperties> list = query.getResultList();
			page.setResultList(list);
			return page;
		} catch (MapPropertiesException e) {
			log.debug("地图属性扩展表分页查询失败！");
			throw new MapPropertiesException("地图属性扩展表分页查询失败！");
		}
	}
	

	/**
	 * 根据地图矢量id删除矢量扩展属性
	 * 
	 * @param 
	 */
	@Override
	public void deleteByFeature(Long featureid) {
		EntMapProperties entMapProperties = findByFeature(featureid);
		if (BeanUtil.isEmpty(entMapProperties)) {
//			throw new MapPropertiesException("地图属性扩展中地图矢量id为空");
			log.debug("不存在扩展属性！");
			}
		else{
			try{
				entMapPropertiesDao.deleteByFeatureId(featureid);
				log.debug("地图属性扩展表删除成功！");
				}catch(MapPropertiesException e){
					log.debug("地图属性扩展表删除失败！");
					throw new MapPropertiesException("地图属性扩展表删除失败！");
				}
			}
		}

	@Override
	public void deleteBymapId(Long mapId) {
		try {
			if (BeanUtil.isEmpty(mapId)) {
				throw new MapPropertiesException("地图属性所对应的图层mapID不能为空");
			}
			entMapPropertiesDao.deleteByMapId(mapId);
			log.debug("地图属性扩展表删除成功！");
		} catch (Exception e) {
			log.debug("地图属性扩展表删除成功！");
			throw new MapPropertiesException("地图属性扩展表删除失败！");
		}		
	}

}
