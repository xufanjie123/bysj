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
//重置对话框内数据
function resetValue(){
	$("#doctorName").val("");
	$("#time").datebox("setValue","");
	$("#num").val("");
	$("#maxnum").val("");
}
//关闭对话框
function closeDoctorWorkDialog(){
	$("#dlg").dialog("close");
	resetValue();
}
//提交新增患者数据
function saveDoctorWork(){
	$("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			return $(this).form("validate");
		},
		success:function(result){
			var res = JSON.parse(result);
			if(res.errorMsg){
				$.messager.alert("系统提示",res.errorMsg);
				return;
			}else{
				$.messager.alert("系统提示","保存成功");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			}
		}
	});
}
//删除选中的患者数据
function deleteDoctorWork(){
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
			$.post("${root}" + "/back/doctorWorkInfo/deleteDoctorWork",{ids:ids},function(result){
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
//修改患者资料
function openDoctorWorkModifyDialog(){
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要编辑的数据！");
		return;
	}
	var row=selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle","编辑医生上班信息");
	$("#doctorName").val(row.doctorname);
	$("#time").datebox("setValue",row.workdate);
	$("#num").val(row.orderNum);
	$("#maxnum").val(row.maxNum);
	url = "${root}" + "/back/doctorWorkInfo/saveDoctorWork/"+row.id;
}
//查询符合条件的用户
function searchDoctorWork(){
	$('#dg').datagrid('load',{
		name:$('#s_doctorName').val(),
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
<table id="dg" title="值班信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${root }/back/doctorWorkInfo/doctorworks" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center" hidden="true">上班ID</th>
				<th field="doctorname" width="50" align="center" >医生姓名</th>
				<th field="doctorgender" width="30"  align="center">性别</th>
				<th field="sectionname" width="50" align="center">科室</th>
				<th field="workdate" width="80" align="center">上班日期</th>
				<th field="orderNum" width="30" align="center">预约数量</th>
				<th field="maxNum" width="30" align="center">最多预约数</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:openDoctorWorkModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteDoctorWork()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			<form id="export" method="post">
			&nbsp;姓名：&nbsp;<input type="text" name="s_doctorName" id="s_doctorName" size="10"/>
			&nbsp;科室：&nbsp;<input class="easyui-combobox" id="s_keshiId" name="s_keshiId" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'sectionname',url:'${root }/back/doctorWorkInfo/sections'"/>   
			<a href="javascript:searchDoctorWork()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
	</div>
		
	<div id="dlg" class="easyui-dialog" style="width: 300px;height: 320px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>医生姓名：</td>
					<td><input type="text" id="doctorName" class="easyui-validatebox" disabled="disabled" required="true"/></td>
				</tr>
				<tr>
					<td>上班日期：</td>
					<td><input class="easyui-datebox" name="workdate" id="time" disabled="disabled" required="true" editable="false" /></td>
				</tr>
				<tr>
					<td>预约数量：</td>
					<td><input type="text" name="orderNum" id="num" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>最大预约数：</td>
					<td><input type="text" name="maxNum" id="maxnum" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveDoctorWork()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDoctorWorkDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>