package com.trgis.trmap.personal.service;
import java.util.List;

import com.trgis.trmap.personal.exception.UserIntegralException;
import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.personal.exception.UserAttachException;

/**
 * 附件服务类
 * @author liuyan
 * @date 2016-03-08
 */
public interface UserAttachService {
	
	/**
	 * 添加附件
	 * @param UserAttach
	 */
	public void createAttach(UserAttach UserAttach) throws UserAttachException;
	
	/**
	 * @Description:根据id删除附件
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param id
	 * @throws UserAttachException
	 */
	public void deleteUserAttach(Long id) throws UserAttachException;
	
	/**
	 * @Description:根据ossid删除附件
	 * @Author liuyan 
	 * @Date 2016年3月14日
	 * @param id
	 * @throws UserAttachException
	 */
	public void deleteUserAttachByossid(String ossid) throws UserAttachException;
	
	/**
	 * @Description:修改附件
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param userAttach
	 * @throws UserAttachException
	 */
	public void editUserAttach(UserAttach userAttach) throws UserAttachException;
	
	/**
	 * 获取一个事件的所有附件
	 * @param userRecord
	 * @param delflag
	 * @param ext		附件类型
	 * @param length	查询个数
	 * @return
	 * @throws UserAttachException
	 */
	public List<UserAttach> findAllUserAttach(UserRecord userRecord,Integer delflag, String ext,Integer length) throws UserAttachException ; 

	/**
	 * 根据云存储ID查找文件
	 * @author liuyan
	 * @param ossid
	 * @return
	 */
	public UserAttach findUserAttachByOssid(String ossid);

	/**
	 * @Description: 保存记录时修改附件表
	 * @author yanpeng
	 * @date 2016年3月10日 下午4:03:52
	 * @param id
	 * @param ossid
	 */
	public void updateRecord(Long id,String ossid) throws UserAttachException ;

	/**
	 * @Description: 查询一个未删除的附件
	 * @author yanpeng
	 * @date 2016年3月29日 上午11:26:53
	 * @param id
	 * @param value
	 * @return
	 * @throws UserAttachException
	 */
	public String findOne(Long id, Integer value) throws UserAttachException;
	
	/**
	 * @Description: 统计当前用户空间使用大小
	 * @author yanpeng
	 * @date 2016年3月29日 上午11:27:56
	 * @param user
	 * @return
	 * @throws UserIntegralException
	 */
	public Double getSpaceSize(User user) throws UserAttachException;
}
