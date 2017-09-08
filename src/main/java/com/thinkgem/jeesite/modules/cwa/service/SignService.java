/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import net.sf.ehcache.constructs.nonstop.store.ExceptionOnTimeoutStore;
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
	
	public List<Sign> findList(Sign sign){
		Date beginTime = sign.getBeginSignTime();
		Date endTime = sign.getEndSignTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(beginTime == null || "".equals(beginTime)){
			beginTime = new Date();
			String begin = sdf.format(beginTime)+" 00:00:00";
			try {
				sign.setBeginSignTime(newSdf.parse(begin));
			}catch(Exception e){

			}
		}
		if(endTime == null || "".equals(endTime)){
				sign.setEndSignTime(new Date());
		}
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

	public List<Map<String,Object>> findPersonSignByMonth(Sign sign) {
		List<Map<String,Object>> list =  new ArrayList<Map<String, Object>>();
		sign.setUser(UserUtils.getUser());
		if(sign.getBeginSignTime() == null){
			sign.setBeginSignTime(new Date());
		}
		if(sign.getEndSignTime() == null){
			String beginMonth = DateUtils.getYear()+"-"+DateUtils.getMonth()+"-01"+" 00:00:00";
			try {
				sign.setEndSignTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginMonth));
			}catch(Exception e){}
		}
		List<Sign> signs = dao.findPersonSignByMonth(sign);
		for(Sign caseInfo : signs){
			Date signTime = caseInfo.getSignTime();
			Calendar c = Calendar.getInstance();
			c.setTime(signTime);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("day",c.get(Calendar.DAY_OF_MONTH));
			map.put("sign",caseInfo);
			list.add(map);
		}
		return list;
	}

//	public List<Sign> findOfficeSign(Sign sign,String id){
//		return dao.findOfficeSign(Sign sign,id);
//	}
}