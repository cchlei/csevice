package com.trgis.trmap.qtuser.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.qtuser.dao.AttachfileDao;
import com.trgis.trmap.qtuser.dao.MapGraphicsDao;
import com.trgis.trmap.qtuser.exception.MapGraphicsException;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 
 * @author doris
 * @date 2015-09-11
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MapGraphicsServiceImpl implements MapGraphicsService{
	private static final Log log = LogFactory.getLog(MapGraphicsServiceImpl.class);
    @Autowired
    private MapGraphicsDao mapGraphicsDao;
    @Autowired
    private AttachfileDao attachfileDao;
	@Override
	public void save(MapGraphics graphics) {
		try {
			if (BeanUtil.isEmpty(graphics)) {
				throw new MapGraphicsException("地图矢量对象不能为空！");
			}
			mapGraphicsDao.save(graphics);
			log.debug("地图矢量保存成功！");
		} catch (Exception e) {
			log.debug("地图矢量保存失败！");
			throw new MapGraphicsException("地图矢量保存失败！");
		}
	
	}

	@Override
	public void edit(MapGraphics graphics) {
		try {
			if (BeanUtil.isEmpty(graphics)) {
				throw new MapGraphicsException("地图矢量对象不能为空！");
			}
			mapGraphicsDao.saveAndFlush(graphics);
			//删除需将矢量级联附件一并删除
			if(graphics.getDelflag() == EnumUtil.DELFLAG.DEL.getValue()){
				attachfileDao.clearAttachfileById(graphics);
			}
			log.debug("地图矢量修改成功！");
		} catch (Exception e) {
			log.debug("地图矢量修改失败！");
			throw new MapGraphicsException("地图矢量修改失败！");
		}
	}

	@Override
	public void delete(Long id) {
		try {
			MapGraphics mapGraphics = mapGraphicsDao.findOne(id);
			if (BeanUtil.isEmpty(mapGraphics)) {
				throw new MapGraphicsException("地图矢量未找到！");
			}
			mapGraphics.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			mapGraphicsDao.saveAndFlush(mapGraphics);
			log.debug("地图矢量删除成功！");
			attachfileDao.clearAttachfileById(mapGraphics);
			log.debug("地图矢量相关附件删除成功！");
		} catch (Exception e) {
			log.debug("地图矢量删除失败！");
			throw new MapGraphicsException("地图矢量删除失败！");
			
		}
	}

	@Override
	public List<MapGraphics> findAll() {
		
		try {
			return mapGraphicsDao.findAll();
		} catch (Exception e) {
			log.debug("地图矢量列表查询失败！");
			throw new MapGraphicsException("地图矢量列表查询失败！");
		}
	}

	@Override
	public MapGraphics findById(Long id) {
		try {
			return mapGraphicsDao.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询地图矢量失败！");
			throw new MapGraphicsException("按id查询地图矢量失败！");
		}
	}

	@Override
	public List<MapGraphics> findByUserMap(UserMap userMap) {
		try {
			if (BeanUtil.isEmpty(userMap)) {
				throw new MapGraphicsException("个人用户地图对象不能为空！");
			}
			return mapGraphicsDao.findMapGraphicsByUserMap(userMap,EnumUtil.DELFLAG.NOMAL.getValue());
		} catch (Exception e) {
			log.debug("按个人地图查询地图矢量失败！");
			throw new MapGraphicsException("按个人地图查询地图矢量失败！");
		}
	}

	@Override
	public Page<MapGraphics> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy orderby) {
		Specification<MapGraphics> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = mapGraphicsDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, orderby);
		return mapGraphicsDao.findAll(specifications,page);
	}

	@Override
	public Integer countGraphics(User user) {
		return mapGraphicsDao.countGraphics(user,EnumUtil.DELFLAG.NOMAL.getValue());
	}

}
