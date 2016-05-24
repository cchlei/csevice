package com.trgis.trmap.system.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.util.DateUtil;
import com.trgis.trmap.system.utils.Result;
import com.trgis.trmap.userauth.model.emun.UserEntity;
import com.trgis.trmap.userauth.model.emun.UserStatus;
import com.trgis.trmap.userauth.service.UserService;

@Controller
@RequestMapping(value = "/count")
public class CountController {

	private static final Logger logger = LoggerFactory.getLogger(CountController.class);

	@Autowired
	private UserService userService;
    
	/**
	 * @author Alice
	 * 统计用户注册数量页面转发
	 * @return
	 */
	@RequestMapping(value = "/sumregister", method = RequestMethod.GET)
	public String tosumregister() {
		return "count/sumregister";
	}
	
    /**
     * @author Alice
     * 统计用户注册数量
     */
    @ResponseBody
    @RequestMapping(value = "/sumregister", method = RequestMethod.POST)
    public Result sumregister(Result result) {
		try {
			logger.debug("count sum of register begin");
			Long p[] = new Long[12];
			Long e[] = new Long[12];
			Long pi[] = new Long[12];
			Long ei[] = new Long[12];
    		result.setStatus(Result.STATUS_OK);
    		Map<String, Object> map = new HashMap<String, Object>();
    		//统计最近12个月
            String[] last12Months = new String[12];  
            Calendar begin = Calendar.getInstance();
            begin.set(Calendar.MONTH, begin.get(Calendar.MONTH)+1); 
            for(int i=0; i<12; i++){  
            	begin.set(Calendar.MONTH, begin.get(Calendar.MONTH)-1); //逐次往前推1个月 
            	begin.set(Calendar.DAY_OF_MONTH, 1);
                last12Months[11-i] = DateUtil.dateToString(begin.getTime(), "yyyy-MM");
                //计算人数
                Date endtime =DateUtil.parseDate(last12Months[11-i]+"-"+begin.getActualMaximum(Calendar.DAY_OF_MONTH), "yyyy-MM-dd");
                p[11-i] = userService.countByStatus(DateUtil.parseDate("2015-01", "yyyy-MM"), endtime, UserStatus.ACTIVE, UserEntity.PERSONAL);
                e[11-i] = userService.countByStatus(DateUtil.parseDate("2015-01", "yyyy-MM"), endtime, UserStatus.ACTIVE, UserEntity.ENTERPRISE);
                pi[11-i] = userService.countByStatus(begin.getTime(), endtime, UserStatus.ACTIVE, UserEntity.PERSONAL);
                ei[11-i] = userService.countByStatus(begin.getTime(), endtime, UserStatus.ACTIVE, UserEntity.ENTERPRISE);
            }  
            map.put("xStr", last12Months);
    		map.put("p", p);
    		map.put("e", e);
    		map.put("pi", pi);
    		map.put("ei", ei);
    		result.setData(map);
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("统计用户注册数量失败！");
		}
		return result;
    }
    
    public static void main(String[] args) {
    	Calendar end = Calendar.getInstance();
    	end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH)); 
    	System.out.println(DateUtil.dateToString(end.getTime(), "yyyy-MM-dd"));
	}
}
