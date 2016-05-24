package com.trgis.trmap.personal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.trmap.personal.model.UserTopicTag;

public interface UserTopicTagService {

	Page<UserTopicTag> findAll(int page,int rows,String searchValue);
	
	void createEntity(UserTopicTag modle);
	
	UserTopicTag findOneByEntity(UserTopicTag modle);
	
	void createEntityList(List<UserTopicTag> list);

	void modifyEntity(UserTopicTag modle);
	
	void removeEntity(UserTopicTag modle);
	
	void removeEntity(Long id);
}
