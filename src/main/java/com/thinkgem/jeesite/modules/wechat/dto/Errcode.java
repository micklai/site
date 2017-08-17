package com.thinkgem.jeesite.modules.wechat.dto;

/**
 * 全局返回码 
 * @author Administrator
 *
 */
public enum Errcode {
	
	SYSTEMBUSY(-1,"系统繁忙，此时请开发者稍候再试"),
	
	SUCCESS(0,"请求成功"),
	
	GET_ACCESS_TOKEN_ERR(40001,"获取access_token时AppSecret错误，或者access_token无效。"),
	
	CERTIFICATE_ERR(40002,"不合法的凭证类型"),
	
	OPENID_ERR(40003,"不合法的OpenID，请确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID"),
	
	APPID_ERR(40013,"不合法的AppID，请检查AppID的正确性，避免异常字符，注意大小写"),
	
	ACCESS_TOKEN_ERR(40014,"不合法的access_token，请认真比对access_token的有效性（如是否过期）"),
	
	ACCESS_TOKEN_ERR1(42001,"不合法的access_token，请认真比对access_token的有效性（如是否过期）");
	
	
	private int errCode;
	
	
	private String errStr;

	private Errcode(int errCode, String errStr) {
		this.errCode = errCode;
		this.errStr = errStr;
	}
	
	public int getErrCode() {
		return errCode;
	}

	public String getErrStr() {
		return errStr;
	}
	
	public static Errcode errCodeOf(int index) {
		for (Errcode errCode : values()) {
			if (errCode.errCode == index) {
				return errCode;
			}
		}
		return null;
	}
}
