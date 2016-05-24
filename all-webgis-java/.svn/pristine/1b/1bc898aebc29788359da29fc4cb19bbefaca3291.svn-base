package test.com.trgis.qtmap.qtuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.EnumUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestMapGraphicsService {
	@Autowired
	private UserMapService userMapService;
	@Autowired
	private MapGraphicsService mapGraphicsService;
	
	@Test
	public void testsave() {
		MapGraphics graphics = new MapGraphics();
		graphics.setGcreatedate(new Date());
		graphics.setGname("一条线线");
		graphics.setGtype("线");
		UserMap userMap = userMapService.findUserMapById(9L);
		graphics.setUserMap(userMap);
		mapGraphicsService.save(graphics);
	}

	@Test
	public void testedit() {
		MapGraphics graphics = mapGraphicsService.findById(23l);
		graphics.setGname("一个点");
		graphics.setGtype("点");
		mapGraphicsService.edit(graphics);
	}
	@Test
	public void testdelete() {
		mapGraphicsService.delete(24l);
	}

	@Test
	public void testfindAll() {
		List<MapGraphics> mapList = mapGraphicsService.findAll();
		for (MapGraphics mapGraphics : mapList) {
			System.out.println(mapGraphics.getGname()+"------");
		}
	}

	@Test
	public void  testfindById() {
		MapGraphics mapGraphics = mapGraphicsService.findById(22l);
		System.out.println(mapGraphics.getGname()+"+++++++");
	}

	@Test
	public void testfindByUserMap() {
		UserMap userMap  = userMapService.findUserMapById(10L);
		List<MapGraphics> mapList =mapGraphicsService.findByUserMap(userMap); 
		for (MapGraphics mapGraphics : mapList) {
			System.out.println(mapGraphics.getGname()+"*************");
		}
	}
	@Test
	public void testGraphicsList(){
    	ConditionGroup group = new ConditionGroup();
    	group.setSearchRelation(SearchRelation.AND);
    	// 条件1
    	String gname ="67";
    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
    	conditions.add(new SearchCondition("gname", Operator.LIKE, gname));
    	conditions.add(new SearchCondition("userMap.user.id", Operator.EQ,2l));
    	conditions.add(new SearchCondition("gtype", Operator.EQ,EnumUtil.GTYPE.LINE.getValue()));
    	group.setConditions(conditions);
    	OrderBy order = new OrderBy("gcreatedate", "desc");
    	int pageNo = 1;
        int pageSize = 10;
    	Page<MapGraphics> mapGraphic=mapGraphicsService.findByConditions(group,pageNo,pageSize, order);
    	List<MapGraphics> sList = mapGraphic.getContent();
    	for (MapGraphics mapGraphics : sList) {
    		System.out.println(mapGraphics.getUserMap().getMapname());
			System.out.println(mapGraphics.getGname()+"++++++++++");
			System.out.println(mapGraphics.getUserMap().getMapname()+"++++++++++");
		}
    }
}
