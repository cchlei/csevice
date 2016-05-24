package com.trgis.trmap.userauth.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 账号限制 所依赖的记录实体类（也可以用 redis 缓存 但是没搞过）
 * @author chlei
 * @2016年2月23日上午10:26:26
 * 1,同IP帐号注册限制  2,同IP帐号找回限制  访问记录
 * 注册 找回 
 */

@Entity
@Table(name="account_confine")
public class AccountConfine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 记录访问ip
	 */
	@Column(name="record_ip",length=32)
	private String ip;
	
	/**
	 * 记录访问时间 转换成String 类型
	 */
	@Column(name = "record_time",length = 32)
//	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
//	private Date recordTime = new Date();
	private String recordTime;
	
	/**
	 *注册类型 (0 个人注册 ,1 企业注册)
	 */
	@Column(name = "registType", length = 2)
	private Integer registType;
		
	/**
	 * 用来找回密码的邮箱
	 */
	@Column(name = "email")
	private String email;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRegistType() {
		return registType;
	}

	public void setRegistType(Integer registType) {
		this.registType = registType;
	}
}
