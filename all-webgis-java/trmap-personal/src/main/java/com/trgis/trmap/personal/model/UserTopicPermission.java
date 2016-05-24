package com.trgis.trmap.personal.model;

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
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserTopicPermission
 * @Description: 专题的开放权限实体类
 * @author yanpeng
 * @date 2016年5月12日 下午1:32:20
 */
@Entity
@Table(name = "trmap_user_topic_permission")
public class UserTopicPermission implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private UserTopic userTopic;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date occurtime = new Date();
	/**
	 * 分组id，如果不为空，表示此分组为展开
	 */
	private Long gid;
		
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserTopic getUserTopic() {
		return userTopic;
	}
	public void setUserTopic(UserTopic userTopic) {
		this.userTopic = userTopic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getOccurtime() {
		return occurtime;
	}
	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}
	
}
