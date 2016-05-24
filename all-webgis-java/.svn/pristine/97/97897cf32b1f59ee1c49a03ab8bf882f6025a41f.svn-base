package test.com.trgis.qtmap.qtenterprise;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEnterpriseCaifoService {
	@Autowired
	private EntCainfoService entCainfoService;
	@Autowired
	private UserService userService;
	//企业用户信息认证 
	@Test
	public void testaddEnterprise() {
		EntCainfo e=new EntCainfo();
		e.setAddress("杭州市西湖区38号");
		e.setManagerName("李四");
		e.setBossName("马云");
		e.setBossIdentifyId("610425198822240632");
		e.setCastatus(EnumUtil.CASTATUS.AUDITWAIT.getValue());
		e.setComment("淘呀淘呀淘");
		e.setEnterpriseAttachUrl("qtenterprise/WEB-INF/map");
		e.setEnterpriseName("阿里巴巴网络有限公司");
		e.setEnterpriseId("1256e656");
		e.setCertificateDate(new Date());
		e.setManagerIdentifyId("61032319921111685X");
		e.setManagerIdentifyUrl("qtenterprise/WEB-INF/map");
		e.setManagerPhone("15229262500");
		e.setUtilValidDate(new Date());
		User user = userService.findUser(743l);
		e.setUser(user);
		entCainfoService.save(e);
		System.out.println(e);
	}
	
	@Test
	public void testfindById() {
		EntCainfo e = entCainfoService.findById(755l);
		Assert.assertEquals(e.getBossName(), "张朝阳");
		Assert.assertEquals(e.getEnterpriseName(), "搜狐科技有限公司");
		Assert.assertEquals(e.getAddress(), "北京市朝阳区120号");
		System.out.println(e);
	}
	//企业认证信息维护
	//@Test
	public void testedit() {
		EntCainfo e = entCainfoService.findById(753l);
		//e.setCastatus(EnumUtil.CASTATUS.AUDITING.getValue());
		/*e.setAddress("北京市朝阳区250号SOHU大厦");
		e.setBossIdentifyId("610323195001014523");
		e.setBossName("zhangchaoyang");
		e.setCastatus(EnumUtil.CASTATUS.AUDITING.getValue());
		e.setCertificateDate(new Date());
		e.setComment("搜狐视频欢迎你。。。。");
		e.setEnterpriseAttachUrl("qtenterprise/WEB-INF/jsp");
		e.setEnterpriseId("1232423e4");
		e.setManagerIdentifyId("610323199201076868");
		e.setManagerIdentifyUrl("qtenterprise/WEB-INF/jsp");
		e.setManagerName("Bob");
		e.setManagerPhone("15229262475");
		e.setUtilValidDate(new Date());*/
		User user = userService.findUser(750l);
		e.setUser(user);
		entCainfoService.edit(e);
		EntCainfo e1 = entCainfoService.findById(753l);
		System.out.println(e1);
	}
	
	//@Test
	public void Tesedelete() {
		entCainfoService.delete(752l);
	}
	
	//@Test
	public void TestfindAll() {
		List<EntCainfo> list = entCainfoService.findAll();
		for (EntCainfo entCainfo : list) {
			System.out.println(entCainfo.getId() + "/" +entCainfo.getBossName() + "/" + entCainfo.getEnterpriseName() + "/" + entCainfo.getAddress()
					+ "/" + entCainfo.getComment() + "/" + entCainfo.getManagerName() + "/" + entCainfo.getCastatus());
		}
	}
	
	//@Test
	public void TestfindByUser() {
		User us = userService.findUser(750l);
		EntCainfo cainfo = entCainfoService.findByUser(us);
		Assert.assertEquals("北京市朝阳区250号SOHU大厦", cainfo.getAddress());
		Assert.assertEquals(Long.valueOf(755), cainfo.getId());
		Assert.assertEquals("zhangchaoyang", cainfo.getBossName());
		Assert.assertEquals("搜狐科技有限公司", cainfo.getEnterpriseName());
		System.out.println(cainfo);
	}
	
	//@Test
	public void TestfindByConditions() {
		// 组装第一条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		// 条件1
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("castatus", Operator.EQ, EnumUtil.CASTATUS.AUDITED.getValue()));
		conditions.add(new SearchCondition("user.id", Operator.EQ, 750l));
		
		group.setConditions(conditions);
		
		OrderBy order = new OrderBy("certificateDate", "desc");
		Page<EntCainfo> cainfo = entCainfoService.findByConditions(group, 2, 1, order);
		
		System.out.print("总条数" + cainfo.getTotalElements());
		System.out.println("总页数" + cainfo.getTotalPages());
	
		for (Iterator<EntCainfo> iterator = cainfo.getContent().iterator(); iterator.hasNext();) {
			EntCainfo ecf = iterator.next();
			System.out.println(ecf.getEnterpriseName());
		}
	}
	//撤销认证信息
	@Test
	public void TestrevocationApply () {
		entCainfoService.revocationApply(757l);
		EntCainfo cainfo = entCainfoService.findById(757l);
		Assert.assertEquals(1 + "", cainfo.getCastatus());
	}
	
	@Test
	public void TestfindByUsername() {
		EntCainfo cainfo = entCainfoService.findByUsername("mm12345");
		Assert.assertEquals("李彦宏",cainfo.getBossName());
		Assert.assertEquals("百度科技有限公司", cainfo.getEnterpriseName());
		System.out.println(cainfo);
	}
	
	@Test
	public void TestfindByEnterpriseName() {
		EntCainfo cainfo = entCainfoService.findByEnterpriseName("阿里巴巴网络有限公司");
		Assert.assertEquals("杭州市西湖区38号", cainfo.getAddress());
		Assert.assertEquals("马云", cainfo.getBossName());
		Assert.assertEquals("淘呀淘呀淘", cainfo.getComment());
		Assert.assertEquals("李四", cainfo.getManagerName());
		Assert.assertEquals("15229262500", cainfo.getManagerPhone());
		System.out.println("cainfo: " + cainfo);
	}
	
	@Test
	public void TestfindByUserAndCastatus() {
		/*User user = userService.findUser(Long.valueOf("10893"));
		EntCainfo cainfo = entCainfoService.findByUserAndCastatus(user, "1");
		Assert.assertEquals("苗XX", cainfo.getBossName());
		Assert.assertEquals("中国苗氏企业集团", cainfo.getEnterpriseName());
		Assert.assertEquals("苗萌", cainfo.getManagerName());
		System.out.println("cainfo: " + cainfo);*/
		List<EntCainfo> lsca =  entCainfoService.findByCastatus("2");
		Assert.assertEquals(8, lsca.size());
		for (EntCainfo entCainfo : lsca) {
			System.out.println(entCainfo.getEnterpriseName());
		}
	}
}
