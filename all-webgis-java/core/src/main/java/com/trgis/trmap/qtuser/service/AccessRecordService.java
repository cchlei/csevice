package com.trgis.trmap.qtuser.service;

import java.util.Map;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.trmap.qtuser.model.AccessRecord;
import com.trgis.trmap.qtuser.model.UserMap;

/**
 * 用户地图访问
 * @author Alice
 *
 * 2015年10月7日
 */
public interface AccessRecordService {
	/**
	 * 按天统计数据
	 * @param mapid
	 * @return
	 */
	public Map<String,Long> countByDaysAndId(UserMap userMap);
	/**
	 * 单个地图服务当天点击数
	 * @param service_id	
	 * @param time
	 * @return
	 */
	public long countByTodayAndId(ConditionGroup conditionGroup);
	/**
	 * 新增记录
	 * @param serviceRecord
	 */
	public void save(AccessRecord serviceRecord);
}
