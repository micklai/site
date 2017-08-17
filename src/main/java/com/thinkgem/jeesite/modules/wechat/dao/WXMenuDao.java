package com.thinkgem.jeesite.modules.wechat.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wechat.dto.WXMenuTree;
import com.thinkgem.jeesite.modules.wechat.entity.WechatMenu;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;

@MyBatisDao
public interface WXMenuDao extends CrudDao<WechatMenu>{
	/**
	 * 根据父id获取所有的子栏目
	 * @param pid
	 * @return
	 */
	public List<WechatMenu> listByParent(Integer mid);
	/**
	 * 获取子栏目的最大的排序号
	 * @param pid
	 * @return
	 */
	public int getMaxOrderByParent(Integer mid);
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
	 * 根据类型获取菜单列表
	 * @return
	 */
	public List<WechatMenu> listByType(WXMenuType wxMenuType);
	
	/**
	 * 通过一个数组来完成排序
	 * @param ids
	 */
	public void updateSort(Integer[] ids);
	
	/**
	 * 查询所有
	 * */
	public List<WechatMenu> loadAll();
	
}
