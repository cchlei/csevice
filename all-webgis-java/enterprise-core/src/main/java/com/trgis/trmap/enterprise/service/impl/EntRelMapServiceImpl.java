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
import com.trgis.trmap.enterprise.dao.EntRelUserMapDAO;
import com.trgis.trmap.enterprise.dao.EntUserMapDAO;
import com.trgis.trmap.enterprise.exception.EntRelUserMapException;
import com.trgis.trmap.enterprise.exception.EntUserMapException;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.hstore.dao.EntRelMapPropertiesDao;

/**
 * 企业地图服务(已发布)
 * 
 * @author Alice
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntRelMapServiceImpl implements EntRelMapService {
	
	private static final Log log = LogFactory.getLog(EntMapService.class);
	
	@Autowired
	private EntUserMapDAO entUserMapDAO;
	@Autowired
	private EntRelUserMapDAO entRelUserMapDAO;
	@Autowired
	private EntRelMapGraphicsDAO relMapGraphicsDao;
	@Autowired
	private EntRelMapPropertiesDao relpropertiesDao;
	@Override
	public EntRelUserMap findRelUserMapById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new EntRelUserMapException("企业图层（已发布）id不能为空!");
			}
			return entRelUserMapDAO.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询企业图层（已发布）失败！");
			throw new EntUserMapException("按id查询企业图层（已发布）失败！");
		}
	}
	
	@Override
	public void editRelUserMap(EntRelUserMap entRelUserMap) {
		// TODO Auto-generated method stub
		try {
			entRelUserMapDAO.save(entRelUserMap);
		} catch (Exception e) {
			log.debug("编辑企业图层（已发布）失败！");
			throw new EntUserMapException("编辑企业图层（已发布）失败！");
		}
	}
	
	@Override
	public void deleteEntRelUserMap(Long id) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new EntRelUserMapException("企业图层（已发布）id不能为空!");
			}
			EntUserMap userMap = entUserMapDAO.findOne(id);
			//暂时不做发布图层及扩展属性删除，因为服务删除后，订单还要继续显示，所需字段不能删除
//			EntRelLayermeta entRelLayermeta = relLayermetaDao.findByEntRelUserMap(userMap);
//			//删除发布图层及发布扩展属性
//			if(entRelLayermeta!=null){
//				EntLayermeta layermeta = layermetaDao.findOne(entRelLayermeta.getLayermetaid());
//				List<EntRelAttributemeta> relAttributemetaList = relAttributemetaDao.findByLayermeta(layermeta);
//				for (EntRelAttributemeta relAttributemeta : relAttributemetaList) {
//					relAttributemetaDao.delete(relAttributemeta);//扩展属性结构
//				}
//				relLayermetaDao.delete(entRelLayermeta);//图层
//			}
			relpropertiesDao.deleteByMapId(userMap.getId());//扩展属性值
			relMapGraphicsDao.deleteByEntRelUserMap(userMap);//矢量
			EntRelUserMap relmap = entRelUserMapDAO.findOne(id);
			relmap.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			relmap.setPassids("");
			relmap.setUntreatedids("");
			entRelUserMapDAO.saveAndFlush(relmap);
		} catch (Exception e) {
			log.debug("按id删除企业图层（已发布）失败！");
			throw new EntUserMapException("按id删除企业图层（已发布）失败！");
		}
	}

	@Override
	public EntRelUserMap findRelUserMapByMapId(Long mapid) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(mapid)) {
				throw new EntRelUserMapException("企业图层（已发布）mapid不能为空!");
			}
			EntUserMap emap = entUserMapDAO.findOne(mapid);
			if (BeanUtil.isEmpty(emap)) {
				throw new EntRelUserMapException("企业图层（已发布）对应的底图未找到!");
			}
			return entRelUserMapDAO.findByEmap(emap);
		} catch (Exception e) {
			log.debug("按mapid查询企业图层（已发布）失败！");
			throw new EntUserMapException("按mapid查询企业图层（已发布）失败！");
		}
	}
	@Override
	public Page<EntRelUserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
			try {
			Specification<EntRelUserMap> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
			entRelUserMapDAO.count(specifications);
			long count = entRelUserMapDAO.count(specifications);
			if(count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			return entRelUserMapDAO.findAll(specifications,page);
			} catch (Exception e) {
				log.debug("按条件查询企业图层（已发布）失败！");
				throw new EntUserMapException("按条件查询企业图层（已发布）失败！");
			}
		}

	@Override
	public List<EntRelUserMap> findNoOutdate(Integer delflag, Integer isonline) {
		return entRelUserMapDAO.findNoOutdate(delflag, isonline);
	}

	@Override
	public Long findcount(Integer delflag,Long user_id) {
		return entRelUserMapDAO.getcount(delflag, user_id);
	}

	
}
