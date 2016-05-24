/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserTopic.java
 * @Prject: trmap-personal
 * @Package: com.trgis.trmap.personal.modle
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月8日 下午4:15:27
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
 * @ClassName: UserTopic
 * @Description:  我的专题
 * @author: chlei
 * @date: 2016年3月8日 下午4:15:27
 */
@Entity
@Table(name = "trmap_user_topic")
public class UserTopic implements Serializable {
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	/**
	 * 专题名称 name	char(10)
	 */
	@Column(name = "name", length = 10)
	private String 	name;
	
	/**
	 * 专题描述 	desc	char(50)
	 */
	@Column(name = "description", length = 50)
	private String 	description;
	
	/**
	 * 分享状态	shareflag	numeric(2)
	 * 状态包括：WFX(0, "未分享"), YFX(1, "已分享");
	 * 相关操作包括：
	 */
	@Column(name = "shareflag", length = 2)
	private Integer shareflag=EnumUtil.SHAREFLAG.YFX.getValue();

	/**
	 * 发生时间	occurtime	time
	 */
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date occurtime  =new Date();
	
	/**
	 * 创建时间	creatime	time
	 */
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date creatime =new Date();
	
	/**
	 * 封面地址	coverurl	char(255)
	 */
	@Column(name = "coverurl", length = 255)
	private String 	coverurl;
	
	/**
	 * 专题颜色	topic_color	char(10)
	 */
	@Column(name = "topic_color", length = 10)
	private String 	topiccolor;
	
	/**
	 * 是否被 选中的 状态	selectState	smallint
	 * NOCHECK(0, "未选中"), CHECKED(1, "已选中");
	 */
	@Column(name = "select_status", length = 50)
	private Integer selectStatus=EnumUtil.CHECKSTATUS.CHECKED.getValue();
	
	/**
	 * 删除标志	delflag	integer	
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag", length = 50)
	private Integer delflag=EnumUtil.DELFLAG.NOMAL.getValue();
	
	
	@Column(name = "permissions")
	private Integer jurisdict=EnumUtil.TOPIC_PERMISSION.ALL.getValue();
	
	
	/**
	 * 专题对应的用户	
	 */	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}



	public Integer getJurisdict() {
		return jurisdict;
	}

	public void setJurisdict(Integer jurisdict) {
		this.jurisdict = jurisdict;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getShareflag() {
		return shareflag;
	}

	public void setShareflag(Integer shareflag) {
		this.shareflag = shareflag;
	}

	public Date getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}

	public Date getCreatime() {
		return creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	public String getCoverurl() {
		return coverurl;
	}

	public void setCoverurl(String coverurl) {
		this.coverurl = coverurl;
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

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

}
