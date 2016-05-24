package com.trgis.trmap.enterprise.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 扩展属性表结构（表名） (已发布)
 * @author Alice
 *
 * 2015年12月1日
 */
@Entity
@Table(name = "qtmap_enterprise_release_layermeta")
public class EntRelLayermeta implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	private Long layermetaid;
	/**
	 * 企业地图
	 */
	//预留关系	@ManyToOne(fetch = FetchType.EAGER)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entmap_id")
	private EntUserMap map;
	
	public Long getLayermetaid() {
		return layermetaid;
	}
	public void setLayermetaid(Long layermetaid) {
		this.layermetaid = layermetaid;
	}
	public EntUserMap getMap() {
		return map;
	}
	public void setMap(EntUserMap map) {
		this.map = map;
	}
	public EntRelLayermeta() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EntRelLayermeta(EntUserMap map) {
		super();
		this.map = map;
	}

}
