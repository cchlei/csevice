package test.com.trgis.qtmap.qtenterprise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEnterpriseAccountService {

	@Autowired
	private EntCainfoService epService;

	@Test
	public void testFindEnterprise() {
		EntCainfo epInfo = epService.findByUsername("admin");
		System.out.println(epInfo == null);
	}

}