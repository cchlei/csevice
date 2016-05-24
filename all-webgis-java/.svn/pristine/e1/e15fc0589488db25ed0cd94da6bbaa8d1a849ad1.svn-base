package com.trgis.trmap.personal.webapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.exception.UserCommentException;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.exception.UserTopicException;
import com.trgis.trmap.personal.model.UserAttach;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.service.UserCommentService;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.utils.StringHandle;
import com.trgis.trmap.personal.webapp.vo.UserRecordVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * 事件记录控制器
 * 
 * @author yanpeng
 *
 */

@Controller
@RequestMapping("/record")
public class UserRecordController {
	private static final Logger logger = LoggerFactory.getLogger(UserRecordController.class);

	@Autowired
	private UserTopicService topicService;
	@Autowired
	private UserCommentService commentService;
	@Autowired
	private UserAttachService attachService;
	@Autowired
	private UserRecordService userRecordService;
	@Autowired
	private UserAttachService userAttachService;
	@Autowired
	private UserService userService;

	/**
	 * @Description: 记录详情
	 * @author yanpeng
	 * @date 2016年3月10日 下午2:01:23
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRecord/{id}", method ={RequestMethod.GET,RequestMethod.POST})
	public UserRecordVo getUserRecordById(@PathVariable("id") Long recordId){
		UserRecordVo uRecordVo = new UserRecordVo();
		try {
			UserRecord userRecord  = userRecordService.findUserRecordById(recordId);
			uRecordVo.setId(userRecord.getId());
			uRecordVo.setName(userRecord.getTitle());
			uRecordVo.setDescription(userRecord.getDescription());
			uRecordVo.setOccurtime(new SimpleDateFormat("yyyy-MM-dd").format(userRecord.getOccurtime()));
			uRecordVo.setAddrname(userRecord.getAddrname());
			uRecordVo.setGeom(userRecord.getGeom());
			uRecordVo.setRecord_type(userRecord.getRecordType());
			uRecordVo.setShareflag(userRecord.getShareflag());
			List<UserAttach> attachs = userAttachService.findAllUserAttach(userRecord,EnumUtil.DELFLAG.NOMAL.getValue(),null,null);
			List<String> list = new ArrayList<String>();
			for (UserAttach userAttach : attachs) {
				list.add(userAttach.getOssid());
			}
			uRecordVo.setPiclist(list);
		} catch (UserRecordException e) {
			logger.debug("===记录详情获取失败！！===");
			e.printStackTrace();
		} catch (UserAttachException e) {
			logger.debug("===记录详情获取失败！！===");
			e.printStackTrace();
		}
		return uRecordVo;
	}
	/**
	 * @Description:去添加的页面
	 * @Author liuyan 
	 * @Date 2016年3月10日
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/toadd/{id} ", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAdd(@PathVariable("id") String topicId,ModelAndView mv) throws UserTopicException{
		try {
			UserTopic userTopic = topicService.findUserTopicById(Long.valueOf(topicId));
			if (userTopic.getShareflag() == 0) {// 表示私密专题
				mv.addObject("flag","0");// 表示私密专题
			}else{
				mv.addObject("flag","1");// 表公开专题
			}

		} catch (Exception e) {
			logger.debug("===就是这个专题的id不存在！！===");
			e.printStackTrace();
		}

		mv.addObject("topicId", topicId);
		mv.setViewName("/record/record_add");
		return mv;
		
	}
	/**
	 * @ClassName:CreateRecord
	 * @Description:添加事件
	 * @Author liuyan
	 * @Date 2016年3月10日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Result createRecord(UserRecord userRecord, String topicid, String coverurl, String occurtimes) {
		Result result = new Result();
		logger.debug("===添加记录！！===");
		// 设置时间
		if (StringUtils.isNotBlank(occurtimes)) {
			Date occurtime = StringHandle.getDateFromString(occurtimes);
			userRecord.setOccurtime(occurtime);
		}
		try {
			// 设置专题
			if (StringUtils.isNotBlank(topicid)) {
				UserTopic userTopic;
				userTopic = topicService.findUserTopicById(Long.valueOf(topicid));
				if (!BeanUtil.isEmpty(userTopic)) {
					logger.debug("===配置专题的id===");
					userTopic.setId(Long.valueOf(topicid));
					userRecord.setTopic(userTopic);
					
				}
			}
		} catch (NumberFormatException e) {
			result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		} catch (UserTopicException e) {
			result.setMsg("专题id不存在！");
			result.setStatus(Result.STATUS_FAILED);
			logger.debug("===专题的id不存在===");
			e.printStackTrace();
		}

		try {
			// 添加事件
			logger.debug("===添加事件===");
			Long id = userRecordService.createUserRecord(userRecord);// 添加事件
			if (id != null) {
				if (StringUtils.isNotBlank(coverurl)) {// 循环添加事件相关附件
					String ids[] = coverurl.split(",");
					for (String ossid : ids) {
						if (StringUtils.isNotBlank(ossid)) {
							userAttachService.updateRecord(id, ossid);
						}
					}
				}
			}
			result.setStatus(Result.STATUS_OK);
			result.setMsg("记录添加成功！");
			logger.debug("===添加事件成功===");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("记录添加失败！");
			logger.debug("===添加事件失败！===");
		}

		return result;
	}
    /**
	 * @Description: 事件记录的修改
	 * @author yanpeng
	 * @date 2016年3月10日 下午2:44:55
	 * @param userRecord 记录对象
	 * @param attachStr 附件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result updateRecord(String occurtimes,UserRecord userRecord,String coverurl,String topicid){
		Result result = new Result();
		UserTopic userTopic = new UserTopic();
		try {
			//获取未修改前的状态
			if (StringUtils.isNotBlank(topicid)){
				userTopic.setId(Long.valueOf(topicid));
				userRecord.setTopic(userTopic);
			}
			Date dateFromString = StringHandle.getDateFromString(occurtimes);
			userRecord.setOccurtime(dateFromString);
			userRecordService.editUserRecord(userRecord);
			if (StringUtils.isNotBlank(coverurl)) {//修改附件的rid
				String ids[] = coverurl.split(",");
				for (String ossid : ids) {
					if (StringUtils.isNotBlank(ossid)) {
						userAttachService.updateRecord(userRecord.getId(),ossid);
					}
				}
			}
			
			result.setStatus(Result.STATUS_OK);
			result.setMsg("修改成功");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("修改失敗");
		}
		return result;
	}
	
	/**
	 * @Description: 按照事件条件查询符合用户选择状态的事件数据
	 * @author yanpeng
	 * @date 2016年3月12日 上午10:52:04
	 * @param date
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/month", method ={RequestMethod.POST, RequestMethod.GET})
	public Result getListByMonth(String date,Integer dire) {
		Result result = new Result();
		Map<String,Object> map = null;
		List<Map<String,Object>> reList = new ArrayList<Map<String,Object>>();
		Date dateFromString = StringHandle.getDateFromString(date);
		try {
			if (BeanUtil.isEmpty(dateFromString)) {
				dateFromString = new Date();
			}
			User user = getUser();
			List<UserTopic> topicList = topicService.findAllUserTopicCollect(user.getId());
			List<UserRecord> list = userRecordService.getListByMonth(dateFromString,user);
			List<UserRecord> listCollect = userRecordService.getListByMonth(dateFromString,topicList);
			List<UserRecord> datas = new ArrayList<UserRecord>();
			//合并两个集合，并按时间排序
 			while(list.size()!=0&&listCollect.size()!=0){
 				if (list.get(0).getOccurtime().getTime()<listCollect.get(0).getOccurtime().getTime()) {
 					datas.add(list.get(0));
 					list.remove(0);
				}else{
					datas.add(listCollect.get(0));
					listCollect.remove(0);
				}
			}
 			if (list.size()==0&&listCollect.size()!=0) {
 				datas.addAll(listCollect);
			}
 			if (list.size()!=0&&listCollect.size()==0) {
 				datas.addAll(list);
			}
			for (UserRecord userRecord : datas) {
				map = new HashMap<String,Object>();
				map.put("id",userRecord.getId());
				map.put("title", userRecord.getTitle());
				map.put("occurtime", userRecord.getOccurtime());
				map.put("addrname", userRecord.getAddrname());
				map.put("geom", userRecord.getGeom());
				map.put("recordType", userRecord.getRecordType());
				map.put("shareflag", userRecord.getShareflag());
				map.put("topiccolor", userRecord.getTopic().getTopiccolor());
				map.put("coverturl", attachService.findOne(userRecord.getId(),EnumUtil.DELFLAG.NOMAL.getValue()));
				if (map.get("coverturl")==null) {
					map.put("coverturl", userRecord.getTopic().getCoverurl());
				}
				map.put("topicid", userRecord.getTopic().getId());
				map.put("username", userRecord.getTopic().getUser().getUsername());
				map.put("headimg", userRecord.getTopic().getUser().getHeadimg());
				reList.add(map);
			}
			if (reList==null || reList.size()==0 ) {
				result.setStatus(Result.STATUS_FAILED);
				result.setData(new ArrayList<Object>());
				result.setMsg("本月没有数据");
			}else{
				result.setStatus(Result.STATUS_OK);
				result.setData(reList);
			}
		} catch (Exception e) {
			logger.debug("==按月查询数据查询失败！==");
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("本月没有数据");
			result.setData(new ArrayList<Object>());
		}
		return result;
	}
	/**
	 * @Description: 前往事件修改页面
	 * @author yanpeng
	 * @date 2016年3月12日 下午4:08:14
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/toedit/{id}", method ={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEdit( @PathVariable("id") Long id,ModelAndView mv) throws ParseException{
		try {
			UserRecord record = userRecordService.findUserRecordById(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String datestr = sdf.format(record.getOccurtime());
	       /* Date date= StringHandle.getDateFromString(datestr);
			record.setOccurtime(date);*/
			UserTopic userTopic = record.getTopic();
			if(BeanUtil.isNotEmpty(userTopic)){
				if (userTopic.getShareflag() == 0) {// 表示私密专题
					mv.addObject("flag","0");// 表示私密专题
				}else{
					mv.addObject("flag","1");// 表公开专题
				}
			}
	        mv.addObject("recordId",id);
			mv.addObject("record",record);
			mv.addObject("datestr",datestr);
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
		mv.setViewName("/record/record_edit");
		return mv;
	}
	
	/**
	 * 时间轴页面事件列表
	 * @author Alice
	 * @param topicid	专题id
	 * @param open		分享状态
	 * @param key		关键字
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/records/{id}")
	public Map<String, Object> records(@PathVariable("id") Long topicid, Integer open, String key) {
		Map<String, Object> result = new HashMap<String, Object>();
		Assert.notNull(topicid, "参数不能为空");
		UserTopic topic = null;
		try {
			topic = topicService.findUserTopicById(topicid);
			User user =topic.getUser();
			String username = (String) SecurityUtils.getSubject().getPrincipal();
			User loginUser = userService.findUserByUsername(username);//当前登录的用户
			Long num_of_public = userRecordService.countByTopic(topic, EnumUtil.SHAREFLAG.YFX.getValue(), EnumUtil.DELFLAG.NOMAL.getValue(),key);
			Long num_of_private =userRecordService.countByTopic(topic, EnumUtil.SHAREFLAG.WFX.getValue(), EnumUtil.DELFLAG.NOMAL.getValue(),key);
			if (topic != null) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				 List<UserRecord> features = null;
		         if(user.getId().equals(loginUser.getId())){//查看信息的是当前用户是全部显示
		        	 logger.debug("主人查询！");
		        	features = userRecordService.findByTopic(topic,EnumUtil.DELFLAG.NOMAL.getValue(), open, key);
		         }else{//不是专题的主人查看的时候只能查看公开专题下的公开记录
		        	 logger.debug("访客查询！");
		        	features = userRecordService.findByTopic(topic, EnumUtil.DELFLAG.NOMAL.getValue(),EnumUtil.SHAREFLAG.YFX.getValue(), key);
		         }
				if (features != null && !features.isEmpty()) {
					for (UserRecord record : features) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", record.getId());
						map.put("name", record.getTitle());
						String desc = "";
						if(BeanUtil.isNotEmpty(record.getDescription())){
							if(record.getDescription().length()>150){//时间轴的描述信息字数限制
							    desc =	record.getDescription().substring(0, 150);
							}else{
								 desc =	record.getDescription();
							}
						}
						map.put("description", desc);
						map.put("occurtime", record.getOccurtime().getTime());
						map.put("addrname", record.getAddrname());
						map.put("geom", record.getGeom());
						map.put("record_type", record.getRecordType());
						map.put("shareflag", record.getShareflag());
						List<UserAttach> attachlist = attachService.findAllUserAttach(record,EnumUtil.DELFLAG.NOMAL.getValue(),",jpg,JPG,jpeg,JPEG,bmp,BMP,png,PNG,",3);
						List<String> piclist = new ArrayList<String>();
						for (UserAttach userAttach : attachlist) {
							piclist.add(userAttach.getOssid());
						}
						map.put("piclist", piclist);//附件
						map.put("num_msg", commentService.countByRecord(record));//留言数
						list.add(map);
					}
				}
				result.put("num_of_public", num_of_public);
				result.put("num_of_private", num_of_private);
				result.put("rows", list);
			}
		} catch (UserTopicException e) {
			e.printStackTrace();
		} catch (UserAttachException e) {
			e.printStackTrace();
		} catch (UserCommentException e) {
			e.printStackTrace();
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @Description:获取附件
	 * @Author liuyan 
	 * @Date 2016年3月15日 下午7:55:16
	 * @param recordid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attachs/{id}")
	public  Map<String, Object> attachsByRecord(@PathVariable("id") Long recordid) {
		Map<String, Object> mv =new HashMap<String, Object>();
		List<Map<String, Object>> piclist = null;
		Assert.notNull(recordid, "参数不能为空");
	    UserRecord record = null;
		try {
			record = userRecordService.findUserRecordById(recordid);
		    List<UserAttach> attachlist = attachService.findAllUserAttach(record,EnumUtil.DELFLAG.NOMAL.getValue(),null,null);
		    piclist = new ArrayList<Map<String, Object>>();
		    System.out.println(attachlist.size());
		    for (UserAttach userAttach : attachlist) {
			   Map<String, Object> map = new HashMap<String, Object>();
			   map.put("id",userAttach.getOssid());
			   map.put("name", userAttach.getAttachName());
			   map.put("ext", userAttach.getAttachSuffix());
		       piclist.add(map);
		    }
		    mv.put("rows", piclist);
		} catch (UserRecordException e) {
			e.printStackTrace();
		} catch (UserAttachException e) {
			e.printStackTrace();
		}
		return mv;
		}
	/**
     * 删除指定专题下的事件记录
     * @author doris
     * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method ={RequestMethod.POST, RequestMethod.GET})
	public Map<String,Object> delete(Long id) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
			try {
				
				if(BeanUtil.isNotEmpty(id)){
					userRecordService.deleteUserRecord(id);;
					map.put("status", Result.STATUS_OK);
					map.put("msg"," 删除成功！");
					logger.debug("记录删除完毕！");
				}
				
			} catch (UserRecordException e) {
				map.put("status", Result.STATUS_FAILED);
				map.put("msg"," 删除失败！");
				logger.debug("记录删除失败！");
			}
		
		return map;
	}
	/**
	 * @Description: 前往记录浏览页面
	 * @author yanpeng
	 * @date 2016年3月17日 上午10:55:12
	 * @param recordId
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/toRecord/{id}", method ={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView toRecordBrows(@PathVariable("id") Long recordId,ModelAndView modelAndView){
		 Long ruserid = null;
		 String rusername = null;
		 try {
				User user = getUser();
			    UserRecord record =	userRecordService.findUserRecordById(recordId);
			    if(BeanUtil.isNotEmpty(record)){
				    UserTopic topic = record.getTopic();
				    if(topic!=null && BeanUtil.isNotEmpty(topic)){
				    	ruserid = topic.getUser().getId();
				    	rusername =topic.getUser().getUsername();
			        }
				    if (topic.getUser().getId()==user.getId()) {
				    	modelAndView.setViewName("/record/record_browse");
					}else{
						modelAndView.setViewName("/record/record_browse_public");
					}
			    }
				modelAndView.addObject("buser_id", ruserid);
				modelAndView.addObject("busername", rusername);
				modelAndView.addObject("recordId", recordId);
				modelAndView.addObject("cuserId", user.getId());
				modelAndView.addObject("current_user_img", user.getHeadimg());
				modelAndView.addObject("current_user_name", user.getUsername());
			} catch (Exception e) {
				logger.debug("==去浏览页面失败！==");
			}
			return modelAndView;
	}
	/**
	 * @Description: 前往记录浏览页面
	 * @author yanpeng
	 * @date 2016年3月17日 上午10:55:12
	 * @param recordId
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/topubRecord/{id}", method ={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView toPubRecordBrows(@PathVariable("id") Long recordId,ModelAndView modelAndView){
		 Long ruserid = null;
		 String rusername = null;
		try {
			    UserRecord record =	userRecordService.findUserRecordById(recordId);
			    if(BeanUtil.isNotEmpty(record)){
			    UserTopic topic =	topicService.findUserTopicById(record.getTopic().getId());
			    
			    if(topic!=null && BeanUtil.isNotEmpty(topic)){
			    	ruserid = topic.getUser().getId();
			    	rusername =topic.getUser().getUsername();
		        }
		    }
			User user = getUser();
			modelAndView.addObject("buser_id", ruserid);
			modelAndView.addObject("busername", rusername);
			modelAndView.addObject("recordId", recordId);
			modelAndView.addObject("cuserId", user.getId());
			modelAndView.addObject("current_user_name", user.getUsername());
	   } catch (Exception e) {
		   logger.debug("==去浏览页面失败！==");
	   }
		modelAndView.setViewName("/record/record_browse_public");
		return modelAndView;
	}
	
	
	/**
	 * @Description: 获取相邻月数据跳过空月，上一月和下一月
	 * @author yanpeng
	 * @date 2016年3月18日 上午9:41:54
	 * @param date
	 * @paramdire -1 上个月  1 表示下一个月
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/nextMonth", method ={RequestMethod.POST, RequestMethod.GET})
	public Result getListNextMonth(String date,Integer dire) {
		Result result = new Result();
		Map<String,Object> map = new HashMap<String,Object>();;
		Date dateFromString = StringHandle.getDateFromString(date);
		try {
			if (BeanUtil.isEmpty(dateFromString)) {
				dateFromString = new Date();
			}
			User user = getUser();
			List<UserTopic> topicList = topicService.findAllUserTopicCollect(user.getId());
			List<UserTopic> mytopicList = topicService.findAllUserTopic(user.getId());
			Long[] collids = null;
			Long[] myids = null;
			if (mytopicList!=null && mytopicList.size()!=0) {
				myids = new Long[mytopicList.size()];
				for (int i = 0; i < mytopicList.size(); i++) {
					myids[i] = mytopicList.get(i).getId();
				}
			}
			if (topicList!=null && topicList.size()!=0) {
				collids = new Long[topicList.size()];
				for (int i = 0; i < topicList.size(); i++) {
					collids[i] = topicList.get(i).getId();
				}
			}
			Date nextMonth = userRecordService.getListNextMonth(dateFromString, dire, user,myids,collids);
			map.put("date",nextMonth);
			result.setStatus(Result.STATUS_OK);
			result.setData(map);
		} catch (Exception e) {
			logger.debug("==相邻月查询数据查询失败！==");
			result.setStatus(Result.STATUS_FAILED);
			if (dire==-1) {
				result.setMsg("亲，没有数据了，要想看更多就要多去记录哦");
			}else{
				result.setMsg("亲，后边没有数据了，要想看更多就要多去记录哦");
			}
		}
		return result;
	}
	
	/**
	 * @Description: 获得当前登录用户
	 * @author yanpeng
	 * @date 2016年3月26日 上午9:52:23
	 * @return
	 */
	private User getUser(){
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return userService.findUserByUsername(username);//当前登录的用户
	}
}
