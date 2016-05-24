package com.trgis.trmap.qtuser.utils;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.trgis.common.template.DataTables;



/**
 * JSON 转换工具
 * 
 * @author zhangqian
 *
 */
public class ConvertJSON {

	/**
	 * 将结果集转换为datatables json格式
	 * 
	 * @param userPage
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String convert2DataTables(Long draw, Page<?> userPage) throws JsonProcessingException {
		DataTables<?> dataTables = new DataTables(draw, userPage);
		return JSON.toJSONString(dataTables, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.IgnoreNonFieldGetter,
				SerializerFeature.WriteDateUseDateFormat,SerializerFeature.DisableCircularReferenceDetect);
	}

}
