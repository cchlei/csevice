package com.trgis.trmap.personal.mobile.controller.result;

public class STATUSCODE {

	// 0 成功
	public static final int SUCCESS = 0;
	
	// >=100 认证失败

	/**
	 * 认证失败
	 */
	public static final int AUTHCATION_ERROR = 100;
	
	/**
	 * 账号或密码为空
	 */
	public static final int AUTHCATIONINFO_ANYBLANK_ERROR = 101;
	
	/**
	 * 未找到用户信息
	 */
	public static final int AUTHCATIONINFO_USERNAME_NOTEXIST = 101;
	
	/**
	 * 密码错误
	 */
	public static final int AUTHCATIONINFO_PASSWORD_ERROR = 102;
	
	/**
	 * 认证失败，用户状态不可用
	 */
	public static final int AUTHCATION_STATUS_ERROR = 110;
	
	// >=500 系统错误
	/**
	 * 系统运行时异常错误
	 */
	public static final int SYSTEM_ERROR = 500;


	



}
