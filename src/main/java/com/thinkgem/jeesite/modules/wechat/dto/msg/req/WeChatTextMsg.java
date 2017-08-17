package com.thinkgem.jeesite.modules.wechat.dto.msg.req;



/**
 * 文本消息 
 */
public class WeChatTextMsg extends WeChatMsg{
	// 消息内容  
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
