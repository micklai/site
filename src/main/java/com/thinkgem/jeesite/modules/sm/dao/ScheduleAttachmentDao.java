/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sm.entity.ScheduleAttachment;

/**
 * 日程管理类DAO接口
 * @author lhc
 * @version 2017-09-01
 */
@MyBatisDao
public interface ScheduleAttachmentDao extends CrudDao<ScheduleAttachment> {
	
}