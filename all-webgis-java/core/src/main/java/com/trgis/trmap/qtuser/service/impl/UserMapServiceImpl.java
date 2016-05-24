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
import com.trgis.trmap.qtuser.dao.UserMapDao;
import com.trgis.trmap.qtuser.exception.UserMapException;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.EncrUtil;
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.userauth.model.User;

/**
 * @author doris
 * @date 2015-09-11
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserMapServiceImpl implements UserMapService{
	
	private static final Log log = LogFactory.getLog(UserMapServiceImpl.class);

	@Autowired
	private UserMapDao userMapDao;
	@Autowired
	private MapGraphicsDao mapGraphicsDao;
	@Autowired
	private AttachfileDao attachfileDao;
	@Override
	public void createUserMap(UserMap userMap) {
		try {
			if (BeanUtil.isEmpty(userMap)) {
				throw new UserMapException("个人地图对象不能为空!");
			}
			userMapDao.save(userMap);
			log.debug("个人地图保存成功！");
		} catch (Exception e) {
			log.debug("个人地图保存失败！");
			throw new UserMapException("个人地图保存失败!");
		}
	}
	
	/**
	 * 分享地图 以及取消分享
	 */
	@Override
	public void shareUserMap(Long id,Integer isshare) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserMapException("个人地图id不能为空!");
			}
			if (BeanUtil.isEmpty(isshare)) {
				throw new UserMapException("是否分享个人地图的标志不能为空！");
			}
			userMapDao.shareUserMap(isshare, id);
			log.debug("分享或审核个人地图成功！");
		} catch (Exception e) {
			log.debug("分享或审核个人地图失败！");
			throw new UserMapException("分享或审核个人地图失败！");
		}
	}

	@Override
	public void deleteUserMap(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserMapException("个人地图id不能为空!");
			}
			UserMap userMap = userMapDao.findOne(id);
			userMap.setMapdelflag(EnumUtil.DELFLAG.DEL.getValue());//删除状态
			userMap.setIsshare(EnumUtil.SHARE.WFX.getValue());//关闭分享
			userMapDao.save(userMap);
			//同时级联删除地图的矢量及附件
			mapGraphicsDao.delByUsermap(EnumUtil.DELFLAG.DEL.getValue(),userMap);
			List<MapGraphics> mglist = mapGraphicsDao.findMapGraphicsByUserMap(userMap,EnumUtil.DELFLAG.NOMAL.getValue());
			for (MapGraphics mg : mglist) {
				//清除矢量即可，系统会自动清除未绑定的数据
				attachfileDao.clearAttachfileById(mg);
			}
			log.debug("删除个人地图成功！");
		} catch (Exception e) {
			log.debug("删除个人地图失败！");
			throw new UserMapException("删除个人地图失败！");
		}
	}

	@Override
	public void editUserMap(UserMap userMap) {
		try {
			if (BeanUtil.isEmpty(userMap)) {
				throw new UserMapException("个人地图对象不能为空!");
			}
			userMapDao.saveAndFlush(userMap);
			log.debug("修改个人地图成功！");
		} catch (Exception e) {
			log.debug("修改个人地图失败！");
			throw new UserMapException("修改个人地图失败！");
		}
	}

	@Override
	public List<UserMap> findAllUserMap() {
		try {
			return userMapDao.findAll();
		} catch (Exception e) {
			log.debug("查询个人地图列表失败！");
			throw new UserMapException("查询个人地图列表失败！");
		}
	}

	@Override
	public List<UserMap> findUserMap() {
		try {
			return userMapDao.findUserMapByMapdelflag(0);
		} catch (Exception e) {
			log.debug("查询个人地图列表失败！");
			throw new UserMapException("查询个人地图列表失败！");
		}
		
	}

	@Override
	public UserMap findUserMapById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserMapException("个人地图id不能为空!");
			}
			return userMapDao.findOne(id);
		} catch (Exception e) {
			log.debug("按id查询个人地图失败！");
			throw new UserMapException("按id查询个人地图失败！");
		}
	}
	
	/**
	 * 分页
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	@Override
	public Page<UserMap> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<UserMap> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = userMapDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userMapDao.findAll(specifications,page);
	}

	@Override
	public void setShareUrl(Long id) {
		// TODO Auto-generated method stub
		UserMap usermap = userMapDao.findOne(id);
		if(BeanUtil.isNotEmpty(usermap)){
			usermap.setShareurl(EncrUtil.encoderByDES(id+"", EncrUtil.URLKEY));
		}
		userMapDao.saveAndFlush(usermap);
	}

	@Override
	public UserMap findUserAppMap(User user, Boolean app) {
		return userMapDao.findUserMapByUserAndApp(user, app);
	}
	
}
