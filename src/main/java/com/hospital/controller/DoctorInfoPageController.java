package com.hospital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Doctor;
import com.hospital.entity.Section;
import com.hospital.service.DoctorService;
import com.hospital.service.DoctorWorkService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/back/doctorInfo")
public class DoctorInfoPageController {
	@Autowired
	private SectionService sectionService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private DoctorWorkService doctorWorkService;
	@RequestMapping("/sections")
	@ResponseBody
	public List<Section> getSections(){
		return sectionService.getSections();
	}
	@RequestMapping("/doctors")
	@ResponseBody
	public JSONObject getDoctors(String name,String gender,String section,int page,int rows){
		return doctorService.getDoctors(name, gender, section, page, rows);
	}
	@RequestMapping("/saveDoctor")
	@ResponseBody
	public JSONObject saveDoctor(String name,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		return doctorService.addDoctor(name, gender, birthday, school, worktime, sectionId, title, skill);
	}
	@RequestMapping("/saveDoctor/{id}")
	@ResponseBody
	public JSONObject updateDoctor(@PathVariable("id") Integer id,String name,String gender,String birthday,String school,
			String worktime,String sectionId,String title,String skill) throws ParseException{
		return doctorService.updateDoctor(id, name, gender, birthday, school, worktime, sectionId, title, skill);
	}
	@RequestMapping("/deleteDoctors")
	@ResponseBody
	public JSONObject deleteDoctors(String ids){
		return doctorService.deleteDoctors(ids);
	}
	@RequestMapping("/saveDoctorWork/{id}")
	@ResponseBody
	public JSONObject saveDoctorWork(@PathVariable("id") Integer id,String workdate,String maxNum) throws ParseException{
		return doctorWorkService.saveDoctorWork(id, workdate, maxNum);
	}
}
