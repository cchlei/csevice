package com.trgis.trmap.qtuser.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.qtuser.dao.AccessRecordDao;
import com.trgis.trmap.qtuser.exception.MapGraphicsException;
import com.trgis.trmap.qtuser.model.AccessRecord;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.AccessRecordService;
import com.trgis.trmap.qtuser.utils.BeanUtil;

/**个人地图访问
 * 
 * @author Alice
 *
 * 2015年10月7日
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccessRecordServiceImpl implements AccessRecordService{
	private static final Log log = LogFactory.getLog(AccessRecordServiceImpl.class);
    @Autowired
    private AccessRecordDao serviceRecordDao;
    
	@Override
	public void save(AccessRecord s) {
		try {
			if (BeanUtil.isEmpty(s)) {
				throw new MapGraphicsException("个人地图访问记录不能为空！");
			}
			serviceRecordDao.save(s);
			log.debug("个人地图访问记录保存成功！");
		} catch (Exception e) {
			log.debug("个人地图访问记录保存失败！");
			throw new MapGraphicsException("个人地图访问记录保存失败！");
		}
	
	}
	
	/**
	 * 按天查询点击数量汇总（统计用）
	 */
	@Override
	public Map<String, Long> countByDaysAndId(UserMap userMap) {
		//查询map被点击的数据
		List<Object[]> list = serviceRecordDao.countByDaysAndId(userMap);
		Map<String,Long> resultMap = new TreeMap<String, Long>();
		for (Object[] objects : list) {
			String time = DateUtil.dateToString((Date)objects[0], DateUtil.PARSEPATTERN);
			Long count = (Long)objects[1];
			resultMap.put(time, count);
		}
		return resultMap;
	}
	/**
	 * 记录这个时间已经访问的数次（一般按天计算，time格式为yyyy-MM-dd）
	 */
	@Override
	public long countByTodayAndId(ConditionGroup conditionGroup) {
		// TODO Auto-generated method stub
		Specification<AccessRecord> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long countnum = serviceRecordDao.count(specifications);
		return countnum;
	}
}
