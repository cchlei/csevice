package com.trgis.trmap.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.enterprise.model.EntCainfo;
import com.trgis.trmap.enterprise.service.EntCainfoService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.system.utils.JSONPage;
import com.trgis.trmap.system.utils.Result;
import com.trgis.trmap.userauth.model.User;
/**
 * 企业认证信息管理
 * 
 * 1、认证成功后生成token
 * 2、认证的审核功能（认证成功、认证失败）
 * 
 * @author Alice
 *
 * 2016年4月13日
 */
@Controller
@RequestMapping(value = "/entcainfo")
public class EnterpriseCainfoController {

	private static final Logger logger = LoggerFactory.getLogger(EnterpriseCainfoController.class);

	@Autowired
	private EntCainfoService cainfoService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String redirectList() {
		return "user/entcainfolist";
	}

    /**
     * @author Alice
     * 企业认证信息分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONPage getList(int rows,int page,String searchVal) {
		logger.debug("分页查询企业认证信息");
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
 		cgRoot.getConditions().add(new SearchCondition("castatus", Operator.NLIKE, EnumUtil.CASTATUS.CANCELED.getValue()));
    	//查询条件
    	String[] search= null;
    	if(!StringUtils.isEmpty(searchVal)){
			search = searchVal.split(":");//search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			if ("all".equals(search[1])) {
				 String [] types = {"enterpriseName","address","bossName","managerPhone"};
				 for (int i = 0; i < types.length; i++) {
					 searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
				 }
			 }else{
				 searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
			 }
			 filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			 cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
    	}
    	OrderBy order1 = new OrderBy("castatus", "asc");
    	OrderBy order2 = new OrderBy("id", "desc");
		Page<EntCainfo> cainfoPage = cainfoService.findByConditions(cgRoot, page, rows, order1, order2);

		JSONPage json = new JSONPage();
        List<EntCainfo> cainfos = cainfoPage==null?new ArrayList<EntCainfo>():cainfoPage.getContent();
        for (EntCainfo c : cainfos) {
        	//详情
        	String entname = "<a href='javascript:void(0);' onclick='view("+c.getId()+");'>"+c.getEnterpriseName()+"</a>";
        	c.setEntname(entname);
        	//操作按钮
        	String operation = "";
        	if(c.getCastatus().equals(EnumUtil.CASTATUS.AUDITWAIT.getValue()))
        		operation += "<a class='viewInfo' href='javascript:void(0);' onclick='pass("+c.getId()+");'>通过</a>"
        				+ "<a class='viewInfo' href='javascript:void(0);' onclick='fail("+c.getId()+");'>驳回</a>";
        	if(c.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue()))
        		operation += "<a class='viewInfo' href='javascript:void(0);' onclick='openEdit("+c.getId()+");'>修改</a>";
			c.setOperation(operation);
			//关联账户
			String account = "<a href='javascript:void(0);' onclick='account("+c.getId()+");'>"+c.getUser().getUsername()+"</a>";
			c.setAccount(account);
        	
		}
        json.setRows(cainfos);
        json.setTotal(cainfoPage==null?0:(int)cainfoPage.getTotalElements());
		return json;
    }
    
    /**
     * @author Alice
     * 审核企业认证
     */
    @ResponseBody
    @RequestMapping(value = "/audit/{status}/{id}", method = RequestMethod.POST)
    public Result audit(@PathVariable Integer status, @PathVariable Long id, String remarks, Result result) {
		try {
			EntCainfo cainfo = cainfoService.findById(id);
			if(cainfo != null){
				cainfo.setCastatus(status==0?EnumUtil.CASTATUS.AUDITED.getValue():EnumUtil.CASTATUS.AUDITFAIL.getValue());
				cainfo.setRemarks(status==0?"":remarks);
				if(cainfo.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue())){
					String s = UUID.randomUUID().toString();
					cainfo.setSecretKey(s.replaceAll("-", ""));//加密私盐
				}
				cainfoService.edit(cainfo);
				result.setStatus(Result.STATUS_OK);
    		}else{
    			result.setStatus(Result.STATUS_FAILED);
    			result.setMsg("审核企业认证操作失败！");
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("审核企业认证操作失败！");
		}
		return result;
    }
    
    /**
     * @author Alice
     * 浏览详情
     */
    @ResponseBody
    @RequestMapping(value = "/toView/{id}", method = RequestMethod.POST)
    public EntCainfo toView(@PathVariable Long id,Result result) {
    	EntCainfo cainfo = cainfoService.findById(id);
		return cainfo;
    }
    
    /**
     * @author Alice
     * 关联账户
     */
    @ResponseBody
    @RequestMapping(value = "/account/{id}", method = RequestMethod.POST)
    public User account(@PathVariable Long id,Result result) {
    	EntCainfo cainfo = cainfoService.findById(id);
		return cainfo.getUser();
    }
    
	/**
	 * 修改前校验
	 * @param type
	 * @param value
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "/validation/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result validation(@PathVariable Long id, @RequestParam String value, Result result) {
		logger.debug("EnterpriseName validation.....");
		EntCainfo cainfo = null;
		EntCainfo entcainfo = cainfoService.findById(id);
		//修改认证信息时，若企业名未改动则无须校验重复性(修改信息和提交信息都会进行重复性校验)
		if(BeanUtil.isEmpty(entcainfo)) {//表示在提交信息
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("企业认证信息未找到！");
		}else if(value.equals(entcainfo.getEnterpriseName())){//表示修改时企业名称未改动
			result.setStatus(Result.STATUS_OK);
		}else {//表示修改时企业名称已改动
			cainfo = cainfoService.findByEnterpriseName(value);
			if (cainfo != null) {
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("企业名称已被注册！");
			} else {
				result.setStatus(Result.STATUS_OK);
			}
		}
		return result;
	}
	
	/**
	 * 修改前校验
	 * @param type
	 * @param value
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result modify(@PathVariable Long id, EntCainfo entcainfo, Result result) {
		logger.debug("EnterpriseName validation.....");
		EntCainfo old = cainfoService.findById(id);
		try {
			if(old!=null && old.getCastatus().equals(EnumUtil.CASTATUS.AUDITED.getValue())){
				entcainfo.setCastatus(EnumUtil.CASTATUS.AUDITED.getValue());
				entcainfo.setCertificateDate(old.getCertificateDate());
				entcainfo.setUtilValidDate(old.getUtilValidDate());
				entcainfo.setUser(old.getUser());
				entcainfo.setOrgAttachUrl(old.getOrgAttachUrl());
				entcainfo.setOrgId(old.getOrgId());
				cainfoService.edit(entcainfo);
				result.setStatus(Result.STATUS_OK);
			}else{
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("只有认证通过的才能被修改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("修改企业认证信息失败！");
		}
		return result;
	}
}
