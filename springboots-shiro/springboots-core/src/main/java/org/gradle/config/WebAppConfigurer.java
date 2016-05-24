package org.gradle.config;

import org.gradle.interceptor.InterceptorImp;
import org.gradle.interceptor.InterceptorWeb;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
//实现自定义拦截器只需要3步： 
//1、创建我们自己的拦截器类并实现 HandlerInterceptor 接口。 
//2、创建一个Java类继承WebMvcConfigurerAdapter，并重写 addInterceptors 方法。 
//2、实例化我们自定义的拦截器，然后将对像手动添加到拦截器链中（在addInterceptors方法中添加）。 
//PS：本文重点在如何在Spring-Boot中使用拦截器，关于拦截器的原理请大家查阅资料了解。
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
        super.addResourceHandlers(registry);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new InterceptorImp()).addPathPatterns("/**");
        registry.addInterceptor(new InterceptorWeb()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
 
}
//如果我们将/myres/* 修改为 /* 与默认的相同时，则会覆盖系统的配置，可以多次使用 addResourceLocations 添加目录，优先级先添加的高于后添加的。
//其中 addResourceLocations 的参数是动参，可以这样写 addResourceLocations
//(“classpath:/img1/”, “classpath:/img2/”, “classpath:/img3/”);
//使用外部目录,如果我们要指定一个绝对路径的文件夹（如 D:/data/api_files ），则只需要使用 addResourceLocations 指定即可。
//// 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
//registry.addResourceHandler("/api_files/**").addResourceLocations("file:D:/data/api_files"