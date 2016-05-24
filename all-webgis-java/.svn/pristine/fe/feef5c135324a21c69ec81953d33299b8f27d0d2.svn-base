package com.trgis.trmap.qtuser.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.resource.client.OssService;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.utils.ConvertJSON;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 附件上传控制器
 * 
 * @author Alice
 *
 */
@Controller
@RequestMapping("/attachfile")
public class AttachfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	@Autowired
	private OssService ossService;
	@Autowired
	private AttachfileService attachfileService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/attachCount", method = RequestMethod.GET)
	public String attachCount() {
		return "statistics/attachcount";
	}

	/**
	 * 附件上传
	 * 
	 * Ajax
	 * @param <HttpServletResponse>
	 * 
	 * @param request
	 * @param session
	 * @param mapGraphicsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public <HttpServletResponse> String upload(HttpServletResponse request, HttpServletResponse response) {
		String id = "";
		try {
			// 将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 上传文件到服务器
					id = ossService.uploadFile(file.getInputStream(), file.getOriginalFilename(), "qtmap");
					// 暂时用名字代替，减少服务器垃圾
					String username = (String) SecurityUtils.getSubject().getPrincipal();
					User user = userService.findUserByUsername(username);
					String attachStr=getSizeByte(file.getSize());
					Attachfile attachfile = new Attachfile(null,null,file.getOriginalFilename(), file.getContentType(),
							null, id, file.getSize(),attachStr, user.getId(), new Date(), "");
					attachfileService.saveAttachfile(attachfile);
				}
			}
		} catch (IOException e) {
			logger.debug("===上传附件失败，服务器未正确响应===");
			// TODO log日志 异常处理
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 附件分页列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attachList", method = { RequestMethod.POST, RequestMethod.GET })
	public String attachList(Long draw, String searchText, String filterName, Integer start, Integer length,
			String column, String dir) {
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 条件1关键字
		String attachName = searchText;
		if (StringUtils.isNotBlank(attachName)) {
			conditions.add(new SearchCondition("attachName", Operator.LIKE, attachName));
		}
		// 条件2用户
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findUserByUsername(username);
		conditions.add(new SearchCondition("uploadUserId", Operator.EQ, user.getId()));
		conditions.add(new SearchCondition("mapGraphics", Operator.ISNOTNULL, null));
		// 条件3种类
		// String attachType = request.getParameter("attachType");
		// if(StringUtils.isNotBlank(attachType)){
		// int type = Integer.parseInt(attachType);
		// if(type != EnumUtil.ATTACHTYPE.ALL.getValue()){
		// conditions.add(new SearchCondition("attachType", Operator.EQ, type));
		// }
		// }
		group.setConditions(conditions);

		// 页码
		if (start == null) {
			start = 0;
		}
		if (length == null) {
			length = 10;
		}
		int pageNum = (start / length) + 1;

		// 排序
		OrderBy order = null;
		if (StringUtils.isNotBlank(column)) {
			order = new OrderBy(column, dir);
		}else{
			order = new OrderBy("uploadTime", "desc");
		}
		Page<Attachfile> attachfile = attachfileService.findByConditions(group, pageNum, length, order);
		try {
			return ConvertJSON.convert2DataTables(draw, attachfile);
		} catch (JsonProcessingException e) {
			logger.debug("===JSON数据转换失败.返回空字符串===", e);
			return "";
		}
	}

	/**
	 * 删除用户文件
	 * 
	 * @param fileId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> deleteFile(@RequestParam("fileId") String fileId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long id = Long.valueOf(fileId);
			attachfileService.deleteAttachfile(id);
			map.put("result", "success");
			map.put("msg", "删除单个附件成功！");
			// TODO 删除oss云端存贮，以后实现
		} catch (Exception e) {
			logger.debug("===删除单个附件失败！===");
			map.put("result", "error");
			map.put("msg", "删除单个附件失败！");
		}
		return map;
	}

	/**
	 * 删除多个用户文件
	 * 
	 * @param fileId
	 * @param out
	 */
	@RequestMapping(value = "/deleteFiles", method = { RequestMethod.GET, RequestMethod.POST })
	public void deleteFiles(@RequestParam("fileIds") String fileIds, PrintWriter out) {
		try {
			if (StringUtils.isBlank(fileIds)) {
				String[] ids = fileIds.split(",");
				for (String s : ids) {
					if (StringUtils.isBlank(s))
						attachfileService.deleteAttachfile(Long.parseLong(s));
					out.print("{\"result\":\"success\",\"msg\":\"删除单个附件成功！\"}");
					// TODO 删除oss云端存贮，以后实现
				}
			}
		} catch (Exception e) {
			logger.debug("===删除附件失败！===");
			out.print("{\"result\":\"error\",\"msg\":\"删除附件失败！\"}");
		} finally {
			out.flush();
			out.close();
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

	/**
	 * 统计当前用户的矢量数据
	 * 
	 * @param gid
	 */
	@ResponseBody
	@RequestMapping(value = "/countAttach", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> countAttach() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// TODO 根据用户等级应该有的条数，暂时设置为500条
			Integer totalCount = 20;
			// 已使用条数
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			Double usedCount = attachfileService.getUsedDataSpace(user.getId());
			map.put("result", "success");
			map.put("totalCount", totalCount);
			map.put("usedCount", usedCount);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "获取用户矢量数据失败！");
		}
		return map;
	}
}
