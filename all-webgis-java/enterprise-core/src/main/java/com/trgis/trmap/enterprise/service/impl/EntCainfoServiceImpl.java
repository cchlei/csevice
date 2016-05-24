package com.trgis.trmap.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.enterprise.dao.EntCainfoDao;
import com.trgis.trmap.enterprise.exception.EntCainfoException;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;

/**
 * 企业认证信息服务实现
 * 
 * @author doris
 * @date 2015-11-27
 */
@Service
@Transactional
public class EntCainfoServiceImpl implements EntCainfoService {

	private static final Logger logger = LoggerFactory.getLogger(EntCainfoService.class);

	@Autowired
	private EntCainfoDao entCainfoDao;

	@Autowired
	private UserDAO userDao;

	@Override
	public void save(EntCainfo cainfo) {
		try {
			if (BeanUtil.isEmpty(cainfo)) {
				throw new EntCainfoException("ent cainfo can't be null");
			}
			entCainfoDao.save(cainfo);
			logger.debug("cainfo save success.");
		} catch (Exception e) {
			logger.error("enterprise cainfo save error:"+e.getMessage(),e);
			throw new EntCainfoException("enterprise cainfo save error:"+e.getMessage(),e);
		}
	}

	@Override
	public void edit(EntCainfo cainfo) {
		try {
			if (BeanUtil.isEmpty(cainfo)) {
				throw new EntCainfoException("ent cainfo can't be null");
			}
			entCainfoDao.saveAndFlush(cainfo);
			logger.debug("cainfo edit success.");
		} catch (Exception e) {
			logger.error("enterprise cainfo edit error:"+e.getMessage(),e);
			throw new EntCainfoException("enterprise cainfo edit error:"+e.getMessage(),e);
		}
	}

	@Override
	public void delete(Long id) {
		try {
			EntCainfo cainfo = entCainfoDao.findOne(id);
			if (BeanUtil.isEmpty(cainfo)) {
				throw new EntCainfoException("can't find ent cainfo.");
			}
			entCainfoDao.delete(cainfo);
			logger.debug("cainfo delete success.");
		} catch (Exception e) {
			logger.error("enterprise cainfo delete error:"+e.getMessage(),e);
			throw new EntCainfoException("enterprise cainfo delete error:"+e.getMessage(),e);

		}
	}

	@Override
	public List<EntCainfo> findAll() {
		try {
			return entCainfoDao.findAll();
		} catch (Exception e) {
			logger.error("enterprise cainfo query error:"+e.getMessage(),e);
			throw new EntCainfoException("enterprise cainfo query error:"+e.getMessage(),e);
		}
	}

	@Override
	public EntCainfo findById(Long id) {
		return entCainfoDao.findOne(id);
	}

	@Override
	public EntCainfo findByUser(User user) {
		logger.debug("findByUser "+ user.getId());
		return entCainfoDao.findByUser(user);
	}

	@Override
	public Page<EntCainfo> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<EntCainfo> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = entCainfoDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return entCainfoDao.findAll(specifications, page);
	}
	
	/**
	 * 撤销企业认证信息
	 * @param id
	 */
	@Override
	public void revocationApply(Long id) {
		entCainfoDao.editCastatus(EnumUtil.CASTATUS.CANCELED.getValue(), id);
	}

	@Override
	public EntCainfo findByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		User user = userDao.findByUsername(username);
		if (user == null) {
			return null;
		}
		return entCainfoDao.findByUser(user);
	}

	@Override
	public EntCainfo findByEnterpriseName(String enterpriseName) {
		if(StringUtils.isBlank(enterpriseName)) {
			return null;
		}
		EntCainfo entCainfo = entCainfoDao.findByEnterpriseName(enterpriseName);
		if(entCainfo == null) {
			return null;
		}
		return entCainfo;
	}

	@Override
	public EntCainfo findByUserAndCastatus(User user, String castatus) {
		
		if(BeanUtil.isNotEmpty(user) && BeanUtil.isNotEmpty(castatus)) {
			return entCainfoDao.findByUserAndCastatus(user, castatus);
		}
		return null;
	}

	@Override
	public List<EntCainfo> findByCastatus(String castatus) {
		List<EntCainfo> lsca = new ArrayList<>();
		if(BeanUtil.isNotEmpty(castatus)) {
			lsca = entCainfoDao.findByCastatus(castatus);
			return lsca;
		}
		return null;
	}

}
