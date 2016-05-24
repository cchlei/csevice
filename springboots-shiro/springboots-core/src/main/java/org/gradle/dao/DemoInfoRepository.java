package org.gradle.dao;
import org.gradle.model.DemoInfo;
import org.springframework.data.repository.CrudRepository;

 
/**
 * 操作数据库.
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
public interface DemoInfoRepository extends CrudRepository<DemoInfo,Long>{
 
}
