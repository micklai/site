/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sch.entity.SmSchedule;
import com.thinkgem.jeesite.modules.sch.dao.SmScheduleDao;
import com.thinkgem.jeesite.modules.sch.entity.SmScheduleAttachment;
import com.thinkgem.jeesite.modules.sch.dao.SmScheduleAttachmentDao;

/**
 * 日程管理类Service
 * @author lhc
 * @version 2017-08-28
 */
@Service
@Transactional(readOnly = true)
public class SmScheduleService extends CrudService<SmScheduleDao, SmSchedule> {

	@Autowired
	private SmScheduleAttachmentDao smScheduleAttachmentDao;
	
	public SmSchedule get(String id) {
		SmSchedule smSchedule = super.get(id);
		smSchedule.setSmScheduleAttachmentList(smScheduleAttachmentDao.findList(new SmScheduleAttachment(smSchedule)));
		return smSchedule;
	}
	
	public List<SmSchedule> findList(SmSchedule smSchedule) {
		return super.findList(smSchedule);
	}
	
	public Page<SmSchedule> findPage(Page<SmSchedule> page, SmSchedule smSchedule) {
		return super.findPage(page, smSchedule);
	}
	
	@Transactional(readOnly = false)
	public void save(SmSchedule smSchedule) {
		super.save(smSchedule);
		for (SmScheduleAttachment smScheduleAttachment : smSchedule.getSmScheduleAttachmentList()){
			if (smScheduleAttachment.getId() == null){
				continue;
			}
			if (SmScheduleAttachment.DEL_FLAG_NORMAL.equals(smScheduleAttachment.getDelFlag())){
				if (StringUtils.isBlank(smScheduleAttachment.getId())){
					smScheduleAttachment.setScheduleId(smSchedule);
					smScheduleAttachment.preInsert();
					smScheduleAttachmentDao.insert(smScheduleAttachment);
				}else{
					smScheduleAttachment.preUpdate();
					smScheduleAttachmentDao.update(smScheduleAttachment);
				}
			}else{
				smScheduleAttachmentDao.delete(smScheduleAttachment);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SmSchedule smSchedule) {
		super.delete(smSchedule);
		smScheduleAttachmentDao.delete(new SmScheduleAttachment(smSchedule));
	}
	
}