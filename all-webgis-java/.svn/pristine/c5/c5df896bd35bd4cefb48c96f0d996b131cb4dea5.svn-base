package com.trgis.trmap.userauth.service;

import java.util.List;

import com.trgis.trmap.userauth.model.AccountConfine;

/**
 * 接口访问 记录类
 * 
 * @author chenhl
 *
 */
public interface AccountConfineService {
	
	/**
	 * 根据ip 注册类型 查询注册记录
	 * 
	 * @param username
	 * @return
	 */
	public List<AccountConfine> findAccountConfineByIp(String recordip,Integer rtype,String strdate);
	
	/**
	 * 添加 注册记录
	 * @param 
	 */
	public void addAccountConfine(AccountConfine accountConfine);
	
	/**
	 *  查询 修改密码 邮箱记录流水
	 */
	public List<AccountConfine> findByEmail(String ip,String email,String strdates);
}
