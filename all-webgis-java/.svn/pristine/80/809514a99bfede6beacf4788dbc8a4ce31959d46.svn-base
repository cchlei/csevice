package com.trgis.trmap.enterprise.util;

public class EnumUtil {
	// 企业认证信息状态
	public static enum CASTATUS {
		AUDITWAIT("0", "待审核"), CANCELED("1", "已取消"), AUDITED("2", "认证通过"), AUDITFAIL("3", "认证未通过");
		private String value;
		private String name;

		CASTATUS(String value, String name) {
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
	}

	// 矢量类型
	public static enum GTYPE {
		ALL("-1", "全部"), POINT("1", "点"), LINE("2", "线"), AREA("3", "面");

		private String value;
		private String name;

		GTYPE(String value, String name) {
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
	}

	// 删除标志0未删除 1已删除
	public static enum DELFLAG {
		NOMAL(0, "未删除"), DEL(1, "已删除");
		private Integer value;
		private String name;

		DELFLAG(Integer value, String name) {
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

	/**
	 * 0 未发布 1 已发布
	 */
	public static enum SHARE {
		WFB(0, "未发布"), YFB(1, "已发布");
		private Integer value;
		private String name;

		SHARE(Integer value, String name) {
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

	/**
	 * 0 下线 1 上线
	 * 
	 * @author Alice
	 *
	 *         2015年12月10日
	 */
	public static enum ONLINE {
		XX(0, "下线"), SX(1, "上线"), GQ(2, "挂起");
		private Integer value;
		private String name;

		ONLINE(Integer value, String name) {
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

	/**
	 * 审批状态
	 * 
	 * @author Alice
	 *
	 *         2015年12月24日
	 */
	public static enum ISAPPROVED {
		UNTREATED(0, "待审批"), PASS(1, "通过"), FAIL(2, "驳回"), OUTDATE(3, "过期"), DISABLE(4, "已撤销"), EXCEP(5, "异常");
		private Integer value;
		private String name;

		ISAPPROVED(Integer value, String name) {
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
