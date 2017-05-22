package com.hospital.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Section;
import com.hospital.service.DoctorWorkService;
import com.hospital.service.OrderService;
import com.hospital.service.PatientService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/front/doctorWorkInfo")
public class FrontDoctorWorkController {
	@Autowired
	private DoctorWorkService doctorWorkService;
	@Autowired
	private SectionService sectionService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/doctorworks")
	@ResponseBody
	public JSONObject getDoctorworks(String name,String stdate,
			String eddate,String section,int page,int rows) throws Exception{
		return doctorWorkService.getDoctorworks(name, stdate,eddate,section, page, rows);
	}
	@RequestMapping("/sections")
	@ResponseBody
	public List<Section> getSections(){
		return sectionService.getSections();
	}
	@RequestMapping("/addOrder")
	@ResponseBody
	public JSONObject addOrder(String id,HttpSession session) throws HttpException, IOException{
		return orderService.addOrder(id, session);
	}
}
