package com.trgis.trmap.hstore.service.query;

/**
 * 排序查询
 * 
 * HStore的排序原生sql为
 * 
 * order by (stores->'birthday')::DATE
 * 
 * stores:hstore列名 ->'birthday' 列内部的birthday列
 * 
 * ::DATE 表示为日期类型 ::INT 表示为整形
 * 
 * 默认不写为字符串
 * 
 * @author zhangqian
 *
 */
public class Sort {

	// 排序列
	private String key;

	private TypeEnum type;

	// 方向
	private DirectionEnum direction;

	public Sort(String key, DirectionEnum direction) {
		this.key = key;
		this.direction = direction;
	}

	public Sort(String key, DirectionEnum direction, TypeEnum type) {
		this.key = key;
		this.direction = direction;
		this.type = type;
	}

	/**
	 * 返回排序
	 * @return
	 */
	public String sortSQL() {
		switch (this.type) {
		case INT:
			switch (this.direction) {
			case ASC:
				return String.format(" order by cast((stores->'%s') as INT) asc ", key);
			case DESC:
				return String.format(" order by cast((stores->'%s') as INT) desc ", key);
			default:
				return String.format(" order by cast((stores->'%s') as INT) asc ", key);
			}
		case STRING:
			switch (this.direction) {
			case ASC:
				return String.format(" order by (stores->'%s') asc ", key);
			case DESC:
				return String.format(" order by (stores->'%s') desc ", key);
			default:
				return String.format(" order by (stores->'%s') asc ", key);
			}
		case DATE:
			switch (this.direction) {
			case ASC:
				return String.format(" order by cast((stores->'%s') as DATE) asc ", key);
			case DESC:
				return String.format(" order by cast((stores->'%s') as DATE) desc ", key);
			default:
				return String.format(" order by cast((stores->'%s') as DATE) asc ", key);
			}
		default:
			return "";
		}
	}
}
