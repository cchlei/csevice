/**
 * 
 */
package com.trgis.trmap.userauth.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.trmap.userauth.dao.ChangepassInfoDAO;
import com.trgis.trmap.userauth.dao.UserDAO;
import com.trgis.trmap.userauth.exception.AccountFindException;
import com.trgis.trmap.userauth.exception.AccountRegisterException;
import com.trgis.trmap.userauth.exception.ValidationCodeException;
import com.trgis.trmap.userauth.model.ChangepassInfo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserForm;
import com.trgis.trmap.userauth.service.UserService;
import com.trgis.trmap.userauth.util.UserEncrypt;

/**
 * @author zhangqian
 *
 */
@Service
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserServiceImpl implements UserService {

	// 运行日志记录
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	// ======================发送邮件相关配置==========================//
	private static final String SENDER = "account@trmap.cn";
	private static final String SENDERNAME = "天润云地图";

	private static final String SUBJECT_ACTIVE = "天润云地图-邮箱验证";
	private static final String SUBJECT_FINDACCOUNT = "天润云地图-账号找回";
	private static final String SUBJECT_RESETPASS = "天润云地图-重置密码";
	private static final String SUBJECT_RESETEMAIL = "天润云地图-重置邮箱";

	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String CODE = "code";
	private static final String USERNAME = "username";
	private static final String SECURITYUSER = "securityuser";
	private static final String SECURITYKEY = "securitykey";

	// ======================================================//

	private static final String EMAIL_QUEUE = "tr-email";
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private UserDAO userDao;

	@Autowired
	private ChangepassInfoDAO changepassInfoDao;
	
	/**
	 * 加密用户邮箱生成code验证码
	 * 
	 * @param email
	 * @param salt
	 * @return
	 */
	private String encryptEmailCode(String email, String salt) {
		return UserEncrypt.emailCode(email, salt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trgis.qtmap.userauth.service.UserService#createUser(com.trgis.
	 * qtmap.qtuser.userauth.service.UserForm)
	 */
	@Override
	public User createUser(UserForm userForm) throws AccountRegisterException {
		logger.debug("===creat user.===");
		Assert.notNull(userForm);
		User user = null;
		// 检查用户表单数据是否错误
		user = userDao.findByUsername(userForm.getUsername());
		if (user != null) {
			logger.debug("===user account is exist,you can't create repeat.===");
			throw new AccountRegisterException("===user account is exist,you can't create repeat.===");
		}
		user = userDao.findByEmail(userForm.getEmail());
		if (user != null) {
			logger.debug("===user email is exist,you can't create repeat.===");
			throw new AccountRegisterException("===user email is exist,you can't create repeat.===");
		}
		// user = userDao.findByMobile(userForm.getMobile());
		// if (user != null) {
		// logger.debug("===user mobile is exist,you can't create repeat.===");
		// throw new AccountRegisterException("===user mobile is exist,you can't
		// create repeat.===");
		// }
		if (StringUtils.isBlank(userForm.getEmail())) {
			logger.debug("===user email error,email can't blank.===");
			throw new AccountRegisterException("===user account error,user email can't blank.===");
		}
		user = new User();
		logger.debug("===copying userform to user.===");
		try {
			BeanUtils.copyProperties(user, userForm);
			user.setName(user.getUsername());// 默认的用户名称使用账号
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.debug("===user register form incorrect.===", e);
			throw new AccountRegisterException("===user register form incorrect.===");
		}
		//用户注册为个人用户
		user.setEntity(UserEntity.PERSONAL);
		logger.debug("===generate user security info.===");
		user.setSalt(UserEncrypt.generateSalt());
		logger.debug("===encrypt user pass.===");
		user.setPassword(UserEncrypt.md5hash(userForm.getTextpassword(), user.getSalt()));
		userDao.save(user);
		logger.debug("===create user success.===");
		return user;
	}

	@Override
	public User activeUser(String email, String code) {
		logger.debug("===email active user account.===");
		Assert.notNull(email, "email is null.");
		Assert.notNull(code, "validate code is null.");
		User user = userDao.findByEmail(email);
		if (user == null) {
			logger.debug("===email is not exist.===");
			throw new AccountFindException("email is not exist.");
		}
		// 验证code是否正确，防止通过URL非法激活用户
		String encryptCode = encryptEmailCode(email, user.getSalt());
		if (!StringUtils.equals(encryptCode, code)) {
			logger.debug("===validate code is incorrect.===");
			throw new ValidationCodeException("validate code is incorrect.");
		}
		user.setStatus(UserStatus.ACTIVE);
		userDao.saveAndFlush(user);
		logger.debug("===user acount active success.===");
		return user;
	}

	@Override
	public void editUser(Long id, UserForm userForm) {
		logger.debug("===edit user info===");
		Assert.notNull(id, "user id is null.");
		Assert.notNull(userForm);
		logger.debug("===find user.===");
		User user = userDao.findOne(id);
		if (user == null) {
			logger.debug("===can't find user.does your id is correct?===");
			throw new AccountFindException("can't find user.does your id is correct?");
		}
		try {
			logger.debug("===change user info.===");
			BeanUtils.copyProperties(user, userForm);
			userDao.saveAndFlush(user);
			logger.debug("===change user success.===");
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.debug("===change user info error.===", e);
			throw new AccountFindException("change user info error.", e);
		}
	}

	@Override
	public void changeUserStatus(Long id, UserStatus status) {
		logger.debug("===change user status.===");
		Assert.notNull(id);
		Assert.notNull(status);
		logger.debug("===tart find user.===");
		User user = userDao.findOne(id);
		if (user == null) {
			logger.debug("===can't find user.does your id is correct?===");
			throw new AccountFindException("can't find user.does your id is correct?");
		}
		logger.debug("===change user status.===");
		user.setStatus(status);
		userDao.saveAndFlush(user);
		logger.debug("===change user status success.===");
	}

	@Override
	public void changePass(Long id, String oldPass, String newPass) {
		try {
			logger.debug("===change user pass===");
			Assert.notNull(id);
			Assert.notNull(oldPass);
			Assert.notNull(newPass);
			User user = userDao.findOne(id);
			if (user == null) {
				logger.debug("===can't find user.does your id is correct?===");
				throw new AccountFindException("can't find user.does your id is correct?");
			}
			logger.debug("===validate old pass is correct?===");
			if (!StringUtils.equals(user.getPassword(), UserEncrypt.md5hash(oldPass, user.getSalt()))) {
				logger.debug("===old password is incorrect.===");
				throw new AccountFindException("old password is incorrect");
			}
			logger.debug("===validate old password is correct. start change new pass.===");
			user.setSalt(UserEncrypt.generateSalt()); // 重置密码生成新salt
			user.setPassword(UserEncrypt.md5hash(newPass, user.getSalt()));
			userDao.saveAndFlush(user);
			logger.debug("===change new pass success.===");
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void findPassFromMailAndMobile(String email, String mobile) {
		logger.debug("===find user password.===");
		Assert.notNull(email);
//		Assert.notNull(mobile);
		logger.debug("===find user by request email&mobile.===");
		User user = userDao.findByEmail(email);
		if (user == null ){//|| !StringUtils.equals(user.getMobile(), mobile)) {
			logger.debug("===can't find user.does your email&mobile is correct?===");
			throw new AccountFindException("can't find user.does your email&mobile is correct?");
		}
		// 检查该用户是否之前发送过重置密码的邮件
		logger.debug("check this user find pass yet.");
		List<ChangepassInfo> oldChangepassInfoList = changepassInfoDao.findByUserId(user.getId());
		if (oldChangepassInfoList != null && !oldChangepassInfoList.isEmpty()) {
			// 删除之前的密码重置请求
			logger.debug("===remove all of find pass.===");
			changepassInfoDao.delete(oldChangepassInfoList);
		}

		// 创建新的密码重置请求
		logger.debug("===create new user find pass request and send new email.===");
		ChangepassInfo changepassInfo = new ChangepassInfo();
		changepassInfo.setUserId(user.getId());
		changepassInfo.setSalt(UserEncrypt.generateSalt());
		changepassInfo.setSecurityuser(securityUserEncrypt(changepassInfo));
		changepassInfo.setSecuritykey(securityKeyEncrypt(changepassInfo));
		changepassInfoDao.save(changepassInfo);
		logger.debug("===find user,begin send find account email.===");
		sendResetPassEmail(user, changepassInfo);
		logger.debug("===find account success.===");
	}

	@Override
	public void resetPassword(Long id, String newPass) {
		logger.debug("===reset user pass===");
		Assert.notNull(id);
		Assert.notNull(newPass);
		User user = userDao.findOne(id);
		if (user == null) {
			logger.debug("===can't find user.does your id is correct?===");
			throw new AccountFindException("can't find user.does your id is correct?");
		}
		logger.debug("===validate old password is correct. start change new pass.===");
		user.setSalt(UserEncrypt.generateSalt()); // 重置密码生成新salt
		user.setPassword(UserEncrypt.md5hash(newPass, user.getSalt()));
		userDao.saveAndFlush(user);
		logger.debug("===change new pass success.===");

	}

	/**
	 * 生成用户查询安全码
	 * 
	 * 用户查询重置请求
	 * 
	 * @param changepassInfo
	 * @return
	 */
	@SuppressWarnings("static-access")
	private String securityKeyEncrypt(ChangepassInfo changepassInfo) {
		String key = changepassInfo.getUserId() + changepassInfo.getCreateDate().toString() + changepassInfo.getSalt();
		String securityKey = UUID.randomUUID().randomUUID().toString() + "-"
				+ UserEncrypt.md5hash(key, changepassInfo.getSalt());
		return securityKey;
	}

	/**
	 * 用户重置密码给安全校验
	 * 
	 * 用于校验查询请求的合法性,做验证比较使用
	 * 
	 * @param changepassInfo
	 * @return
	 */
	private String securityUserEncrypt(ChangepassInfo changepassInfo) {
		String userKey = changepassInfo.getUserId() + changepassInfo.getSalt();
		String securityUser = UserEncrypt.md5hash(userKey, changepassInfo.getSalt());
		return securityUser;
	}

	@Override
	public boolean validationResetPass(String securityuser, String securitykey) {
		logger.debug("===validation user reset pass request is correct?===");
		ChangepassInfo changepassInfo = changepassInfoDao.findBySecurityuserAndSecuritykey(securityuser, securitykey);
		if (changepassInfo == null) {
			logger.debug("===Incorrect user reset requset.===");
			throw new ValidationCodeException("Incorrect user reset request.");
		}
		return true;
	}

	@Override
	public void findAccountFromMailAndMobile(String email, String mobile) {
		logger.debug("===find user account.===");
		Assert.notNull(email);
//		Assert.notNull(mobile);
		logger.debug("===find user by request email&mobile.===");
		User user = userDao.findByEmail(email);
		if (user == null ){// || !StringUtils.equals(user.getMobile(), mobile)) {
			logger.debug("===can't find user.does your email&mobile is correct?===");
			throw new AccountFindException("can't find user.does your email&mobile is correct?");
		}
		logger.debug("===find user,begin send find account email.===");
		sendFindAccountEmail(user);
		logger.debug("===find account success.===");
	}

	@Override
	public User findUserByUsername(String username) {
		logger.debug("===find user by username.===");
		if (StringUtils.isBlank(username)) {
			return null;
		}
		return userDao.findByUsername(username);
	}

	@Override
	public User findUser(Long id) {
		logger.debug("===find user by id.===");
		Assert.notNull(id, "id is not null.");
		return userDao.findOne(id);
	}

	@Override
	public void sendUserActiveEmail(User user) {
		logger.debug("===create user active email===");
		String receiver = user.getEmail();
		Map<String,Object> mailProperties = new HashMap<String, Object>();
		// 组织邮件的发送人和接收人
		mailProperties.put("to", receiver);
		mailProperties.put("from", SENDER);
		mailProperties.put("fromName", SENDERNAME);
		mailProperties.put("subject", SUBJECT_ACTIVE);
		// 如果使用模版则设置模版中的参数
		mailProperties.put("mail", "activemail.ftl");
		Map<String, Object> model = new HashMap<String,Object>();
		model.put(NAME, user.getName());
		model.put(EMAIL, user.getEmail());
		model.put(CODE, encryptEmailCode(user.getEmail(), user.getSalt()));
		mailProperties.put("model", model);
		// 发送到消息服务
		amqpTemplate.convertAndSend(EMAIL_QUEUE,mailProperties);
		logger.debug("===email send success.===");
	}

	/**
	 * 发送查找账号邮件
	 * 
	 * @param user
	 */
	private void sendFindAccountEmail(User user) {
		logger.debug("===create find account email===");
		String receiver = user.getEmail();
		Map<String,Object> mailProperties = new HashMap<String, Object>();
		// 组织邮件的发送人和接收人
		mailProperties.put("to", receiver);
		mailProperties.put("from", SENDER);
		mailProperties.put("fromName", SENDERNAME);
		mailProperties.put("subject", SUBJECT_FINDACCOUNT);
		// 如果使用模版则设置模版中的参数
		mailProperties.put("mail", "findaccount.ftl");
		Map<String, Object> model = new HashMap<String,Object>();
		model.put(NAME, user.getName());
		model.put(USERNAME, user.getUsername());
		mailProperties.put("model", model);
		// 发送到消息服务
		amqpTemplate.convertAndSend(EMAIL_QUEUE,mailProperties);
		logger.debug("===email send success.===");
	}

	/**
	 * 发送重置密码邮件
	 * 
	 * @param user
	 * @param changepassInfo
	 */
	private void sendResetPassEmail(User user, ChangepassInfo changepassInfo) {
		logger.debug("===create reset pass email===");
		String receiver = user.getEmail();
		Map<String,Object> mailProperties = new HashMap<String, Object>();
		// 组织邮件的发送人和接收人
		mailProperties.put("to", receiver);
		mailProperties.put("from", SENDER);
		mailProperties.put("fromName", SENDERNAME);
		mailProperties.put("subject", SUBJECT_RESETPASS);
		// 如果使用模版则设置模版中的参数
		mailProperties.put("mail", "entresetpass.ftl");
		Map<String, Object> model = new HashMap<String,Object>();
		model.put(NAME, user.getName());
		model.put(EMAIL, user.getEmail());
		model.put(SECURITYUSER, changepassInfo.getSecurityuser());
		model.put(SECURITYKEY, changepassInfo.getSecuritykey());
		mailProperties.put("model", model);
		// 发送到消息服务
		amqpTemplate.convertAndSend(EMAIL_QUEUE,mailProperties);
		logger.debug("===email send success.===");
	}
	// =========================================================================================

	@Override
	public Page<User> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<User> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = userDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userDao.findAll(specifications, page);
	}

	@Override
	public User findUserByEmail(String email) {
		logger.debug("===find user by email.===");
		if (StringUtils.isBlank(email)) {
			return null;
		}
		return userDao.findByEmail(email);
	}
	@Override
	public User findUserByname(String name) {
		logger.debug("===find user by name.===");
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return userDao.findByname(name);
	}
	
	
	@Override
	public List<User> findUserByStatus(UserStatus status) {
		logger.debug("===find user by status.===");
//		if (StringUtils.isBlank(status)) {
//			return null;
//		}
		return userDao.findByStatus(status);
	}

	@Override
	public void deleteUser(User user) {
		logger.debug("===find user by user.===");
		userDao.delete(user);
	}
	
	@Override
	public User findUserByMobile(String mobile) {
		logger.debug("===find user by mobile.===");
		if (StringUtils.isBlank(mobile)) {
			return null;
		}
		return userDao.findByMobile(mobile);
	}
	 /**
     * @author add by liuyan
	 * 发送重置邮件通知
	 * @param user
     */
	@Override
	public void sendresetEmail(User user) {
		logger.debug("===resetemail  start===");
		String receiver = user.getEmail();
		Map<String,Object> mailProperties = new HashMap<String, Object>();
		// 组织邮件的发送人和接收人
		mailProperties.put("to", receiver);
		mailProperties.put("from", SENDER);
		mailProperties.put("fromName", SENDERNAME);
		mailProperties.put("subject", SUBJECT_RESETEMAIL);
		// 如果使用模版则设置模版中的参数
		mailProperties.put("mail", "resetemail.ftl");
		Map<String, Object> model = new HashMap<String,Object>();
		model.put(NAME, user.getUsername());
		model.put(EMAIL, user.getEmail());
		model.put(CODE, encryptEmailCode(user.getEmail(), user.getSalt()));
		mailProperties.put("model", model);
		// 发送到消息服务
		amqpTemplate.convertAndSend(EMAIL_QUEUE,mailProperties);
		logger.debug("===email send success.===");
	}
	 /**
     * @author add by liuyan
	 * 重置邮件
     */
	@Override
	public void resetemail(Long id, String email,String code) {
		logger.debug("===change user status.===");
		Assert.notNull(id);
		Assert.notNull(email);
		logger.debug("===tart find user.===");
		User user = userDao.findOne(id);
		if (user == null) {
			logger.debug("===can't find user.does your id is correct?===");
			throw new AccountFindException("can't find user.does your id is correct?");
		}
		// 验证code是否正确，防止通过URL非法激活用户
		String encryptCode = encryptEmailCode(email, user.getSalt());
		if (!StringUtils.equals(encryptCode, code)) {
			logger.debug("===validate code is incorrect.===");
			throw new ValidationCodeException("validate code is incorrect.");
		}
		logger.debug("===change user email.===");
		user.setEmail(email);
		userDao.saveAndFlush(user);
		logger.debug("===change user email success.===");
	}

	@Override
	public Long countByStatus(Date begindate, Date enddate, UserStatus status, UserEntity entity) {
		// TODO Auto-generated method stub
		return userDao.countByStatus(begindate, enddate, status, entity);
	}
/**
 * 获取所有用户
 */
	@Override
	public List<User> findAllUser(ConditionGroup conditionGroup){
		Specification<User> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		return userDao.findAll(specifications);
	}

}
