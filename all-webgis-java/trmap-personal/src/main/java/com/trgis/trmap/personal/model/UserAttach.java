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
 *  附件表
 * @author liuyan
 * @date 2016-03-08
 */
@Entity
@Table(name = "trmap_user_attach")
public class UserAttach implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 外键
	 * 事件id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rid")
	private UserRecord userRecord;
	
	/**
	 * 云端id
	 */
	@Column(name = "ossid", length = 255)
	private String  ossid;
	
	/**
	 * 附件名称
	 */
	@Column(name = "attach_name", length = 64)
	private String attachName;

	/**
	 * 附件大小
	 */
	@Column(name = "attach_size" )
	private Long  attachSize;
	
	/**
	 * 附件后缀
	 */
	@Column(name = "attach_suffix" ,length = 8)
	private String  attachSuffix;
	
	/**
	 * 上传时间
	 */
	@Column(name = "upload_time" )
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date uploadTime = new Date();
	
	/**
	 * 删除标志	delflag	integer	
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag")
	private Integer delflag=EnumUtil.DELFLAG.NOMAL.getValue();
	
	/**
	 * 用户
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "upload_user_id")
	private User user;
		
    public UserAttach(){}

	

	public UserAttach(String ossid, String attachName, Long attachSize, String attachSuffix, Integer delflag,
			User user) {
		super();
		this.ossid = ossid;
		this.attachName = attachName;
		this.attachSize = attachSize;
		this.attachSuffix = attachSuffix;
		this.delflag = delflag;
		this.user = user;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRecord getUserRecord() {
		return userRecord;
	}

	public void setUserRecord(UserRecord userRecord) {
		this.userRecord = userRecord;
	}

	public String getOssid() {
		return ossid;
	}

	public void setOssid(String ossid) {
		this.ossid = ossid;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public Long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public String getAttachSuffix() {
		return attachSuffix;
	}

	public void setAttachSuffix(String attachSuffix) {
		this.attachSuffix = attachSuffix;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

