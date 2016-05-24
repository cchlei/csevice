package test.com.trgis.qtmap.qtenterprise;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.service.EntMapService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEntMapGraphics {

	@Autowired
	private EntMapGraphicsService entGraphicsService;
	@Autowired
	private EntMapService entMapService;
	@Test
	public void testaddEntMapGraphics() {
		EntMapGraphics graphics=new EntMapGraphics();
		graphics.setGcreatedate(new Date());
		graphics.setGname("一条线线");
		graphics.setGtype("线");
		EntUserMap userMap = entMapService.findUserMapById(787l);
		graphics.setUserMap(userMap);
		entGraphicsService.createEntMapGraphics(graphics);
	}
	@Test
	public void testeditEntMapGraphics() {
		EntMapGraphics graphics=entGraphicsService.findById(790l);
		graphics.setGeom("test");
		entGraphicsService.editEntMapGraphics(graphics);
	}
	@Test
	public void testFindByKet() {
		EntMapGraphics graphics;
		try {
			EntUserMap map = entMapService.findUserMapById(998998L);
			graphics = entGraphicsService.findByIdentifykey(map, "a");
			System.out.println(graphics.getGname());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
