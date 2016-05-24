/**
 * 
 */
package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.gis.format.GeoJSON;
import com.trgis.common.gis.format.GeoJSONFactory;
import com.trgis.common.gis.geometry.Feature;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.CollectionUtils;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.web.interceptor.annotation.UserCainfoRequired;
import com.trgis.trmap.enterprise.web.util.Constants;
import com.trgis.trmap.enterprise.web.vo.FeatureVO;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 企业地图服务
 * 
 * @author zhangqian
 *
 */
@Controller
@RequestMapping("/entmap")
public class EntMapController {

	@Autowired
	private EntMapService entMapService;
	@Autowired
	private EntRelMapService entRelMapService;
	@Autowired
	private EnterpriseUserService entUserService;
	@Autowired
	private EntLayermetaService epLayermetaService;
	@Autowired
	private EntMapGraphicsService entMapGraphicsService;
	@Autowired
	private EntCainfoService cainfoService;
	@RequestMapping("/init")
	public ModelAndView initMap() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/map_creater");
		return mv;
	}
	/**
	 * 跳转企业地图列表页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tomaplist")
	public ModelAndView toMapList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("map/map_make");
		return modelAndView;
	}
	/**
	 * 企业地图分页列表
	 * @author doris
	 * @date 2015-12-16
	 * @param rows
	 * @param page
	 * @param search
	 * @param fbtime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getentmaplist",method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getEntMapList(Integer rows, Integer page,String search,String fbtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);

		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 关键字
		if (BeanUtil.isNotEmpty(search)) {
			search = search == null ? "" : BeanUtil.HtmltoText(search).replaceAll("\"", "").replace("'", "");
			conditions.add(new SearchCondition("mapname", Operator.LIKE, search));
		}
		int pageNo = page == null ? 0 : page;
		int pageSize = rows == null ? 24 : rows;
		Date end = new Date();
		if (BeanUtil.isNotEmpty(fbtime)) {
			if(!fbtime.equals("创建时间")){
				if(Integer.valueOf(fbtime)!=-1){
					Date begin = getBegin(Integer.valueOf(fbtime));
					conditions.add(new SearchCondition("umcreatetime", Operator.BETWEEN, begin, end));
					}
			}
		}
		// 用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		conditions.add(new SearchCondition("user.id", Operator.EQ, user.getId()));
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		// 排序
		OrderBy order = new OrderBy("umcreatetime", "desc");
		Page<EntUserMap> pa = entMapService.findByConditions(group, pageNo, pageSize, order);
		if (BeanUtil.isNotEmpty(pa)) {
			List<EntUserMap> list = pa.getContent();
			map.put("rows", list);
			map.put("totalCount",pa.getTotalElements());
		}else{
			map.put("rows",new ArrayList<>());
			map.put("totalCount",0);
		}
		
		return map;
	}
	/**
	 * 获取时间
	 * 
	 * @param fbtime
	 * @return
	 */
	private Date getBegin(Integer fbtime) {
		Calendar c = Calendar.getInstance();
		if (fbtime % 12 == 0) {
			c.add(Calendar.YEAR, -(fbtime / 12));
		} else if (Calendar.MONTH < fbtime) {
			c.add(Calendar.MONTH, -fbtime);
		} else {
			c.add(Calendar.YEAR, -1);
			c.add(12 + Calendar.MONTH, fbtime);
		}
		return c.getTime();
	}
	/**
	 * 增加企业地图
	 * @author chenhl
	 * @date 2015-12-17
	 * @param rows
	 * @param page
	 * @param method={RequestMethod.POST,RequestMethod.GET}
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "/addEntUserMap", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addEntUserMap(String mapname ,String mapdesc) {
		ModelAndView modelAndView=null;
		
		EntUserMap enterpriseMap = new EntUserMap();
		//获取当前企业用户
		 String username = (String) SecurityUtils.getSubject().getPrincipal();
		 User user = entUserService.findUserByUsername(username);
		 if(BeanUtil.isNotEmpty(mapname)){//!mapname.equals("")&&mapname!=null
			 enterpriseMap.setUser(user);
			 enterpriseMap.setMapname(mapname);
			 enterpriseMap.setIsshare(0);
			 enterpriseMap.setMapdesc(mapdesc);
			 entMapService.createEnterpriseMap(enterpriseMap);
			 // 同时保存layermeta 把 EntUserMap 中的ID取出保存进来
			 EntLayermeta entLayermeta = new EntLayermeta();
			 entLayermeta.setMap(enterpriseMap);

			 epLayermetaService.createEnterpriseLayermeta(entLayermeta);
			 modelAndView=new ModelAndView();
			 modelAndView.setViewName("redirect:/entmap/toEditEntMap/"+enterpriseMap.getId());
			 modelAndView.addObject("enterpriseMap", enterpriseMap);
		}
		return modelAndView;
	}
	/**
	 * 编辑企业地图
	 * @author chenhl
	 * @date 2015-12-17
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toEditEntMap/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toEditEntMap(@PathVariable("id") Long id) {
		EntUserMap enterpriseMap = entMapService.findUserMapById(id);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("map/map_creater");
		modelAndView.addObject("enterpriseMap", enterpriseMap);
		return modelAndView;
	}

	/**
	 * @author chenhl 删除整个图层 进行级联删除 EntUserMap(图层) EntLayermeta（中间表）EntAttributemeta同时 删除 这三张表的数据
	 *         
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteEntMap", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> deleteMapEnterprise(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 通过前台传来的id 获得其对应的对象
		EntUserMap userMap = entMapService.findUserMapById(id);
		if (BeanUtil.isNotEmpty(userMap)) {
			if(userMap.getIsshare() == EnumUtil.SHARE.WFB.getValue()){ //直接删除
				entMapService.deleteEntUserMap(id);
				map.put("result", "success");
			}else{//已发布，需要为下线状态才能删除
				EntRelUserMap relmap = entRelMapService.findRelUserMapById(id);
				if(relmap.getIsonline() == EnumUtil.ONLINE.XX.getValue()){
					entRelMapService.deleteEntRelUserMap(id);
					map.put("result", "success");
				}else{//已发布
					map.put("result", "YFB");//前端判断的是YFB，OMG表示不理解
				}
			}
		}else {
			map.put("result", "error");
		}
		return map;
	}

	/**
	 * @author chenhl 根据图层ID 编辑 地图名称
	 */
	@ResponseBody
	@RequestMapping(value = "/editEntUserMap", method = { RequestMethod.POST })
	public Map<String, Object> editEntUserMap(Long mapid, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (BeanUtil.isNotEmpty(mapid)) {
			EntUserMap entUserMap = entMapService.findUserMapById(mapid);
			entUserMap.setMapname(name);// 地图名称
			entMapService.editUserMap(entUserMap);
			map.put("result", "success");
			map.put("msg", "修改图层名称成功！");
		} else {
			map.put("result", "error");
			map.put("msg", "修改图层名称失败！");
		}
		return map;
	}

	/**
	 * 判断当前用户是否可发布
	 * @author Alice
	 * @param featureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/couldRelease")
	public Map<String, Object> couldRelease(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		//未认证用户不得申请服务
		EntCainfo sqf = cainfoService.findByUser(user);
		if(BeanUtil.isNotEmpty(sqf) && sqf.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue())){
			map.put("result", "success");
		}else{
			map.put("result", "error");
			map.put("msg", "未认证用户不得发布服务");
		}
		return map;
	}
	
	/**
	 * 跳转发布页面
	 * 
	 * @author Alice
	 * @param featureId
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/toRelease/{emapid}")
	public ModelAndView toRelease(@PathVariable("emapid") Long emapid,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		EntUserMap entmap;
		try {
			entmap = entMapService.findUserMapById(emapid);
			if (entmap.getIsshare() == EnumUtil.SHARE.YFB.getValue()) {
				EntRelUserMap entrelmap = entRelMapService.findRelUserMapByMapId(emapid);
				String path = request.getContextPath();
		        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		        entrelmap.setShareurl(basePath+Constants.MAPSHARE+entrelmap.getShareurl());
				modelAndView.setViewName("map/metadata_update");
				modelAndView.addObject("entrelmap", entrelmap);
			} else {
				modelAndView.setViewName("map/metadata_release");
			}
			modelAndView.addObject("entmap", entmap);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("404");
		}
		return modelAndView;
	}

	/**
	 * 保存元数据修改
	 * 
	 * @author Alice
	 * @param emapid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMap", method = RequestMethod.POST)
	public Map<String, Object> updateMap(EntUserMap entmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entMapService.createEnterpriseMap(entmap);
			map.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "编辑地图元数据失败！");
		}
		return map;
	}

	/**
	 * 发布地图
	 * 
	 * @author Alice
	 * @param emapid
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/releaseMap", method = RequestMethod.POST)
	public Map<String, Object> releaseMap(EntUserMap entmap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entMapService.releaseUserMap(entmap);
			map.put("result", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "发布地图元数据失败！");
		}
		return map;
	}

	/**
	 * @Title: getFeatures
	 * @Description: 查询地图所有要素显示在地图上
	 * @author zhangqian
	 * 
	 * @param mapid		地图id
	 * @return			GeoJSON
	 * 
	 * @throws
	 */
	//	@UserMapRequired //因为预览的时候也要查，所以放开验证
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/features/{mapid}")
	public String getFeatures(@PathVariable("mapid") Long mapid) {
		Assert.notNull(mapid, "参数不能为空");
		EntUserMap userMap = entMapService.findUserMapById(mapid);
		GeoJSON featureCollection = new GeoJSON();
		if (userMap != null) {
			List<EntMapGraphics> features = entMapGraphicsService.findByEntUserMap(userMap);
			if (features != null && !features.isEmpty()) {
				List<FeatureVO> featurevos = CollectionUtils.copyBean(features, FeatureVO.class);
				for (FeatureVO feature : featurevos) {
					Feature featureGeometry = GeoJSONFactory.generateGJF(feature.getId(),feature.getGeom(), feature);
					if(featureGeometry == null) {
						continue;
					}
					featureCollection.add(featureGeometry);
				}
				features = null;
				featurevos = null;
				return GeoJSONFactory.convertGeoJSON(featureCollection);
			}
		}
		return GeoJSONFactory.convertGeoJSON(featureCollection);
	}
}
