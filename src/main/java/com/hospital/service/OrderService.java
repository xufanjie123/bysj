package com.hospital.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.DoctorWorkDao;
import com.hospital.dao.OrderDao;
import com.hospital.dao.SectionDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.Orders;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.JsonUtil;
import com.hospital.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private SectionDao sectionDao;
	@Autowired
	private DoctorWorkDao doctorWorkDao;
	public JSONObject getOrders(HttpSession session,String stdate,
			String eddate,String doctorname,String section,int page,int rows) throws Exception{
		Patient patient = new Patient();
		int pid = (Integer) session.getAttribute("currentUserId");
		patient.setId(pid);
		Doctor doctor = new Doctor();
		if(!StringUtil.isEmpty(doctorname)){
			doctor.setDoctorname(doctorname);
		}
		if(!StringUtil.isEmpty(section) && !section.equals("0")){
			doctor.setSectionId(Integer.parseInt(section));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(orderDao.orderList(pageBean, patient, doctor, stdate, eddate));
		int total = orderDao.orderCount(patient, doctor, stdate, eddate);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject getOrders(String patientname,String stdate,
			String eddate,String doctorname,String section,int page,int rows) throws Exception{
		Patient patient = new Patient();
		if(!StringUtil.isEmpty(patientname)){
			patient.setTruename(patientname);
		}
		Doctor doctor = new Doctor();
		if(!StringUtil.isEmpty(doctorname)){
			doctor.setDoctorname(doctorname);
		}
		if(!StringUtil.isEmpty(section) && !section.equals("0")){
			doctor.setSectionId(Integer.parseInt(section));
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(orderDao.orderList(pageBean, patient, doctor, stdate, eddate));
		int total = orderDao.orderCount(patient, doctor, stdate, eddate);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
	public JSONObject deleteOrders(String ids){
		String[] idArray = ids.split(",");
		for (String string : idArray) {
			int id = Integer.parseInt(string);
			Orders orders = new OrderDao().getOrdersById(id);
			Doctorwork doctorwork2 = new Doctorwork();
			doctorwork2.setDoctorId(orders.getDoctorId());
			doctorwork2.setWorkdate(orders.getOrdertime());
			Doctorwork doctorwork = doctorWorkDao.getDoctorWork(doctorwork2);
			int count = doctorwork.getOrderNum();
			count --;
			doctorwork.setOrderNum(count);
			doctorWorkDao.updateDoctorWork(doctorwork);
			orderDao.deleteOrderById(id);
		}
		JSONObject result=new JSONObject();
		int delNums = idArray.length;
		if(delNums>0){
			result.put("success", "true");
			result.put("delNums", delNums);
		}else{
			result.put("errorMsg", "删除失败");
		}
		return result;
	}
	public JSONObject addOrder(String id,HttpSession session){
		int pid = (Integer) session.getAttribute("currentUserId");
		int wid = Integer.parseInt(id);
		Doctorwork doctorwork = doctorWorkDao.getDoctorWorkById(wid);	
		int waitNum = orderDao.getMaxWaitNum(doctorwork.getDoctorId(),doctorwork.getWorkdate());
		waitNum ++;
		Orders orders = new Orders();
		orders.setDoctorId(doctorwork.getDoctorId());
		orders.setPatientId(pid);
		orders.setOrdertime(doctorwork.getWorkdate());
		orders.setWaitnum(waitNum);
		orderDao.addOrder(orders);
		int count = doctorwork.getOrderNum();
		count ++;
		doctorwork.setOrderNum(count);
		doctorWorkDao.updateDoctorWork(doctorwork);
		JSONObject result=new JSONObject();
		result.put("success", "true");
		return result;
	}
}
