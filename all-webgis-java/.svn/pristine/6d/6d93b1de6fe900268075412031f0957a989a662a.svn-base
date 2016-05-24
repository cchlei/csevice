package com.trgis.trmap.qtuser.model;

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
import com.trgis.trmap.qtuser.utils.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 蜻蜓个人地图
 * @author doris
 * @date 2015-09-10
 */
@Entity
@Table(name = "qtuser_usermap")
public class UserMap implements Serializable {

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
	 * 状态包括：未分享，审批中，已分享 
	 * 相关操作包括：申请分享，取消分享，审批
	 */
	@Column(name = "isshare",length = 4)
	private Integer isshare = EnumUtil.SHARE.WFX.getValue();
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
	 * 删除状态 
	 */
	@Column(name = "mapdelflag",length = 4)
	private Integer mapdelflag = EnumUtil.DELFLAG.NOMAL.getValue();
	/**
	 * 创建时间
	 */
	@Column(name = "umcreatetime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date umcreatetime = new Date();
	/**
	 * 是否是移动端 
	 */
	@Column
	private boolean app = false;
	
	public UserMap() {
		super();
	}
	public UserMap(Long id, User user, String mapname, String mapdesc, Integer isshare, String shareurl, String umicon,
			String umdefaultbase, Integer mapdelflag, Date umcreatetime) {
		super();
		this.id = id;
		this.user = user;
		this.mapname = mapname;
		this.mapdesc = mapdesc;
		this.isshare = isshare;
		this.shareurl = shareurl;
		this.umicon = umicon;
		this.umdefaultbase = umdefaultbase;
		this.mapdelflag = mapdelflag;
		this.umcreatetime = umcreatetime;
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
	public Integer getMapdelflag() {
		return mapdelflag;
	}
	public void setMapdelflag(Integer mapdelflag) {
		this.mapdelflag = mapdelflag;
	}
	public Date getUmcreatetime() {
		return umcreatetime;
	}
	public void setUmcreatetime(Date umcreatetime) {
		this.umcreatetime = umcreatetime;
	}
	public boolean isApp() {
		return app;
	}
	public void setApp(boolean app) {
		this.app = app;
	}
}
