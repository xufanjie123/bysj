package com.hospital.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.dao.DoctorDao;
import com.hospital.dao.DoctorWorkDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.PageBean;
import com.hospital.util.JsonUtil;
import com.hospital.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class DoctorWorkService {
	@Autowired
	private DoctorWorkDao doctorWorkDao;
	@Autowired 
	private DoctorDao doctorDao;
	public JSONObject getDoctorworksByDoctorId(Integer id,int page,int rows) throws Exception{
		Doctor doctor = doctorDao.getDoctor(id);
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(doctorWorkDao.doctorListRs(pageBean,null,null,doctor));
		int total = doctorWorkDao.doctorCount(doctor);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject getDoctorworks(String name,String stdate,
			String eddate,String section,int page,int rows) throws Exception{
		Doctor doctor = new Doctor();
		if(!StringUtil.isEmpty(name)){
			doctor.setDoctorname(name);
		}if(!StringUtil.isEmpty(section)&& !section.equals("0")){
			doctor.setSectionId(Integer.parseInt(section));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(doctorWorkDao.doctorListRs(pageBean,stdate,eddate,doctor));
		int total = doctorWorkDao.doctorCount(doctor);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject saveDoctorWork(Integer id,String workdate,String worktime,String maxNum) throws ParseException{
		Doctorwork doctorwork = new Doctorwork();
		doctorwork.setDoctorId(id);
		StringBuffer sb = new StringBuffer(workdate);
		if(Integer.parseInt(worktime) == 1){
			sb.append(" 8:00:00");
		}else if(Integer.parseInt(worktime) == 2){
			sb.append(" 10:00:00");
		}else if(Integer.parseInt(worktime) == 3){
			sb.append(" 13:00:00");
		}else if(Integer.parseInt(worktime) == 4){
			sb.append(" 15:00:00");
		}
		Timestamp date= Timestamp.valueOf(sb.toString());
		doctorwork.setWorkdate(date);
		doctorwork.setOrderNum(0);
		doctorwork.setMaxNum(Integer.parseInt(maxNum));
		
		JSONObject result=new JSONObject();
		Doctorwork doctorwork2 = doctorWorkDao.getDoctorWork(doctorwork);
		if(doctorwork2 != null){
			result.put("errorMsg", "添加失败,此医生此时间段已有工作安排");
			return result;
		}
		if(doctorWorkDao.addDoctorWork(doctorwork)){
			result.put("success", "true");
		}else{
			result.put("errorMsg", "添加失败");
		}
		return result;
	}
	public JSONObject updateDoctorWork(@PathVariable("id") Integer id,
			String orderNum,String maxNum) throws ParseException{
		Doctorwork doctorwork = doctorWorkDao.getDoctorWorkById(id);
		doctorwork.setOrderNum(Integer.parseInt(orderNum));
		doctorwork.setMaxNum(Integer.parseInt(maxNum));
		JSONObject result=new JSONObject();
		if(Integer.parseInt(orderNum) > Integer.parseInt(maxNum)){
			result.put("errorMsg", "添加失败,预约数量不能大于最大预约数");
			return result;
		}
		if(doctorWorkDao.updateDoctorWork(doctorwork)){
			result.put("success", "true");
		}else{
			result.put("errorMsg", "添加失败");
		}
		return result;
	}
	public JSONObject deleteDoctorWork(String ids){
		String[] idArray = ids.split(",");
		for (String string : idArray) {
			int id = Integer.parseInt(string);
			doctorWorkDao.deleteDoctorWorkById(id);
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
