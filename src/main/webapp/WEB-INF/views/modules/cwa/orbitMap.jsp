<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轨道记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7012b35c62e5fd0b8c97e160cb5a4448"></script>
	<script type="text/javascript">
		$(function(){
			var map = new BMap.Map("baiduMap");
			var point = new BMap.Point(116.404, 39.915);
			map.centerAndZoom(point, 14);
		})
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
	<style type="text/css">
		html,body,#baiduMap{height:100%}
	</style>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/cwa/orbit/">轨道记录列表</a></li>
	<li class="active"><a href="${ctx}/cwa/orbit/map">轨道地图视图</a></li>
</ul>
<form:form id="searchForm" modelAttribute="orbit" action="${ctx}/cwa/orbit/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>查询时间：</label>
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
<div id="baiduMap" class="accordion-group"></div>
</body>
</html>