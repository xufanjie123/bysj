package com.hospital.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.DoctorDao;
import com.hospital.dao.SectionDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
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
	public String login(String username,String password,HttpSession session){
		Doctor doctor = doctorDao.getDoctor(username);
		if(doctor != null && password.equals(doctor.getDoctorpwd())){
			session.setAttribute("message", "登录成功，欢迎医生" + doctor.getDoctorname());
			session.setAttribute("currentUser", doctor.getDoctorname());
			session.setAttribute("currentUserId", doctor.getId());
			return "/doctor/main";
		}
		else{
			session.setAttribute("message", "用户不存在或密码错误");
			return "/login";
		}
	}
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
			mapOfColValues.put("pwd", doctor2.getDoctorpwd());
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
	public JSONObject addDoctor(String name,String password,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		Doctor doctor = new Doctor();
		doctor.setDoctorname(name);
		doctor.setDoctorpwd(password);
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
		if(doctorDao.addDoctor(doctor)){		
			result.put("success", "true");
		}else {
			result.put("errorMsg", "添加失败");
		}
		return result;
	}
	public JSONObject updateDoctor(Integer id,String name,String password,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		Doctor doctor = new Doctor();
		doctor.setId(id);
		doctor.setDoctorname(name);
		doctor.setDoctorpwd(password);
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
	public void export(String name,String gender,String section,HttpServletResponse response){
		Doctor doctor = new Doctor();
		doctor.setDoctorname(name);
		doctor.setDoctorgender(gender);
		if(!StringUtil.isEmpty(section) && !section.equals("0")){
			doctor.setSectionId(Integer.parseInt(section));
		}
		List<Doctor> doctors = doctorDao.getDoctors(doctor, null);
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet("医生信息");  
		sheet.setDefaultColumnWidth((short) 15);  
		HSSFCellStyle style = workbook.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFRow row = sheet.createRow(0);  
		HSSFCell cell =row.createCell((short) 0);
		cell.setCellValue("姓名");  
		cell.setCellStyle(style); 
		cell =row.createCell((short) 1);
		cell.setCellValue("密码");  
		cell.setCellStyle(style);  
		cell =row.createCell((short) 2);
		cell.setCellValue("性别");  
		cell.setCellStyle(style);  
		cell =row.createCell((short) 3);
		cell.setCellValue("出生日期");  
		cell.setCellStyle(style); 
		cell =row.createCell((short) 4);
		cell.setCellValue("毕业院校");  
		cell.setCellStyle(style);
		cell =row.createCell((short) 5);
		cell.setCellValue("从业年限");  
		cell.setCellStyle(style);
		cell =row.createCell((short) 6);
		cell.setCellValue("科室");  
		cell.setCellStyle(style);
		cell =row.createCell((short) 7);
		cell.setCellValue("职称");  
		cell.setCellStyle(style);
		cell =row.createCell((short) 8);
		cell.setCellValue("专长");  
		cell.setCellStyle(style);
		int i = 0;
		for (Doctor doctor2 : doctors) {
			i++;
			row = sheet.createRow(i);  
			row.createCell((short) 0).setCellValue(doctor2.getDoctorname());
			row.createCell((short) 1).setCellValue(doctor2.getDoctorpwd());
			row.createCell((short) 2).setCellValue(doctor2.getDoctorgender());
			row.createCell((short) 3).setCellValue(DataUtil.formatDate(doctor2.getBirthday(), "yyyy-MM-dd"));
			row.createCell((short) 4).setCellValue(doctor2.getSchool());
			row.createCell((short) 5).setCellValue(doctor2.getWorktime());
			row.createCell((short) 6).setCellValue(sectionDao.getSectionNameById(doctor2.getSectionId()));
			row.createCell((short) 7).setCellValue(doctor2.getTitle());
			row.createCell((short) 8).setCellValue(doctor2.getSkill());
		}
		try  
		{  
			FileOutputStream fout = new FileOutputStream("./DoctorInfo.xls");  
			workbook.write(fout);  
			fout.close();  
			
			String path="./DoctorInfo.xls";
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
