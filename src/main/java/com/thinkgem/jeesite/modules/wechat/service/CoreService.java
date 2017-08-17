package com.thinkgem.jeesite.modules.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.modules.wechat.dto.msg.WeChatArticle;
import com.thinkgem.jeesite.modules.wechat.dto.msg.resp.WeChatNewsMsg;
import com.thinkgem.jeesite.modules.wechat.dto.msg.resp.WeChatTextMsg;
import com.thinkgem.jeesite.modules.wechat.service.impl.WeChatServiceImpl;
import com.thinkgem.jeesite.modules.wechat.util.WeChatMsgUtil;

public class CoreService {
	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = WeChatMsgUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            WeChatTextMsg respMsg = new WeChatTextMsg();  
            respMsg.setToUserName(fromUserName);  
            respMsg.setFromUserName(toUserName);  
            respMsg.setCreateTime(new Date().getTime());  
            respMsg.setMsgType(WeChatMsgUtil.RESP_MESSAGE_TYPE_TEXT);  
            respMsg.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
            }  
            // 图片消息  
            else if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
            	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";   
            }  
            // 地理位置消息  
            else if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
            	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";    
            }  
            // 链接消息  
            else if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_LINK)) { 
            	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
            }  
            // 音频消息  
            else if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_VOICE)) { 
            	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
            }  
            // 事件推送  
            else if (msgType.equals(WeChatMsgUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(WeChatMsgUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(WeChatMsgUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(WeChatMsgUtil.EVENT_TYPE_CLICK)) {  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
  
                    if (eventKey.equals("11")) {  
                        
                        // 创建图文消息  
                    	WeChatNewsMsg newsMessage = new WeChatNewsMsg();  
                        newsMessage.setToUserName(fromUserName);  
                        newsMessage.setFromUserName(toUserName);  
                        newsMessage.setCreateTime(new Date().getTime());  
                        newsMessage.setMsgType(WeChatMsgUtil.RESP_MESSAGE_TYPE_NEWS);  
                        newsMessage.setFuncFlag(0);  
                        
                        return getArticle(newsMessage);
                    } else if (eventKey.equals("12")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("13")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("14")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("21")) {
                    	//调用创建菜单方法
                    	WeChatServiceImpl weChatServiceImpl = new WeChatServiceImpl();
                        respContent = weChatServiceImpl.createMenu();  
                    } else if (eventKey.equals("22")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("23")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("24")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("25")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("31")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("32")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    } else if (eventKey.equals("33")) {  
                    	respContent = "您好,现在是测试阶段。暂时不支持问题反应！   真诚感谢您的关注 ";  
                    }  
                }  
            }  
  
            respMsg.setContent(respContent);  
            respMessage = WeChatMsgUtil.textMessageToXml(respMsg);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
    
    private static String getArticle(WeChatNewsMsg newsMessage){
        
    	List<WeChatArticle> articleList = new ArrayList<WeChatArticle>(); 
    	WeChatArticle article1 = new WeChatArticle();  
        article1.setTitle("高额融资背后的焦虑，陆金所将要走向何方？");  
        article1.setDescription("");  
        // 将图片置为空  
        article1.setPicUrl("http://g.hiphotos.baidu.com/news/crop%3D0%2C22%2C900%2C540%3Bw%3D638/sign=bcf8eb34968fa0ec6b883e4d1ba775dc/42a98226cffc1e1754aaa5364d90f603738de9a4.jpg");  
        article1.setUrl("http://junstapo.baijia.baidu.com/article/299772");  

        WeChatArticle article2 = new WeChatArticle();  
        article2.setTitle("大白打不过的邪恶机器人的现实版");  
        article2.setDescription("");  
        article2.setPicUrl("http://a.hiphotos.baidu.com/news/w%3D638/sign=15c57abc4010b912bfc1f5fdfbfcfcb5/7a899e510fb30f249a73d104cf95d143ac4b03cf.jpg");  
        article2.setUrl("http://firstbeam.baijia.baidu.com/article/299686");  

        WeChatArticle article3 = new WeChatArticle();  
        article3.setTitle("中国土豪遇见好莱坞，万达花230亿买了个爹");  
        article3.setDescription("");  
        article3.setPicUrl("http://a.hiphotos.baidu.com/news/w%3D638/sign=bb06fc797ff0f736d8fe4f023254b382/8601a18b87d6277f8e4df83e2f381f30e924fc5d.jpg");  
        article3.setUrl("http://b12.baijia.baidu.com/article/299644");  

        WeChatArticle article4 = new WeChatArticle();  
        article4.setTitle("新金融巨头纷纷融资，”新三极”寡头隐现变数颇多");  
        article4.setDescription("");  
        article4.setPicUrl("http://g.hiphotos.baidu.com/news/crop%3D0%2C1%2C535%2C321%3Bw%3D638/sign=6b0efc4d05087bf469a30da9cfe37b1d/d1a20cf431adcbef0092bd00abaf2edda3cc9f16.jpg");  
        article4.setUrl("http://liukuang.baijia.baidu.com/article/299542");  

        articleList.add(article1);  
        articleList.add(article2);  
        articleList.add(article3);  
        articleList.add(article4);  
        newsMessage.setArticleCount(articleList.size());  
        newsMessage.setArticles(articleList);  
        return WeChatMsgUtil.newsMessageToXml(newsMessage);  
    }
    
    public static void main(String[] args) {
    	 // 创建图文消息  
    	WeChatNewsMsg newsMessage = new WeChatNewsMsg();  
        newsMessage.setToUserName("fromUserName");  
        newsMessage.setFromUserName("toUserName");  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(WeChatMsgUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0);  
		System.out.println(getArticle(newsMessage));
		
		// 回复文本消息  
        WeChatTextMsg respMsg = new WeChatTextMsg();  
        respMsg.setToUserName("fromUserName");  
        respMsg.setFromUserName("toUserName");  
        respMsg.setCreateTime(new Date().getTime());  
        respMsg.setMsgType(WeChatMsgUtil.RESP_MESSAGE_TYPE_TEXT);  
        respMsg.setFuncFlag(0);  
        respMsg.setContent("respContent");  
        System.out.println(WeChatMsgUtil.textMessageToXml(respMsg));
	}
}