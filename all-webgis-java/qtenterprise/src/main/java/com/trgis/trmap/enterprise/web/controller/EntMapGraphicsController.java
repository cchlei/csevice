package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.vo.PropertyVo;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;

/**
 * 企业矢量要素控制器
 * 
 * @author doris
 * @date 2015-12-03
 */
@Controller
@RequestMapping("/entgraphics")
public class EntMapGraphicsController {
	
	private static final Logger logger = LoggerFactory.getLogger(EntAccountController.class);
	
	@Autowired
	private EntMapGraphicsService entMapGraphicsService;
	@Autowired
	private EntMapService entMapService;
	@Autowired
	private EntMapPropertiesService entMapPropertiesService;
	@Autowired
	private EntAttributemetaService epAttributemetaService;
	@Autowired
	private EntLayermetaService epLayermetaService;

	// 编辑矢量信息
	@ResponseBody
	@RequestMapping(value = "/editentgraphics", method ={RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> editEntMapGraphics(@RequestParam Map<String, Object> entmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long mapid = Long.valueOf(entmap.get("mapid").toString());
			EntUserMap entMap = entMapService.findUserMapById(mapid);
			EntLayermeta entLayermeta = epLayermetaService.findByEntUserMap(entMap);
			List<EntAttributemeta> entAttmetaList = epAttributemetaService.findByEntLayermeta(entLayermeta);
			if (BeanUtil.isEmpty(entmap.get("fid"))) {
				EntMapGraphics ent = new EntMapGraphics();
				if (BeanUtil.isNotEmpty(entmap.get("gname"))) {
					ent.setGname(entmap.get("gname").toString());
				}
				if (BeanUtil.isNotEmpty(entmap.get("gtype"))) {
					ent.setGtype(entmap.get("gtype").toString());
				}
				if (BeanUtil.isNotEmpty(entmap.get("geom"))) {
					ent.setGeom(entmap.get("geom").toString());
				}
				EntUserMap eMap = entMapService.findUserMapById(mapid);
				ent.setUserMap(eMap);
				ent.setDelflag(EnumUtil.DELFLAG.NOMAL.getValue());
				ent.setGcreatedate(new Date());
				entMapGraphicsService.createEntMapGraphics(ent);
				saveEntMapProperties(ent.getId(), mapid, entmap,entAttmetaList);
				map.put("result", "success");
				map.put("id", ent.getId());
				map.put("msg", "保存矢量成功！");
			} else {
				Long fid = Long.valueOf(entmap.get("fid").toString());
				EntMapGraphics ent = entMapGraphicsService.findById(fid);
				if (BeanUtil.isNotEmpty(entmap.get("gname"))) {
					ent.setGname(entmap.get("gname").toString());
				}
				if (BeanUtil.isNotEmpty(entmap.get("geom"))) {
					ent.setGeom(entmap.get("geom").toString());
				}
				entMapGraphicsService.editEntMapGraphics(ent);
				editEntMapProperties(fid,mapid,entmap,entAttmetaList);
				map.put("result", "success");
				map.put("id", ent.getId());
				map.put("msg", "修改矢量成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "编辑矢量失败！");
		}
		return map;
	}

	/**
	 * 保存扩展属性值
	 * 
	 * @param featureId
	 * @param mapid
	 * @param entmap
	 * @param entAttmetaList 
	 */
	private void saveEntMapProperties(Long featureId, Long mapid, Map<String, Object> entmap,
			List<EntAttributemeta> entAttmetaList) {
		EntMapProperties ma = new EntMapProperties();
		Map<String, String> m = new HashMap<String, String>();
		for (Iterator<EntAttributemeta> iterator = entAttmetaList.iterator(); iterator.hasNext();) {
			EntAttributemeta entAttributemeta = (EntAttributemeta) iterator.next();
			checkEditEntProperties(m, entAttributemeta, entmap);
		}
		ma.getStores().putAll(m);
		ma.setFeatureId(featureId);
		entMapPropertiesService.save(ma);
	}

	/**
	 * 修改扩展属性
	 * @param featureId
	 * @param mapid
	 * @param entmap
	 * @param entAttmetaList 
	 */
	private void editEntMapProperties(Long featureId, Long mapid, Map<String, Object> entmap,
			List<EntAttributemeta> entAttmetaList) {
		EntMapProperties ma =entMapPropertiesService.findByFeature(featureId);
		Map<String, String> m = ma.getStores();
		for (Iterator<EntAttributemeta> iterator = entAttmetaList.iterator(); iterator.hasNext();) {
			EntAttributemeta entAttributemeta = (EntAttributemeta) iterator.next();
			checkEditEntProperties(m,entAttributemeta,entmap);
		}
		entMapPropertiesService.edit(ma);
	}
	/**
	 * 确认扩展属性字段并插入确认的值
	 * 
	 * @param m
	 * @param entAttributemeta
	 * @param entmap
	 */
	private void checkEditEntProperties(Map<String, String> m, EntAttributemeta entAttributemeta,
			Map<String, Object> entmap) {
		Iterator<String> it = entmap.keySet().iterator();
		while (it.hasNext()) {
			String key;
			String value;
			key = it.next().toString();
			boolean flag=BeanUtil.isNumber(key);
			if (flag) {
				long id = entAttributemeta.getAttrid();
				if (id == Long.valueOf(key)) {
					if (BeanUtil.isNotEmpty(entmap.get(key))) {
						value = entmap.get(key).toString();
						m.put(entAttributemeta.getAttrid().toString(), value);
					} else {
						m.put(key, "");
					}
					break;
				}
			} else {
				continue;
			}
		}

	}
	/**
	 * 根据矢量id获取矢量信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getgraphicsbyid", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> getEntMapGraphicsById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (BeanUtil.isEmpty(id)) {
			map.put("result", "error");
			map.put("msg", "矢量id不能为空！");
			return map;
		}
		try {
			EntMapGraphics ent = entMapGraphicsService.findById(id);
			map.put("entgraphics", ent);
			return map;
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "获取矢量信息失败！");
			return map;
		}
	}

	// 获取矢量列表
	@ResponseBody
	@RequestMapping(value = "/getgraphics", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> getEntMapGraphics(Long mapid, Long fid) {
		Map<String, Object> map = new HashMap<>();
		EntUserMap entMap = entMapService.findUserMapById(mapid);
		EntLayermeta entLayermeta = epLayermetaService.findByEntUserMap(entMap);
		List<EntAttributemeta> entAttmetaList = epAttributemetaService.findByEntLayermeta(entLayermeta);
		if(BeanUtil.isNotEmpty(fid)){
			EntMapGraphics ent = entMapGraphicsService.findById(fid);
			map.put("gname", ent.getGname());
			map.put("fid", fid);
		}else{
			map.put("gname", "");
		}
		if (BeanUtil.isNotEmpty(fid)) {
			//这个地方根据 fid 获得EntMapProperties如果是空 的话 得要先进行保存EntUserMap实体类等相关元素到EntMapProperties ，然后才能获得 EntMapProperties这个对象
			EntMapProperties entProperties = entMapPropertiesService.findByFeature(fid);
			for (Iterator<EntAttributemeta> iterator = entAttmetaList.iterator(); iterator.hasNext();) {
				EntAttributemeta entAttributemeta = (EntAttributemeta) iterator.next();
				checkEntProperties(entProperties, entAttributemeta, map);
			}
		}
		map.put("result", "success");
		return map;
	}

	/**
	 * 校验扩展属性中是否有匹配值
	 * 
	 * @param entProperties
	 * @param entProperties
	 * @param entAttributemeta
	 */
	private void checkEntProperties(EntMapProperties entProperties, EntAttributemeta entAttributemeta,
			Map<String, Object> gmap) {
		// 查询矢量扩展列表
		Map<String, String> stores = entProperties.getStores();
		Iterator<String> it = stores.keySet().iterator();
		while (it.hasNext()) {
			String key;
			String value;
			key = it.next().toString();
			boolean flag=BeanUtil.isNumber(key);
			if (flag) {
				value = stores.get(key);
				long id=entAttributemeta.getAttrid();
				if (id==Long.valueOf(key)) {
					gmap.put(entAttributemeta.getAttrid().toString(), value);
					break;
				}
			}else{
				continue;
			}
		
		}

	}
	 /**
	  * @author  chenhl（你写的什么gui）...路过学习
	  *   
	  *   1,查询图层 中包含的所有矢量(
	  *   		矢量：矢量（英语：vector，也称作矢量）是数学、物理学和工程科学等多个自然科学中的基本概念，
	  *   			指一个同时具有大小和方向（比如：东、南、西、北）的几何对象，因常常以箭头符号标示以区别于其它量而得名。
	  *   			我们这里面的矢量其实就是 点 线 面上有很多属性组成的标记  这个标记上的属性就是我们项目中（自定义的）矢量。)
	  *   2,一个标记（点线面）上可以自定义矢量（个人理解标记上的属性），然后矢量（标记上的属性）有对应的值  
	  *   //根据 图层mapid获取矢量信息 列表的数据
	  *   
	  *   同时进行 分页
	  * @param map_id
	  * @time
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value="/getgraphicsbymapid",method = {RequestMethod.POST, RequestMethod.GET})
	public Map<String, Object>  getEntMapGraphicsByMapId(Integer rows,Integer page,Long mapid,String keyword,String geom,String sort,String direction){
		logger.debug("范围查询开始");
		Map<String,Object> result = new HashMap<String,Object>();//返回结果
		EntUserMap userMap = entMapService.findUserMapById(mapid);//获得某个图层
		if(BeanUtil.isEmpty(userMap)){
			result.put("result", "error");
			result.put("msg", "未查找到地图服务");
			return result;
		}
		int pageNo = page == null ? 0 : page;
		int pageSize = rows == null ? 20 : rows;
		List<EntMapGraphics> features = new ArrayList<EntMapGraphics>(); 
		if(StringUtils.isNotBlank(geom)) {//范围查询
			List<EntMapGraphics> listEntMapGraphics = entMapGraphicsService.findByEntUserMap(keyword,geom, mapid, pageSize, pageNo);
			if(BeanUtil.isNotEmpty(listEntMapGraphics)){
				result.put("totalCount", entMapGraphicsService.findCountByMap(keyword, geom, mapid));
			}else{
				result.put("totalCount", 0);
			}
			features = listEntMapGraphics;
		} else {//基本查询
			ConditionGroup group = new ConditionGroup();
			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			group.setSearchRelation(SearchRelation.AND);
			if(BeanUtil.isNotEmpty(keyword)){
				conditions.add(new SearchCondition("gname", Operator.LIKE, keyword));
			}
			conditions.add(new SearchCondition("userMap.id", Operator.EQ, userMap.getId()));
			group.setConditions(conditions);
			OrderBy order = new OrderBy("id", "desc");
			Page<EntMapGraphics> pages = entMapGraphicsService.findByEntUserMapConditions(group, pageNo, pageSize, order);
			if(pages == null){
				result.put("totalCount", 0);
			}else{
				result.put("totalCount", pages.getTotalElements());
				features = pages.getContent();
			}
		}
		//查询扩展属性 根据查询扩展属性的顺序把矢量属性相对应的值 对应 	
		EntLayermeta entLayermeta = epLayermetaService.findByEntUserMap(userMap);
		List<EntAttributemeta> entAttributemetaList = epAttributemetaService.findByEntLayermeta(entLayermeta);//根据对象获得图层对应的扩展属性结构
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (BeanUtil.isNotEmpty(features)) {
			for(EntMapGraphics entMapGraphics:features){//遍历矢量 
				Map<String, Object> data = new HashMap<String, Object>();
				Long featureId = entMapGraphics.getId();//遍历矢量 获得矢量对应得id
				data.put("fid", featureId);
				 //"attrs":[ "Airi", "Satou", "Accountant", "Tokyo", "2008\/11\/28", 162700]
				 List<String> row = new ArrayList<String>();
				 row.add(entMapGraphics.getId().toString());
				 row.add(entMapGraphics.getGname());
				 row.add(DateUtil.dateToString(entMapGraphics.getGcreatedate(), "yyyy-MM-dd"));
				 ArrayList<PropertyVo> entproperties = new ArrayList<>();
				 EntMapProperties pro = entMapPropertiesService.findByFeature(featureId);
				 if(BeanUtil.isNotEmpty(pro)){ //有扩展属性值
					 for (String key : pro.getStores().keySet()) {
						PropertyVo provo = new PropertyVo();
						provo.setAttrkey(key);//扩展属性的id
						provo.setAttrvalue(pro.getStores().get(key));
						entproperties.add(provo);
					 }
				 }
				 for(EntAttributemeta entAttributemeta : entAttributemetaList){ //遍历扩展属性 集合对象
					 String attrid = entAttributemeta.getAttrid().toString();
					 boolean hasvalue = false;
					 for (PropertyVo propertyVo : entproperties) {
						 String attrkey = propertyVo.getAttrkey();
						 if(attrkey.equals(attrid)){
							 row.add(propertyVo.getAttrvalue());
							 hasvalue = true;
							 break;
						 }
					}
					if(!hasvalue){
						row.add("");
					}
				 }
				 data.put("attrs", row);
				 list.add(data);
			}
		}
		result.put("data", list);
		return result;		
	}

	
	/**
	  * 删除矢量扩展 属性对应得值
	  * 
	  * @param 
	  */
	 @ResponseBody
	 @RequestMapping(value = "/deletebyid", method = {RequestMethod.POST })
	 public Map<String, Object> deleteGraphics(@RequestParam("fid") String fid) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(BeanUtil.isNotEmpty(fid)){
		 try {
			 Long id = Long.valueOf(fid);
			 entMapGraphicsService.delete(id);
//			// 删除矢量扩展属性对应得值
//			 entMapPropertiesService.deleteByFeature(id);
			 map.put("result", "success");
			 map.put("msg", "删除矢量成功！");
			 } catch (Exception e) {
				 e.printStackTrace();
				 map.put("result", "error");
				 map.put("msg", "删除矢量失败！");
				 }
		 }else{
			 map.put("result", "error");
			 map.put("msg", "删除矢量失败！");
		 }
		 return map;
		 }
}
