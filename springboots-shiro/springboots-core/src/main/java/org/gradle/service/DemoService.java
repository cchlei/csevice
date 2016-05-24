package org.gradle.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.gradle.dao.DemoDao;
import org.gradle.dao.DemoRepository;
import org.gradle.model.Demo;

 
/**
 * 提供Demo服务类.
 * @author chlei
 *
 */
@Service
public class DemoService {
   
    @Resource
    private DemoRepository demoRepository;
   
    @Resource
    private DemoDao demoDao;
   
    public void save(Demo demo){
           demoRepository.save(demo);
    }
   
    public Demo getById(long id){
           //demoRepository.findOne(id);//在demoRepository可以直接使用findOne进行获取.
           return demoDao.getById(id);
    }
}
