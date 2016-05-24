package test.trgis.qtmap.qtuser.userauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-*.xml" })
public class TestUser {

	@Autowired
	private UserService userService;

	/*
	 * 用户注册
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trgis.qtmap.userauth.service.UserServiceImpl#createUser(com.
	 * trgis.qtmap.qtuser.userauth.service.UserForm)
	 */
	@Test
	public void testCreateUser() throws AccountRegisterException {
		// 提交注册表单
		UserForm userForm = new UserForm();
		userForm.setName("赵四");
		userForm.setUsername("zhaosi");
		userForm.setMobile(Integer.toString(new Random().nextInt(13)));
		userForm.setEmail("bettysboy@trgis.com");
		userForm.setTextpassword("123456");
		User user = null;
		user = userService.createUser(userForm);
		userService.sendUserActiveEmail(user);
	}

	@Test
	public void testRandomCreateUser() throws AccountRegisterException {
		// 提交注册表单
		UserForm userForm = new UserForm();
		userForm.setName("张谦");
		userForm.setUsername("zhangqian");
		userForm.setMobile("18691865693");
		userForm.setEmail("bettysboy@trgis.com");
		userForm.setTextpassword("123456");
		User user = null;
		user = userService.createUser(userForm);
		userService.sendUserActiveEmail(user);
	}

	@Test
	public void testFindUser() {
		String username = "zhaosi";
		User user = userService.findUserByUsername(username);
		assertEquals(username, user.getUsername());
		username = "";
		user = userService.findUserByUsername(username);
		assertNull(user);
	}

	@Test
	public void testChangeUserStatus() {
		Long id = null;
		UserStatus status = null;
		String username = "zhaosi";
		User user = userService.findUserByUsername(username);
		id = user.getId();
		status = UserStatus.ACTIVE;
		userService.changeUserStatus(id, status);
	}

	@Test
	public void testChangeUserPass() {
		Long id = null;
		String username = "zhaosi";
		User user = userService.findUserByUsername(username);
		id = user.getId();
		String oldPass = "123456";
		String newPass = "654321";
		userService.changePass(id, oldPass, newPass);
	}

	@Test
	public void testFindAccount() {
		String email = "bettysboy@trgis.com";
		String mobile = "12";
		userService.findAccountFromMailAndMobile(email, mobile);
	}

	@Test
	public void testResetPass() {
		String email = "bettysboy@trgis.com";
		String mobile = "12";
		userService.findPassFromMailAndMobile(email, mobile);
	}

	@Test
	public void testValidationReset() {
		String securityuser = "2dd93395e8348af10862e90d85a306f4";
		String securitykey = "3c536d1a-7583-40db-a7e9-ee4470a0563d-e8def857c910135d8dbb56411f9eaa90";
		boolean result = userService.validationResetPass(securityuser, securitykey);
		assertEquals(result, true);
	}

	@Test
	public void testfindUserByPage() {
		// Map<String, Object> conds = null;
		// Sort order = new Sort("id", "asc");
		// int pageNum = 1;
		// int pageSize = 10;
		// Page<User> users = userService.findUserByPage(conds, order, pageNum,
		// pageSize);
		// List<User> list = users.getContent();
		// for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		// User user = (User) iterator.next();
		// System.out.println(user.getEmail());
		// }
	}

	@Test
	public void testFindCondition() throws ParseException {
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);

		// 条件1
		String startDate = "2013-09-30";
		String endDate = "2017-09-30";
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("status", Operator.EQ, UserStatus.INACTIVE));
		// conditions.add(new SearchCondition("createDate", Operator.BETWEEN,
		// DateUtil.parseDate(startDate, DateUtil.PARSEPATTERN),
		// DateUtil.parseDate(endDate, DateUtil.PARSEPATTERN)));
		group.setConditions(conditions);

		// 条件2为子条件组
		List<ConditionGroup> subConditions = new ArrayList<ConditionGroup>();

		// 创建第二组条件
		ConditionGroup group2 = new ConditionGroup();
		group2.setSearchRelation(SearchRelation.OR); // 设置条件关系
		List<SearchCondition> conditions2 = new ArrayList<SearchCondition>(); // 设置条件
		conditions2.add(new SearchCondition("name", Operator.LIKE, "张"));
		conditions2.add(new SearchCondition("email", Operator.LIKE, "张"));
		conditions2.add(new SearchCondition("username", Operator.LIKE, "张"));
		group2.setConditions(conditions2);// 将条件集合加入到第二组条件中

		// 第2组条件加入条件组
		subConditions.add(group2);

		// //条件3为子条件组
		// ConditionGroup group3 = new ConditionGroup();
		// group3.setSearchRelation(SearchRelation.AND); // 设置条件关系
		// List<SearchCondition> conditions3 = new
		// ArrayList<SearchCondition>();// 设置条件
		// String startDate = "2016-09-30";
		// String endDate = "2017-09-30";
		// conditions3.add(new SearchCondition("createDate", Operator.GTE,
		// DateUtil.parseDate(startDate, DateUtil.PARSEPATTERN)));
		// conditions3.add(new SearchCondition("createDate", Operator.LTE,
		// DateUtil.parseDate(endDate, DateUtil.PARSEPATTERN)));
		// group3.setConditions(conditions3);

		// 第3组条件加入条件组
		// subConditions.add(group3);

		// 条件子组装入条件总组
		group.setSubConditions(subConditions);

		OrderBy order = new OrderBy("createDate", "desc");
		Page<User> users = userService.findByConditions(group, 0, 10, order);

		System.out.println(users.getContent().size());
		
		for (Iterator<User> iterator = users.getContent().iterator(); iterator.hasNext();) {
			User user = iterator.next();
			System.out.println(user.getName());
		}
	}

}