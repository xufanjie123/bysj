package com.hospital.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.entity.Admin;
import com.hospital.entity.Patient;
import com.hospital.service.AdminService;
import com.hospital.service.LoginService;
import com.hospital.service.PatientService;

@Controller
public class LoginPageController {
	@Autowired
	private AdminService adminServie;
	@Autowired
	private PatientService patientService;
	@RequestMapping("/resign")
	public String resign(){
		return "/resign";
	}
	@RequestMapping("/realresign")
	public String realresign(Patient patient,HttpSession session) throws UnsupportedEncodingException{
		return patientService.resign(patient, session);
	}
	@RequestMapping("/login")
	public String login(String username,String password,String id,HttpSession session){
		if(id.equals("admin")){
			return adminServie.login(username, password, session);
		}
		else{
			return patientService.login(username, password, session);
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("currentUser");
		return "/login";
	}
}
