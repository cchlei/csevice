package com.trgis.trmap.qtuser.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.trmap.qtuser.model.Suggestion;
import com.trgis.trmap.qtuser.service.SuggestionService;
import com.trgis.trmap.qtuser.utils.BeanUtil;
import com.trgis.trmap.qtuser.utils.StringHandle;

/**
 * 意见建议控制器
 * 
 * @author Alice
 *
 */
@Controller
@RequestMapping("/suggestion")
public class SuggestionController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	@Autowired
	private SuggestionService suggestionService;
	/**
	 * 返回到客户端的json格式
	 */
	private static final String RESULT_JSON = "{\"result\":\"%s\",\"msg\":\"%s\"}";

	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	/**
	 * 新增意见建议
	 */
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String saveSuggestion(@RequestParam String content){
		try {
			Suggestion suggestion = new Suggestion();
			suggestion.setContent(content);
			suggestionService.saveSuggestion(suggestion);
			logger.debug("新增意见建议成功");
			return String.format(RESULT_JSON, SUCCESS, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(String.format("%s意见建议新增失败", content));
			return String.format(RESULT_JSON, ERROR, "意见建议新增失败");
		}
	}
	/**
	 * 分页列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String,Object> userMapList(HttpServletRequest request,HttpSession session) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	//查询条件
    	ConditionGroup group = new ConditionGroup();
    	group.setSearchRelation(SearchRelation.AND);
    	
    	List<SearchCondition> conditions = new ArrayList<SearchCondition>();
    	// 关键字
    	String searchText = request.getParameter("searchText");//搜索
    	if(BeanUtil.isNotEmpty(searchText)){
    		searchText = searchText ==null?"":StringHandle.HtmltoText(searchText).replaceAll("\"", "").replace("'", "");
    		conditions.add(new SearchCondition("content", Operator.LIKE, searchText));
    	}
    	group.setConditions(conditions);
    	String pageNoStr = request.getParameter("page");
    	int pageNo = pageNoStr==null?0:Integer.parseInt(pageNoStr);
    	String rowsStr = request.getParameter("rows");
        int pageSize = pageNoStr==null?24:Integer.parseInt(rowsStr);
		//排序
        OrderBy order = new OrderBy("createtime", "desc");
		Page<Suggestion> page = suggestionService.findByConditions(group, pageNo, pageSize, order);//.findByConditions(group, 0, 10, order);
        map.put("rows", page.getContent());
        map.put("totalCount", page.getTotalElements());
        return map;
    }

	/**
	 * 删除单个
	 * 
	 * @param fileId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteOne", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteOne(@RequestParam("suggestionid") Long suggestionid) {
		try {
			suggestionService.deleteSuggestion(suggestionid);
			logger.debug("===删除意见建议成功！===");
			return String.format(RESULT_JSON, SUCCESS, "删除意见建议成功");
		} catch (Exception e) {
			logger.debug("===删除意见建议失败！===");
			return String.format(RESULT_JSON, ERROR, "删除意见建议失败");
		}
	}
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteMulti(@RequestParam("ids") String ids) {
		try {
			if (StringUtils.isBlank(ids)) {
				String[] idstr = ids.split(",");
				for (String s : idstr) {
					if (StringUtils.isBlank(s))
						suggestionService.deleteSuggestion(Long.parseLong(s));
				}
				return String.format(RESULT_JSON, SUCCESS, "删除意见建议成功");
			}else{
				logger.debug("===删除附件失败！===");
				return String.format(RESULT_JSON, ERROR, "删除意见建议失败");
			}
		} catch (Exception e) {
			logger.debug("===删除附件失败！===");
			return String.format(RESULT_JSON, ERROR, "删除意见建议失败");
		}
	}
}
