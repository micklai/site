package com.thinkgem.jeesite.modules.wechat.entity;


import org.hibernate.validator.constraints.NotEmpty;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;


/**
 * 微信菜单
 */
public class WechatMenu extends DataEntity<WechatMenu>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ROOT_NAME = "微信菜单";
	public static final int ROOT_ID = 0;
	/**
	 * 菜单名
	 */
	private String name;
	/**
	 * 菜单类型
	 */
	private WXMenuType type;
	/**
	 * 菜单跳转路径
	 */
	private String url;
	/**
	 * 菜单对应标识
	 */
	private String menuKey;
	/**
	 * 菜单的序号
	 */
	private int orders;
	/**
	 * 父类栏目
	 */
	private WechatMenu parent;
	
	@NotEmpty(message="栏目名称不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public WXMenuType getType() {
		return type;
	}

	public void setType(WXMenuType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}
	
	public WechatMenu getParent() {
		return parent;
	}

	public void setParent(WechatMenu parent) {
		this.parent = parent;
	}

	
	public WechatMenu() {
	} 
	

	public WechatMenu( String name, WXMenuType type, String url, String key, int orders, 
			WechatMenu parent) {
		super();
		this.name = name;
		this.type = type;
		this.url = url;
		this.menuKey = key;
		this.orders = orders;
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", menuKey=" + menuKey
				+ ", orders=" + orders + ", parent=" + parent + "]";
	}


	
	 
    
    
}
