/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.msgpush.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.msgpush.entity.SysPushMessage;

/**
 * SysPushMessageDAO接口
 * @author lhc
 * @version 2017-08-22
 */
@MyBatisDao
public interface SysPushMessageDao extends CrudDao<SysPushMessage> {
	
}