package com.trgis.trmap.account.task;  
   
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserService;  
   
/** 
 * 基于注解的定时器 
 * @author chelei 
 */  

@Component("accountTask")  
public class AccountTask{  


	@Autowired
	private UserService userService;
       
    /**
     * 心跳更新。启动时执行一次，之后每隔2秒执行一次
     * @Scheduled(fixedRate = 1000*2)
     * @Scheduled(fixedRate =1000*1*60*30)  
     * 定时计算。每天凌晨 01:00 执行一次
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT) 
     * @Scheduled(cron = "0 0 1 * * * )    
     */  
	//定时计算。每天凌晨 01:00 执行一次
	@Scheduled(cron = "0 0 1 * * *" )
    public void autoCardCalculate(){
		//获取所有已注册未激活账户
		List<User> inActiveUserList=userService.findUserByStatus(UserStatus.INACTIVE);
		//如果有已注册未激活的账户则应该判断是否创建超过三天
		if(inActiveUserList.size()>0){
			try {  
			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		   Date today= df.parse(df.format(new Date()));
    		   for(User inActiveUser :inActiveUserList){
	    		   Date createDate=inActiveUser.getCreateDate();
		    	   long day=(today.getTime()-createDate.getTime())/(24*60*60*1000);
		    		//如果超过3天则进行清理
		    		if(day>3){
		    			userService.deleteUser(inActiveUser);
		    		}
	    	  }
			} catch (ParseException e) {
	    		   e.printStackTrace();
	   }
}
    	
    }  
}