package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.service.EntRelAttributemetaService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.web.interceptor.annotation.UserCainfoRequired;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 企业矢量要素扩展控制器
 * 
 * @author doris
 * @date 2015-12-15
 */
@Controller
@RequestMapping("/entrelmap")
public class EntRelMapController {
	@Autowired
	private EntMapService entMapService;
	@Autowired
	private EntRelMapService entRelMapService;
	@Autowired
	private EntLayermetaService layermetaService;
	@Autowired
	private EntRelAttributemetaService relAttributeService;
	@Autowired
	private EnterpriseUserService entUserService;
	@Autowired
	private EntCainfoService cainfoService;
	@Autowired
	private EntApplicationService applicationService;
	/**
	 * 跳转至服务申请界面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toserviceget/{mapid}")
	public ModelAndView toServiceGet(@PathVariable Long mapid) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("servicehall/service_getter");
		EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
		modelAndView.addObject("relmap", relmap);
		return modelAndView;
	}
	/**
	 * 跳转至限制矢量的地图服务预览界面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tolimitview/{mapid}")
	public ModelAndView toLimitview(@PathVariable Long mapid) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("servicehall/service_view_limit");
		EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
		modelAndView.addObject("relmap", relmap);
		return modelAndView;
	}
	/**
	 * 跳转已发布页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toreleaselist")
	public ModelAndView toRelease() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("release/service_query");
		return modelAndView;
	}
	/**
	 * 跳转服务大厅的地图服务查询页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tosystemmsg")
	public ModelAndView toSystemMsg() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("system/system_msg");
		return modelAndView;
	}
	/**
	 * 跳转服务大厅的地图服务查询页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toservicelist")
	public ModelAndView toServiceRelease() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("servicehall/service_hall");
		return modelAndView;
	}

	/**
	 * 企业地图分页列表
	 * 
	 * @author doris
	 * @date 2015-12-16
	 * @param rows
	 * @param page
	 * @param search
	 * @param fwtype
	 * @param jiekou
	 * @param fbtime
	 * @param gxtime
	 * @param yxqk
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "/getreleaselist", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getReleaseList(Integer rows, Integer page, String search, String fwtype, String jiekou,
			String fbtime, String gxtime, String yxqk,String area) {
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
		// 用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		if (BeanUtil.isNotEmpty(fwtype)) {
			 ConditionGroup filterGroup = new ConditionGroup();
			 filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			 List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			 String[] fwtypestr=fwtype.split("%%");
			 for (String type : fwtypestr) {
				 searchFilters.add(new SearchCondition("tags", Operator.LIKE, type));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			group.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}
		if (BeanUtil.isNotEmpty(jiekou)) {
			conditions.add(new SearchCondition("serviceType", Operator.LIKE, jiekou));
		}
		Date end = new Date();
		if (BeanUtil.isNotEmpty(fbtime)) {
			if(Integer.valueOf(fbtime)!=-1){
			Date begin = getBegin(-Integer.valueOf(fbtime));
			conditions.add(new SearchCondition("releasetime", Operator.BETWEEN, begin, end));
			}
		}
		if (BeanUtil.isNotEmpty(gxtime)) {
			if(Integer.valueOf(gxtime)!=-1){
				Date begin = getBegin(-Integer.valueOf(gxtime));
				conditions.add(new SearchCondition("updateretime", Operator.BETWEEN, begin, end));
			}
		}
		if (BeanUtil.isNotEmpty(yxqk)) {
			conditions.add(new SearchCondition("isonline", Operator.EQ, yxqk));
		}
		conditions.add(new SearchCondition("user.id", Operator.EQ, user.getId()));
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		//服务大厅的地图服务地域查询条件
		if(BeanUtil.isNotEmpty(area)){
			if(!area.equals("全国 所有")){
				if(area.contains("所有")){
					area=area.substring(0,area.lastIndexOf("所有")-1);
					conditions.add(new SearchCondition("area", Operator.LIKE, area));
				}else{
					conditions.add(new SearchCondition("area", Operator.EQ, area));
				}
			}
		}
		// 排序
		OrderBy order = new OrderBy("umcreatetime", "desc");
		Page<EntRelUserMap> pa = entRelMapService.findByConditions(group, pageNo, pageSize, order);
		if (BeanUtil.isNotEmpty(pa)) {
			List<EntRelUserMap> list = pa.getContent();
			map.put("rows", list);
			map.put("totalCount",pa.getTotalElements());
		}else{
			map.put("rows",new ArrayList<>());
			map.put("totalCount",0);
		}
		
		return map;

	}
	/**
	 * 服务大厅企业地图分页列表
	 * 
	 * @author doris
	 * @date 2015-12-29
	 * @param rows
	 * @param page
	 * @param search
	 * @param fwtype
	 * @param jiekou
	 * @param fbtime
	 * @param gxtime
	 * @param yxqk
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "/getalllist", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getAllList(Integer rows, Integer page, String search, String fwtype, String jiekou,
			String fbtime, String gxtime, String yxqk,String area) {
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
		if (BeanUtil.isNotEmpty(fwtype)) {
			 ConditionGroup filterGroup = new ConditionGroup();
			 filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			 List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			 String[] fwtypestr=fwtype.split("%%");
			 for (String type : fwtypestr) {
				 searchFilters.add(new SearchCondition("tags", Operator.LIKE, type));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			group.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}
		if (BeanUtil.isNotEmpty(jiekou)) {
			conditions.add(new SearchCondition("serviceType", Operator.LIKE, jiekou));
		}
		Date end = new Date();
		if (BeanUtil.isNotEmpty(fbtime)) {
			if(Integer.valueOf(fbtime)!=-1){
			Date begin = getBegin(-Integer.valueOf(fbtime));
			conditions.add(new SearchCondition("releasetime", Operator.BETWEEN, begin, end));
			}
		}
		if (BeanUtil.isNotEmpty(gxtime)) {
			if(Integer.valueOf(gxtime)!=-1){
				Date begin = getBegin(-Integer.valueOf(gxtime));
				conditions.add(new SearchCondition("updateretime", Operator.BETWEEN, begin, end));
			}
		}
		if (BeanUtil.isNotEmpty(yxqk)) {
			conditions.add(new SearchCondition("isonline", Operator.EQ, yxqk));
		}
		conditions.add(new SearchCondition("isonline", Operator.EQ, EnumUtil.ONLINE.SX.getValue()));
		conditions.add(new SearchCondition("isshare", Operator.EQ, EnumUtil.SHARE.YFB.getValue()));
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		//服务大厅的地图服务地域查询条件
		if(BeanUtil.isNotEmpty(area)){
			if(!area.equals("全国 所有")){
				if(area.contains("所有")){
					area=area.substring(0,area.lastIndexOf("所有")-1);
					conditions.add(new SearchCondition("area", Operator.LIKE, area));
				}else{
					conditions.add(new SearchCondition("area", Operator.EQ, area));
				}
			}
		}
		// 排序
		OrderBy order = new OrderBy("umcreatetime", "desc");
		Page<EntRelUserMap> pa = entRelMapService.findByConditions(group, pageNo, pageSize, order);
		if (BeanUtil.isNotEmpty(pa)) {
			List<EntRelUserMap> list = pa.getContent();
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
		c.add(Calendar.MONTH, fbtime);
		return c.getTime();
	}
	
	/**
	 * 服务上下线操作
	 * @param mapid
	 * @param statue
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/online")
	public Map<String, Object> online(Long mapid, String statue, String reason, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
			if("上线".equals(statue)){
				//计算一下是否已超出服务有效期
				if(relmap.getValiditytime().getTime() > new Date().getTime()){ //服务时间未超出当前时间
					//如果之前有正常使用中的订单，上线需要恢复，并邮件通知
					if(BeanUtil.isNotEmpty(relmap.getPassids())){
						List<String> ids = java.util.Arrays.asList(relmap.getPassids().split(","));
						for (String string : ids) {
							Long id = Long.parseLong(string);
							EntApplication application = applicationService.findById(id);
							entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+relmap.getMapname()+"，之前由于发布方企业暂停了服务，现已恢复，您可以继续正常使用该服务了！");
							applicationService.updatePassById(EnumUtil.ISAPPROVED.PASS.getValue(), "", id);
						}
					}
					//如果之前有未审批的订单，上线需要恢复，不发邮件
					if(BeanUtil.isNotEmpty(relmap.getUntreatedids())){
						List<String> ids = java.util.Arrays.asList(relmap.getUntreatedids().split(","));
						for (String string : ids) {
							Long id = Long.parseLong(string);
							applicationService.updatePassById(EnumUtil.ISAPPROVED.UNTREATED.getValue(), "", id);
						}
					}
					//未过期恢复上线
					relmap.setIsonline(EnumUtil.ONLINE.SX.getValue());
					relmap.setPassids("");
					relmap.setUntreatedids("");
					entRelMapService.editRelUserMap(relmap);
				}else{
					//过期自动下线
					//修改所有异常订单原因为下线
					applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，你可以在服务大厅选择其他服务申请。", relmap, EnumUtil.ISAPPROVED.EXCEP.getValue());
					//如果之前有正常使用中的订单，过期自动下线
					if(BeanUtil.isNotEmpty(relmap.getPassids())){
						List<String> ids = java.util.Arrays.asList(relmap.getPassids().split(","));
						for (String string : ids) {
							Long id = Long.parseLong(string);
							EntApplication application = applicationService.findById(id);
							entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+relmap.getMapname()+"已过期，因此您可能暂时无法使用该服务了，有问题请联系该企业！");
							applicationService.updatePassById(EnumUtil.ISAPPROVED.OUTDATE.getValue(), "", id);
						}
					}
					//待审核列表中的订单变为异常状态
					applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，你可以在服务大厅选择其他服务申请。", relmap, EnumUtil.ISAPPROVED.UNTREATED.getValue());
					//删除服务并且将地图元数据状态修改为未发布状态
					entRelMapService.deleteEntRelUserMap(mapid);//relmap.setApplicationids("");
					EntUserMap usermap = entMapService.findUserMapById(mapid);
					usermap.setIsshare(EnumUtil.SHARE.WFB.getValue());
					entMapService.createEnterpriseMap(usermap);
				}
			}else if("挂起".equals(statue)){
				//先记录所有正常使用服务的申请id+未审批的申请id
				List<Long> passids = applicationService.getIdsByPassmap(relmap, EnumUtil.ISAPPROVED.PASS.getValue());
				List<Long> untreatedids = applicationService.getIdsByPassmap(relmap, EnumUtil.ISAPPROVED.UNTREATED.getValue());
				Integer isonline = EnumUtil.ONLINE.GQ.getValue();
				relmap.setIsonline(isonline);
				relmap.setPassids(StringUtils.join(passids, ","));
				relmap.setUntreatedids(StringUtils.join(untreatedids, ","));
				entRelMapService.editRelUserMap(relmap);
				//需要邮件通知正常使用的用户
				List<EntApplication> applications = applicationService.getByPassmap(relmap,EnumUtil.ISAPPROVED.PASS.getValue());
				for (EntApplication application : applications) {
					entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+relmap.getMapname()+"，由于发布方企业暂停了服务，因此您可能暂时无法使用该服务了，请联系该企业或等待服务上线，服务恢复使用后会邮件通知您！");
				}
				//修改所有正常状态下的申请为异常
				applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), reason, relmap, EnumUtil.ISAPPROVED.PASS.getValue());
				//待审核列表中的订单变为异常状态
				applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已挂起，原因："+reason, relmap, EnumUtil.ISAPPROVED.UNTREATED.getValue());
			}else{//下线
				//需要邮件通知正常使用的用户
				List<EntApplication> applications = applicationService.getByPassmap(relmap,EnumUtil.ISAPPROVED.PASS.getValue());
				for (EntApplication application : applications) {
					entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+relmap.getMapname()+"，由于发布方企业下线了服务，因此您可能无法使用该服务了，有问题请联系该企业！");
				}
				//修改所有异常订单原因为下线
				applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，原因："+reason, relmap, EnumUtil.ISAPPROVED.EXCEP.getValue());
				//修改所有正常状态下的申请为异常
				applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), reason, relmap, EnumUtil.ISAPPROVED.PASS.getValue());
				//待审核列表中的订单变为异常状态
				applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，原因："+reason, relmap, EnumUtil.ISAPPROVED.UNTREATED.getValue());
				//删除服务并且将地图元数据状态修改为未发布状态
				entRelMapService.deleteEntRelUserMap(mapid);//relmap.setApplicationids("");
				EntUserMap usermap = entMapService.findUserMapById(mapid);
				usermap.setIsshare(EnumUtil.SHARE.WFB.getValue());
				entMapService.createEnterpriseMap(usermap);
			}
			map.put("relust", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 判断是否可以申请
	 * @param mapid
	 * @param modelAndView
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isApply/{mapid}")
	public Map<String, Object> isApply(@PathVariable Long mapid) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询是否已经申请
		//申请方
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		//未认证用户不得申请服务
		EntCainfo sqf = cainfoService.findByUser(user);
		if(BeanUtil.isNotEmpty(sqf) && sqf.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue())){
			//申请的服务
			EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
			if(relmap.getDelflag() == EnumUtil.DELFLAG.DEL.getValue()){
				map.put("result", "error");
				map.put("msg", "该服务已被删除，请刷新");
			}else if(relmap.getIsshare() != EnumUtil.ONLINE.SX.getValue()){
				map.put("result", "error");
				map.put("msg", "改服务已被挂起或下线，请刷新");
			}else if(relmap.getUser().getId()==user.getId()){
				map.put("result", "error");
				map.put("msg", "不能申请自己发布的服务");
			}else{
				List<EntApplication> list = applicationService.findByGetterAndRelUserMap(user, relmap);
				if(BeanUtil.isEmpty(list)){
					map.put("result", "success");
				}else{
					map.put("result", "error");
					map.put("msg", "您已经申请过该服务，请在服务获取列表页面进行相关操作");
				}
			}
		}else{
			map.put("result", "error");
			map.put("msg", "未认证用户不得申请服务");
		}
		return map;
	}
	/**
	 * 跳转申请页面
	 * applicationid有可能为null
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/toApply/{mapid}/{applicationid}")
	public ModelAndView toApply(@PathVariable Long mapid,@PathVariable String applicationid, ModelAndView modelAndView) {
		modelAndView.addObject("mapid", mapid);
		modelAndView.addObject("applicationid", applicationid);
		modelAndView.setViewName("servicehall/service_apply");
		return modelAndView;
	}

	/**
	 * 查询申请信息页面(申请第一步)
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/getApplyInfo/{mapid}")
	public Map<String, Object> getApplyInfo(@PathVariable Long mapid) {
		//查询已发布服务的属性封装成json
		EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
		Map<String, Object> result = new HashMap<String, Object>();
		//1接口类型
		String serviceType[] = relmap.getServiceType().split(",");
		//2基本属性结构
		List<Object> tclayer = new ArrayList<Object>();
		Map<String, Object> mrtype = new HashMap<String, Object>();
		mrtype.put("class", "mrtype");
		mrtype.put("name", "默认图层");
		List<Object> jbshlist = new ArrayList<Object>();
		Map<String, String> attributemete = new HashMap<String, String>();
		attributemete.put("name", "服务名称");
		attributemete.put("code", "mapname");
		Map<String, String> attributemete1 = new HashMap<String, String>();
		attributemete1.put("name", "发布单位");
		attributemete1.put("code", "username");
		Map<String, String> attributemete2 = new HashMap<String, String>();
		attributemete2.put("name", "有效时间");
		attributemete2.put("code", "validitytime");
		Map<String, String> attributemete3 = new HashMap<String, String>();
		attributemete3.put("name", "创建时间");
		attributemete3.put("code", "umcreatetime");
		Map<String, String> attributemete4 = new HashMap<String, String>();
		attributemete4.put("name", "服务标签");
		attributemete4.put("code", "tags");
		Map<String, String> attributemete5 = new HashMap<String, String>();
		attributemete5.put("name", "坐标系");
		attributemete5.put("code", "coordinate");
		jbshlist.add(attributemete);
		jbshlist.add(attributemete1);
		jbshlist.add(attributemete2);
		jbshlist.add(attributemete3);
		jbshlist.add(attributemete4);
		jbshlist.add(attributemete5);
		mrtype.put("data", jbshlist);
		tclayer.add(mrtype);
		//3扩展属性结构
		Map<String, Object> layer = new HashMap<String, Object>();
		EntUserMap map = entMapService.findUserMapById(mapid);
		List<EntLayermeta> layermeta = layermetaService.findLayermetasByMap(map);
		List<EntRelAttributemeta> Attributelist = new ArrayList<EntRelAttributemeta>();
		List<Object> list = new ArrayList<Object>();
		if(BeanUtil.isNotEmpty(layermeta)){
			Attributelist = relAttributeService.findByEntLayermeta(layermeta.get(0));
			for (int i = 0; i < Attributelist.size(); i++) {
				Map<String, String> attribute = new HashMap<String, String>();
				attribute.put("name", Attributelist.get(i).getAttralias());
				attribute.put("code", Attributelist.get(i).getAttrid().toString());
				attribute.put("checked", "false");
				list.add(attribute);
			}
		}
		layer.put("class", "layer");
		layer.put("name", "可选图层");
		layer.put("data", list);
		tclayer.add(layer);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tclayer", tclayer);
		m.put("jktype", Arrays.asList(serviceType));
		if(BeanUtil.isNotEmpty(relmap.getValiditytime())){
			m.put("validitytime", BeanUtil.datetostring(relmap.getValiditytime()));
		}
		result.put("rows", m);
		return result;
	}
	
	/**
	 * 查询申请form页面(申请第二步)
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/getApplyForm/{mapid}")
	public Map<String, Object> getApplyForm(@PathVariable Long mapid) {
		EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("sid", relmap.getId().toString());
		//申请方
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		EntCainfo sqf = cainfoService.findByUser(user);
		map.put("sqcompany", sqf.getEnterpriseName());
		map.put("sqcontacts", sqf.getManagerName());
		map.put("sqemail", sqf.getUser().getEmail());
		map.put("sqphone", sqf.getUser().getMobile());
	    //发布方
		EntCainfo fbf = cainfoService.findByUser(relmap.getUser());
		map.put("fbcompany", fbf.getEnterpriseName());
		map.put("fbcontacts", fbf.getManagerName());
		map.put("fbemail", fbf.getUser().getEmail());
		map.put("fbphone", fbf.getUser().getMobile());
		//服务信息
		map.put("servicename", relmap.getMapname());
		map.put("area", relmap.getArea());
		map.put("releasetime", DateUtil.dateToString(relmap.getReleasetime(),"yyyy年MM月dd日"));
		map.put("refreshtime", DateUtil.dateToString(relmap.getUpdateretime(),"yyyy年MM月dd日"));
		map.put("enddate", DateUtil.dateToString(relmap.getValiditytime(),"yyyy年MM月dd日"));
		map.put("ysnumber", relmap.getFeaturecount().toString());
		map.put("range", relmap.getScope());
		map.put("coordinate", relmap.getCoordinate());
		result.put("rows", map);
		return result;
	}
	
	/**
	 * 申请提交(申请第三步)
	 * @return
	 */
	@UserCainfoRequired
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/applyMap/{mapid}/{applicationid}")
	public Map<String, Object> applyMap(@PathVariable Long mapid,@PathVariable String applicationid,String serviceType,String attributes,Integer sqtime) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntRelUserMap relmap = entRelMapService.findRelUserMapById(mapid);
			if(getBegin(sqtime).getTime() > relmap.getValiditytime().getTime()){
				result.put("result", "error");
				result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
			}else{
				//申请方
				String username = (String) SecurityUtils.getSubject().getPrincipal();
				User user = entUserService.findUserByUsername(username);
				
				EntApplication application = new EntApplication();
				if(BeanUtil.isNull(applicationid)||"null".equals(applicationid)){//新订单
					String num = applicationService.findMaxNumber();
					if(BeanUtil.isNull(num)){//当天没有
						num = DateUtil.dateToString(new Date(), "yyyyMMdd")+"000001";
					}else{
						num = String.valueOf(Long.parseLong(num)+1);
					}
					application.setNumber(num);
				}else{//续约订单OR修改订单
					Calendar c = Calendar.getInstance();
					application = applicationService.findById(Long.parseLong(applicationid));
					//原订单
					EntApplication original = applicationService.findByNumber(application.getRenumber());
					if(BeanUtil.isEmpty(original)){//修改订单
						application.setApplytime(new Date());
						c.add(Calendar.MONTH, sqtime);
						if(c.getTime().getTime() > relmap.getValiditytime().getTime()){
							result.put("result", "error");
							result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
							return result;
						}
					}else{//续约订单
						//根据续约的时间，查询是否超出服务有限期范围
						if(original.getIsapproved() == EnumUtil.ISAPPROVED.PASS.getValue()){//通过状态时间为累加计算
							c.setTime(original.getApplytime());
							c.add(Calendar.MONTH, sqtime + original.getApplymonth());
							if(c.getTime().getTime() > relmap.getValiditytime().getTime()){
								result.put("result", "error");
								result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
								return result;
							}
						}else{//其他状态时间以当前起算
							c.add(Calendar.MONTH, sqtime);
							if(c.getTime().getTime() > relmap.getValiditytime().getTime()){
								result.put("result", "error");
								result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
								return result;
							}
						}
					}
				}
				application.setSuggestion("");
				application.setGetter(user);//申请方
				application.setSetter(relmap.getUser());//发布方
				application.setRelUserMap(relmap);
				application.setApplymonth(sqtime);
				application.setServiceType(serviceType);
				application.setAttributes(attributes);
				application.setIsapproved(EnumUtil.ISAPPROVED.UNTREATED.getValue());//待审批
				applicationService.save(application);
				result.put("result", "success");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "申请提交失败");
		}
		return result;
	}
}
