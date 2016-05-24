package com.trgis.trmap.enterprise.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.trmap.enterprise.dao.EntAttributemetaDao;
import com.trgis.trmap.enterprise.dao.EntLayermetaDao;
import com.trgis.trmap.enterprise.dao.EntUserMapDAO;
import com.trgis.trmap.enterprise.exception.EntAttributemetaException;
import com.trgis.trmap.enterprise.exception.EntMapGraphicsException;
import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.util.BeanUtil;
@Service
@Transactional
public class EntAttributemetaServiceImpl implements EntAttributemetaService{
	
	private static final Log log = LogFactory.getLog(EntAttributemetaService.class);
	
	@Autowired
	private EntAttributemetaDao enterpriseAttributemetaDao;
	@Autowired
	private EntUserMapDAO entUserMapDao;
	@Autowired
	private EntLayermetaDao entLayermetaDao;
	
	@Override
	public String createEnterpriseAttributemeta(EntAttributemeta entAttributemeta) {
		// TODO Auto-generated method stub
		try {
			if (BeanUtil.isEmpty(entAttributemeta)) {
				throw new EntAttributemetaException(" 扩展属性表结构（列结构） 默认信息不能为空！");
			}
			enterpriseAttributemetaDao.save(entAttributemeta);
			log.debug(" 扩展属性表结构（列结构） 信息保存成功！");
		} catch (Exception e) {
			log.debug(" 扩展属性表结构（列结构） 信息保存失败！");
			throw new EntAttributemetaException(" 扩展属性表结构（列结构） 信息保存失败！");
		}
		return log.toString();
	}
	
	@Override
	public void editAttriButeMeta(List<String[]> thList, Long emapid) {
		// TODO Auto-generated method stub
		EntUserMap map = entUserMapDao.findOne(emapid);
		if(BeanUtil.isEmpty(map)){
			log.debug("更新企业地图扩展属性表结构时未查询到企业地图数据！");
			throw new EntAttributemetaException("更新企业地图扩展属性表结构时未查询到企业地图数据！");
		}
		try {
			List<EntLayermeta> layermeta = entLayermetaDao.findByMap(map);
			if(BeanUtil.isNotNull(layermeta) && layermeta.size() > 0){
				List<EntAttributemeta> attributemetas = enterpriseAttributemetaDao.findByLayermeta(layermeta.get(0));
				for (int i = 0; i < thList.size(); i++) {
					boolean include = false;
					for (int j = 0; j < attributemetas.size(); j++) {
						if(thList.get(i)[0].equals(attributemetas.get(j).getAttralias())){
							include = true;
							if(!thList.get(i)[1].equals(attributemetas.get(j).getAttrcode())){//code列名称改变
								EntAttributemeta attr = findByAttralias(thList.get(i)[0],layermeta.get(0));
								attr.setAttrcode(thList.get(i)[1]);
								enterpriseAttributemetaDao.save(attr);
							}
							break;
						}
					}
					if(!include){
						EntAttributemeta attr = new EntAttributemeta();
						attr.setAttralias(thList.get(i)[0]);
						attr.setAttrcode(thList.get(i)[1]);
						attr.setLayermeta(layermeta.get(0));
						enterpriseAttributemetaDao.save(attr);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("更新企业地图扩展属性表结构失败！");
			throw new EntAttributemetaException("更新企业地图扩展属性表结构失败！");
		}
	}

	@Override
	public List<EntAttributemeta> findByEntLayermeta(EntLayermeta entLayermeta) {
		try {
			if (BeanUtil.isEmpty(entLayermeta)) {
				throw new EntAttributemetaException("图层元数据表信息不能为空！");
			}
			return enterpriseAttributemetaDao.findByLayermeta(entLayermeta);
		} catch (Exception e) {
			log.debug("查询扩展属性表结构（列结构） 信息失败！");
			throw new EntAttributemetaException("查询扩展属性表结构（列结构） 信息失败！");
		}
	}

	@Override
	public void deletByEntAttributemeta(Long id) {
		// TODO Auto-generated method stub
		if (BeanUtil.isEmpty(id)) {
			throw new EntAttributemetaException("id信息不能为空！");
		}
		try {
			enterpriseAttributemetaDao.delete(id);
		} catch (Exception e) {
			log.debug("删除扩展属性表结构（行） 信息失败！");
			throw new EntAttributemetaException("删除扩展属性表结构（行） 信息失败！");
		}
	}

	@Override
	public EntAttributemeta findByAttralias(String attralias,EntLayermeta entLayermeta) {
		// TODO Auto-generated method stub
		List<EntAttributemeta> attrlist= enterpriseAttributemetaDao.findByAttralias(attralias, entLayermeta);
		if (BeanUtil.isEmpty(attrlist)) {
			log.debug("根据标题列未找到未找到对应的扩展属性列！");
			throw new EntMapGraphicsException("根据标题列未找到未找到对应的扩展属性列！");
		}else{
			return attrlist.get(0);
		}
	}

	
}
