package com.trgis.trmap.personal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.personal.exception.UserMessageException;
import com.trgis.trmap.personal.model.UserMessage;
import com.trgis.trmap.personal.service.UserMessageService;
import com.trgis.trmap.personal.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
/**
 * 
 * @Title: TestUserMessageService
 * @Description: 消息测试类
 * @author liuyan
 * @date 2016年3月26日 下午6:45:54
 *
 */

@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class TestUserMessageService {
	@Autowired
	private UserMessageService userMessageService;
	
	/**---通过测试
	 * @Description:添加消息
	 * @Author liuyan 
	 * @Date 2016年3月26日 下午6:46:35
	 */
	@Test
	public void testaddMessage(){
		try {
			UserMessage message = new UserMessage();
			User user = new User();
			user.setId(8l);
			message.setMessageTitle("给刘艳发送个消息哦");
			message.setMessageContent("给刘艳发送个消息给刘艳发送个消息给刘艳发送个消息给刘艳发送个消息给刘艳发送个消息");
			message.setMsgType(1);
			message.setUser(user);
			message.setOccurtime(new Date());
			userMessageService.addMessage(message);
		} catch (UserMessageException e) {
			e.printStackTrace();
		}
	}
	/**---通过测试
	 * @Description:删除消息--假删除
	 * @Author liuyan 
	 * @Date 2016年3月26日 下午6:53:58
	 */
	@Test
	public void testdeleteMessage(){
		try {
			//测试findMessageById()
		    UserMessage massage = userMessageService.findMessageById(16728l);
		    System.out.println(massage.getId());
			userMessageService.deleteMessage(massage.getId());
			
		} catch (Exception e) {
		}
		
	}
	/**---通过测试
	 * @Description:修改
	 * @Author liuyan 
	 * @Date 2016年3月26日 下午6:54:08
	 */
	@Test
	public void testupdateMessage(){
		try {
			 UserMessage message = userMessageService.findMessageById(16727l);
			message.setMessageTitle("给刘艳发送个消息哦,怎么样呀");
			message.setOccurtime(new Date());
			message.setReadflag(EnumUtil.READ.NOMAL.getValue());
			userMessageService.updateMessage(message);
		} catch (Exception e) {
		}
		
	}
   /**
    * @Description:根据条件查询，---暂时不要
    * @Author liuyan 
    * @Date 2016年3月26日 下午6:54:19
    */
	@Test
	public void findMessageByUser(){
		//TODO
	}
	
	/**---通过测试
	 * @Description:根据条件查询
	 * @Author liuyan 
	 * @Date 2016年3月28日 上午10:00:36
	 */
	@Test
	public void testfindByConditions(){
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		conditions.add(new SearchCondition("user", Operator.EQ, 8l));
		conditions.add(new SearchCondition("delflag", Operator.EQ, EnumUtil.DELFLAG.NOMAL.getValue()));
		group.setConditions(conditions);
		int pageNum = 0;
		int pageSize = 8;
		OrderBy order = new OrderBy("occurtime", "desc");
		Page<UserMessage> findByConditions = userMessageService.findByConditions(group, pageNum, pageSize, order);
		List<UserMessage> list = findByConditions.getContent();
		System.err.println(list.get(1).getId()+"++++++"+list.size());

	}
	/**---通过测试
	 * @Description:获取统计数
	 *  阅读标志
	 * NOMAL(0, "未阅读"), READ(1, "已阅读"),ALL(-1,"所有")
	 * @Author liuyan 
	 * @Date 2016年3月28日 上午9:56:11
	 */
	@Test
	public void testGetReadCount(){
		try {
			User user = new User();
			user.setId(8l);
			System.err.println(userMessageService.getReadCount(user,EnumUtil.READ.NOMAL.getValue()));
		}catch (UserMessageException e) {
			e.printStackTrace();
		}
	}
	
   
}
