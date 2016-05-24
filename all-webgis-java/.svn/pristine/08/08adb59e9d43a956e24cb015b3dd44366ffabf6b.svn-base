package com.trgis.trmap.qtuser.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.ConvertJSON;
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 地图矢量控制器
 * 
 * @author doris
 *
 */
@Controller
@RequestMapping("/graphics")
public class MapGraphicsController {

	@Autowired
	private MapGraphicsService mapGraphicsService;
	@Autowired
	private UserService userService;
	@Autowired
	private AttachfileService attachfileService;
	
	@RequestMapping(value = "/graphicsCount", method = RequestMethod.GET)
	public String graphicsCount() {
		return "statistics/graphicscount";
	}

	/**
	 * 地图矢量分页列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/graphicsList", method = { RequestMethod.POST, RequestMethod.GET })
	public String graphicsList(Long draw, String searchText, String filterName, Integer start, Integer length,
			String column, String dir, String point, String line, String surface) {
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		// 条件1
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		if (BeanUtil.isNotEmpty(searchText)) {
			conditions.add(new SearchCondition("gname", Operator.LIKE, searchText));
		}
		if (BeanUtil.isNotEmpty(point) || BeanUtil.isNotEmpty(line) || BeanUtil.isNotEmpty(surface)) {
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			if (BeanUtil.isNotEmpty(point)) {
				searchFilters.add(new SearchCondition("gtype", Operator.EQ, point));
			}
			if (BeanUtil.isNotEmpty(line)) {
				searchFilters.add(new SearchCondition("gtype", Operator.EQ, line));
			}
			if (BeanUtil.isNotEmpty(surface)) {
				searchFilters.add(new SearchCondition("gtype", Operator.EQ, surface));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			group.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserByUsername(username);
		conditions.add(new SearchCondition("userMap.user.id", Operator.EQ, user.getId()));
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		if (start == null) {
			start = 0;
		}
		if (length == null) {
			length = 10;
		}
		int pageNum = (start / length) + 1;
		OrderBy order = null;
		if (StringUtils.isNotBlank(column)) {
			order = new OrderBy(column, dir);
		}
		Page<MapGraphics> mapGraphic = mapGraphicsService.findByConditions(group, pageNum, length, order);
		try {
			return ConvertJSON.convert2DataTables(draw, mapGraphic);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 删除矢量
	 * 
	 * @param gid
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteGraphics", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> deleteGraphics(@RequestParam("gid") String gid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long id = Long.valueOf(gid);
			MapGraphics mapGraphics = mapGraphicsService.findById(id);
			mapGraphics.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			mapGraphicsService.edit(mapGraphics);
			//删除关联附件
			List<Attachfile> attachfiles = attachfileService.getByGraphicsId(mapGraphics);
			for (Attachfile attachfile : attachfiles) {
				attachfileService.deleteAttachfile(attachfile.getId());
			}
			map.put("result", "success");
			map.put("msg", "删除矢量成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "删除矢量失败！");
		}
		return map;
	}

	/**
	 * 统计当前用户的矢量数据
	 * 
	 * @param gid
	 */
	@ResponseBody
	@RequestMapping(value = "/countGraphics", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> countGraphics() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// TODO 根据用户等级应该有的条数，暂时设置为500条
			Integer totalCount = 100;
			// 已使用条数
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			if(BeanUtil.isEmpty(user)){
				throw new Exception("用户为空");
			}
			Integer usedCount = mapGraphicsService.countGraphics(user);
			map.put("result", "success");
			map.put("totalCount", totalCount);
			map.put("usedCount", usedCount);
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "获取用户矢量数据失败！");
		}
		return map;
	}
}
