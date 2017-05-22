<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病人信息管理</title>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="icon" href="<%=basePath%>/images/favicon.ico"  type="image/x-icon">
<script type="text/javascript">
var savePatientUrl;
//打开新增患者对话框
function openSickAddDialog(){
	//在勾选情况下点击新增要先清除数据
	resetValue();
	$("#dlg").dialog("open").dialog("setTitle","添加患者信息");
	savePatientUrl = "${root}" + "/back/patientInfo/savePatient";//为url赋值
}
//重置对话框内数据
function resetValue(){
	$("#sickName").val("");
	$("#sickPwd").val("");
	$("#trueName").val("");
	$("#sex").combobox("setValue","");
	$("#age").val("");
	$("#sid").val("");
	$("#sickDesc").val("");
}
//关闭对话框
function closeSickDialog(){
	$("#dlg").dialog("close");
	resetValue();
}
//提交新增患者数据
function saveSick(){
	$("#fm").form("submit",{
		url:savePatientUrl,
		onSubmit:function(){
			if($('#sex').combobox("getValue")==""){
				$.messager.alert("系统提示","请选择性别");
				return false;
			}
			return $(this).form("validate");
		},
		success:function(result){
			if(result.errorMsg){
				$.messager.alert("系统提示",result.errorMsg);
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
function deleteSick(){
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
			$.post("${root}" + "/back/patientInfo/deletePatients",{ids:ids},function(result){
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
function openSickModifyDialog(){
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要编辑的数据！");
		return;
	}
	var row=selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle","编辑患者资料");
	$("#sickName").val(row.username);
	$("#sickPwd").val(row.password);
	$("#trueName").val(row.truename);
	$("#sex").combobox("setValue",row.gender);
	$("#age").val(row.age);
	$("#sickDesc").val(row.description);
	
	savePatientUrl = "${root}" + "/back/patientInfo/savePatient/"+row.id;
}
//查询符合条件的用户
function searchSick(){
	$('#s_sickName1').val($('#s_sickName').val());
	$('#s_trueName1').val($('#s_trueName').val());
	$('#s_sex1').val($('#s_sex').combobox("getValue"));
	$('#s_age1').val($('#s_age').val());
	$('#dg').datagrid('load',{
		name:$('#s_sickName').val(),
		truename:$('#s_trueName').val(),
		gender:$('#s_sex').combobox("getValue"),
		age:$('#s_age').val()
	});
}
function export1(){
	$('#submit11').trigger("click");
}
</script>
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${root }/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${root }/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
</head>
<body style="margin:5px">
<table id="dg" title="患者信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${root }/back/patientInfo/patients" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="username" width="100" align="center" >用户名</th>
				<th field="password" width="100" align="center" >密码</th>
				<th field="truename" width="100"  align="center">真实姓名</th>
				<th field="gender" width="50"  align="center">性别</th>
				<th field="age" width="100" align="center">年龄</th>
				<th field="description" width="200" align="center">手机号码</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:openSickAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openSickModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteSick()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:export1()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">导出</a>
		</div>
		<div>
			<form id="export" method="post">
			&nbsp;用户名：&nbsp;<input type="text" name="name" id="s_sickName" size="10"/>
			&nbsp;真实姓名：&nbsp;<input type="text" name="truename" id="s_trueName" size="10"/>
			&nbsp;性别：&nbsp;<select class="easyui-combobox" id="s_sex" name="gender" editable="false" panelHeight="auto">
			    <option value="">请选择...</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
			&nbsp;年龄：&nbsp;<input type="text" name="age" id="s_age" size="10"/>
			<a href="javascript:searchSick()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
		<div>
			<form id="export11" style="display: none;" method="post" action="${root }/back/patientInfo/export">
			<input type="hidden" name="name" id="s_sickName1" value=""/>
			<input type="hidden" name="truename" id="s_sickName1" value=""/>
			<input type="hidden" name="gender" id="s_sex1" value=""/>
			<input type="hidden" name="age" id="s_age1" value=""/>
			<input type="submit" id="submit11">
			</form>	
		</div>
	</div>
		
	<div id="dlg" class="easyui-dialog" style="width: 570px;height: 340px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>		
					<td>昵称：</td>
					<td><input type="text" name="username" id="sickName" class="easyui-validatebox" required="true"/></td>
					<td>密码：</td>
					<td><input type="text" name="password" id="sickPwd" class="easyui-validatebox" required="true"/></td>
					
				</tr>
				<tr>
					<td>真实姓名：</td>
					<td><input type="text" name="truename" id="trueName" class="easyui-validatebox" required="true"/></td>
					<td>性别：</td>
					<td><select class="easyui-combobox" id="sex" name="gender" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select></td>
				</tr>
				<tr>
				
					<td>年龄：</td>
					<td><input type="text" name="age" id="age" class="easyui-validatebox" data-options="required:true"/></td>
				
				</tr>
				<tr>
					<td valign="top">手机号</td>
					<td colspan="4"><input type="text" name="description" id="sickDesc" class="easyui-validatebox" data-options="required:true" validType="length[11,11]"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveSick()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeSickDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>