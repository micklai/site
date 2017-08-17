package com.thinkgem.jeesite.modules.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.wechat.dao.WXMenuDao;
import com.thinkgem.jeesite.modules.wechat.dto.WXMenuTree;
import com.thinkgem.jeesite.modules.wechat.entity.WechatMenu;
import com.thinkgem.jeesite.modules.wechat.enums.WXMenuType;
import com.thinkgem.jeesite.modules.wechat.service.WXMenuService;

@Service
@Transactional(readOnly = true)
public class WXMenuServiceImpl implements WXMenuService {

	@Resource
	private WXMenuDao wxMenuDao;

	@Transactional(readOnly = false)
	public void add(WechatMenu wechatMenu, Integer mid) {
		Integer orders = wxMenuDao.getMaxOrderByParent(mid);
		if (mid != null && mid > 0) {
			WechatMenu pm = wxMenuDao.get(mid.toString());
			wechatMenu.setParent(pm);
		}
		wechatMenu.setOrders(orders + 1);
		wxMenuDao.insert(wechatMenu);
	}

	@Transactional(readOnly = false)
	public void update(WechatMenu wechatMenu) {
		wxMenuDao.update(wechatMenu);
	}

	@Transactional(readOnly = false)
	public void delete(int id) {
		// 1、需要判断是否存在子栏目
		List<WechatMenu> cs = wxMenuDao.listByParent(id);
		if (cs != null && cs.size() > 0) {
		}
		wxMenuDao.delete(String.valueOf(id));
	}

	@Override
	public WechatMenu load(int id) {
		return wxMenuDao.get(String.valueOf(id));
	}

	@Override
	public List<WechatMenu> listByParent(Integer mid) {
		return wxMenuDao.listByParent(mid);
	}

	@Override
	public List<WXMenuTree> generateTree() {
		return wxMenuDao.generateTree();
	}

	@Override
	public List<WXMenuTree> generateTreeByParent(Integer mid) {
		return wxMenuDao.generateTreeByParent(mid);
	}

	@Override
	public List<WechatMenu> listByType(WXMenuType wxMenuType) {
		return wxMenuDao.listByType(wxMenuType);
	}

	@Transactional(readOnly = false)
	public void updateSort(Integer[] ids) {
		wxMenuDao.updateSort(ids);
	}

}
