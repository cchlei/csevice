package com.trgis.trmap.personal;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;


@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class TestUserTopicService {
	@Autowired
	private UserTopicService userTopicService;
	@Autowired
	private UserDAO userDao;
	

	@Test
	public void testGetShareCount(){
		try {
			System.err.println(userTopicService.getShareCount(EnumUtil.SHAREFLAG.YFX.getValue()));
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGgetTopicCount(){
		try {
			Map<String, Long> topicCount = userTopicService.getTopicCount(2L);
			for (Map.Entry<String, Long> entry : topicCount.entrySet()) {
				System.out.println(entry.getKey()+"***"+entry.getValue());
			}
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateUserTopic(){
		try {
//			UserTopic userTopic = null;
			UserTopic userTopic = new UserTopic();
			userTopicService.createUserTopic(userTopic );
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author chlei
	 * @Description: TODO 删除专题
	 */
	@Test
	public void testDeleteUserTopic(){
				try {
					userTopicService.deleteUserTopic(16057L);
				} catch (UserTopicException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * @Description:测试查询标题名称  --测试通过
	 * @Author liuyan 
	 * @Date 2016年3月11日
	 */
	@Test
	public void testfindUserTopicname(){
		try {
			
		List<String> names = userTopicService.findUserTopicname();
		for(String name:names){
			System.out.println(name);
		}
		System.out.println(names.size());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 测试获取我的专题列表
	 * @author yanpeng
	 * @date 2016年3月11日 下午2:10:21
	 */
	@Test
	public void testFindAllUserTopic() {
		try {
			List<UserTopic> userTopic = userTopicService.findAllUserTopic(10893l);
			System.out.println(userTopic);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 测试获取我的收藏专题列表
	 * @author yanpeng
	 * @date 2016年3月11日 下午2:10:21
	 */
	@Test
	public void testfindAllUserTopicCollect() {
		try {
			List<UserTopic> userTopic = userTopicService.findAllUserTopicCollect(2l);
			System.out.println(userTopic);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有分享的专题
	 */
	@Test
	public void testGetShareTopic(){
		List<UserTopic> userTopicList=userTopicService.findShareUserTopic();
		for(UserTopic userTopic:userTopicList){
			System.out.println(userTopic.getDelflag()+"++++++++"+userTopic.getShareflag());
		}
	}
	/**
	 * @Description: 测试修改我的专题颜色
	 * @author yanpeng
	 * @date 2016年3月11日 下午3:21:14
	 */
	@Test
	public void testUpdateTopicColor(){
		try {
			userTopicService.updateTopicColor("#wobaichi", 16079l);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 测试修改我的专题状态
	 * @author yanpeng
	 * @date 2016年3月11日 下午6:08:45
	 */
	@Test
	public void testUpdateTopicStatus(){
		try {
			userTopicService.updateSelectStatus(1, 16087l);
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAttentionTopics(){
		User user = userDao.findOne(8l);
		try {
			List<UserTopic> attentionTopic = userTopicService.getAttentionTopic(user, EnumUtil.SHAREFLAG.YFX.getValue(), EnumUtil.DELFLAG.NOMAL.getValue());
			System.out.println();
		} catch (UserTopicException e) {
			e.printStackTrace();
		}
	}
}
