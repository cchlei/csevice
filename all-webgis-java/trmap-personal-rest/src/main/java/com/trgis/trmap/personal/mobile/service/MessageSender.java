/**  
 * @Title: MessageSender.java
 * @Package com.trgis.email.jms.producter
 * @author zhangqian
 * @date 2016年1月15日 下午8:25:23
 * @version V1.0  
 */
package com.trgis.trmap.personal.mobile.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MessageSender
 * @Description: 消息发送
 * @author zhangqian
 * @date 2016年1月15日 下午8:25:23
 *
 */
@Component
public class MessageSender {
	
	/**
	 * 消息队列名称
	 */
	public static final String QUEUE_NAME = "tr-email";
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * Demo
	 * @Title: sendMsg
	 * @Description: 发送邮件的示例程序
	 */
	public void sendMsg(){
		Map<String,Object> mailProperties = new HashMap<String, Object>();
		
		// 组织邮件的发送人和接收人
		mailProperties.put("to", "jger.zq@qq.com");
		mailProperties.put("from", "account@trmap.cn");
		mailProperties.put("fromName", "天润云地图-帐号中心");
		mailProperties.put("subject", "测试邮件");
		
		// 如果使用模版则设置模版中的参数
		mailProperties.put("mail", "activemail.ftl");
		Map<String, Object> model = new HashMap<String,Object>();
		model.put("host", "主机");
		model.put("email", "激活的邮箱");
		model.put("code","CODE");
		model.put("name","张三");
		mailProperties.put("model", model);
		
		// 如果不使用模版则直接设置内容
		mailProperties.put("content", "邮件内容");

		// 发送到消息服务
		rabbitTemplate.convertAndSend(QUEUE_NAME, mailProperties);
	}
	

}
