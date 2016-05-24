package com.trgis.trmap.system.utils;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trgis.common.template.DataTables;
import com.trgis.trmap.userauth.model.User;

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
	public static String convert2DataTables(Long draw, Page<User> userPage) throws JsonProcessingException {
		DataTables<User> dataTables = new DataTables<User>(draw,userPage);
		return dataTables.toJSONString();
	}

}
