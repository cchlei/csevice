package com.trgis.trmap.system.vo;

import java.util.List;

/**
 * 封装发送消息表单
 * @author majl
 * @Description 
 * @data 2016年4月21日
 */
public class MessagForm {

	private String title;
	
	private String content;
	
	private List<String> usernames;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	
	
}
