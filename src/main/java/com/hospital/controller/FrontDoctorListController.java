package com.hospital.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Doctor;
import com.hospital.entity.Section;
import com.hospital.service.DoctorService;
import com.hospital.service.DoctorWorkService;
import com.hospital.service.OrderService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/front/doctorInfo")
public class FrontDoctorListController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private SectionService sectionService;
	@Autowired 
	private DoctorWorkService doctorWorkService;
	@Autowired 
	private OrderService orderService;
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
	@RequestMapping("/doctorWorks/{id}")
	@ResponseBody
	public JSONObject doctorWorks(@PathVariable("id") Integer id,int page,int rows) throws Exception{
		return doctorWorkService.getDoctorworksByDoctorId(id, page, rows);
	}
	@RequestMapping("/saveOrder/{id}")
	@ResponseBody
	public JSONObject saveOrder(@PathVariable("id") String id,HttpSession session) throws HttpException, IOException{
		return orderService.addOrder(id, session);
	}
}
