package com.thinkgem.jeesite.modules.test.web;

import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by pengt on 2017-7-27.
 */
@Controller
@RequestMapping(value = "app/test")
public class AppController extends BaseController{

    @RequestMapping(value="oa")
    @ResponseBody
    public String test(HttpServletRequest request){
        System.out.println("app访问成功");
        Enumeration en = request.getHeaderNames();
        while (en.hasMoreElements()){
            String key = (String)en.nextElement();
            String value = request.getHeader(key);
            System.out.println("key="+key+",value="+value);
        }
        return "error";
    }
}
