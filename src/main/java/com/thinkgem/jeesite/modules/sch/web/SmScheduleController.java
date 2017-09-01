/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sch.web;

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
import com.thinkgem.jeesite.modules.sch.entity.SmSchedule;
import com.thinkgem.jeesite.modules.sch.service.SmScheduleService;

/**
 * 日程管理类Controller
 * @author lhc
 * @version 2017-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sch/smSchedule")
public class SmScheduleController extends BaseController {

	@Autowired
	private SmScheduleService smScheduleService;
	
	@ModelAttribute
	public SmSchedule get(@RequestParam(required=false) String id) {
		SmSchedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smScheduleService.get(id);
		}
		if (entity == null){
			entity = new SmSchedule();
		}
		return entity;
	}
	
	@RequiresPermissions("sch:smSchedule:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmSchedule smSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmSchedule> page = smScheduleService.findPage(new Page<SmSchedule>(request, response), smSchedule); 
		model.addAttribute("page", page);
		return "modules/sch/smScheduleList";
	}

	@RequiresPermissions("sch:smSchedule:view")
	@RequestMapping(value = "form")
	public String form(SmSchedule smSchedule, Model model) {
		model.addAttribute("smSchedule", smSchedule);
		return "modules/sch/smScheduleForm";
	}

	@RequiresPermissions("sch:smSchedule:edit")
	@RequestMapping(value = "save")
	public String save(SmSchedule smSchedule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smSchedule)){
			return form(smSchedule, model);
		}
		smScheduleService.save(smSchedule);
		addMessage(redirectAttributes, "保存添加日志管理成功");
		return "redirect:"+Global.getAdminPath()+"/sch/smSchedule/?repage";
	}
	
	@RequiresPermissions("sch:smSchedule:edit")
	@RequestMapping(value = "delete")
	public String delete(SmSchedule smSchedule, RedirectAttributes redirectAttributes) {
		smScheduleService.delete(smSchedule);
		addMessage(redirectAttributes, "删除添加日志管理成功");
		return "redirect:"+Global.getAdminPath()+"/sch/smSchedule/?repage";
	}

}