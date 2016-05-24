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
 * @ClassName: UserGroup
 * @Description: 用户组
 * @author yanpeng
 * @date 2016年3月22日 下午5:31:15
 */
@Entity
@Table(name = "trmap_user_group")
public class UserGroup implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long gid;
	
	/**
	 * 组名称
	 */
	@Column(name = "group_name", length = 20)
	private String groupName;
	
	/**
	 * 删除标志
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag",length = 8)
	private Integer delflag = EnumUtil.DELFLAG.NOMAL.getValue();

	/**
	 * 创建时间
	 */
	@Column(name = "occurtime")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date occurtime = new Date();

	/**
	 * 创建者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Date getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}