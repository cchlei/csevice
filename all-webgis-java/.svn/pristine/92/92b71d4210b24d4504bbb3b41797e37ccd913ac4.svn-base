package test.com.trgis.qtmap.qtenterprise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEntMapService {

	@Autowired
	private EntMapService entMapService;
	/**
	 * 测试企业地图发布
	 */
	@Test
	public void releaseUserMap(){
		EntUserMap map = entMapService.findUserMapById(809l);
		entMapService.releaseUserMap(map);
	}

}
