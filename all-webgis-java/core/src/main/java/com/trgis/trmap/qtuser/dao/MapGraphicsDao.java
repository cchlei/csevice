package com.trgis.trmap.qtuser.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.qtuser.model.MapGraphics;
import com.trgis.trmap.qtuser.model.UserMap;
import com.trgis.trmap.userauth.model.User;
/**
 * @author doris
 * 地图矢量dao
 */
@Repository
public interface MapGraphicsDao extends JpaRepository<MapGraphics,Long>,JpaSpecificationExecutor<MapGraphics>{

	/**
	 * 根据id查询个人地图记录
	 * @param id
	 * @return
	 */
	@Query("from MapGraphics  where userMap = ?1 and delflag = ?2 order by id desc")
	public List<MapGraphics> findMapGraphicsByUserMap(UserMap userMap,Integer delflag);
	/**
	 * 根据id查询个人地图记录
	 * @param id
	 * @return
	 */ 
	public MapGraphics findMapGraphicsById(Long id);
	/**
	 * 统计矢量
	 * @param user
	 * @return
	 */
	@Query("select count(id) from MapGraphics  where userMap.user = ?1 and delflag = ?2")
	public Integer countGraphics(User user,Integer delflag);
	/**
	 * 根据mapid级联删除矢量数据
	 * @param date
	 */
	@Modifying 
	@Query("update MapGraphics set delflag = ?1 where userMap = ?2") 
	public void delByUsermap(Integer delflag, UserMap userMap); 
}
