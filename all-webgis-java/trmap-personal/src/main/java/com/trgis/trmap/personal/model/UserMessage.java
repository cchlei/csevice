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
 * @ClassName: UserMessage
 * @Description: 系统消息
 * @author yanpeng
 * @date 2016年3月22日 下午5:31:15
 */
@Entity
@Table(name = "trmap_user_message")
public class UserMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 消息标题
	 */
	@Column(name = "message_title", length = 20)
	private String messageTitle;
	/**
	 * 消息内容
	 */
	@Column(name = "message_content", length = 100)
	private String messageContent;
	
	/**
	 * 删除标志
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag",length = 4)
	private Integer delflag = EnumUtil.DELFLAG.NOMAL.getValue();
	
	/**
	 * 阅读标志
	 * NOMAL(0, "未阅读"), READ(1, "已阅读"),ALL(-1,"所有")
	 */
	@Column(name = "readflag",length = 4)
	private Integer readflag = EnumUtil.READ.NOMAL.getValue();
	
	/**
	 * 消息类型
	 * WELCONME(0, "欢迎信"), FANS(1, "新增粉丝"), COMMENT(2, "新的评论");
	 */
	@Column(name = "message_type",length = 4)
	private Integer msgType = EnumUtil.MESSAGE_TYPE.WELCONME.getValue();


	/**
	 * 发送时间
	 */
	@Column(name = "occurtime")
	@JsonFormat(pattern = "yyyy年MM月dd日  HH:mm:ss", timezone = "GMT+8")
	private Date occurtime = new Date();
	
	/**
	 * 如果消息类型为新的评论，需要带着评论的id
	 * 记录id
	 */
//	@JoinColumn(name = "comment_id")
//	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name = "comment_id")
	private Long userComment;

	/**
	 * 接收者
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Integer getReadflag() {
		return readflag;
	}

	public void setReadflag(Integer readflag) {
		this.readflag = readflag;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Date getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}
	public Long getUserComment() {
		return userComment;
	}

	public void setUserComment(Long userComment) {
		this.userComment = userComment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}