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
    <title>信息管理</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<title>医院挂号系统</title>
	<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
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
  	$.post("${root}" + "/front/patientInfo/patient",null,function(data){
		$("#truename").val(data.truename);
		if(data.patientgender == "男"){
			$("#bgender").attr("checked","checked");
		}else{
			$("#ggender").attr("checked","checked");
		}
		$('#age').numberspinner('setValue', data.patientage); 
		$("#phone").val(data.description);
		
  	  },'json');
  	</script>
  </head>
  
  <body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="个人信息修改" style="width:400px;padding:30px 60px;">
	<form action="${root }/front/patientInfo/savePatient" method="post">
		<input name="id" type="hidden" value="0">
		<input id="username" name="username" validType="length[4,32]" 
			required="true" type="hidden" value="" class="easyui-validatebox" 
			style="width:100%;height:32px" >
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
			<input id="bgender" type="radio" name="patientgender" value="男" style="width:25%" >男
			<input id="ggender" type="radio" name="patientgender" value="女" style="width:25%" >女
		</div>
		<div style="margin-bottom:20px">
			<div>年龄:</div>
			<input id="age" name="patientage" class="easyui-numberspinner" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>手机号</div>
			<input id="phone" name="description" validType="length[11,11]" 
			required="true" type="text" value="" class="easyui-validatebox" 
			style="width:100%;height:32px" >
		</div>
		<div>
			<input id="resign" type="submit" class="easyui-linkbutton" style="width:100%;height:32px" value="修改">
		</div>
	</form>
		<script type="text/javascript">
			$(function(){
				$('#resign').click(function(){
					if($('#password').hasClass("validatebox-invalid")||
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
