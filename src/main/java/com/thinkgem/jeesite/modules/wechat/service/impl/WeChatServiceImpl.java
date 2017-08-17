package com.thinkgem.jeesite.modules.wechat.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.wechat.dao.WXMenuDao;
import com.thinkgem.jeesite.modules.wechat.dto.AccessToken;
import com.thinkgem.jeesite.modules.wechat.dto.ComplexButton;
import com.thinkgem.jeesite.modules.wechat.dto.MenuDto;
import com.thinkgem.jeesite.modules.wechat.dto.ViewButton;
import com.thinkgem.jeesite.modules.wechat.dto.WeChatButton;
import com.thinkgem.jeesite.modules.wechat.dto.WeChatMenuButton;
import com.thinkgem.jeesite.modules.wechat.entity.WechatMenu;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;
import com.thinkgem.jeesite.modules.wechat.service.WeChatService;
import com.thinkgem.jeesite.modules.wechat.util.WeChatUtil;

@Service
@Transactional(readOnly = true)
public class WeChatServiceImpl implements WeChatService{
	
	@Autowired
	private WXMenuDao wxMenuDao;
	
	@Transactional(readOnly = false)
	public String createMenu(){
		MenuDto menuDto = getMenu();
		System.out.println(JsonMapper.toJsonString(menuDto));
		
		//获取accessToken
		AccessToken at = WeChatUtil.getAccessToken();
		String respMsgs;
		//创建微信响应文本消息
		if (null != at) {
			// 调用接口创建菜单
			System.out.println(at.getToken());
			int result = WeChatUtil.createMenu(menuDto, at.getToken()); 
			System.out.println(result);
			if(result!= 0){
				System.out.println("创建失败");
				respMsgs = "创建失败";
			}else{
				respMsgs = "创建成功";
			}
			return respMsgs;
		}else{
			respMsgs = "获取accessToken失败";
		}
		//把消息转换成xml 响应到微信
		return respMsgs;
		
	}
	
	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public MenuDto getMenu() {
				
		List<WechatMenu> m = wxMenuDao.loadAll();
		List<WechatMenu> midList = new ArrayList<WechatMenu>();
		int num = 0;
		for (WechatMenu wechatMenu : m) {
			if(wechatMenu.getParent()==null){
				midList.add(num, wechatMenu);
				num++;
			}
		}
		
		List<WeChatButton> menuBtnList = new ArrayList<WeChatButton>();
		if(midList.size()>0){
			for (int i = 0; i < midList.size(); i++) {
				WechatMenu parentMenu = midList.get(i);
				List<WeChatButton> wcbList = new ArrayList<WeChatButton>();
				//添加菜单按钮
				for (WechatMenu wechatMenu : m) {
					if(wechatMenu.getParent()!=null && wechatMenu.getParent().getId() == parentMenu.getId()){
						if(wechatMenu.getType().equals(WXMenuType.CLICK)){
							wcbList.add(new WeChatMenuButton(wechatMenu.getName(),"click",wechatMenu.getMenuKey()));
						}else if(wechatMenu.getType().equals(WXMenuType.VIEW)){
							String url =wechatMenu.getUrl();
							if(wechatMenu.getMenuKey().equals("encode")){
								url = WeChatRedirect(url, "snsapi_userinfo", WeChatUtil.appId);
							}
							wcbList.add(new ViewButton(wechatMenu.getName(),"view",url));
						}
					}
				}
				ComplexButton mainBtn = new ComplexButton();
				mainBtn.setName(parentMenu.getName());
				mainBtn.setSub_button(wcbList);
				menuBtnList.add(mainBtn);
			}
		}
		
		MenuDto menu = new MenuDto();
		menu.setButton(menuBtnList);
		
		return menu;
	}

	
	/**
	 * 
	 * @param redirectUrl
	 *            回调地址
	 * @param scope
	 *            应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
	 *            snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关
	 *            注的情况下，只要用户授权，也能获取其信息）
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @param appId
	 *            公众号的唯一标识
	 * @return encding 后的回调界面地址
	 */
	public static String WeChatRedirect(String redirectUrl, String scope, String appId) {
		try {
			Long state;
			state = new Date().getTime();
			String encRedirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appId
					+ "&redirect_uri=" + encRedirectUrl + "&response_type=code&" + "scope=" + scope + "&state=" + state
					+ "#wechat_redirect";
			System.out.println(requestUrl);
			return requestUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("获取回调地址失败");
		return "";
	}
}
