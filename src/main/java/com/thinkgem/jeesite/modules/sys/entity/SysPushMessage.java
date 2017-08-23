/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 消息推送类Entity
 * @author lhc
 * @version 2017-08-22
 */
public class SysPushMessage extends DataEntity<SysPushMessage> {
	
	private static final long serialVersionUID = 1L;
	private String typeId;		// 消息类型id
	private String typeName;		// 消息类型名称
	private String msgContent;		// 消息内容
	private Date msgDate;		// 消息发送时间
	private String dataId;		// 消息对应具体数据id
	private Office office;		// 推送目标机构名
	private User user;		// 推送目标用户
	
	public SysPushMessage() {
		super();
	}

	public SysPushMessage(String id){
		super(id);
	}

	@Length(min=1, max=32, message="消息类型id长度必须介于 1 和 32 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=32, message="消息类型名称长度必须介于 1 和 32 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=1, max=100, message="消息内容长度必须介于 1 和 100 之间")
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMsgData() {
		return msgDate;
	}

	public void setMsgData(Date msgData) {
		this.msgDate = msgData;
	}
	
	@Length(min=1, max=32, message="消息对应具体数据id长度必须介于 1 和 32 之间")
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	@NotNull(message="推送目标机构名不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@NotNull(message="推送目标用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}