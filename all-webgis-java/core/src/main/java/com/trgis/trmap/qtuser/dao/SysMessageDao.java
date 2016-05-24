package com.trgis.trmap.qtuser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.SysMessage;
import com.trgis.trmap.qtuser.model.UserMap;
/**
 * @author doris
 * 用户消息dao
 */
@Repository
public interface SysMessageDao extends JpaRepository<SysMessage,Long>{
	/**
	 * 根据用户查询系统消息
	 * @param id
	 * @return
	 */
	public List<SysMessage> findSysMessageByUserMap(UserMap userMap);
	/**
	 * 根据删除标志查询系统消息记录
	 * @param mapdelflag
	 * @return
	 */
	public List<SysMessage>findSysMessageByMdelflagAndUserMap(Integer mdelflag,UserMap userMap);
}
