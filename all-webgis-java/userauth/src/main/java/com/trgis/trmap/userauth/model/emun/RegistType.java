package com.trgis.trmap.userauth.model.emun;

public class RegistType {
	
	//注册类型 (0 个人注册 ,1 企业注册)regist
	public static enum REGISTFLAG {
		PREGIST(0, "个人注册"), EREGIST(1, "企业注册");
		private Integer value;
		private String name;

		REGISTFLAG(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
	}
}
