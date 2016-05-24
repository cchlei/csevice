/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: UserTopicVo.java
 * @Prject: trmap-personal-webapp
 * @Package: com.trgis.trmap.personal.webapp.vo
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月12日 上午10:03:39
 * @version: V1.0  
 */
package com.trgis.trmap.personal.webapp.vo;

import java.io.Serializable;

import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserTopicVo
 * @Description: 专题封装Vo
 * @author: chlei
 * @date: 2016年3月12日 上午10:03:39
 */
public class UserTopicVo implements Serializable{
	
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	
	private static final long serialVersionUID = 1L;


	/**
	 *  主键 id
	 */
	private Long id;
	
	
	/**
	 * 专题名称 name	char(10)
	 */
	private String 	name;
	
	/**
	 * 专题描述 	desc	char(50)
	 */
	private String 	description;
	
	/**
	 * 分享状态	shareflag	numeric(2)
	 * 状态包括：WFX(0, "未分享"), YFX(1, "已分享");
	 * 相关操作包括：
	 */
	private Integer shareflag;

	/**
	 * 发生时间	occurtime	time
	 */
	private String occurtime;
	
	/**
	 * 创建时间	creatime	time
	 */
	private String creatime;
	
	/**
	 * 封面地址	coverurl	char(30)
	 */
	private String 	coverurl;
	
	/**
	 * 专题颜色	topic_color	char(10)
	 */
	private String 	topiccolor;
	
	/**
	 * 是否被 选中的 状态	selectState	smallint
	 * NOCHECK(0, "未选中"), CHECKED(1, "已选中");
	 */
	private Integer selectStatus;
	
	/**
	 * 删除标志	delflag	integer	
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	private Integer delflag;
	
	/**
	 * 专题对应的用户	
	 */	
	private User user;
	
	/**
	 * 专题应得记录数 瞬时	
	 */	
	private Long record;
	
	/**
	 * 专题应得收藏数 瞬时	
	 */	
	private Long collect;
	
	/**
	 * @return
	 * 是否被收藏
	 */
	private Boolean iscollect;
	
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShareflag() {
		return shareflag;
	}

	public void setShareflag(Integer shareflag) {
		this.shareflag = shareflag;
	}

	public String getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(String occurtime) {
		this.occurtime = occurtime;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getRecord() {
		return record;
	}

	public void setRecord(Long record) {
		this.record = record;
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
