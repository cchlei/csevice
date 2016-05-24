package com.trgis.trmap.qtuser.utils;

public class EnumUtil {
	 // 删除标志0未删除 1已删除 
	  public static enum DELFLAG {
	    NOMAL(0, "未删除"),DEL(1, "已删除");
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
		 *  0 未分享
		 *  1 已分享（审核通过）
		 *  2 已申请
		 *  3 审核不通过 
	  */
	  public static enum SHARE {
	    WFX(0, "未分享"),YFX(1, "已分享"),SPZ(2, "审批中"),BTH(3, "被退回");
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
	  //  阅读状态 已读 未读
	  public static enum READ {
	    YD(0, "已读"),WD(1, "未读");
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
	  //图层的状态
	  public static enum BASEMAP {
		COLOR(0,"彩色版"),COOL(1, "冷色版"),WORLDIMAGE(2, "天地图影像"),GRAY(3, "灰色版"),
		NOTEMAP(4, "彩色注记版"),WARM(5, "暖色版"),WORLD(6, "天地图");
	    private Integer value;
	    private String name;

	    BASEMAP(Integer value, String name) {
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
	  //文件的类型
	  public static enum ATTACHTYPE {
		ALL(-1,"全部"),IMAGE(0,"图片"),WORD(1, "文档"),VEDIO(2, "视频"),AUDIO(3,"音频");
		  
	    private Integer value;
	    private String name;

	    ATTACHTYPE(Integer value, String name) {
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
	  //矢量类型
	  public static enum GTYPE {
		ALL("-1","全部"),POINT("1","点"),LINE("2", "线"),AREA("3", "面");
		  
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
}
