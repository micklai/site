/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cwa.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤管理相关Entity
 *
 * @author lhc
 * @version 2017-08-22
 */
public class Sign extends DataEntity<Sign> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户Id
	private String userName;		// 用户名
	private Date signTime;		// 签到时间
	private String signAddress;		// 签到地点
	private String xPos;		// 签到的经度坐标
	private String yPos;		// 签到的纬度坐标
	private String remark;		// 签到备注信息
	private String customerName;		// 拜访对象
	private String simNum;		// 签到时手机sim卡号
	private String signType;		// 签到类型，1：自动，2：手动
	private String leaderResignRemark;		// 领导补签说明
	private String resignState;		// 补签状态
	private Date beginSignTime;		// 开始 签到时间
	private Date endSignTime;		// 结束 签到时间
	
	public Sign() {
		super();
	}

	public Sign(String id){
		super(id);
	}

	@NotNull(message="用户Id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="签到时间不能为空")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@Length(min=1, max=128, message="签到地点长度必须介于 1 和 128 之间")
	public String getSignAddress() {
		return signAddress;
	}

	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}
	
	@Length(min=1, max=18, message="签到的经度坐标长度必须介于 1 和 18 之间")
	public String getXPos() {
		return xPos;
	}

	public void setXPos(String xPos) {
		this.xPos = xPos;
	}
	
	@Length(min=1, max=18, message="签到的纬度坐标长度必须介于 1 和 18 之间")
	public String getYPos() {
		return yPos;
	}

	public void setYPos(String yPos) {
		this.yPos = yPos;
	}
	
	@Length(min=0, max=128, message="签到备注信息长度必须介于 0 和 128 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=32, message="拜访对象长度必须介于 0 和 32 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=1, max=16, message="签到时手机sim卡号长度必须介于 1 和 16 之间")
	public String getSimNum() {
		return simNum;
	}

	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}
	
	@Length(min=1, max=1, message="签到类型，1：自动，2：手动长度必须介于 1 和 1 之间")
	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	@Length(min=1, max=128, message="领导补签说明长度必须介于 1 和 128 之间")
	public String getLeaderResignRemark() {
		return leaderResignRemark;
	}

	public void setLeaderResignRemark(String leaderResignRemark) {
		this.leaderResignRemark = leaderResignRemark;
	}
	
	@Length(min=1, max=18, message="补签状态长度必须介于 1 和 18 之间")
	public String getResignState() {
		return resignState;
	}

	public void setResignState(String resignState) {
		this.resignState = resignState;
	}
	
	public Date getBeginSignTime() {
		return beginSignTime;
	}

	public void setBeginSignTime(Date beginSignTime) {
		this.beginSignTime = beginSignTime;
	}
	
	public Date getEndSignTime() {
		return endSignTime;
	}

	public void setEndSignTime(Date endSignTime) {
		this.endSignTime = endSignTime;
	}
		
}