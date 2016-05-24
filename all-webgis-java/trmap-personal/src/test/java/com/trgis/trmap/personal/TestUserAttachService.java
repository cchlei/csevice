package com.trgis.trmap.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserAttachService {
	@Autowired
	private UserAttachService userAttachService;
	@Autowired
	private UserDAO userDAO;
    /**
     * @Description:测试增加
     * @Author liuyan 
     * @Date 2016年3月10日
     * @throws UserRecordException
     */
	@Test
	public void testCreateAttachRecord() throws UserRecordException{
		UserAttach userAttach = new UserAttach();
		userAttach.setAttachName("ceshi");
		UserRecord userRecord = new UserRecord();
		userRecord.setId(1l);
		userAttach.setUserRecord(userRecord);
		try {
			userAttachService.createAttach(userAttach);
		} catch (UserAttachException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * @Description:测试修改
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @throws UserRecordException
	 */
	@Test
	public void testupdateRecord() throws UserRecordException{
		//添加一个附件
		UserAttach userAttach = new UserAttach();
		userAttach.setAttachName("liuyanceshi");
		userAttach.setAttachSuffix("jpg");
		userAttach.setOssid("123456");
		try {
			userAttachService.createAttach(userAttach);
		} catch (UserAttachException e1) {
			e1.printStackTrace();
		}
		//得到一个事件的id
		Long id=16063l;
		try {
			//根据ossid修改附件的外键
			userAttachService.updateRecord(id, "123456");
		} catch (UserAttachException e) {
			e.printStackTrace();
		};
		
	}
	/**
	 * @Description:测试编辑
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @throws Exception
	 */
	@Test
	public void testeditUserAttach() throws Exception{
		//添加一个附件
		UserAttach userAttach = new UserAttach();
		userAttach.setAttachName("liuyanceshi");
		userAttach.setAttachSuffix("jpg");
		userAttach.setOssid("123456");
		try {
			userAttachService.createAttach(userAttach);
		} catch (UserAttachException e1) {
			e1.printStackTrace();
		}
		UserRecord userRecord =new UserRecord();
		userRecord.setId(16063l);
		userAttach.setUserRecord(userRecord);
		try {
			
			userAttachService.editUserAttach(userAttach);
		} catch (UserAttachException e) {
			e.printStackTrace();
		};
		
	}
	
	@Test
	public void testSpaceSize(){
		User user = userDAO.getOne(8l);
		try {
			Double spaceSize = userAttachService.getSpaceSize(user );
			System.out.println(spaceSize);
		} catch (UserAttachException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
