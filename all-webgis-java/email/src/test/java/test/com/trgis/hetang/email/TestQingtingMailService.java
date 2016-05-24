package test.com.trgis.hetang.email;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.qtmap.email.QingtingMailService;
import com.trgis.qtmap.email.exception.MailSendFailedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-email.xml" })
public class TestQingtingMailService {

	@Autowired
	private QingtingMailService qingtingMailService;

	@Test
	public void testSendMail() throws MailSendFailedException {
		//邮件发送设置
		String sender = "account@trmap.cn";
		String senderName = "天润云地图-个人账号";
		String receiver = "bettysboy@trgis.com";
		String subject = "用户帐号激活";
		
		//邮件内容设置
		Map<String, String> userProperties = new HashMap<String, String>(0);
		userProperties.put("name", "张三啊");
		userProperties.put("email", "张三啊");
		userProperties.put("salt", "张三啊");
		
		qingtingMailService.sendActiveAccountMail(sender, senderName, receiver, subject, userProperties);
	}

}
