package com.trgis.trmap.personal.webapp.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserRecordVo
 * @Description: 记录浏览封装
 * @author yanpeng
 * @date 2016年3月17日 上午10:54:41
 */
public class UserRecordVo {

	public Long id;
	public String name;
	public String description;
	public String occurtime;
	public String addrname;
	public String geom;
	public String record_type;
	public Integer shareflag;
	public List<String> piclist = new ArrayList<String>();
	public Integer num_msg;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOccurtime() {
		return occurtime;
	}
	public void setOccurtime(String occurtime) {
		this.occurtime = occurtime;
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
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	public Integer getShareflag() {
		return shareflag;
	}
	public void setShareflag(Integer shareflag) {
		this.shareflag = shareflag;
	}
	public List<String> getPiclist() {
		return piclist;
	}
	public void setPiclist(List<String> piclist) {
		this.piclist = piclist;
	}
	public Integer getNum_msg() {
		return num_msg;
	}
	public void setNum_msg(Integer num_msg) {
		this.num_msg = num_msg;
	}
	
}
