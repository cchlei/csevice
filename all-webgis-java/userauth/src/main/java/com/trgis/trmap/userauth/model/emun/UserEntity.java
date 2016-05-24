package com.trgis.trmap.userauth.model.emun;

/**
 * 用户实体枚举
 * 
 * 个人用户 企业用户
 * 
 * @author zhangqian
 *
 */
public enum UserEntity {

	PERSONAL(0, "PERSONAL", "个人用户"), ENTERPRISE(1, "ENTERPRISE", "企业用户");

	private UserEntity(int index, String name, String desc) {
		this.index = index;
		this.name = name;
		this.desc = desc;
	}

	private int index;
	private String name;
	private String desc;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static UserEntity getEntity(int value) {
		for (UserEntity entity : UserEntity.values()) {
			if (entity.getIndex() == value) {
				return entity;
			}
		}
		return null;
	}
}
