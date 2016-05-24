package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;


/**
 * 企业认证信息控制器
 * @author doris
 * @date 2015-11-27
 *
 */
@Controller
@RequestMapping(value = "/entcainfo")
public class EntCainfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(EntCainfoController.class);
	
	private static final String JSONP = "%s(%s)";
	
	@Autowired
	private EntCainfoService cainfoService;
	
	@Autowired
	private EnterpriseUserService entUserService;
	
	//跳转到认证页面
	@RequestMapping(value="/cainfo")
	public ModelAndView cainfoview() {
		logger.debug("cainfo....");
		ModelAndView md = new ModelAndView();
		md.setViewName("account/enterprise_auth");
		return md;
	}
	
	//	企业用户认证信息提交
	@ResponseBody
	@RequestMapping(value="/docainfo", method=RequestMethod.POST)
	public Map<String, Object> submitcainfo(@ModelAttribute EntCainfo cainfo,@RequestParam String year) {
		logger.debug("docainfo....");
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		EntCainfo  entCainfo = cainfoService.findByUser(user);
		if(BeanUtil.isNotEmpty(entCainfo)) {
			map.put("result", "fail");
			map.put("msg", "您已申请过认证！");
			return map;
		}
		cainfo.setUser(user);
		cainfo.setCastatus(EnumUtil.CASTATUS.AUDITWAIT.getValue());
		cainfo.setCertificateDate(new Date());
		Integer n = Integer.valueOf(year);
		cainfo.setUtilValidDate(BeanUtil.formatDate(n));
		try {
			cainfoService.save(cainfo);
			logger.debug("save success.....");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "认证信息提交失败！");
			logger.error("认证信息提交失败:"+e.getMessage());
			return map;
		}
		map.put("result", "success");
		map.put("msg", "认证信息提交成功！");
		return map;
	} 
	
	//跳转到企业信息页面/entcainfo/casumm
	@RequestMapping(value="/casumm")
	public ModelAndView casumview() {
		logger.debug("casumm....");
		ModelAndView md = new ModelAndView();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println("username: " + username);
		User user = entUserService.findUserByUsername(username);
		if(BeanUtil.isEmpty(user)) {
			md.addObject("msg", "找不到登录用户！");
			return md;
		}	
		logger.debug("loginUser is " + user.getName());
		md.addObject("user", user);
		EntCainfo cainfo = cainfoService.findByUser(user);
		md.addObject("cainfo", cainfo);
		md.setViewName("account/enterprise_summ");
		return md;
	}
	
	//跳转到认证修改页面
	@RequestMapping(value="/update")
	public ModelAndView updatecainfoview() {
		logger.debug("cainfo updateview....");
		ModelAndView md = new ModelAndView();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println("username: " + username);
		User user = entUserService.findUserByUsername(username);
		EntCainfo cainfo = cainfoService.findByUser(user);
		md.addObject("cainfo", cainfo);
		md.setViewName("account/enterprise_update");
		return md;
	}
	
	//	企业用户认证信息修改
	@ResponseBody
	@RequestMapping(value="/doupdate", method=RequestMethod.POST)
	public Map<String, Object>  updatecainfo(@ModelAttribute EntCainfo cainfo,@RequestParam String year) {
		logger.debug("cainfo update....");
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		cainfo.setUser(user);
		cainfo.setCastatus(EnumUtil.CASTATUS.AUDITWAIT.getValue());
		cainfo.setCertificateDate(new Date());
		Integer n = Integer.valueOf(year);
		cainfo.setUtilValidDate(BeanUtil.formatDate(n));
		try {
			cainfoService.edit(cainfo);
			logger.debug("update success.....");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "认证信息修改失败！");
			return map;
		}
		map.put("result", "success");
		map.put("msg", "认证信息修改成功！");
		return map;
	} 
	//服务大厅所展示企业信息的获取/entcainfo/service_hall
	@ResponseBody
	@RequestMapping(value="/service_hall")
	public Map<String,Object> getcainfo() {
		logger.debug("service_hall getcainfo...");
		Map<String, Object> map = new HashMap<String, Object>();
		//EntCainfo  entCainfo = cainfoService.findByUser(user);
		List<Object> list = new ArrayList<>();
		List<EntCainfo> lsca = cainfoService.findByCastatus(EnumUtil.CASTATUS.AUDITED.getValue());
		for (EntCainfo cainfo : lsca) {
			Map<String, Object> mmap = new HashMap<String, Object>();
			mmap.put("qyname",cainfo.getEnterpriseName());
			String img = cainfo.getUser().getHeadimg();
			if(BeanUtil.isNotEmpty(img)) {
				mmap.put("img", img);
			}else {
				mmap.put("img", "");
			}
			list.add(mmap);
		}
		map.put("rows", list);
		return map;
	}
	//认证信息撤销entcainfo/revoke
	@ResponseBody
	@RequestMapping(value="/revoke")
	public Map<String, Object> revokecainfo() {
		logger.debug("cainfo revoke....");
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		EntCainfo  entCainfo = cainfoService.findByUser(user);
		String status = entCainfo.getCastatus();
		if(status.equals("0")) {
			cainfoService.revocationApply(entCainfo.getId());//撤销申请接口
//			entCainfo.setCastatus(EnumUtil.CASTATUS.AUDITFAIL.getValue());
//			cainfoService.edit(entCainfo);
//			EntCainfo Cainfo = cainfoService.findById(entCainfo.getId());
//			if(!"3".equals(Cainfo.getCastatus())) {
//				map.put("result", "error");
//				map.put("msg", "撤销失败！");
//				return map;
//			}
			logger.debug("revoke success....");
			map.put("result", "success");
			map.put("msg", "撤销成功！");
			return map;
		}else {
			map.put("result", "fail");
			map.put("msg", "此状态下不允许撤销！");
			return map;
		}
	}
	//验证企业名称的重复性
	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	@ResponseBody
	public String validation(@RequestParam String type, @RequestParam String value, @RequestParam String callback) {
		logger.debug("EnterpriseName validation.....");
		EntCainfo cainfo = null;
		if (StringUtils.isBlank(type)) {
			return jsonp(callback, false);
		}
		/*try {
			byte[] pool = value.getBytes("iso-8859-1");
			value = new String(pool, "UTF-8");
			System.out.println("value: " + value);
		} catch (UnsupportedEncodingException e) {
			return jsonp(callback, false);
		}*/
		
		//EntCainfo entcainfo = (EntCainfo)request.getSession().getAttribute("cainfo");
		//获得当前登录用户的企业认证信息
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		EntCainfo entcainfo  = cainfoService.findByUser(user);
		//修改认证信息时，若企业名未改动则无须校验重复性(修改信息和提交信息都会进行重复性校验)
		if(BeanUtil.isEmpty(entcainfo)) {//表示在提交信息
			
			switch (type) {
			case "enterpriseName":
				cainfo = cainfoService.findByEnterpriseName(value);
				if (cainfo != null) {
					// 企业名已存在 返回false;
					return jsonp(callback, false);
				} else {
					return jsonp(callback, true);
				}
			}
			return jsonp(callback, true);
		}else if(value.equals(entcainfo.getEnterpriseName())){//表示修改时企业名称未改动
			return jsonp(callback, true);
		}else {//表示修改时企业名称已改动
			switch (type) {
			case "enterpriseName":
				cainfo = cainfoService.findByEnterpriseName(value);
				if (cainfo != null) {
					// 企业名已存在 返回false;
					return jsonp(callback, false);
				} else {
					return jsonp(callback, true);
				}
			}
			return jsonp(callback, true);
		}
	}
	
	private String jsonp(String callback, boolean b) {
		return String.format(JSONP, callback, Boolean.toString(b));
	}
}
