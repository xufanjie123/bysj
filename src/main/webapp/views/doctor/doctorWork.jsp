<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生值班管理</title>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="icon" href="<%=basePath%>/images/favicon.ico"  type="image/x-icon">
<script type="text/javascript">
var url;
function searchDoctorWork(){
	$('#dg').datagrid('load',{
		stdate:$('#s_bGhDate').datebox("getValue"),
		eddate:$('#s_eGhDate').datebox("getValue")
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
<table id="dg" title="值班信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${root }/doctor/doctorWorkInfo/doctorworks" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center" hidden="true">上班ID</th>
				<th field="doctorname" width="50" align="center" >医生姓名</th>
				<th field="doctorgender" width="30"  align="center">性别</th>
				<th field="sectionname" width="50" align="center">科室</th>
				<th field="workdate" width="80" align="center">上班时间</th>
				<th field="orderNum" width="30" align="center">预约数量</th>
				<th field="maxNum" width="30" align="center">最多预约数</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<form id="export" method="post">
			&nbsp;上班日期：&nbsp;<input class="easyui-datebox" name="s_bGhDate" id="s_bGhDate" editable="false" size="10"/>-><input class="easyui-datebox" name="s_eGhDate" id="s_eGhDate" editable="false" size="10"/>
			<a href="javascript:searchDoctorWork()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
	</div>
		

</body>
</html>