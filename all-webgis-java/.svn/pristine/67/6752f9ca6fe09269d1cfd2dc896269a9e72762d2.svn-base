package com.trgis.trmap.enterprise.web.controller;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 定时器操作
 * @author Alice
 *
 * 2016年1月7日
 */
@Component
public class EntDataQuartzTask {
	
	private static final Logger logger = LoggerFactory.getLogger(EntDataQuartzTask.class);
	@Autowired
	private EntMapService entMapService;
	@Autowired
	private EntApplicationService applicationService;
	@Autowired
	private EntRelMapService relmapService;
	@Autowired
	private EnterpriseUserService entUserService;
	
	/**
	 * 每天检查是否有订单及服务过期
	 */
	@Scheduled(cron="0 0 3 * * ?") //每天3点0分触发
	public void dayRun() {
		List<EntApplication> applications =  applicationService.findByPass(EnumUtil.ISAPPROVED.PASS.getValue());
		Calendar c = Calendar.getInstance();
		try {
			for (EntApplication application : applications) {
				c.setTime(application.getApplytime());
				c.add(Calendar.MONTH, application.getApplymonth());
				if(c.getTime().getTime() < new Date().getTime()){ //服务时间超出当前时间
					entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+application.getRelUserMap().getMapname()+"已过期，因此您可能暂时无法使用该服务了，有问题请联系该企业！");
					applicationService.updatePassById(EnumUtil.ISAPPROVED.OUTDATE.getValue(), "", application.getId());
				}
			}
			logger.debug("===每日定时检查过期订单及服务===");
			List<EntRelUserMap> relmaps = relmapService.findNoOutdate(EnumUtil.DELFLAG.NOMAL.getValue(), EnumUtil.ONLINE.SX.getValue());
			for (EntRelUserMap relmap : relmaps) {
				if(relmap.getValiditytime().getTime() < new Date().getTime()){ //服务时间超出当前时间
					//如果之前有正常使用中的订单，过期自动线下线
					if(BeanUtil.isNotEmpty(relmap.getPassids())){
						List<String> ids = java.util.Arrays.asList(relmap.getPassids().split(","));
						for (String string : ids) {
							Long id = Long.parseLong(string);
							EntApplication application = applicationService.findById(id);
							entUserService.sendNoticeEmail(application.getGetter(), "您使用的服务"+relmap.getMapname()+"已过期，因此您可能暂时无法使用该服务了，有问题请联系该企业！");
							applicationService.updatePassById(EnumUtil.ISAPPROVED.OUTDATE.getValue(), "", id);
						}
					}
					//修改所有异常订单原因为下线
					applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，你可以在服务大厅选择其他服务申请。", relmap, EnumUtil.ISAPPROVED.EXCEP.getValue());
					//待审核列表中的订单变为异常状态
					applicationService.updateExcepByRelUserMap(EnumUtil.ISAPPROVED.EXCEP.getValue(), "您申请的服务已下线，你可以在服务大厅选择其他服务申请。", relmap, EnumUtil.ISAPPROVED.UNTREATED.getValue());
					relmap.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
					relmap.setPassids("");
					relmap.setUntreatedids("");
					relmapService.editRelUserMap(relmap);//deleteEntRelUserMap(mapid);
					EntUserMap usermap = entMapService.findUserMapById(relmap.getId());
					usermap.setIsshare(EnumUtil.SHARE.WFB.getValue());
					entMapService.createEnterpriseMap(usermap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("===每日定时清除过期订单及服务失败===");
		}
	}
//    e.g.
//	@Scheduled(cron="0/5 * * * * ? ") //间隔5秒执行  (测试用)
//    "0 0 10,14,16 * * ?" 每天上午10点，下午2点，4点
//    "0 0/30 9-17 * * ?"   朝九晚五工作时间内每半小时
//    "0 0 12 ? * WED" 表示每个星期三中午12点 
//    "0 0 12 * * ?" 每天中午12点触发 
//    "0 15 10 ? * *" 每天上午10:15触发 
//    "0 15 10 * * ?" 每天上午10:15触发 
//    "0 15 10 * * ? *" 每天上午10:15触发 
//    "0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
//    "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
//    "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
//    "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
//    "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
//    "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
//    "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
//    "0 15 10 15 * ?" 每月15日上午10:15触发 
//    "0 15 10 L * ?" 每月最后一日的上午10:15触发 
//    "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
//    "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
//    "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
}
