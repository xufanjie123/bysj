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
<style type="text/css">
  		div.panel.panel-htop{
  			margin:0 auto;
  		}
  	</style>
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
  </head>
  
  <body>
    <h1 style="text-align:center">医院挂号系统</h2>
	<p style="text-align:center">登录</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="登录" style="width:400px;padding:30px 60px;">
	<form action="/bysj/login" method="post">
		<div style="margin-bottom:20px">
			<div>帐号:</div>
			<input id="username" name="username" validType="length[4,32]" 
			required="true" type="text" value="" class="easyui-validatebox" 
			style="width:100%;height:32px" >
		</div>
		<div style="margin-bottom:20px">
			<div>密码:</div>
			<input id="password" name="password" validType="length[4,32]" 
			class="easyui-validatebox" required="true" type="password" value=""
			style="width:100%;height:32px"/>
		</div>
		<div style="margin-bottom:20px">
			<div>用户身份:</div>
			<input type="radio" name="id" value="patient" style="width:25%" checked="checked">患者
			<input type="radio" name="id" value="admin" style="width:25%">管理员
		</div>
		<div>
			<input id="login" type="submit" class="easyui-linkbutton" style="width:49%;height:32px" value="登录">
			<a href="/bysj/resign" class="easyui-linkbutton" style="width:49%;height:32px">注册</a>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#login").click(function(){
					if($('#username').hasClass("validatebox-invalid")||
						$('#password').hasClass("validatebox-invalid")){
						alert("帐号密码格式错误");
						return false;
					}else{
							return true;
					}
				})
			})
		</script>
	</form>
	</div>
  </body>
</html>
