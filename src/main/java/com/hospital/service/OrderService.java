package com.hospital.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.DoctorWorkDao;
import com.hospital.dao.OrderDao;
import com.hospital.dao.PatientDao;
import com.hospital.dao.SectionDao;
import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.Orders;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.DataUtil;
import com.hospital.util.JsonUtil;
import com.hospital.util.MessageUtil;
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
	@Autowired
	private PatientDao patientDao;
	public JSONObject getOrders(int did,int page,int rows,String stdate,
			String eddate) throws Exception{
		Doctor doctor = new Doctor();
		doctor.setId(did);
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setRows(rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JsonUtil.formatRsToJsonArray(orderDao.orderList(pageBean, new Patient(), doctor, stdate, eddate));
		int total = orderDao.orderCount(new Patient(), doctor, stdate, eddate);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
	}
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
			if(doctorwork != null){
				int count = doctorwork.getOrderNum();
				count --;
				doctorwork.setOrderNum(count);
				doctorWorkDao.updateDoctorWork(doctorwork);
			}
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
	public JSONObject addOrder(String id,HttpSession session) throws HttpException, IOException{
		int pid = (Integer) session.getAttribute("currentUserId");
		int wid = Integer.parseInt(id);
		Doctorwork doctorwork = doctorWorkDao.getDoctorWorkById(wid);
		JSONObject result=new JSONObject();
		if(doctorwork.getOrderNum() == doctorwork.getMaxNum()){
			result.put("errorMsg", "预约失败，此医生此时段预约已满");
		}
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
		result.put("success", "true");
		Patient patient = patientDao.getPatientById(pid);
		StringBuffer sb = new StringBuffer();
		sb.append("预约成功，您的预约时间为");
		Calendar ca=Calendar.getInstance();
		ca.setTime((Timestamp)orders.getOrdertime());
		ca.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(ca.getTime());
		Timestamp timestamp = Timestamp.valueOf(dateStr);
		sb.append(DataUtil.formatDate((Timestamp)orders.getOrdertime(), "yyyy-MM-dd HH:mm") + "至" + DataUtil.formatDate(timestamp, "HH:mm"));
		sb.append(",您的预约号为");
		sb.append(orders.getWaitnum());
		sb.append(",请准时去医院");
		System.out.println(sb.toString());
		System.out.println(patient.getDescription());
		System.out.println(result.toString());
//		MessageUtil.sendMsg(patient.getDescription(), sb.toString());
		return result;
	}
}
