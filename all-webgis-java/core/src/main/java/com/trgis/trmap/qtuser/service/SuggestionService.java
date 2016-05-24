package com.trgis.trmap.qtuser.service;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.qtuser.model.Suggestion;

/**
 * 建议意见
 * @author Alice
 *
 * 2015年11月6日
 */
public interface SuggestionService {
	
	/**
	 * 创建地图
	 */
	public void saveSuggestion(Suggestion suggestion);
	/**
	 * 删除地图
	 */
	public void deleteSuggestion(Long id);
	/**
	 * 分页查询 .
	 */
	public Page<Suggestion> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
	
}
