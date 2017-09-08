/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysPushMessage;

import java.util.List;

/**
 * 消息推送类DAO接口
 * @author lhc
 * @version 2017-08-22
 */
@MyBatisDao
public interface SysPushMessageDao extends CrudDao<SysPushMessage> {

    //根据用户获取用户对应的未读消息 根据推送消息时间判断是否未读
    public List<SysPushMessage> getNoReadByUserId(SysPushMessage message);
}