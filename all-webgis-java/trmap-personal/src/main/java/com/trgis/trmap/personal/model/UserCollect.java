/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserCollect.java
 * @Prject: trmap-personal
 * @Package: com.trgis.trmap.personal.modle
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月8日 下午5:24:49
 * @version: V1.0  
 */
package com.trgis.trmap.personal.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserCollect
 * @Description: 收藏表
 * @author: chlei
 * @date: 2016年3月8日 下午5:24:49
 */
@Entity
@Table(name = "trmap_user_collect")
public class UserCollect implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 专题颜色	topic_color	char(10)
	 */
	@Column(name = "topic_color", length = 10)
	private String 	topiccolor;
	
	/**
	 * 创建时间	creatime	time
	 */
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date creatime =new Date();
	/**
	 * 选中状态	selectState	smallint
	 * NOCHECK(0, "未选中"), CHECKED(1, "已选中");
	 */
	@Column(name = "select_status")
	private Integer selectStatus=EnumUtil.CHECKSTATUS.NOCHECK.getValue();
	
	/**
	 * 收藏者	
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * 收藏的专题	
	 */	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private UserTopic topic;

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

}
