package com.trgis.trmap.personal.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.trmap.personal.dao.UserAttachDao;
import com.trgis.trmap.personal.dao.UserCommentDao;
import com.trgis.trmap.personal.dao.UserRecordDao;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.util.BeanUtil;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.exception.AccountFindException;
import com.trgis.trmap.userauth.model.User;

@Service
@Transactional
public class UserRecordServiceImpl implements UserRecordService {

	@Autowired
	private UserRecordDao userRecordDao;
	@Autowired
	private UserAttachDao userAttachDao;
	@Autowired
	private UserCommentDao  userCommentDao;

	private static final Log log = LogFactory.getLog(UserRecordServiceImpl.class);

	/**
	 * 添加记录
	 * @return 
	 */
	@Override
	public Long createUserRecord(UserRecord userRecord) throws UserRecordException {
		UserRecord record = null;
		try {
			if (BeanUtil.isEmpty(userRecord)) {
				throw new UserRecordException("事件记录对象不能为空!");
			}
			record = userRecordDao.save(userRecord);
			log.debug("事件记录保存成功！");
		} catch (Exception e) {

			log.debug("事件记录保存失败！");
			throw new UserRecordException("事件记录保存失败!");
		}
		return record.getId();
	}
    /**
     * 删除事件：
     * 设置事件的DELFLAG---del
     * 同时清除所有的相关附件
     */
	@Override
	public void deleteUserRecord(Long id) throws UserRecordException {
		try {
			UserRecord userRecord = userRecordDao.findOne(id);
			if (BeanUtil.isEmpty(userRecord)) {
				throw new UserRecordException("记录未找到！");
			}
			userRecord.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
			userRecordDao.saveAndFlush(userRecord);
			log.debug("记录删除成功！");
			userAttachDao.clearAttachfileById(userRecord);
			log.debug("记录相关附件删除成功！");
			userCommentDao.clearUserCommentById(userRecord.getId());
			log.debug("事件 评论删除状态改为已删除标记！");
		} catch (Exception e) {
			log.debug("记录删除失败！");
			throw new UserRecordException("记录删除失败！");
			
		}
	}
    /**
     * 编辑记录:
     * 1.修改记录的信息
     * 
     * 
     */
	@Override
	public void editUserRecord(UserRecord userRecord) throws UserRecordException {
		try {
			if (BeanUtil.isEmpty(userRecord)) {
				throw new UserRecordException("事件对象不能为空！");
			}
			if(userRecordDao.findOne(userRecord.getId()) != null){
				userRecordDao.saveAndFlush(userRecord);
				
			}else{
				log.debug("===can't find userRecord your id is correct?===");
				throw new AccountFindException("can't find userRecord your id is correct?");
			}
			userRecordDao.saveAndFlush(userRecord);
			log.debug("修改成功！");
		} catch (Exception e) {
			log.debug("修改失败！");
			throw new UserRecordException("修改失败！");
		}
	}


	@Override
	public UserRecord findUserRecordById(Long id) throws UserRecordException {
		UserRecord userRecord = null;
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new UserRecordException("id为空");
			}
			userRecord = userRecordDao.findOne(id);
			log.debug("查询成功！");
			
		} catch (Exception e) {
			log.debug("查询失败！");
			throw new UserRecordException("查询失败!");
		}
		return  userRecord;
	}

	@Override
	public Long findUserRecordByUserTopic(Long topicId,Integer delflag) {
		
		return userRecordDao.findUserRecordByUserTopic(topicId,delflag,EnumUtil.SHAREFLAG.YFX.getValue());
	}
	
	/**
	 * @author Alice
	 */
	@Override
	public List<UserRecord> findByTopic(UserTopic userTopic, Integer delflag, Integer shareflag, String keywords) {
		if(BeanUtil.isEmpty(keywords)){
			keywords = "";
		}
		if(shareflag == EnumUtil.SHAREFLAG.ALL.getValue()){
			return userRecordDao.findByTopic(userTopic, delflag, keywords);
		}else{
			return userRecordDao.findByTopic(userTopic, delflag, shareflag, keywords);
		}
	}
	@Override
	public List<UserRecord> getListByMonth(Date date,User user) throws UserRecordException {
		try {
			if (BeanUtil.isEmpty(date)) {
				date = new Date();
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String strDate = format.format(date);
			
			log.debug("查询成功！");
			return userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), strDate,EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("查询失败！");
			throw new UserRecordException("查询失败!");
		}
	}

	@Override
	public Date getListNextMonth(Date date, Integer type,User user,Long[] myids,Long[] collids) throws UserRecordException {
		try {
			Integer myNextMonth = null,state = null;
			if (BeanUtil.isEmpty(date)) {
				date = new Date();
			}
			if (BeanUtil.isEmpty(type)) {
				type = -1;
			}
			Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
			Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
			Integer collNextMonth= null;
			if (type == -1) { // 查询上一个月的数据
				Integer minYear = userRecordDao.getMinYear(EnumUtil.CHECKSTATUS.CHECKED.getValue());
				while (myNextMonth == null) {
					if (minYear <= year) {// 如果有记录的时间的年份小于当前的年，继续查询前一年的数据
						if (myids != null) { // 查询我的专题前一个月有数据的月份
							myNextMonth = userRecordDao.getPreMonth(myids, year, month,EnumUtil.CHECKSTATUS.CHECKED.getValue(),EnumUtil.SHAREFLAG.YFX.getValue(),EnumUtil.DELFLAG.NOMAL.getValue());
						}
						if (collids != null) {// 查询我的收藏前一个月有数据的月份
							collNextMonth = userRecordDao.getPreMonthCollect(collids, year, month,EnumUtil.CHECKSTATUS.CHECKED.getValue(),EnumUtil.SHAREFLAG.YFX.getValue());
						}
						year--;
						state = compare(myNextMonth, collNextMonth, true);
					} else {
						break;
					}
				}
				year++;
			} else { // 查询下一个月的数据
				Integer maxYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
				while (myNextMonth == null) {
					if (maxYear >= year) {
						if (myids != null) {
							myNextMonth = userRecordDao.getAfterMonth(myids, year, month,EnumUtil.CHECKSTATUS.CHECKED.getValue(),EnumUtil.SHAREFLAG.YFX.getValue(),EnumUtil.DELFLAG.NOMAL.getValue());
						}
						if (collids != null) {
							collNextMonth = userRecordDao.getAfterMonthCollect(collids,year, month,EnumUtil.CHECKSTATUS.CHECKED.getValue(),EnumUtil.SHAREFLAG.YFX.getValue());
						}
						year++;
						state = compare(myNextMonth, collNextMonth, false);
					} else {
						break;
					}
				}
				year--;
			}
			if (state == null) {
				throw new UserRecordException("相邻月没有数据！");
			}
			//如果相邻月有记录，将时间格式话为 yy-MM
			StringBuffer sb = new StringBuffer().append(year).append("-");
			if (state < 10) {
				sb.append("0").append(state);
			} else {
				sb.append(state);
			}
			log.debug("查询成功！");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
			if (type == -1) {// 查询上一个月的数据
				if (myNextMonth !=null && collNextMonth == null) { //我的专题前一个月有数据，我的收藏没有数据
					List<UserRecord> list = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					return list.get(0).getOccurtime();
				}
				if (collNextMonth !=null && myNextMonth == null) { //我的收藏前一个月有数据，我的专题没有数据
					List<UserRecord> collList = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(), collids);
					return collList.get(0).getOccurtime();
				}
				//我的收藏和我的专题前一个月都有数据，比较哪一个时间是最后一天
				if (myNextMonth == collNextMonth) {
					List<UserRecord> list = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					List<UserRecord> collList = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(), collids);
					int my = Integer.parseInt(dateFormat.format(list.get(0).getOccurtime()));
					int coll = Integer.parseInt(dateFormat.format(collList.get(0).getOccurtime()));
					return my > coll ? list.get(0).getOccurtime() : collList.get(0).getOccurtime();
				}
				if (myNextMonth > collNextMonth) {
					List<UserRecord> list = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					return list.get(0).getOccurtime();
				}else{
					List<UserRecord> collList = userRecordDao.getListByMonthDesc(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(), collids);
					return collList.get(0).getOccurtime();
				}
			} else {// 查询下一个月的数据
				if (myNextMonth !=null && collNextMonth == null) {//我的专题后一个月有数据，我的收藏没有数据
					List<UserRecord> list = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					return list.get(0).getOccurtime();
				}
				if (collNextMonth !=null && myNextMonth == null) {//我的收藏后一个月有数据，我的专题没有数据
					List<UserRecord> collList = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),collids);
					return collList.get(0).getOccurtime();
				}
				//我的收藏和我的专题后一个月都有数据，比较哪一个时间是最前一天
				if (myNextMonth == collNextMonth) {
					List<UserRecord> collList = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),collids);
					List<UserRecord> list = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					int my = Integer.parseInt(dateFormat.format(list.get(0).getOccurtime()));
					int coll = Integer.parseInt(dateFormat.format(collList.get(0).getOccurtime()));
					return my < coll ? list.get(0).getOccurtime() : collList.get(0).getOccurtime();
				}
				if (myNextMonth > collNextMonth) {
					List<UserRecord> collList = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),collids);
					return collList.get(0).getOccurtime();
				}else{
					List<UserRecord> list = userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), sb.toString(),EnumUtil.CHECKSTATUS.CHECKED.getValue(),user);
					return list.get(0).getOccurtime();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("查询失败！");
			throw new UserRecordException("查询失败!");
		}
	}
	
	/**
	 * @Description: 比较两个数的大小
	 * @author yanpeng
	 * @date 2016年3月26日 下午1:00:43
	 * @param a
	 * @param b
	 * @param flag true 返回最大值  false 返回最小值
	 * @return
	 */
	private Integer compare(Integer a,Integer b,boolean flag){
//		if (a == null && b == null) {
//			return null;
//		}
//		if (a == null && b != null) {
//			return b;
//		}
//		if (a != null && b == null) {
//			return a;
//		}
		if (a == null) 
			if (b == null) return null;
			else return b;
		else 
			if (b == null) return a;
			else return a > b == flag ? a : b;
			 
		 
	}
	
	/**
	 * @author Alice
	 */
	@Override
	public Long countByTopic(UserTopic userTopic, Integer shareflag, Integer delflag,String key) throws UserRecordException {
		if (null==key||key.equals("")) {
			return userRecordDao.countByTopic(userTopic, shareflag, delflag);
		}else{
			return userRecordDao.countByTopic(userTopic, shareflag, delflag,key);
		}
	}
	@Override
	public void updateRecordByTopicId(Long topicid, Integer shareflag) {
		try {
			userRecordDao.upRecordshareflagByTid(shareflag, topicid,EnumUtil.DELFLAG.NOMAL.getValue());
			log.debug("修改相关记录成功！");
		} catch (Exception e) {
			log.debug("修改相关记录失败！");
		}
	}
	@Override
	public List<UserRecord> getListByMonth(Date date,List<UserTopic> topicList) throws UserRecordException {
		try {
			if (BeanUtil.isEmpty(date)) {
				date = new Date();
			}
			if (BeanUtil.isEmpty(topicList)) {
				return new ArrayList<UserRecord>();
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String strDate = format.format(date);
			Long[] ids = new Long[topicList.size()];
			for (int i = 0; i < topicList.size(); i++) {
				ids[i] = topicList.get(i).getId();
			}
			log.debug("查询成功！");
			return userRecordDao.getListByMonth(EnumUtil.DELFLAG.NOMAL.getValue(), strDate,EnumUtil.CHECKSTATUS.CHECKED.getValue(),ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("查询失败！");
			throw new UserRecordException("查询失败!");
		}
	}
}
