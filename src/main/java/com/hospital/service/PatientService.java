package com.hospital.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.PatientDao;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class PatientService {
	@Autowired
	private PatientDao patientDao;
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
}
