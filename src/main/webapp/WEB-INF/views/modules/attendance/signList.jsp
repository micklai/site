<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签到成功管理</title>
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
		<li class="active"><a href="${ctx}/attendance/sign/">签到成功列表</a></li>
		<shiro:hasPermission name="attendance:sign:edit"><li><a href="${ctx}/attendance/sign/form">签到成功添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sign" action="${ctx}/attendance/sign/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>签到时间：</label>
				<input name="beginSignTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sign.beginSignTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSignTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sign.endSignTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>用户名</th>
				<th>签到时间</th>
				<th>签到地点</th>
				<th>签到备注信息</th>
				<th>补签状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="attendance:sign:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sign">
			<tr>
				<td><a href="${ctx}/attendance/sign/form?id=${sign.id}">
					${sign.userName}
				</a></td>
				<td>
					<fmt:formatDate value="${sign.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sign.signAddress}
				</td>
				<td>
					${sign.remark}
				</td>
				<td>
					${sign.resignState}
				</td>
				<td>
					<fmt:formatDate value="${sign.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sign.remarks}
				</td>
				<shiro:hasPermission name="attendance:sign:edit"><td>
    				<a href="${ctx}/attendance/sign/form?id=${sign.id}">修改</a>
					<a href="${ctx}/attendance/sign/delete?id=${sign.id}" onclick="return confirmx('确认要删除该签到成功吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>