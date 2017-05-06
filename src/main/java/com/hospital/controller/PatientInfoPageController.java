package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.service.PatientService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/back/patientInfo")
public class PatientInfoPageController {
	@Autowired
	PatientService patientServie;
	@RequestMapping("/patients")
	@ResponseBody
	public JSONObject getPatients(String name,String truename,String gender,String age,int page,int rows){
		return patientServie.getPatients(name, truename, gender, age, page, rows);
	}
	@RequestMapping("/savePatient")
	@ResponseBody
	public JSONObject addPatient(String username,String password,String truename,String gender,
			String age,String description){
		return patientServie.addPatient(username, password, truename, gender, age, description);
	}
	@RequestMapping("/savePatient/{id}")
	@ResponseBody
	public JSONObject updatePatient(@PathVariable("id") Integer id,String username,String password,String truename,String gender,
			String age,String description){
		return patientServie.updatePatient(id, username, password, truename, gender, age, description);
	}
	@RequestMapping("/deletePatients")
	@ResponseBody
	public JSONObject deletePatinets(String ids){
		return patientServie.deletePatinets(ids);
	}
}
