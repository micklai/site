/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.thinkgem.jeesite.modules.wechat.enums;

public enum WXMenuType {
	
	VIEW("跳转网页"),TEXT("返回文本"),
	IMG("返回图片"),VOICE("返回声音"),
	PHOTO("返回photo"),VIDEO("返回视频"),
	CLICK("点击事件");
	
	private String name;
	
	private WXMenuType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
