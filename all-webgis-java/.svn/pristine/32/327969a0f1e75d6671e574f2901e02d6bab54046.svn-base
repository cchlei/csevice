/**
 * 
 */
package com.trgis.trmap.enterprise.web.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.trgis.trmap.enterprise.web.vo.FeatureVO;

/**
 * 获取Features的结果集
 * 
 * @author zhangqian
 *
 */
public class FeaturesResult {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	@JSONField
	private String result;

	@JSONField
	private List<FeatureVO> data = new ArrayList<FeatureVO>();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<FeatureVO> getData() {
		return data;
	}

	public void setData(List<FeatureVO> data) {
		this.data = data;
	}
	
}
