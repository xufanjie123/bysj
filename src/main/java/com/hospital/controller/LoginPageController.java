package com.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
	@RequestMapping("/resign")
	public String resign(){
		return "/resign";
	}
	@RequestMapping("/realresign")
	public String realresign(){
		
	}
}
