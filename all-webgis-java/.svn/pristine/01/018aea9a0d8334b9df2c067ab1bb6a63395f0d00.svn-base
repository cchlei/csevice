package com.trgis.trmap.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class MenuController {

    /**
     * @author Alice
     * 获得当前用户登录权限树（暂时写在页面上，待角色权限加入后，入库查询）
     * @param 
     * @return
     */
    @RequestMapping(value = "/")
    public ModelAndView permissionTree() {
    	ModelAndView mav = new ModelAndView("/menu");
//    	List<TreeDate> result = new ArrayList<TreeDate>();//构造返回给前段的集合
    	try {
//    		UserInfo login = (UserInfo)session.getAttribute("login");
//	    	RoleInfo role = roleService.findRoleByRolename(login.getRolename());
//	    	if(role!=null){
//		    	List<PermissionInfo> loginPlist =permissionService.getPermissionsByRoleid(role.getRoleid());//登录者所拥有权限
//		    	List<PermissionInfo> pAllList = permissionService.getRootPermissions();//根节点权限
//		    	for (int i = 0; i < pAllList.size(); i++) {
//		    		if(loginPlist.contains(pAllList.get(i))){//证明有此权限
//						TreeDate roottreedate = new TreeDate();//父节点
//						roottreedate.setName(pAllList.get(i).getPermissionname());
//						roottreedate.setOpen(pAllList.get(i).isClosed());
//						List<PermissionInfo> children = pAllList.get(i).getPermissions();
//						if(children!=null&&children.size()>0){//如果有子集
//							List<TreeDate> treedatelist = getChildren(pAllList.get(i), loginPlist);
//							roottreedate.setChildren(treedatelist);
//						}
//						result.add(roottreedate);
//		    		}
//				}
//	    	}
//	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String resultString="" ;
//			resultString = objectMapper.writeValueAsString(result);
			mav.addObject("resultString",resultString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return mav;
    }
}
