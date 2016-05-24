package com.trgis.trmap.personal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.personal.exception.UserAttachException;
import com.trgis.trmap.personal.exception.UserRecordException;
import com.trgis.trmap.personal.model.UserRecord;
import com.trgis.trmap.personal.service.UserAttachService;
import com.trgis.trmap.personal.service.UserRecordService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class TestUserRecordController {
	@Autowired
	private UserRecordService userRecordService;
	@Autowired
	private UserAttachService userAttachService;
	/**
	 * @Description: 测试级联修改附件
	 * @author yanpeng
	 * @date 2016年3月10日 下午6:10:33
	 */
	@Test
	public void testGetCollectCount(){
		try {
			UserRecord userRecord = new UserRecord();
			userRecord.setId(1l);
			userRecord.setAddrname("测试修改呢,hah ");
			userRecordService.editUserRecord(userRecord );
			String attachStr = "2,3";
			if (StringUtils.isNotBlank(attachStr )) {
				String ids[] = attachStr.split(",");
				for (String ossid : ids) {
					if (StringUtils.isNotBlank(ossid)) {
						userAttachService.updateRecord(userRecord.getId(),ossid);
					}
				}
			}
		} catch (UserAttachException e) {
			e.printStackTrace();
		} catch (UserRecordException e) {
			e.printStackTrace();
		}
	}
}
