package com.thinkgem.jeesite.modules.wechat.dto;

/**
 * 微信菜单树对象
 * @author Administrator
 *
 */
public class WXMenuTree {
	
	private Integer id;
	private String name;
	private Integer mid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
	public WXMenuTree(Integer id, String name, Integer mid) {
		super();
		this.id = id;
		this.name = name;
		this.mid = mid;
	}
	
	public WXMenuTree() {
	}
	@Override
	public String toString() {
		return "WXMenuTree [id=" + id + ", name=" + name + ", mid=" + mid + "]";
	}
	
	
	
}
