package com.trgis.trmap.personal.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.personal.dao.UserAttachDao;
import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;


@Service
@Transactional
public class UserAttachServiceImpl implements UserAttachService{

	
	@Autowired
	private UserAttachDao userAttachDao;
	
	private static final Log log = LogFactory.getLog(UserAttachServiceImpl.class);

	@Override
	public void createAttach(UserAttach userAttach) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(userAttach)) {
			throw new UserAttachException("附件对象不能为空!");
			}
			userAttachDao.save(userAttach);
			log.debug("附件保存成功！");
		} catch (Exception e) {
			log.debug("附件保存失败！");
			throw new UserAttachException("附件保存失败!");
		}
		
	}
	@Override
	public void deleteUserAttach(Long id) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserAttachException("附件id不能为空!");
			}
			UserAttach userAttach = userAttachDao.getOne(id);
			userAttach.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			if(BeanUtil.isNotEmpty(userAttach)){
				this.editUserAttach(userAttach);
				log.debug("删除附件成功！");
			}else{
				log.debug("没有此附件！");
			}
		} catch (Exception e) {
			log.debug("删除附件失败！");
			throw new UserAttachException("删除附件失败！");
		}
		
	}
	
	@Override
	public void deleteUserAttachByossid(String ossid) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(ossid)) {
				throw new UserAttachException("ossid不能为空!");
			}
			userAttachDao.deleteAttach(EnumUtil.DELFLAG.DEL.getValue(), ossid);
			log.debug("删除附件成功！");
			
		} catch (Exception e) {
			log.debug("删除附件失败！");
			throw new UserAttachException("删除附件失败！");
		}
		
	}
	@Override
	public void editUserAttach(UserAttach userAttach) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(userAttach)) {
				throw new UserAttachException("附件不能为空！");
			}
			userAttachDao.saveAndFlush(userAttach);
			log.debug("附件修改成功！");
		} catch (Exception e) {
			log.debug("附件修改失败！");
			throw new UserAttachException("附件修改失败！");
		}
	}
	
	@Override
	public List<UserAttach> findAllUserAttach(UserRecord userRecord,Integer delflag, String ext, Integer length) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(userRecord)) {
				throw new UserAttachException("事件记录对象不能为空!");
			}
			if (BeanUtil.isEmpty(userRecord.getId())) {
				throw new UserAttachException("事件记录id不能为空!");
			}
			log.debug("事件相关附件查询成功！");
			if (BeanUtil.isEmpty(ext)&&BeanUtil.isEmpty(length)) {
				return userAttachDao.findByUserRecord(userRecord,delflag);
			}
			return userAttachDao.findByUserRecord(userRecord.getId(),delflag,ext,length);
		} catch (Exception e) {
			log.debug("事件相关附件查询失败！");
			throw new UserAttachException("事件相关附件查询失败!");
		}
	}
	
	@Override
	public UserAttach findUserAttachByOssid(String ossid) {
		List<UserAttach> list = userAttachDao.findUserAttachByOssid(ossid);
		if(BeanUtil.isNotEmpty(list)&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateRecord(Long id,String ossid) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserAttachException("事件记录id不能为空!");
			}
			if (BeanUtil.isEmpty(ossid)) {
				throw new UserAttachException("附件云端id不能为空!");
			}
			userAttachDao.updateRecord(id,ossid);
			log.debug("附件修改成功！");
		} catch (Exception e) {
			log.debug("附件修改失败！");
			throw new UserAttachException("附件修改失败!");
		}
	}
	@Override
	public String findOne(Long id, Integer value) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserAttachException("事件记录id不能为空!");
			}
			log.debug("附件查询成功！");
			return userAttachDao.findOneByRecord(id,value);
		} catch (Exception e) {
			log.debug("附件查询失败！");
			throw new UserAttachException("附件查询失败!");
		}
	}
	@Override
	public Double getSpaceSize(User user) throws UserAttachException {
		try {
			if (BeanUtil.isEmpty(user)) {
				throw new UserAttachException("用户信息为空!");
			}
			Long size = userAttachDao.querySpaceSize(user, EnumUtil.DELFLAG.NOMAL.getValue());
			double temp = size/1024.0/1024.0;
			DecimalFormat df= new DecimalFormat("0.00");
			log.debug("空间大小统计成功！");
			return Double.parseDouble(df.format(temp));
		} catch (Exception e) {
			log.debug("空间大小统计失败！");
			throw new UserAttachException("空间大小统计失败!");
		}
	}


}
