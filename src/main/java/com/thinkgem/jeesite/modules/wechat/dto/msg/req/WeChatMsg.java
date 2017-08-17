package com.thinkgem.jeesite.modules.wechat.dto.msg.req;

import com.thinkgem.jeesite.modules.wechat.dto.msg.WeXinMsg;

/**
 * 消息基类（普通用户 -> 公众帐号）
 */
public class WeChatMsg extends WeXinMsg{
    // 消息id，64位整型  
    private long MsgId;
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}  
}
