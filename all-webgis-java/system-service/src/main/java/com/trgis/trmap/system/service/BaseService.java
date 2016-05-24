package com.trgis.trmap.system.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;


public interface BaseService <T extends Serializable>{
	
	Page<T> findAll(int page,int rows,String searchValue,String imageTypeCheck);
	
	void createEntity(T modle);
	
	T findOneByEntity(T modle);
	
	void createEntityList(List<T> list);

	T modifyEntity(T modle);
	
	void removeEntity(T modle);
	
	void removeEntity(Long id);
	
	T findOneById(Long id);
}
