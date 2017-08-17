package com.thinkgem.jeesite.modules.wechat.dto;

import java.util.List;

/** 
 * 带有2级菜单的按钮（父按钮） 
 */ 
public class ComplexButton extends WeChatButton{
	private List<WeChatButton> sub_button;  
	  
	public List<WeChatButton> getSub_button() {  
		return sub_button;  
	}  
	  
	public void setSub_button(List<WeChatButton> wcbList) {  
		this.sub_button = wcbList;  
	}

}
