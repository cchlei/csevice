package com.trgis.trmap.hstore.service.query;

import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.exception.MapPropertiesException;

/**
 * 组装条件对象
 * 
 * @see postgresql to_date formatter
 *      http://www.techonthenet.com/postgresql/functions/to_date.php
 * 
 * @author zhangqian
 *
 */
public class Conditions {

	private String key;

	private String[] value;

	private TypeEnum typeEnum = TypeEnum.STRING;// DEFAULT

	private ConditionalEnum conditional = ConditionalEnum.LIKE; // DEFAULT

	public Conditions(String key, String[] value) {
		this.key = key;
		this.value = value;
	}

	public Conditions(String key, String[] value, TypeEnum typeEnum) {
		this.key = key;
		this.value = value;
		this.typeEnum = typeEnum;
	}

	public Conditions(String key, String[] value, ConditionalEnum conditional) {
		this.key = key;
		this.value = value;
		this.conditional = conditional;
	}

	public Conditions(String key, String[] value, TypeEnum typeEnum, ConditionalEnum conditional) {
		super();
		this.key = key;
		this.value = value;
		this.typeEnum = typeEnum;
		this.conditional = conditional;
	}

	/**
	 * 将条件转换为hstore sql 语句
	 * @return
	 * @throws HStoreSQLException
	 */
	public String convertHStoreSql() throws MapPropertiesException {
		switch (this.conditional) {
		case LT:
			switch (this.typeEnum) {
			case STRING:
				throw new MapPropertiesException("字符串不能比较大小");
			case INT:
				return String.format("cast((stores->'%s') as INT) < %s", this.key, this.value[0]);
			case DATE:
				//TODO 需要加入日期格式的正则表达式判断
				return String.format("cast((stores->'%s') as DATE) < to_date('%s','YYYY-MM-DD HH24:MI:SS');", this.key,this.value[0]);
			default:
				throw new MapPropertiesException("值类型错误");
			}
		case LTE:
			switch (this.typeEnum) {
			case STRING:
				throw new MapPropertiesException("字符串不能比较大小");
			case INT:
				return String.format("cast((stores->'%s') as INT) <= %s", this.key, this.value[0]);
			case DATE:
				//TODO 需要加入日期格式的正则表达式判断
				return String.format("cast((stores->'%s') as DATE) <= to_date('%s','YYYY-MM-DD HH24:MI:SS');", this.key,this.value[0]);
			default:
				throw new MapPropertiesException("值类型错误");
			}
		case GT:
			switch (this.typeEnum) {
			case STRING:
				throw new MapPropertiesException("字符串不能比较大小");
			case INT:
				return String.format("cast((stores->'%s') as INT) > %s", this.key, this.value[0]);
			case DATE:
				//TODO 需要加入日期格式的正则表达式判断
				return String.format("cast((stores->'%s') as DATE) > to_date('%s','YYYY-MM-DD HH24:MI:SS')", this.key,this.value[0]);
			default:
				throw new MapPropertiesException("值类型错误");
			}
		case GTE:
			switch (this.typeEnum) {
			case STRING:
				throw new MapPropertiesException("字符串不能比较大小");
			case INT:
				return String.format("cast((stores->'%s') as INT) >= %s", this.key, this.value[0]);
			case DATE:
				//TODO 需要加入日期格式的正则表达式判断
				return String.format("cast((stores->'%s') as DATE) >= to_date('%s','YYYY-MM-DD HH24:MI:SS')", this.key,this.value[0]);
			default:
				throw new MapPropertiesException("值类型错误");
			}
		case LIKE:
				return String.format("(stores->'%s') like '%s'", this.key, this.value[0]);
		case NOTLIKE:
			return String.format("(stores->'%s') not like '%s'", this.key, this.value[0]);
		case EQUALS:
			return String.format("(stores->'%s') = '%s'", this.key, this.value[0]);
		case BETWEEN:
			// TODO 需要验证参数长度
			switch (this.typeEnum) {
			case STRING:
				throw new MapPropertiesException("字符串不能比较");
			case INT:
				return String.format("cast((stores->'%s') as INT) between %s and %s", this.key, this.value[0],this.value[1]);
			case DATE:
				//TODO 需要加入日期格式的正则表达式判断
				return String.format("cast((stores->'%s') as DATE) => to_date('%s','YYYY-MM-DD HH24:MI:SS') and to_date('%s','YYYY-MM-DD HH24:MI:SS')", this.key,this.value[0],this.value[1]);
			default:
				throw new MapPropertiesException("值类型错误");
			}
		case IN:
			return String.format("(stores->'%s') in (%s)", this.key, this.value[0]);
		case NOTIN:
			return String.format("(stores->'%s') not in (%s)", this.key, this.value[0]);
		default:
			return "";
		}
	}

	public static void main(String[] args) throws MapPropertiesException {
		Conditions cs = new Conditions("age", new String[] { "'12','13'" },TypeEnum.STRING,ConditionalEnum.IN);
		System.out.println(cs.convertHStoreSql());
	}

}

