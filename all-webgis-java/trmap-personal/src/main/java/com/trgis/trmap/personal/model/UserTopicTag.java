package com.trgis.trmap.personal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description:  专题名称备选表
 * @author: Alice
 */
@Entity
@Table(name = "trmap_user_topic_tag")
public class UserTopicTag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  主键 id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 备选专题名称 name
	 */
	@Column(name = "name", length = 10)
	private String 	name;
	
	/**
	 * 排序 sort
	 */
	@Column(name = "sort")
	private Integer sort;

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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
