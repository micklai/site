/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysPushMessage;
import com.thinkgem.jeesite.modules.sys.dao.SysPushMessageDao;

/**
 * 消息推送类Service
 * @author lhc
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class SysPushMessageService extends CrudService<SysPushMessageDao, SysPushMessage> {

	public SysPushMessage get(String id) {
		return super.get(id);
	}
	
	public List<SysPushMessage> findList(SysPushMessage sysPushMessage) {
		return super.findList(sysPushMessage);
	}
	
	public Page<SysPushMessage> findPage(Page<SysPushMessage> page, SysPushMessage sysPushMessage) {
		return super.findPage(page, sysPushMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(SysPushMessage sysPushMessage) {
		super.save(sysPushMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysPushMessage sysPushMessage) {
		super.delete(sysPushMessage);
	}
	
}