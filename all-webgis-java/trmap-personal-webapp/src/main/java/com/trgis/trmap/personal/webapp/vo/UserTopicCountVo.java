package com.trgis.trmap.personal.webapp.vo;

public class UserTopicCountVo {
	
	private Long alltheme = 0l;
	private Long opentheme  = 0l;
	private Long privatetheme  = 0l;
	private Long sharetheme  = 0l;
	private Long focustheme  = 0l;
	private Long collectiontheme  = 0l;
	
	public Long getAlltheme() {
		return alltheme;
	}
	public void setAlltheme(Long alltheme) {
		this.alltheme = alltheme;
	}
	public Long getOpentheme() {
		return opentheme;
	}
	public void setOpentheme(Long opentheme) {
		this.opentheme = opentheme;
	}
	public Long getPrivatetheme() {
		return privatetheme;
	}
	public void setPrivatetheme(Long privatetheme) {
		this.privatetheme = privatetheme;
	}
	public Long getSharetheme() {
		return sharetheme;
	}
	public void setSharetheme(Long sharetheme) {
		this.sharetheme = sharetheme;
	}
	public Long getFocustheme() {
		return focustheme;
	}
	public void setFocustheme(Long focustheme) {
		this.focustheme = focustheme;
	}
	public Long getCollectiontheme() {
		return collectiontheme;
	}
	public void setCollectiontheme(Long collectiontheme) {
		this.collectiontheme = collectiontheme;
	}
	public UserTopicCountVo(Long alltheme, Long opentheme, Long privatetheme, Long sharetheme, Long focustheme,
			Long collectiontheme) {
		super();
		this.alltheme = alltheme;
		this.opentheme = opentheme;
		this.privatetheme = privatetheme;
		this.sharetheme = sharetheme;
		this.focustheme = focustheme;
		this.collectiontheme = collectiontheme;
	}
	public UserTopicCountVo() {
		super();
	}
	
}
