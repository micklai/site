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
		.accordion-group{width:48%;height:100%}
		#mainContent{height:80%}
		#baiduMap{position:absolute;right:10px;bottom: 10px;width:30%;height:40%}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7012b35c62e5fd0b8c97e160cb5a4448"></script>
	<script type="text/javascript">
        // 百度地图API功能
		$(function(){
			var map = new BMap.Map("baiduMap");
			var point = new BMap.Point(116.404, 39.915);
			map.centerAndZoom(point, 14);
            addBigMapCtrl(map);
		})
        function addBigMapCtrl(map,smallMapCtrl) {
            function bigMapController() {
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(10, 10);
            }
            bigMapController.prototype = new BMap.Control();
            bigMapController.prototype.initialize = function (map) {
                // 创建一个DOM元素
                var div = document.createElement("div");
                // 添加文字说明
                div.appendChild(document.createTextNode("大图"));
                // 设置样式
                div.style.cursor = "pointer";
                div.style.border = "1px solid gray";
                div.style.backgroundColor = "white";
                div.onclick = function (e) {
                    $("#baiduMap").removeAttr("style")
                    $("#baiduMap").css({
                        width: "100%",
                        height: "100%",
                        float: "left",
                        overflow: "hidden"
                    });
                    map.removeControl(bigMapCtrl);
                    addSmallMap(map,bigMapCtrl);
                };
                map.getContainer().appendChild(div);
                // 将DOM元素返回
                return div;
            }
            var bigMapCtrl = new bigMapController();
            map.addControl(bigMapCtrl);
        }
        function addSmallMap(map,bigMapCtrl){
            function smallMapController(){
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(10, 10);
            }
            smallMapController.prototype = new BMap.Control();
            smallMapController.prototype.initialize = function(map){
                // 创建一个DOM元素
                var div = document.createElement("div");
                // 添加文字说明
                div.appendChild(document.createTextNode("小图"));
                // 设置样式
                div.style.cursor = "pointer";
                div.style.border = "1px solid gray";
                div.style.backgroundColor = "white";
                div.onclick = function(e){
                    $("#baiduMap").removeAttr("style")
                    $("#baiduMap").css({position:"absolute",right:"10px",bottom: "10px",width:"30%",height:"40%",overflow:"hidden"});
                    map.removeControl(bigMapCtrl);
                    addBigMapCtrl(map,smallMapCtrl);
                };
                map.getContainer().appendChild(div);
                // 将DOM元素返回
                return div;
            }
            var smallMapCtrl = new smallMapController();
            map.addControl(smallMapCtrl);
        }
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
			<li><label>归属公司：</label><sys:treeselect id="company" name="user.company.id" value="${user.company.id}" labelName="user.company.name" labelValue="${sign.user.company.name}"
													title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>归属部门：</label><sys:treeselect id="office" name="user.office.id" value="${user.office.id}" labelName="user.office.name" labelValue="${sign.user.office.name}"
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
						<th>补签状态</th>
						<th>更新时间</th>
						<th>备注信息</th>
						<%--<shiro:hasPermission name="cwa:sign:edit"><th>操作</th></shiro:hasPermission>--%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sign">
					<tr>
						<td><a href="${ctx}/cwa/orbit/list?id=${sign.id}" target="_blank">
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
						<%--<shiro:hasPermission name="cwa:sign:edit"><td>
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