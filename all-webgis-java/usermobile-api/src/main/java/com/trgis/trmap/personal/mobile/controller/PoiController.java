package com.trgis.trmap.personal.mobile.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.jwt.SignedJWT;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.resource.client.OssService;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.userauth.jwt.JsonWebTokenService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 档案接口
 * 
 * @author Alice
 *
 */
@Controller
@RequestMapping(value = "/poi")
public class PoiController {

	private static final Logger logger = LoggerFactory.getLogger(PoiController.class);
	
	@Autowired
	private OssService ossService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapService userMapService;
	@Autowired
	private MapGraphicsService mapGraphicsService;
	@Autowired
	private AttachfileService attachfileService;
	
	/**
	 * 移动端分页列表
	 * 
	 * 访问地址：http://localhost:8080/poi/list?jwt=M&pageNo=1&pageSize=10
	 * 
	 * 其中jwt的值需要调用localhost:8080/mobile/login?username=Alice&password=123456返回
	 * 
	 * @param jwt		当前用户成功登录票据
	 * @param pageNo	当前页	从0开始
	 * @param pageSize	分页单位	默认10
	 * 
	 * @return	返回结构 "{\"result\":\"%s\",\"msg\":\"%s\"}"
	 * 
	 * 成功result为"success",msg为当前页数据
	 * 失败result为"error",msg是错误信息
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(String jwt, Integer pageNo, Integer pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			//校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			}else{
				String username = JsonWebTokenService.getUsername(jwt);
				logger.debug(String.format("当前查询第：%s页，每页：%s条", pageNo, pageSize));
				ConditionGroup group = new ConditionGroup();
				group.setSearchRelation(SearchRelation.AND);
				List<SearchCondition> conditions = new ArrayList<SearchCondition>();
				User user = userService.findUserByUsername(username);
				conditions.add(new SearchCondition("userMap.user.id", Operator.EQ, user.getId()));
				conditions.add(new SearchCondition("userMap.app", Operator.EQ, true));
				conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
				group.setConditions(conditions);
				if (BeanUtil.isEmpty(pageNo)) {
					pageNo = 0;
				}
				if (BeanUtil.isEmpty(pageSize)) {
					pageSize = 10;
				}
				OrderBy order = new OrderBy("gcreatedate", "DESC");
				Page<MapGraphics> mapGraphic = mapGraphicsService.findByConditions(group, pageNo, pageSize, order);
				//处理返回json,只保留需要部分
				List<POI> volist = new ArrayList<POI>();
				if(BeanUtil.isNotEmpty(mapGraphic)){
					List<MapGraphics> list = mapGraphic.getContent();
					for (MapGraphics g : list) {
						List<String> filelist = attachfileService.getOssidByGraphicsId(g);
						POI poi = new POI(g.getId(), g.getGname(), g.getGstyle(), DateUtil.dateToString(g.getGcreatedate(), "yyyy-MM-dd"), DateUtil.dateToString(g.getGcreatedate(), "HH:mm"), g.getGeom(), g.getDateID()==null?"":g.getDateID(), filelist);
						volist.add(poi);
					}
				}
				result.put("result", "success");
				result.put("msg", volist);
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		}
	}
	
	/**
	 * 档案详情接口
	 * 
	 * 访问地址：http://localhost:8080/poi/viewinfo?jwt=M&id=21
	 * 
	 * 其中jwt的值需要调用localhost:8080/mobile/login?username=Alice&password=123456返回
	 * http://oss.trmap.cn/thumb/80/02188826-1323-4ba0-bc01-d680517a6aaf-d1452489979402
	 * http://oss.trmap.cn/thumb/200_200/02188826-1323-4ba0-bc01-d680517a6aaf-d1452489979402
	 * 
	 * @param jwt	当前用户成功登录票据
	 * @param id	查询的档案id
	 * 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/viewinfo")
	public Map<String, Object> viewinfo(String jwt, Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			//校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			}else{
				logger.debug(String.format("查询id为：%s的档案详情", id));
				MapGraphics g = mapGraphicsService.findById(id);
				//附件集合
				List<String> filelist = attachfileService.getOssidByGraphicsId(g);
				//简化成POI数据结构返回
				POI poi = new POI(g.getId(), g.getGname(), g.getGstyle(), DateUtil.dateToString(g.getGcreatedate(), "yyyy-MM-dd"), DateUtil.dateToString(g.getGcreatedate(), "HH:mm"), g.getGeom(), g.getDateID(), filelist);
				result.put("result", "success");
				result.put("msg", poi);
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		}
	}
	
	/**
	 * 档案同步上传接口
	 * 
	 * 访问地址：http://localhost:8080/poi/upload?jwt=M&……
	 * 
	 * @param jwt		当前用户成功登录票据
	 * @param title		新增档案的标题
	 * @param describe	档案的描述
	 * @param geom		wkt坐标
	 * @param files		上传图片集合
	 * 
	 * @return 返回结构 "{\"result\":\"%s\",\"msg\":\"%s\"}"
	 * 
	 * 成功result为"success",msg为成功入库的id
	 * 失败result为"error",msg是错误信息
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public Map<String, Object> upload(String jwt, String title, String describe, String geom, String dateID, @RequestParam("files") MultipartFile[] files) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			//校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
			}else{
				MapGraphics g = new MapGraphics();
				g.setGname(title);
				g.setGstyle(describe);
				g.setGeom(geom);
				g.setDateID(dateID);
				//用户及当前图层(用户注册移动端时生成移动端专有用户图层)
				String username = JsonWebTokenService.getUsername(jwt);
				User user = userService.findUserByUsername(username);
				UserMap usermap = userMapService.findUserAppMap(user, true);
				g.setUserMap(usermap);
				mapGraphicsService.save(g);
				//附件集合
				for (MultipartFile file : files) {
					if (file != null) {
						// 上传文件到服务器
						String id = ossService.uploadFile(file.getInputStream(), file.getOriginalFilename(), "qtmap");
						String attachStr = getSizeByte(file.getSize());
						Attachfile attachfile = new Attachfile(null,g,file.getOriginalFilename(), file.getContentType(),
								null, id, file.getSize(),attachStr, user.getId(), new Date(), "");
						attachfileService.saveAttachfile(attachfile);
					}
				}
				logger.debug(String.format("添加档案成功id为：%s", g.getId()));
				result.put("result", "success");
				result.put("msg", g.getId());
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
		} catch (Exception e) {
			logger.debug("添加档案失败", e);
			result.put("result", "error");
			result.put("msg", "添加档案失败");
		}
		return result;
	}
	
	/////////////////////////////////////////////////////////////////
	//以下为同步下载接口，提供两种实现方法
	//一种带进度（需要交互多次，先访问allid方法连接，返回所有id，然后算出移动端未同步条数，再访问viewinfo方法连接进行单个同步）
	//一种不带进度（一次交互）访问download方法连接，返回所有档案，逐条根据id查询是否需要在本地同步
	//供移动端根据实际情况选择
	
	/**
	 * 获取所有档案id接口
	 * 
	 * 访问地址：http://localhost:8080/poi/allid?jwt=M
	 * 
	 * @param jwt	当前用户成功登录票据
	 * 
	 * @return 返回当前用户所有档案id集合，需要移动端根据id判断移动端库是否已经同步过,然后再次根据未同步的id请求下载
	 */
	@ResponseBody
	@RequestMapping(value = "/allid")
	public Map<String, Object> allid(String jwt) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			//校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			}else{
				String username = JsonWebTokenService.getUsername(jwt);
				//根据用户查找注册时产生的移动端专有map
				User user = userService.findUserByUsername(username);
				UserMap map = userMapService.findUserAppMap(user, true);
				List<MapGraphics> list = mapGraphicsService.findByUserMap(map);
				List<Long> ids = new ArrayList<Long>();
				for (MapGraphics mapGraphics : list) {
					ids.add(mapGraphics.getId());
				}
				logger.debug(String.format("查询用户：%s的所有档案id集合", username));
				result.put("result", "success");
				result.put("msg", ids);
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		}
	}
	
	/**
	 * 档案恢复接口
	 * 
	 * 访问地址：http://localhost:8080/poi/download?jwt=M
	 * 
	 * @param jwt	当前用户成功登录票据
	 * 
	 * @return 返回当前用户所有档案集合，需要移动端根据id判断移动端库是否已经同步过
	 */
	@ResponseBody
	@RequestMapping(value = "/download")
	public Map<String, Object> download(String jwt) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SignedJWT parseJWT = SignedJWT.parse(jwt);
			//校验jwt
			if (!JsonWebTokenService.checkDomainAndTime(parseJWT)) {
				logger.debug("[JWT]失效");
				result.put("result", "error");
				result.put("msg", "jwt失效");
				return result;
			}else{
				String username = JsonWebTokenService.getUsername(jwt);
				//根据用户查找注册时产生的移动端专有map
				User user = userService.findUserByUsername(username);
				UserMap map = userMapService.findUserAppMap(user, true);
				List<MapGraphics> list = mapGraphicsService.findByUserMap(map);
				//简化成POI数据结构返回
				List<POI> poilist = new ArrayList<POI>();
				for (MapGraphics g : list) {
					//附件集合
					List<String> filelist = attachfileService.getOssidByGraphicsId(g);
					POI poi = new POI(g.getId(), g.getGname()==null?"":g.getGname(), g.getGstyle()==null?"":g.getGstyle(), 
							DateUtil.dateToString(g.getGcreatedate(), "yyyy-MM-dd"), DateUtil.dateToString(g.getGcreatedate(), "HH:mm"), 
							g.getGeom()==null?"":g.getGeom(), g.getDateID()==null?"":g.getDateID(), filelist);
					poilist.add(poi);
				}
				logger.debug(String.format("查询用户：%s的所有档案集合", username));
				result.put("result", "success");
				result.put("msg", poilist);
				return result;
			}
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
			result.put("result", "error");
			result.put("msg", "jwt出错");
			return result;
		} catch (Exception e) {
			logger.debug("同步下载出错", e);
			result.put("result", "error");
			result.put("msg", "同步下载出错");
			return result;
		}
	}
	
	public class POI{  
		private Long id;
		private String title;
		private String describe;
		private String date;
		private String time;
		private String geom;
		private String dateID;
		private List<String> attachment;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getGeom() {
			return geom;
		}
		public void setGeom(String geom) {
			this.geom = geom;
		}
		public List<String> getAttachment() {
			return attachment;
		}
		public void setAttachment(List<String> attachment) {
			this.attachment = attachment;
		}
		public String getDateID() {
			return dateID;
		}
		public void setDateID(String dateID) {
			this.dateID = dateID;
		}
		public POI(Long id, String title, String describe, String date, String time, String geom, String dateID,
				List<String> attachment) {
			super();
			this.id = id;
			this.title = title;
			this.describe = describe;
			this.date = date;
			this.time = time;
			this.geom = geom;
			this.dateID = dateID;
			this.attachment = attachment;
		}
	} 
	/**
	 * 文件大小
	 * 
	 * @param fileSize
	 * @return
	 */
	private String getSizeByte(long fileSize) {
		String size = "";
		BigDecimal bg = new BigDecimal(fileSize / 1024.00);
		if (fileSize < 1024) {
			size = fileSize + "B";
		} else if (fileSize >= 1024 && fileSize < 1048576) {
			size = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "K";
		} else if (fileSize >= 1048576 && fileSize < 1073741824) {
			size = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "M";
		} else if (fileSize >= 1073741824 && fileSize < 1099511627776l) {
			size = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "G";
		}
		return size;
	}
}
