/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.cwa.entity.Sign;
import com.thinkgem.jeesite.modules.cwa.service.SignService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private SystemService systemService;

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
	@RequestMapping(value = "")
	public String index(Sign sign ,Model model){
		return "modules/cwa/index";
	}

	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value = {"list"})
	public String list(Sign sign, HttpServletRequest request, HttpServletResponse response, Model model) {
		sign.getSqlMap().put("dsf",signService.dataScopeFilter(UserUtils.getUser(),"o","u2"));
		Page<Sign> page = new Page<Sign>(request,response);
		sign.setPage(page);
		List<Sign> signs = signService.findList(sign);
		page.setList(signs);
		model.addAttribute("page",page);
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

	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value = "unSignList")
	public String unSignList(User user, Model model,HttpServletRequest request,HttpServletResponse response){
		List<User> users = systemService.findUser(new User());
		List<Sign> signs = signService.findTodaySignList();
		List<User> userList = new ArrayList<User>();
		int num = users.size();
		for(User userInfo : users){
			boolean flag = false;
			for(Sign signInfo : signs){
				if(userInfo.getId().equals(signInfo.getUser().getId())){
					flag = true;
					break;
				}
			}
			if(!flag){
				userList.add(userInfo);
			}
		}
		model.addAttribute("userList",userList);
		addMessage(model, "默认显示当前时间今天所属员工未签到或签到异常一共："+num+"人");
		return "modules/cwa/unSignList";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		return null;
	}

	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value = "personSign")
	public String personSignIndex(Sign sign){
		return "modules/cwa/psersonSignIndex";
	}

	@ResponseBody
	@RequiresPermissions("cwa:sign:view")
	@RequestMapping(value="findPersonSignByMonth")
	public List<Map<String,Object>> findPersonSignByMonth(Sign sign,HttpServletRequest request,HttpServletResponse response){
		return signService.findPersonSignByMonth(sign);
	}
}