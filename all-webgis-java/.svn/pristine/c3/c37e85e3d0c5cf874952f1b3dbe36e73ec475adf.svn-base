package com.trgis.trmap.qtuser.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

/**
 * 基础底图图层类
 * @author Alice
 *
 * 2015年9月28日
 */
@Entity
@Table(name = "qtuser_basemap")
public class BaseMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 透明度
	 */
	private Double opacity;
	/**
	 * 显示层级
	 */
	private Integer z_index;
	/**
	 * 关联地图id
	 */
	private Long mapId;
	/**
	 * 类别
	 */
	private String basemapType;
	/**
	 * 不入库，展示时临时使用
	 */
	@Transient
	private Boolean selected = true;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getOpacity() {
		return opacity;
	}
	public void setOpacity(Double opacity) {
		this.opacity = opacity;
	}
	public Integer getZ_index() {
		return z_index;
	}
	public void setZ_index(Integer z_index) {
		this.z_index = z_index;
	}
	public Long getMapId() {
		return mapId;
	}
	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}
	public String getBasemapType() {
		return basemapType;
	}
	public void setBasemapType(String basemapType) {
		this.basemapType = basemapType;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public BaseMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseMap(Double opacity, Integer z_index, Long mapId, String basemapType) {
		super();
		this.opacity = opacity;
		this.z_index = z_index;
		this.mapId = mapId;
		this.basemapType = basemapType;
	}

}
