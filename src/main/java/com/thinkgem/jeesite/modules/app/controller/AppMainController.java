package com.thinkgem.jeesite.modules.app.controller;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengt on 2017-8-21.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/main")
public class AppMainController extends BaseController{

    @Autowired
    private SystemService systemService;

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
}

