package com.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Section;
import com.hospital.service.DoctorService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/front/doctorInfo")
public class FrontDoctorListController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private SectionService sectionService;
	@RequestMapping("/doctors")
	@ResponseBody
	public JSONObject getFrontDoctors(String name,String gender,String section,int page,int rows){
		return doctorService.getDoctors(name, gender, section, page, rows);
	}
	@RequestMapping("/sections")
	@ResponseBody
	public List<Section> getSections(){
		return sectionService.getSections();
	}
}
