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
    <base href="<%=basePath%>">
    <title>登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>医院挂号系统</title>
	<style type="text/css" media="screen">
			.container{
				margin-top: 135px;
				/*background-color: yellow;*/
			}
		</style>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="./css/bootstrap.min.css" >
		<link rel="stylesheet" href="./css/bootstrap-theme.min.css" >
  </head>
  
  <body>
    <div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<form action="servlet/LoginServlet" method="POST" class="form-horizontal" role="form">
					<div class="form-group">
						<h1 class="text-center">医院挂号系统</h1>
						<hr class="divider">
					</div>
					<div class="form-group">
						<input class="form-control" type="text" name="username" value="" placeholder="请输入你的账号" required autofocus>
					</div>
					<div class="form-group">
						<input class="form-control" type="password" name="password" value="" placeholder="请输入你的密码">
					</div>
					<div class="form-group ">
						<div class="form-control">
							<p class="pull-right"><input type="radio" name="id" value="admin" >管理员</p>
							<p><input  type="radio" name="id" value="user" checked="checked" >病人</p>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-3 col-sm-offset-3">
								<button type="submit" name="submit" class="btn btn-primary btn-lg">登录</button>
							</div>
							<div class="col-sm-3 ">
								<a class="btn btn-primary btn-lg" data-toggle="modal" href='#modal-id'>注册</a>
								<div class="modal fade" id="modal-id">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
												<h4 class="modal-title">用户须知</h4>
											</div>
											<div class="modal-body">
												<p>请认真阅读本站用户协议，按照规定进行挂号！</p>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">不接受</button>
												<a href="resign.jsp" title="" class="btn btn-primary ">接受并注册</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>

		<script src="./scripts/jquery-1.11.1.js"></script>
		<script src="./scripts/bootstrap.min.js" ></script>
  </body>
</html>
