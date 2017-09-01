/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.service;

import java.util.List;

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
	
}