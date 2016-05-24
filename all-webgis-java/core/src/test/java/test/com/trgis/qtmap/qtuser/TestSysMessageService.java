package test.com.trgis.qtmap.qtuser;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.qtuser.model.SysMessage;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.qtuser.service.ISysMessageService;
import com.trgis.trmap.qtuser.service.UserMapService;
import com.trgis.trmap.qtuser.utils.EnumUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestSysMessageService {
	@Autowired
	private UserMapService userMapService;
	@Autowired
	private ISysMessageService sysMessageService;
	@Test
	public void testsave() {
		SysMessage sysMessage = new SysMessage();
		sysMessage.setMdelflag(0);
		sysMessage.setMtitle("干撒");
		sysMessage.setReadflag(EnumUtil.READ.WD.getValue());
		sysMessage.setMsgcreatetime(new Date());
		UserMap userMap = userMapService.findUserMapById(10L);
		sysMessage.setUserMap(userMap);
		sysMessageService.save(sysMessage);
	}

	@Test
	public void testedit() {
		SysMessage sysMessage = sysMessageService.findById(30l);
		sysMessage.setMtitle("科学技术");
		sysMessageService.edit(sysMessage);
	}

	@Test
	public void testdelete() {
		
		sysMessageService.delete(31l);
	}

	@Test
	public void testfindAll() {
		 List<SysMessage> slList = sysMessageService.findAll();
		for (SysMessage sysMessage : slList) {
			System.out.println(sysMessage.getMtitle()+"---------");
		}
	}

	@Test
	public void findById() {
		SysMessage sysMessage = sysMessageService.findById(29l);
		System.out.println(sysMessage.getMtitle()+"=======");
	
	}

	@Test
	public void findByUserMap() {
		UserMap userMap = userMapService.findUserMapById(10L);
		 List<SysMessage> slList=sysMessageService.findByUserMap(userMap);
		 for (SysMessage sysMessage : slList) {
				System.out.println(sysMessage.getMtitle()+"$$$$$$$$");
			}
	}

	@Test
	public void findSysMessages() {
		UserMap userMap = userMapService.findUserMapById(10L);
		 List<SysMessage> slList=sysMessageService.findSysMessages(0, userMap);
		 for (SysMessage sysMessage : slList) {
				System.out.println(sysMessage.getMtitle()+"*********");
			}
	}

}
