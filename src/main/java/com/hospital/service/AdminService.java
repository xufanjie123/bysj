package com.hospital.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.AdminDao;
import com.hospital.entity.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	public String login(String username,String password,HttpSession session){
		Admin admin = adminDao.getAdmin(username);
		if(admin != null && password.equals(admin.getPassword())){
			session.setAttribute("message", "登录成功，欢迎管理员" + admin.getUsername());
			session.setAttribute("currentUser", admin.getUsername());
			return "/back/main";
		}
		else{
			session.setAttribute("message", "用户不存在或密码错误");
			return "/login";
		}
	}
}
