package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.enterprise.dao.EntRelAttributemetaDAO;
import com.trgis.trmap.enterprise.exception.EntAttributemetaException;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntRelAttributemeta;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.service.EntRelAttributemetaService;
import com.trgis.trmap.enterprise.util.BeanUtil;
@Service
@Transactional
public class EntRelAttributemetaServiceImpl implements EntRelAttributemetaService{
	
	private static final Log log = LogFactory.getLog(EntAttributemetaService.class);
	
	@Autowired
	private EntRelAttributemetaDAO relAttributemetaDao;
	
	@Override
	public List<EntRelAttributemeta> findByEntLayermeta(EntLayermeta entLayermeta) {
		try {
			if (BeanUtil.isEmpty(entLayermeta)) {
				throw new EntAttributemetaException("图层元数据表信息不能为空！");
			}
			return relAttributemetaDao.findByLayermeta(entLayermeta);
		} catch (Exception e) {
			log.debug("查询扩展属性表结构（列结构） 信息失败！");
			throw new EntAttributemetaException("查询扩展属性表结构（列结构） 信息失败！");
		}
	}

	@Override
	public EntRelAttributemeta findById(Long id) {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new EntAttributemetaException("图层元数据表id不能为空！");
			}
			return relAttributemetaDao.findOne(id);
		} catch (Exception e) {
			log.debug("查询扩展属性表结构（列结构） 信息失败！");
			throw new EntAttributemetaException("查询扩展属性表结构（列结构） 信息失败！");
		}
	}
	
	@Override
	public void deletByEntRelAttributemeta(Long id) {
		// TODO Auto-generated method stub
		if (BeanUtil.isEmpty(id)) {
			throw new EntAttributemetaException("id信息不能为空！");
		}
		try {
			relAttributemetaDao.delete(id);
		} catch (Exception e) {
			log.debug("删除扩展属性表结构（行） 信息失败！");
			throw new EntAttributemetaException("删除扩展属性表结构（行） 信息失败！");
		}
	}
}
