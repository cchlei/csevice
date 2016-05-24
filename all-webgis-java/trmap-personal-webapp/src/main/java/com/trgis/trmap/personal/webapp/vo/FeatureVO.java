/**
 * 
 */
package com.trgis.trmap.personal.webapp.vo;

/**
 * @author zhangqian
 *
 */
public class FeatureVO {

	private Long id;

	private String title;
	
	private String occurtime;
	
	private String description;
	
	private String geom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(String occurtime) {
		this.occurtime = occurtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
