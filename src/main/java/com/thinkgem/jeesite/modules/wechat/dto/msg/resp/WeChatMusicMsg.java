package com.thinkgem.jeesite.modules.wechat.dto.msg.resp;

import com.thinkgem.jeesite.modules.wechat.dto.msg.WeChatMusic;

/**
 * 音乐消息 
 */
public class WeChatMusicMsg extends WeChatMsg{
	// 音乐  
    private WeChatMusic Music;

	public WeChatMusic getMusic() {
		return Music;
	}

	public void setMusic(WeChatMusic music) {
		Music = music;
	}
}
