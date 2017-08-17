package com.thinkgem.jeesite.modules.wechat.dto.msg.resp;

import com.thinkgem.jeesite.modules.wechat.dto.msg.WeXinMsg;

/**
 * 微信响应消息
 */
public class WeChatMsg extends WeXinMsg{
	// 位0x0001被标志时，星标刚收到的消息  
    private int FuncFlag;

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}  
}
