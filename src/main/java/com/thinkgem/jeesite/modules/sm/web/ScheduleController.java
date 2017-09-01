/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sm.entity.Schedule;
import com.thinkgem.jeesite.modules.sm.service.ScheduleService;

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
	@RequestMapping(value = {"list", "index"})
	public String list(Schedule schedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Schedule> page = scheduleService.findPage(new Page<Schedule>(request, response), schedule); 
		model.addAttribute("page", page);
		return "modules/sm/scheduleList";
	}

	@RequiresPermissions("sm:schedule:view")
	@RequestMapping(value = "form")
	public String form(Schedule schedule, Model model) {
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
		addMessage(redirectAttributes, "保存日程管理成功");
		return "redirect:"+Global.getAdminPath()+"/sm/schedule/?repage";
	}
	
	@RequiresPermissions("sm:schedule:edit")
	@RequestMapping(value = "delete")
	public String delete(Schedule schedule, RedirectAttributes redirectAttributes) {
		scheduleService.delete(schedule);
		addMessage(redirectAttributes, "删除日程管理成功");
		return "redirect:"+Global.getAdminPath()+"/sm/schedule/?repage";
	}

}