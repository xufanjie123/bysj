package com.hospital.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.entity.Admin;
import com.hospital.entity.Patient;
import com.hospital.service.LoginService;

@Controller
public class LoginPageController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/resign")
	public String resign(){
		return "/resign";
	}
	@RequestMapping("/realresign")
	public String realresign(Patient patient,HttpSession session) throws UnsupportedEncodingException{
		Patient patient2 = loginService.getPatient(patient.getUsername());
		if(patient2 == null){
			loginService.addPatient(patient);
			session.setAttribute("message", "注册成功");
			return "/login";
		}else{
			session.setAttribute("message", "该用户名已存在！");
			return "redirect:/resign";
		}
	}
	@RequestMapping("/login")
	public String login(String username,String password,String id,HttpSession session){
		//如果当前有登录状态 直接进入
		if(session.getAttribute("currentUser") != null && !session.getAttribute("currentUser").equals("")){
			return "/back/main";
		}
		if(id.equals("admin")){
			System.out.println("------");
			System.out.println(username + " " +password);
			Admin admin = loginService.getAdmin(username);
			if(password.equals(admin.getPassword())){
				session.setAttribute("message", "登录成功，欢迎管理员" + admin.getUsername());
				session.setAttribute("currentUser", admin.getUsername());
				return "/back/main";
			}
			else{
				session.setAttribute("message", "用户不存在或密码错误");
				return "/login";
			}
		}
		else{
			return null;
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("currentUser");
		return "/login";
	}
}
