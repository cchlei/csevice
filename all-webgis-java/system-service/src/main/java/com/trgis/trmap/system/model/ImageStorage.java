package com.trgis.trmap.system.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trgis.trmap.system.util.EnumUtil.DELFLAG;
import com.trgis.trmap.system.util.EnumUtil.IMAGETYPE;





/**
 * 
 * @author majl
 * @Description 默认图库
 * @data 2016年4月12日
 */
@Entity
@Table(name= "images_torage")
public class ImageStorage implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//图库名称
	@Column(name="name")
	private String name;
	
	//图库地址 
	@Column(name="ossid")
	private String ossid;
	
	//类型
	@Column(name = "image_type" ,length=50)
	private Integer imageType=IMAGETYPE.TOPIC.getValue();
	
	/**
	 * 删除标志	delflag	integer	
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag", length = 50)
	private Integer delflag=DELFLAG.NOMAL.getValue();
	
	//删除标志名称
	private String delflagName;
	
	//图片类型名称
	private String imageTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOssid() {
		return ossid;
	}

	public void setOssid(String ossid) {
		this.ossid = ossid;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public String getDelflagName() {
		return delflagName;
	}

	public void setDelflagName(String delflagName) {
		this.delflagName = delflagName;
	}

	public String getImageTypeName() {
		return imageTypeName;
	}

	public void setImageTypeName(String imageTypeName) {
		this.imageTypeName = imageTypeName;
	}

}
