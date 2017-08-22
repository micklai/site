/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attendance.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.attendance.entity.Orbit;

/**
 * 考勤轨迹类DAO接口
 * @author lhc
 * @version 2017-08-21
 */
@MyBatisDao
public interface OrbitDao extends CrudDao<Orbit> {
	
}