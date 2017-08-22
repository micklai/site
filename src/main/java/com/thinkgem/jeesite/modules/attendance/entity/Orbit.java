/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attendance.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤轨迹类Entity
 * @author lhc
 * @version 2017-08-21
 */
public class Orbit extends DataEntity<Orbit> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户Id
	private Date orbitTime;		// 具体时间点
	private String xPos;		// 签到的经度坐标
	private String yPos;		// 签到的纬度坐标
	private String simNum;		// 终端的sim卡号
	private String address;		// 当前位置
	private Date beginOrbitTime;		// 开始 具体时间点
	private Date endOrbitTime;		// 结束 具体时间点
	
	public Orbit() {
		super();
	}

	public Orbit(String id){
		super(id);
	}

	@NotNull(message="用户Id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="具体时间点不能为空")
	public Date getOrbitTime() {
		return orbitTime;
	}

	public void setOrbitTime(Date orbitTime) {
		this.orbitTime = orbitTime;
	}
	
	@Length(min=1, max=16, message="签到的经度坐标长度必须介于 1 和 16 之间")
	public String getXPos() {
		return xPos;
	}

	public void setXPos(String xPos) {
		this.xPos = xPos;
	}
	
	@Length(min=1, max=16, message="签到的纬度坐标长度必须介于 1 和 16 之间")
	public String getYPos() {
		return yPos;
	}

	public void setYPos(String yPos) {
		this.yPos = yPos;
	}
	
	@Length(min=1, max=16, message="终端的sim卡号长度必须介于 1 和 16 之间")
	public String getSimNum() {
		return simNum;
	}

	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}
	
	@Length(min=1, max=128, message="当前位置长度必须介于 1 和 128 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getBeginOrbitTime() {
		return beginOrbitTime;
	}

	public void setBeginOrbitTime(Date beginOrbitTime) {
		this.beginOrbitTime = beginOrbitTime;
	}
	
	public Date getEndOrbitTime() {
		return endOrbitTime;
	}

	public void setEndOrbitTime(Date endOrbitTime) {
		this.endOrbitTime = endOrbitTime;
	}
		
}