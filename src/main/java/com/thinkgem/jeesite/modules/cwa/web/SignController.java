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
import com.thinkgem.jeesite.modules.cwa.entity.Sign;
import com.thinkgem.jeesite.modules.cwa.service.SignService;

/**
 * 考勤管理相关Controller
 * @author lhc
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cwa/sign")
public class SignController extends BaseController {

	@Autowired
	private SignService signService;
	
	@ModelAttribute
	public Sign get(@RequestParam(required=false) String id) {
		Sign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signService.get(id);
		}
		if (entity == null){
			entity = new Sign();
		}
		return entity;
	}
	
	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sign sign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sign> page = signService.findPage(new Page<Sign>(request, response), sign); 
		model.addAttribute("page", page);
		return "modules/cwa/signList";
	}

	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value = "form")
	public String form(Sign sign, Model model) {
		model.addAttribute("sign", sign);
		return "modules/cwa/signForm";
	}

	@RequiresPermissions("cwa:sign:edit")
	@RequestMapping(value = "save")
	public String save(Sign sign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sign)){
			return form(sign, model);
		}
		signService.save(sign);
		addMessage(redirectAttributes, "保存考勤管理成功");
		return "redirect:"+Global.getAdminPath()+"/cwa/sign/?repage";
	}
	
	@RequiresPermissions("cwa:sign:edit")
	@RequestMapping(value = "delete")
	public String delete(Sign sign, RedirectAttributes redirectAttributes) {
		signService.delete(sign);
		addMessage(redirectAttributes, "删除考勤管理成功");
		return "redirect:"+Global.getAdminPath()+"/cwa/sign/?repage";
	}

}