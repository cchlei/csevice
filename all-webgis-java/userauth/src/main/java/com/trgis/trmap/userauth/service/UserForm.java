package com.trgis.trmap.userauth.service;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户表单
 * 
 * @author zhangqian
 *
 */
@SuppressWarnings("serial")
public class UserForm implements Serializable {

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String textpassword;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 头像.头像url地址
	 */
	@Column(name = "headimg")
	private String headimg;

	public UserForm() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return textpassword
	 */
	
	public String getTextpassword() {
		return textpassword;
	}

	/**
	 * textpassword 要设置的 textpassword
	 */
	public void setTextpassword(String textpassword) {
		this.textpassword = textpassword;
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

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public void cleanTextPass() {
		this.setTextpassword(StringUtils.EMPTY);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
