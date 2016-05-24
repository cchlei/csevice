/**
 * 
 */
package com.trgis.trmap.hstore.service;

import java.util.List;

import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.model.HStoreModel;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.Sort;

/**
 * HStore数据存储业务逻辑处理类
 * 
 * @author zhangqian
 *
 */
public interface HStoreService {

	/**
	 * 保存或更新
	 * 
	 * @param model
	 */
	void save(HStoreModel model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void update(HStoreModel model);

	/**
	 * 获取一条数据
	 * 
	 * @param id
	 *            数据id
	 * 
	 * @return 返回数据对象
	 */
	HStoreModel getHStoreModel(Long id);

	/**
	 * 分页查询数据
	 * 
	 * select h from hstore_model h 
	 * 
	 * @param page 分页
	 * @param sort 排序
	 * @param conditions 条件
	 * @param relation 条件的关系 or || and(DEFAULT)
	 * @return
	 * @throws HStoreSQLException 
	 */
	Page queryPage(Page page, Sort sort, List<Conditions> conditions,String relation) throws HStoreSQLException;

	/**
	 * 查询所有 测试用方法，正式接口不包含次方法
	 * @return
	 */
	List<HStoreModel> queryAll();

}
