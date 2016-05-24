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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 用户申请服务记录
 * @author Alice
 *
 * 2015年12月24日
 */
@Entity
@Table(name = "qtmap_enterprise_application")  
public class EntApplication implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 生成编号(yyyyMMdd-0001)
	 */
	@Column(length = 32)
	private String number;
	/**
	 * 申请方
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "getter_id")
	private User getter;
	/**
	 * 发布方
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "setter_id")
	private User setter;
	/**
	 * 地图服务
	 */
	//@JsonIgnore
	//@JSONField(label="secret")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "map_id")
	private EntRelUserMap relUserMap;
	/**
	 * 使用年限
	 */
	@Column
	private Integer applymonth;
	/**
	 * 申请的接口
	 */
	@Column
	private String serviceType;
	/**
	 * 申请的字段
	 */
	@Column
	private String attributes;
	/**
	 * 申请时间
	 */
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date applytime = new Date();

	/**
	 * 审批状态（默认为未处理）
	 */
	@Column(name = "isapproved",length = 4)
	private Integer isapproved = EnumUtil.ISAPPROVED.UNTREATED.getValue();
	/**
	 * 审核时间
	 */
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date approvedtime;
	/**
	 * 审核意见
	 */
	@Column
	private String suggestion;
	
	/**add by doris at 2016-1-5**/
	/**
	 * 截止日期
	 */
	@Transient
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date utilDate;
	/**
	 * 申请的字段名称
	 */
	@Transient
	private String attributesName;
	/**
	 * 申请方企业
	 */
	@Transient
	private String gettercomp;
	/**
	 * 发布方企业
	 */
	@Transient
	private String settercomp;
	/**
	 * 续约订单
	 */
	@Column
	private String renumber;
	/**
	 * 是否为发布方设置了暂停状态
	 */
	@Column
	private Integer ispause;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public User getGetter() {
		return getter;
	}
	public void setGetter(User getter) {
		this.getter = getter;
	}
	public User getSetter() {
		return setter;
	}
	public void setSetter(User setter) {
		this.setter = setter;
	}
	public EntRelUserMap getRelUserMap() {
		return relUserMap;
	}
	public void setRelUserMap(EntRelUserMap relUserMap) {
		this.relUserMap = relUserMap;
	}
	public Integer getApplymonth() {
		return applymonth;
	}
	public void setApplymonth(Integer applymonth) {
		this.applymonth = applymonth;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public Date getApplytime() {
		return applytime;
	}
	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}
	public Integer getIsapproved() {
		return isapproved;
	}
	public void setIsapproved(Integer isapproved) {
		this.isapproved = isapproved;
	}
	public Date getApprovedtime() {
		return approvedtime;
	}
	public void setApprovedtime(Date approvedtime) {
		this.approvedtime = approvedtime;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Date getUtilDate() {
		return utilDate;
	}
	public void setUtilDate(Date utilDate) {
		this.utilDate = utilDate;
	}
	public String getAttributesName() {
		return attributesName;
	}
	public void setAttributesName(String attributesName) {
		this.attributesName = attributesName;
	}
	public String getRenumber() {
		return renumber;
	}
	public void setRenumber(String renumber) {
		this.renumber = renumber;
	}
	public String getGettercomp() {
		return gettercomp;
	}
	public void setGettercomp(String gettercomp) {
		this.gettercomp = gettercomp;
	}
	public String getSettercomp() {
		return settercomp;
	}
	public void setSettercomp(String settercomp) {
		this.settercomp = settercomp;
	}
	public Integer getIspause() {
		return ispause;
	}
	public void setIspause(Integer ispause) {
		this.ispause = ispause;
	}
}
