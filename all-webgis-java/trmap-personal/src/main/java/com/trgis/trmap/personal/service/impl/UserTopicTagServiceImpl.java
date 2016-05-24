package com.trgis.trmap.personal.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.dao.UserTopicTagDao;
import com.trgis.trmap.personal.model.UserTopicTag;
import com.trgis.trmap.personal.service.UserTopicTagService;

/**
 * 
 * @author majl
 * @Description 
 * @data 2016年4月12日
 */
@Service
@Transactional
public class UserTopicTagServiceImpl implements UserTopicTagService {
	private static final Logger logger = LoggerFactory.getLogger(UserTopicTagServiceImpl.class);


	@Autowired
	private UserTopicTagDao  userTopicTagDao;

	@Override
	public Page<UserTopicTag> findAll(int page ,int rows,String searchValue) {
		//新建查询条件对象 
		ConditionGroup cgroot = new ConditionGroup();
		//设置根查询关机为and
		cgroot.setSearchRelation(SearchRelation.AND);
		try{
			if(!StringUtils.isEmpty(searchValue)){
				String [] searchs = searchValue.split(":");
				//子条件对象
				ConditionGroup subcg= new ConditionGroup();
				subcg.setSearchRelation(SearchRelation.OR);
				List<SearchCondition> conditions = new ArrayList<SearchCondition>();
				//判断添加查询条件
				if(searchs.length>1&&!StringUtils.isBlank(searchs[1])){
					boolean isNum =	Pattern.matches("^[-\\+]?[\\d]*$",searchs[1]);
					switch(searchs[0]){
					case "id":
						if(isNum){
							conditions.add(new SearchCondition("id",Operator.EQ,Integer.parseInt(searchs[1])));
						}
						break;
					case "name":
						conditions.add(new SearchCondition("name",Operator.LIKE,searchs[1]));
						break;
					default:
						if(isNum){
							conditions.add(new SearchCondition("id",Operator.EQ,Integer.parseInt(searchs[1])));
						}else{
							conditions.add(new SearchCondition("name",Operator.LIKE,searchs[1]));
						}
						break;
					}
					//查询条件添加到查询条件对象
					subcg.getConditions().addAll(conditions);
					cgroot.getSubConditions().add(subcg);
				}
			}
			//排序
			OrderBy order = new OrderBy("id","asc");
			Specification<UserTopicTag> specs = DynamicSpecficationUtil.buildSpecfication(cgroot);
			long count = userTopicTagDao.count(specs);
			if(count ==0){
				return null;
			} 
			//分页对象
			PageRequest pr = PageAndSortUtil.buildPageAndSort(count, page, rows, order);		
			Page<UserTopicTag> pages = userTopicTagDao.findAll(specs,pr);
			return pages;
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public void createEntity(UserTopicTag modle) {
		// TODO Auto-generated method stub
		userTopicTagDao.save(modle);
	}

	@Override
	public UserTopicTag findOneByEntity(UserTopicTag modle) {
		// TODO Auto-generated method stub
		return userTopicTagDao.findOne(modle.getId());
	}



	@Override
	public void modifyEntity(UserTopicTag modle) {

		userTopicTagDao.saveAndFlush(modle);
	}

	@Override
	public void removeEntity(UserTopicTag modle) {
		// TODO Auto-generated method stub

	}

	public UserTopicTagDao getUserTopicTagDao() {
		return userTopicTagDao;
	}

	public void setUserTopicTagDao(UserTopicTagDao userTopicTagDao) {
		this.userTopicTagDao = userTopicTagDao;
	}

	@Override
	public void removeEntity(Long id) {
		userTopicTagDao.delete(id);
	}

	@Override
	public void createEntityList(List<UserTopicTag> list) {
		// TODO Auto-generated method stub

	}





}
