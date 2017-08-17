package com.thinkgem.jeesite.modules.wechat.web;
/*package com.thinkgem.jeesite.modules.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.wechat.dto.WXMenuTree;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;
import com.thinkgem.jeesite.modules.wechat.model.Menu;
import com.thinkgem.jeesite.modules.wechat.service.ProblemService;
import com.thinkgem.jeesite.modules.wechat.service.WXMenuService;
import com.thinkgem.jeesite.modules.wechat.service.WeChatService;

@RequestMapping("/admin/weixin")
@Controller
public class MenuTreeController {
	
	@Autowired
	private WXMenuService wxMenuService;
	
	@Autowired
	private WeChatService weChatService;
	
	@Autowired
	private ProblemService problemService;
	
	*//**
	 * 创建自定义菜单
	 * @param model 控制器通过Model把数据传到view层
	 * @param req	
	 * @param resp
	 * @return 
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/create/menu" })
	public String createMenu(Model model ,HttpServletRequest req ,HttpServletResponse resp){
		System.out.println("进入方法");
		
		
		String msg = weChatService.createMenu();
		//返回json数据
		return JsonUtil.getInstance().obj2json(msg);
	}
	
	@RequestMapping("/menus")
	public String list(Model model) {
		List<WXMenuTree> menuTree= wxMenuService.generateTree();
		model.addAttribute("treeDatas", JsonUtil.getInstance().obj2json(menuTree));
		return "wechat/list";
	
	}
	

	
	@RequestMapping("/treeAll")
	public @ResponseBody List<WXMenuTree> tree() {
		List<WXMenuTree> treeList = wxMenuService.generateTree();
		return treeList;
	}
	
	
	@RequestMapping(value="/treeAs",method=RequestMethod.POST)
	public @ResponseBody List<TreeDto> tree(@Param Integer mid) {
		List<TreeDto> tds = new ArrayList<TreeDto>();
		if(mid==null||mid<0) {
			tds.add(new TreeDto(0,"微信根菜单",1));
			return tds;
		}
		
		List<WXMenuTree> cts = wxMenuService.generateTreeByParent(mid);
		for(WXMenuTree ct:cts) {
			if(ct.getMid()==null){
				tds.add(new TreeDto(ct.getId(),ct.getName(),1));
			}else{
				tds.add(new TreeDto(ct.getId(),ct.getName(),0));
			}
		}
		return tds;
	}
	
	
	@RequestMapping("/menus/updateSort")
	public @ResponseBody AjaxObj updateSort(@Param Integer[] ids) {
		try {
			wxMenuService.updateSort(ids);
		} catch (Exception e) {
			return new AjaxObj(0,e.getMessage());
		}
		return new AjaxObj(1);
	}
	
	
	@RequestMapping("/menus/{id}")
	public String listChild(@PathVariable Integer id,@Param Integer refresh,Model model) {
		Menu pm = null;
		if(refresh==null) {
			model.addAttribute("refresh",0);
		} else {
			model.addAttribute("refresh",1);
		}
		
		int mid = 0;
		if(id==0){
			mid=id;	
		}else{			
			Menu tm = wxMenuService.load(id);
			if (tm.getParent() != null) {
				mid = tm.getParent().getId();

			} else {
				mid = id;
			}
		}
		
		if(id==null||id<=0) {
			pm = new Menu();
			pm.setName(Menu.ROOT_NAME);
			pm.setId(Menu.ROOT_ID);
		} else{
			pm = wxMenuService.load(id);
		}
		model.addAttribute("pm", pm);
		model.addAttribute("menus",wxMenuService.listByParent(id));
		
		model.addAttribute("mid", mid);
		
		return "wechat/list_child";
	}
	

	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model) {
		Menu m = wxMenuService.load(id);
		model.addAttribute("menu", m);
		Menu pm = null;
		if(m.getParent()==null) {
			pm = new Menu();
			pm.setId(Menu.ROOT_ID);
			pm.setName(Menu.ROOT_NAME);
		} else {
			pm = m.getParent();
		}
		model.addAttribute("pc",pm);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(WXMenuType.class, "name"));
		return "wechat/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,Menu menu,BindingResult br,Model model) {
		if(br.hasErrors()) {
			model.addAttribute("types", EnumUtils.enumProp2NameMap(WXMenuType.class, "name"));	
			return "wechat/update";
		}
		Menu tm = wxMenuService.load(id);
		int mid = 0;
		if(tm.getParent()!=null) {
			mid = tm.getParent().getId();
		}
		tm.setId(menu.getId());
		tm.setMenuKey(menu.getMenuKey());
		tm.setName(menu.getName());
		tm.setOrders(menu.getOrders());
		tm.setType(menu.getType());
		tm.setUrl(menu.getUrl());
	
		wxMenuService.update(tm);
		//indexService.generateTop();
		return "redirect:/admin/weixin/menus/"+mid+"?refresh=1";
	}

	@RequestMapping("/delete/{mid}/{id}")
	public String delete(@PathVariable Integer mid,@PathVariable Integer id,Model model) {
		wxMenuService.delete(id);
		//indexService.generateTop();
		return "redirect:/admin/weixin/menus/"+mid+"?refresh=1";
	}
	
	private void initAdd(Model model,Integer pid) {
		if(pid==null) pid = 0;
		Menu pm = null;
		if(pid==0) {
			pm = new Menu();
			pm.setId(Menu.ROOT_ID);
			pm.setName(Menu.ROOT_NAME);
		} else {
			pm = wxMenuService.load(pid);
		}
		model.addAttribute("pm", pm);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(WXMenuType.class, "name"));
	}
	
	@RequestMapping(value="/add/{mid}",method=RequestMethod.GET)
	public String add(@PathVariable Integer mid,Model model) {
		initAdd(model, mid);
		model.addAttribute(new Menu());
		return "wechat/add";
	}
	
	@RequestMapping(value="/add/{mid}",method=RequestMethod.POST)
	public String add(@PathVariable Integer mid,Menu menu,BindingResult br,Model model) {
		if(br.hasErrors()) {
			initAdd(model, mid);
			return "wechat/add";
		}
		wxMenuService.add(menu, mid);
		//indexService.generateTop();
		return "redirect:/admin/weixin/menus/"+mid+"?refresh=1";
			
	}
	
	
	
	
}
*/