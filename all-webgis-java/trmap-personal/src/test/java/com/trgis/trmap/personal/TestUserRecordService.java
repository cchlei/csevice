package com.trgis.trmap.personal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.util.EnumUtil;
/**
 * 记录测试类  
 * @author liuyan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserRecordService {
	@Autowired
	private UserRecordService userRecordService;

	@Test
	public void testGetShareCount() throws UserRecordException{
		System.err.println(userRecordService);
	}
	
   
	/**
	 * @author liuyan
	 * 增加
	 */
	@Test
	public void testCreateUserTopic(){
		try {
			UserRecord userRecord = new UserRecord();
			userRecord.setTitle("我就是试试好了没");
			userRecord.setAddrname("太上老君");
			userRecord.setOccurtime(new Date());
			userRecord.setCreatime(new Date());
			userRecordService.createUserRecord(userRecord );
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author chlei
	 * @Description:  查询专题记录数
	 */
	@Test
	public void testGetUserTopicSubUserRecordCount(){
			UserTopic userTopic = new UserTopic();
			userTopic.setId(16057l);
			Integer delflag=EnumUtil.DELFLAG.NOMAL.getValue();
			Long count=userRecordService.findUserRecordByUserTopic(userTopic.getId(),delflag);
			System.out.println(userTopic.getId());
			System.out.println(count);
	}
	/**
	 * 修改
	 * @author yanpeng
	 */
	@Test
	public void testEditUserRecord(){
		try {
			UserRecord userRecord = userRecordService.findUserRecordById(1l);
			userRecord.setTitle("我就是试试好了没");
			userRecord.setAddrname("太上老君");
			userRecordService.editUserRecord(userRecord);
			System.out.println();
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author yanpeng
	 * 删除
	 */
	@Test
	public void testDeleteUserRecord(){
		try {
			userRecordService.deleteUserRecord(16064l);
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 测试获取相邻月数据
	 * @author yanpeng
	 * @date 2016年3月15日 下午4:00:02
	 */
	@Test
	public void testGetNextMonth(){
//		try {
//			List<UserRecord> listByDay = userRecordService.getListNextMonth(new Date(), 0);
//			System.out.println(listByDay.size());
//		} catch (UserRecordException e) {
//			e.printStackTrace();
//		}
	}
}
