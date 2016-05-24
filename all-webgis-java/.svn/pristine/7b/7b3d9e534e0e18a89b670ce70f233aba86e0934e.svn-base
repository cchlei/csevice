package test.com.trgis.qtmap.qtenterprise;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.util.JSONUtils;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.service.EntDataService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEnterpriseDataService {

	@Autowired
	private EntDataService epDataService;

	@Test
	public void testFindMapData() {
		List<EntMapGraphics> lists = epDataService.findMapGraphics(505l);
		System.out.println(JSONUtils.convertList(lists));
	}

}