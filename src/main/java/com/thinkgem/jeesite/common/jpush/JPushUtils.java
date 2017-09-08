package com.thinkgem.jeesite.common.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import com.thinkgem.jeesite.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.tools.jar.Main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengt on 2017-8-15.
 * 极光推送工具类
 */
public class JPushUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final static String APP_KEY = Global.getConfig("AppKey");
    private final static String MASTER_SECRET = Global.getConfig("MasterSecret");
    private JPushClient jpushClient;

    public JPushClient getJpushClient() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        return jpushClient;
    }

    /**
     * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
     * @return PushPayload
     */
    public static PushPayload buildPushObject_all_all_alert(String ALERT) {
        return PushPayload.alertAll(ALERT);
    }

    /**
     * 构建推送对象：所有平台，推送目标是别名为 alias，通知内容为 ALERT
     * @return PushPayload
     */
    public static PushPayload buildPushObject_all_alias_alert(String[] alias,String ALERT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    /**
     * 构建推送对象：平台是 Android与ios，目标是 tag 为 tags 的设备，内容是 Android与ios 通知 ALERT，并且标题为 TITLE
     * @return PushPayload
     */
    public static PushPayload buildPushObject_androidAndIos_tags_alertWithMessage(String[] tags, String ALERT, String TITLE, Map<String,String> extras,String msgTitle,String msgContent) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tags))
                .setNotification(Notification.android(ALERT, TITLE, extras))
                .setMessage(Message.newBuilder()
                            .setTitle(msgTitle)
                            .setMsgContent(msgContent)
                            .addExtras(extras)
                            .build())
                .build();
    }




    public void sendPush(PushPayload payload){
        jpushClient = this.getJpushClient();
        try {
            PushResult result = jpushClient.sendPush(payload);

            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());

        }
    }

    /**
     * 构建推送对象：推送内容包含SMS信息
     * @param content
     */
    public static void sendWithSMS(String content,String title,String msgContent,String[] alias){
        Logger logger = LoggerFactory.getLogger(JPushUtils.class);
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            SMS sms = SMS.content(content, 10);
            PushResult result = jpushClient.sendAndroidMessageWithAlias(title, msgContent, sms, alias);

        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void main(String[] args){
        JPushUtils utils = new JPushUtils();
        utils.sendPush(JPushUtils.buildPushObject_androidAndIos_tags_alertWithMessage(new String[]{"0843fb308ee44c058dfc9437574bf294"},"明天上午十点大合仓开会","我的行程",new HashMap<String,String>(),"这是消息标题","这是消息内容"));
        System.out.println("推送成功!");
    }
}
