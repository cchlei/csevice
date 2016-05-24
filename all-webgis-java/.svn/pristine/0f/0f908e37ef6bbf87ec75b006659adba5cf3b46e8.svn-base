package com.trgis.trmap.qtuser.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;

/**
 * 矢量附件
 * @author Alice
 * 2015年9月19日
 */
@Repository
public interface AttachfileDao extends JpaRepository<Attachfile,Long>,JpaSpecificationExecutor<Attachfile>{
	
	/**
	 * 根据矢量查所属附件
	 */
	public List<Attachfile> findByMapGraphics(MapGraphics mapGraphics);
	
	@Query("select ossid from Attachfile where mapGraphics = ?1")
	public List<String> findOssidByMapGraphics(MapGraphics mapGraphics);
	
	public List<Attachfile> findAttachfileByOssid(String ossid);
	
	/**
	 * 用户上传已使用用量
	 * @param userid
	 * @return
	 */
	@Query("select sum(attachSize) from Attachfile where uploadUserId = ?1 and mapGraphics is not null")
	public Double getUsedDataSpace(Long userid);
	
	/**
	 * 清除矢量的附件信息	
	 * @param mapGraphics
	 */
	@Modifying 
	@Query("update Attachfile set mapGraphics = null where mapGraphics =?1") 
	public void clearAttachfileById(MapGraphics mapGraphics); 
	
	/**
	 * 删除之前没有绑定矢量的空数据，清除服务器垃圾
	 * @param date
	 */
	@Modifying 
	@Query("delete from Attachfile where mapGraphics = null and uploadTime <?1") 
	public void clearAttachfileByDate(Date date); 
}
