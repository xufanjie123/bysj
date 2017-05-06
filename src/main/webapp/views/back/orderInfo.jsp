<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病人挂号管理</title>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="icon" href="<%=basePath%>/images/favicon.ico"  type="image/x-icon">
<script type="text/javascript">
//删除选中的挂号数据
function deleteYuYue(){
	//获得选中数据对象
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length==0){
		$.messager.alert("系统提示","请选择要删除的数据！");
		return;
	}
	var strIds=[];//要删除的序号组合
	for(var i=0;i<selectedRows.length;i++){
		strIds.push(selectedRows[i].id);
	}
	var ids=strIds.join(",");
	$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
		if(r){
			//ajax提交 delIds
			$.post("${root}" + "/back/orderInfo/deleteOrders",{ids:ids},function(result){
				if(result.success){
					$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert('系统提示',result.errorMsg);
				}
			},"json");
		}
	});
}

//查询符合条件的挂号信息
function searchYuYue(){
	$('#dg').datagrid('load',{
		patientname:$('#s_sickName').val(),
		stdate:$('#s_bGhDate').datebox("getValue"),
		eddate:$('#s_eGhDate').datebox("getValue"),
		doctorname:$('#s_doctorName').val(),
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
<table id="dg" title="预约信息" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${root }/back/orderInfo/orders" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="truename" width="100" align="center">预约人</th>
				<th field="patientgender" width="100"  align="center">性别</th>
				<th field="ordertime" width="100"  align="center">预约时间</th>
				<th field="doctorname" width="100"  align="center">医生姓名</th>
				<th field="sectionname" width="100"  align="center">科室</th>
				<th field="title" width="100" align="center">职称</th>
				<th field="waitnum" width="150" align="center">预约号</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:deleteYuYue()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
		<form id="export" method="post">
		&nbsp;预约人：&nbsp;<input type="text" name="s_sickName" id="s_sickName" size="10"/>
		&nbsp;挂号日期：&nbsp;<input class="easyui-datebox" name="s_bGhDate" id="s_bGhDate" editable="false" size="10"/>-><input class="easyui-datebox" name="s_eGhDate" id="s_eGhDate" editable="false" size="10"/>
		&nbsp;医生名称：&nbsp;<input class="text" id="s_doctorName" name="s_doctorName" size="10" /> 
		&nbsp;科室：&nbsp;<input class="easyui-combobox" id="s_keshiId" name="s_keshiId" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'sectionname',url:'${root }/back/orderInfo/sections'"/>
		<a href="javascript:searchYuYue()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</form></div>
	</div>
	
</body>
</html>