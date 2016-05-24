/**
 * 
 */
package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.web.vo.MenuVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.service.EnterpriseUserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 企业登录跳转
 * 
 * @author zhangqian
 *
 */
@Controller
public class EntLoginController {

	private static final Logger logger = LoggerFactory.getLogger(EntLoginController.class);

	@Autowired
	private EnterpriseUserService entUserService;
	
	@Autowired
	private EntCainfoService entCaInfoService;

	@Deprecated
	@RequestMapping(value = "/console.action")
	public ModelAndView entLogin() {
		logger.debug("login ent system.");
		ModelAndView mv = new ModelAndView();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User entUser = entUserService.findUserByUsername(username);
		if (null == entUser) {
			logger.debug("login error. the user is null.");
			mv.setViewName("error/500");
			logger.debug("forward to 500 error page.");
		} else {
			EntCainfo entInfo = entCaInfoService.findByUser(entUser);
			mv.addObject("ent",entInfo);
			mv.setViewName("console");
		}
		logger.debug("login sucess!");
		return mv;
	}
	

	@RequestMapping("/")
	public ModelAndView userLogin(ModelAndView mv){
		try {
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			logger.debug("Login to Ent System.");
			logger.debug("current user is " + username);
			User user = entUserService.findUserByUsername(username);
			if (BeanUtil.isNotEmpty(user)) {
				switch (user.getStatus()) {
				case ACTIVE:// 正常激活的用户跳转界面
					logger.debug(username + "用户登录成功");
					if(UserEntity.ENTERPRISE.equals(user.getEntity())){
						mv.setViewName("/main");//去企业中心
					}
					break;
				case INACTIVE:// 未激活用户跳转的界面
					logger.debug(username + "用户状态为inactive，不能登录");
					mv.addObject("code", UserEncrypt.QTAES_Encrypt(user.getId().toString()));
					mv.addObject("a", user.getEmail());
					mv.addObject("g", user.getUsername());
					mv.setViewName("redirect:http://a.trmap.cn/inactive.jsp");//前往account未激活页面
					break;
				default:
					logger.debug(username + "用户状态未定义，不能登录");
					mv.setViewName("redirect:/logout");
					break;
				}
				
			}else {
				logger.debug("用户登录失败，查找不到用户");
				mv.setViewName("redirect:/logout");
			}
		} catch (Exception e) {
			logger.debug("用户登录失败", e);
			mv.setViewName("redirect:/logout");
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/menu.action")
	public Map<String, Object> menu(){
		Map<String, Object> map = new HashMap<String, Object>();
		//一级菜单
		List<MenuVo> first = new ArrayList<MenuVo>();
		//===================================================
		MenuVo mf1 = new MenuVo();
		mf1.setName("综合信息");
		mf1.setIcon("zhxx");
		mf1.setHref("entcainfo/casumm");
		first.add(mf1);
		//===================================================
		MenuVo mf2 = new MenuVo();
		mf2.setName("服务共享");
		mf2.setIcon("fwgx");
		mf2.setSubs(getMenu(
				new MenuVo("地图发布","dtfb","",null,getMenu(
						new MenuVo("制作与发布","zzyfb","entmap/tomaplist",null,null),
						new MenuVo("已发布管理","yfbgl","entrelmap/toreleaselist",null,null)
						)),
				new MenuVo("接入审批","jrsp","",null,getMenu(
						new MenuVo("待审批","dsp","entapp/tounaudit",null,null),
						new MenuVo("已审批","ysp","entapp/toapproved",null,null)
						)),
				new MenuVo("服务获取","fwhq","entapp/tolist",null,null)
//				new MenuVo("服务获取","fwhq","entrelmap/toservicelist",null,null)
				//new MenuVo("服务监测","fwjc","",null,null)
				));
		first.add(mf2);
		//===================================================
		MenuVo mf3 = new MenuVo();
		mf3.setName("账号管理");
		mf3.setIcon("zhgl");
		mf3.setSubs(getMenu(
				new MenuVo("账号信息","zhxxin","http://a.trmap.cn/e/toupdate",null,null)
//				new MenuVo("系统消息","xitxx","entrelmap/tosystemmsg",null,null)
				));
		first.add(mf3);
		map.put("menus",first);
		return map;
	}

	/**
	 * 获取菜单
	 * @return
	 */
	private List<MenuVo> getMenu(MenuVo ...vos) {
		List<MenuVo> menus = new ArrayList<MenuVo>();
		for (MenuVo menuVo : vos) {
			menus.add(menuVo);
		}
		return menus;
	}

}
