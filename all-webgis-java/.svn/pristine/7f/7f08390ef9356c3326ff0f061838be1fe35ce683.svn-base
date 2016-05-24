package com.trgis.trmap.qtuser.model;

import java.io.Serializable;
import java.sql.Blob;
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
/**
 * 蜻蜓系统以及用户消息
 * @author doris
 * @date 2015-09-11
 */
@Entity
@Table(name = "qtuser_message")
public class SysMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 个人地图
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "map_message_id")
	private UserMap userMap;
	/**
	 * 消息标题
	 */
	@Column(name = "mtitle", length = 255)
	private String mtitle;
	/**
	 * 消息内容
	 */
	@Column(name = "mcontent")
	private Blob mcontent;
	/**
	 * 阅读状态
	 */
	@Column(name = "readflag",length = 2)
	private Integer readflag;
	/**
	 * 消息删除
	 */
	@Column(name = "mdelflag",length = 2)
	private Integer mdelflag;
	/**
	 * 创建时间
	 */
	@Column(name = "msgcreatetime", nullable = false)
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date msgcreatetime;
	
	public SysMessage() {
		super();
	}
	
	public SysMessage(Long id, UserMap userMap, String mtitle, Blob mcontent, Integer readflag, Integer mdelflag,
			Date msgcreatetime) {
		super();
		this.id = id;
		this.userMap = userMap;
		this.mtitle = mtitle;
		this.mcontent = mcontent;
		this.readflag = readflag;
		this.mdelflag = mdelflag;
		this.msgcreatetime = msgcreatetime;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserMap getUserMap() {
		return userMap;
	}
	public void setUserMap(UserMap userMap) {
		this.userMap = userMap;
	}
	public String getMtitle() {
		return mtitle;
	}
	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}
	public Blob getMcontent() {
		return mcontent;
	}
	public void setMcontent(Blob mcontent) {
		this.mcontent = mcontent;
	}
	public Integer getReadflag() {
		return readflag;
	}
	public void setReadflag(Integer readflag) {
		this.readflag = readflag;
	}
	public Integer getMdelflag() {
		return mdelflag;
	}
	public void setMdelflag(Integer mdelflag) {
		this.mdelflag = mdelflag;
	}

	public Date getMsgcreatetime() {
		return msgcreatetime;
	}

	public void setMsgcreatetime(Date msgcreatetime) {
		this.msgcreatetime = msgcreatetime;
	}
	
}
