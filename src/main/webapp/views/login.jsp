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
<script language="javascript" type="text/javascript">

        var code;
        function createCode() {
            code = "";
            var codeLength = 4; //验证码的长度
            var checkCode = document.getElementById("checkCode");
            var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //所有候选组成验证码的字符，当然也可以用中文的
            for (var i = 0; i < codeLength; i++) 
            {
                var charNum = Math.floor(Math.random() * 52);
                code += codeChars[charNum];
            }
            if (checkCode) 
            {
                checkCode.className = "code";
                checkCode.innerHTML = code;
            }
        }
        function validateCode() 
        {
            var inputCode = document.getElementById("inputCode").value;
            if (inputCode.length <= 0) 
            {
                alert("请输入验证码！");
                return false;
            }
            else if (inputCode.toUpperCase() != code.toUpperCase()) 
            {
                alert("验证码输入有误！");
                createCode();
                return false;
            }
            else 
            {
                return true;
            }        
        }    
</script>
<style type="text/css">
    .code
    {
            background:url(code_bg.jpg);
            font-family:Arial;
            font-style:italic;
             color:blue;
             font-size:30px;
             border:0;
             padding:2px 3px;
             letter-spacing:3px;
             font-weight:bolder;                     
             cursor:pointer;
             width:150px;
             height:32px;
             line-height:32px;
             text-align:center;
             vertical-align:middle;

    }
    </style>
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
			<input id="username" name="username" validType="length[2,32]" 
			required="true" type="text" value="" class="easyui-validatebox" 
			style="width:100%;height:32px" >
		</div>
		<div style="margin-bottom:20px">
			<div>密码:</div>
			<input id="password" name="password" validType="length[3,32]" 
			class="easyui-validatebox" required="true" type="password" value=""
			style="width:100%;height:32px"/>
		</div>
		<div style="margin-bottom:20px">
			<div>验证码:</div>
			<div>
            <div id="xfj" style="width: 50%;float: left;"><a class="code" id="checkCode" style="width:180px !important" onclick="createCode()"></a></div>
           	<input id="inputCode" name="inputCode" validType="length[4,4]" 
			class="easyui-validatebox" required="true" type="text" value=""
			style="width:49%;height:32px"/></div>
        </div>
		<div style="margin-bottom:20px;">
			<div>用户身份:</div>
			<input type="radio" name="id" value="patient" style="width:18%;" checked="checked">患者
			<input type="radio" name="id" value="doctor" style="width:18%">医生
			<input type="radio" name="id" value="admin" style="width:18%">管理员
		</div>
		<div style="margin-bottom:20px">
			<input id="login" type="submit" class="easyui-linkbutton" style="width:49%;height:32px" value="登录">
			<a href="/bysj/resign" class="easyui-linkbutton" style="width:49%;height:32px">注册</a>
		</div>
		<script type="text/javascript">
			$(function(){
				createCode();
				$("#login").click(function(){
					if($('#username').hasClass("validatebox-invalid")||
						$('#password').hasClass("validatebox-invalid")){
						alert("帐号密码格式错误");
						return false;
					}else{
						return validateCode();
					}
				})
			})
		</script>
	</form>
	</div>
  </body>
</html>
