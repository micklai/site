<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加日志管理管理</title>
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
		<li class="active"><a href="${ctx}/sch/smSchedule/">添加日志管理列表</a></li>
		<shiro:hasPermission name="sch:smSchedule:edit"><li><a href="${ctx}/sch/smSchedule/form">添加日志管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smSchedule" action="${ctx}/sch/smSchedule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型名称：</label>
				<form:input path="typeName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>日程标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>日程完成状态，0：未完成；1:完成</th>
				<th>是否自动考勤签到，0：否；1：是</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sch:smSchedule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smSchedule">
			<tr>
				<td><a href="${ctx}/sch/smSchedule/form?id=${smSchedule.id}">
					${smSchedule.typeName}
				</a></td>
				<td>
					${smSchedule.title}
				</td>
				<td>
					<fmt:formatDate value="${smSchedule.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${smSchedule.address}
				</td>
				<td>
					${smSchedule.remark}
				</td>
				<td>
					${smSchedule.visitName}
				</td>
				<td>
					${smSchedule.resultState}
				</td>
				<td>
					${smSchedule.autoFlag}
				</td>
				<td>
					${smSchedule.remarks}
				</td>
				<shiro:hasPermission name="sch:smSchedule:edit"><td>
    				<a href="${ctx}/sch/smSchedule/form?id=${smSchedule.id}">修改</a>
					<a href="${ctx}/sch/smSchedule/delete?id=${smSchedule.id}" onclick="return confirmx('确认要删除该添加日志管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>