package com.trgis.trmap.personal;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserGroupException;
import com.trgis.trmap.personal.model.UserGroup;
import com.trgis.trmap.personal.service.UserGroupService;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserGroupService {
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserDAO userDao;
	
	/**
	 * @Description: 测试保存分组
	 * @author yanpeng
	 * @date 2016年3月25日 下午4:47:12
	 */
	@Test
	public void testSaveGroup(){
		User user = userDao.findOne(15522l);
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupName("wozai ceshi");
		userGroup.setUser(user);
		try {
			userGroupService.saveGroup(userGroup,user);
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 测试获取分组列表
	 * @author yanpeng
	 * @date 2016年3月25日 下午4:37:04
	 */
	@Test
	public void testGetGroups(){
		try {
			User user = userDao.findOne(15522l);
			List<UserGroup> groups = userGroupService.getGroups(user);
			System.out.println(groups.size());
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 测试删除
	 * @author yanpeng
	 * @date 2016年3月25日 下午4:51:15
	 */
	@Test
	public void testDeleteGroup(){
		try {
			userGroupService.deleteGroup(15522l, 16678l);
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 测试修改
	 * @author yanpeng
	 * @date 2016年3月25日 下午4:51:43
	 */
	@Test
	public void testUpdateGroup(){
		try {
			User user = userDao.findOne(15522l);
			List<UserGroup> groups = userGroupService.getGroups(user);
			for (UserGroup userGroup : groups) {
				userGroup.setGroupName("wozai ceshi xiugai");
				userGroupService.updateGroup(userGroup,user);
			}
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
	}
}
