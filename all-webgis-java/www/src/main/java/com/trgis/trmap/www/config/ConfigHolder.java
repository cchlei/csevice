package com.trgis.trmap.www.config;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.trgis.trmap.userauth.model.emun.UserEntity;

/**
 * 全局配置
 * 
 * @author zhangqian
 *
 */
public class ConfigHolder {

	/**
	 * 企业中心地址
	 */
	private static String enterpriseCenterUrl;

	/**
	 * 企业注册action
	 */
	private static String enterpriseRegisterAction;

	/**
	 * 登录地址
	 */
	private static String loginUrl;

	/**
	 * 用户中心地址
	 */
	private static String userCenterUrl;

	/**
	 * 用户注册action
	 */
	private static String userRegisterAction;

	@SuppressWarnings("rawtypes")
	public static String getCenterUrl() {
		// 获取附带的用户属性
		Map attrs = (Map) SecurityUtils.getSubject().getPrincipals().asList().get(1);
		String entity = (String) attrs.get("entity");
		if (StringUtils.equals(UserEntity.ENTERPRISE.getName().toUpperCase(), entity.toUpperCase())) {
			return getEnterpriseCenterUrl();
		} else {
			return getUserCenterUrl();
		}
	}

	public static String getEnterpriseCenterUrl() {
		return enterpriseCenterUrl;
	}

	public static String getEnterpriseRegisterAction() {
		return enterpriseRegisterAction;
	}

	public static String getLoginUrl() {
		return loginUrl;
	}

	public static String getUserCenterUrl() {
		return userCenterUrl;
	}

	public static String getUserRegisterAction() {
		return userRegisterAction;
	}

	public static void setEnterpriseCenterUrl(String enterpriseCenterUrl) {
		ConfigHolder.enterpriseCenterUrl = enterpriseCenterUrl;
	}

	public static void setEnterpriseRegisterAction(String enterpriseRegisterAction) {
		ConfigHolder.enterpriseRegisterAction = enterpriseRegisterAction;
	}

	public static void setLoginUrl(String loginUrl) {
		ConfigHolder.loginUrl = loginUrl;
	}

	public static void setUserCenterUrl(String userCenterUrl) {
		ConfigHolder.userCenterUrl = userCenterUrl;
	}

	public static void setUserRegisterAction(String userRegisterAction) {
		ConfigHolder.userRegisterAction = userRegisterAction;
	}

	public ConfigHolder() {
	}

}
