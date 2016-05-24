package com.trgis.trmap.system.util;

/**
 * 
 * @author majl
 * @Description 枚举工具
 * @data 2016年4月12日
 */
public class EnumUtil {

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
		public static String getName(Integer value){
			String name = null;
			for(DELFLAG delflag : DELFLAG.values()){
				if(value==delflag.value)
					name= delflag.name;
			}
			return name;
		}
	}
	public static enum IMAGETYPE{
		PORTRAIT(0,"头像"),TOPIC(1,"专题图片");
		private Integer value;
		private String name;

		IMAGETYPE(Integer value,String name){
			this.value=value;
			this.name=name;
		}
		public Integer getValue(){
			return this.value;
		}
		public String getName(){
			return this.name;
		}
		public static String getName(Integer value){
			String name =null;
			for(IMAGETYPE imagetype : IMAGETYPE.values()){
				if(value == imagetype.value){
					name = imagetype.name;
				}
			}
			return name;
		}
	}
}
