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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 天润云企业地图
 * @author doris
 * @date 2015-09-10
 */
@Entity
@Table(name = "qtmap_enterprise_usermap")  
public class EntUserMap implements Serializable {

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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * 地图名称
	 */
	@Column(name = "mapname", length = 32)
	private String mapname;

	/**
	 * 地图描述
	 */
	@Column(name = "mapdesc",length = 255)
	private String mapdesc;
	/**
	 * 分享状态
	 * 状态包括：已发布、未发布
	 * 相关操作包括：
	 */
	@Column(name = "isshare",length = 4)
	private Integer isshare;
	/**
	 * 分享地址
	 */
	@Column(name = "shareurl",length =255)
	private String shareurl;
	/**
	 * 地图图标
	 */
	@Column(name = "umicon",length =255)
	private String umicon;
	
	/**
	 * 默认底图
	 */
	@Column(name = "umdefaultbase",length =16)
	private String umdefaultbase="彩色注记版";
	
	/**
	 * 删除状态 0正常1删除
	 */
	@Column(name = "delflag",length = 4)
	private Integer delflag = EnumUtil.DELFLAG.NOMAL.getValue();

	/**
	 * 创建时间
	 */
	@Column(name = "umcreatetime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date umcreatetime = new Date();
	/**
	 * 接口类型
	 */
	@Column(name = "serviceType")
	private String serviceType;
	/**
	 * 标签
	 */
	@Column(name = "tags")
	private String tags;
	/**
	 * 行政区划
	 */
	@Column(name = "area")
	private String area;
	/**
	 * 有效区间（一年、两年、三年、五年未发布时只做保存，已发布后才根据发布时间计算validitytime）
	 */
	@Column(name = "validityregion")
	private Integer validityregion;
	
	public EntUserMap() {
		super();
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
	public String getMapname() {
		return mapname;
	}
	public void setMapname(String mapname) {
		this.mapname = mapname;
	}
	public String getMapdesc() {
		return mapdesc;
	}
	public void setMapdesc(String mapdesc) {
		this.mapdesc = mapdesc;
	}
	public Integer getIsshare() {
		return isshare;
	}
	public void setIsshare(Integer isshare) {
		this.isshare = isshare;
	}
	public String getShareurl() {
		return shareurl;
	}
	public void setShareurl(String shareurl) {
		this.shareurl = shareurl;
	}
	public String getUmicon() {
		return umicon;
	}
	public void setUmicon(String umicon) {
		this.umicon = umicon;
	}
	public String getUmdefaultbase() {
		return umdefaultbase;
	}
	public void setUmdefaultbase(String umdefaultbase) {
		this.umdefaultbase = umdefaultbase;
	}
	public Date getUmcreatetime() {
		return umcreatetime;
	}
	public void setUmcreatetime(Date umcreatetime) {
		this.umcreatetime = umcreatetime;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getDelflag() {
		return delflag;
	}
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	public Integer getValidityregion() {
		return validityregion;
	}
	public void setValidityregion(Integer validityregion) {
		this.validityregion = validityregion;
	}
}
