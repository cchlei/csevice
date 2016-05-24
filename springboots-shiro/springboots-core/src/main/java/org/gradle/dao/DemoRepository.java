package org.gradle.dao;

import org.gradle.model.Demo;
import org.springframework.data.repository.CrudRepository;

/*
 * 在CrudRepository自带常用的crud方法.
 * 这样一个基本dao就写完了.
 */
public interface DemoRepository extends CrudRepository<Demo, Long>{
 
}
