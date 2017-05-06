package com.hospital.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.DoctorDao;
import com.hospital.dao.SectionDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.PageBean;
import com.hospital.util.DataUtil;
import com.hospital.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class DoctorService {
	@Autowired
	private DoctorDao doctorDao;
	@Autowired
	private SectionDao sectionDao;
	public JSONObject getDoctors(String name,String gender,String section,int page,int rows){
		Doctor doctor = new Doctor();
		doctor.setDoctorname(name);
		System.out.println(gender);
		doctor.setDoctorgender(gender);
		if(!StringUtil.isEmpty(section) && !section.equals("0")){
			doctor.setSectionId(Integer.parseInt(section));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		List<Doctor> doctorList = doctorDao.getDoctors(doctor, pageBean);
		JSONArray jsonArray = new JSONArray();
		for (Doctor doctor2 : doctorList) {
			JSONObject mapOfColValues=new JSONObject();
			mapOfColValues.put("id", doctor2.getId());
			mapOfColValues.put("name", doctor2.getDoctorname());
			mapOfColValues.put("gender", doctor2.getDoctorgender());
			mapOfColValues.put("birthday", DataUtil.formatDate(doctor2.getBirthday(), "yyyy-MM-dd"));
			mapOfColValues.put("school", doctor2.getSchool());
			mapOfColValues.put("worktime", doctor2.getWorktime());
			mapOfColValues.put("sectionId", doctor2.getSectionId());
			mapOfColValues.put("title", doctor2.getTitle());
			mapOfColValues.put("skill", doctor2.getSkill());
			mapOfColValues.put("sectionName", sectionDao.getSectionNameById(doctor2.getSectionId()));
//			System.out.println(mapOfColValues.toString());
			jsonArray.add(mapOfColValues);
		}
		int total = doctorDao.getDoctorCount(doctor);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject addDoctor(String name,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		Doctor doctor = new Doctor();
		doctor.setDoctorname(name);;
		doctor.setDoctorgender(gender);;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=sdf.parse(birthday);  
		doctor.setBirthday(date);
		doctor.setSchool(school);
		doctor.setWorktime(Integer.parseInt(worktime));
		doctor.setSectionId(Integer.parseInt(sectionId));
		doctor.setTitle(title);
		doctor.setSkill(skill);
		JSONObject result=new JSONObject();
		if(doctorDao.addDoctor(doctor)){		
			result.put("success", "true");
		}else {
			result.put("errorMsg", "添加失败");
		}
		return result;
	}
	public JSONObject updateDoctor(Integer id,String name,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		Doctor doctor = new Doctor();
		doctor.setId(id);
		doctor.setDoctorname(name);
		doctor.setDoctorgender(gender);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=sdf.parse(birthday);  
		doctor.setBirthday(date);
		doctor.setSchool(school);
		doctor.setWorktime(Integer.parseInt(worktime));
		doctor.setSectionId(Integer.parseInt(sectionId));
		doctor.setTitle(title);
		doctor.setSkill(skill);
		JSONObject result=new JSONObject();
		if(doctorDao.updateDoctor(doctor)){
			result.put("success", "true");
		}else {
			result.put("errorMsg", "修改失败");
		}
		return result;
	}
	public JSONObject deleteDoctors(String ids){
		String[] idArray = ids.split(",");
		for (String string : idArray) {
			int id = Integer.parseInt(string);
			doctorDao.deleteDoctorById(id);
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
