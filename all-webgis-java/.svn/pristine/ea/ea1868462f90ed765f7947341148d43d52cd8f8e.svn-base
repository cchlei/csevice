package test.trgis.qtmap.qtuser.userauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.userauth.model.SystemUser;
import com.trgis.trmap.userauth.service.SystemUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-*.xml" })
public class TestSystemUser {

	@Autowired
	private SystemUserService systemUserService;
	
	@Test
	public void testCreateSystemUser(){
		SystemUser systemUser = new SystemUser();
		systemUser.setUsername("admin");
		systemUser.setPassword("123456");
		systemUserService.createSystemUser(systemUser);
	}
	
	
}
