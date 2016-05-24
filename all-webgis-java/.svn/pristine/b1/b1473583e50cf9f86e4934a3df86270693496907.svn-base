package com.trgis.trmap.qtuser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 意见建议
 * @author Alice
 *
 * 2015年11月6日
 */
@Entity
@Table(name = "qtuser_suggestion")
public class Suggestion implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 内容
	 */
	@Column(name = "content",length = 200)
	private String content;
	
	/**
	 * 创建时间
	 */
	@Column(name = "createtime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date createtime = new Date();
	
	public Suggestion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Suggestion(String content, Date createtime) {
		super();
		this.content = content;
		this.createtime = createtime;
	}

}
