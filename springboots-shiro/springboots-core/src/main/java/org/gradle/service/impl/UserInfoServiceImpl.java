package org.gradle.service.impl;

import javax.annotation.Resource;

import org.gradle.dao.UserInfoRepository;
import org.gradle.model.UserInfo;
import org.gradle.service.UserInfoService;
import org.springframework.stereotype.Service;
 
@Service
public class UserInfoServiceImpl implements UserInfoService{
   
    @Resource
    private UserInfoRepository userInfoRepository;
   
    @Override
    public UserInfo findByUsername(String username) {
       System.out.println("UserInfoServiceImpl.findByUsername()");
       return userInfoRepository.findByUsername(username);
    }
   
}
