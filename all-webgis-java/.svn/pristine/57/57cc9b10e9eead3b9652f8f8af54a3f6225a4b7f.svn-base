package com.trgis.trmap.qtuser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.qtuser.dao.BaseMapDao;
import com.trgis.trmap.qtuser.model.BaseMap;
import com.trgis.trmap.qtuser.service.BaseMapService;

/**
 * 基础底图图层业务类
 * @author Alice
 *
 * 2015年9月28日
 */
@Service
@Transactional
public class BaseMapServiceImpl implements BaseMapService{

	@Autowired
	private BaseMapDao dao;
	@Override
	public List<BaseMap> getBaseMapByMapId(Long mapId) {
		// TODO Auto-generated method stub
		return dao.getBaseMapByMapId(mapId);
	}
	@Override
	public void addBaseMap(BaseMap basemap) {
		// TODO Auto-generated method stub
		dao.save(basemap);
	}
	@Override
	public void deleteBaseMapByMapId(Long mapid) {
		// TODO Auto-generated method stub
		dao.deleteBaseMapByMapId(mapid);
	}

}
