package com.thinkgem.jeesite.modules.wechat.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.wechat.dto.WeChatUser;
import com.thinkgem.jeesite.modules.wechat.dto.WeixinOauth2Token;
import com.thinkgem.jeesite.modules.wechat.util.WeChatUtil;

/**
 * 微信js接口验证
 */
public class OAuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public OAuthServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");// 用户同意授权
		// 申请的测试号appid   appsecret
		System.out.println("dopost");
		System.out.println(code);
		WeChatUtil wcUtil = new WeChatUtil();
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = wcUtil.getOauth2AccessToken(code);
			System.out.println(weixinOauth2Token.getAccessToken());
			
			if(null == weixinOauth2Token){
				return;
			}
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			System.out.println(accessToken);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			WeChatUser wcu = wcUtil.getSNSUserInfo(accessToken, openId);// 设置要传递的参数
			System.out.println(wcu.getNickname());
			request.setAttribute("wcu", wcu);
		}
		System.out.println("---------------------------------------------------");
		// 跳转到index.jsp
		request.getRequestDispatcher("jsp/wechat/list.jsp").forward(request, response);
	}

	

	public void init() throws ServletException {
	}

}
