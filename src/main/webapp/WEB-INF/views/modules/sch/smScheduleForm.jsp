<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加日志管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sch/smSchedule/">添加日志管理列表</a></li>
		<li class="active"><a href="${ctx}/sch/smSchedule/form?id=${smSchedule.id}">添加日志管理<shiro:hasPermission name="sch:smSchedule:edit">${not empty smSchedule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sch:smSchedule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smSchedule" action="${ctx}/sch/smSchedule/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户Id：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${smSchedule.user.id}" labelName="user.name" labelValue="${smSchedule.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字典表中类型id：</label>
			<div class="controls">
				<form:input path="typeId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型名称：</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${smSchedule.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程地点：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度坐标：</label>
			<div class="controls">
				<form:input path="xPos" htmlEscape="false" maxlength="18" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度坐标：</label>
			<div class="controls">
				<form:input path="yPos" htmlEscape="false" maxlength="18" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行程的备注信息：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访对象：</label>
			<div class="controls">
				<form:input path="visitName" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程完成状态，0：未完成；1:完成：</label>
			<div class="controls">
				<form:input path="resultState" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否自动考勤签到，0：否；1：是：</label>
			<div class="controls">
				<form:input path="autoFlag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">日程管理信息附件表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>用户id</th>
								<th>附件名称</th>
								<th>附件路径</th>
								<th>附件类型</th>
								<th>福建创建时间</th>
								<th>备注信息</th>
								<shiro:hasPermission name="sch:smSchedule:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="smScheduleAttachmentList">
						</tbody>
						<shiro:hasPermission name="sch:smSchedule:edit"><tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#smScheduleAttachmentList', smScheduleAttachmentRowIdx, smScheduleAttachmentTpl);smScheduleAttachmentRowIdx = smScheduleAttachmentRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="smScheduleAttachmentTpl">//<!--
						<tr id="smScheduleAttachmentList{{idx}}">
							<td class="hide">
								<input id="smScheduleAttachmentList{{idx}}_id" name="smScheduleAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="smScheduleAttachmentList{{idx}}_delFlag" name="smScheduleAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<sys:treeselect id="smScheduleAttachmentList{{idx}}_user" name="smScheduleAttachmentList[{{idx}}].user.id" value="{{row.user.id}}" labelName="smScheduleAttachmentList{{idx}}.user.name" labelValue="{{row.user.name}}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="smScheduleAttachmentList{{idx}}_attachmentName" name="smScheduleAttachmentList[{{idx}}].attachmentName" type="text" value="{{row.attachmentName}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="smScheduleAttachmentList{{idx}}_attachmentPath" name="smScheduleAttachmentList[{{idx}}].attachmentPath" type="text" value="{{row.attachmentPath}}" maxlength="128" class="input-small required"/>
							</td>
							<td>
								<input id="smScheduleAttachmentList{{idx}}_attachmentType" name="smScheduleAttachmentList[{{idx}}].attachmentType" type="text" value="{{row.attachmentType}}" maxlength="16" class="input-small required"/>
							</td>
							<td>
								<input id="smScheduleAttachmentList{{idx}}_createTime" name="smScheduleAttachmentList[{{idx}}].createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="{{row.createTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<textarea id="smScheduleAttachmentList{{idx}}_remarks" name="smScheduleAttachmentList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="sch:smSchedule:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#smScheduleAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var smScheduleAttachmentRowIdx = 0, smScheduleAttachmentTpl = $("#smScheduleAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(smSchedule.smScheduleAttachmentList)};
							for (var i=0; i<data.length; i++){
								addRow('#smScheduleAttachmentList', smScheduleAttachmentRowIdx, smScheduleAttachmentTpl, data[i]);
								smScheduleAttachmentRowIdx = smScheduleAttachmentRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="sch:smSchedule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>