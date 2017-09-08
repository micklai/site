/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sm.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 日程管理类Entity
 * @author lhc
 * @version 2017-09-01
 */
public class Schedule extends DataEntity<Schedule> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户Id
	private String typeId;		// 字典表中类型id
	private String typeName;		// 类型名称
	private String title;		// 日程标题
	private Date time;		// 日程时间
	private String address;		// 日程地点
	private String xPos;		// 经度坐标
	private String yPos;		// 纬度坐标
	private String remark;		// 行程的备注信息
	private String visitName;		// 拜访对象
	private String resultState;		// 日程完成状态，0：未完成；1:完成
	private String autoFlag;		// 是否自动考勤签到，0：否；1：是
	private Date beginScheduleTime;		// 开始 签到时间
	private Date endScheduleTime;		// 结束 签到时间
	private List<ScheduleAttachment> scheduleAttachmentList = Lists.newArrayList();		// 子表列表
	
	public Schedule() {
		super();
	}

	public Schedule(String id){
		super(id);
	}

	@NotNull(message="用户Id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=32, message="字典表中类型id长度必须介于 1 和 32 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=100, message="类型名称长度必须介于 1 和 100 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=1, max=128, message="日程标题长度必须介于 1 和 128 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="日程时间不能为空")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Length(min=0, max=128, message="日程地点长度必须介于 0 和 128 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=18, message="经度坐标长度必须介于 0 和 18 之间")
	public String getXPos() {
		return xPos;
	}

	public void setXPos(String xPos) {
		this.xPos = xPos;
	}
	
	@Length(min=0, max=18, message="纬度坐标长度必须介于 0 和 18 之间")
	public String getYPos() {
		return yPos;
	}

	public void setYPos(String yPos) {
		this.yPos = yPos;
	}
	
	@Length(min=0, max=128, message="行程的备注信息长度必须介于 0 和 128 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=32, message="拜访对象长度必须介于 0 和 32 之间")
	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}
	
	@Length(min=0, max=1, message="日程完成状态，0：未完成；1:完成长度必须介于 0 和 1 之间")
	public String getResultState() {
		return resultState;
	}

	public void setResultState(String resultState) {
		this.resultState = resultState;
	}
	
	@Length(min=0, max=1, message="是否自动考勤签到，0：否；1：是长度必须介于 0 和 1 之间")
	public String getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}
	
	public List<ScheduleAttachment> getScheduleAttachmentList() {
		return scheduleAttachmentList;
	}

	public void setScheduleAttachmentList(List<ScheduleAttachment> scheduleAttachmentList) {
		this.scheduleAttachmentList = scheduleAttachmentList;
	}

	public Date getBeginScheduleTime() {
		return beginScheduleTime;
	}

	public void setBeginScheduleTime(Date beginScheduleTime) {
		this.beginScheduleTime = beginScheduleTime;
	}

	public Date getEndScheduleTime() {
		return endScheduleTime;
	}

	public void setEndScheduleTime(Date endScheduleTime) {
		this.endScheduleTime = endScheduleTime;
	}
}