package com.thinkgem.jeesite.modules.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.wechat.dto.AccessToken;
import com.thinkgem.jeesite.modules.wechat.dto.MenuDto;
import com.thinkgem.jeesite.modules.wechat.dto.WeChatUser;
import com.thinkgem.jeesite.modules.wechat.dto.WeixinOauth2Token;


/**
 * 公众平台通用接口工具类
 */
public class WeChatUtil {
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 第三方用户唯一凭证
	public static String appId = "wx27c9cc21e58d602b";
	// 第三方用户唯一凭证密钥
	public static String appSecret = "a9df7d5a88e02a6cda4996f7520b035a";
	/**
	 * 基础 ACCESS_TOKEN
	 */
	public static String ACCESS_TOKEN = "0";

	/**
	 * ACCESS_TOKEN 到期时间
	 */
	public static long EXPIRESIN = 0;
	
	/**
	 * js验证需要 JSAPI_TICKET
	 */
	public static String JSAPI_TICKET = "0";
	
	/**
	 * js验证到期时间
	 */
	public static long JS_EXPIRESIN = 0;
	
	public static String OPENID = "";
	
	public static String SCOPE = "";
	
	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", appSecret);

		if (ACCESS_TOKEN.equalsIgnoreCase("0") || EXPIRESIN <= new Date().getTime()) {
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				accessToken = new AccessToken();
				ACCESS_TOKEN = jsonObject.getString("access_token");
				int expiresIn = jsonObject.getInt("expires_in");
				// 将获取到的expires_in转换为到期时间 为避免出错调整一下重新获取时间
				EXPIRESIN = getExpiresIn(expiresIn - 200);

				accessToken.setToken(ACCESS_TOKEN);
				accessToken.setExpiresIn(expiresIn);
			} else {
				System.out.println("获取数据出错 请检查appid appsecret");
			}
		}
		accessToken = new AccessToken();
		accessToken.setToken(ACCESS_TOKEN);
		accessToken.setExpiresIn(7200);
		return accessToken;
	}

	/**
	 * 获取	jsapi_ticket
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static String getJSapiTicket() {
		AccessToken accessToken = null;
		accessToken = getAccessToken();
		if(JSAPI_TICKET.equalsIgnoreCase("0") || JS_EXPIRESIN <= new Date().getTime()){
			if(null != accessToken){
				String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken.getToken());
				JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
				// 如果请求成功
				if (null != jsonObject) {
					JSAPI_TICKET = jsonObject.getString("ticket");
					int expiresIn = jsonObject.getInt("expires_in");
					// 将获取到的expires_in转换为到期时间 为避免出错调整一下重新获取时间
					JS_EXPIRESIN = getExpiresIn(expiresIn - 200);
				} else {
					System.out.println("获取数据出错 请检查appid appsecret");
				}
			}else{
				System.out.println("获取数据出错 accessToken 出错");
			}
		}
		
		return JSAPI_TICKET;
	}
	
	
	/**
	 * 重置时间验证
	 */
	public static long getExpiresIn(int expiresIn) {
		Long time = new Date().getTime() + expiresIn * 1000;
		return time;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(MenuDto menu, String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JsonMapper.toJsonString(menu);
		System.out.println(jsonMenu);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
			}
		}
		System.out.println(result);
		return result;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new HttpsX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			System.out.println("进入");
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			System.out.println("设置");
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			System.out.println("连接成功");
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			//TODO 转换可能错误
			jsonObject = (JSONObject) JsonMapper.fromJsonString(buffer.toString(), JSONObject.class);
			
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

	/**
	 * 
	 * @param redirectUrl
	 *            回调地址
	 * @param scope
	 *            应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
	 *              snsapi_userinfo（弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关
	 *            注的情况下，只要用户授权，也能获取其信息）
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @param appId
	 *            公众号的唯一标识
	 * @return encding 后的回调界面地址
	 */
	public static String WeChatRedirect(String redirectUrl, String scope, Long state) {
		// "https://www.cdhncy.com/OAuthServlet";
		try {
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

	/*----------------------------网页端--------------------------------------------------*/
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public WeixinOauth2Token getOauth2AccessToken( String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		
			// 获取网页授权凭证
			JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				try {
					OPENID = jsonObject.getString("openid");
					SCOPE = jsonObject.getString("scope");
					wat = new WeixinOauth2Token();
					wat.setAccessToken(jsonObject.getString("access_token"));
					wat.setExpiresIn(jsonObject.getInt("expires_in"));
					wat.setRefreshToken(jsonObject.getString("refresh_token"));
					wat.setOpenId(OPENID);
					wat.setScope(SCOPE);
				} catch (Exception e) {
					wat = null;
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					System.out.println("errorCode:"+errorCode+"-----"+"errorMsg:"+errorMsg);
				}
			}
		return wat;
	}
	
	/**
	 * 通过网页授权获取用户信息
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return WeChatUser
	 */
	@SuppressWarnings("unchecked")
	public WeChatUser getSNSUserInfo(String oauthAccessToken, String openId) {
		WeChatUser wcu = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", oauthAccessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				wcu = new WeChatUser();
				// 用户的标识
				wcu.setOpenId(jsonObject.getString("openid"));
				// 昵称
				wcu.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				wcu.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				wcu.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				wcu.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				wcu.setCity(jsonObject.getString("city"));
				// 用户头像
				wcu.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				JSONArray jsonArray = jsonObject.getJSONArray("privilege");
				//TODO 转换可能错误
				wcu.setPrivilegeList((List<String>)JsonMapper.fromJsonString(jsonArray.toString(), List.class));
			} catch (Exception e) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("errorCode:"+errorCode+"-----"+"errorMsg:"+errorMsg);
			}
		}
		return wcu;
	}
	
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new HttpsX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			//TODO 转换可能错误
			jsonObject = (JSONObject) JsonMapper.fromJsonString(buffer.toString(), JSONObject.class);
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

}
