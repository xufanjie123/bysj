package com.hospital.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.entity.Section;
import com.hospital.service.OrderService;
import com.hospital.service.SectionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/front/frontOrderInfo")
public class FrontOrderInfoController {
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
	public JSONObject getOrders(HttpSession session,String stdate,
			String eddate,String doctorname,String section,int page,int rows) throws Exception{
		return orderService.getOrders(session, stdate, eddate, doctorname, section, page, rows);
	}
}
