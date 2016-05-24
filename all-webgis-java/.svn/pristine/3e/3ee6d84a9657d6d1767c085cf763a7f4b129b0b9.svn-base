package com.trgis.trmap.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserCollectException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserCollect;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserCollectService;
import com.trgis.trmap.userauth.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserCollectService {
	@Autowired
	private UserCollectService userCollectService;

	@Test
	public void testGetCollectCount(){
		try {
			System.err.println(userCollectService.getCollectCount(2));
		} catch (UserCollectException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author chlei
	 * @Description: TODO 收藏专题
	 */
	@Test
	public void testAddCollect(){
			UserCollect userCollect=new UserCollect();
			
			//1,哪个专题
			UserTopic userTopic = new UserTopic();
			userTopic.setId(16057l);
			//2,收藏者 用户
//			String username = (String) SecurityUtils.getSubject().getPrincipal();
//			User user = userService.findUserByUsername(username);
			User user=new User();
			user.setId(10893L);  //user.getId()
			//3,添加到收藏
			userCollect.setTopic(userTopic);
			userCollect.setUser(user);
			userCollectService.addUserCollect(userCollect);
			
			
	}
	
	/**
	 * @author chlei
	 * @Description: TODO 取消收藏
	 */
	@Test
	public void testCancelCollect(){
			UserCollect userCollect=new UserCollect();
			
			//1,哪个专题
			UserTopic userTopic = new UserTopic();
			userTopic.setId(16057l);
			//2,收藏者 用户
//			String username = (String) SecurityUtils.getSubject().getPrincipal();
//			User user = userService.findUserByUsername(username);
			User user=new User();
			user.setId(10893L);  //user.getId()
			//3, 取消收藏
			userCollect.setTopic(userTopic);
			userCollect.setUser(user);
//			userCollectService.cancelUserCollect(userCollect);
			
			
	}
	
	
	/**
	 * @author chlei
	 * @Description: TODO 我的收藏
	 */
	@Test
	public void testCollect(){
			UserCollect userCollect=new UserCollect();
			
			//1,哪个专题
			UserTopic userTopic = new UserTopic();
			userTopic.setId(16057l);
			//2,收藏者 用户
//			String username = (String) SecurityUtils.getSubject().getPrincipal();
//			User user = userService.findUserByUsername(username);
			User user=new User();
			user.setId(10893L);  //user.getId()
			//3, 取消收藏
			userCollect.setTopic(userTopic);
			userCollect.setUser(user);
//			userCollectService.cancelUserCollect(userCollect);
			
			
	}
	
	/**
	 * @author chlei
	 * @Description: TODO 查询我的专题被收藏数
	 */
	@Test
	public void testGetUserTopicSubUserCollectCount(){
			//1,哪个专题
			UserTopic userTopic = new UserTopic();
			userTopic.setId(16057l);
			//2,被收藏的个数
			Long count=userCollectService.findUserCollectByUserTopic(userTopic.getId());
			System.out.println(count);
	}
	
	/**
	 * @Description: 测试修改我的收藏专题颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午3:21:14
	 */
	@Test
	public void testUpdateTopicColor(){
		try {
			userCollectService.updateTopicColor("#ff2b2b4b", 2l, 16057l);
		} catch (UserCollectException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUpdateTopicStatus(){
		try {
			userCollectService.updateSelectStatus(0, 1l, 2l);
		} catch (UserCollectException e) {
			e.printStackTrace();
		}
	}
}
