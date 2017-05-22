package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.Orders;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.DataUtil;
import com.hospital.util.DbUtil;
import com.hospital.util.HibernateUtil;
import com.hospital.util.StringUtil;

@Repository
public class OrderDao {
	public Orders getOrdersById(int id){
		Session session = HibernateUtil.getSession();
		String hql = "from Orders where id = " + id;
		Query query = session.createQuery(hql);
		List<Orders> ordersList = query.list();
		if(!ordersList.isEmpty()){
			return ordersList.get(0);
		}else {
			return null;
		}
	}
	public ResultSet orderList(PageBean pageBean, Patient patient,
			Doctor doctor, String stdate, String eddate) throws Exception {
		Connection con = new DbUtil().getCon();
		StringBuffer sb=new StringBuffer("SELECT o.id,p.truename,p.patientgender,p.patientage,p.description,o.ordertime,"
				+ "d.doctorname,s.sectionname,d.title,o.waitnum"
				+ " FROM orders o ,patient p ,doctor d,section s WHERE o.patientId=p.id AND o.doctorId=d.id AND d.sectionId=s.id");
		if(!StringUtil.isEmpty(patient.getTruename())){
			sb.append(" and p.truename like '%"+patient.getTruename()+"%'");
		}
		if(!StringUtil.isEmpty(doctor.getDoctorname())){
			sb.append(" and d.doctorName like '%"+doctor.getDoctorname()+"%'");
		}
		if(doctor.getSectionId() != null){
			sb.append(" and d.sectionId ="+doctor.getSectionId());
		}
		if(!StringUtil.isEmpty(stdate)){
			sb.append(" and TO_DAYS(o.ordertime)>=TO_DAYS('"+stdate+"')");
		}
		if(!StringUtil.isEmpty(eddate)){
			sb.append(" and TO_DAYS(o.ordertime)<=TO_DAYS('"+eddate+"')");
		}
		if(patient.getId() != null){
			sb.append(" and p.id = " + patient.getId());
		}
		if(doctor.getId() != null){
			sb.append(" and d.id = " + doctor.getId());
		}
		sb.append(" order by o.ordertime");
		// 分页
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}
//		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	public int orderCount(Patient patient,
			Doctor doctor, String stdate, String eddate) throws Exception {
		Connection con = new DbUtil().getCon();
		StringBuffer sb=new StringBuffer("select count(*) as total FROM orders o ,patient p ,doctor d,section s WHERE o.patientId=p.id AND o.doctorId=d.id AND d.sectionId=s.id");
		if(!StringUtil.isEmpty(patient.getTruename())){
			sb.append(" and p.truename like '%"+patient.getTruename()+"%'");
		}
		if(!StringUtil.isEmpty(doctor.getDoctorname())){
			sb.append(" and d.doctorName like '%"+doctor.getDoctorname()+"%'");
		}
		if(doctor.getSectionId() != null){
			sb.append(" and d.sectionId ="+doctor.getSectionId());
		}
		if(!StringUtil.isEmpty(stdate)){
			sb.append(" and TO_DAYS(o.ordertime)>=TO_DAYS('"+stdate+"')");
		}
		if(!StringUtil.isEmpty(eddate)){
			sb.append(" and TO_DAYS(o.ordertime)<=TO_DAYS('"+eddate+"')");
		}
		if(patient.getId() != null){
			sb.append(" and p.id = " + patient.getId());
		}
		if(doctor.getId() != null){
			sb.append(" and d.id = " + doctor.getId());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	public boolean deleteOrderById(int id){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String hql = "delete Orders o where o.id = " + id;
		Query query = session.createQuery(hql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return true;
	}
	public int getMaxWaitNum(int did,Timestamp date){
		Session session = HibernateUtil.getSession();
		String hql1 = "select max(waitnum) from Orders where doctorId = " + did
				+ " and ordertime = '" + DataUtil.formatDate(date, "yyyy-MM-dd HH:mm") + "'";
		Query query = session.createQuery(hql1);
		if(query.list().get(0) == null){
			return 0;
		}
		int count = Integer.parseInt(query.list().get(0).toString());
		return count;
	}
	public boolean addOrder(Orders orders){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(orders);
		transaction.commit();
		session.close();
		return true;
	}
}
