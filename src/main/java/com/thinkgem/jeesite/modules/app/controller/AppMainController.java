package com.thinkgem.jeesite.modules.app.controller;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.SysPushMessage;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.SysPushMessageService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengt on 2017-8-21.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/main")
public class AppMainController extends BaseController{

    @Autowired
    private SystemService systemService;

    @Autowired
    private SysPushMessageService sysPushMessageService;

    @Autowired
    private DictService dictService;


    /**
     * app下载登录用户数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "loadUserInfo")
    public String loadUserInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String userId = request.getParameter("userId");
        if(null == userId || "".equals(userId)){
            resultMap.put("code",0);
            return renderString(response,resultMap);
        }
        User user = systemService.getUser(userId);
        if(null == user){
            resultMap.put("code",0);
            return renderString(response,resultMap);
        }
        String companyId = user.getCompany().getId();
        String companyName = user.getCompany().getName();
        String officeId = user.getOffice().getId();
        String officeName = user.getOffice().getName();

        resultMap.put("companyId",companyId);
        resultMap.put("companyName",companyName);
        resultMap.put("officeId",officeId);
        resultMap.put("officeName",officeName);
        resultMap.put("code",1);
        return renderString(response,resultMap);
    }

    /**
     * 下载系统消息类数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="downloadMsg")
    public String downloadMsg(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String userId = request.getParameter("userId");
        if(null == userId || "".equals(userId)){
            resultMap.put("code",0);
            return renderString(response,resultMap);
        }

        List<Map<String,String>> datas = new ArrayList<Map<String, String>>();
        List<SysPushMessage> messageList = sysPushMessageService.findListByUserId(userId);
        if(null != messageList && messageList.size() > 0){
            for (int i = 0;i < messageList.size() ;i++){
                Map<String,String> data = new HashMap<String,String>();
                SysPushMessage message = messageList.get(i);
                data.put("id",message.getId());
                data.put("typeName",message.getTypeName());
                data.put("msgContent",message.getMsgContent());
                data.put("msgDate", DateUtils.formatDate(message.getMsgDate(),"yyyy-MM-dd HH:mm:ss"));
                data.put("msgContent",message.getMsgContent());
                datas.add(data);

                //修改推送消息状态为已读
                message.setReceive(true);
                sysPushMessageService.update(message);
            }
        }
        resultMap.put("code",1);
        resultMap.put("datas",datas);
        return renderString(response,resultMap);
    }

    /**
     * app客户端下载系统字典数据
     * @param request
     * @param response
     */
    @RequestMapping(value = "downloadSysDict")
    public void downloadSysDict(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //字典类型
        String dictType = request.getParameter("dictType");
        Dict dict = new Dict();
        dict.setType(dictType);
        List<Dict> dictList = dictService.findList(dict);
        if(null == dictList){
            dictList = new ArrayList<Dict>();
        }
        resultMap.put("code",1);
        resultMap.put("datas",dictList);
        renderString(response,resultMap);
    }
}

