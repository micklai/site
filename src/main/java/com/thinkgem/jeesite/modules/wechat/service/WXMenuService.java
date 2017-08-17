package com.thinkgem.jeesite.modules.wechat.service;

import java.util.List;


import com.thinkgem.jeesite.modules.wechat.dto.WXMenuTree;
import com.thinkgem.jeesite.modules.wechat.entity.WechatMenu;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;

public interface WXMenuService {

	/**
	 * 添加菜单
	 * @param channel
	 * @param pid
	 */
	public void add(WechatMenu wechatMenu,Integer mid);
	/**
	 * 更新菜单
	 * @param channel
	 */
	public void update(WechatMenu wechatMenu);
	/**
	 * 删除菜单
	 * @param id
	 */
	public void delete(int id);
	
	
	public WechatMenu load(int id);
	/**
	 * 根据父亲id加载栏目，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去
	 * @param pid
	 * @return
	 */
	public List<WechatMenu> listByParent(Integer mid);
	
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 */
	public List<WXMenuTree> generateTree();
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表
	 * @param pid
	 * @return
	 */
	public List<WXMenuTree> generateTreeByParent(Integer mid);
	
	/**
	 * 根据类型获取菜单
	 * @return
	 */
	public List<WechatMenu> listByType(WXMenuType wxMenuType);
	
	/**
	 * 排序
	 * @param ids
	 */
	public void updateSort(Integer[] ids);
	
}
