package com.trgis.trmap.enterprise.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.enterprise.service.EntApplicationService;
import com.trgis.trmap.enterprise.service.EntRelMapService;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 服务信息控制器
 * @author mm
 * @date 2016-01-28
 *
 */
@Controller
@RequestMapping(value = "/entamount")
public class EntAmountController {
	
	private static final Logger logger = LoggerFactory.getLogger(EntCainfoController.class);
	
	@Autowired
	private EntApplicationService appservice;
	
	@Autowired
	private EntRelMapService relmapservice;
	
	@Autowired
	private EnterpriseUserService entUserService;
	
	@ResponseBody
	@RequestMapping("/getamount")
	public Map<String, Object> getAmount() {
		logger.debug("dogetamount....");
		Map<String, Object> map = new HashMap<String, Object>();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = entUserService.findUserByUsername(username);
		Long approval = appservice.finduntreated(user.getId(), EnumUtil.ISAPPROVED.UNTREATED.getValue());
		Long obtain = appservice.findpass(user.getId(),EnumUtil.ISAPPROVED.PASS.getValue());
		Long publish = relmapservice.findcount(EnumUtil.DELFLAG.NOMAL.getValue(),user.getId());
		map.put("approval", convert(approval));
		map.put("obtain",convert(obtain));
		map.put("publish",convert(publish));
		//TODO(计算资源)
		map.put("rent", convert(8888l));
		return map;
	}
	
	/**
	 * 将资源数量以123,456,789这种方式显示（亿量级）
	 * @param num
	 * @return
	 */
	public String convert(Long num) {
		long n = num/1000;
		long m = num/1000000;
		long mm = 4512121212120l;
		String s = String.valueOf(num);
		StringBuilder sb = new StringBuilder(s);
		String str = sb.toString();
		if(n > 0 && n < 1000) {
			sb.insert(str.length() - 3, ",");
		}else if(m > 0 && m < 1000) {
			sb.insert(str.length() - 3, ",");
			sb.insert(str.length() - 6, ",");
		}
		return sb.toString();
	}
	
}
