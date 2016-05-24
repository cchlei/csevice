package com.trgis.trmap.personal.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.exception.UserMessageException;
import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.model.UserTopic;
import com.trgis.trmap.personal.service.UserCommentService;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.service.UserTopicService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.vo.UserCommentVo;
import com.trgis.trmap.personal.webapp.vo.UsermessageVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

@Controller
@RequestMapping("/message")
public class UserMessageController {
	private static final Logger logger = LoggerFactory.getLogger(UserMessageController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserMessageService userMessageService;
	@Autowired
	private UserCommentService userCommentService;
	@Autowired
	private UserTopicService userTopicService;
	
	@RequestMapping(value = "/tocenter ", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toAdd(ModelAndView mv)  {
		try {
			User user = this.getUser();
			mv.addObject("current_user_id", user.getId());
			mv.addObject("current_user_name", user.getUsername());
		} catch (Exception e) {
			logger.debug("获取用户信息失败！");
		}
		
		mv.setViewName("/msg_center2");
		return mv;

	}

	/**
	 * @Description:添加消息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:07:23
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public Result createMessage(UserMessage message) {
		Result result = new Result();
		logger.debug("===添加消息！！===");
		try {
			if (!BeanUtil.isEmpty(message)) {
				userMessageService.addMessage(message);
			}
			result.setStatus(Result.STATUS_OK);
			logger.debug("===添加消息成功===");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("添加消息失败！");
			logger.debug("===添加消息失败！===");
		}

		return result;
	}

	/**
	 * @Description:修改消息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:11:40
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object> updateMessage(@RequestParam("id") Long id,@RequestParam("readflag") Integer readflag) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserMessage message = null;
		try {
			if(BeanUtil.isNotEmpty(id)){//修该阅读状态
			 message =  userMessageService.findMessageById(id);
			 if(message.getReadflag()!=1 && readflag==0){
				 message.setReadflag(EnumUtil.READ.READ.getValue());
			     userMessageService.updateMessage(message);}
			}
			result.put("status", Result.STATUS_OK);
			result.put("msg", " 修改成功！");
		} catch (Exception e) {
			result.put("status",Result.STATUS_FAILED);
			result.put("msg", "修改失敗！");
		}
		return result;
	}

	/**
	 * @Description:删除消息
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:11:52
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> deleteMessage(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (BeanUtil.isNotEmpty(id)) {
				userMessageService.deleteMessage(id);
				map.put("status", Result.STATUS_OK);
				map.put("msg", " 删除成功！");
				logger.debug("删除完毕！");
			}
		} catch (UserMessageException e) {
			logger.debug("删除失败！");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @Description:获取消息的列表
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:18:20
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/messageList", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> getMessageList(@RequestParam("readflag") Integer readflag,
			@RequestParam("rows") Integer rows, @RequestParam("page") Integer page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UsermessageVo> listvoo = new ArrayList<UsermessageVo>();
		long  totalCount = 0;
		try {
			ConditionGroup group = new ConditionGroup();
			group.setSearchRelation(SearchRelation.AND);
			String pageNoStr = request.getParameter("page");
			int pageNo = pageNoStr == null ? 0 : Integer.parseInt(pageNoStr);
			String rowsStr = request.getParameter("rows");
			int pageSize = pageNoStr == null ? 24 : Integer.parseInt(rowsStr);
			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			// 分享状态
			if (BeanUtil.isNotEmpty(readflag) && (readflag == 1 || readflag == 0)) {// 已读未读状态
				conditions.add(new SearchCondition("readflag", Operator.EQ, readflag));
			}
			// 删除状态
			conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
			// 用户
			conditions.add(new SearchCondition("user", Operator.EQ, this.getUser()));
			group.setConditions(conditions);
			String timeindex = request.getParameter("timeindex");// 时间排序
			OrderBy order = timeindex == null ? new OrderBy("occurtime", "desc") : new OrderBy("occurtime", timeindex);
			Page<UserMessage> pages = userMessageService.findByConditions(group, pageNo, pageSize, order);
			List<UserMessage> list = null;
			UsermessageVo usermessageVo = null;
			// 返回数据封装
			if (null != pages && org.apache.commons.collections.CollectionUtils.isNotEmpty(pages.getContent())) {
				list = pages.getContent();
				logger.debug("消息列表初级查询成功！");
				totalCount = pages.getTotalElements();
				// 重新封装vo
				for (UserMessage message : list) {
					usermessageVo = gzuserMessageVo(message);
					listvoo.add(usermessageVo);
					logger.debug("该消息的所有信息已获取完毕！");
				}

			}
			map.put("rows", listvoo);
			map.put("totalCount",totalCount);
		} catch (Exception e) {
			logger.debug("列表查询失败！");
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * @Description:根据阅读状态消息数据统计
	 * @Author liuyan
	 * @Date 2016年3月26日 下午6:10:53
	 * @param readflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/count", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> getMessageCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = getUser();
			if (BeanUtil.isEmpty(user)) {
				throw new UserMessageException("用户为空");
			}
			Long countTopicYyd = userMessageService.getReadCount(user, EnumUtil.READ.READ.getValue());
			Long countTopicWyd = userMessageService.getReadCount(user, EnumUtil.READ.NOMAL.getValue());
			Long countTopicAll = countTopicYyd +countTopicWyd;
			map.put("all", countTopicAll);
			map.put("wyd", countTopicWyd);
			map.put("yyd", countTopicYyd);
			logger.debug("消息统计数据完成！");
		} catch (Exception e) {
			logger.debug("消息统计数据失败！");
		}
		return map;
	}

	/**
	 * @Description:获得当前登录用户
	 * @Author liuyan
	 * @Date 2016年3月26日 下午5:14:40
	 * @return
	 */
	private User getUser() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return userService.findUserByUsername(username);// 当前登录的用户
	}

	
	/**
	 * @Description:构造评论表vo的方法,没有封装childlist
	 * @Author liuyan 
	 * @Date 2016年4月5日 下午2:33:57
	 * @param comment评论
	 * @return评论vo
	 */
		private UserCommentVo gzUserCommentVo(UserComment comment) {
			UserCommentVo pre = new UserCommentVo();
			try{
			if(!BeanUtil.isEmpty(comment)){
				if (null != comment.getId() && BeanUtil.isNotEmpty(comment.getId())) {
					pre.setId(comment.getId());
				}
				if (null != comment.getBuser() && BeanUtil.isNotEmpty(comment.getBuser())) {
					pre.setBuser_id(comment.getBuser().getId());
					pre.setBusername(comment.getBuser().getUsername());
					pre.setBuser_headimg(comment.getBuser().getHeadimg());
				}
				if (null != comment.getCuser() && BeanUtil.isNotEmpty(comment.getCuser())) {
					pre.setCuser_id(comment.getCuser().getId());
					pre.setCusername(comment.getCuser().getUsername());
					pre.setCuser_headimg(comment.getCuser().getHeadimg());
				}
				if (null != comment.getComtime() && BeanUtil.isNotEmpty(comment.getComtime())) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String str = format.format(comment.getComtime());
					pre.setComtime(str);// 时间
				}
				if (null != comment.getComcontent() && BeanUtil.isNotEmpty(comment.getComcontent())) {
					pre.setComcontent(comment.getComcontent());// 内容
				}
				if (null != comment.getUserComment() && BeanUtil.isNotEmpty(comment.getUserComment())) {
					pre.setParent_id(comment.getUserComment().getId());// 父级id
				}
				
				if (null != comment.getUserRecord() && BeanUtil.isNotEmpty(comment.getUserRecord())) {
				    UserRecord record =	comment.getUserRecord();
					pre.setUser_rid(record.getId());// 记录id
					pre.setTitle(record.getTitle());// 记录标题
					pre.setDescription(record.getDescription());// 记录描述
					if(record.getId()!=null&& BeanUtil.isNotEmpty(record.getId())){
						UserTopic topic =userTopicService.findUserTopicById(record.getTopic().getId());
						if(topic!=null && BeanUtil.isNotEmpty(topic)){
							pre.setRec_user_id(topic.getUser().getId());//写记录的人的id
					    }
				    }
				  }
		    	}
			} catch (Exception e) {
				logger.debug("UserCommentVo封装数据失败！");
			}
			return pre;
		}
		
		/**
		 * @Description:构造messagevO
		 * @Author liuyan 
		 * @Date 2016年4月7日 下午7:40:15
		 * @param message
		 * @param cvolist
		 * @return
		 */
		private  UsermessageVo gzuserMessageVo(UserMessage message){
			UsermessageVo usermessageVo  = new UsermessageVo();
			if(!BeanUtil.isEmpty(message)){
				usermessageVo.setId(message.getId());
				usermessageVo.setMessageTitle(message.getMessageTitle());
				usermessageVo.setMessageContent(message.getMessageContent());
				usermessageVo.setMsgType(message.getMsgType());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strdate = format.format(message.getOccurtime());
				usermessageVo.setOccurtime(strdate);
				usermessageVo.setReadflag(message.getReadflag());
				if(message.getUserComment()!=null && !BeanUtil.isEmpty(message.getUserComment())){
					UserComment comment = userCommentService.findComment(message.getUserComment());
					usermessageVo.setComment(gzUserCommentVo(comment));
				}
				if(message.getUser()!=null && !BeanUtil.isEmpty(message.getUser())){
					usermessageVo.setUserId(message.getUser().getId());
					usermessageVo.setUsername(message.getUser().getUsername());
				}
			}
			return usermessageVo;
			
		}
}
