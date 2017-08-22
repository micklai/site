<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>记录轨迹成功管理</title>
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
		<li class="active"><a href="${ctx}/attendance/orbit/">记录轨迹成功列表</a></li>
		<shiro:hasPermission name="attendance:orbit:edit"><li><a href="${ctx}/attendance/orbit/form">记录轨迹成功添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orbit" action="${ctx}/attendance/orbit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>具体时间点：</label>
				<input name="beginOrbitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orbit.beginOrbitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOrbitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orbit.endOrbitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>具体时间点</th>
				<th>当前位置</th>
				<shiro:hasPermission name="attendance:orbit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orbit">
			<tr>
				<td><a href="${ctx}/attendance/orbit/form?id=${orbit.id}">
					<fmt:formatDate value="${orbit.orbitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${orbit.address}
				</td>
				<shiro:hasPermission name="attendance:orbit:edit"><td>
    				<a href="${ctx}/attendance/orbit/form?id=${orbit.id}">修改</a>
					<a href="${ctx}/attendance/orbit/delete?id=${orbit.id}" onclick="return confirmx('确认要删除该记录轨迹成功吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>