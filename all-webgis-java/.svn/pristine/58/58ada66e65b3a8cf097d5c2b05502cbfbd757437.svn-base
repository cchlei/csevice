package com.trgis.trmap.enterprise.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;

/**
 * 扩展属性表结构（列结构）Controller
 * @author chlei
 * @2015年12月7日下午6:30:03
 */
@Controller
@RequestMapping("/entattributemeta")
public class EntAttributemetaController {
	@Autowired
	private EntAttributemetaService epAttributemetaService;
	@Autowired
	private EntLayermetaService epLayermetaService;
	@Autowired
	private EntMapService epMapService;
	//增加 扩展属性表结构（列结构）信息
	@ResponseBody
	@RequestMapping(value="/addattributemeta", method = {RequestMethod.POST, RequestMethod.GET})
	public Map<String, Object> addEntAttributemeta(Long mapid,String field,String title,String type){
			String	attralias=title,attrcode=field ,attrtype=type;//名字
			Map<String, Object> map = new HashMap<String, Object>();
			if(BeanUtil.isNotEmpty(mapid)){
				EntUserMap entUserMap=epMapService.findUserMapById(mapid);
				if(BeanUtil.isNotEmpty(entUserMap)){
					EntLayermeta entLayermeta=epLayermetaService.findByEntUserMap(entUserMap);
					 if(BeanUtil.isNotEmpty(entLayermeta.getLayermetaid())){
						 //保存layermeta 把 EntUserMap 中的ID取出保存进来
						 EntAttributemeta entAttributemeta=new EntAttributemeta();
						 entAttributemeta.setAttralias(attralias);//属性名称
						 entAttributemeta.setAttrcode(attrcode);//属性编码
						 entAttributemeta.setAttrtype(attrtype);//属性类型(默认为文本型)
						 entAttributemeta.setLayermeta(entLayermeta);
						 epAttributemetaService.createEnterpriseAttributemeta(entAttributemeta);
						 map.put("result", "success");
						 map.put("id",entAttributemeta.getAttrid());//主键
						 map.put("msg", "保存扩展属性成功！");
					 }else{
						 map.put("result", "error");
						 map.put("msg", "保存扩展属性失败！");
					 }
				}else{
					 map.put("result", "error");
					 map.put("msg", "保存扩展属性失败！");
				}
			}else{
				 map.put("result", "error");
				 map.put("msg", "保存扩展属性失败！");
			}
			
			return map;
	 }
	
	/**
	 * 删除 扩展属性表结构（列结构）信息 
	 * @param attrid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public Map<String, Object> deleteEntAttributemeta(Long mapid,Long aid){
				Long attrid=aid;
				Map<String, Object> map = new HashMap<String, Object>();
					 if(BeanUtil.isNotEmpty(attrid)){
						//通过前台传来的id 删除其EntAttributemeta
						epAttributemetaService.deletByEntAttributemeta(attrid);
						map.put("result", "success");
						map.put("id",attrid);//主键
						map.put("msg", "删除扩展属性列结构成功！");
					 }else{
						 map.put("result", "error");
						 map.put("id",attrid);//主键
						 map.put("msg", "删除扩展属性列结构失败！");
					 }
				return map;
	}
	/**
	  * @author Administrator chenhl
	  * @param id
	  * @return JSON格式字符串	将类对象包装成JSON格式 ： 
	  * {
		  "fields" :[
		    {"field":"sq",        "title":"编号"},
		    {"field":"name",      "title":"名称"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"no",        "title":"数值字段"},
		    {"field":"date",      "title":"时间"}
		  ]
		},
	  */
	 //根据 图层对应的id 获得扩展属性 并且直接把基本属性 合并成 列表头 
	 @ResponseBody// 把map返回到页面
	 @RequestMapping(value="/getEntAttributemetaListField",method = {RequestMethod.POST, RequestMethod.GET})
	 public Map<String, Object>  getEntAttributemetaListField(Long mapid){
		 List<Map<String , Object>> attributemetaListField=new ArrayList<Map<String , Object>>();
		 Map<String, Object> map = null;
		 try {
			 //基本属性
			 map = new HashMap<String, Object>();
			 map.put("field", "sq");
			 map.put("title", "编号");//这是基本 图层矢量属性
			 map.put("type", "2");//整数
			 map.put("fixed", "1");//固定值
			 map.put("hidden", "1");//修改矢量
			 map.put("id", "sq");//扩展属性对应的id
			 attributemetaListField.add(map);
			 map = new HashMap<String, Object>();
			 map.put("field", "gname");
			 map.put("title", "名称");//基本 图层矢量属性
			 map.put("type", "1");//文本
			 map.put("fixed", "1");//固定值
			 map.put("id", "gname");//扩展属性对应的id
			 attributemetaListField.add(map);
			 map = new HashMap<String, Object>();
			 map.put("field", "date");
			 map.put("title", "时间");//基本 图层矢量属性
			 map.put("type", "4");//日期
			 map.put("fixed", "1");//固定值
			 map.put("hidden", "1");//修改矢量
			 map.put("id", "date");//扩展属性对应的id
			 attributemetaListField.add(map);
			 //扩展属性
			 EntUserMap entUserMap=epMapService.findUserMapById(mapid);
			 EntLayermeta entLayermeta=epLayermetaService.findByEntUserMap(entUserMap);
			 List<EntAttributemeta> entAttributemetaList=epAttributemetaService.findByEntLayermeta(entLayermeta);//更具对象获得对应的扩展属性
			 for(EntAttributemeta entAttributemeta:entAttributemetaList){
				 map = new HashMap<String, Object>();
				 map.put("field", entAttributemeta.getAttrcode());//扩展属性编码
				 map.put("title", entAttributemeta.getAttralias());//扩展属性对应的列名
				 map.put("type", entAttributemeta.getAttrtype());//扩展属性对应的类别
				 map.put("id", entAttributemeta.getAttrid());//扩展属性对应的id
				 attributemetaListField.add(map);
			 }
			 map = new HashMap<String, Object>();
			 map.put("fields", attributemetaListField);
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "获取矢量信息失败！");
		}
		 return map;
	 }
	 
	 @RequestMapping (value="/showView")
	 public ModelAndView showView() {
		 ModelAndView modelAndView=new ModelAndView();
		 modelAndView.setViewName("map/map_creater");
		return modelAndView;
	 }
	 	
}
