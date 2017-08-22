/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.attendance.entity.Orbit;
import com.thinkgem.jeesite.modules.attendance.dao.OrbitDao;

/**
 * 考勤轨迹类Service
 * @author lhc
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class OrbitService extends CrudService<OrbitDao, Orbit> {

	public Orbit get(String id) {
		return super.get(id);
	}
	
	public List<Orbit> findList(Orbit orbit) {
		return super.findList(orbit);
	}
	
	public Page<Orbit> findPage(Page<Orbit> page, Orbit orbit) {
		return super.findPage(page, orbit);
	}
	
	@Transactional(readOnly = false)
	public void save(Orbit orbit) {
		super.save(orbit);
	}
	
	@Transactional(readOnly = false)
	public void delete(Orbit orbit) {
		super.delete(orbit);
	}
	
}