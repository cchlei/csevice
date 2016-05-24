package org.gradle;

import javax.servlet.MultipartConfigElement;

import org.gradle.plugin.SpringUtil;
import org.gradle.properties.ApplicationDefultSettings;
import org.gradle.properties.Settings;
import org.gradle.servlet.ServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//@ServletComponentScan是的spring能够扫描到我们自己编写的servlet和filter。
@ServletComponentScan
//其中@SpringBootApplication申明让spring boot自动给程序进行必要的配置，
//等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
@SpringBootApplication
//新用新的配置文件 在入口用此注解 进行读取 
@EnableConfigurationProperties({Settings.class,ApplicationDefultSettings.class}) 
//@Import(value={SpringUtil.class})
//启动类进行注解指定 扫描的包 可以使用：basePackageClasses={},basePackages={}
@ComponentScan(basePackages={"cn.gradle","com.gradle","org.gradle"})
public class DemoMain {

	 /**注册Spring Util
     * 这里为了和上一个冲突，所以方面名为：springUtil2
     * 实际中使用springUtil
     */
    @Bean
    public SpringUtil springUtil(){
    	return new SpringUtil();
    }
    
    /**
     * 注册Servlet.不需要添加注解：@ServletComponentScan
     * @return
     * 至于如何使用代码的方式注册Filter和Listener，请参Servlet的注册。
     * 不同的是需要使用 FilterRegistrationBean 和 ServletListenerRegistrationBean 这两个类。
     */
    @Bean
    public ServletRegistrationBean ServletConfigure(){
    	return new ServletRegistrationBean(new ServletConfiguration(),"/configuration/*");
    }
	
    @Bean 
    public MultipartConfigElement multipartConfigElement() { 
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("128KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("256KB"); 
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        return factory.createMultipartConfig(); 
    } 
    
	public static void main(String[] args) {
	    SpringApplication.run(DemoMain.class, new String[]{"hello,","林峰"});
	}
}
