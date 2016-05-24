package com.trgis.trmap.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserTopicPermissionException;
import com.trgis.trmap.personal.service.UserTopicPermissionService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.userauth.dao.UserDAO;


@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class TestUserTopicPermission {
	@Autowired
	private UserTopicService userTopicService;
	@Autowired
	private UserTopicPermissionService userTopicPermissionService;
	@Autowired
	private UserDAO userDao;
	
	
	@Test
	public void testadd(){
		try {
			userTopicPermissionService.addPermission(16057l, 8l);
		} catch (UserTopicPermissionException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testList(){
		try {
			Long[] userId = new Long[2];
			userId[0] = 8l;
			userId[1] = 723l;
			userTopicPermissionService.addPermissions(16057l, userId );
		} catch (UserTopicPermissionException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testdel(){
		try {
			userTopicPermissionService.deletePermission(16057l, 8l);
		} catch (UserTopicPermissionException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testdels(){
		try {
			userTopicPermissionService.deleteByTopic(16057l);
		} catch (UserTopicPermissionException e) {
			e.printStackTrace();
		}
	}
}
