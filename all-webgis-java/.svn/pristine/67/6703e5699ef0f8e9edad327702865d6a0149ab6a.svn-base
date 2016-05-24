package com.trgis.trmap.system.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.trgis.trmap.system.dao.ImageStorageDao;
import com.trgis.trmap.system.model.ImageStorage;
import com.trgis.trmap.system.service.ImageStorageService;
import com.trgis.trmap.system.util.EnumUtil;

/**
 * 
 * @author majl
 * @Description 
 * @data 2016年4月12日
 */
@Service
@Transactional
public class ImageStorageServiceImpl implements ImageStorageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageStorageServiceImpl.class);

	@Autowired
	private ImageStorageDao imageStorageDao;

	@Override
	public Page<ImageStorage> findAll(int page, int rows, String searchValue,String imageTypeCheck) {
		// TODO Auto-generated method stub	//新建查询条件对象 
		ConditionGroup cgroot = new ConditionGroup();
		//设置根查询关机为and
		cgroot.setSearchRelation(SearchRelation.AND);
		//过滤删除掉的子条件对象 
		cgroot.addCondition(new SearchCondition("delflag",Operator.NEQ,EnumUtil.DELFLAG.DEL.getValue()));
		if(StringUtils.isNotBlank(imageTypeCheck)){
		cgroot.addCondition(new SearchCondition("imageType",Operator.EQ,Integer.parseInt(imageTypeCheck)));
		}
		if(!StringUtils.isEmpty(searchValue)){
			String [] searchs = searchValue.split(":");
			//子条件对象
			if(searchs.length>1&&!StringUtils.isBlank(searchs[1])){
				ConditionGroup subcg= new ConditionGroup();
				subcg.setSearchRelation(SearchRelation.OR);

				List<SearchCondition> conditions = new ArrayList<SearchCondition>();
				int value;
				//判断添加查询条件
				switch(searchs[0]){
				case "name":
					conditions.add(new SearchCondition(searchs[0], Operator.LIKE, searchs[1]));
					break;
				}
				//查询条件添加到查询条件对象
				subcg.getConditions().addAll(conditions);
				cgroot.getSubConditions().add(subcg);
			}
		}

		//排序
		OrderBy order = new OrderBy("id","asc");
		Specification<ImageStorage> specs = DynamicSpecficationUtil.buildSpecfication(cgroot);
		long count = imageStorageDao.count(specs);
		if(count ==0){
			return null;
		} 
		//分页对象
		PageRequest pr = PageAndSortUtil.buildPageAndSort(count, page, rows, order);	
		Page<ImageStorage> pages = imageStorageDao.findAll(specs,pr);
		return pages;
	}

	@Override
	public void createEntity(ImageStorage modle) {
		// TODO Auto-generated method stub

	}

	@Override
	public ImageStorage findOneByEntity(ImageStorage modle) {
		return imageStorageDao.findOne(modle.getId());
	}

	@Override
	public ImageStorage modifyEntity(ImageStorage modle) {
		return imageStorageDao.save(modle);

	}

	@Override
	public void removeEntity(ImageStorage modle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEntity(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createEntityList(List<ImageStorage> list) {
		imageStorageDao.save(list);
	}

	@Override
	public ImageStorage findOneById(Long id) {
		return imageStorageDao.getOne(id);
	}

	@Override
	public List<ImageStorage> findByImgtype(Integer imgtype) {
		//获取未删除的图库
		return imageStorageDao.findByImageTypeAndDelflag(imgtype,EnumUtil.DELFLAG.NOMAL.getValue());
	}
	
}
