package com.trgis.trmap.hstore.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
/**
 * 蜻蜓个人地图矢量扩展属性
 * @author doris
 * @date 2015-09-14
 */
import org.hibernate.annotations.TypeDef;

import com.trgis.trmap.hstore.HstoreUserType;
/**
 * 矢量扩展属性(已发布)
 * @author Alice
 *
 * 2015年12月7日
 */
@Entity
@Table(name = "qtmap_enterprise_release_properties")
@TypeDef(name = "qtmap_enterprise_release_properties", typeClass = HstoreUserType.class)
public class EntRelMapProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 * 
	 */
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 用户id  
	 */
	@Column(name = "userid")
	private Long userId;
	/**
	 * 地图id  
	 */
	@Column(name = "mapid")
	private Long mapId;
	
	/**
	 * 地图矢量id  
	 */
	@Column(name = "featureid")
	private Long featureId;
	/**
	 * 矢量扩展列
	 */
	@Type(type = "hstore")
	@Column(columnDefinition = "hstore")
	private Map<String, String> stores = new HashMap<String, String>();
	public EntRelMapProperties() {
		super();
	}
	public EntRelMapProperties(Long id, Long userId, Long mapId, Long featureId, Map<String, String> stores) {
		super();
		this.id = id;
		this.userId = userId;
		this.mapId = mapId;
		this.featureId = featureId;
		this.stores = stores;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Map<String, String> getStores() {
		return stores;
	}

	public void setStores(Map<String, String> stores) {
		this.stores = stores;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMapId() {
		return mapId;
	}
	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}
	public Long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}


}
