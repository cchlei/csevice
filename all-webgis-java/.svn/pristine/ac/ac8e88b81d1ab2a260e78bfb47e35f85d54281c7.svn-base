package com.trgis.trmap.enterprise.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.enterprise.dao.EntApplicationDao;
import com.trgis.trmap.enterprise.exception.EntApplicationException;
import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.userauth.model.User;

/**
 * 服务申请
 * @author Alice
 *
 * 2015年12月28日
 */
@Service
@Transactional
public class EntApplicationServiceImpl implements EntApplicationService {

	@Autowired
	private EntApplicationDao applicationDao;

	@Override
	public void save(EntApplication application) {
		applicationDao.save(application);	
	}
	/**
	 * 当天最大编号
	 */
	@Override
	public String findMaxNumber() {
		List<String> list = applicationDao.findMaxNumber(DateUtil.dateToString(new Date(),"yyyyMMdd"));
		if(BeanUtil.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<EntApplication> findByGetterAndRelUserMap(User getter, EntRelUserMap relUserMap) {
		return applicationDao.findByGetterAndRelUserMap(getter, relUserMap);
	}
	
	@Override
	public Page<EntApplication> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		try {
			Specification<EntApplication> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
			applicationDao.count(specifications);
			long count = applicationDao.count(specifications);
			if(count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			return applicationDao.findAll(specifications,page);
		} catch (Exception e) {
			throw new EntApplicationException("按条件查询申请的地图服务失败！");
		}
	}
	
	@Override
	public void updateExcepByRelUserMap(Integer isapproved, String reason, EntRelUserMap relUserMap, Integer nowapproved) {
		try {
			applicationDao.updateExcepByRelUserMap(isapproved, reason, relUserMap, nowapproved);
		} catch (Exception e) {
			throw new EntApplicationException("修改申请状态失败！");
		}
	}
	@Override
	public EntApplication findById(Long id) {
		return applicationDao.findOne(id);
	}
	@Override
	public void delete(Long id) {
		applicationDao.delete(id);
	}
	@Override
	public List<Long> getIdsByPassmap(EntRelUserMap relUserMap, Integer isapproved) {
		return applicationDao.getIdsByPassmap(relUserMap, isapproved);
	}
	@Override
	public List<EntApplication> getByPassmap(EntRelUserMap relUserMap, Integer isapproved) {
		return applicationDao.getByPassmap(relUserMap, isapproved);
	}
	@Override
	public void updatePassById(Integer isapproved, String reason, Long id) {
		try {
			applicationDao.updatePassById(isapproved, reason, id);
		} catch (Exception e) {
			throw new EntApplicationException("修改申请状态失败！");
		}
	}
	@Override
	public List<EntApplication> findRepaly(User getter, EntRelUserMap relUserMap) {
		return applicationDao.findRepaly(getter, relUserMap);
	}
	@Override
	public EntApplication findByNumber(String number) {
		// TODO Auto-generated method stub
		return applicationDao.findByNumber(number);
	}
	@Override
	public List<EntApplication> findByPass(Integer isapproved) {
		// TODO Auto-generated method stub
		return applicationDao.findByPass(isapproved);
	}
	@Override
	public Long finduntreated(Long userid, Integer isapproved) {
		return applicationDao.getuntreated(userid, isapproved);
	}
	@Override
	public Long findpass(Long userid, Integer isapproved) {
		return applicationDao.getpass(userid, isapproved);
	}
}
