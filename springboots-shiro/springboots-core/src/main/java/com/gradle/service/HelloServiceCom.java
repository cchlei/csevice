package com.gradle.service;


import org.springframework.stereotype.Service;
 
@Service
public class HelloServiceCom {
   
    /**
     * 启动的时候观察控制台是否打印此信息;
     */
    public HelloServiceCom() {
       System.out.println("com.gradle.service.HelloService()");
       System.out.println("com.gradle.service.HelloService.HelloService()");
       System.out.println("com.gradle.service.HelloService()");
    }
   
}