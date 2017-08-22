/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cwa.entity.Sign;

/**
 * 考勤管理相关DAO接口
 * @author lhc
 * @version 2017-08-22
 */
@MyBatisDao
public interface SignDao extends CrudDao<Sign> {
	
}