package test.com.trgis.qtmap.qtuser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.service.MapGraphicsService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.userauth.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestAttachfileService {
	@Autowired
	private AttachfileService attachfileService;
	@Autowired
	private MapGraphicsService mapGraphicsService;
	
	private UserService userService;

	@Test
	public void getByGraphicsId() {
		MapGraphics mg = new MapGraphics();
		mg.setId(73l);
		List<Attachfile> list = attachfileService.getByGraphicsId(mg);
		System.out.println(list.size());
	}

	@Test
	public void testAttachfileList() {
		// 组装条件组
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 条件1关键字
		String attachName = "";
		if (StringUtils.isNotBlank(attachName)) {
			conditions.add(new SearchCondition("attachName", Operator.LIKE, attachName));
		}
		// 条件2用户
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		//User user = userService.findUserByUsername(username);
		conditions.add(new SearchCondition("uploadUserId", Operator.EQ, "2"));
		conditions.add(new SearchCondition("mapGraphics", Operator.ISNOTNULL, null));
		group.setConditions(conditions);
		OrderBy order = new OrderBy("uploadTime", "desc");
		int pageNo = 1;
		int pageSize = 10;
		Page<Attachfile> attachfile = attachfileService.findByConditions(group, pageNo, pageSize, order);
		List<Attachfile> aList = attachfile.getContent();
		for (Attachfile attachfile2 : aList) {
			System.out.println(attachfile2.getAttachName() + "+++++++++++");
		}
	}

	@Test
	public void findAttachfileByOssid() {
		Attachfile af = attachfileService.findAttachfileByOssid("f6f30079-96c0-4f6d-8642-38c1a3fa1003-d1442806766131");
		System.out.println(af.getAttachName());
	}

	@Test
	public void saveAttachfileOssid() {
		Attachfile attachfile = attachfileService
				.findAttachfileByOssid("f6f30079-96c0-4f6d-8642-38c1a3fa1003-d1442806766131");
		MapGraphics mg = mapGraphicsService.findById(304L);
		if (BeanUtil.isNotEmpty(attachfile)) {
			attachfile.setMapGraphics(mg);
			attachfileService.saveAttachfile(attachfile);
		}
		System.out.println(attachfile.getMapGraphics().getId());
	}

	@Test
	public void deleteAttachfile() {
		attachfileService.deleteAttachfile(82l);
	}

	@Test
	public void getUsedDataSpace() {
		Double space = attachfileService.getUsedDataSpace(2l);
		System.out.println(space);
	}

	@Test
	public void clearAttachfileById() {
		MapGraphics mg = mapGraphicsService.findById(432L);
		attachfileService.clearAttachfileById(mg);
	}

	@Test
	public void attachfileDao() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = "2015-09-22";
		Date date = sdf.parse(strDate);
		attachfileService.clearAttachfileByDate(date);
	}
}
