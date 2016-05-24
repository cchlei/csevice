package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.trmap.enterprise.dao.EntMapGraphicsDAO;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.service.EntDataService;
import com.trgis.trmap.enterprise.util.EnumUtil;

/**
 * 蜻蜓企业账号服务
 * 
 * @author zhangqian
 *
 */
@Service
@Transactional
public class EntDataServiceImpl implements EntDataService {

	@Autowired
	private EntMapGraphicsDAO mapGraphicsDAO;

	@Override
	public List<EntMapGraphics> findMapGraphics(Long mapId) {
		ConditionGroup condition = new ConditionGroup();
		SearchCondition mapIdCondition = new SearchCondition("userMap.id", Operator.EQ, mapId);
		SearchCondition deleteCondition = new SearchCondition("delflag", Operator.EQ,
				EnumUtil.DELFLAG.NOMAL.getValue());
		condition.addCondition(mapIdCondition, deleteCondition);
		Specification<EntMapGraphics> specification = DynamicSpecficationUtil.buildSpecfication(condition);
		return mapGraphicsDAO.findAll(specification);
	}

	@Override
	public void importDateToMap(Long mapId, List<EntMapGraphics> graphics) {
		// TODO 导入数据到地图
	}

}
