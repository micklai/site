/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.service;

import java.util.*;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sm.entity.Schedule;
import com.thinkgem.jeesite.modules.sm.dao.ScheduleDao;
import com.thinkgem.jeesite.modules.sm.entity.ScheduleAttachment;
import com.thinkgem.jeesite.modules.sm.dao.ScheduleAttachmentDao;

/**
 * 日程管理类Service
 * @author lhc
 * @version 2017-09-01
 */
@Service
@Transactional(readOnly = true)
public class ScheduleService extends CrudService<ScheduleDao, Schedule> {

	@Autowired
	private ScheduleAttachmentDao scheduleAttachmentDao;

	@Autowired
	private ScheduleDao scheduleDao;
	
	public Schedule get(String id) {
		Schedule schedule = super.get(id);
		schedule.setScheduleAttachmentList(scheduleAttachmentDao.findList(new ScheduleAttachment(schedule)));
		return schedule;
	}
	
	public List<Schedule> findList(Schedule schedule) {
		return super.findList(schedule);
	}
	
	public Page<Schedule> findPage(Page<Schedule> page, Schedule schedule) {
		return super.findPage(page, schedule);
	}
	
	@Transactional(readOnly = false)
	public void save(Schedule schedule) {
		super.save(schedule);
		for (ScheduleAttachment scheduleAttachment : schedule.getScheduleAttachmentList()){
			if (scheduleAttachment.getId() == null){
				continue;
			}
			if (ScheduleAttachment.DEL_FLAG_NORMAL.equals(scheduleAttachment.getDelFlag())){
				if (StringUtils.isBlank(scheduleAttachment.getId())){
					scheduleAttachment.setScheduleId(schedule);
					scheduleAttachment.preInsert();
					scheduleAttachmentDao.insert(scheduleAttachment);
				}else{
					scheduleAttachment.preUpdate();
					scheduleAttachmentDao.update(scheduleAttachment);
				}
			}else{
				scheduleAttachmentDao.delete(scheduleAttachment);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Schedule schedule) {
		super.delete(schedule);
		scheduleAttachmentDao.delete(new ScheduleAttachment(schedule));
	}

	public Map<String,Object> pageListByConditon(Page<Schedule> page,User user,Map<String, Object> conditons){
		List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
		Schedule schedule = new Schedule();
		if(conditons.containsKey("downloadType") && "sub".equals(String.valueOf(conditons.get("downloadType")))){
			schedule.getSqlMap().put("dsf",dataScopeFilter(user.getCurrentUser(), "o", "a"));
		}
		if(conditons.containsKey("downloadType") && "my".equals(String.valueOf(conditons.get("downloadType")))){
			schedule.setUser(user);
		}
		schedule.setPage(page);

		List<Schedule> list = scheduleDao.findListByCondition(conditons);
		int count = list.size();
		String lastDateTime = "";//最后次数据的时间
		for (Schedule s :list){
			Date time = s.getTime();
			String yyyyMMddStr = DateUtils.formatDate(time);
			String HHmmStr = DateUtils.formatDate(time,"HH:mm");
			String id = s.getId();
			String userId = s.getUser().getId();
			String typeId = s.getTypeId();
			String title = s.getTitle();
			String remark = s.getRemark();
			lastDateTime = yyyyMMddStr;

			ArrayList<Map<String,String>> subSubList = null;
			Map<String,String> subSubMap = new HashMap<String, String>();
			subSubMap.put("id",id);
			subSubMap.put("userId",userId);
			subSubMap.put("typeId",typeId);
			subSubMap.put("title",title);
			subSubMap.put("remark",remark);
			subSubMap.put("date",HHmmStr);

			boolean containSub = false;
			for (int i = 0;i < datas.size();i++){
				Map<String,Object> subMap = datas.get(i);
				if(yyyyMMddStr.equals(subMap.get("time"))){
					subSubList = (ArrayList<Map<String,String>>)subMap.get("subSubList");
					subSubList.add(subSubMap);
					containSub = true;
					break;
				}
			}

			if(!containSub){
				subSubList = new ArrayList<Map<String, String>>();
				subSubList.add(subSubMap);
				Map<String,Object> subMap = new HashMap<String,Object>();
				subMap.put("time",yyyyMMddStr);
				subMap.put("subSubList",subSubList);
				datas.add(subMap);
			}
		}

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("count",count);
		resultMap.put("datas",datas);
		resultMap.put("lastDateTime",lastDateTime);
		return resultMap;
	}

}