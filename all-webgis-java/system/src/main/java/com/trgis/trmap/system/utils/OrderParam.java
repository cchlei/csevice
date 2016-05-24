package com.trgis.trmap.system.utils;

/**
 * order表单封装
 * 
 * @author zhangqian
 *
 */
public class OrderParam {

	private String column;
	
	private String dir;
	
	public OrderParam() {
	}
	
	public OrderParam(String column, String dir) {
		this.column = column;
		this.dir = dir;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

}
