package com.trgis.trmap.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.system.model.ImageStorage;

/**
 * 
 * @author majl
 * @Description 
 * @data 2016年4月12日
 */

@Repository
public interface ImageStorageDao  extends  JpaRepository<ImageStorage, Long> ,JpaSpecificationExecutor<ImageStorage>{


	List<ImageStorage> findByImageTypeAndDelflag(Integer imgtype,Integer delflag);

}
