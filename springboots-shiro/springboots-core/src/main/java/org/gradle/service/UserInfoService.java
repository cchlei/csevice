package org.gradle.service;

import org.gradle.model.UserInfo;
public interface UserInfoService {
   
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
   
}