package com.trgis.trmap.hstore.service.query;

import java.util.Iterator;
import java.util.List;

import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.exception.MapPropertiesException;

public class SQLUtil {

	public static final String AND = " and ";
	public static final String OR = " or ";

	/**
	 * 转换查询条件到HStore sql
	 * 
	 * @param conditions 条件
	 * @param relation 关系 
	 * @return
	 * @throws HStoreSQLException 
	 */
	public static String convertConditions(List<Conditions> conditions, String relation) throws MapPropertiesException {
		if(conditions == null || conditions.size()==0) { //没有条件
			return "";
		}
		
		if(relation==null) {
			relation = AND;
		}
		
		StringBuffer whereSql = new StringBuffer(" where ");
		for (Iterator iterator = conditions.iterator(); iterator.hasNext();) {
			Conditions condition = (Conditions) iterator.next();
			whereSql.append(condition.convertHStoreSql());
			if(iterator.hasNext()){
				whereSql.append(relation);
			}
		}
		return whereSql.toString();
	}

}
