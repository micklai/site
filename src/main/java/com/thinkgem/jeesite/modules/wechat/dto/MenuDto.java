package com.thinkgem.jeesite.modules.wechat.dto;

import java.util.List;


/**
 * 菜单
 */
public class MenuDto {
	public static final String ROOT_NAME = "微信菜单";
	public static final int ROOT_ID = 0;
	
	private List<WeChatButton> button;  
	  
    public List<WeChatButton> getButton() {  
        return button;  
    }  
  
    public void setButton(List<WeChatButton> button) {  
        this.button = button;  

    }
    
    
}
