/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cwa.entity.Orbit;
import com.thinkgem.jeesite.modules.cwa.dao.OrbitDao;

/**
 * 考勤轨道记录Service
 * @author lhc
 * @version 2017-08-22
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
		Date begin = orbit.getBeginOrbitTime();
		Date end = orbit.getEndOrbitTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(begin == null || "".equals(begin)){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MONTH,c.get(Calendar.MONTH)-1);
			try {
				Date monthAgo = c.getTime();
				orbit.setBeginOrbitTime(monthAgo);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		if(end == null || "".equals(end)){
			Date now = new Date();
			orbit.setEndOrbitTime(now);
		}
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