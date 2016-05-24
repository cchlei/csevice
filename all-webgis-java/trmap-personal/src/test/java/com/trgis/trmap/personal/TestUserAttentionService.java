package com.trgis.trmap.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.exception.UserAttentionException;
import com.trgis.trmap.personal.model.UserAttention;
import com.trgis.trmap.personal.service.UserAttentionService;
import com.trgis.trmap.personal.util.GroupAttentionVo;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserAttentionService {
	@Autowired
	private UserAttentionService userAttentionService;
	@Autowired
	private UserDAO userDao;
	
	
	/**
	 * @Description: 测试添加关注好友
	 * @author yanpeng
	 * @date 2016年3月25日 下午2:31:14
	 * @throws UserAttentionException
	 */
	@Test
	public void testSaveAttention() throws UserAttentionException{
		Long[] ids = new Long[]{3l,6l,5l,2l,70l,382l,456l,18144l,497l,921l,723l,15522l,743l,11238l,10893l};
		User cuser = userDao.findOne(8l);
		for (Long id : ids) {
			User buser = userDao.findOne(id);
			userAttentionService.saveAttention(cuser, buser,null);
		}
	}
	
	/**
	 * @Description: 测试取消关注好友
	 * @author yanpeng
	 * @date 2016年3月25日 下午2:31:30
	 * @throws UserAttentionException
	 */
	@Test
	public void testCancleAttention() throws UserAttentionException{
		User cuser = userDao.findOne(723l);
//		User cuser = userDao.findOne(743l);
		User buser = userDao.findOne(921l);
		userAttentionService.cancleAttention(723l, 921l);
	}
	
	/**
	 * @Description: 测试修改好友所属分组
	 * @author yanpeng
	 * @date 2016年3月25日 下午3:39:58
	 * @throws UserAttentionException
	 */
	@Test
	public void testUpdateGroup() throws UserAttentionException{
		userAttentionService.updateGroup(16666l, 723l, 921l);
	}
	
	/**
	 * @Description: 测试获取我的好友列表
	 * @author yanpeng
	 * @date 2016年3月28日 上午9:52:02
	 */
	@Test
	public void testGetAttentions(){
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("cuser", Operator.EQ, 921l));
		group.setConditions(conditions);
		int pageNum = 0;
		int pageSize = 8;
		OrderBy order = new OrderBy("occurtime", "desc");
		try {
			Page<UserAttention> findByConditions = userAttentionService.findByConditions(group, pageNum, pageSize,
					order);
			System.out.println(findByConditions.getSize());
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testGetAttentionNumber(){
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("cuser", Operator.EQ, 723l));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("occurtime", "desc");
		try {
			Long number = userAttentionService.getAttentionNumber(group);
			System.out.println("我的好友数量 ：" +number);
		} catch (Exception e) {
		}
	}
	@Test
	public void testGetFansNumber(){
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("buser", Operator.EQ, 15522l));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("occurtime", "desc");
		try {
			Long number = userAttentionService.getAttentionNumber(group);
			System.out.println("我的粉丝数量 ：" +number);
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * @Description: 测试获取我的粉丝列表
	 * @author yanpeng
	 * @date 2016年3月28日 上午9:57:36
	 */
	@Test
	public void testGetFans(){
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("buser", Operator.EQ, 743l));
		group.setConditions(conditions);
		int pageNum = 0;
		int pageSize = 8;
		OrderBy order = new OrderBy("occurtime", "desc");
		try {
			Page<UserAttention> findByConditions = userAttentionService.findByConditions(group, pageNum, pageSize, order);
			List<UserAttention> content = findByConditions.getContent();
			System.out.println(content.get(0).getAtteflag());
		} catch (UserAttentionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 测试模糊查询好友
	 * @author yanpeng
	 * @date 2016年3月30日 下午4:20:39
	 */
	@Test
	public void testGetUser(){
		try {
			List<Object[]> users = userAttentionService.findUsers(8l, null, 6);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for (Object[] objects : users) {
				map = new HashMap<String,Object>();
				map.put("id", objects[0]);
				map.put("name", objects[1]);
				map.put("covert", objects[2]);
				list.add(map);
			}
		} catch (UserAttentionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUsers(){
		try {
			Object[] attens = userAttentionService.getAttens(8l);
		} catch (UserAttentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getAttensAndGroup(){
		try {
			User user = userDao.findOne(8l);
			List<GroupAttentionVo> attensAndGroup = userAttentionService.getAttensAndGroup(user,null);
			System.out.println(attensAndGroup);
		} catch (UserAttentionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUsersByKey(){
		int pageNum = 0;
        int pageSize = 12;
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("status", Operator.EQ,UserStatus.ACTIVE));
		conditions.add(new SearchCondition("id", Operator.NEQ,8));
		conditions.add(new SearchCondition("username", Operator.LIKE,"a"));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("id", "asc");
		try {
			Page<User> findByConditions = userAttentionService.findUserByConditions(group, pageNum, pageSize,order);
			System.out.println(findByConditions.getTotalElements());
		} catch (UserAttentionException e) {
			e.printStackTrace();
		}
	}
}
