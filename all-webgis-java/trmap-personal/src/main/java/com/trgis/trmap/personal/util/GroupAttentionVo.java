package com.trgis.trmap.personal.util;

import java.util.List;

public class GroupAttentionVo {
	private Long gid;
	private String name;
	private boolean shrink = false;
	private List<UserVo> user;
	

	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isShrink() {
		return shrink;
	}


	public void setShrink(boolean shrink) {
		this.shrink = shrink;
	}


	public List<UserVo> getUser() {
		return user;
	}


	public void setUser(List<UserVo> user) {
		this.user = user;
	}


	public static class UserVo{
		private Long userId;
		private String userName;
		private String headimg;
		private boolean checked = false;
		
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getHeadimg() {
			return headimg;
		}
		public void setHeadimg(String headimg) {
			this.headimg = headimg;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
}
