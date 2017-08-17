package com.thinkgem.jeesite.modules.wechat.dto.msg.resp;

import java.util.List;

import com.thinkgem.jeesite.modules.wechat.dto.msg.WeChatArticle;



/**
 * 响应消息之图文消息
 */
public class WeChatNewsMsg extends WeChatMsg{
	// 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<WeChatArticle> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<WeChatArticle> getArticles() {
		return Articles;
	}
	public void setArticles(List<WeChatArticle> articles) {
		Articles = articles;
	} 
}
