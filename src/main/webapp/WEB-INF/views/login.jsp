<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<title>医院挂号系统</title>
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/demo/demo.css">
	<script type="text/javascript" src="./jquery-easyui-1.5.2/jquery.min.js"></script>
	<script type="text/javascript" src="./jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
  	<style type="text/css">
  		div.panel.panel-htop{
  			margin:0 auto;
  		}
  	</style>
  </head>
  
  <body>
    <h1 style="text-align:center">医院挂号系统</h2>
	<p style="text-align:center">登录</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="登录" style="width:400px;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<div>帐号:</div>
			<input class="easyui-textbox" data-options="prompt:'请输入帐号'" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>密码:</div>
			<input class="easyui-passwordbox" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>Last Name:</div>
			<input class="" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>Company:</div>
			<input class="easyui-textbox" style="width:100%;height:32px">
		</div>
		
		<div>
			<a href="#" class="easyui-linkbutton" style="width:100%;height:32px">登录</a>
		</div>
	</div>
  </body>
</html>
