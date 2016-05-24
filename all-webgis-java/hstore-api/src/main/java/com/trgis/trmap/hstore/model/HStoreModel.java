/**
 * 
 */
package com.trgis.trmap.hstore.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.trgis.trmap.hstore.HstoreUserType;

/**
 * NoSql 存储对象
 * 
 * @author zhangqian
 *
 */
@Entity
@TypeDef(name = "hstore", typeClass = HstoreUserType.class)
public class HStoreModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String tableName;

	@Type(type = "hstore")
	@Column(columnDefinition = "hstore")
	private Map<String, String> stores = new HashMap<String, String>();

	public HStoreModel() {
	}

	public HStoreModel(Long id, String tableName, Map<String, String> stores) {
		this.id = id;
		this.tableName = tableName;
		this.stores = stores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getStores() {
		return stores;
	}

	public void setStores(Map<String, String> stores) {
		this.stores = stores;
	}

}
