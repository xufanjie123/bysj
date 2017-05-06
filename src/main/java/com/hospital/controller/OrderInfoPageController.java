package com.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Section;
import com.hospital.service.OrderService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/back/orderInfo")
public class OrderInfoPageController {
	@Autowired
	private SectionService sectionService;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/sections")
	@ResponseBody
	public List<Section> getSections(){
		return sectionService.getSections();
	}
	@RequestMapping("/orders")
	@ResponseBody
	public JSONObject getOrders(String patientname,String stdate,
			String eddate,String doctorname,String section,int page,int rows) throws Exception{
		return orderService.getOrders(patientname, stdate, eddate, doctorname, section,page,rows);
	}
	@RequestMapping("/deleteOrders")
	@ResponseBody
	public JSONObject deleteOrders(String ids){
		return orderService.deleteOrders(ids);
	}
}
