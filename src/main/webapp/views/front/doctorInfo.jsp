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
var saveDoctorUrl;
var saveDoctorWorkUrl;

//重置对话框内数据
function resetValue(){
	$("#doctorName").val("");
	$("#sex").combobox("setValue","");
	$("#birthday").datebox("setValue","");
	$("#byyx").val("");
	$("#cynx").val("");
	$("#doctorPwd").val("");
	$("#keshiId").combobox("setValue","0");
	$("#zhicheng").val("");
	$("#zhuanchang").val("");
	
}
function resetValue1(){
	$("#time").val("");
	$("#num").val("");
	$("#maxnum").val("");
}
//关闭对话框
function closeDoctorDialog(){
	$("#dlg").dialog("close");
	resetValue();
}

//修改患者资料
function openDoctorModifyDialog(){
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一个要挂号的医生！");
		return;
	}
	var row=selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle","医生上班信息");
	$('#dg2').datagrid({
		url:"${root}" + "/front/doctorInfo/doctorWorks/"+row.id
	});
}
//查询符合条件的用户
function searchDoctor(){
	$('#s_doctorName1').val($('#s_doctorName').val());
	$('#s_sex1').val($('#s_sex').combobox("getValue"));
	$('#s_keshiId1').val($('#s_keshiId').combobox("getValue"));
	$('#dg').datagrid('load',{
		name:$('#s_doctorName').val(),
		gender:$('#s_sex').combobox("getValue"),
		section:$('#s_keshiId').combobox("getValue")
	});
}
function addOrder(){
	var selectedRows=$("#dg2").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一个要预约的时间段！");
		return;
	}
	var row=selectedRows[0];
	if(row.orderNum == row.maxNum){
		$.messager.alert("系统提示","此医生该时间段预约已满");
		return;
	}
	var url = "${root}" + "/front/doctorInfo/saveOrder/"+row.id;
	$.post(url,null,function(result){
		if(result.success){
			$.messager.alert("系统提示","您已成功预约");
			$("#dg").datagrid("reload");
			$("#dlg").dialog("close");
		}else{
			$.messager.alert('系统提示',result.errorMsg);
		}
	},'json');
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
			<a href="javascript:openDoctorModifyDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">挂号</a>
		</div>
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
		<div>
			<form id="export11" style="display: none;" method="post" action="${root }/back/doctorInfo/export">
			<input type="hidden" name="name" id ="s_doctorName1" value=""/>
			<input type="hidden" name="gender" id="s_sex1" value=""/>
			<input type="hidden" name="section" id="s_keshiId1" value="0"/>
			<input type="submit" id="submit11">
			</form>	
		</div>
	</div>
		//添加医生
	<div id="dlg" class="easyui-dialog" style="width: 730px;height: 520px;padding: 10px 20px"
		closed="true"  buttons="#dlg-buttons">
		<table id="dg2" title="医生信息" class="easyui-datagrid" fitColumns="true" pagination="true"
	   fit="true" >
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center" hidden="true"></th>
				<th field="doctorname" width="120" align="center" >医生姓名</th>
				<th field="doctorgender" width="80"  align="center">性别</th>
				<th field="sectionname" width="120" align="center">科室</th>
				<th field="workdate" width="150" align="center">上班时间</th>
				<th field="orderNum" width="120" align="center">预约数量</th>
				<th field="maxNum" width="80" align="center">最大数量</th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:addOrder()" class="easyui-linkbutton" iconCls="icon-ok">挂号</a>
		<a href="javascript:closeDoctorDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>