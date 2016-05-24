package com.trgis.trmap.personal.service;

import java.util.List;

import com.trgis.trmap.personal.exception.UserCommentException;
import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.personal.model.UserRecord;

/**
 * 评论服务类
 * @author yanpeng
 *
 */
public interface UserCommentService {
	
	/**
	 * 根据事件id查询对应的评论
	 * @param id 事件id
	 * @return
	 */
	public List<UserComment> getUserCommentsByRecord(Long rid,Long pid) throws UserCommentException;
	
	/**
	 * 统计留言数
	 * @param userRecord
	 * @return
	 * @throws UserCommentException
	 */
	public Long countByRecord(UserRecord userRecord) throws UserCommentException ;
	
	/**
	 * 根据评论id删除评论
	 * @param id
	 */
	public void deleteUserCommentsById(Long id) throws UserCommentException ;
	
	/**
	 * 添加评论
	 * @param userComment
	 * @return 
	 */
	public UserComment saveUserComment(UserComment userComment) throws UserCommentException;
	  /**
	 * @Description:根据id查找评论
	 * @Author liuyan 
	 * @Date 2016年4月7日 下午1:52:01
	 * @param id
	 */
	public UserComment findComment(Long id);
/**
 * @Description:删除子集评论
 * @Author liuyan 
 * @Date 2016年4月7日 下午5:35:26
 * @param id
 * @throws UserCommentException 
 */
	public void deleteUserCommentsBypid(Long id) throws UserCommentException;
	/**
	 * @Description:判断当前评论有无子节点
	 * @Author liuyan 
	 * @Date 2016年4月7日 下午6:49:45
	 * @param id
	 * @return
	 */
    public Boolean findchild(Long id);
}
