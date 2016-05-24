package com.trgis.trmap.qtuser.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.qtuser.dao.AttachfileDao;
import com.trgis.trmap.qtuser.exception.AttachfileException;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.service.AttachfileService;
import com.trgis.trmap.qtuser.utils.BeanUtil;

/** 
 * 矢量附件服务实现接口
 * @author Alice
 * 2015年9月19日
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachfileServicrImpl implements AttachfileService{
	// 运行日志记录
	private static final Log log = LogFactory.getLog(AttachfileServicrImpl.class);

	@Autowired
	private AttachfileDao attachfileDao;

	@Override
	public void saveAttachfile(Attachfile attachfile) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(attachfile)) {
				throw new AttachfileException("矢量附件对象不能为空!");
			}
			attachfileDao.save(attachfile);
			log.debug("矢量附件保存成功！");
		} catch (Exception e) {
			log.debug("矢量附件保存失败！");
			throw new AttachfileException("数量附件保存失败!");
		}
	}

	@Override
	public void deleteAttachfile(Long id) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new AttachfileException("矢量附件id不能为空!");
			}
			Attachfile attachfile = attachfileDao.getOne(id);
			if(BeanUtil.isNotEmpty(attachfile)){
				attachfileDao.delete(id);
				log.debug("删除矢量附件成功！");
			}else{
				log.debug("没有此附件！");
			}
		} catch (Exception e) {
			log.debug("删除矢量附件失败！");
			throw new AttachfileException("删除矢量附件失败！");
		}
	}

	@Override
	public List<Attachfile> getByGraphicsId(MapGraphics mapGraphics) {
		// TODO Auto-generated method stub
		try {
			return attachfileDao.findByMapGraphics(mapGraphics);
		} catch (Exception e) {
			log.debug("查询矢量附件列表失败！");
			throw new AttachfileException("查询矢量附件列表失败！");
		}
	}
	@Override
	public List<String> getOssidByGraphicsId(MapGraphics mapGraphics) {
		// TODO Auto-generated method stub
		try {
			return attachfileDao.findOssidByMapGraphics(mapGraphics);
		} catch (Exception e) {
			log.debug("查询矢量附件列表失败！");
			throw new AttachfileException("查询矢量附件列表失败！");
		}
	}
	@Override
	public Page<Attachfile> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy order) {
		Specification<Attachfile> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = attachfileDao.count(specifications);
		if(count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return attachfileDao.findAll(specifications,page);
	}
	
	/**
	 * 根据云存储id查找文件
	 */
	@Override
	public Attachfile findAttachfileByOssid(String ossid) {
		// TODO Auto-generated method stub
		List<Attachfile> list = attachfileDao.findAttachfileByOssid(ossid);
		if(BeanUtil.isNotEmpty(list)&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查找用户已使用空间
	 */
	@Override
	public Double getUsedDataSpace(Long userid) {
		// TODO Auto-generated method stub
		return attachfileDao.getUsedDataSpace(userid);
	}

	@Override
	public void clearAttachfileById(MapGraphics mapGraphics) {
		// TODO Auto-generated method stub
		attachfileDao.clearAttachfileById(mapGraphics);
	}

	@Override
	public void clearAttachfileByDate(Date date) {
		// TODO Auto-generated method stub
		attachfileDao.clearAttachfileByDate(date);
	}
	
}
