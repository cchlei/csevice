package org.gradle.controller;

import java.util.Map;

import org.gradle.properties.ApplicationDefultSettings;
import org.gradle.properties.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * 测试
 * @author chlei(QQ:361079125)
 * @version v.0.1
 */
@RestController//spring4 新特性 其实现就是在@RestController中加入@ResponseBody和@Controller	
@RequestMapping("/demo")
public class JspController implements EnvironmentAware {
      
	@Autowired  
	Settings settings;  
	
	@Autowired  
	ApplicationDefultSettings applicationDefultSettings;
	
       // 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    @Value("${application.hello:Hello chlei}")
    private String hello;
   
      
   @RequestMapping("/helloJsp")
   public String helloJsp(Map<String,Object> map){
          System.out.println("HelloController.helloJsp().hello="+hello);
          map.put("hello", hello);
          return "helloJsp";
   }
   
   /**
    * 读取系统环境
    */
   @Override
   public void setEnvironment(Environment environment) {
       String s = environment.getProperty("JAVA_HOME");
       System.out.println("Controller+"+s);
   }
   
   /**
    *  使用定义的properties
    */
   
   @RequestMapping("/test")  
   public @ResponseBody String test(){  
   
	   System.out.println(settings.getGender()+"---"+settings.getName());  
	   System.out.println(applicationDefultSettings.getGender()+"==="+applicationDefultSettings.getGender());  
	   return "ok";  
   } 
}
