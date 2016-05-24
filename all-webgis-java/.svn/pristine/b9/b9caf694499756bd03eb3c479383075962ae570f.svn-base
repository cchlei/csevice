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
import com.trgis.trmap.userauth.model.User;

/**
 * 评论表
 * @author 燕鹏
 */
@Entity
@Table(name = "trmap_user_comment")
public class UserComment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 评论时间
	 */
	@Column(name = "comtime")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date comtime = new Date();

	/**
	 * 评论內容
	 */
	@Column(name = "comcontent", length = 100)
	private String comcontent;

	/**
	 * 评论者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cuser_id")
	private User cuser;
	
	/**
	 * 被评论者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buser_id")
	private User buser;

	/**
	 * 相关事件
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_rid")
	private UserRecord userRecord;
	
	
	/**
	 * 父级id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private UserComment userComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getComtime() {
		return comtime;
	}

	public void setComtime(Date comtime) {
		this.comtime = comtime;
	}

	public String getComcontent() {
		return comcontent;
	}

	public void setComcontent(String comcontent) {
		this.comcontent = comcontent;
	}

	

	public User getCuser() {
		return cuser;
	}

	public void setCuser(User cuser) {
		this.cuser = cuser;
	}

	public User getBuser() {
		return buser;
	}

	public void setBuser(User buser) {
		this.buser = buser;
	}

	public UserComment getUserComment() {
		return userComment;
	}

	public void setUserComment(UserComment userComment) {
		this.userComment = userComment;
	}

	public UserRecord getUserRecord() {
		return userRecord;
	}

	public void setUserRecord(UserRecord userRecord) {
		this.userRecord = userRecord;
	}
	
}