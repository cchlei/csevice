package test.com.trgis.qtmap.qtuser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.trgis.trmap.qtuser.model.Suggestion;
import com.trgis.trmap.qtuser.service.SuggestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestSuggestionService {
	@Autowired
	private SuggestionService suggestionService;

	@Test
	public void getByGraphicsId() {
		Suggestion suggestion = new Suggestion();
		suggestion.setContent("testtest意见建议222");
		suggestionService.saveSuggestion(suggestion);
		System.out.println(suggestion.getId());
	}

	@Test
	public void testAttachfileList() {
		// 组装条件组
		ConditionGroup group = new ConditionGroup();
		group.setSearchRelation(SearchRelation.AND);
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		// 条件1关键字
		String content = "";
		if (StringUtils.isNotBlank(content)) {
			conditions.add(new SearchCondition("content", Operator.LIKE, content));
		}
		group.setConditions(conditions);
		OrderBy order = new OrderBy("createtime", "desc");
		int pageNo = 1;
		int pageSize = 10;
		Page<Suggestion> suggestion = suggestionService.findByConditions(group, pageNo, pageSize, order);
		List<Suggestion> aList = suggestion.getContent();
		for (Suggestion s : aList) {
			System.out.println(s.getContent() + "+++++++++++");
		}
	}

}
