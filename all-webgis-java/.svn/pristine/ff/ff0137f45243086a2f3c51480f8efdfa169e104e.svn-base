package com.trgis.trmap.personal.webapp.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.gis.format.GeoJSON;
import com.trgis.common.gis.format.GeoJSONFactory;
import com.trgis.common.gis.geometry.Feature;
import com.trgis.common.util.CollectionUtils;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.StringHandle;
import com.trgis.trmap.personal.webapp.vo.FeatureVO;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 
 * @author Alice
 *
 * 2016年3月11日
 */
@Controller
@RequestMapping("/map")
public class MapController {

	@Autowired
	UserTopicService topicService;
	
	@Autowired
	UserRecordService recordService;

	@Autowired
	private UserService userService;

	@RequestMapping (value="/showView")
	public ModelAndView showView() {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("map/map");
		return modelAndView;
	}
	
	/**
	 * @author Alice
	 * @Title: featuresByTopic
	 * @Description: 查询专题记录显示在地图上
	 * @param topicid	专题id
	 * @param open		是否公开
	 * @param key		查询关键字
	 * @return			GeoJSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/featuresByTopic/{topicid}")
	public String featuresByTopic(@PathVariable("topicid") Long topicid, Integer open, String key) {
		Assert.notNull(topicid, "参数不能为空");
		UserTopic topic = null;
		GeoJSON featureCollection = new GeoJSON();
		try {
			topic = topicService.findUserTopicById(topicid);
		if (topic != null) {
			List<UserRecord> features = recordService.findByTopic(topic, EnumUtil.DELFLAG.NOMAL.getValue(), open, key);
			if (features != null && !features.isEmpty()) {
				List<FeatureVO> featurevos = CollectionUtils.copyBean(features, FeatureVO.class);
				for (FeatureVO feature : featurevos) {
					Feature featureGeometry = GeoJSONFactory.generateGJF(feature.getId(),feature.getGeom(), feature);
					if(featureGeometry == null) {
						continue;
					}
					featureCollection.add(featureGeometry);
				}
				features = null;
				featurevos = null;
				return GeoJSONFactory.convertGeoJSON(featureCollection);
			}
		}
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
		return  GeoJSONFactory.convertGeoJSON(featureCollection);
	}
	/**
	 * @author Alice
	 * @Title: featuresByTopic
	 * @Description: 查询记录显示在地图上
	 * @param recordid	记录id
	 * @return			GeoJSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/featuresByRecord/{recordid}")
	public String featuresByTopic(@PathVariable("recordid") Long recordid) {
		Assert.notNull(recordid, "参数不能为空");
		UserRecord record = null;
		GeoJSON featureCollection = new GeoJSON();
		try {
			record = recordService.findUserRecordById(recordid);
			if (record != null) {
					FeatureVO featurevo = CollectionUtils.copyBean(record, FeatureVO.class);
						Feature featureGeometry = GeoJSONFactory.generateGJF(featurevo.getId(),featurevo.getGeom(), featurevo);
						if(featureGeometry != null) {
							featureCollection.add(featureGeometry);
						}
					featurevo = null;
					return GeoJSONFactory.convertGeoJSON(featureCollection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  GeoJSONFactory.convertGeoJSON(featureCollection);
	}
	/**
	 * @author doris
	 * @Title: featuresByMonth
	 * @Description: 按月查询事件记录显示在地图上
	 * @param date 日期
	 * @param dire 选中状态
	 * @return	GeoJSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/month", method ={RequestMethod.POST, RequestMethod.GET})
	public String featuresByMonth(String date) {
		GeoJSON featureCollection = new GeoJSON();
		try {
			Date dateFromString = new Date();
			if (BeanUtil.isNotEmpty(date)) {
				 dateFromString = StringHandle.getDateFromString(date);
			}
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User user = userService.findUserByUsername(username);
			List<UserRecord> features = recordService.getListByMonth(dateFromString,user);
			if (features != null && !features.isEmpty()) {
				List<FeatureVO> featurevos = CollectionUtils.copyBean(features, FeatureVO.class);
				for (FeatureVO feature : featurevos) {
					Feature featureGeometry = GeoJSONFactory.generateGJF(feature.getId(),feature.getGeom(), feature);
					if(featureGeometry == null) {
						continue;
					}
					featureCollection.add(featureGeometry);
				}
				features = null;
				featurevos = null;
				return GeoJSONFactory.convertGeoJSON(featureCollection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  GeoJSONFactory.convertGeoJSON(featureCollection);
	}
}
