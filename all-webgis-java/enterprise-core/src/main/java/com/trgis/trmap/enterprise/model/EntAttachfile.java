package com.trgis.trmap.enterprise.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 矢量附件
 * @author Alice
 * 2015年9月19日
 */
@Entity
@Table(name = "qtmap_enterprise_attachfile")
public class EntAttachfile implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 所属矢量
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "map_graphics_id")
	private EntMapGraphics mapGraphics;
	/**
	 * 文件名
	 */
	private String attachName;
	
	/**
	 * 文件类型（后缀名）
	 */
	private String attachSuffix;
	
	/**
	 * 文件类型（后缀名）
	 */
	private Integer attachType;
	
	/**
	 * 远程服务器文件id
	 */
	private String ossid;
	
	/**
	 * 文件大小
	 */
	private Long attachSize;
	/**
	 * 文件尺寸
	 */
	private String attachStr;
	
	/**
	 * 上传用户ID
	 */
	private Long uploadUserId;
	
	/**
	 * 上传时间
	 */
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date uploadTime = new Date();
	
	/**
	 * MD5
	 */
	private String attachMD5;

	public EntAttachfile() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Long getId() {
		return id;
	}

	public EntAttachfile(Long id, EntMapGraphics mapGraphics, String attachName, String attachSuffix, Integer attachType,
			String ossid, Long attachSize, String attachStr, Long uploadUserId, Date uploadTime, String attachMD5) {
		super();
		this.id = id;
		this.mapGraphics = mapGraphics;
		this.attachName = attachName;
		this.attachSuffix = attachSuffix;
		this.attachType = attachType;
		this.ossid = ossid;
		this.attachSize = attachSize;
		this.attachStr = attachStr;
		this.uploadUserId = uploadUserId;
		this.uploadTime = uploadTime;
		this.attachMD5 = attachMD5;
	}




	public void setId(Long id) {
		this.id = id;
	}

	public EntMapGraphics getMapGraphics() {
		return mapGraphics;
	}

	public void setMapGraphics(EntMapGraphics mapGraphics) {
		this.mapGraphics = mapGraphics;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachSuffix() {
		return attachSuffix;
	}

	public void setAttachSuffix(String attachSuffix) {
		this.attachSuffix = attachSuffix;
	}

	public String getOssid() {
		return ossid;
	}

	public void setOssid(String ossid) {
		this.ossid = ossid;
	}

	public long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public long getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(Long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getAttachMD5() {
		return attachMD5;
	}

	public void setAttachMD5(String attachMD5) {
		this.attachMD5 = attachMD5;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}
	public String getAttachStr() {
		return attachStr;
	}
	public void setAttachStr(String attachStr) {
		this.attachStr = attachStr;
	}
	
}
