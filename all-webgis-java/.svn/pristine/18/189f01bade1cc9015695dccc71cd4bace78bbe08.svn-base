package com.trgis.trmap.system.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.trgis.resource.client.OssService;
import com.trgis.trmap.system.model.ImageStorage;
import com.trgis.trmap.system.service.ImageStorageService;
import com.trgis.trmap.system.util.EnumUtil;
import com.trgis.trmap.system.utils.FileResult;
import com.trgis.trmap.system.utils.JSONPResult;
import com.trgis.trmap.system.utils.JSONPage;
import com.trgis.trmap.system.utils.MessageConverts;
import com.trgis.trmap.system.utils.Result;
import com.trgis.trmap.system.vo.ImageStorageForm;

/**
 * 
 * @author majl
 * @Description 默认图库控制器
 * @data 2016年4月12日
 */
@Controller
@RequestMapping(value="/imagestorage")
public class ImageStorageController {

	private static final Logger logger = LoggerFactory.getLogger(ImageStorageController.class);

	@Autowired
	private OssService ossService;


	@Autowired
	private ImageStorageService imageStorageService;

	//页面转发 
	@RequestMapping(value="/imgstoragelist" ,method=RequestMethod.GET)
	public String imageStorageList(){

		return "imagestorage/imgstoragelist";
	}
//查询默认图库
	@RequestMapping(value="/imgstoragelist",method=RequestMethod.POST)
	@ResponseBody
	public JSONPage imageStorageList(int rows,int page,String searchVal,String imageTypeVal){
		Page<ImageStorage> storages = null ;
		storages=imageStorageService.findAll(page, rows, searchVal,imageTypeVal);
		JSONPage jp = new JSONPage();
		if(storages!=null && storages.getContent()!=null && storages.getContent().size()>0){
		for(ImageStorage storage : storages.getContent()){
			storage.setDelflagName(EnumUtil.DELFLAG.getName(storage.getDelflag()));
			storage.setImageTypeName(EnumUtil.IMAGETYPE.getName(storage.getImageType()));
		}
		jp.setRows(storages==null?new ArrayList<ImageStorage>():storages.getContent());
		}else{
			List<ImageStorage> storageList = new ArrayList<ImageStorage>();
			jp.setRows(storageList);
		}
		jp.setTotal(storages==null?0:(int)storages.getTotalElements());
		return jp;
	}
	
	/**
	 * @Description://专题页面获取专题的封面图片
	 * @Author liuyan 
	 * @Date 2016年4月26日 下午2:19:20
	 * @param imageTypeVal
	 * @return
	 * @author Alice
	 * @Date 2016年5月16日 修改为jsonp格式
	 */
	@ResponseBody
	@RequestMapping(value="/imglist/{id}", method=RequestMethod.GET)
	public String imageList(@PathVariable("id") String imageTypeVal, @RequestParam String callback){
		List<ImageStorage> storages = null ;
		Result result = new Result();
		if (StringUtils.isBlank(imageTypeVal)) {
			result.setStatus(Result.STATUS_FAILED);
			return JSONPResult.jsonp(callback, result);
		}
		
		storages = imageStorageService.findByImgtype(Integer.valueOf(imageTypeVal));
		if(storages!=null){
			List<String> ossids = new ArrayList<String>();
			for(ImageStorage storage :storages){
				ossids.add(storage.getOssid());
			}
			logger.debug("图库获取成功！");
		    result.setStatus(Result.STATUS_OK);
		    result.setData(ossids);
			return JSONPResult.jsonp(callback, result);
		}else{
			result.setStatus(Result.STATUS_FAILED);
			return JSONPResult.jsonp(callback, result);
		}
	}
	
//添加默认图库 
	@RequestMapping(value="/addimagestorm",method=RequestMethod.POST)
	@ResponseBody
	public Result createImageStorag(ImageStorageForm imageStorageForm,Integer imageTypeValue){
		logger.debug("add imageStorm start!");
		logger.debug("imageTypeValue"+imageTypeValue+"\timageTypeName:"+EnumUtil.IMAGETYPE.getName(imageTypeValue));
		Result result = new Result();
		try{
			if(imageStorageForm!=null && imageStorageForm.getStorages()!=null){
			 for(ImageStorage storage : imageStorageForm.getStorages()){
				 logger.debug("ossid:"+storage.getOssid());
				 logger.debug("fileName:"+storage.getName());
				 storage.setImageType(imageTypeValue);
			 }
			imageStorageService.createEntityList(imageStorageForm.getStorages());
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
			}
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug("add imageStorm failed!"+e.getMessage());
			e.printStackTrace();
		}
		logger.debug("add imageStorm SUCCESS!");
		return result;
	}

	/**
	 * 上传图片
	 * @return
	 */
	@RequestMapping(value="/upload" ,method=RequestMethod.POST)
	@ResponseBody
	public FileResult upload(HttpServletRequest request, HttpServletResponse response){
		logger.debug("upload picture start!");
		FileResult result = new FileResult();
		try{
			String ossid = null;
			String filename = null;
				MultipartHttpServletRequest mrequest= (MultipartHttpServletRequest)request;
				Iterator<String> filenames = mrequest.getFileNames();
				while(filenames.hasNext()){
					MultipartFile file = mrequest.getFile(filenames.next());
					if(null != file){
						if(StringUtils.isNotBlank(file.getOriginalFilename()));{
							//上传文件到服务器
							filename = file.getOriginalFilename();
							ossid = ossService.uploadFile(file.getInputStream(), filename, "qtmap");
							result.setOssid(ossid);
						}
					}
				}
			
			result.setStatus(Result.STATUS_OK);
			result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug("upload picture fatled!"+e.getMessage());
			e.printStackTrace();
		}
		logger.debug("upload picture SUCCESS!");
		return result;
	}
  //删除默认图库 
	@RequestMapping(value="removeimageStorage",method=RequestMethod.POST)
	@ResponseBody
	public Result removeImageStorage(Long id,Result result){
		try{
		ImageStorage imagestorage = imageStorageService.findOneById(id);
		if(imagestorage!=null){
			if(imagestorage.getDelflag()==EnumUtil.DELFLAG.NOMAL.getValue()){
				imagestorage.setDelflag(EnumUtil.DELFLAG.DEL.getValue());
				imageStorageService.modifyEntity(imagestorage);
				result.setStatus(Result.STATUS_OK);
				result.setMsg(MessageConverts.DEFAULT_SUCCESS_MSG);
			}
		}
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(MessageConverts.DEFAULT_FAILED_MSG);
			logger.debug(e.getMessage());
		}
		return result;
	}

}
