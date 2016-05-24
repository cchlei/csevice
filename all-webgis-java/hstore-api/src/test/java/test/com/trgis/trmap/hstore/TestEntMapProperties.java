package test.com.trgis.trmap.hstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.hstore.HstoreHelper;
import com.trgis.trmap.hstore.exception.MapPropertiesException;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.DirectionEnum;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.SQLUtil;
import com.trgis.trmap.hstore.service.query.Sort;
import com.trgis.trmap.hstore.service.query.TypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-*.xml" })
public class TestEntMapProperties {
 @Autowired
 private EntMapPropertiesService mapPropertiesService;
	/**
	 * 测试新增数据
	 */
	@Test
	public void testSave() {
			EntMapProperties ma = new EntMapProperties();
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", "赵军");
			String age = "23";
			m.put("age", age);
			String sex = "男";
			m.put("sex", sex);
			ma.getStores().putAll(m);
			ma.setFeatureId(790l);
			mapPropertiesService.save(ma);
		}
	/**
	 * 测试更新数据
	 * 
	 * 给所有人加上编码
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testUpdate() throws InterruptedException {
			EntMapProperties entMapProperties =mapPropertiesService.findById(796l);
			entMapProperties.getStores().put("staffid",Integer.toString(new Random().nextInt(20)));
			mapPropertiesService.edit(entMapProperties);
	}
	@Test
	public void testGetPage(){
		List<Conditions> conditions = new ArrayList<Conditions>();
//		Conditions cond = new Conditions("age", new String[]{"13"},TypeEnum.INT,ConditionalEnum.LT);
//		Conditions namecond = new Conditions("name", new String[]{"%a%"},ConditionalEnum.LIKE);
//		conditions.add(cond);
//		conditions.add(namecond);
		Sort sort = new Sort("age", DirectionEnum.ASC,TypeEnum.STRING);
		try {
			Page page = new Page(1, 10);
			page = mapPropertiesService.queryPage(page, sort, conditions, SQLUtil.AND);
			System.out.println(page.toString());
			
			for (Iterator iterator = page.getResultList().iterator(); iterator.hasNext();) {
				EntMapProperties entMapProperties = (EntMapProperties) iterator.next();
				entMapProperties.setFeatureId(790l);
				mapPropertiesService.edit(entMapProperties);
				System.out.println(HstoreHelper.toString(entMapProperties.getStores()));
			}
			
		} catch (MapPropertiesException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFindByFeature(){
		EntMapProperties entMapProperties = mapPropertiesService.findByFeature(584l);
		Map<String, String> ent =entMapProperties.getStores();
		Iterator it=ent.keySet().iterator();    
		while (it.hasNext()) {
			String key; 
		    String value; 
		    key=it.next().toString(); 
		    value=ent.get(key); 
		    System.out.println(key+"--"+value); 
		}
	}
}
