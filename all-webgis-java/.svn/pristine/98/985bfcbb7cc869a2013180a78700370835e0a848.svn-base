package com.trgis.trmap.qtuser.web.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.BaseMap;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.service.BaseMapService;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.qtuser.utils.StringHandle;
import com.trgis.trmap.qtuser.web.util.Constants;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 个人地图编辑
 * @author doris
 * @date 2015-9-19	
 *
 */
@Controller
@RequestMapping("/mapedit")
public class UserMapController {
	@Autowired
	private UserMapService userMapService;
	@Autowired
	private MapGraphicsService mapGraphicsService;
	@Autowired
	private UserService userService;
	@Autowired
	private AttachfileService attachfileService;
	@Autowired
	private BaseMapService baseMapService;
	
	/**
	 * 保存或修改个人地图数据
	 * 从前台页面获取到一个jsonStr
	 * 包含个人地图描述、基础图层、Feature信息
	 */
	@RequestMapping(value="/editMapInfo",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void editMapInfo(HttpSession session, HttpServletResponse response,HttpServletRequest request, PrintWriter out ){
		String resultStr = "{";
		try {
			//登录用户
			String jsonStr =  request.getParameter("jsonStr");
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			ObjectMapper mapper = new ObjectMapper();  
			JsonNode rootNode = mapper.readTree(jsonStr);
			//获取地图的名称和描述
			UserMap userMap = editMapInfo(rootNode, user);
			//获取地图的底图数据
			JsonNode baseMaps = rootNode.path("layer_status");
			String baseMapStr = editBaseMapInfo(baseMaps , userMap.getId());
			//获取地图上的Feature数据
			JsonNode features = rootNode.path("feature");
			JsonNode del_idlist = rootNode.path("del_idlist");
			JsonNode feature_edited = rootNode.path("feature_edited");
			//循环处理当前个人地图所属的矢量列表
			String featureInfoLst = loopEditFeatureInfo(features ,feature_edited, del_idlist ,userMap, user);
			resultStr = resultStr+"\"mapid\":"+userMap.getId()+","+"\"baseMapids\":"+baseMapStr+","+"\"featureids\":"+featureInfoLst+"}";
			out.print(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("");
		} // 读取Json  
		out.flush();
		out.close();
	}
	/**
	 * 
	 * 获取地图的底图数据
	 * 删除原有的底图数据，插入新的底图数据
	 * @param baseMaps
	 */
	private String editBaseMapInfo(JsonNode baseMaps ,  Long mapid){
		String baseMapLst = "[";
		//根据mapid获取该地图对应的底图数据，并删除
		baseMapService.deleteBaseMapByMapId(mapid);
		//解析获取到要添加的底图数据，并插入数据库
		for (int i = 0; i < baseMaps.size(); i++) {  
			BaseMap bean = new BaseMap();
			JsonNode basemap = baseMaps.get(i);
			String basemapType = basemap.path("name").asText();
			double basemapOpacity = basemap.path("opacity").asDouble();
			//底图的排序，只给一个先后顺序。
			int basemapZIndex = basemap.path("id").asInt();;
			//地图的基础底图入库
			bean.setMapId(mapid);
			bean.setOpacity(basemapOpacity);
			bean.setZ_index(basemapZIndex);
			bean.setBasemapType(basemapType);
			baseMapService.addBaseMap(bean);
			Long id = bean.getId();
			baseMapLst += "{\"id\":"+id+"},"; 
		}
		if(baseMapLst.lastIndexOf(",") ==-1){
			baseMapLst +="]";
		}else{
			baseMapLst = baseMapLst.substring(0, baseMapLst.lastIndexOf(","));
			baseMapLst +="]";
		}
		return baseMapLst;
	}
	/**
	 * 编辑地图数据
	 * @param rootNode
	 * @param userInfo
	 * @return
	 */
	private UserMap editMapInfo(JsonNode rootNode, User user){
		UserMap userMap = new UserMap();
		//获取地图的名称和描述
		JsonNode project = rootNode.path("project");  
		String projectName = project.path("name").asText();
		String projectDesc = project.path("desc").asText();
		String mapid = project.path("mapid").asText();
		if(StringUtils.isNotEmpty(mapid)){//编辑
			userMap = userMapService.findUserMapById(Long.parseLong(mapid));
		}
		userMap.setMapname(projectName);
		userMap.setMapdesc(projectDesc);
		userMap.setUser(user);
		//是否公开，需要传值
		boolean share = project.path("share").asBoolean();
		userMap.setIsshare(share?1:0);
//		userMapService.shareUserMap(Long.parseLong(mapid), share?1:0);
		//暂时为用户可以定制是否分享(以后改成只要编辑后都为不分享，等待管理员审核)
		//userMap.setIsshare(EnumUtil.SHARE.WFX.getValue());
		//地图的基础信息入库
		if(BeanUtil.isNotEmpty(userMap.getId())&&userMap.getId()!=0){//编辑
			//获取地图的底图数据并更新
//			JsonNode baseMaps = rootNode.path("layer_status");
//			JsonNode basemap = baseMaps.get(0);
//			String basemapType = basemap.path("name").asText();
			userMapService.editUserMap(userMap);
		}else{//新增
			userMap.setUmcreatetime(new Date());
			userMap.setMapdelflag(EnumUtil.DELFLAG.NOMAL.getValue());
			userMapService.createUserMap(userMap);
			userMapService.setShareUrl(userMap.getId());
		}
		return userMap;
	}

	/**
	 * 编辑地图上的Feature数据
	 * 解析json
	 * 发现有id，则update
	 * 无id,则insert
	 * 
	 * @param features
	 * @param mapid
	 * @param userInfo
	 */
	private String loopEditFeatureInfo(JsonNode features, JsonNode feature_edited, JsonNode del_idlist,  UserMap userMap, User user){
		//循环添加矢量
		String featureIdLst = "[";
		for (int i = 0; i < features.size(); i++) {  
			MapGraphics dataBean = new MapGraphics();
			JsonNode feature = features.get(i);
			String typeStr = feature.path("type").asText();
			int type = 0;
			if(typeStr == "point" || "point".equals(typeStr)){
				dataBean.setGeom(feature.path("geom").asText());
				type = 1;
			}else if(typeStr == "line" || "line".equals(typeStr)){	
				dataBean.setGeom(feature.path("geom").asText());
				type = 2;
			}else if(typeStr == "poly" || "poly".equals(typeStr)){
				String temp_str = feature.path("geom").asText();
				//如果是3，说明只绘制了2个点。录入数据库时会报错。(构成面的三个点中有两个点重合)
				if(temp_str.split(",").length == 3){
					temp_str = temp_str.toUpperCase().replace("POLYGON(", "LINESTRING");
					temp_str = temp_str.toUpperCase().replace("))",")");
					type = 2;
				}else{
					type = 3;
				}
				dataBean.setGeom(temp_str);
			}
			dataBean.setGtype(type+"");
			editFeatureInfo(feature,dataBean,userMap,user.getId());
			featureIdLst += "{\"id\":"+dataBean.getId()+"},";
		}
		//循环编辑更新的内容
		for (int i = 0; i < feature_edited.size(); i++) {  
			JsonNode feature = feature_edited.get(i);
			MapGraphics dataBean = new MapGraphics();
			dataBean = mapGraphicsService.findById(Long.parseLong(feature.path("id").asText()));
			String typeStr = feature.path("type").asText();
			dataBean.setGeom(feature.path("geom").asText());
			int type = 0;
			if(typeStr == "point" || "point".equals(typeStr)){
				type = 1;
			}else if(typeStr == "line" || "line".equals(typeStr)){
				type = 2;
			}else if(typeStr == "poly" || "poly".equals(typeStr)){
				type = 3;
			}
			dataBean.setGtype(type+"");
			editFeatureInfo(feature,dataBean,userMap,user.getId());
		}
		for(int i=0;i < del_idlist.size();i++){
			long tempid = del_idlist.path(i).asLong();
			mapGraphicsService.delete(tempid);
		}
		
		if(featureIdLst.lastIndexOf(",") ==-1){
			featureIdLst +="]";
		}else{
			featureIdLst = featureIdLst.substring(0, featureIdLst.lastIndexOf(","));
			featureIdLst +="]";
		}
		return featureIdLst;
	}

	/**
	 * 编辑单个地图矢量
	 * @param feature
	 * @param dataBean
	 * @param mapid
	 */
	private void editFeatureInfo(JsonNode feature, MapGraphics dataBean, UserMap userMap,Long userid) {
		Long id = feature.path("id").asLong();
		dataBean.setDelflag(EnumUtil.DELFLAG.NOMAL.getValue());
		dataBean.setGname(StringUtils.isBlank(feature.path("name").asText())?"未命名标注":feature.path("name").asText());
		dataBean.setGstyle(feature.path("desc").asText());
		dataBean.setUserMap(userMap);
		//1.导入；2. 录入。前台添加了导入功能后进行修改。后续改进
		//dataBean.setSource(2);
		dataBean.setRadius(feature.path("scale").asDouble());
		Date coourtime = null;
		try {
			if(StringUtils.isNotBlank(feature.path("occurtime").asText())){
				coourtime = DateUtil.parseDate(feature.path("occurtime").asText(), "yyyy-MM-dd");
			}
			dataBean.setOccurtime(coourtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dataBean.setColor(feature.path("color").asText());
		dataBean.setSymbol(feature.path("symbol").asText());
		dataBean.setStrokeWidth(feature.path("stroke_width").asDouble());
		dataBean.setStrokeColor(feature.path("stroke_color").asText());
		dataBean.setStrokeOpacity(feature.path("stroke_opacity").asDouble());
		dataBean.setFillColor(feature.path("fill_color").asText());
		dataBean.setFillOpacity(feature.path("fill_opacity").asDouble());
		if(id != 0){
			mapGraphicsService.edit(dataBean);
		}else{
			dataBean.setGcreatedate(new Date());
			mapGraphicsService.save(dataBean);
		}
		//先全部清除关系再重新添加
		attachfileService.clearAttachfileById(dataBean);
		String alreadyfiles = feature.path("alreadyfiles").asText();
		//添加附件关联（没有关联的附件要定时删除）
		String fileids = alreadyfiles + feature.path("files").asText();
		if(StringUtils.isNotBlank(fileids)){
			String ids[] = fileids.split(",");
			for (String s : ids) {
				if(StringUtils.isNotBlank(s)){
					String ossid = s;
					if(s.contains(":")){
						ossid = s.split(":")[0];
					}
					Attachfile attachfile = attachfileService.findAttachfileByOssid(ossid);
					if(BeanUtil.isNotEmpty(attachfile)){
						attachfile.setMapGraphics(dataBean);
						attachfileService.saveAttachfile(attachfile);
					}
				}
			}
		}
	}
	/**
	 * 根据mapid获取json(编辑展示页面)
	 * json格式为：jsonStr:{"feature":[{}],"layer_status":[{},{}],"project":{}}
	 * 返回到页面
	 * 
	 * @param session
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/showMapInfo")
	@ResponseBody
	public void showMapInfo(HttpSession session, HttpServletResponse response,HttpServletRequest request , PrintWriter out){
		Map<String,Object> result = new HashMap<String,Object>();
		response.setCharacterEncoding("UTF-8"); 
		try {
			//获取到mapid
			Long  mapid=Long.parseLong(request.getParameter("mapid"));
			//获取个人地图
			UserMap personMap = userMapService.findUserMapById(mapid);
			//0：不分享；1：分享
			boolean share = personMap.getIsshare()==0?false:true;
			Map<String,Object> project = new HashMap<String,Object>();
			project.put("name", personMap.getMapname());
			project.put("desc", personMap.getMapdesc());
			project.put("share", share);
			result.put("project", project);
			
			//获取个人地图对应的底图
			List<BaseMap> layer_status = baseMapService.getBaseMapByMapId(mapid);
			result.put("layer_status", layer_status);
			
			//获取个人地图上的Feature列表
			List<Object> feature = new ArrayList<Object>();
			List<MapGraphics> personFeatureDataLst = mapGraphicsService.findByUserMap(personMap);
			for(int i=0;i < personFeatureDataLst.size();i++){
				MapGraphics bean = personFeatureDataLst.get(i);
				Map<String,Object> baseMap = new HashMap<String,Object>();
				baseMap.put("tmpid", bean.getId());
				baseMap.put("id", bean.getId());
				baseMap.put("name", bean.getGname());
				//发生时间不为空时再去转换，避免空指针异常（by mm）
				if(BeanUtil.isNotEmpty(bean.getOccurtime())){
					baseMap.put("occurtime", DateUtil.dateToString(bean.getOccurtime(), "yyyy-MM-dd") );
				}
				baseMap.put("desc", bean.getGstyle());
				baseMap.put("geom", bean.getGeom());
				if(bean.getGtype().equals("1")){
					baseMap.put("type", "point");
					baseMap.put("scale", bean.getRadius());
					baseMap.put("color", bean.getColor());
					baseMap.put("symbol", bean.getSymbol());
				}else if(bean.getGtype().equals("2")){
					baseMap.put("type", "line");
					baseMap.put("stroke_width", bean.getStrokeWidth());
					baseMap.put("stroke_color", bean.getStrokeColor());
					baseMap.put("stroke_opacity", bean.getStrokeOpacity());
				}else if(bean.getGtype().equals("3")){
					baseMap.put("type", "poly");
					baseMap.put("stroke_width", bean.getStrokeWidth());
					baseMap.put("stroke_color", bean.getStrokeColor());
					baseMap.put("stroke_opacity", bean.getStrokeOpacity());
					baseMap.put("fill_color", bean.getFillColor());
					baseMap.put("fill_opacity", bean.getFillOpacity());
				}
				//附件
				List<Attachfile> filelist = new ArrayList<Attachfile>();
				filelist = attachfileService.getByGraphicsId(bean);
				String files = "";
				for (Attachfile attachfile : filelist) {
					files += ","+attachfile.getOssid()+":"+attachfile.getAttachName();
				}
				if(files.indexOf(",")==0){
					files = files.substring(1, files.length());
				}
				baseMap.put("files", "");//新增加的
				baseMap.put("alreadyfiles", files);//已经上传的
				feature.add(baseMap);
			}
			result.put("feature", feature);
			String jsonStr = JSON.toJSONString(result);
			out.print(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 个人地图分页列表
     * isshare 分享状态 0 为未分享 1已分享
     * @return
     * method={RequestMethod.POST,RequestMethod.GET}
     */
    @ResponseBody
    @RequestMapping(value = "/userMapList/{isshare}", method={RequestMethod.POST})
    public Map<String,Object> userMapList(@PathVariable("isshare") int isshare,HttpServletRequest request,HttpSession session) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	//查询条件
    	ConditionGroup group = new ConditionGroup();
    	group.setSearchRelation(SearchRelation.AND);
    	
    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
    	// 关键字
    	String searchText = request.getParameter("searchText");//搜索
    	if(BeanUtil.isNotEmpty(searchText)){
    		searchText = searchText ==null?"":StringHandle.HtmltoText(searchText).replaceAll("\"", "").replace("'", "");
    		conditions.add(new SearchCondition("mapname", Operator.LIKE, searchText));
    	}
    	String pageNoStr = request.getParameter("page");
    	int pageNo = pageNoStr==null?0:Integer.parseInt(pageNoStr);
    	String rowsStr = request.getParameter("rows");
        int pageSize = pageNoStr==null?24:Integer.parseInt(rowsStr);
        
	    //删除状态
		conditions.add(new SearchCondition("mapdelflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		//分享状态
		if(isshare==0){//未分享（这里包含未分享、审核中及审核退回三种状态）
			conditions.add(new SearchCondition("isshare", Operator.NEQ, EnumUtil.SHARE.YFX.getValue()));
		}else if(isshare==1){//已分享
			conditions.add(new SearchCondition("isshare", Operator.EQ, EnumUtil.SHARE.YFX.getValue()));
		}else {//全部
		}
		//用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserByUsername(username);
	    conditions.add(new SearchCondition("user.id", Operator.EQ, user.getId()));
		group.setConditions(conditions);
		//排序
		OrderBy order = new OrderBy("umcreatetime", "desc");
		String timeindex = request.getParameter("timeindex");//时间排序
		if(BeanUtil.isNotEmpty(timeindex)){
			order = new OrderBy("umcreatetime", timeindex);
		}
		Page<UserMap> page = userMapService.findByConditions(group, pageNo, pageSize, order);//.findByConditions(group, 0, 10, order);
		//构造一下shareurl
        List<UserMap> list = page.getContent();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        for (UserMap userMap : list) {
        	userMap.setShareurl(basePath+Constants.MAPSHARE+userMap.getShareurl());
			//替换默认图片 使用 Identicon生成默认图片
        	userMap.setUmicon(String.format("http://identicon.relucks.org/qingting-%s?size=200", userMap.getId()));//todo 先给个默认图片，以后生成缩略图后替换
		}
        map.put("rows", list);
        map.put("totalCount", page.getTotalElements());
        return map;
    }
	
	/**
	 * 申请分享地图
	 * @param map_id
	 * @param out
	 * @param request
	 */
	@RequestMapping(value="/publicMap",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void publicMap(@RequestParam("mapid")Long mapid,PrintWriter out){
		UserMap usermap = userMapService.findUserMapById(mapid);
		if(BeanUtil.isNotEmpty(usermap)){
			//userMap.setIsshare(EnumUtil.SHARE.SPZ.getValue());//申请分享
			//todo 暂时未不需要审核，以后添加管理员审核
			String quxiao ="";
			if(usermap.getIsshare()==EnumUtil.SHARE.YFX.getValue()){
				userMapService.shareUserMap(mapid, EnumUtil.SHARE.WFX.getValue());
				quxiao  = "取消";
			}else{
				userMapService.shareUserMap(mapid, EnumUtil.SHARE.YFX.getValue());
			}
			out.print("{\"result\":\"success\",\"action\":\""+quxiao+"\"}");
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 取消分享
	 * @param map_id
	 * @param out
	 */
	@RequestMapping(value="/undoPublicMap",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void undoPublicMap(@RequestParam("map_id")Long map_id,PrintWriter out){
		if(BeanUtil.isNotEmpty(map_id)){
			userMapService.shareUserMap(map_id, EnumUtil.SHARE.WFX.getValue());
			out.print("{\"result\":\"success\"}");
		}
	}
	
	/**
	 * 管理员审核
	 * @param mapId
	 * @param isshare 2审核通过 3退回
	 * @param out
	 */
	@ResponseBody
	@RequestMapping("auditMap/{isshare}")
	public void updStatus(@RequestParam("mapId")String mapId,@PathVariable("isshare")Integer isshare,PrintWriter out){
		try {
			if(BeanUtil.isNotEmpty(mapId)){
				Long mapid = Long.parseLong(mapId);
//				UserMap usermap = userMapService.findUserMapById(mapid);
				userMapService.shareUserMap(mapid, isshare);
				out.print("{\"result\":\"success\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除地图
	 * @param session
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping(value="/deleteMap")
	@ResponseBody
	public void deleteMap(@RequestParam("mapid")Long mapid, HttpSession session,HttpServletRequest request , HttpServletResponse response,PrintWriter out) {
		try {
			if(BeanUtil.isNotEmpty(mapid)){
				userMapService.deleteUserMap(mapid);
				out.print("{\"result\":\"success\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"success\"}");
		}
	}
	
	/**
	 * 获取uasermap对应图层
	 * @param mapid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPersonMapBaseMap",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<BaseMap> getPersonMapBaseMap(@RequestParam("mapid")Long mapid,HttpServletRequest request){
		List<BaseMap> list = baseMapService.getBaseMapByMapId(mapid);
		return list;
	}
	/**
	 * 获取usermap对应的矢量
	 * @param mapid
	 * @return
	 */
	@RequestMapping(value="/getPersonMapFeature",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Page<MapGraphics> getPersonMapFeature(@RequestParam("mapid")Long mapid,HttpServletRequest request, HttpSession session){
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("delflag", Operator.EQ,EnumUtil.DELFLAG.NOMAL.getValue()));
		conditions.add(new SearchCondition("userMap.id", Operator.EQ,mapid));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("gcreatedate", "desc");
		Integer pageNo = Integer.parseInt(request.getParameter("page"));
		Integer pageSize = Integer.parseInt(request.getParameter("rows"));
	    Page<MapGraphics> mapGraphic = mapGraphicsService.findByConditions(group,pageNo,pageSize, order);
		return mapGraphic;
	}
	/**
	 * 获取usermap基本信息(显示详情时使用)
	 * @param mapid
	 * @param out
	 */
	@RequestMapping(value="/getMapInfo",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getMapInfo(@RequestParam("mapid")Long mapid,HttpSession session, PrintWriter out){
		UserMap userMap = userMapService.findUserMapById(mapid);
		//创建地图者的个人信息，并非登录者
		User user = userMap.getUser();
		out.print("{\"result\":\"success\",\"mapInfo\":{" +
				"\"mapName\":\""+userMap.getMapname()+"\"," +
				"\"userName\":\""+user.getUsername()+"\"," +
				"\"mapDescribe\":\""+userMap.getMapdesc()+"\"," +
				"\"headimg\":\""+user.getHeadimg()+"\"" +
				"}}");
	}
	//////////////////////////////////以下为跳转
	/**
	 * 分页列表跳转
	 * @param isshare
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userMapList/{isshare}", method = RequestMethod.GET)
	public String redirectUserList(@PathVariable("isshare") int isshare,HttpServletRequest request) {
		request.setAttribute("isshare", isshare);
		return "map/maplist";
	}
    /**
     * 跳转新增页面
     * @return
     * method={RequestMethod.POST,RequestMethod.GET}
     */
    @ResponseBody
    @RequestMapping(value = "/toEdit", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView toEdit(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("mapid", request.getParameter("mapid"));
    	modelAndView.setViewName("map/mapshow");
        return modelAndView;
    }
    /**
     * 跳转未分享提示页面
     * @return
     * method={RequestMethod.POST,RequestMethod.GET}
     */
    @ResponseBody
    @RequestMapping(value = "/noshare", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView noshare(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("map/noshare");
        return modelAndView;
    }

    /**
	 * 关联地图数据 
	 */
	@RequestMapping(value="relationMapData",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void relationMapData(@RequestParam("mapId")Long mapId,@RequestParam("featureId")Long featureId,PrintWriter out){
		try {
			//底图数据
			List<BaseMap> list = baseMapService.getBaseMapByMapId(mapId);
			MapGraphics feature = mapGraphicsService.findById(featureId);
			if(feature == null){
				throw new Exception("can not find data from personFeatureData by id = "+featureId);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			String maps = objectMapper.writeValueAsString(list);
			String fea = objectMapper.writeValueAsString(feature);
			out.print("{\"result\":\"success\",\"maps\":"+maps+",\"feature\":"+fea+"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
//	public static void main(String[] args) {
//		String str = "{\"brand_no\":\"jycy,sy\",\"unit_rank\":\"2\",\"package\":\"2\"}";
//	    String alibabaJson = JSON.toJSONString(str);
////	    List<String> list = JSON.parseArray(str, String.class);
//	    Map<String, Object> map = JSON.parseObject(str);
//	    System.out.println(map.size());
//	}
}
