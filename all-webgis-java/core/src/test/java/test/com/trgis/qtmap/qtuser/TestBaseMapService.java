package test.com.trgis.qtmap.qtuser;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.qtuser.model.BaseMap;
import com.trgis.trmap.qtuser.service.BaseMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestBaseMapService {
	@Autowired
	private BaseMapService baseMapService;
	@Test
	public void testAddBaseMap() {
		BaseMap basemap = new BaseMap();
//		COLOR(0,"彩色版"),COOL(1, "冷色版"),WORLDIMAGE(2, "天地图影像"),GRAY(3, "灰色版"),
//		NOTEMAP(4, "彩色注记版"),WARM(5, "暖色版"),WORLD(6, "天地图");
		basemap.setBasemapType("彩色注记版");
		basemap.setMapId(633l);
		basemap.setOpacity(0.5);
		basemap.setZ_index(1);
		baseMapService.addBaseMap(basemap);
	}
	
	@Test
	public void getBaseMapByMapId(){
		List<BaseMap> list = baseMapService.getBaseMapByMapId(336l);
		System.out.println(list.get(0).getBasemapType());
	}
	
	@Test
	public void deleteBaseMapByMapId(){
		baseMapService.deleteBaseMapByMapId(633l);
	}
}
