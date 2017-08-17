package com.thinkgem.jeesite.modules.wechat.dto;

/**
 * 普通按钮（子按钮）
 */
public class WeChatMenuButton extends WeChatButton{
	private String type;  
	private String key;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public WeChatMenuButton(String name,String type, String key) {
		super.setName(name);
		this.type = type;
		this.key = key;
	} 
	
}
