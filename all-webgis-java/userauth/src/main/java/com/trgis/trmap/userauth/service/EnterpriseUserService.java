package com.trgis.trmap.userauth.service;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;

/**
 * 企业用户服务接口
 * @author doris
 * @date 2015年11月26日
 */
public interface EnterpriseUserService {
	/**
	 * 
	 * 创建用户
	 * 
	 * @param userForm
	 * @return
	 * @throws AccountRegisterException
	 */
	public User createUser(UserForm userForm) throws AccountRegisterException;

	/**
	 * 为了保证事务的正确性，用户创建完成后才发送激活邮件，如激活邮件发送失败则可以调用此方法再次发送.
	 * 
	 * @param user
	 * @throws MailSendFailedException
	 *             用户邮件发送失败时抛出此异常
	 */
	public void sendUserActiveEmail(User user);

	/**
	 * UserManage:VALIDATE
	 * 
	 * 邮箱验证
	 * 
	 * @param email
	 * @param code
	 * @return 
	 */
	public User activeUser(String email, String code);

	/**
	 * UserManage:EDIT
	 * 
	 * 编辑用户
	 * 
	 * @param user
	 */
	public void editUser(Long id, UserForm user);

	/**
	 * 
	 * 用户状态管理
	 * 
	 * @param id
	 * @param status
	 */
	public void changeUserStatus(Long id, UserStatus status);

	/**
	 * 
	 * 修改密码
	 * 
	 * @param id
	 * @param oldPass
	 * @param newPass
	 */
	public void changePass(Long id, String oldPass, String newPass);

	/**
	 * 
	 * 找回密码
	 * 
	 * 触发找回密码邮件
	 * 
	 * @param email
	 */
	public void findPassFromMailAndMobile(String email, String mobile);

	/**
	 * 验证重置密码请求是否合法
	 * 
	 * @param securityuser
	 * @param securitykey
	 */
	public boolean validationResetPass(String securityuser, String securitykey);

	/**
	 * 重置密码
	 * 
	 * @param id
	 * @param newPass
	 */
	public void resetPassword(Long id, String newPass);

	/**
	 * 
	 * 找回账号
	 * 
	 * 触发找回账号邮件，将用户账号发送至邮箱
	 * 
	 * @param email
	 * @param mobile
	 */
	public void findAccountFromMailAndMobile(String email, String mobile);

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);
	/**
	 * 根据电话查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByMobile(String mobile);
	
	/**
	 * 根据企业名称查询企业用户
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByEnterprisename(String enterprisename);
	/**
	 * 根据用户id查询用户
	 * 
	 * @param id
	 * @return
	 */
	public User findUser(Long id);

	/**
	 * 根据条件查询所有结果
	 * 
	 * @param conditionGroup
	 * @return
	 */
	public Page<User> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);

	/**
	 * 根据邮箱查询用户
	 * @param email
	 * @return
	 */
	public User findUserByEmail(String email);
	
	/**
	 * @author Alice
	 * 给企业用户发送通知
	 * @param user
	 * @param msg
	 */
	public void sendNoticeEmail(User user, String msg);
	/**
	 * @author liuyan
	 * 给企业用户发送邮箱重置通知
	 * @param user
	 */
	public void sendresetEmail(User user);
	 /**
     * @author add by liuyan
	 * 重置邮件
     */
	public void resetemail(Long id, String email,String code);
	

}
