package test.trgis.qtmap.qtuser.userauth;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.EnterpriseUserService;
import com.trgis.trmap.userauth.service.UserForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-*.xml" })
public class TestUserEnter {
	@Autowired
	private EnterpriseUserService entUserService;
	private UserStatus status = UserStatus.ACTIVE;
	@Test
	public void testCreateUser() throws AccountRegisterException {
		// 提交注册表单
		UserForm userForm = new UserForm();
		userForm.setName("liuyan");
		userForm.setUsername("liuyan");
		userForm.setMobile(Integer.toString(new Random().nextInt(13)));
		userForm.setEmail("1164739892@qq.com");
		userForm.setTextpassword("123456");
		userForm.setGender("女");
		User user = new User();
		try {
			BeanUtils.copyProperties(user, userForm);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
        user = entUserService.createUser(userForm);
		entUserService.sendUserActiveEmail(user);
	}
	  @Test 
	  public void testEditUser() throws AccountRegisterException{
		    UserForm userForm = new UserForm();
			userForm.setName("刘艳大坏蛋");
			userForm.setUsername("huaidan");
			userForm.setMobile(Integer.toString(new Random().nextInt(13))+Integer.toString(new Random().nextInt(13)));
			userForm.setEmail("5555555@qq.com");
			userForm.setTextpassword("123456");
			userForm.setGender("女");
		    entUserService.editUser(756l,userForm);
	}
	  @Test
	  public void testFindUserByEmail() {
			String email = "1164739892@qq.com";
		   	User user =	entUserService.findUserByEmail(email);
		   	System.out.println(user.getName());
			}
	  
	 @Test 
	  public void testChangeUserStatus() throws AccountRegisterException{
		    
		    entUserService.changeUserStatus(2123l,status);
	  }
	  
	  @Test 
	  public void testChangePass() throws AccountRegisterException{
		    Long id = null;
			String username = "liuyan";
			User user = entUserService.findUserByUsername(username);
			id = user.getId();
		   String oldPass = "654321";
		   String newPass = "123456";
		   entUserService.changePass(id, oldPass, newPass);
	   }
	  @Test 
	  public void testFindPassFromMailAndMobile() throws AccountRegisterException{
			String email = "1164739892@qq.com";
			String mobile = "7";
		   entUserService.findPassFromMailAndMobile(email, mobile);
	  }
	  @Test
	  public void testResetPass() {
			String email = "1164739892@qq.com";
			String mobile = "7";
			entUserService.findPassFromMailAndMobile(email, mobile);
		    String newPass = "123456";
		   // entUserService.resetPassword(id, newPass);
		}
	  
	
}
