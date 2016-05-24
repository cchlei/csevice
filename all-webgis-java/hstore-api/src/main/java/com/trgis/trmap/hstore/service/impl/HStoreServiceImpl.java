package com.trgis.trmap.hstore.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Service;

import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.model.HStoreModel;
import com.trgis.trmap.hstore.service.HStoreService;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.SQLUtil;
import com.trgis.trmap.hstore.service.query.Sort;

@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HStoreServiceImpl implements HStoreService {
	
	public HStoreServiceImpl() {
		System.out.println("HStoreServiceImpl初始化");
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(HStoreModel model) {
		em.persist(model);
	}

	@Override
	public void update(HStoreModel model) {
		em.merge(model);
	}

	@Override
	public HStoreModel getHStoreModel(Long id) {
		HStoreModel model = em.find(HStoreModel.class, id);
		Hibernate.initialize(model);
		return model;
	}

	@Override
	public Page queryPage(Page page, Sort sort, List<Conditions> conditions, String relation) throws HStoreSQLException {
		String countSql = "select count(*) from hstore_model ";
		String sql = "select * from hstore_model ";
		
		// 根据条件过滤
		if (conditions != null && conditions.size() > 0) {
			String condSql = SQLUtil.convertConditions(conditions, relation);
			countSql+=condSql;
			sql += condSql;
		}
		if(sort != null) {
			sql += sort.sortSQL();
		}
		if(page != null) {
			// 先获取分页总记录数
			int recordCount = ((BigInteger) em.createNativeQuery(countSql).getSingleResult()).intValue();
			page.setRecordCount(recordCount);
			sql += page.pageSql();
		}
		TypedQuery<HStoreModel> query = (TypedQuery<HStoreModel>) em.createNativeQuery(sql,HStoreModel.class);
		List<HStoreModel> list = query.getResultList();
		page.setResultList(list);
		return page;
	}

	@Override
	public List<HStoreModel> queryAll() {
		return em.createNativeQuery("select * from hstore_model h", HStoreModel.class).getResultList();
	}

}
