package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
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
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.service.EntRelAttributemetaService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.web.util.Constants;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 地图申请记录
 * @author doris
 * @date 2015-12-31
 */
@Controller
@RequestMapping("/entapp")
public class EntApplicationController {
    
	@Autowired
	private EntApplicationService entApplicationService;
	@Autowired
	private EnterpriseUserService entUserService;
	@Autowired
	private EntRelAttributemetaService entRelAttributemetaService;
	@Autowired
	private EntCainfoService entCainfoService;
	/**
	 * 跳转获取地图服务的页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tolist")
	public ModelAndView toEntApplicationList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("release/service_obtain");
		return modelAndView;
	}
	/**
	 * 跳转到待审核页面
	 * @author mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tounaudit")
	public ModelAndView toUnEntApplicationList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("servicehall/service_approval");
		return modelAndView;
	}

	/**
	 * 跳转到已审核页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toapproved")
	public ModelAndView toApEntApplicationList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("servicehall/service_approval_sp");
		return modelAndView;
	}
	/**
	 * 获取申请服务列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getlist", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getEntApplicationList(Integer rows, Integer page, String sqtime, String jztime,
			Integer approval, String search) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);

		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 关键字
		if (BeanUtil.isNotEmpty(search)) {
			search = search == null ? "" : BeanUtil.HtmltoText(search).replaceAll("\"", "").replace("'", "");
			conditions.add(new SearchCondition("relUserMap.mapname", Operator.LIKE, search));
		}
		int pageNo = page == null ? 0 : page;
		int pageSize = rows == null ? 24 : rows;
		// 用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);

		if (BeanUtil.isNotEmpty(approval)) {
			if (approval != -1) {
				conditions.add(new SearchCondition("isapproved", Operator.EQ, approval));
			}
		}
		Date end = new Date();
		if (BeanUtil.isNotEmpty(sqtime)) {
			if (Integer.valueOf(sqtime) != -1) {
				Date begin = getBegin(-Integer.valueOf(sqtime));
				conditions.add(new SearchCondition("applytime", Operator.BETWEEN, begin, end));
			}
		}
		if (BeanUtil.isNotEmpty(jztime)) {
			if (Integer.valueOf(jztime) != -1) {
				Date begin = getBegin(-Integer.valueOf(jztime));
				conditions.add(new SearchCondition("relUserMap.validitytime", Operator.BETWEEN, begin, end));
			}
		}

		conditions.add(new SearchCondition("getter.id", Operator.EQ, user.getId()));
		group.setConditions(conditions);

		// 排序
		OrderBy order = new OrderBy("applytime", "desc");
		Page<EntApplication> pa;
		try {
			pa = entApplicationService.findByConditions(group, pageNo, pageSize, order);
			if (BeanUtil.isNotEmpty(pa)) {
				List<EntApplication> list = pa.getContent();
				map.put("rows",setAttributesName(list));
				map.put("totalCount", pa.getTotalElements());
			} else {
				map.put("rows", new ArrayList<>());
				map.put("totalCount", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return map;

	}
	/**
	 * 获取待审核的服务列表
	 * @author mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getunaudit", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getUnEntApplicationList(Integer rows, Integer page, String sqtime, String search) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 关键字
		if (BeanUtil.isNotEmpty(search)) {
			search = search == null ? "" : BeanUtil.HtmltoText(search).replaceAll("\"", "").replace("'", "");
			conditions.add(new SearchCondition("relUserMap.mapname", Operator.LIKE, search));
		}
		int pageNo = page == null ? 0 : page;
		int pageSize = rows == null ? 12 : rows;
		// 用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		
		conditions.add(new SearchCondition("isapproved", Operator.EQ, EnumUtil.ISAPPROVED.UNTREATED.getValue()));
		
		Date end = new Date();
		if (BeanUtil.isNotEmpty(sqtime)) {
			if (Integer.valueOf(sqtime) != -1) {
				Date begin = getBegin(-Integer.valueOf(sqtime));
				conditions.add(new SearchCondition("applytime", Operator.BETWEEN, begin, end));
			}
		}
		//其他用户申请自己的服务
		conditions.add(new SearchCondition("setter.id", Operator.EQ, user.getId()));
		group.setConditions(conditions);
		
		// 排序
		OrderBy order = new OrderBy("applytime", "desc");
		
		Page<EntApplication> pa = entApplicationService.findByConditions(group, pageNo, pageSize, order);
		if (BeanUtil.isNotEmpty(pa)) {
			List<EntApplication> list = pa.getContent();
			for (EntApplication entApp : list) {
				EntCainfo gent = entCainfoService.findByUser(entApp.getGetter());
				entApp.setGettercomp(gent.getEnterpriseName());
			}
			map.put("rows",setAttributesName(list));
			map.put("totalCount", pa.getTotalElements());
		} else {
			map.put("rows", new ArrayList<>());
			map.put("totalCount", 0);
		}
		
		return map;
		
	}
	/**
	 * 获取已审核的服务列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getapproved", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getApEntApplicationList(Integer rows, Integer page, String sqtime, String search) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询条件
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 关键字
		if (BeanUtil.isNotEmpty(search)) {
			search = search == null ? "" : BeanUtil.HtmltoText(search).replaceAll("\"", "").replace("'", "");
			conditions.add(new SearchCondition("relUserMap.mapname", Operator.LIKE, search));
		}
		int pageNo = page == null ? 0 : page;
		int pageSize = rows == null ? 12 : rows;
		// 用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		
//		conditions.add(new SearchCondition("isapproved", Operator.EQ, EnumUtil.ISAPPROVED.PASS.getValue()));
		
		Date end = new Date();
		if (BeanUtil.isNotEmpty(sqtime)) {
			if (Integer.valueOf(sqtime) != -1) {
				Date begin = getBegin(-Integer.valueOf(sqtime));
				conditions.add(new SearchCondition("applytime", Operator.BETWEEN, begin, end));
			}
		}
		//拼接子条件查询
		ConditionGroup filterGroup = new ConditionGroup();
		filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
		// 判断并添加查询条件
		List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
		searchFilters.add(new SearchCondition("ispause", Operator.EQ, EnumUtil.ONLINE.GQ.getValue()));
		searchFilters.add(new SearchCondition("isapproved", Operator.EQ, EnumUtil.ISAPPROVED.PASS.getValue()));
		searchFilters.add(new SearchCondition("isapproved", Operator.EQ, EnumUtil.ISAPPROVED.OUTDATE.getValue()));
		filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
		group.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		group.setConditions(searchFilters);
		conditions.add(new SearchCondition("setter.id", Operator.EQ, user.getId()));
		group.setConditions(conditions);
		
		// 排序
		OrderBy order = new OrderBy("applytime", "desc");
		Page<EntApplication> pa = entApplicationService.findByConditions(group, pageNo, pageSize, order);
		if (BeanUtil.isNotEmpty(pa)) {
			List<EntApplication> list = pa.getContent();
			for (EntApplication entApp : list) {
				EntCainfo gent = entCainfoService.findByUser(entApp.getGetter());
				entApp.setGettercomp(gent.getEnterpriseName());
			}
			map.put("rows",setAttributesName(list));
			map.put("totalCount", pa.getTotalElements());
		} else {
			map.put("rows", new ArrayList<>());
			map.put("totalCount", 0);
		}
		
		return map;
		
	}	
	/**
	 * 置换list中属性为json中所需的字段
	 * @param list
	 * @return 
	 */
	private List<EntApplication> setAttributesName(List<EntApplication> list) {
		List<EntApplication> eList = new ArrayList<>();
		try {
			for (EntApplication entApplication : list) {
				chageNames(entApplication);
				if(BeanUtil.isNotEmpty(entApplication.getApprovedtime())){
			    	entApplication.setUtilDate(getUtilDate(entApplication));
				}
				eList.add(entApplication);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return eList;
	}


	/**
	 * 将id转换为名字
	 * @param entApplication
	 */
	private void chageNames(EntApplication entApplication) {
		if(BeanUtil.isNotEmpty(entApplication.getAttributes())){
			String [] idstrs =entApplication.getAttributes().split(",");
			entApplication.setAttributesName(entApplication.getAttributes());
	        for (String id : idstrs) {
	        	if(BeanUtil.isNumber(id)){
	        		EntRelAttributemeta en = entRelAttributemetaService.findById(Long.valueOf(id));
	            	entApplication.setAttributesName(entApplication.getAttributesName().replace(id, en.getAttralias()));
	        	}
			}
		}
        entApplication.setSettercomp(entCainfoService.findByUser(entApplication.getSetter()).getEnterpriseName());
	}
	/**
	 * 获取审批截止日期
	 * @param entApplication
	 * @return
	 */
	private Date getUtilDate(EntApplication entApplication) {
		Calendar c = Calendar.getInstance();
		c.setTime(entApplication.getApplytime());//根据申请时间开始计算（不会超出服务有效期）
		Integer applymonth= entApplication.getApplymonth();
		c.add(Calendar.MONTH, applymonth);
		return c.getTime();
	}

	/**
	 * 获取时间
	 * 
	 * @param fbtime
	 * @return
	 */
	private static Date getBegin(Integer fbtime) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, fbtime);
		return c.getTime();
	}
	
	/**
	 * 获取当前对象
	 * @return
	 * @author doris
	 */
	@ResponseBody
	@RequestMapping(value = "/getObj")
	public  Map<String, Object> getObj(Long id,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			EntCainfo sent = entCainfoService.findByUser(application.getSetter());
			EntCainfo gent = entCainfoService.findByUser(application.getGetter());
			map.put("number", application.getNumber());
			map.put("isapproved", application.getIsapproved());
			if(BeanUtil.isNotEmpty(application.getApprovedtime())){
				map.put("approvedtime", BeanUtil.datetostring(application.getApprovedtime()));
			}
			//单位信息
			map.put("gettercomp", gent.getEnterpriseName());
			map.put("settercomp", sent.getEnterpriseName());
			map.put("gusername", gent.getManagerName());
			map.put("susername", sent.getManagerName());
			map.put("gemail", application.getGetter().getEmail());
			map.put("semail", application.getSetter().getEmail());
			map.put("gmobile", gent.getManagerPhone());
			map.put("smobile", sent.getManagerPhone());
			//服务信息
			EntRelUserMap entRelMap = application.getRelUserMap();
			map.put("mapname",entRelMap.getMapname());
			map.put("area", entRelMap.getArea());
			map.put("releasetime", BeanUtil.datetostring(entRelMap.getReleasetime()));
			map.put("updateretime", BeanUtil.datetostring(entRelMap.getUpdateretime()));
			if(BeanUtil.isNotEmpty(entRelMap.getValiditytime())){
				map.put("validitytime", BeanUtil.datetostring(entRelMap.getValiditytime()));
			}
			map.put("featurecount", entRelMap.getFeaturecount());
			map.put("scope", entRelMap.getScope());
			map.put("coordinate", entRelMap.getCoordinate());
			//申请信息
			map.put("applymonth", application.getApplymonth());
			map.put("serviceType", application.getServiceType());
			chageNames(application);
			map.put("attributesName",application.getAttributesName());
			map.put("applytime",  BeanUtil.datetostring(application.getApplytime()));
			map.put("shareurl",geturl(application,request));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			map.put("error","获取信息失败");
		}
		return map;
	}

	/**
	 * 获取接口发布地址
	 * @param entApplication
	 * @param request 
	 * @return
	 */
	private String geturl(EntApplication entApplication, HttpServletRequest request) {
		EntRelUserMap re = entApplication.getRelUserMap();
		String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath+Constants.MAPSHARE+re.getShareurl();
	}


	/**
	 * 撤销申请（并不删除）
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelApply")
	public Map<String, Object> cancelApply(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			application.setIsapproved(EnumUtil.ISAPPROVED.DISABLE.getValue());//已撤销
			entApplicationService.save(application);
			result.put("result", "success");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "撤销提交失败");
		}
		return result;
	}
	
	/**
	 * 删除申请
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteApply")
	public Map<String, Object> deleteApply(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			entApplicationService.delete(id);
			result.put("result", "success");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "撤销提交失败");
		}
		return result;
	}
	/**
	 * 判断是否可续约
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/couldApply")
	public Map<String, Object> couldApply(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			EntRelUserMap relmap = application.getRelUserMap();
			//不可同时生成多张待审批的续约订单
			List<EntApplication> replaylist = entApplicationService.findRepaly(application.getGetter(), relmap);
			if(BeanUtil.isNotEmpty(replaylist)){
				result.put("result", "error");
				result.put("msg", "您已经续约过当前服务</br>订单编号为"+replaylist.get(0).getNumber()+",请查询");
			}else{
				result.put("result", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "检查失败");
		}
		return result;
	}
	/**
	 * 续约
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/reApply")
	public Map<String, Object> reApply(Long id, Integer date) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			EntRelUserMap relmap = application.getRelUserMap();
			Calendar c = Calendar.getInstance();
			//根据续约的时间，查询是否超出服务有限期范围
			if(application.getIsapproved() == EnumUtil.ISAPPROVED.PASS.getValue()){//通过状态时间为累加计算
				c.setTime(application.getApplytime());
				c.add(Calendar.MONTH, date+application.getApplymonth());
				if(c.getTime().getTime() > relmap.getValiditytime().getTime()){
					result.put("result", "error");
					result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
					return result;
				}
			}else{//其他状态时间一当前起算
				c.add(Calendar.MONTH, date);
				if(c.getTime().getTime() > relmap.getValiditytime().getTime()){
					result.put("result", "error");
					result.put("msg", "申请时长不能大于服务期限，当前服务有效期至"+DateUtil.dateToString(relmap.getValiditytime(), "yyyy-MM-dd"));
					return result;
				}
			}
			//续约不影响原来的订单，重新申请一个新的
			EntApplication newapplication = new EntApplication();
			newapplication.setRenumber(application.getNumber());//被续约的单号
			String num = entApplicationService.findMaxNumber();
			if(BeanUtil.isNull(num)){//当天没有
				num = DateUtil.dateToString(new Date(), "yyyyMMdd")+"000001";
			}else{
				num = String.valueOf(Long.parseLong(num)+1);
			}
			newapplication.setNumber(num);
			newapplication.setApplymonth(date);
			newapplication.setGetter(application.getGetter());//申请方
			newapplication.setSetter(application.getSetter());//发布方
			newapplication.setRelUserMap(relmap);
			newapplication.setServiceType(application.getServiceType());
			newapplication.setAttributes(application.getAttributes());
			newapplication.setIsapproved(EnumUtil.ISAPPROVED.UNTREATED.getValue());//待审批
			entApplicationService.save(newapplication);
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "撤销提交失败");
		}
		return result;
	}
	
	/**
	 * 审核订单申请
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/audiApply")
	public Map<String, Object> audiApply(String ispass, Long id, String reason) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			if("yes".equals(ispass)){
				application.setIsapproved(EnumUtil.ISAPPROVED.PASS.getValue());
				application.setApprovedtime(new Date());
				//如果是续约的订单需要将原来的订单删除（续约来源：已过期及未过期）
				if(BeanUtil.isNotEmpty(application.getRenumber())){
					EntApplication original = entApplicationService.findByNumber(application.getRenumber());
					if(BeanUtil.isNotEmpty(original)){
						if(new Date().before(getUtilDate(original))) { //如果原订单未过期,时长累加
							application.setApplytime(original.getApplytime());
							application.setApplymonth(original.getApplymonth() + application.getApplymonth());
						}else{
							application.setApplytime(new Date());//过期/申请时间重新计算
						}
						entApplicationService.delete(original.getId());
					}
					application.setRenumber(null);
				}
			}else if("no".equals(ispass)){
				application.setSuggestion(reason);
				application.setIsapproved(EnumUtil.ISAPPROVED.FAIL.getValue());
			}
			entApplicationService.save(application);
			result.put("result", "success");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "审批失败");
		}
		return result;
	}
	
	/**
	 * 订单暂停
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/pauseApply")
	public Map<String, Object> pauseApply(Long id, String reason) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			application.setIsapproved(EnumUtil.ISAPPROVED.EXCEP.getValue());
			application.setSuggestion(reason);
			application.setIspause(EnumUtil.ONLINE.GQ.getValue());//暂停
			entApplicationService.save(application);
			entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+application.getRelUserMap().getMapname()+"由于发布方企业暂停了服务，因此您可能暂时无法使用该服务了，暂停原因："+reason+";请联系该企业或等待服务上线，服务恢复使用后会邮件通知您！");
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "暂停失败");
		}
		return result;
	}
	
	/**
	 * 订单恢复
	 * @return
	 * @author Alice
	 */
	@ResponseBody
	@RequestMapping(value = "/restoreApply")
	public Map<String, Object> restoreApply(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EntApplication application = entApplicationService.findById(id);
			application.setIspause(null);
			//如果未过期
			Calendar c = Calendar.getInstance();
			c.setTime(application.getApplytime());
			c.add(Calendar.MONTH, application.getApplymonth());
			if(c.getTime().getTime() > new Date().getTime()){ //服务时间未超出当前时间
				entApplicationService.updatePassById(EnumUtil.ISAPPROVED.PASS.getValue(), "", id);
				entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+application.getRelUserMap().getMapname()+"已恢复使用！");
			}else{
				entApplicationService.updatePassById(EnumUtil.ISAPPROVED.OUTDATE.getValue(), "", id);
				entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+application.getRelUserMap().getMapname()+"，已到申请有效期，谢谢使用，如需继续使用请续约！");
			}
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "error");
			result.put("msg", "恢复失败");
		}
		return result;
	}
}
