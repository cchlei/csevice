package com.trgis.qtmap.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.trgis.qtmap.email.exception.MailSendFailedException;
import com.trgis.qtmap.email.exception.SendMailException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件发送服务 使用Spring Mail
 * 
 * @author zhangqian
 *
 */
@Service
public class QingtingMailService {

	private static final Log log = LogFactory.getLog(QingtingMailService.class);

	private static final String DEFAULT_ENCODING = "utf-8";

	private JavaMailSender mailSender;

	/**
	 * 激活邮件
	 */
	private Template activeMail;

	private Template findAccountMail;

	private Template resetpassMail;
	
	private Template entActiveMail;

	private Template entFindAccountMail;

	private Template entResetpassMail;
	/**
	 * @author Alice
	 * 通知邮件通用模板
	 */
	private Template noticeMail;
	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendActiveAccountMail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) throws MailSendFailedException{
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(activeMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}

	public void sendFindAccountEmail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) {
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(findAccountMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}

	public void sendResetpassEmail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) {
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(resetpassMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}

	/**
	 * add by doris at 2015-11-30
	 * 企业版邮件发送
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendActiveEntAccountMail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) throws MailSendFailedException{
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(entActiveMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}
	
	public void sendFindEntAccountEmail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) {
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(entFindAccountMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}

	public void sendResetpassEntEmail(String sender, String sendername, String receiver, String subject,
			Map<String, String> userProperties) {
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(entResetpassMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}
	
	/**
	 * @author Alice
	 * 发送通知邮件
	 */
	public void sendNoticeEmail(String sender, String sendername, String receiver, String subject, 
			Map<String, String> userProperties) {
		try {
			log.debug(String.format("给用户%s发送邮件", receiver));
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setFrom(sender, sendername);
			helper.setTo(receiver);
			helper.setSubject(subject);
			// 设置邮件内容
			String content = null;
			// 获取激活邮件模板，并赋值
			content = FreeMarkerTemplateUtils.processTemplateIntoString(noticeMail, userProperties);
			// 设置邮件内容
			helper.setText(content, true);
			// 发送邮件
			mailSender.send(msg);
			log.debug("邮件发送成功!");
		} catch (UnsupportedEncodingException e) {
			log.debug("错误的邮件编码.", e);
			throw new SendMailException("错误的邮件编码.", e);
		} catch (javax.mail.MessagingException e) {
			log.debug("邮件发送失败.", e);
			throw new SendMailException("邮件发送失败.", e);
		} catch (IOException e) {
			log.debug("模板未找到.", e);
			throw new SendMailException("模板未找到.", e);
		} catch (TemplateException e) {
			log.debug("模板加载错误.", e);
			throw new SendMailException("模板加载错误.", e);
		}
	}
	
	/**
	 * 设置邮件发送器
	 * 
	 * @param mailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		activeMail = freemarkerConfiguration.getTemplate("activemail.ftl", DEFAULT_ENCODING);
		findAccountMail = freemarkerConfiguration.getTemplate("findaccount.ftl", DEFAULT_ENCODING);
		resetpassMail = freemarkerConfiguration.getTemplate("resetpass.ftl", DEFAULT_ENCODING);
		entActiveMail = freemarkerConfiguration.getTemplate("entactivemail.ftl", DEFAULT_ENCODING);
		entFindAccountMail = freemarkerConfiguration.getTemplate("entfindaccount.ftl", DEFAULT_ENCODING);
		entResetpassMail = freemarkerConfiguration.getTemplate("entresetpass.ftl", DEFAULT_ENCODING);
		noticeMail = freemarkerConfiguration.getTemplate("notice.ftl", DEFAULT_ENCODING);
	}

}
