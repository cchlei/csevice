package com.trgis.trmap.qtuser.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.trmap.qtuser.model.Attachfile;
import com.trgis.trmap.qtuser.model.MapGraphics;

/**
 * 矢量附件服务类
 * @author Alice
 * 2015年9月19日
 */
public interface AttachfileService {
	
	/**
	 * 添加文件记录
	 */
	public void saveAttachfile(Attachfile attachfile);

	/**
	 * 删除文件记录
	 */
	public void deleteAttachfile(Long id);
	
	/**
	 * 根据矢量id清除所有附件关联关系
	 * @param attachfileid
	 */
	public void clearAttachfileById(MapGraphics mapGraphics);
	
	/**
	 * 按时间清除没有绑定矢量的附件信息
	 * @param date
	 */
	public void clearAttachfileByDate(Date date);
	
	/**
	 * 根据矢量id查找附件
	 */
	public List<Attachfile> getByGraphicsId(MapGraphics mapGraphics);
	public List<String> getOssidByGraphicsId(MapGraphics mapGraphics);
	/**
	 * 分页
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param orderby
	 * @return
	 */
	public Page<Attachfile> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy orderby);

	/**
	 * 根据云存储ID查找文件
	 * @param ossid
	 * @return
	 */
	public Attachfile findAttachfileByOssid(String ossid);
	
	/**
	 * 查找用户已使用的用量
	 * @param user
	 * @return
	 */
	public Double getUsedDataSpace(Long userid);
}
