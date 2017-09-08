package com.thinkgem.jeesite.modules.test.web;

import cn.jpush.api.JPushClient;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.SysPushMessage;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysPushMessageService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * Created by pengt on 2017-7-28.
 */
public class Test {
    private static String src = "rsa security";
    public static void main(String[] args){
        try {
            SysPushMessageService sysPushMessageService = SpringContextHolder.getBean(SysPushMessageService.class);
            SystemService systemService = SpringContextHolder.getBean(SystemService.class);
            User user = systemService.getUser("0843fb308ee44c058dfc9437574bf294");
            Office office = user.getOffice();
            SysPushMessage message = new SysPushMessage();
            message.setUser(user);
            message.setOffice(office);
            message.setMsgDate(new Date());
            message.setMsgContent("hjn明天上午开会");
            message.setTypeName("我的行程");
            message.setTypeId("1");

            sysPushMessageService.save(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void jdkRSA() throws Exception{
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        //2.执行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(src.getBytes());
        byte[] res = signature.sign();
        System.out.println("签名:"+ HexBin.encode(res));

        //3.验证签名
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(src.getBytes());
        boolean bool = signature.verify(res);
        System.out.println("验证1："+bool);



    }
}
