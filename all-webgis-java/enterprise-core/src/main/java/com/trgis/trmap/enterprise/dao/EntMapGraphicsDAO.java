package com.trgis.trmap.enterprise.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.trmap.enterprise.model.EntMapGraphics;
import com.trgis.trmap.enterprise.model.EntUserMap;

/**
 * 要素DAO
 * 
 * @author zhangqian
 *
 */
@Repository
public interface EntMapGraphicsDAO  extends JpaRepository<EntMapGraphics,Long>,JpaSpecificationExecutor<EntMapGraphics>{

	@Query("from EntMapGraphics  where userMap = ?1 and delflag = ?2")
	public List<EntMapGraphics> findByEntUserMap(EntUserMap entUserMap,Integer delflag);
	
	@Query("from EntMapGraphics  where userMap = ?1 and identifykey = ?2")
	public List<EntMapGraphics> findByIdentifykey(EntUserMap entUserMap, String identifykey);
	
	@Query("select count(*) from EntMapGraphics  where userMap = ?1 and delflag = ?2")
	public Long findCountByMap(EntUserMap entUserMap,Integer delflag); 
	
	@Query("from EntMapGraphics  where userMap = ?1 and delflag = ?2")
	public List<EntMapGraphics> findByEntUserMapeSe(EntUserMap entUserMap,Pageable pageable,Integer delflag);
	/**
	 * @author chlei
	 * @param geom
	 * @param entUserMap
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	//范围查询中的地图矢量坐标点	'POLYGON((108.93184661865234 34.34726284760974,109.0228271484375 34.33649072696441,108.93081665039064 34.27097780992197,108.93184661865234 34.34726284760974))'
	@Query(nativeQuery=true,value="select * from qtmap_enterprise_graphics where st_within(geom,st_geomfromtext(?1)) and gname LIKE %?2%  and map_id = ?3 order by id desc limit ?4 offset (?5 -1 )*20")
	public List<EntMapGraphics> findByEntUserMap(String geom , String keyword ,Long mapid , Integer pageSize , Integer pageNum);
	
	/**
	 * @author chlei
	 * @param geom
	 * @param entUserMap
	 * @return
	 */
	@Query(nativeQuery=true,value="select count(*) from qtmap_enterprise_graphics where st_within(geom,st_geomfromtext(?1)) and map_id = ?2 and gname LIKE %?3%" )
	public Long findCountEntMapGraphics(String geom, Long mapid, String keyword);
			
	@Modifying
	@Query("delete from EntMapGraphics where userMap = ?1")
	public void deleteByEntUserMap(EntUserMap entUserMap);
}
