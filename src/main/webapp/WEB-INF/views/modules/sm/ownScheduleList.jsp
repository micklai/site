<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日程管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sm/schedule/own/list">日程管理列表</a></li>
		<shiro:hasPermission name="sm:schedule:edit"><li><a href="${ctx}/sm/schedule/own/form">新增日程</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="schedule" action="${ctx}/sm/schedule/own/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<inpit id="userId" name="user.id" type="hidden" value="${user.id}">
		<ul class="ul-form">
			<li><label>类型名称：</label>
				<form:input path="typeName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>日程标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>签到时间：</label>
				<input name="beginScheduleTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${schedule.beginScheduleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endScheduleTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${schedule.endScheduleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型名称</th>
				<th>日程标题</th>
				<th>日程时间</th>
				<th>日程地点</th>
				<th>行程的备注信息</th>
				<th>拜访对象</th>
				<th>日程完成状态</th>
				<%--<th>是否自动考勤签到</th>--%>
				<th>备注信息</th>
				<shiro:hasPermission name="sm:schedule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schedule">
			<tr>
				<td><a href="${ctx}/sm/schedule/form?id=${schedule.id}">
					${schedule.typeName}
				</a></td>
				<td>
					${schedule.title}
				</td>
				<td>
					<fmt:formatDate value="${schedule.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${schedule.address}
				</td>
				<td>
					${schedule.remark}
				</td>
				<td>
					${schedule.visitName}
				</td>
				<c:if test="${schedule.resultState} == 0">
					<td>
							完成
					</td>
				</c:if>
				<c:if test="${schedule.resultState} == 1">
					<td>
						未完成
					</td>
				</c:if>
				<%--<td>
					${schedule.resultState}
				</td>--%>
				<%--<td>
					${schedule.autoFlag}
				</td>--%>
				<td>
					${schedule.remarks}
				</td>
				<shiro:hasPermission name="sm:schedule:edit"><td>
    				<a href="${ctx}/sm/schedule/own/form?id=${schedule.id}">修改</a>
					<a href="${ctx}/sm/schedule/delete?id=${schedule.id}" onclick="return confirmx('确认要删除该日程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>