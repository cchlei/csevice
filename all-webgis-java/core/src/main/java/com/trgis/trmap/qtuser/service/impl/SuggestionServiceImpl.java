package com.trgis.trmap.qtuser.service.impl;


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
import com.trgis.trmap.qtuser.dao.SuggestionDao;
import com.trgis.trmap.qtuser.exception.UserMapException;
import com.trgis.trmap.qtuser.model.Suggestion;
import com.trgis.trmap.qtuser.service.SuggestionService;
import com.trgis.trmap.qtuser.utils.BeanUtil;

/**
 * 意见建议
 * @author Alice
 *
 * 2015年11月6日
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SuggestionServiceImpl implements SuggestionService{
	
	private static final Log log = LogFactory.getLog(SuggestionServiceImpl.class);

	@Autowired
	private SuggestionDao suggestionDao;
	
	/**
	 * 分页
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 */
	@Override
	public Page<Suggestion> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<Suggestion> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = suggestionDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return suggestionDao.findAll(specifications,page);
	}

	/**
	 * 保存
	 */
	@Override
	public void saveSuggestion(Suggestion suggestion) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(suggestion)) {
				throw new UserMapException("意见建议不能为空!");
			}
			suggestionDao.save(suggestion);
			log.debug("意见建议保存成功！");
		} catch (Exception e) {
			log.debug("意见建议保存失败！");
			throw new UserMapException("意见建议保存失败!");
		}
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteSuggestion(Long id) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserMapException("意见建议不能为空!");
			}
			Suggestion suggestion = suggestionDao.findOne(id);
			if(BeanUtil.isNotEmpty(suggestion)){
				suggestionDao.delete(id);
				log.debug("删除意见建议成功！");
			}else{
				log.debug("没有此意见建议！id:"+id);
			}
		} catch (Exception e) {
			log.debug("意见建议删除失败！");
			throw new UserMapException("意见建议删除失败!");
		}
	}
	
}
