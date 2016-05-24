package com.trgis.trmap.personal;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.exception.UserCommentException;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserComment;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.service.UserCommentService;
import com.trgis.trmap.personal.service.UserRecordService;
import com.trgis.trmap.userauth.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserCommentService {
	@Autowired
	private UserCommentService userCommentService;
	@Autowired
	private UserRecordService userRecordService;
	@Autowired
	private UserAttachService userAttachService;

	
	@Test
	public void testGetCollectCount() throws UserAttachException{
		try {
			UserRecord userRecord = userRecordService.findUserRecordById((long) 16680);
			List<UserComment> userCommentsByRecord = userCommentService.getUserCommentsByRecord(userRecord.getId(),null);
			System.out.println(userCommentsByRecord.size());
		}catch (UserRecordException e) {
			e.printStackTrace();
		} catch (UserCommentException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete(){
		try {
			userCommentService.deleteUserCommentsById(16733L);
			System.out.println("success");
		}catch (UserCommentException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSave(){
		try {
			User user = new User();
			UserRecord userrecord =new UserRecord();
			userrecord.setId(16680l);
			user.setId(8l);
			UserComment userComment = new UserComment();
			userComment.setComcontent("ggffff");
			userComment.setCuser(user);
			userComment.setUserRecord(userrecord);
			userCommentService.saveUserComment(userComment);
			System.out.println("success");
		}catch (UserCommentException e) {
			e.printStackTrace();
		}
	}
}
