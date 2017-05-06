<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生信息管理</title>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="icon" href="<%=basePath%>/images/favicon.ico"  type="image/x-icon">
<script type="text/javascript">
//查询符合条件的用户
function searchDoctor(){
	$('#dg').datagrid('load',{
		name:$('#s_doctorName').val(),
		gender:$('#s_sex').combobox("getValue"),
		section:$('#s_keshiId').combobox("getValue")
	});
}
</script>
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
</head>
<body style="margin:5px">
<table id="dg" title="医生信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${root }/front/doctorInfo/doctors" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center" hidden="true">用户ID</th>
				<th field="name" width="120" align="center" >医生姓名</th>
				<th field="gender" width="80"  align="center">性别</th>
				<th field="birthday" width="120" align="center">出生日期</th>
				<th field="school" width="150" align="center">毕业院校</th>
				<th field="worktime" width="120" align="center">从业年限</th>
				<th field="sectionName" width="80" align="center">科室</th>
				<th field="title" width="80" align="center">职称</th>
				<th field="skill" width="120" align="center">专长</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<form id="export" method="post">
			&nbsp;姓名：&nbsp;<input type="text" name="name" id="s_doctorName" size="10"/>
			&nbsp;性别：&nbsp;<select class="easyui-combobox" id="s_sex" name="gender" editable="false" panelHeight="auto">
			    <option value="">请选择...</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
			&nbsp;科室：&nbsp;<input class="easyui-combobox" id="s_keshiId" name="section" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'sectionname',url:'${root }'+ '/front/doctorInfo/sections'"/>   
			<a href="javascript:searchDoctor()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
	</div>
</body>
</html>