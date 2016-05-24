package com.trgis.trmap.enterprise.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.trmap.enterprise.model.EntApplication;
import com.trgis.trmap.enterprise.model.EntRelUserMap;
import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.EncrUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 解密转发处理
 * 说明：
 * 本类只进行解密转发处理，如需加密，请进行本类main方法中的处理
 * http://服务器IP:端口/ucenter/mapshare/加密后字符串  格式的url
 */
@Controller
@RequestMapping(value = "/urlFilter")
public class UrlFilterController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
	private EntRelMapService relmapService;
    @Autowired
	private EnterpriseUserService entUserService;
    @Autowired
    private EntApplicationService applicationService;
    /**
     * 解密
     * 地图分享访问转发处理
     */
    @ResponseBody
    @RequestMapping(value = "/mapshare/{path}", method = RequestMethod.GET)
    public ModelAndView mapshare(@PathVariable("path") String path, HttpServletRequest request,HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView();
        try {
        	String url = URLDecoder.decode(path, "utf-8");
        	String value = EncrUtil.decoderByDES(url, EncrUtil.URLKEY);
			long time = (new Date()).getTime();//当前时间
			long temp = 0l;
			if(session.getAttribute("lastRequest") != null){
			    temp = (long) session.getAttribute("lastRequest");//上次访问时间
			}
			long msg = time - temp;	//间隔时间
			if(msg < 5000){  //5秒是控制多少秒内不能两次刷新 
				modelAndView.setViewName("map/mapservice");
			} else {
				session.setAttribute("lastRequest",time);
				Long mapId = Long.parseLong(value);
				EntRelUserMap relmap = relmapService.findRelUserMapById(mapId);
				if(relmap.getDelflag() == EnumUtil.DELFLAG.DEL.getValue()){
					modelAndView.setViewName("servicehall/mapdelete");
				}else if(relmap.getIsshare() != EnumUtil.SHARE.YFB.getValue()){
					modelAndView.setViewName("servicehall/noshare");
				}else{
					//key跟地址同时拿到才能成功访问
					//key根据用户生成
					//当前用户的订单
					modelAndView.setViewName("servicehall/nokey");
					String username = (String) SecurityUtils.getSubject().getPrincipal();
					User user = entUserService.findUserByUsername(username);
					if(username.equals(relmap.getUser().getUsername())){//发布者
						modelAndView.addObject("relmap", relmap);
						modelAndView.setViewName("servicehall/service_view");
					}else{//访客
						List<EntApplication> list = applicationService.findByGetterAndRelUserMap(user, relmap);
						for (EntApplication app : list) {
							if(app.getIsapproved() == EnumUtil.ISAPPROVED.PASS.getValue()){
								modelAndView.addObject("relmap", relmap);
								modelAndView.setViewName("servicehall/service_view");
								break;
							}
						}
					}
//					//TODO 暂时没有设置访问次数为固定值，和用户等级一起做
//					int total = 200;
//					// 组装第一条件组
//					ConditionGroup group = new ConditionGroup();
//					group.setSearchRelation(SearchRelation.AND);
//					Date today = DateUtil.getStartTime();
//					List<SearchCondition> conditions = new ArrayList<SearchCondition>();
//					conditions.add(new SearchCondition("requesttime", Operator.BETWEEN, today, DateUtil.getNextDay(today)));
//					conditions.add(new SearchCondition("userMap.id", Operator.EQ, mapId));
//					group.setConditions(conditions);
//					//获取地图创建人等级访问限制数量
//					long already = serviceRecordService.countByTodayAndId(group);
//					//与地图当日已经点击次数作比较
//			    	if(already<total){
//			    		// 统计访问次数
//			            AccessRecord serviceRecord = new AccessRecord();
//			            serviceRecord.setRequestip(request.getRemoteAddr());
//			            serviceRecord.setRequesttime(DateUtil.getStartTime());
//			            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));  
//			            serviceRecord.setRequestSource(userAgent.getBrowser().toString());
//			            serviceRecord.setUserMap(userMap);
//			            serviceRecordService.save(serviceRecord);
//			        	modelAndView.setViewName("map/mapservice");
//			    	}else{
//			    		// 超出
//			        	modelAndView.setViewName("map/over_count");
//			    	}
				}
			}
			modelAndView.addObject("mapid", value);
		} catch (NumberFormatException e) {
			// TODO 404page
			logger.debug("===地图分享链接附带的地图ID有误，无法正确查找分享地图===");
		} catch (Exception e) {
			// TODO 500page
			logger.debug("===地图分享链接有误，无法正确查找分享地图===");
		}
    	return modelAndView;
    }
    
    /**
     * 看后续是否需要加密处理，暂时不加密，需通过用户登录访问
     * 关联地图矢量访问转发处理
     *
     * @return
     * @throws IOException 
     * @throws ServletException 
     */
    @ResponseBody
    @RequestMapping(value = "/showdata/{mapid}/{featureid}", method = RequestMethod.GET)
    public ModelAndView showdata(@PathVariable("mapid") String mapid, @PathVariable("featureid") String featureid
    		) throws IOException, ServletException {
    	ModelAndView modelAndView = new ModelAndView();
        // 转发
    	modelAndView.setViewName("map/showdata");
    	modelAndView.addObject("mapId", mapid);
    	modelAndView.addObject("featureId", featureid);
    	return modelAndView;
    }
    
}