/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.SysPushMessage;
import com.thinkgem.jeesite.modules.sys.service.SysPushMessageService;

/**
 * 消息推送类Controller
 * @author lhc
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysPushMessage")
public class SysPushMessageController extends BaseController {

	@Autowired
	private SysPushMessageService sysPushMessageService;
	
	@ModelAttribute
	public SysPushMessage get(@RequestParam(required=false) String id) {
		SysPushMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysPushMessageService.get(id);
		}
		if (entity == null){
			entity = new SysPushMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysPushMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysPushMessage sysPushMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysPushMessage> page = sysPushMessageService.findPage(new Page<SysPushMessage>(request, response), sysPushMessage); 
		model.addAttribute("page", page);
		return "modules/sys/sysPushMessageList";
	}

	@RequiresPermissions("sys:sysPushMessage:view")
	@RequestMapping(value = "form")
	public String form(SysPushMessage sysPushMessage, Model model) {
		model.addAttribute("sysPushMessage", sysPushMessage);
		return "modules/sys/sysPushMessageForm";
	}

	@RequiresPermissions("sys:sysPushMessage:edit")
	@RequestMapping(value = "save")
	public String save(SysPushMessage sysPushMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysPushMessage)){
			return form(sysPushMessage, model);
		}
		sysPushMessageService.save(sysPushMessage);
		addMessage(redirectAttributes, "保存消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysPushMessage/?repage";
	}
	
	@RequiresPermissions("sys:sysPushMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(SysPushMessage sysPushMessage, RedirectAttributes redirectAttributes) {
		sysPushMessageService.delete(sysPushMessage);
		addMessage(redirectAttributes, "删除消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysPushMessage/?repage";
	}

}