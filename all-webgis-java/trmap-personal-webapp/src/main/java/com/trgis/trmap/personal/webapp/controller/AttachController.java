package com.trgis.trmap.personal.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.trgis.resource.client.OssService;
import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 
 * @Title: AttachController
 * @Description: 附件上传控制器
 * @author liuyan
 * @date 2016年3月10日 上午10:02:25
 *
 */
@Controller
@RequestMapping("/attach")
public class AttachController {

	private static final Logger logger = LoggerFactory.getLogger(AttachController.class);

	@Autowired
	private OssService ossService;
	@Autowired
	private UserAttachService userAttachService;
	@Autowired
	private UserService userService;

	/**
	 * @Description:附件上传
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @param request
	 * @param response
	 * @return 返回一串串上传后的字符串，用id拼接","号分割
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result upload(HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		String coverurl = "";
		try {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			Map<String,Object> map = new HashMap<String,Object>();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				String id = null;
				if (file != null) {
					String attachname = file.getOriginalFilename();
					// 后缀
					String attachSuffix = attachname.substring(attachname.lastIndexOf(".")+1);
					// 上传文件到服务器
					id = ossService.uploadFile(file.getInputStream(), file.getOriginalFilename(), "qtmap");
					String username = (String) SecurityUtils.getSubject().getPrincipal();
					User user = userService.findUserByUsername(username);
					long attachSize = file.getSize();
					UserAttach userAttach = new UserAttach(id, attachname, attachSize, attachSuffix,EnumUtil.DELFLAG.NOMAL.getValue(), user);
					try {
						userAttachService.createAttach(userAttach);
						map.put("name",userAttach.getAttachName());
						map.put("ext",userAttach.getAttachSuffix());
						map.put("id",id);
					} catch (UserAttachException e) {
						e.printStackTrace();
					}
				}
				coverurl += id + ",";
			}
			coverurl =	coverurl.substring(0, coverurl.length()-1);//去掉最后一个，号
//			result.setData(coverurl);
			result.setData(map);
			result.setMsg("上传附件成功！");
			result.setStatus(Result.STATUS_OK);
		} catch (IOException e) {
			logger.debug("===上传附件失败，服务器未正确响应===");
			result.setData(null);
			result.setMsg("上传附件失败！");
			result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Description:删除用户文件
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @param fileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> deleteFile(@RequestParam("fileid") String fileId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userAttachService.deleteUserAttachByossid(fileId);
			map.put("status",Result.STATUS_OK);
			map.put("msg", fileId+"删除成功！");
			logger.debug("===删除成功！===");
		} catch (Exception e) {
			logger.debug("===删除失败！===");
			map.put("status", Result.STATUS_FAILED);
			map.put("msg", fileId+"删除失败！");
		}
		return map;
	}

}
