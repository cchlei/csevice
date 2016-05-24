package com.trgis.trmap.enterprise.web.controller;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.trgis.resource.client.OssService;
import com.trgis.trmap.enterprise.model.EntAttributemeta;
import com.trgis.trmap.enterprise.model.EntLayermeta;
import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;
import com.trgis.trmap.enterprise.service.EntAttributemetaService;
import com.trgis.trmap.enterprise.service.EntLayermetaService;
import com.trgis.trmap.enterprise.service.EntMapGraphicsService;
import com.trgis.trmap.enterprise.service.EntMapService;
import com.trgis.trmap.enterprise.util.BeanUtil;
import com.trgis.trmap.enterprise.util.EnumUtil;
import com.trgis.trmap.enterprise.web.util.ExcelUtils;
import com.trgis.trmap.hstore.model.EntMapProperties;
import com.trgis.trmap.hstore.service.EntMapPropertiesService;
import com.trgis.trmap.userauth.model.User;
import com.trgis.trmap.userauth.service.EnterpriseUserService;

/**
 * 企业文件控制器(上传、删除、缩略图等)
 * 
 * @author Alice
 *
 */
@Controller
@RequestMapping("/entfile")
public class EntFileController {

	private static final Logger logger = LoggerFactory.getLogger(EntFileController.class);
	
	@Autowired
	private OssService ossService;
	@Autowired
	private EntMapService entMapService;
	@Autowired
	private EntLayermetaService layermetaService;
	@Autowired
	private EnterpriseUserService entUserService;
	@Autowired
	private EntMapGraphicsService entMapGraphicsService;
	@Autowired
	private EntMapPropertiesService entMapPropertiesService;
	@Autowired
	private EntAttributemetaService entAttributemetaService;
	/**
	 * 上传
	 * @param request
	 * @param response
	 * @return
	 * 下载地址为 http://oss.trmap.cn/rs/download/拼接ossid
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method ={RequestMethod.POST, RequestMethod.GET})
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		String id = "";
		try {
			// 将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 上传文件到服务器
					id = ossService.uploadFile(file.getInputStream(), file.getOriginalFilename(), "qtmap");
				}
			}
		} catch (Exception e) {
			logger.debug("===上传失败，服务器未正确响应===");
			// TODO log日志 异常处理
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * 导入excel并解析表结构
	 * @param emapid 企业地图ID
	 * @param name 标题
	 * @param x 经度
	 * @param y 纬度
	 * @param key 唯一标识列
	 * @return 成功插入条数
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel/{mapid}")
	public Map<String, Object> importExcel(@PathVariable("mapid") Long mapid, String identifykey, String title,
			String x, String y, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
				InputStream is = file.getInputStream();
					if (!is.markSupported()) {
			            is = new PushbackInputStream(is, 8);
			        }
					Integer count = 0;
			        if (POIFSFileSystem.hasPOIFSHeader(is)) {
			        	count = readXls(is, mapid, identifykey, title, x, y);
			        }else if (POIXMLDocument.hasOOXMLHeader(is)) {
			        	count = readXlsx(is, mapid, identifykey, title, x, y);
			        }
			        map.put("result", "success");
					map.put("count", count);
					System.out.println(count);
				}
			}
		} catch (Exception e) {
			logger.debug("===解析excel表结构失败===");
			// TODO log日志 异常处理
			//e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * xls文件 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public Integer readXls(InputStream is,Long emapid, String identifykey,
		 String title, String x, String y) throws Exception{
		Integer count = 0;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		// 循环每一页
		for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			//标题行处理表扩展结构
			HSSFRow thhssfRow0 = hssfSheet.getRow(0);
			if(thhssfRow0 == null){
				continue;
			}
			HSSFRow thhssfRow1 = hssfSheet.getRow(1);
			int thminColIx = thhssfRow0.getFirstCellNum();
			int thmaxColIx = thhssfRow0.getLastCellNum();
			List<String[]> th4List = new ArrayList<String[]>();//基本属性表头结构
			List<String[]> thList = new ArrayList<String[]>();//扩展属性表头结构
			for(int thcolIx = thminColIx; thcolIx < thmaxColIx; thcolIx++){
				String[] sth = new String[2];
				HSSFCell thcell0 = thhssfRow0.getCell(thcolIx);
				HSSFCell thcell1 = thhssfRow1.getCell(thcolIx);
				sth[0] = thcell0==null?"":thcell0.toString();
				sth[1] = thcell1==null?"":thcell1.toString();
				thList.add(sth);
			}
			List<String[]> attrthList = new ArrayList<String[]>();//入库扩展属性表结构
			for (int i = 0; i < thList.size(); i++) {
				String thx = thList.get(i)[0];
				if(thx.equals(x)||thx.equals(y)||thx.equals(identifykey)||thx.equals(title)){
					th4List.add(thList.get(i));
					continue;
				}
				attrthList.add(thList.get(i));
			}
			if(th4List.size() < 4){
				throw new Exception("更新企业地图扩展属性表结构失败！");
			}
			entAttributemetaService.editAttriButeMeta(attrthList, emapid);
			List<EntLayermeta> layermeta = layermetaService.findLayermetasByMap(entMapService.findUserMapById(emapid));
			if(BeanUtil.isNotEmpty(layermeta)){
				//以下为处理数据
				//处理当前页
				for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					int minColIx = hssfRow.getFirstCellNum();
					int maxColIx = hssfRow.getLastCellNum();
					Map<String, String> m = new HashMap<String, String>();//基本属性
					Map<String, String> mattr = new HashMap<String, String>();//扩展属性
					//便利该行，获取每个cell
					for(int colIx = minColIx; colIx < maxColIx; colIx++){
						HSSFCell cell = hssfRow.getCell(colIx);
						if(cell == null){
							continue;
						}
						String thx = thList.get(colIx)[0];
						if(thx.equals(x)){
							m.put(x, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(y)){
							m.put(y, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(identifykey)){
							m.put(identifykey, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(title)){
							m.put(title, ExcelUtils.getStringVal(cell));
						}else{
							EntAttributemeta attr = entAttributemetaService.findByAttralias(thList.get(colIx)[0],layermeta.get(0));
							mattr.put(attr.getAttrid().toString(), ExcelUtils.getStringVal(cell));
						}
					}
					//一行遍历完毕，进行两个操作1、矢量基本属性入矢量表2、矢量扩展属性入矢量表
					EntUserMap userMap = entMapService.findUserMapById(emapid);
					//根据key值判断是追加OR更新
					EntMapGraphics emg;
					try {
						emg = entMapGraphicsService.findByIdentifykey(userMap, m.get(identifykey));
					} catch (Exception e) {
						emg =new EntMapGraphics();
					}
					//1基本属性
					emg.setUserMap(userMap);
					emg.setGname(m.get(title));
					emg.setIdentifykey(m.get(identifykey));
					emg.setGtype(EnumUtil.GTYPE.POINT.getValue());//拼接WKT（目前只接受点类型）
					emg.setGeom("POINT ("+m.get(x)+" "+m.get(y)+")");
					entMapGraphicsService.createEntMapGraphics(emg);
					//2扩展属性
					EntMapProperties properties;
					try {
						properties = entMapPropertiesService.findByFeature(emg.getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						properties = new EntMapProperties();
					}
					if(BeanUtil.isEmpty(properties)){
						properties = new EntMapProperties();
					}
					properties.setMapId(emapid);
					properties.setFeatureId(emg.getId());
					properties.getStores().putAll(mattr);
					//获取当前企业用户
					String username = (String) SecurityUtils.getSubject().getPrincipal();
					User user = entUserService.findUserByUsername(username);
					properties.setUserId(user.getId());
					//properties有id会执行update,没有id会执行新增
					entMapPropertiesService.edit(properties);
					count++;
				}
			}
		}
		return count;
	}
	/**
	 * xlsx文件 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public Integer readXlsx(InputStream is,Long emapid, String identifykey,
		 String title, String x, String y) throws Exception{
		Integer count = 0;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(OPCPackage.open(is));
		// 循环每一页
		for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++){
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if(xssfSheet == null){
				continue;
			}
			//标题行处理表扩展结构
			XSSFRow thxssfRow0 = xssfSheet.getRow(0);
			if(thxssfRow0 == null){
				continue;
			}
			XSSFRow thxssfRow1 = xssfSheet.getRow(1);
			int thminColIx = thxssfRow0.getFirstCellNum();
			int thmaxColIx = thxssfRow0.getLastCellNum();
			List<String[]> th4List = new ArrayList<String[]>();//基本属性表头结构
			List<String[]> thList = new ArrayList<String[]>();//扩展属性表头结构
			for(int thcolIx = thminColIx; thcolIx < thmaxColIx; thcolIx++){
				String[] sth = new String[2];
				XSSFCell thcell0 = thxssfRow0.getCell(thcolIx);
				XSSFCell thcell1 = thxssfRow1.getCell(thcolIx);
				sth[0] = thcell0==null?"":thcell0.toString();
				sth[1] = thcell1==null?"":thcell1.toString();
				thList.add(sth);
			}
			List<String[]> attrthList = new ArrayList<String[]>();//入库扩展属性表结构
			for (int i = 0; i < thList.size(); i++) {
				String thx = thList.get(i)[0];
				if(thx.equals(x)||thx.equals(y)||thx.equals(identifykey)||thx.equals(title)){
					th4List.add(thList.get(i));
					continue;
				}
				attrthList.add(thList.get(i));
			}
			if(th4List.size() < 4){
				throw new Exception("更新企业地图扩展属性表结构失败！");
			}
			entAttributemetaService.editAttriButeMeta(attrthList, emapid);
			List<EntLayermeta> layermeta = layermetaService.findLayermetasByMap(entMapService.findUserMapById(emapid));
			if(BeanUtil.isNotEmpty(layermeta)){
				//以下为处理数据
				//处理当前页
				for(int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					int minColIx = xssfRow.getFirstCellNum();
					int maxColIx = xssfRow.getLastCellNum();
					Map<String, String> m = new HashMap<String, String>();
					Map<String, String> mattr = new HashMap<String, String>();//扩展属性
					//便利该行，获取每个cell
					for(int colIx = minColIx; colIx < maxColIx; colIx++){
						XSSFCell cell = xssfRow.getCell(colIx);
						if(cell == null){
							continue;
						}
						String thx = thList.get(colIx)[0];
						if(thx.equals(x)){
							m.put(x, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(y)){
							m.put(y, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(identifykey)){
							m.put(identifykey, ExcelUtils.getStringVal(cell));
						}else if(thx.equals(title)){
							m.put(title, ExcelUtils.getStringVal(cell));
						}else{
							EntAttributemeta attr = entAttributemetaService.findByAttralias(thList.get(colIx)[0],layermeta.get(0));
							mattr.put(attr.getAttrid().toString(), ExcelUtils.getStringVal(cell));
						}
					}
					//一行遍历完毕，进行两个操作1、矢量基本属性入矢量表2、矢量扩展属性入矢量表
					EntUserMap userMap = entMapService.findUserMapById(emapid);
					//根据key值判断是追加OR更新
					EntMapGraphics emg;
					try {
						emg = entMapGraphicsService.findByIdentifykey(userMap, m.get(identifykey));
					} catch (Exception e) {
						emg =new EntMapGraphics();
					}
					//1基本属性
					emg.setUserMap(userMap);
					emg.setGname(m.get(title));
					emg.setIdentifykey(m.get(identifykey));
					emg.setGtype(EnumUtil.GTYPE.POINT.getValue());//拼接WKT（目前只接受点类型）
					emg.setGeom("POINT ("+m.get(x)+" "+m.get(y)+")");
					entMapGraphicsService.createEntMapGraphics(emg);
					//2扩展属性
					EntMapProperties properties;
					try {
						properties = entMapPropertiesService.findByFeature(emg.getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						properties = new EntMapProperties();
					}
					if(BeanUtil.isEmpty(properties)){
						properties = new EntMapProperties();
					}
					properties.setMapId(emapid);
					properties.setFeatureId(emg.getId());
					properties.getStores().putAll(mattr);
					//获取当前企业用户
					String username = (String) SecurityUtils.getSubject().getPrincipal();
					User user = entUserService.findUserByUsername(username);
					properties.setUserId(user.getId());
					//properties有id会执行update,没有id会执行新增
					entMapPropertiesService.edit(properties);
					count++;
				}
			}
		}
		return count;
	}
}
