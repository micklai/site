/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.attendance.entity.Sign;
import com.thinkgem.jeesite.modules.attendance.dao.SignDao;

/**
 * 考勤记录相关类Service
 * @author lhc
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class SignService extends CrudService<SignDao, Sign> {

	public Sign get(String id) {
		return super.get(id);
	}
	
	public List<Sign> findList(Sign sign) {
		return super.findList(sign);
	}
	
	public Page<Sign> findPage(Page<Sign> page, Sign sign) {
		return super.findPage(page, sign);
	}
	
	@Transactional(readOnly = false)
	public void save(Sign sign) {
		super.save(sign);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sign sign) {
		super.delete(sign);
	}
	
}