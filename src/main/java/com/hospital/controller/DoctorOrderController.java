package com.hospital.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.service.OrderService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/doctor/orderInfo")
public class DoctorOrderController {
	@Autowired
	private OrderService orderService;
	@RequestMapping("/orders")
	@ResponseBody
	public JSONObject getOrders(HttpSession session,String stdate,
			String eddate,int page,int rows) throws Exception{
		int did = (Integer) session.getAttribute("currentUserId");
		return orderService.getOrders(did,page,rows,stdate,eddate);
	}
}
