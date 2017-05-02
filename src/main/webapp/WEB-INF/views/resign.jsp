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
    <title>注册</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<title>医院挂号系统</title>
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./jquery-easyui-1.5.2/demo/demo.css">
	<script type="text/javascript" src="./jquery-easyui-1.5.2/jquery.min.js"></script>
	<script type="text/javascript" src="./jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
  	<script type="text/javascript">
  	$.extend($.fn.validatebox.defaults.rules, {  
  	    /*必须和某个字段相等*/
  	    equalTo: {
  	        validator:function(value,param){
  	            return $(param[0]).val() == value;
  	        },
  	        message:'两次输入密码不一致'
  	    }
  	           
  	});
  	</script>
  </head>
  
  <body>
    <h1 style="text-align:center">医院挂号系统</h2>
	<p style="text-align:center">注册</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="注册" style="width:400px;padding:30px 60px;">
	<form action="/bysj/realresign" method="post">
		<input name="id" type="hidden" value="0">
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
			<div>确认密码:</div>
			<input type="password" name="repassword" id="repassword" required="true" 
			class="easyui-validatebox"  validType="equalTo['#password']" 
			invalidMessage="两次输入密码不匹配" style="width:100%;height:32px" />
		</div>
		<div style="margin-bottom:20px">
			<div>真实姓名:</div>
			<input id="truename" name="truename" type="text" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>性别:</div>
			<input type="radio" name="gender" value="男" style="width:25%" checked="checked">男
			<input type="radio" name="gender" value="女" style="width:25%">女
		</div>
		<div style="margin-bottom:20px">
			<div>年龄:</div>
			<input name="age" class="easyui-numberspinner" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>病情描述:</div>
			<input name="description" class="easyui-textbox" multiline="true" style="width:100%;height:50px">
		</div>
		<div>
			<input id="resign" type="submit" class="easyui-linkbutton" style="width:100%;height:32px" value="注册">
			
		</div>
	</form>
		<script type="text/javascript">
			$(function(){
				$('#resign').click(function(){
					if($('#username').hasClass("validatebox-invalid")||
						$('#password').hasClass("validatebox-invalid")||
						$('#repassword').hasClass("validatebox-invalid")){
						alert("请正确输入信息");
						return false;
					}else{
						return true;
					}
				});
			})
		</script>
	</div>
  </body>
</html>
