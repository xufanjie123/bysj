<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<title>医院挂号预约系统</title>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	String message = (String)session.getAttribute("message");
	if(message == null||message.equals("")){

	}else{
%>
<script type="text/javascript">
        alert("<%=message%>");
</script>
<% 
	session.removeAttribute("message");
}
%>
<%
	// 权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("../index.jsp");
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		// 数据
		var treeData=[{
			text:"基础操作",
			iconCls:"icon-menu",
			children:[{
				text:"值班信息",
				iconCls:"icon-user",
				attributes:{
					url:"${root}" +"/views/doctor/doctorWork.jsp"
				}
			},{
				text:"挂号信息",
				iconCls:"icon-user",
				attributes:{
					url:"${root}" +"/views/doctor/orderInfo.jsp"
				}
			}]
		}];
		
		// 实例化树菜单
		$("#tree").tree({
			data:treeData,
			lines:true,
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url,node.iconCls);
				}
			}
		});
		
		// 新增Tab
		function openTab(text,url,iconCls){
			if($("#tabs").tabs('exists',text)){
				$("#tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content,
					iconCls:iconCls,
				});
			}
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 85px;background-color: #E0EDFF">
		<div align="left" style="float: left; height:80px"><img src="images/logo_1.jpg"></div>
		<div style="padding-top: 50px;padding-right: 20px; float:right">当前用户：&nbsp;<font color="red" >${currentUser}</font><a href="/bysj/logout" style="margin-left:20px;text-decoration:none">注销登录</a></div>
		
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页"  data-options="iconCls:'icon-welcome'">
				<div align="center" style="padding-top: 100px;"><font color="red" size="8"><b>欢迎使用医院挂号预约系统</b></font></div>
			</div>
		</div>
	</div>
	<div region="west" style="width: 150px;" title="导航菜单" split="true">
		<ul id="tree"></ul>
	</div>
	<div region="south" style="height: 25px;" align="center">版权所有</div>
</body>
</html>