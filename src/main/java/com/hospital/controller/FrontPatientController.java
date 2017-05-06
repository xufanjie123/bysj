package com.hospital.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Patient;
import com.hospital.service.PatientService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/front/patientInfo")
public class FrontPatientController {
	@Autowired
	private PatientService patientService;
	@RequestMapping("/patient")
	@ResponseBody
	public Patient getPatient(HttpSession session){
		return patientService.getPatient(session);
	}
	@RequestMapping("/savePatient")
	public String savePatient(HttpSession session,Patient patient){
		return patientService.savePatient(session,patient);
	}
}
