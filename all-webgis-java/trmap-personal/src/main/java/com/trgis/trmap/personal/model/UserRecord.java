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
/**
 * 事件记录表
 * @author liuyan
 * @date 2016-03-08
 */
@Entity
@Table(name = "trmap_user_record")
public class UserRecord implements Serializable {

	
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
	 * 专题id
	 */
	@JoinColumn(name = "topic_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private UserTopic topic;
	
	/**
	 * 标题
	 */
	@Column(name = "title", length = 10)
	private String title;

	/**
	 * 描述
	 */
	@Column(name = "description",length = 5000)
	private String description;


	/**
	 * 发生时间
	 */
	@Column(name = "occurtime" )
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date occurtime;
	
	/**
	 * 创建时间
	 */
	@Column(name = "creatime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date creatime  = new Date();
	
	/**
	 * 地点名称
	 */
	@Column(name = "addrname",length = 64)
	private String addrname;
	
	/**
	 * 地点坐标
	 */
	@Column(name = "geom",length =2000)
	private String  geom;
	
	
	/**
	 * 矢量类型
	 *
	 */
	@Column(name = "record_type",length = 8)
	private String recordType =EnumUtil.RTYPE.POINT.getValue();
	
	/**
	 * 分享标志
	 * 状态包括：WFX(0, "未分享"), YFX(1, "已分享");
	 * 相关操作包括：
	 */
	@Column(name = "shareflag",length = 8)
	private Integer shareflag = EnumUtil.SHAREFLAG.WFX.getValue();
	
	/**
	 * 删除标志
	 * NOMAL(0, "未删除"), DEL(1, "已删除");
	 */
	@Column(name = "delflag",length = 8)
	private Integer delflag = EnumUtil.DELFLAG.NOMAL.getValue();
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserTopic getTopic() {
		return topic;
	}

	public void setTopic(UserTopic topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(Date occurtime) {
		this.occurtime = occurtime;
	}

	public Date getCreatime() {
		return creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	public String getAddrname() {
		return addrname;
	}

	public void setAddrname(String addrname) {
		this.addrname = addrname;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public Integer getShareflag() {
		return shareflag;
	}

	public void setShareflag(Integer shareflag) {
		this.shareflag = shareflag;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserRecord(){}
	
	public UserRecord( UserTopic topic, String title, String description, Date occurtime,
			String addrname, String geom, String recordType, Integer shareflag, Integer delflag) {
		super();
		this.topic = topic;
		this.title = title;
		this.description = description;
		this.occurtime = occurtime;
		this.addrname = addrname;
		this.geom = geom;
		this.recordType = recordType;
		this.shareflag = shareflag;
		this.delflag = delflag;
	}

	
}
