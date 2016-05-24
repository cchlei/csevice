/**  
 * @Title: EmailSender.java
 * @Package com.trgis.trmap.esb.email
 * @author zhangqian
 * @date 2016年1月18日 下午2:37:18
 * @version V1.0  
 */
package com.trgis.trmap.esb.email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: EmailSender
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangqian
 * @date 2016年1月18日 下午2:37:18
 *
 */
@Service
public class EmailSender {

	private static final String EMAIL_QUEUE = "tr-email";
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void sendEmail(){

		Map<String,Object> mailProperties = new HashMap<String, Object>();
		mailProperties.put("mail", "activemail.ftl"); //邮件模版的名称
		mailProperties.put("to", "99880246@qq.com");
		mailProperties.put("from", "account@trmap.cn");
		mailProperties.put("subject", "你好");
		mailProperties.put("content", "邮件测试");
		
		Map<String, Object> model = new HashMap<String,Object>();
		model.put("name","李斌");
		model.put("host", "ent.trmap.cn");
		model.put("email", "99880246@qq.com");
		model.put("code","01");
		mailProperties.put("model", model);
		
		amqpTemplate.convertAndSend(EMAIL_QUEUE,mailProperties);
	}
	
}
