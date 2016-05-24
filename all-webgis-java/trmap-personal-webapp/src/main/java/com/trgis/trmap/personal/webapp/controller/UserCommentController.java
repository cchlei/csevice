package com.trgis.trmap.personal.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.personal.exception.UserCommentException;
import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserCommentService;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.personal.webapp.utils.BeanUtil;
import com.trgis.trmap.personal.webapp.utils.Result;
import com.trgis.trmap.personal.webapp.vo.UserCommentVo;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;

/**
 * @ClassName: UserCommentController
 * @Description: 评论控制器
 * @author yanpeng
 * @date 2016年3月10日 下午2:20:20
 */
@Controller
@RequestMapping("/comment")
public class UserCommentController {
	private static final Logger logger = LoggerFactory.getLogger(AttachController.class);

	@Autowired
	private UserCommentService userCommentService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRecordService userRecordService;
	@Autowired
	private UserMessageService userMessageService;

	/**
	 * @Description: 添加评论
	 * @author yanpeng
	 * @date 2016年3月10日 下午2:24:15
	 * @param userComment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> saveComment(String comcontent,Long rid,Long buser_id,Long cuser_id,Long parent_id,Long ruserid) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserComment userComment = new UserComment();
		UserMessage message = new UserMessage();
		UserComment ccomment = null;
		User cuser =  null;
		User buser = null; 
		try {
			if(!BeanUtil.isEmpty(comcontent) && comcontent!=null){
				userComment.setComcontent(comcontent);
			}
			if(!BeanUtil.isEmpty(rid) && rid!=null){
			    UserRecord record =	userRecordService.findUserRecordById(rid);
				userComment.setUserRecord(record);
			}
			if(!BeanUtil.isEmpty(buser_id)  && buser_id!=null){
		    	buser = userService.findUser(buser_id);
				userComment.setBuser(buser);
			}
			if(!BeanUtil.isEmpty(cuser_id)  && cuser_id!=null){
				cuser = userService.findUser(cuser_id);
				message.setMessageContent(cuser.getUsername()+"评论了您！");
				userComment.setCuser(cuser);
			}
			if(!BeanUtil.isEmpty(parent_id) && parent_id!=null){
				UserComment comment = userCommentService.findComment(parent_id);
				userComment.setUserComment(comment);;
			}
			if(!BeanUtil.isEmpty(ruserid) && ruserid!=null){
				 User user = null;
				 if(BeanUtil.isNotEmpty(buser_id)){
					 user = userService.findUser(buser_id);//如果被评论者存在就发送消息给被评论者
				  }else{
					 user = userService.findUser(ruserid);//如果被评论者不存在就给写记录的人发送消息
				  }
				 message.setUser(user);
			}
			ccomment =	userCommentService.saveUserComment(userComment);
			message.setMsgType(EnumUtil.MESSAGE_TYPE.COMMENT.getValue());
			message.setMessageTitle("您有新的评论！");
			if(!BeanUtil.isEmpty(ccomment)){
				message.setUserComment(ccomment.getId());
			}
			result.put("status", Result.STATUS_OK);
			result.put("msg", "留言添加成功！");
			userMessageService.addMessage(message);
			//userMessageService.addMessage(message);
			logger.debug("==发送消息成功！==");
		} catch (Exception e) {
			logger.debug("==留言添加失败！==");
			result.put("status", Result.STATUS_FAILED);
			result.put("msg", "留言添加失败！");
		}
		result.put("commentId",ccomment.getId());
		return result;
	}

	/**
	 * @Description: 删除评论
	 * @author yanpeng
	 * @date 2016年3月10日 下午2:31:22
	 * @param commentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST, RequestMethod.GET })
	public Result deleteComment(Long id) {
		Result result = new Result();
		try {
			//删除消息表中的评论外键
			 userMessageService.updateMeg(id);
		    //查询的当前评论有无子节点
		    Boolean boo =  userCommentService.findchild(id);
		    if(boo){
		    	userCommentService.deleteUserCommentsBypid(id);
			    userCommentService.deleteUserCommentsById(id);
		    }else{
		    	userCommentService.deleteUserCommentsById(id);
		    }
		    result.setStatus(Result.STATUS_OK);
			result.setMsg("留言删除成功！");
		} catch (Exception e) {
			logger.debug("==留言删除失败！==");
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("留言删除失败！");
		}
		
		return result;
	}

	/**
	 * @Description:获取评论列表
	 * @Author liuyan
	 * @Date 2016年3月28日 上午11:31:52
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> getCommentList(@PathVariable("id") Long rid) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserCommentVo> list = new ArrayList<UserCommentVo>();
		try {
			// 获取第一级评论即parent_id=null
			List<UserComment> prelist = userCommentService.getUserCommentsByRecord(rid, null);
			if (prelist != null && !prelist.isEmpty()) {
				for (UserComment comment : prelist) {// 循环获取第二级
					// 构造vo 包装第一级
					UserCommentVo pre = gzUserCommentVo(comment);
					List<UserCommentVo> sonvolist = new ArrayList<UserCommentVo>();
					List<UserComment> sonlist = userCommentService.getUserCommentsByRecord(rid, comment.getId());
					if (null != sonlist && sonlist.size() > 0) {
						for (int i = 0; i < sonlist.size(); i++) {
							UserCommentVo son = gzUserCommentVo(sonlist.get(i));// 循环构造子评论vo
							sonvolist.add(i, son);
						}
						pre.setChild(sonvolist);// 把子列表放进去
					}
					list.add(pre);// 将一个大评论放进结果集
				}
			}
		} catch (UserCommentException e) {
			e.printStackTrace();
		}
		result.put("rows", list);
		return result;

	}
/**
 * @Description:构造评论表vo的方法,没有封装childlist
 * @Author liuyan 
 * @Date 2016年3月29日 下午2:33:57
 * @param comment评论
 * @return评论vo
 */
	private UserCommentVo gzUserCommentVo(UserComment comment) {
		UserCommentVo pre = new UserCommentVo();
		Long id = comment.getId();
		User buser = comment.getBuser();
		User cuser = comment.getCuser();
		Date ctime = comment.getComtime();
		String content = comment.getComcontent();
		UserComment userComment = comment.getUserComment();
		Long rid = comment.getUserRecord().getId();
		
		if (null != id && BeanUtil.isNotEmpty(id)) {
			pre.setId(id);
		}
		if (null != buser && BeanUtil.isNotEmpty(buser)) {
			pre.setBuser_id(buser.getId());
			pre.setBuser_headimg(buser.getHeadimg());
			pre.setBusername(buser.getUsername());
		}
		
		if (null != cuser && BeanUtil.isNotEmpty(cuser)) {
			pre.setCuser_id(cuser.getId());
			pre.setCuser_headimg(cuser.getHeadimg());
			pre.setCusername(cuser.getUsername());
		}
		if (null != ctime && BeanUtil.isNotEmpty(ctime)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = format.format(ctime);
			pre.setComtime(str);// 时间
		}
		if (null != content && BeanUtil.isNotEmpty(content)) {
			pre.setComcontent(content);// 内容
		}
		if (null != userComment && BeanUtil.isNotEmpty(userComment)) {
			pre.setParent_id(userComment.getId());// 父级id
		}
		if (null != rid && BeanUtil.isNotEmpty(rid)) {
			pre.setUser_rid(rid);// 记录id
		}
		return pre;
	}
}
