package com.hospital.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Doctorwork;
import com.hospital.service.DoctorWorkService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/doctor/doctorWorkInfo")
public class DoctorDoctorWorkController {
	@Autowired
	private DoctorWorkService doctorWorkService;
	@RequestMapping("/doctorworks")
	@ResponseBody
	public JSONObject getDoctorworks(HttpSession session,String stdate,
			String eddate, int page , int rows) throws Exception{
		String name = (String) session.getAttribute("currentUser");
		return doctorWorkService.getDoctorworks(name,stdate,eddate,null, page, rows);
	}
}
