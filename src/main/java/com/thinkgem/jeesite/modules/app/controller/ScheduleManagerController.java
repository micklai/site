package com.thinkgem.jeesite.modules.app.controller;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sm.entity.Schedule;
import com.thinkgem.jeesite.modules.sm.service.ScheduleService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pengt on 2017-8-31.
 * 日程管理controller
 */
@Controller
@RequestMapping(value="${adminPath}/app/sm")
public class ScheduleManagerController extends BaseController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SystemService systemService;

    /**
     * 保存我的日程
     * @param request
     * @param response
     */
    @RequestMapping(value="saveSchedule")
    public void saveSchedule(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String userId = request.getParameter("userId");
        String title = request.getParameter("title");
        String typeId = request.getParameter("typeId");
        String typeName = request.getParameter("typeName");
        String date = request.getParameter("date");
        String addr = request.getParameter("addr");
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");
        String remark = request.getParameter("remark");
        String visitName = request.getParameter("visitName");
        String autoFlag = request.getParameter("autoFlag");

        User user = systemService.getUser(userId);
        if(null == user){
            resultMap.put("code",0);
            renderString(response,resultMap);
            return;
        }

        Schedule schedule = new Schedule();
        schedule.setTypeId(typeId);
        schedule.setTypeName(typeName);
        schedule.setTitle(title);
        schedule.setTime(DateUtils.parseDate(date));
        schedule.setAddress(addr);
        schedule.setXPos(lon);
        schedule.setYPos(lat);
        schedule.setRemark(remark);
        schedule.setVisitName(visitName);
        schedule.setAutoFlag(autoFlag);
        schedule.setUser(user);
        try {
            scheduleService.save(schedule);
            resultMap.put("code",1);
            renderString(response,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",1);
            renderString(response,resultMap);
        }
    }

    /**
     * 下载日程信息 包括我的日程和下属日程两种类型的数据获取
     * @param request
     * @param response
     */
    @RequestMapping(value = "downloadScheduleList")
    public void downloadScheduleList(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String userId = request.getParameter("userId");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String downloadType = request.getParameter("downloadType");
        if(null == userId || "".equals(userId) || null == downloadType || "".equals(downloadType) || null == pageNo || "".equals(pageNo) ||"null".equals(pageNo) || null == pageSize || "".equals(pageSize)){
            resultMap.put("code",1);
            renderString(response,resultMap);
            return;
        }

        Map<String,Object> conditions = new HashMap<String,Object>();
        conditions.put("userId",userId);
        conditions.put("pageNo",Integer.parseInt(pageNo));
        conditions.put("pageSize",Integer.parseInt(pageSize));
        conditions.put("downloadType",downloadType);
        User user = systemService.getUser(userId);
        Page<Schedule> page = new Page<Schedule>(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
        //page.setPageNo(Integer.parseInt(pageNo));
        //page.setPageSize(Integer.parseInt(pageSize));
        Map<String,Object> map = scheduleService.pageListByConditon(page,user,conditions);

        resultMap.put("code",1);
        resultMap.put("datas",map.get("datas"));
        resultMap.put("count",map.get("count"));
        resultMap.put("lastDateTime",map.get("lastDateTime"));
        renderString(response,resultMap);
    }

    @RequestMapping(value = "loadScheduleById")
    public void loadScheduleById(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String userId = request.getParameter("userId");
        String id = request.getParameter("scheduleId");
        if(null == id || "".equals(id)){
            resultMap.put("code",0);
            renderString(response,resultMap);
            return;
        }
        Schedule schedule = scheduleService.get(id);
        if(null == schedule){
            resultMap.put("code",0);
            renderString(response,resultMap);
            return;
        }

        resultMap.put("code",1);
        resultMap.put("data",schedule);
        renderString(response,resultMap);

    }
}
