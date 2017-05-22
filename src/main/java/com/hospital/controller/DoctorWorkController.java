package com.hospital.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Section;
import com.hospital.service.DoctorWorkService;
import com.hospital.service.PatientService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/back/doctorWorkInfo")
public class DoctorWorkController {
	@Autowired
	private SectionService sectionService;
	@Autowired
	private DoctorWorkService doctorWorkService;
	@RequestMapping("/doctorworks")
	@ResponseBody
	public JSONObject getDoctorworks(String name,String stdate,
			String eddate,String section,int page,int rows) throws Exception{
		return doctorWorkService.getDoctorworks(name,stdate,eddate,section, page, rows);
	}
	@RequestMapping("/sections")
	@ResponseBody
	public List<Section> getSections(){
		return sectionService.getSections();
	}
	@RequestMapping("/saveDoctorWork/{id}")
	@ResponseBody
	public JSONObject updateDoctorWork(@PathVariable("id") Integer id,
			String orderNum,String maxNum) throws ParseException{
		return doctorWorkService.updateDoctorWork(id, orderNum, maxNum);
	}
	@RequestMapping("/deleteDoctorWork")
	@ResponseBody
	public JSONObject deleteDoctorWork(String ids){
		return doctorWorkService.deleteDoctorWork(ids);
	}
}
