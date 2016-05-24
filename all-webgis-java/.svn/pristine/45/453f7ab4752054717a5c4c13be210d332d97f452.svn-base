package com.trgis.trmap.enterprise.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.trgis.common.util.CollectionUtils;
import com.trgis.trmap.enterprise.dao.EntAttributemetaDao;
import com.trgis.trmap.enterprise.dao.EntLayermetaDao;
import com.trgis.trmap.enterprise.dao.EntMapGraphicsDAO;
import com.trgis.trmap.enterprise.dao.EntRelAttributemetaDAO;
import com.trgis.trmap.enterprise.dao.EntRelLayermetaDAO;
import com.trgis.trmap.enterprise.dao.EntRelMapGraphicsDAO;
import com.trgis.trmap.enterprise.dao.EntRelUserMapDAO;
import com.trgis.trmap.enterprise.dao.EntUserMapDAO;
import com.trgis.trmap.enterprise.exception.EntUserMapException;
import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.model.EntRelLayermeta;
import com.trgis.trmap.enterprise.model.EntRelMapGraphics;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EncrUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.hstore.dao.EntMapPropertiesDao;
import com.trgis.trmap.hstore.dao.EntRelMapPropertiesDao;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.model.EntRelMapProperties;
/**
 * 企业地图服务crud实现
 * 
 * @author chlei
 *
 * @2015年12月1日下午4:41:37
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntMapServiceImpl implements EntMapService {
	
	private static final Log log = LogFactory.getLog(EntMapService.class);
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private EntUserMapDAO entUserMapDAO;
	@Autowired
	private EntRelUserMapDAO entRelUserMapDAO;
	@Autowired
	private EntMapGraphicsDAO entMapGraphicsDao;
	@Autowired
	private EntRelMapGraphicsDAO entRelMapGraphicsDao;
	@Autowired
	private EntRelLayermetaDAO entRelLayermetaDao;
	@Autowired
	private EntLayermetaDao layermetaDao;
	@Autowired
	private EntAttributemetaDao attributemetaDao;
	@Autowired
	private EntRelAttributemetaDAO relAttributemetaDao;
	@Autowired
	private EntMapPropertiesDao propertiesDao;
	@Autowired
	private EntRelMapPropertiesDao relpropertiesDao;
	@Override
	public void createEnterpriseMap(EntUserMap enterpriseMap) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(enterpriseMap)) {
				throw new EntUserMapException("制图与发布默认信息不能为空！");
			}
			entUserMapDAO.save(enterpriseMap);
			log.debug("制图与发布默认信息保存成功！");
		} catch (Exception e) {
			log.debug("制图与发布默认信息保存失败！");
			throw new EntUserMapException("制图与发布默认信息保存失败！");
		}
//		return log.toString();
	}

	@Override
	public String findUserMapList(Long userId) {
		return null;
	}
	/**
	 * @author Administrator chenhl
	 */
	@Override
	public EntUserMap findUserMapById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new EntUserMapException("企业图层id不能为空!");
			}
			return entUserMapDAO.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询企业图层失败！");
			throw new EntUserMapException("按id查询企业图层失败！");
		}
	}
	/**
	 * @author Administrator chenhl
	 */
	@Override
	public void editUserMap(EntUserMap entUserMap) {
		 em.merge(entUserMap);
	}
	/*
	 * @author Administrator chenhl
	 * 
	 */
	@Override
	public void deleteEntUserMap(Long id) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new EntUserMapException("企业图层id不能为空!");
			}
			EntUserMap userMap = entUserMapDAO.findOne(id);
			//暂时不做发布图层及扩展属性删除，因为服务删除后，订单还要继续显示，所需字段不能删除
//			EntLayermeta entLayermeta = layermetaDao.findByEntUserMap(userMap);
//			List<EntAttributemeta> attributemetaList = attributemetaDao.findByLayermeta(entLayermeta);//entRelLayermetaService.findByEntRelLayermeta(entLayermeta);
//			for (EntAttributemeta attributemeta : attributemetaList) {
//				attributemetaDao.delete(attributemeta);//扩展属性结构
//			}
//			layermetaDao.delete(entLayermeta);//图层
			propertiesDao.deleteByMapId(userMap.getId());//扩展属性值
			entMapGraphicsDao.deleteByEntUserMap(userMap);//矢量
			userMap.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			entUserMapDAO.saveAndFlush(userMap);
		} catch (Exception e) {
			log.debug("按id查询企业图层失败！");
			throw new EntUserMapException("按id查询企业图层失败！");
		}
	}

	@Override
	public Page<EntUserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<EntUserMap> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = entUserMapDAO.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return entUserMapDAO.findAll(specifications,page);
	}
	/**
	 * @author Alice
	 * 发布地图
	 * @param id
	 */
	@Override
	public void releaseUserMap(EntUserMap map) {
		try {
			map.setIsshare(EnumUtil.SHARE.YFB.getValue());
			entUserMapDAO.save(map);
			//1、企业地图复制
			EntRelUserMap relmap = CollectionUtils.copyBean(map, EntRelUserMap.class);
			EntRelUserMap already = entRelUserMapDAO.findOne(map.getId());
			relmap.setUmcreatetime(BeanUtil.isEmpty(already)?new Date():already.getUmcreatetime());//创建时间
			Date time = BeanUtil.isEmpty(already)?new Date():already.getReleasetime();
			relmap.setReleasetime(time);//发布时间
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			cal.set(Calendar.YEAR,cal.get(Calendar.YEAR) + map.getValidityregion());
			Date date=cal.getTime();
			relmap.setValiditytime(date);
			relmap.setUpdateretime(new Date());//重新发布时间
			relmap.setEmap(map);//企业地图 emap;
			relmap.setShareurl(EncrUtil.encoderByDES(map.getId()+"", EncrUtil.URLKEY));//服务地址;访问时：basePath+Constants.MAPSHARE+userMap.getShareurl()
			Long count = entMapGraphicsDao.findCountByMap(map,EnumUtil.DELFLAG.NOMAL.getValue());
			relmap.setFeaturecount(count); //要素数量 featurecount;
			// TODO 调用计算矢量范围接口
			relmap.setScope("");//范围 scope;
			entRelUserMapDAO.save(relmap);
			//2、企业地图对应元数据层复制(现在一对一，以后扩展为一对多)
			List<EntLayermeta> layermetalist = layermetaDao.findByMap(map);
			List<EntRelLayermeta> rellayermetalsit = CollectionUtils.copyBean(layermetalist, EntRelLayermeta.class);
			entRelLayermetaDao.save(rellayermetalsit);
			//3、企业地图对应扩展属性结构复制
			for (int i = 0; i < layermetalist.size(); i++) {
				List<EntAttributemeta> attributemetas = attributemetaDao.findByLayermeta(layermetalist.get(i));
				List<EntRelAttributemeta> relattributemetas = CollectionUtils.copyBean(attributemetas, EntRelAttributemeta.class);
				relAttributemetaDao.save(relattributemetas);
			}
			//4、企业地图对应矢量要素复制
			List<EntMapGraphics> graphicslist =  entMapGraphicsDao.findByEntUserMap(map, EnumUtil.DELFLAG.NOMAL.getValue());
			List<EntRelMapGraphics> relgraphicslist = CollectionUtils.copyBean(graphicslist, EntRelMapGraphics.class);
			entRelMapGraphicsDao.save(relgraphicslist);
			//5、企业地图对应扩展属性hstore值复制
			for (int i = 0; i < graphicslist.size(); i++) {
				List<EntMapProperties> properties = propertiesDao.findByFeatureId(graphicslist.get(i).getId());
				List<EntRelMapProperties> relattributemetas = CollectionUtils.copyBean(properties, EntRelMapProperties.class);
				relpropertiesDao.save(relattributemetas);
			}
		} catch (Exception e) {
			log.debug(map.getId()+"id企业地图发布失败！");
			throw new EntUserMapException(map.getId()+"id企业地图发布失败！");
		}
	}
}
