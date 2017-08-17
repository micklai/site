package com.thinkgem.jeesite.modules.wechat.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.modules.wechat.dao.WXMenuDao;
import com.thinkgem.jeesite.modules.wechat.dto.WXMenuTree;
import com.thinkgem.jeesite.modules.wechat.entity.WechatMenu;
import com.thinkgem.jeesite.modules.wechat.service.WeChatService;

public class WXMenuDaoTest extends BaseTestCase{
	
	@Autowired
	private WXMenuDao wXMenuDao;
	
	@Autowired
	private WeChatService weChatService;

	@Test
	public void testListByParent() {
		List<WechatMenu> menuList = wXMenuDao.listByParent(1);
		for (WechatMenu wechatMenu : menuList) {
			System.out.println(wechatMenu);
		}
	}

	@Test
	public void testGetMaxOrderByParent() {
		int shot = wXMenuDao.getMaxOrderByParent(1);
		System.out.println(shot);
	}

	@Test
	public void testGenerateTree() {
		List<WXMenuTree> menuTree = wXMenuDao.generateTree();
		for (WXMenuTree wxMenuTree : menuTree) {
			System.out.println(wxMenuTree);
		}
	}

	@Test
	public void testGenerateTreeByParent() {
		List<WXMenuTree> menuTree = wXMenuDao.generateTreeByParent(1);
		for (WXMenuTree wxMenuTree : menuTree) {
			System.out.println(wxMenuTree);
		}
	}
	@Test
	public void testWxService(){
		weChatService.createMenu();
		
		
	}


}
