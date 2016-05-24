package com.trgis.trmap.qtuser.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.AccessRecordService;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.EnumUtil;

/**
 * 底图服务点击数量统计
 * @author Alice
 *
 * 2015年10月13日
 */
@Controller
@RequestMapping("/record")
public class RecordController {
	
	@Autowired
	private UserMapService userMapService;
	
	@Autowired
	private AccessRecordService recordService;
	
	
	/**
	 * 点击数量统计图
	 * @param mapId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value="/countByMapId/{mapid}")
	public Map<String,Object> countByMapId(@PathVariable("mapid") Long mapid){
		Map<String,Object> map = new HashMap<String,Object>();
		UserMap userMap = userMapService.findUserMapById(mapid);
		if(BeanUtil.isEmpty(userMap) || userMap.getIsshare()!=EnumUtil.SHARE.YFX.getValue()){
			map.put("result", "未发布该地图服务,请先发布该地图服务");
		}else{
			Map<String,Long> countmap = recordService.countByDaysAndId(userMap);
			List<String> x = new ArrayList<String>();
			List<Long> y = new ArrayList<Long>();
			Long totalNum = 0l;
			for(Map.Entry<String, Long>entity : countmap.entrySet()){
				totalNum += entity.getValue();
				x.add(entity.getKey());
				y.add(entity.getValue());
			}
			map.put("result", "success");
			map.put("x", x);
			map.put("y", y);
			map.put("mapname", userMap.getMapname());
			map.put("totalNum", totalNum);//总点击量
		}
		return map;
	}
}
