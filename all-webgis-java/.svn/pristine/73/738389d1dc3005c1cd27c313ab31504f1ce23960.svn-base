package test.com.trgis.qtmap.qtenterprise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.vo.PropertyVo;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.UserService;
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
public class TestEnterpriseCreatMapReleaseService {
	
		@Autowired
		private EntMapService epMapService;
		@Autowired
		private EntLayermetaService epLayermetaService;
		@Autowired
		private EntAttributemetaService epAttributemetaService;
		@Autowired
		private EntMapGraphicsService entMapGraphicsService;
		@Autowired
		private EntMapPropertiesService entMapPropertiesService;
		@Autowired
		private UserService userService;
		
		/**
		 * @author chenhl
		 */
		@Test
		public void testCreateEnterprise() {
			EntUserMap enterpriseMap=new EntUserMap();
			
			//long Long=1;
			//enterpriseMap.setId(Long);
//			String username = (String) SecurityUtils.getSubject().getPrincipal();
//			User user = entUserService.findUserByUsername(username);
			User user = userService.findUser(2l);
			enterpriseMap.setUser(user);
			enterpriseMap.setMapname("新图层");
			enterpriseMap.setIsshare(0);
			epMapService.createEnterpriseMap(enterpriseMap);
			//同事保存layermeta 把 EntUserMap 中的ID取出保存进来
			EntLayermeta entLayermeta=new EntLayermeta();
			entLayermeta.setMap(enterpriseMap);
			
			epLayermetaService.createEnterpriseLayermeta(entLayermeta);
			//同事保存layermeta 把 EntUserMap 中的ID取出保存进来
			EntAttributemeta entAttributemeta=new EntAttributemeta();
			entAttributemeta.setAttralias("Attribute1");
			entAttributemeta.setAttrcode("1");
			entAttributemeta.setAttrtype("文本型");
			entAttributemeta.setLayermeta(entLayermeta);
			epAttributemetaService.createEnterpriseAttributemeta(entAttributemeta);
			System.out.println(enterpriseMap.getMapname()+"++");
		}
		/**
		 * @author doushan
		 */
		@Test
		public void testfindByEntLayermeta() {
			EntLayermeta entLayermeta=epLayermetaService.findById(840l);
			List<EntAttributemeta> etList=epAttributemetaService.findByEntLayermeta(entLayermeta);
			for (EntAttributemeta entAttributemeta : etList) {
				System.out.println(entAttributemeta.getAttralias()+"+++++++++++++");
			}
		}
		/**
		 * @author chenhl
		 * 根据ID 编辑 EntUserMap
		 */
		@Test
		public void testEditeMapEnterprise() {
			EntUserMap	entUserMap=epMapService.findUserMapById(870l);
			entUserMap.setIsshare(1);
			epMapService.editUserMap(entUserMap);
		}
		
		/**
		 * @author chenhl
		 * 删除整个图层 进行级联删除 EntUserMap(图层) EntLayermeta（中间表） EntAttributemeta同时 删除 这三张表的数据
		 */
		@Test
		public void testDeleteMapEnterprise() {
			
			// 通过前台传来的id 获得其对应的对象 
			EntUserMap entUserMap=epMapService.findUserMapById(839l);
			
			EntLayermeta entLayermeta=epLayermetaService.findByEntUserMap(entUserMap);
			System.out.println(entUserMap.getId());
			
			List<EntAttributemeta> entAttributemetaList=epAttributemetaService.findByEntLayermeta(entLayermeta);
			for(EntAttributemeta entAttributemeta:entAttributemetaList){
				entAttributemeta.getAttrid();
				epAttributemetaService.deletByEntAttributemeta(entAttributemeta.getAttrid());
			}
			epLayermetaService.deleteEntLayermeta(entLayermeta.getLayermetaid());
			//根据获得对象的id 删除 EntUserMap
			epMapService.deleteEntUserMap(entUserMap.getId());
		}
		
		/**
		 * @author chenhl
		 * 单独删除 某个扩展属性EntAttributemeta
		 */
		@Test
		public void testDeleteEntAttributemeta(){
			
			//通过前台传来的id 删除其EntAttributemeta
			epAttributemetaService.deletByEntAttributemeta(9053l);
		}
		@Test
		public void  getEntMapGraphicsByIdList(){
			 List<EntMapGraphics> entMapGraphicsList=new ArrayList<EntMapGraphics>();
			 Map<String, Object> map = new HashMap<String, Object>();
			 try {
				 entMapGraphicsList = entMapGraphicsService.findAllMapGraphics();
				 System.out.println(entMapGraphicsList.toString());
				 for(EntMapGraphics entMapGraphics:entMapGraphicsList){
					 Long featureid=entMapGraphics.getId();
					
					 if(BeanUtil.isEmpty(featureid)){
						 map.put("result", "error");
						 map.put("msg", "矢量id不能为空！");
						 //return map.toString();
					 }
					 try {
						 ArrayList<PropertyVo> entproperties= new ArrayList<>();
						 EntMapProperties pro = entMapPropertiesService.findByFeature(featureid);
						 Map<String, String> stores = pro.getStores();
						 Iterator<String> it=stores.keySet().iterator();  
						 while (it.hasNext()) {
								String key; 
							    String value; 
							    key=it.next().toString(); 
							    value=stores.get(key); 
							    PropertyVo provo = new PropertyVo();
							    provo.setAttrkey(key);
							    provo.setAttrvalue(value);
							    entproperties.add(provo);
							}
						 map.put("entproperties", entproperties);
						 map.put("result","success");
					} catch (Exception e) {
						map.put("result", "error");
						map.put("msg", "获取矢量信息扩展值列表信息失败！");
					}
				 }
				 map.put("id", "编号");
				 map.put("gname", "名称");
				// return "";
			} catch (Exception e) {
				map.put("result", "error");
				map.put("msg", "获取矢量信息失败！");
				//return "";
			}
		 }	
	
			

}
