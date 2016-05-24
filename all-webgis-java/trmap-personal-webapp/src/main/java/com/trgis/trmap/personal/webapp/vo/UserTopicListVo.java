package com.trgis.trmap.personal.webapp.vo;

import java.util.ArrayList;
import java.util.List;

import com.trgis.trmap.personal.model.UserTopic;
/**
 * @ClassName: UserTopicListVo
 * @Description: 日历：专题列表
 * @author yanpeng
 * @date 2016年3月15日 下午3:27:35
 */
public class UserTopicListVo {
	private List<UserTopic> myTopic= new ArrayList<UserTopic>();
	private List<UserTopic> collectTopic= new ArrayList<UserTopic>();
	public List<UserTopic> getMyTopic() {
		return myTopic;
	}
	public void setMyTopic(List<UserTopic> myTopic) {
		this.myTopic = myTopic;
	}
	public List<UserTopic> getCollectTopic() {
		return collectTopic;
	}
	public void setCollectTopic(List<UserTopic> collectTopic) {
		this.collectTopic = collectTopic;
	}
	public UserTopicListVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserTopicListVo(List<UserTopic> myTopic, List<UserTopic> collectTopic) {
		super();
		this.myTopic = myTopic;
		this.collectTopic = collectTopic;
	}
	
}
