/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.web;

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
import com.thinkgem.jeesite.modules.cwa.entity.Orbit;
import com.thinkgem.jeesite.modules.cwa.service.OrbitService;

/**
 * 考勤轨道记录Controller
 * @author lhc
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cwa/orbit")
public class OrbitController extends BaseController {

	@Autowired
	private OrbitService orbitService;
	
	@ModelAttribute
	public Orbit get(@RequestParam(required=false) String id) {
		Orbit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orbitService.get(id);
		}
		if (entity == null){
			entity = new Orbit();
		}
		return entity;
	}
	
	@RequiresPermissions("cwa:orbit:view")
	@RequestMapping(value = {"list", ""})
	public String list(Orbit orbit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Orbit> page = orbitService.findPage(new Page<Orbit>(request, response), orbit); 
		model.addAttribute("page", page);
		return "modules/cwa/orbitList";
	}

	@RequiresPermissions("cwa:orbit:view")
	@RequestMapping(value = "form")
	public String form(Orbit orbit, Model model) {
		model.addAttribute("orbit", orbit);
		return "modules/cwa/orbitForm";
	}

	@RequiresPermissions("cwa:orbit:edit")
	@RequestMapping(value = "save")
	public String save(Orbit orbit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orbit)){
			return form(orbit, model);
		}
		orbitService.save(orbit);
		addMessage(redirectAttributes, "保存轨道记录成功");
		return "redirect:"+Global.getAdminPath()+"/cwa/orbit/?repage";
	}
	
	@RequiresPermissions("cwa:orbit:edit")
	@RequestMapping(value = "delete")
	public String delete(Orbit orbit, RedirectAttributes redirectAttributes) {
		orbitService.delete(orbit);
		addMessage(redirectAttributes, "删除轨道记录成功");
		return "redirect:"+Global.getAdminPath()+"/cwa/orbit/?repage";
	}

}