package com.trgis.trmap.enterprise.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 企业地图服务(已发布)
 * @author Alice
 *
 * 2015年12月7日
 */
@Entity
@Table(name = "qtmap_enterprise_release_usermap")  
public class EntRelUserMap implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
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
	 * 删除状态 0整除1删除
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
	 * 服务标签
	 */
	@Column(name = "tags")
	private String tags;
	/**
	 * 行政区划
	 */
	@Column(name = "area")
	private String area;
	
	//以下为不同与企业图层的服务属性
	/**
	 * 上线状态
	 * 状态包括：上线、下线
	 * 相关操作包括：
	 */
	@Column(name = "isonline",length = 4)
	private Integer isonline = EnumUtil.ONLINE.SX.getValue();
	/**
	 * 企业地图
	 */
	//预留关系	@ManyToOne(fetch = FetchType.EAGER)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entmap_id")
	private EntUserMap emap;
	/**
	 * 要素数量
	 */
	@Column(name = "featurecount")
	private Long featurecount = 0l;
	/**
	 * 范围
	 */
	@Column(name = "scope")
	private String scope = "";
	/**
	 * 坐标系
	 */
	@Column(name = "coordinate")
	private String coordinate = "WGS84";
	/**
	 * 发布时间
	 */
	@Column(name = "releasetime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date releasetime = new Date();
	/**
	 * 有效区间（一年、两年、三年、五年未发布时只做保存，已发布后才根据发布时间计算validitytime）
	 */
	@Column(name = "validityregion")
	private Integer validityregion;
	/**
	 * 有效时间
	 */
	@Column(name = "validitytime")
	@JsonFormat(pattern="yyyy年MM月dd日",timezone="GMT+8")
	private Date validitytime = new Date();
	/**
	 * 更新时间
	 */
	@Column(name = "updateretime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date updateretime = new Date();
	/**
	 * 正常使用状态申请ID
	 */
	@Column
	private String passids = "";
	/**
	 * 未审批状态的申请ID
	 */
	@Column
	private String untreatedids = "";
	
	public EntRelUserMap() {
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
	public Integer getDelflag() {
		return delflag;
	}
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
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
	public Integer getIsonline() {
		return isonline;
	}
	public void setIsonline(Integer isonline) {
		this.isonline = isonline;
	}
	public EntUserMap getEmap() {
		return emap;
	}
	public void setEmap(EntUserMap emap) {
		this.emap = emap;
	}
	public Long getFeaturecount() {
		return featurecount;
	}
	public void setFeaturecount(Long featurecount) {
		this.featurecount = featurecount;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public Date getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}
	public Date getValiditytime() {
		return validitytime;
	}
	public void setValiditytime(Date validitytime) {
		this.validitytime = validitytime;
	}
	public Date getUpdateretime() {
		return updateretime;
	}
	public void setUpdateretime(Date updateretime) {
		this.updateretime = updateretime;
	}
	public Integer getValidityregion() {
		return validityregion;
	}
	public void setValidityregion(Integer validityregion) {
		this.validityregion = validityregion;
	}
	public String getPassids() {
		return passids;
	}
	public void setPassids(String passids) {
		this.passids = passids;
	}
	public String getUntreatedids() {
		return untreatedids;
	}
	public void setUntreatedids(String untreatedids) {
		this.untreatedids = untreatedids;
	}
}
