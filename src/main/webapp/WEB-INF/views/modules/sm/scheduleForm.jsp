<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7012b35c62e5fd0b8c97e160cb5a4448"></script>
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
		function delScheduleAttachment(ele){
		    var id = $(ele).parent().prevAll().first();
		    $.get("${ctx}/sm/schedule/ScheduleAttachment/delete?id="+id,function(data){
		        $(ele);
			})
		}

		var map ;
		var point;
        var myMaker;
        var xPos;
        var yPos;
        var city;
        var geoc = new BMap.Geocoder();
		function positioning(ele) {
			if (typeof(point) == "undefined") {
				alert("正在加载地图，请稍后...");}
			else{
				$('#myModal').modal("show");
				if (typeof(map) == "undefined") {
						map = new BMap.Map("baiduMap");
						map.centerAndZoom(point, 14);
//						geoc = new BMap.Geocoder();
						map.addEventListener("click", function (e) {
                            $("#msg").html("");
                            xPos = e.point.lng;
                            yPos = e.point.lat;
							map.removeOverlay(myMaker);
							myMaker = new BMap.Marker(e.point)
							map.addOverlay(myMaker);
							var pt = e.point;
							geoc.getLocation(pt, function (rs) {
								var addComp = rs.addressComponents;
								$("#selAddressName").html(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber)
							});
						});
					}
				}
        }

        function selAddress(){
		    var address = $("#selAddress").val();
		    address = city+address;
            // 创建地址解析器实例
            var myGeo = new BMap.Geocoder();
            // 将地址解析结果显示在地图上,并调整地图视野
            myGeo.getPoint(address, function(newPoint){
			   if(newPoint){
                   $("#msg").html("");
			       point = newPoint;
			       map.centerAndZoom(point,18);
			   }else{
			       $("#selAddressName").html("");
			       $("#msg").html("输入有误")
			   }
			});
		}

		function ensure(){
            $('#myModal').modal("hide");
            $("#xPos").val(xPos);
			$("#yPos").val(yPos);
            $("#address").val($("#selAddressName").html().trim());
		}

		$(function(){
		    var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    point = new BMap.Point(r.point.lng, r.point.lat);
                    //获取当前城市city
                    geoc.getLocation(point, function (rs) {
                        var addComp = rs.addressComponents;
                        city = addComp.city;
                    });
                }
            });
		});
	</script>
	<style type="text/css">
		#myModal{left:30%;width:70%;top:5%}
		.modal-body{min-height: 450px}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sm/schedule/own/list">日程管理列表</a></li>
		<li class="active"><a href="${ctx}/sm/schedule/own/form?id=${schedule.id}">日程<shiro:hasPermission name="sm:schedule:edit">${not empty schedule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sm:schedule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="schedule" action="${ctx}/sm/schedule/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<form:input path="id" type="hidden"/>
		<form:input id="xPos" path="xPos" type="hidden"/>
		<form:input id="yPos" path="yPos" type="hidden"/>
		<div class="control-group">
			<label class="control-label">日程标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程类型：</label>
			<div class="controls">
				<form:select path="typeName" items="${fns:getDictList('act_category')}" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${schedule.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程地点：</label>
			<div class="controls">
				<form:input id="address" path="address" htmlEscape="false" maxlength="128" class="input-xlarge " readonly="true" onclick="positioning(this)"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行程的备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访对象：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${schedule.user.id}" labelName="user.name" labelValue="${schedule.user.name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日程完成状态：</label>
			<div class="controls">
				<form:input path="resultState" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否自动考勤签到：</label>
			<div class="controls">
				<form:radiobuttons  path="autoFlag" items="${fns:getDictList('act_category')}" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他备注信息：</label>
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
							<th>附件</th>
							<th>备注信息</th>
						</tr>
					</thead>
					<tbody id="scheduleAttachmentList">
					<%--<c:forEach items="${schedule.scheduleAttachmentList}" var="scheduleAttachment">
						<tr>
							<td class="hide">${scheduleAttachment.id}</td>
							<td  width="40%">${scheduleAttachment.attachmentName}</td>
							<dt style="width:95%">${scheduleAttachment.remarks}</dt>
							<shiro:hasPermission name="sm:schedule:edit">
								<td class="text-center" width="10">
									{{#delBtn}}<span class="close" onclick="delScheduleAttachment(this)" title="删除">&times;</span>{{/delBtn}}
								</td>
							</shiro:hasPermission>
						</tr>
					</c:forEach>--%>
					</tbody>
					<shiro:hasPermission name="sm:schedule:edit"><tfoot>
						<tr><td colspan="3"><a href="javascript:" onclick="addRow('#scheduleAttachmentList', scheduleAttachmentRowIdx, scheduleAttachmentTpl);scheduleAttachmentRowIdx = scheduleAttachmentRowIdx + 1;" class="btn">新增</a></td></tr>
					</tfoot></shiro:hasPermission>
				</table>
				<script type="text/template" id="scheduleAttachmentTpl">//<!--
					<tr id="scheduleAttachmentList{{idx}}">
						<td class="hide">
							<input id="scheduleAttachmentList{{idx}}_id" name="scheduleAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
							<input id="scheduleAttachmentList{{idx}}_delFlag" name="scheduleAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>
						</td>
						<td width="40%">
							<input type="file" name="file"></input>
						</td>
						<td>
							<textarea id="scheduleAttachmentList{{idx}}_remarks" name="scheduleAttachmentList[{{idx}}].remarks" rows="1" maxlength="255" style="width:95%"></textarea>
						</td>
						<shiro:hasPermission name="sm:schedule:edit"><td class="text-center" width="10">
							{{#delBtn}}<span class="close" onclick="delRow(this, '#scheduleAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</td></shiro:hasPermission>
					</tr>//-->
				</script>
				<script type="text/javascript">
					var scheduleAttachmentRowIdx = 0, scheduleAttachmentTpl = $("#scheduleAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					$(document).ready(function() {
						var data = ${fns:toJson(schedule.scheduleAttachmentList)};
						for (var i=0; i<data.length; i++){
							addRow('#scheduleAttachmentList', scheduleAttachmentRowIdx, scheduleAttachmentTpl, data[i]);
							scheduleAttachmentRowIdx = scheduleAttachmentRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sm:schedule:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header breadcrumb form-search">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">选取地址</h3>
			<input id="selAddress" class="input-large"/>
			<input id="querybtn" type="button" value="查询" onclick="selAddress()"/><span id="msg" style="color:#F00"></span><span id="selAddressName"></span>
		</div>
		<div id="baiduMap" class="modal-body" >

		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn btn-primary" onclick="ensure()">确定</button>
		</div>
	</div>
</body>
</html>