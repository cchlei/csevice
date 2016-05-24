package com.trgis.trmap.personal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserIntegral
 * @Description: 用户积分等级表
 * @author yanpeng
 * @date 2016年3月29日 上午10:58:07
 */
@Entity
@Table(name = "trmap_user_integral")
public class UserIntegral implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 默认空间大小
	 */
	@Column(name = "space_size" )
	private Long  spaceSize = 100L;
	/**
	 * 默认可发表专题数量
	 */
	@Column(name = "topic_count" )
	private Long  topicCount =200l;
	
	/**
	 * 积分值
	 */
	@Column(name = "integral" )
	private Long  integral =0l;
	
	/**
	 * 等级
	 */
	@Column(name = "grade" )
	private Integer  grade =1;
	
	/**
	 * 用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(Long spaceSize) {
		this.spaceSize = spaceSize;
	}

	public Long getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Long topicCount) {
		this.topicCount = topicCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
