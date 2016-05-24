package org.gradle.service;


import org.gradle.model.DemoInfo;

import javassist.NotFoundException;
 
public interface DemoInfoService {
 
    void delete(Long id);
 
    DemoInfo update(DemoInfo updated) throws NotFoundException;
 
    DemoInfo findById(Long id);
 
    DemoInfo save(DemoInfo demoInfo);
 
}
