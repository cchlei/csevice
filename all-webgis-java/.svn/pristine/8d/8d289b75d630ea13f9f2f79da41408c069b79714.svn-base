/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserCollectVo.java
 * @Prject: trmap-personal-webapp
 * @Package: com.trgis.trmap.personal.webapp.vo
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月14日 上午8:56:56
 * @version: V1.0  
 */
package com.trgis.trmap.personal.webapp.vo;

import java.io.Serializable;

import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserCollectVo
 * @Description: TODO
 * @author: chlei
 * @date: 2016年3月14日 上午8:56:56
 * @param			  	"id":"1",
		      		  	"title":"四川九寨沟旅游记录",//专题名称
		      			"images":"../images/illu1.png",//专题图片
					    "headimg":"../images/header.png",//用户头像
					    "name":"被风吹过的夏天",//用户名
					    "collect":"5",//收藏数
					    "color":"#2bba8d",//专题标签颜色
					    "iscollect":true//是否被收藏
 */
public class UserCollectVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	private Long id;
	
	/**
	 * 专题颜色	topic_color	char(10)
	 */
	private String 	topiccolor;
	
	/**
	 * 选中状态	selectState	smallint
	 * NOCHECK(0, "未选中"), CHECKED(1, "已选中");
	 */
	private Integer selectStatus;
	
	/**
	 * 收藏者	
	 */
	private User user;
	
	/**
	 * 收藏的专题	
	 */	
	private UserTopic topic;
	
	/**
	 * 专题应得收藏数 瞬时	
	 */	
	private Long collect;
	/**
	 * 专题应得记录数 瞬时	
	 */	
	private Long record;
	/**
	 * @return
	 * 是否被收藏
	 */
	private Boolean iscollect;

	
	public Long getRecord() {
		return record;
	}

	public void setRecord(Long record) {
		this.record = record;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopiccolor() {
		return topiccolor;
	}

	public void setTopiccolor(String topiccolor) {
		this.topiccolor = topiccolor;
	}

	public Integer getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(Integer selectStatus) {
		this.selectStatus = selectStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserTopic getTopic() {
		return topic;
	}

	public void setTopic(UserTopic topic) {
		this.topic = topic;
	}

	public Long getCollect() {
		return collect;
	}

	public void setCollect(Long collect) {
		this.collect = collect;
	}

	public Boolean getIscollect() {
		return iscollect;
	}

	public void setIscollect(Boolean iscollect) {
		this.iscollect = iscollect;
	}
	
}
