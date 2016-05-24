package com.trgis.trmap.enterprise.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 菜单封装对象
 * @author doris
 * @date 2015-12-15
 */
public class MenuVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单名称
	 */
    private String name;
    /**
	 * 菜单标志
	 */
    private String icon;
    /**
	 * 菜单链接   默认点击无效
	 */
    private String href = "#";
    /**
	 * 二级菜单
	 */
    private List<MenuVo> subs = new ArrayList<MenuVo>();
    /**
   	 * 二级菜单
   	 */
    private List<MenuVo> three = new ArrayList<MenuVo>();

	public MenuVo() {
		super();
	}
	public MenuVo(String name, String icon, String href, List<MenuVo> subs, List<MenuVo> three) {
		super();
		this.name = name;
		this.icon = icon;
		this.href = href;
		this.subs = subs;
		this.three = three;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<MenuVo> getSubs() {
		return subs;
	}
	public void setSubs(List<MenuVo> subs) {
		this.subs = subs;
	}
	public List<MenuVo> getThree() {
		return three;
	}
	public void setThree(List<MenuVo> three) {
		this.three = three;
	}

}
