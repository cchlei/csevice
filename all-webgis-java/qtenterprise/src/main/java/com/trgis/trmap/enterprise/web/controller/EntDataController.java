package com.trgis.trmap.enterprise.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.util.JSONUtils;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.service.EntDataService;

/**
 * 企业账号获取数据
 * 
 * @author zhangqian
 *
 */
@Controller
@RequestMapping(value = "/data")
public class EntDataController {
	
	private static final String JSONP = "%s(%s)";

	@Autowired
	private EntDataService epDataService;

	/**
	 * 使用权限为企业级用户
	 * 
	 * @param mapId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/graphics", method = RequestMethod.POST)
	public String getMapData(String token, Long mapId) {
		List<EntMapGraphics> graphics = epDataService.findMapGraphics(mapId);
		return JSONUtils.convertList(graphics);
	}

	@ResponseBody
	@RequestMapping(value = "/graphics", method = RequestMethod.GET)
	public String getMapDataJsonp(String token, Long mapId, String callback) {
		List<EntMapGraphics> graphics = epDataService.findMapGraphics(mapId);
		return String.format(JSONP, callback,JSONUtils.convertList(graphics));
	}

}
