/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cwa.entity.Sign;
import com.thinkgem.jeesite.modules.cwa.dao.SignDao;

/**
 * 考勤管理相关Service
 * @author lhc
 * @version 2017-08-22
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

	public List<Sign> findTodaySignList(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(now)+" 00:00:00";
		return dao.findTodaySignList(time);
	}

//	public List<Sign> findOfficeSign(Sign sign,String id){
//		return dao.findOfficeSign(Sign sign,id);
//	}
}