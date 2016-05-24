/**
 * 
 */
package com.trgis.trmap.userauth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * 用户重置密码业务记录
 * 
 * @author zhangqian
 *
 */
@Entity
@Table(name = "qtuser_cpi")
public class ChangepassInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 重置用户
	 */
	@Column
	private Long userId;

	/**
	 * 重置申请时间
	 */
	@Column
	private Date createDate = new Date();

	/**
	 * 重置时间
	 */
	@Column
	private Date resetDate;

	/**
	 * 重置安全码
	 */
	@Column
	private String salt = UserEncrypt.generateSalt();

	/**
	 * 用户加密校验码
	 */
	@Column
	private String securityuser;

	/**
	 * 重置校验码
	 */
	@Column
	private String securitykey;

	/**
	 * 是已重置
	 */
	@Column
	private Boolean reseted = false;

	public ChangepassInfo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecuritykey() {
		return securitykey;
	}

	public void setSecuritykey(String securitykey) {
		this.securitykey = securitykey;
	}

	public Boolean getReseted() {
		return reseted;
	}

	public void setReseted(Boolean reseted) {
		this.reseted = reseted;
	}

	public String getSecurityuser() {
		return securityuser;
	}

	public void setSecurityuser(String securityuser) {
		this.securityuser = securityuser;
	}

}
