package com.thinkgem.jeesite.modules.wechat.dto;

/** 
 * view类型的菜单 
 */ 
public class ViewButton extends WeChatButton{
	private String type;  
	private String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ViewButton(String name,String type, String url) {
		super.setName(name);
		this.type = type;
		this.url = url;
	} 
	
	
}
