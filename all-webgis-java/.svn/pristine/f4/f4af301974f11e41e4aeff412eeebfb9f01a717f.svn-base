package test.com.trgis.trmap.hstore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.hstore.HstoreHelper;
import com.trgis.trmap.hstore.exception.HStoreSQLException;
import com.trgis.trmap.hstore.model.HStoreModel;
import com.trgis.trmap.hstore.service.HStoreService;
import com.trgis.trmap.hstore.service.query.ConditionalEnum;
import com.trgis.trmap.hstore.service.query.Conditions;
import com.trgis.trmap.hstore.service.query.DirectionEnum;
import com.trgis.trmap.hstore.service.query.Page;
import com.trgis.trmap.hstore.service.query.SQLUtil;
import com.trgis.trmap.hstore.service.query.Sort;
import com.trgis.trmap.hstore.service.query.TypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext-*.xml" })
public class TestHStore {

	@Autowired
	private HStoreService hstoreService;

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取随机日期
	 * 
	 * @param beginDate
	 *            起始日期，格式为：yyyy-MM-dd
	 * @param endDate
	 *            结束日期，格式为：yyyy-MM-dd
	 * @return
	 */

	private static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	/**
	 * 测试新增数据
	 */
	@Test
	public void testSave() {
			HStoreModel model = new HStoreModel();
			model.setTableName("测试");
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", UUID.randomUUID().toString());
			String age = Integer.toString(new Random().nextInt(20));
			m.put("age", age);
			model.getStores().putAll(m);
			hstoreService.save(model);
	}

	/**
	 * 测试更新数据
	 * 
	 * 给所有人加上生日
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testUpdate() throws InterruptedException {
		List<HStoreModel> models = hstoreService.queryAll();
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			HStoreModel hStoreModel = (HStoreModel) iterator.next();
			hStoreModel.getStores().put("birthday", sdf.format(randomDate("2000-01-01","2015-07-31")));
			hstoreService.update(hStoreModel);
		}
	}
	
//	@Test
	public void testGetPage(){
		List<Conditions> conditions = new ArrayList<Conditions>();
		Conditions cond = new Conditions("age", new String[]{"13"},TypeEnum.INT,ConditionalEnum.LT);
		Conditions namecond = new Conditions("name", new String[]{"%a%"},ConditionalEnum.LIKE);
		conditions.add(cond);
		conditions.add(namecond);
		Sort sort = new Sort("birthday", DirectionEnum.ASC,TypeEnum.DATE);
		try {
			Page page = new Page(1, 10);
			page = hstoreService.queryPage(page, sort, conditions, SQLUtil.AND);
			System.out.println(page.toString());
			
			for (Iterator iterator = page.getResultList().iterator(); iterator.hasNext();) {
				HStoreModel model = (HStoreModel) iterator.next();
				System.out.println(HstoreHelper.toString(model.getStores()));
			}
			
		} catch (HStoreSQLException e) {
			e.printStackTrace();
		}
	}

}