package com.thinkgem.jeesite.modules.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.wechat.service.CoreService;
import com.thinkgem.jeesite.modules.wechat.util.TokenSignUtil;


/**
 * 响应来自微信服务器的Token Url验证请求
 */
public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public TokenServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	/**
	 * 验证微信Token
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");
        
        PrintWriter out = response.getWriter();  
        System.out.println("获取到"+signature+"-------"+timestamp+"-----------"+nonce+"---"+echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        boolean istrue = TokenSignUtil.checkSignature(signature, timestamp, nonce);
        System.out.println("获取到"+istrue);
        if (istrue) {  
            out.print(echostr);  
        }  
        out.close();  
        out = null;  
	}

	/**  
     * 处理微信服务器发来的消息  
     */ 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(request);  
          
        // 响应消息  
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();  
	}

	public void init() throws ServletException {
	}
	
}
