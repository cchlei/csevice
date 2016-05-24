/**
 * 
 */
package com.trgis.trmap.userauth.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;

/**
 * 账号
 * 
 * @author zhangqian
 *
 */
@Entity
@Table(name = "qtuser_account")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7318259472099676203L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	@Column(name = "username", length = 32, unique = true)
	private String username;

	/**
	 * 密码
	 */
	@JsonIgnore
	@JSONField(label="secret")
	@Column(name = "password")
	private String password;

	/**
	 * 安全秘钥 由系统生成
	 */
	@JsonIgnore
	@JSONField(label="secret")
	@Column(name = "salt")
	private String salt;

	/**
	 * 注册日期
	 */
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss")
	@Column(name = "createdate")
	private Date createDate = new Date();

	/**
	 * 名称
	 */
	@Column(name = "name", length = 20)
	private String name;
	
	/**
	 * 昵称
	 */
	@Column(name = "nickname", length = 20)
	private String nickname;
	
	/**
	 * 性别
	 */
	@Column(name = "gender", length = 2)
	private String gender;

	/**
	 * 手机
	 */
	@Column(name = "mobile", length = 20, unique = true)
	private String mobile;

	/**
	 * 邮箱.用户激活使用
	 */
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	/**
	 * 头像.头像url地址
	 */
	@Column(name = "headimg")
	private String headimg;

	/**
	 * 状态. 用户包含四个状态:未激活，激活（正常），冻结，封停
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING) //表示存储字符串，不存索引
	private UserStatus status = UserStatus.INACTIVE;
	
	/**
	 * 用户账号实体
	 * 如果是企业账号则追加关联的企业实体数据对象开发版暂不追加
	 * 
	 * 2016年1月15日 jger
	 * 添加nullable=false 保证用户帐号数据正常
	 */
	@Column(name = "entity",nullable=false)
	@Enumerated(EnumType.STRING)//表示存储字符串，不存索引
	private UserEntity entity;
	
	/**
	 * @author Alice
	 * 不入库，构造页面操作按钮时使用
	 */
	@Transient
	private String operation; 
	
	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserEntity getEntity() {
		return entity;
	}

	public void setEntity(UserEntity entity) {
		this.entity = entity;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/* (非 Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", createDate=" + createDate + ", name=" + name + ", gender=" + gender + ", mobile=" + mobile
				+ ", email=" + email + ", headimg=" + headimg + ", status=" + status + ", entity=" + entity + "]";
	}
}
