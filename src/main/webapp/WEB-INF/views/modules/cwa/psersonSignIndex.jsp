<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤查看</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
<sys:message content="${message}"/>
	<!-- 账户主要表格 -->
	<div class="account-box">
		<h2 class="account-title">
			<span class="left c3">个人考勤</span>
			<a href="###" class="f-btn-fhby right">返回本月</a>
			<div class="clearfix right">
				<div class="f-btn-jian left">&lt;</div><div class="left f-riqi"><span class="f-year">2017</span>年<span class="f-month">1</span>月</div><div class="f-btn-jia left">&gt;</div><!-- 一定不能换行-->
			</div>
		</h2>
		<div class="f-rili-table">
			<div class="f-rili-head celarfix">
				<div class="f-rili-th">周日</div>
				<div class="f-rili-th">周一</div>
				<div class="f-rili-th">周二</div>
				<div class="f-rili-th">周三</div>
				<div class="f-rili-th">周四</div>
				<div class="f-rili-th">周五</div>
				<div class="f-rili-th">周六</div>
				<div class="clear"></div>
			</div>
			<div class="f-tbody clearfix">

			</div>
		</div>
	</div>
<script>
	var beginSignTime;
	var endSignTime;
	var year;
	var month;
	var mydate;
	function ajax(beginSignTime,endSignTime,year,month){
	    $.post(
			"${ctx}/cwa/sign/findPersonSignByMonth",
			{beginSignTime:beginSignTime,endSignTime:endSignTime},
			function(data){
                var dataObj=eval(data);
				showDate(dataObj,year,month);
			},
			"json"
		)
	}
	function formatTime(year,month){
        var pTime = new Date(parseInt(year),parseInt(month), 0);
        var days = pTime.getDate();
        beginSignTime = year+"-"+month+"-01 00:00:00";
        endSignTime = year+"-"+month+"-"+"days"+" 23:59:59";
        ajax(beginSignTime,endSignTime,year,month);
	}
    //读取年月写入日历  重点算法!!!!!!!!!!!
    function showDate(result,yyyy,mm){
        var dd = new Date(parseInt(yyyy),parseInt(mm), 0);   //Wed Mar 31 00:00:00 UTC+0800 2010
        var daysCount = dd.getDate();            //本月天数
        var mystr ="";//写入代码
        var icon = "";//图标代码
        var today = new Date().getDate(); //今天几号  21
        var monthstart = new Date(parseInt(yyyy)+"/"+parseInt(mm)+"/1").getDay()//本月1日周几
        var lastMonth; //上一月天数
        var nextMounth//下一月天数
        if(  parseInt(mm) ==1 ){
            lastMonth = new Date(parseInt(yyyy)-1,parseInt(12), 0).getDate();
        }else{
            lastMonth = new Date(parseInt(yyyy),parseInt(mm)-1, 0).getDate();
        }
        if( parseInt(mm) ==12 ){
            nextMounth = new Date(parseInt(yyyy)+1,parseInt(1), 0).getDate();
        }else{
            nextMounth = new Date(parseInt(yyyy),parseInt(mm)+1, 0).getDate();
        }
        //计算上月空格数
        for(j=monthstart;j>0;j--){
            mystr += "<div class='f-td f-null f-lastMonth' style='color:#ccc;'>"+(lastMonth-j+1)+"</div>";
        }

        //本月单元格
        for(i=0;i<daysCount;i++){
            //这里为一个单元格，添加内容在此
            mystr += "<div class='f-td f-number'><span class='f-day'>"+(i+1)+"</span>"
                +"<div></div>"
                /*+"<div class='f-table-msg'>回款中<span class='major'>1</span>笔。回款本息;<span class='major'>1，000，000</span>元</div>"//这里加判断*/
                +"</div>";
        }

        //计算下月空格数
        for(k=0; k<42-(daysCount+monthstart);k++ ){//表格保持等高6行42个单元格
            mystr += "<div class='f-td f-null f-nextMounth' style='color:#ccc;'>"+(k+1)+"</div>";
        }



        //写入日历
        $(".f-rili-table .f-tbody").html(mystr);
        //给今日加class
        if( mydate.getFullYear() == yyyy){
            if( (mydate.getMonth()+1 ) == mm){
                var today = mydate.getDate();
                var lastNum = $(".f-lastMonth").length;
                $(".f-rili-table .f-td").eq(today+lastNum-1).addClass("f-today");
            }
        }
        var daySpan = $(".f-day");
        for(var key in result){
            for(var i in daySpan){
                if(daySpan[i].innerHTML == result[key].day){
                    $(daySpan[i]).parent().attr({style:"background-color: #38F709","data-toggle":"tooltip","data-placement":"top"});
                    /*$($(daySpan[i]).next()[0]).after("<div class='f-table-msg'>在"+result[key].sign.signTime+"时"+result[key].sign.remark+"</div>");*/
                    /*$(daySpan[i]).append("<div class='btn btn-default' data-toggle='tooltip' data-placement='top'></div>");*/
                    $(daySpan[i]).parent().attr({"data-toggle":"popover","data-trigger":"hover","data-content":result[key].sign.signTime+"时："+result[key].sign.remark,"data-original-title":"详情"});
                    $(daySpan[i]).parent().popover("toggle");
				}
			}
		}
        //绑定查看方法
        $(".f-yuan").off("mouseover");
        $(".f-yuan").on("mouseover",function(){
            $(this).parent().find(".f-table-msg").show();
        });
        $(".f-table-msg").off("mouseover");
        $(".f-table-msg").on("mouseover",function(){
            $(this).show();
        });
        $(".f-yuan").off("mouseleave");
        $(".f-yuan").on("mouseleave",function(){
            $(this).parent().find(".f-table-msg").hide();
        });
        $(".f-table-msg").off("mouseleave");
        $(".f-table-msg").on("mouseleave",function(){
            $(this).hide();
        });
    }
    $(function(){
        //页面加载初始化年月
        mydate = new Date();
        $(".f-year").html( mydate.getFullYear() );
        $(".f-month").html( mydate.getMonth()+1 );
        year = $(".f-year").html();
        month =  $(".f-month").html();
        formatTime(year,month);
        //日历上一月
        $(".f-btn-jian ").click(function(){
            var mm = parseInt($(".f-month").html());
            var yy = parseInt($(".f-year").html());
            if( mm == 1){//返回12月
                $(".f-year").html(yy-1);
                $(".f-month").html(12);
                year = $(".f-year").html();
                month =  $(".f-month").html();
                formatTime(year,month);
            }else{//上一月
                $(".f-month").html(mm-1);
                year = $(".f-year").html();
                month =  $(".f-month").html();
                formatTime(year,month);
            }
        })
        //日历下一月
        $(".f-btn-jia").click(function(){
            var mm = parseInt($(".f-month").html());
            var yy = parseInt($(".f-year").html());
            if( mm == 12){//返回12月
                $(".f-year").html(yy+1);
                $(".f-month").html(1);
                year = $(".f-year").html();
                month =  $(".f-month").html();
                formatTime(year,month)
            }else{//上一月
                $(".f-month").html(mm+1);
                year = $(".f-year").html();
                month =  $(".f-month").html();
                formatTime(year,month)
            }
        })
        //返回本月
        $(".f-btn-fhby").click(function(){
            $(".f-year").html( mydate.getFullYear() );
            $(".f-month").html( mydate.getMonth()+1 );
            year = $(".f-year").html();
            month =  $(".f-month").html();
            formatTime(year,month)
        })
    })
</script>
<script type="text/javascript">
	var leftWidth = 180; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
        mainObj.css("width","auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>