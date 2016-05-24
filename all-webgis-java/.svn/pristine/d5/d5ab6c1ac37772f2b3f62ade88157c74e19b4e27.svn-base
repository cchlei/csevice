package test.com.trgis.qtmap.qtuser;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.EnumUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestUserMapService {
	@Autowired
	private UserMapService userMapService;
	@Test
	public void testcreateUserMap() {
		UserMap userMap = new UserMap();
		userMap.setMapname("Junit测试3");
		userMap.setMapdesc("超级单元测试3");
		userMap.setIsshare(EnumUtil.SHARE.WFX.getValue());
		userMap.setMapdelflag(0);
		userMap.setShareurl("http://www.baidu.com");
		userMap.setUmdefaultbase("默认底图");
		userMap.setUmcreatetime(new Date());
		userMap.setUmicon("图标");
		userMapService.createUserMap(userMap);
		System.out.println(userMap.getId());
	}

	@Test
	public void testshareUserMap() {
		userMapService.shareUserMap(9l,EnumUtil.SHARE.YFX.getValue());
	}

	@Test
	public void deleteUserMap() {
		userMapService.deleteUserMap(9l);
	}
	@Test
	public void setShareUrl(){
		List<UserMap> list = userMapService.findAllUserMap();
		for (UserMap userMap : list) {
			userMapService.setShareUrl(userMap.getId());
		}
	}
//
//	@Test
//	public void testeditUserMap() {
//		UserMap userMap =userMapService.findUserMapById(10l) ;
//		userMap.setShareurl("www.trgis.com");
//		userMapService.editUserMap(userMap);
//	}
//
//	@Test
//	public void testfindAllUserMap() {
//		List<UserMap> userMapList =userMapService.findAllUserMap();
//		for (UserMap userMap : userMapList) {
//			System.out.println(userMap.getMapname()+"=============");
//		}
//	}
//
//	@Test
//	public  void testfindUserMap() {
//	List<UserMap> userMapList =userMapService.findUserMap();
//		for (UserMap userMap : userMapList) {
//			System.out.println(userMap.getMapname());
//		}
//	}
	
	@Test
	public void testFindPageList(){
		
		// 组装第一条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);

		// 条件1
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("mapdelflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		conditions.add(new SearchCondition("isshare", Operator.EQ, EnumUtil.SHARE.WFX.getValue()));
		conditions.add(new SearchCondition("user.id", Operator.EQ, 2l));
		
		group.setConditions(conditions);

		OrderBy order = new OrderBy("umcreatetime", "desc");
		Page<UserMap> userMap = userMapService.findByConditions(group, 1, 5, order);//.findByConditions(group, 0, 10, order);

		System.out.println(userMap.getTotalElements());
		System.out.println(userMap.getTotalPages());
		
		for (Iterator<UserMap> iterator = userMap.getContent().iterator(); iterator.hasNext();) {
			UserMap um = iterator.next();
			System.out.println(um.getMapname());
		}
	}
}
