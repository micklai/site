package com.thinkgem.jeesite.modules.wechat.dto.msg.req;

/**
 * 微信图片消息
 */
public class WeChatImageMsg extends WeChatMsg{
	// 图片链接  
    private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	} 
}
