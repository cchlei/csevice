package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.vo.PropertyVo;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 企业矢量要素扩展控制器
 * @author doris
 * @date 2015-12-03
 */
@Controller
@RequestMapping("/entproperties")
public class EntMapPropertiesController {
	 @Autowired 
	 private  EntMapPropertiesService entMapPropertiesService;
	 @Autowired
	 private EnterpriseUserService entUserService;
	 @Autowired 
	 private  EntMapGraphicsService entMapGraphicsService;
	 //编辑矢量扩展值
	 @RequestMapping(value="/editproperties", method=RequestMethod.POST)
	 public Map<String, Object> editEntProperties(Long featureId,String attrkey,String attrvalue,Long id){
		 Map<String, Object> map = new HashMap<String, Object>();
			try {
				 if(BeanUtil.isEmpty(id)){
					 EntMapProperties pro = new EntMapProperties();
					 pro.setFeatureId(featureId);
					 //获取当前企业用户
					 String username = (String) SecurityUtils.getSubject().getPrincipal();
					 User user = entUserService.findUserByUsername(username);
					 pro.setUserId(user.getId());
					 //获取当前矢量所在的企业图层
					 EntMapGraphics entgra=entMapGraphicsService.findById(featureId);
					 pro.setMapId(entgra.getUserMap().getId());
					 //写入HSTORE类型键值对
					 Map<String, String> stores = new HashMap<String, String>();
					 stores.put(attrkey, attrvalue);
					 pro.setStores(stores);
					 entMapPropertiesService.save(pro);
					 map.put("result", "success");
					 map.put("id",pro.getId());
					 map.put("msg", "保存矢量扩展属性值成功！");
				 }else{
					 EntMapProperties pro = entMapPropertiesService.findById(id);
					 pro.getStores().put(attrkey,attrvalue);
					 entMapPropertiesService.edit(pro);
					 map.put("result", "success");
					 map.put("id",id);
					 map.put("msg", "修改矢量扩展属性值成功！");
				 }
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "error");
				map.put("msg", "编辑矢量扩展属性值失败！");
			}
			return map;
	 }
	 //根据矢量id获取矢量扩展信息列表
	 @RequestMapping(value="/getprolist", method=RequestMethod.POST)
	 public Map<String, Object>  getEntMapPropertyList(Long featureId){
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(BeanUtil.isEmpty(featureId)){
			 map.put("result", "error");
			 map.put("msg", "矢量id不能为空！");
			 return map;
		 }
		 try {
			 ArrayList<PropertyVo> entproperties= new ArrayList<>();
			 EntMapProperties pro = entMapPropertiesService.findByFeature(featureId);
			 Map<String, String> stores = pro.getStores();
			 Iterator<String> it=stores.keySet().iterator();  
			 while (it.hasNext()) {
					String key; 
				    String value; 
				    key=it.next().toString(); 
				    value=stores.get(key); 
				    PropertyVo provo = new PropertyVo();
				    provo.setAttrkey(key);
				    provo.setAttrvalue(value);
				    entproperties.add(provo);
				}
			 map.put("entproperties", entproperties);
			 map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "获取矢量信息扩展值列表信息失败！");
		}
		return map;
	 }
	 
}
