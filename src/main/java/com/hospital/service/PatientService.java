package com.hospital.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.PatientDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.DataUtil;
import com.hospital.util.HibernateUtil;
import com.hospital.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class PatientService {
	@Autowired
	private PatientDao patientDao;
	public String savePatient(HttpSession session,Patient patient){
		int pid = (Integer) session.getAttribute("currentUserId");
		String pname = (String)session.getAttribute("currentUser");
		patient.setId(pid);
		patient.setUsername(pname);
		patientDao.updatePatient(patient);
		session.setAttribute("message", "修改成功");
		return "/front/patientInfo";
	}
	public String login(String username,String password,HttpSession session){
		Patient patient = patientDao.getPatient(username);
		if(patient != null && password.equals(patient.getPassword())){
			session.setAttribute("message", "登录成功，欢迎用户" + patient.getUsername());
			session.setAttribute("currentUser", patient.getUsername());
			session.setAttribute("currentUserId", patient.getId());
			return "/front/main";
		}
		else{
			session.setAttribute("message", "用户不存在或密码错误");
			return "/login";
		}
	}
	public String resign(Patient patient,HttpSession session){
		Patient patient2 = patientDao.getPatient(patient.getUsername());
		if(patient2 == null){
			patientDao.addPatient(patient);
			session.setAttribute("message", "注册成功");
			return "/login";
		}else{
			session.setAttribute("message", "该用户名已存在！");
			return "redirect:/resign";
		}
	}
	public JSONObject getPatients(String name,String truename,String gender,String age,
			int page,int rows){
		Patient patient = new Patient();
		if(!StringUtil.isEmpty(name)){
			patient.setUsername(name);
		}
		if(!StringUtil.isEmpty(truename)){
			patient.setTruename(truename);
		}
		if(!StringUtil.isEmpty(gender)){
			patient.setPatientgender(gender);
		}
		if(!StringUtil.isEmpty(age)){
			patient.setPatientage(Integer.parseInt(age));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		List<Patient> patientList = patientDao.getPatients(patient, pageBean);
		JSONArray jsonArray = new JSONArray();
		for (Patient patient2 : patientList) {
			JSONObject mapOfColValues=new JSONObject();
			mapOfColValues.put("id", patient2.getId());
			mapOfColValues.put("username", patient2.getUsername());
			mapOfColValues.put("password", patient2.getPassword());
			mapOfColValues.put("truename", patient2.getTruename());
			mapOfColValues.put("gender", patient2.getPatientgender());
			mapOfColValues.put("age", patient2.getPatientage());
			mapOfColValues.put("description", patient2.getDescription());
			jsonArray.add(mapOfColValues);
		}
		int total = patientDao.getPatientCount(patient);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject addPatient(String username,String password,String truename,String gender,
			String age,String description){
		Patient patient = new Patient();
		patient.setUsername(username);
		patient.setPassword(password);
		patient.setTruename(truename);
		patient.setPatientgender(gender);;
		patient.setPatientage(Integer.parseInt(age));
		patient.setDescription(description);
		JSONObject result=new JSONObject();
		if(patientDao.addPatient(patient)){		
			result.put("success", "true");
		}else {
			result.put("errorMsg", "添加失败");
		}
		return result;
	}
	public JSONObject updatePatient(Integer id,String username,String password,String truename,String gender,
			String age,String description){
		Patient patient = new Patient();
		patient.setId(id);
		patient.setUsername(username);
		patient.setPassword(password);
		patient.setTruename(truename);
		patient.setPatientgender(gender);
		patient.setPatientage(Integer.parseInt(age));;
		patient.setDescription(description);
		JSONObject result=new JSONObject();
		if(patientDao.updatePatient(patient)){		
			result.put("success", "true");
		}else {
			result.put("errorMsg", "修改失败");
		}
		return result;
	}
	public JSONObject deletePatinets(String ids){
		String[] idArray = ids.split(",");
		for (String string : idArray) {
			int id = Integer.parseInt(string);
			patientDao.deletePatientById(id);
		}
		JSONObject result=new JSONObject();
		int delNums = idArray.length;
		if(delNums>0){
			result.put("success", "true");
			result.put("delNums", delNums);
		}else{
			result.put("errorMsg", "删除失败");
		}
		return result;
	}
	public Patient getPatient(HttpSession session){
		int pid = (Integer) session.getAttribute("currentUserId");
		return patientDao.getPatientById(pid);
	}
	public void export(String name,String truename,String gender,String age,HttpServletResponse response){
		Patient patient = new Patient();
		if(!StringUtil.isEmpty(name)){
			patient.setUsername(name);
		}
		if(!StringUtil.isEmpty(truename)){
			patient.setTruename(truename);
		}
		if(!StringUtil.isEmpty(gender)){
			patient.setPatientgender(gender);
		}
		if(!StringUtil.isEmpty(age)){
			patient.setPatientage(Integer.parseInt(age));
		}
		List<Patient> patients= patientDao.getPatients(patient, null);
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet("患者信息");  
		sheet.setDefaultColumnWidth((short) 15);  
		HSSFCellStyle style = workbook.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFRow row = sheet.createRow(0);  
		HSSFCell cell =row.createCell((short) 0);
		cell.setCellValue("用户名");  
		cell.setCellStyle(style); 
		cell =row.createCell((short) 1);
		cell.setCellValue("密码");  
		cell.setCellStyle(style);  
		cell =row.createCell((short) 2);
		cell.setCellValue("真实姓名");  
		cell.setCellStyle(style);  
		cell =row.createCell((short) 3);
		cell.setCellValue("性别");  
		cell.setCellStyle(style); 
		cell =row.createCell((short) 4);
		cell.setCellValue("年龄");  
		cell.setCellStyle(style);
		cell =row.createCell((short) 5);
		cell.setCellValue("手机号码");  
		cell.setCellStyle(style);
		int i = 0;
		for (Patient patient2 : patients) {
			i++;
			row = sheet.createRow(i);  
			row.createCell((short) 0).setCellValue(patient2.getUsername());
			row.createCell((short) 1).setCellValue(patient2.getPassword());
			row.createCell((short) 2).setCellValue(patient2.getTruename());
			row.createCell((short) 3).setCellValue(patient2.getPatientgender());
			row.createCell((short) 4).setCellValue(patient2.getPatientage());
			row.createCell((short) 5).setCellValue(patient2.getDescription());
		}
		try  
		{  
			FileOutputStream fout = new FileOutputStream("./PatientInfo.xls");  
			workbook.write(fout);  
			fout.close();  
			
			String path="./PatientInfo.xls";
			File file = new File(path);  
			String filename = file.getName();  
			InputStream fis = new BufferedInputStream(new FileInputStream(path));  
			byte[] buffer = new byte[fis.available()];  
			fis.read(buffer);  
			fis.close();  
			response.reset();  
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));  
			response.addHeader("Content-Length", "" + file.length());  
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
			response.setContentType("application/octet-stream");  
			toClient.write(buffer);  
			toClient.flush();  
			toClient.close();
			file.delete();
		}  
		catch (Exception e)  
		{  
			e.printStackTrace();  
		} 
	}
}
