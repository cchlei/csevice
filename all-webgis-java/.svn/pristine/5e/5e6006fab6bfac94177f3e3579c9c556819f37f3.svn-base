package com.trgis.trmap.enterprise.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 天润云企业认证信息表
 * @author doris
 * @date 2015-11-27
 */
@Entity
@Table(name = "qtmap_enterprise_cainfo")
public class EntCainfo implements Serializable{
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
	 * 用户
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * 企业名称
	 */
	@Column(name = "enterprisename", length = 64)
	private String enterpriseName;
	/**
	 * 简介
	 */
	@Column(name = "comment", length = 255)
	private String comment;
	/**
	 * 地址
	 */
	@Column(name = "address", length = 255)
	private String address;
	/**
	 * 营业执照号码
	 */
	@Column(name = "enterpriseid", length = 16)
	private String enterpriseId;
	/**
	 * 法人姓名
	 */
	@Column(name = "bossname", length = 255)
	private String bossName;
	/**
	 * 法人身份证号码
	 */
	@Column(name = "bossidentifyid", length = 18)
	private String bossIdentifyId;
	/**
	 * 管理员姓名
	 */
	@Column(name = "managername", length = 20)
	private String managerName;
	/**
	 * 管理员身份证号码
	 */
	@Column(name = "manageridentifyid", length = 18)
	private String managerIdentifyId;
	/**
	 * 管理员手机
	 */
	@Column(name = "managerphone", length = 13)
	private String managerPhone;
	/**
	 * 营业执照附件
	 */
	@Column(name = "enterpriseattachurl", length = 200)
	private String enterpriseAttachUrl;
	/**
	 * 管理员身份证附件
	 */
	@Column(name = "manageridentifyurl", length = 200)
	private String managerIdentifyUrl;
	/**
	 * 有效截止日期
	 */
	@Column(name = "utilvaliddate")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date utilValidDate;
	/**
	 * 认证通过时间
	 */
	@Column(name = "certificatedate")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date certificateDate;
	/**
	 * 状态
	 */
	@Column(name = "castatus", length = 2)
	private String castatus;
	
	/**
	 * 安全码
	 */
	@Column
	private String secretKey;
	/**
	 * 组织机构代码
	 */
	@Column(name = "orgid", length = 9)
	private String orgId;
	/**
	 * 组织机构代码附件
	 */
	@Column(name = "orgattachurl", length = 200)
	private String orgAttachUrl;
	/**
	 * 法人身份证附件地址
	 */
	@Column(name = "bossidentifyurl", length = 200)
	private String bossIdentifyUrl;
	/**
	 * 法人身份证证件有效期
	 */
	@Column(name = "bossidentifydate", length = 200)
	private String bossIdentifyDate;
	/**
	 * 管理员身份证证件有效期
	 */
	@Column(name = "manageridentifydate", length = 200)
	private String managerIdentifyDate;
	/**
	 * 申请审核时的驳回意见
	 */
	@Column(name = "remarks", length = 255)
	private String remarks;
	/**
	 * @author Alice
	 * 不入库，构造页面操作按钮时使用
	 */
	@Transient
	private String operation; 
	@Transient
	private String account;
	@Transient
	private String entname;
	
	public EntCainfo() {
		super();
	}
	public String getOrgAttachUrl() {
		return orgAttachUrl;
	}
	public void setOrgAttachUrl(String orgAttachUrl) {
		this.orgAttachUrl = orgAttachUrl;
	}
	public String getBossIdentifyUrl() {
		return bossIdentifyUrl;
	}
	public void setBossIdentifyUrl(String bossIdentifyUrl) {
		this.bossIdentifyUrl = bossIdentifyUrl;
	}
	public EntCainfo(Long id, User user, String enterpriseName, String comment, String address, String enterpriseId,
			String bossName, String bossIdentifyId, String managerName, String managerIdentifyId, String managerPhone,
			String enterpriseAttachUrl, String managerIdentifyUrl, Date utilValidDate, Date certificateDate,
			String castatus, String secretKey, String orgId, String orgAttachUrl, String bossIdentifyUrl,
			String bossIdentifyDate, String managerIdentifyDate) {
		super();
		this.id = id;
		this.user = user;
		this.enterpriseName = enterpriseName;
		this.comment = comment;
		this.address = address;
		this.enterpriseId = enterpriseId;
		this.bossName = bossName;
		this.bossIdentifyId = bossIdentifyId;
		this.managerName = managerName;
		this.managerIdentifyId = managerIdentifyId;
		this.managerPhone = managerPhone;
		this.enterpriseAttachUrl = enterpriseAttachUrl;
		this.managerIdentifyUrl = managerIdentifyUrl;
		this.utilValidDate = utilValidDate;
		this.certificateDate = certificateDate;
		this.castatus = castatus;
		this.secretKey = secretKey;
		this.orgId = orgId;
		this.orgAttachUrl = orgAttachUrl;
		this.bossIdentifyUrl = bossIdentifyUrl;
		this.bossIdentifyDate = bossIdentifyDate;
		this.managerIdentifyDate = managerIdentifyDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getBossIdentifyId() {
		return bossIdentifyId;
	}
	public void setBossIdentifyId(String bossIdentifyId) {
		this.bossIdentifyId = bossIdentifyId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerIdentifyId() {
		return managerIdentifyId;
	}
	public void setManagerIdentifyId(String managerIdentifyId) {
		this.managerIdentifyId = managerIdentifyId;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getEnterpriseAttachUrl() {
		return enterpriseAttachUrl;
	}
	public void setEnterpriseAttachUrl(String enterpriseAttachUrl) {
		this.enterpriseAttachUrl = enterpriseAttachUrl;
	}
	public String getManagerIdentifyUrl() {
		return managerIdentifyUrl;
	}
	public void setManagerIdentifyUrl(String managerIdentifyUrl) {
		this.managerIdentifyUrl = managerIdentifyUrl;
	}
	
	public Date getUtilValidDate() {
		return utilValidDate;
	}

	public void setUtilValidDate(Date utilValidDate) {
		this.utilValidDate = utilValidDate;
	}

	public Date getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getCastatus() {
		return castatus;
	}
	public void setCastatus(String castatus) {
		this.castatus = castatus;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getBossIdentifyDate() {
		return bossIdentifyDate;
	}
	public void setBossIdentifyDate(String bossIdentifyDate) {
		this.bossIdentifyDate = bossIdentifyDate;
	}
	public String getManagerIdentifyDate() {
		return managerIdentifyDate;
	}
	public void setManagerIdentifyDate(String managerIdentifyDate) {
		this.managerIdentifyDate = managerIdentifyDate;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	/**
	 * 展示状态
     * @author Alice
     * AUDITWAIT("0", "待审核"), AUDITING("1", "已取消"), AUDITED("2", "认证通过"), AUDITFAIL("3", "认证未通过");
     */
    public String getCastatusStr() {
        String ret = "";
        if(this.castatus.equals(EnumUtil.CASTATUS.AUDITWAIT.getValue())){
        	ret = "待审核";
        }else if(this.castatus.equals(EnumUtil.CASTATUS.CANCELED.getValue())){
        	ret = "已取消";
        }else if(this.castatus.equals(EnumUtil.CASTATUS.AUDITED.getValue())){
        	ret = "认证通过";
        }else if(this.castatus.equals(EnumUtil.CASTATUS.AUDITFAIL.getValue())){
        	ret = "认证未通过";
        }
        return ret;
    }
}
