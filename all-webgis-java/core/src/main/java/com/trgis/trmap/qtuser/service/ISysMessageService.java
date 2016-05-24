package com.trgis.trmap.qtuser.service;

import java.util.List;

import com.trgis.trmap.qtuser.model.SysMessage;
import com.trgis.trmap.qtuser.model.UserMap;

/**
 * 用户消息服务类
 * @author doris
 * @date 2015-09-11
 */
/**
 * @author Administrator
 *
 */
public interface ISysMessageService {
	/**
	 * 绘制矢量
	 * @param SysMessage
	 */
	public void save(SysMessage sysMessage);
	/**
	 * 修改矢量
	 * @param SysMessage
	 */
	public void edit(SysMessage sysMessage);
	/**
	 * 删除矢量
	 * @param SysMessage
	 */
	public void delete(Long id);
	/**
	 * 获取所有用户消息
	 * @return
	 */
	public List<SysMessage> findAll(); 
	
	/**
	 * 根据id获取用户消息
	 * @param id
	 */
	public SysMessage findById(Long id); 
	
	/**
	 * 根据userMap获取所有用户消息
	 * @param userMap
	 * @return
	 */
	public  List<SysMessage> findByUserMap(UserMap userMap); 
	/**
	 * 根据userMap、删除标志获取所有用户消息
	 * @param userMap
	 * @return
	 */
	public List<SysMessage> findSysMessages(Integer mdelflag,UserMap userMap); 
}
