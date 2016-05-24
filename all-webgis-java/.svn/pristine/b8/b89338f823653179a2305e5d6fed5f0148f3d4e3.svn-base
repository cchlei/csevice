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
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;

/**
 * @ClassName: UserAttention
 * @Description: 关注
 * @author yanpeng
 * @date 2016年3月22日 下午5:31:15
 */
@Entity
@Table(name = "trmap_user_attention")
public class UserAttention implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 关注时间
	 */
	@Column(name = "occurtime")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date occurtime = new Date();

	/**
	 * 互粉状态
	 * YGZ(0, "单方关注"), HGZ(1, "互相关注");
	 */
	@Column(name = "attentionflag",length = 8)
	private Integer atteflag = EnumUtil.ATTENTIONFLAG.YGZ.getValue();
	
	/**
	 * 关注者
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuser_id")
	private User cuser;
	
	/**
	 * 被关注者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buser_id")
	private User buser;
	

	/**
	 * 被关注者所属分组
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private UserGroup userGroup = new UserGroup();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}

	public Integer getAtteflag() {
		return atteflag;
	}

	public void setAtteflag(Integer atteflag) {
		this.atteflag = atteflag;
	}

	public User getCuser() {
		return cuser;
	}

	public void setCuser(User cuser) {
		this.cuser = cuser;
	}

	public User getBuser() {
		return buser;
	}

	public void setBuser(User buser) {
		this.buser = buser;
	}
	
	

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public UserAttention(User cuser, User buser) {
		super();
		this.cuser = cuser;
		this.buser = buser;
	}

	public UserAttention() {
		super();
	}
}