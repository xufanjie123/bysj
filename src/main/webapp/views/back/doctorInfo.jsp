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
//打开新增患者对话框
function openDoctorAddDialog(){
	//在勾选情况下点击新增要先清除数据
	saveDoctorUrl= "${root}" + "/back/doctorInfo/saveDoctor";//为url赋值
	resetValue();
	$("#dlg").dialog("open").dialog("setTitle","添加医生信息");
}
//添加上班日期
function openDoctorWorkAddDialog(){
	resetValue1();
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要编辑的数据！");
		return;
	}
	var row=selectedRows[0];
	saveDoctorWorkUrl = "${root}" + "/back/doctorInfo/saveDoctorWork/"+row.id;
	$("#dlg1").dialog("open").dialog("setTitle","添加医生信息");
}

//重置对话框内数据
function resetValue(){
	$("#doctorName").val("");
	$("#sex").combobox("setValue","");
	$("#birthday").datebox("setValue","");
	$("#byyx").val("");
	$("#cynx").val("");
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
function closeDoctorWorkDialog(){
	$("#dlg1").dialog("close");
	resetValue();
}
//提交新增患者数据
function saveDoctor(){
	$("#fm").form("submit",{
		url:saveDoctorUrl,
		onSubmit:function(){
			if($('#sex').combobox("getValue")==""){
				$.messager.alert("系统提示","请选择性别");
				return false;
			}
			if($('#keshiId').combobox("getValue")==""){
				$.messager.alert("系统提示","请选择所属科室");
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
function saveDoctorWrok(){
	$("#fm1").form("submit",{
		url:saveDoctorWorkUrl,
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
				$("#dlg1").dialog("close");
				$("#dg").datagrid("reload");
			}
		}
	});
}
//删除选中的患者数据
function deleteDoctor(){
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
			$.post("${root}" + "/back/doctorInfo/deleteDoctors",{ids:ids},function(result){
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
function openDoctorModifyDialog(){
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要编辑的数据！");
		return;
	}
	var row=selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle","编辑医生资料");
	$("#doctorName").val(row.name);
	$("#sex").combobox("setValue",row.gender);
	$("#birthday").datebox("setValue",row.birthday);

	$("#byyx").val(row.school);
	$("#cynx").val(row.worktime);
	$("#keshiId").combobox("setValue",row.sectionId);
	
	$("#zhicheng").val(row.title);
	$("#zhuanchang").val(row.skill);
	
	
	saveDoctorUrl="${root}" + "/back/doctorInfo/saveDoctor/"+row.id;
}
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
	 pagination="true" rownumbers="true" url="${root }/back/doctorInfo/doctors" fit="true" toolbar="#tb">
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
			<a href="javascript:openDoctorAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openDoctorModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteDoctor()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:openDoctorWorkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加上班日期</a>
		</div>
		<div>
			<form id="export" method="post">
			&nbsp;姓名：&nbsp;<input type="text" name="name" id="s_doctorName" size="10"/>
			&nbsp;性别：&nbsp;<select class="easyui-combobox" id="s_sex" name="gender" editable="false" panelHeight="auto">
			    <option value="">请选择...</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
			&nbsp;科室：&nbsp;<input class="easyui-combobox" id="s_keshiId" name="section" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'sectionname',url:'${root }'+ '/back/doctorInfo/sections'"/>   
			<a href="javascript:searchDoctor()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</form>
		</div>
	</div>
		//添加医生
	<div id="dlg" class="easyui-dialog" style="width: 530px;height: 300px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>		
					<td>医生姓名：</td>
					<td><input type="text" name="name" id="doctorName" class="easyui-validatebox" required="true"/></td>
					<td>性别：</td>
					<td><select class="easyui-combobox" id="sex" name="gender" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select></td>
				</tr>
				<tr>
					<td>出生日期：</td>
					<td><input class="easyui-datebox" name="birthday" id="birthday" required="true" editable="false" /></td>
					<td>毕业院校：</td>
					<td><input type="text" name="school" id="byyx" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>从业年限：</td>
					<td><input type="text" name="worktime" id="cynx" class="easyui-validatebox" data-options="required:true,validType:'length[1,2]'"/></td>
					<td>科室：</td>
					<td><select class="easyui-combobox"  name="sectionId" id="keshiId" style="width: 155px" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'sectionname',url:'${root }'+ '/back/doctorInfo/sections'"></select> 
					</td>
				</tr>
				<tr>
					<td>职称：</td>
					<td><input type="text" name="title" id="zhicheng" class="easyui-validatebox" required="true"/></td>
					<td>专长：</td>
					<td><input type="text" name="skill" id="zhuanchang" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveDoctor()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDoctorDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<div id="dlg1" class="easyui-dialog" style="width: 270px;height: 180px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons1">
		<form id="fm1" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>上班日期：</td>
					<td><input class="easyui-datebox" name="workdate" id="time" required="true" editable="false" /></td>
				</tr>
				<tr>
					<td>最大预约数：</td>
					<td><input type="text" name="maxNum" id="maxnum" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons1">
		<a href="javascript:saveDoctorWrok()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDoctorWorkDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>