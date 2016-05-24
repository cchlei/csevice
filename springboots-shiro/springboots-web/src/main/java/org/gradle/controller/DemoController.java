package org.gradle.controller;

import javax.annotation.Resource;

import org.gradle.model.Demo;
import org.gradle.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
 
 
/**
 * 测试.
 * @author chlei
 *
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
	
	@Resource
    private DemoService demoService;
	
    /**
     * 返回demo数据:
     * 请求地址：http://127.0.0.1:8080/demo/getDemo
     * @return
     */
    @RequestMapping("/getDemo")
    public Demo getDemo(){
       Demo demo = new Demo();
       demo.setId(1);
       demo.setName("Angel");
       return demo;
    }
    
    /**
     * 返回demo数据:
     * 请求地址：http://127.0.0.1:8080/demo/getFastJson
     * @return
     */
    @RequestMapping("/getFastJson")
    public String getFastJson(){
       Demo demo = new Demo();
       demo.setId(2);
       demo.setName("Angel2");
       return JSONObject.toJSONString(demo);
    }
    
    @RequestMapping("/zeroException")
    public int zeroException(){
       return 100/0;
    }
    
    /**
     * 测试保存数据方法.
     * @return
     */
    @RequestMapping("/save")
    public String save(){
       Demo d = new Demo();
       d.setName("Angel");
       demoService.save(d);//保存数据.
       return"ok.Demo2Controller.save";
    }
   
    
  //地址：http://127.0.0.1:8080/demo2/getById?id=1
    @RequestMapping("/getById")
    public Demo getById(long id){
       return demoService.getById(id);
    }
}