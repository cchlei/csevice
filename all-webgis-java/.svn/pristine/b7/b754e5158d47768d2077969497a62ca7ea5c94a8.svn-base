package com.trgis.trmap.userauth.model.emun;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户状态枚举类
 * 
 * @author zhangqian
 *
 */
public enum UserStatus {

	INACTIVE("0", "inactive", "账号未激活，请前往注册邮箱激活"), // 未激活
	ACTIVE("1", "active", "账号正常"), // 正常
	LOCKED("2", "locked", "账号已被锁定，请联系管理员"), // 冻结
	CLOSED("3", "closed", "由于违法平台使用规则，该账号已封停"); // 封停

	private String value;
	private String name;
	private String message;

	UserStatus(String value, String name, String message) {
		this.value = value;
		this.name = name;
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static UserStatus getStatus(String value) {
		for (UserStatus status : UserStatus.values()) {
			if (StringUtils.equals(status.getValue(), value)) {
				return status;
			}
		}
		return null;
	}
	
	public static UserStatus getStatusByName(String name) {
		for (UserStatus status : UserStatus.values()) {
			if (StringUtils.equalsIgnoreCase(status.getName(), name)) {
				return status;
			}
		}
		return null;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
