package test.com.trgis.qtmap.qtenterprise;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.enterprise.service.EntAttributemetaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEnterpriseAttributeService {
	@Autowired
	private EntAttributemetaService service;
	@Test
	public void entAttributemeta(){
		List<String[]> s = new ArrayList<String[]>();
		s.add(0, new String[]{"标题",""});
		s.add(1, new String[]{"经度",""});
		service.editAttriButeMeta(s,807l);
	}
}
