package com.trgis.trmap.personal.util;

public class EnumUtil {
	// 记录中的地理坐标类型
	public static enum RTYPE {
		POINT("1", "点"), LINE("2", "线"), AREA("3", "面");
		private String value;
		private String name;

		RTYPE(String value, String name) {
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
	 * -1全部 0 未分享 1已分享
	 */
	public static enum SHAREFLAG {
		ALL(-1, "全部"), WFX(0, "未分享"), YFX(1, "已分享");
		private Integer value;
		private String name;

		SHAREFLAG(Integer value, String name) {
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
	 * 选中状态 0 未选中 1选中
	 */
	public static enum CHECKSTATUS {
		NOCHECK(0, "未选中"), CHECKED(1, "已选中");
		private Integer value;
		private String name;

		CHECKSTATUS(Integer value, String name) {
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
	 * @ClassName: READ
	 * @Description: 阅读状态
	 * @author yanpeng
	 * @date 2016年3月24日 上午10:56:39
	 */
	public static enum READ {
		ALL(-1, "全部"),NOMAL(0, "未阅读"), READ(1, "已阅读");
		private Integer value;
		private String name;

		READ(Integer value, String name) {
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
	 * @ClassName: ATTENTIONFLAG
	 * @Description: 关注状态
	 * @author yanpeng
	 * @date 2016年3月24日 上午10:55:15
	 */
	public static enum ATTENTIONFLAG {
		YGZ(0, "关注"), HGZ(1, "互相关注");
		private Integer value;
		private String name;

		ATTENTIONFLAG(Integer value, String name) {
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
	 * @ClassName: MESSAGE_TYPE
	 * @Description: 消息类型
	 * @author yanpeng
	 * @date 2016年3月24日 上午11:04:08
	 */
	public static enum MESSAGE_TYPE {
		WELCONME(0, "欢迎信"), FANS(1, "新增粉丝"), COMMENT(2, "新的评论"),SYSMSG(3,"系统消息")
;
		private Integer value;
		private String name;

		MESSAGE_TYPE(Integer value, String name) {
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
	 * @ClassName: TOPIC_PERMISSION
	 * @Description: 专题公开范围
	 * @author yanpeng
	 * @date 2016年5月13日 上午9:50:23
	 */
	public static enum TOPIC_PERMISSION{
		ALL(-1, "完全公开"), FRIEND(0, "好友公开"),SOME(1,"指定好友公开");
		private Integer value;
		private String name;

		TOPIC_PERMISSION(Integer value, String name) {
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
