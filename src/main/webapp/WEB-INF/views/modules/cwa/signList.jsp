<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤管理管理</title>
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
	<style type="text/css">
		html,body{height:100%}
		.accordion-group{float: left;width:48%;height:100%}
		.signTable{float: left;width:48%;margin: 0 1%;height:100%}
		#mainContent{ position:relative;height:80%}

	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7012b35c62e5fd0b8c97e160cb5a4448"></script>
	<script type="text/javascript">
       window.onload = function () {
           // 百度地图API功能
           var map = new BMap.Map("baiduMap");
           var point = new BMap.Point(116.404, 39.915);
           map.centerAndZoom(point, 5);
       };
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cwa/sign/list">考勤管理列表</a></li>
		<li><a href="${ctx}/cwa/sign/unSignList">未签到列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sign" action="${ctx}/cwa/sign/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<ul class="ul-form">
			<li><label>归属公司：</label><sys:treeselect id="company" name="user.company.id" value="${id}" labelName="user.company.name" labelValue="${name}"
													title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>归属部门：</label><sys:treeselect id="office" name="user.office.id" value="${id}" labelName="user.office.name" labelValue="${name}"
													title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
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
	<div id="mainContent">
		<div class="signTable">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>用户名</th>
						<th>签到时间</th>
						<th>签到地点</th>
						<th>签到备注信息</th>
						<%--<th>补签状态</th>
						<th>更新时间</th>
						<th>备注信息</th>
						<shiro:hasPermission name="cwa:sign:edit"><th>操作</th></shiro:hasPermission>--%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sign">
					<tr>
						<td><a href="${ctx}/cwa/sign/form?id=${sign.id}">
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
						<%--<td>
							${sign.resignState}
						</td>
						<td>
							<fmt:formatDate value="${sign.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${sign.remarks}
						</td>
						<shiro:hasPermission name="cwa:sign:edit"><td>
							<a href="${ctx}/cwa/sign/form?id=${sign.id}">修改</a>
							<a href="${ctx}/cwa/sign/delete?id=${sign.id}" onclick="return confirmx('确认要删除该考勤管理吗？', this.href)">删除</a>
						</td></shiro:hasPermission>--%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination">${page}</div>
		</div>
		<div id="baiduMap" class="accordion-group"></div>
	</div>

</body>
</html>