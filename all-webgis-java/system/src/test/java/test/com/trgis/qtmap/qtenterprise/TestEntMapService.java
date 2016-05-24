package test.com.trgis.qtmap.qtenterprise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.userauth.model.SystemUser;
import com.trgis.trmap.userauth.service.SystemUserService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEntMapService {

	@Autowired
	private SystemUserService systemUserService;
	
	@Test
	public void addUser(){
		SystemUser systemUser = new SystemUser();
		systemUser.setUsername("admin");
		systemUser.setPassword("admin");
		systemUserService.createSystemUser(systemUser);
	}

}
