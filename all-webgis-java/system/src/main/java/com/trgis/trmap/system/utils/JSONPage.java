package com.trgis.trmap.system.utils;

import java.util.List;

public class JSONPage {

	private Integer total;

	private List<?> rows;

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setRows(List<?> result) {
		this.rows = result;
	}

	public Integer getTotal() {
		return total;
	}

	public List<?> getRows() {
		return rows;
	}

	public JSONPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JSONPage(Integer total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

}
