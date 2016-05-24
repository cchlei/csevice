package com.trgis.trmap.account.web;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.trgis.resource.client.OssService;

/**
 * 企业文件控制器(上传、删除、缩略图等)
 * 
 * @author Alice
 *
 */
@Controller
@RequestMapping("/entfile")
public class EntFileController {

	private static final Logger logger = LoggerFactory.getLogger(EntFileController.class);
	
	@Autowired
	private OssService ossService;
	/**
	 * 上传
	 * @param request
	 * @param response
	 * @return
	 * 下载地址为 http://oss.trmap.cn/rs/download/拼接ossid
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method ={RequestMethod.POST, RequestMethod.GET})
	public String upload(HttpServletRequest request, HttpServletResponse response) {
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
				}
			}
		} catch (Exception e) {
			logger.debug("===上传失败，服务器未正确响应===");
			// TODO log日志 异常处理
			e.printStackTrace();
		}
		return id;
	}
	
}
