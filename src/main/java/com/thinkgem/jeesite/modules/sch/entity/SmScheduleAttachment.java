/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sch.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 日程管理类Entity
 * @author lhc
 * @version 2017-08-28
 */
public class SmScheduleAttachment extends DataEntity<SmScheduleAttachment> {
	
	private static final long serialVersionUID = 1L;
	private SmSchedule scheduleId;		// 日程管理信息主表id 父类
	private User user;		// 用户id
	private String attachmentName;		// 附件名称
	private String attachmentPath;		// 附件路径
	private String attachmentType;		// 附件类型
	private Date createTime;		// 福建创建时间
	private Date beginCreateTime;		// 开始 福建创建时间
	private Date endCreateTime;		// 结束 福建创建时间
	
	public SmScheduleAttachment() {
		super();
	}

	public SmScheduleAttachment(String id){
		super(id);
	}

	public SmScheduleAttachment(SmSchedule scheduleId){
		this.scheduleId = scheduleId;
	}

	@Length(min=1, max=32, message="日程管理信息主表id长度必须介于 1 和 32 之间")
	public SmSchedule getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(SmSchedule scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@NotNull(message="用户id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="附件名称长度必须介于 1 和 64 之间")
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	@Length(min=1, max=128, message="附件路径长度必须介于 1 和 128 之间")
	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	
	@Length(min=1, max=16, message="附件类型长度必须介于 1 和 16 之间")
	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="福建创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
}