package com.trgis.trmap.qtuser.model;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trgis.trmap.qtuser.utils.EnumUtil;
/**
 * 蜻蜓个人地图矢量表
 * @author doris
 * @date 2015-09-11
 */
@Entity
@Table(name = "qtuser_graphics")
public class MapGraphics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 个人地图
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "map_id")
	private UserMap userMap;
	/**
	 * 矢量名称
	 */
	@Column(name = "gname", length = 64)
	private String gname = "未命名标注";
	/**
	 * 矢量图形
	 */
	@Column(name = "geom")
	private String geom;																												
	/**
	 * 样式描述
	 */
	@Column(name = "gstyle")
	private String gstyle;
	/**
	 * 矢量类型
	 */
	@Column(name = "gtype", length = 8)
	private String gtype;
	/**
	 * 绘制时间
	 */
	@Column(name = "gcreatedate")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date gcreatedate = new Date();
	/**
	 * 发生时间
	 */
	@Column(name = "occurtime")
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date occurtime;
	/**
	 * 
	 */
	@Column(name = "radius")
	private double radius;
	/**
	 * 
	 */
	@Column(name = "color", length =50)
	private String color;
	/**
	 * 
	 */
	@Column(name = "symbol", length =255)
	private String symbol;
	/**
	 * 
	 */
	@Column(name = "strokeWidth")
	private double strokeWidth;
	/**
	 * 
	 */
	@Column(name = "strokeColor", length =50)
	private String strokeColor;
	/**
	 * 
	 */
	@Column(name = "strokeOpacity")
	private double strokeOpacity;
	/**
	 *  填充色
	 */
	@Column(name = "fillColor", length = 50)
	private String fillColor;
	/**
	 * 
	 */
	@Column(name = "fillOpacity")
	private double fillOpacity;
	/**
	 * 消息删除
	 */
	@Column(name = "delflag",length = 2)
	private Integer delflag = EnumUtil.DELFLAG.NOMAL.getValue();
	public MapGraphics() {
		super();
	}
	/**
	 * 移动端专用
	 */
	@Column(name = "date_id", length = 50)
	private String dateID;
	
	public MapGraphics(Long id, UserMap userMap, String gname, String geom, String gstyle, String gtype,
			Date gcreatedate, double radius, String color, String symbol, double strokeWidth, String strokeColor,
			double strokeOpacity, String fillColor, double fillOpacity, Integer delflag) {
		super();
		this.id = id;
		this.userMap = userMap;
		this.gname = gname;
		this.geom = geom;
		this.gstyle = gstyle;
		this.gtype = gtype;
		this.gcreatedate = gcreatedate;
		this.radius = radius;
		this.color = color;
		this.symbol = symbol;
		this.strokeWidth = strokeWidth;
		this.strokeColor = strokeColor;
		this.strokeOpacity = strokeOpacity;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
		this.delflag = delflag;
	}

	public String getGstyle() {
		return gstyle;
	}
	public void setGstyle(String gstyle) {
		this.gstyle = gstyle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserMap getUserMap() {
		return userMap;
	}
	public void setUserMap(UserMap userMap) {
		this.userMap = userMap;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getGtype() {
		return gtype;
	}
	public void setGtype(String gtype) {
		this.gtype = gtype;
	}
	public Date getGcreatedate() {
		return gcreatedate;
	}
	public void setGcreatedate(Date gcreatedate) {
		this.gcreatedate = gcreatedate;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public double getStrokeOpacity() {
		return strokeOpacity;
	}
	public void setStrokeOpacity(double strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public double getFillOpacity() {
		return fillOpacity;
	}
	public void setFillOpacity(double fillOpacity) {
		this.fillOpacity = fillOpacity;
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

	public String getDateID() {
		return dateID;
	}

	public void setDateID(String dateID) {
		this.dateID = dateID;
	}
}
