/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.sm.entity.ScheduleAttachment;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.http.HttpResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sm.entity.Schedule;
import com.thinkgem.jeesite.modules.sm.service.ScheduleService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日程管理类Controller
 * @author lhc
 * @version 2017-09-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sm/schedule")
public class ScheduleController extends BaseController {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Schedule get(@RequestParam(required=false) String id) {
		Schedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scheduleService.get(id);
		}
		if (entity == null){
			entity = new Schedule();
		}
		return entity;
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = "all/index")
	public String index(Schedule schedule,Model model){
		model.addAttribute("schedule", schedule);
		return "modules/sm/scheduleIndex";
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = {"all/list"})
	public String list(Schedule schedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(schedule.getBeginScheduleTime() == null){
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(now)+" 00:00:00";
			SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = null;
			try {
				today = newSdf.parse(str);
				schedule.setBeginScheduleTime(today);
			}catch(Exception e){}
		}
		if(schedule.getEndScheduleTime() == null){
			schedule.setEndScheduleTime(new Date());
		}
		schedule.getSqlMap().put("dsf", systemService.dataScopeFilter(UserUtils.getUser(), "o", "u2"));
		Page<Schedule> page = scheduleService.findPage(new Page<Schedule>(request, response), schedule);
		model.addAttribute("page", page);
		return "modules/sm/scheduleList";
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = "own/form")
	public String form(Schedule schedule, Model model) {
		List map = DictUtils.getDictList("act_category");
//		schedule = scheduleService.get(schedule);   //删除下一行，注解这行
		schedule = new Schedule();
		model.addAttribute("schedule", schedule);
		return "modules/sm/scheduleForm";
	}

	@RequiresPermissions("sm:schedule:edit")
	@RequestMapping(value = "save")
	public String save(Schedule schedule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, schedule)){
			return form(schedule, model);
		}
		scheduleService.save(schedule);
		addMessage(redirectAttributes, "保存日程成功!");
		return "redirect:"+Global.getAdminPath()+"/sm/schedule/all/index?repage";
	}
	
	@RequiresPermissions("sm:schedule:edit")
	@RequestMapping(value = "delete")
	public String delete(Schedule schedule, RedirectAttributes redirectAttributes) {
		scheduleService.delete(schedule);
		addMessage(redirectAttributes, "删除日程管理成功");
		return "redirect:"+Global.getAdminPath()+"/sm/schedule/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	@RequiresPermissions("user")
	public List<Map<String, Object>> treeData(){
		List<User> list = systemService.findAllAffiliationByOfficeId(UserUtils.getUser());
		List<Map<String,Object>> mapList = Lists.newArrayList();
		for(User user : list){
			Map map = Maps.newHashMap();
			map.put("id",user.getOffice().getId());
			map.put("pId",user.getOffice().getParentId());
//			map.put("pIds",user.getOffice().getParentIds());
			map.put("userId",user.getId());
			map.put("name",user.getName());
			mapList.add(map);
		}
//		System.out.print(mapList);
		return mapList;
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = {"own/index"})
	public String ownindex(Schedule schedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		schedule.setUser(UserUtils.getUser());
		Page<Schedule> page = scheduleService.findPage(new Page<Schedule>(request, response), schedule);
		model.addAttribute("page", page);
		return "modules/sm/ownScheduleList";
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = {"own/list"})
	public String ownList(Schedule schedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sm/ownScheduleList";
	}
}