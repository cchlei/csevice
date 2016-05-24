package com.trgis.trmap.enterprise.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 扩展属性表结构（列结构） (已发布)
 * @author Alice
 *
 * 2015年12月1日
 */
@Entity
@Table(name = "qtmap_enterprise_release_attributmeta")
public class EntRelAttributemeta implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	private Long attrid;
	
	/**
	 * 属性名称
	 * 
	 */
	private String attralias;
	
	/**
	 * 属性编码
	 * 
	 */
	private String attrcode;
	
	/**
	 * 属性类型(默认为文本型)
	 * 
	 */
	private String attrtype = "文本型";
	
	/**
	 * 对应扩展属性表名
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "layermetaid")
	private EntLayermeta layermeta;

	public Long getAttrid() {
		return attrid;
	}

	public void setAttrid(Long attrid) {
		this.attrid = attrid;
	}

	public String getAttralias() {
		return attralias;
	}

	public void setAttralias(String attralias) {
		this.attralias = attralias;
	}

	public String getAttrcode() {
		return attrcode;
	}

	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}

	public String getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}

	public EntLayermeta getLayermeta() {
		return layermeta;
	}

	public void setLayermeta(EntLayermeta layermeta) {
		this.layermeta = layermeta;
	}

	public EntRelAttributemeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntRelAttributemeta(String attralias, String attrcode, String attrtype, EntLayermeta layermeta) {
		super();
		this.attralias = attralias;
		this.attrcode = attrcode;
		this.attrtype = attrtype;
		this.layermeta = layermeta;
	}

}
