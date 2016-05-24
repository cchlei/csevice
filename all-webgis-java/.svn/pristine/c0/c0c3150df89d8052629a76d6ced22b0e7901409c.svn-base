package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.enterprise.model.EntRelMapGraphics;
import com.trgis.trmap.enterprise.service.EntRelGraphicsService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;

/**
 * 企业矢量要素发布服务控制器
 * 
 * @author doris
 * @date 2015-12-25
 */
@Controller
@RequestMapping("/entrelgraphics")
public class EntRelMapGraphicsController {
	@Autowired
	private EntRelGraphicsService entRelGraphicsService;

	/**
	 * 地图矢量分页列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getlist", method = { RequestMethod.POST, RequestMethod.GET })
	public  Map<String, Object> graphicsList(Integer rows, Integer page,String key,Long mapid) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		// 条件1
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		if (BeanUtil.isNotEmpty(key)) {
			conditions.add(new SearchCondition("gname", Operator.LIKE, key));
		}
		if (BeanUtil.isNotEmpty(mapid)) {
			conditions.add(new SearchCondition("userMap.id", Operator.EQ, mapid));
		}
		
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("gcreatedate", "desc");
		Page<EntRelMapGraphics> pa = entRelGraphicsService.findByConditions(group, page,rows,order);
		if (BeanUtil.isNotEmpty(pa)) {
			map.put("rows", pa.getContent());
			map.put("totalCount",pa.getTotalElements());
		}else{
			map.put("rows",new ArrayList<>());
			map.put("totalCount",0);
		}
		
		return map;
	}
	/**
	 * 获取前10条指定地图的矢量列表
	 * @param mapid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getlimitlist", method = { RequestMethod.POST, RequestMethod.GET })
	public  Map<String, Object> getlimitList(Long mapid) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		// 条件1
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		if (BeanUtil.isNotEmpty(mapid)) {
			conditions.add(new SearchCondition("userMap.id", Operator.EQ, mapid));
		}
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("gcreatedate", "desc");
		//未申请通过订单的只让看10条数据
		Page<EntRelMapGraphics> pa = entRelGraphicsService.findByConditions(group,0,10,order);
		if (BeanUtil.isNotEmpty(pa)) {
			map.put("rows", pa.getContent());
			map.put("totalCount",pa.getTotalElements());
		}else{
			map.put("rows",new ArrayList<>());
			map.put("totalCount",0);
		}
		return map;
	}
}
