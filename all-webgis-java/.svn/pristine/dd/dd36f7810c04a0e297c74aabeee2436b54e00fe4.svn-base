package test.com.trgis.qtmap.qtuser;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.common.util.DateUtil;
import com.trgis.trmap.qtuser.model.AccessRecord;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.AccessRecordService;
import com.trgis.trmap.qtuser.service.UserMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestServiceRecordService {
	@Autowired
	private AccessRecordService recordService;
	@Autowired
	private UserMapService userMapService;
	
	@Test
	public void createAccessRecord() {
		AccessRecord accessRecord  = new AccessRecord();
		accessRecord.setRequesttime(new Date());
		accessRecord.setRequestip("192.168.1.111");
		UserMap userMap = userMapService.findUserMapById(10L);
		accessRecord.setUserMap(userMap);
		recordService.save(accessRecord);
	}
	@Test
	public void testFindPageList() throws ParseException{
		
		// 组装第一条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);

		// 条件1
		String datetime = "2015-10-07";
		Date d = DateUtil.parseDate(datetime, DateUtil.PARSEPATTERN);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("requesttime", Operator.BETWEEN,d,DateUtil.getNextDay(d)));
		conditions.add(new SearchCondition("userMap.id", Operator.EQ, 207l));
		group.setConditions(conditions);
		long num = recordService.countByTodayAndId(group);
		System.out.println(num);
	}
	
	@Test
	public void countByDaysAndId(){
		UserMap usermap = userMapService.findUserMapById(336l);
		Map<String, Long> map = recordService.countByDaysAndId(usermap);
		System.out.println(map.size());
	}
}
