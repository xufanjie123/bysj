package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Doctor;
import com.hospital.entity.Doctorwork;
import com.hospital.entity.PageBean;
import com.hospital.util.DataUtil;
import com.hospital.util.DbUtil;
import com.hospital.util.HibernateUtil;
import com.hospital.util.StringUtil;
@Repository
public class DoctorWorkDao {
	public int doctorCount(Doctor doctor) throws Exception {
		Connection con = new DbUtil().getCon();
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from doctorwork w,section s,doctor d where d.id=w.doctorId and d.sectionId=s.id");
		if (doctor.getSectionId() != null) {
			sb.append(" and d.sectionId='" + doctor.getSectionId() + "'");
		}
		if (!StringUtil.isEmpty(doctor.getDoctorname())) {
			sb.append(" and d.doctorname like '%" + doctor.getDoctorname()
					+ "%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	public ResultSet doctorListRs(PageBean pageBean,
			Doctor doctor) throws Exception {
		System.out.println(doctor);
		Connection con = new DbUtil().getCon();
		StringBuffer sb = new StringBuffer(
				"select w.id,d.doctorname,d.doctorgender,s.sectionname,w.workdate,w.ordernum,w.maxnum"
				+ " from doctorwork w,section s,doctor d where d.id=w.doctorId and d.sectionId=s.id");
		if (doctor.getSectionId() != null) {
			sb.append(" and d.sectionId = '" + doctor.getSectionId() + "'");
		}
		if (!StringUtil.isEmpty(doctor.getDoctorname())) {
			sb.append(" and d.doctorname like '%" + doctor.getDoctorname()
					+ "%'");
		}
		// 分页
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}
		// System.out.println("doctor select sql:"+sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public boolean addDoctorWork(Doctorwork doctorwork){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(doctorwork);
		transaction.commit();
		session.close();
		return true;
	}
	public Doctorwork getDoctorWork(Doctorwork doctorwork){
		Session session = HibernateUtil.getSession();
		String hql = "from Doctorwork where doctorId = " + doctorwork.getDoctorId() +
				" and workdate = '" + DataUtil.formatDate(doctorwork.getWorkdate(), "yyyy-MM-dd")
				 + "'";
		Query query = session.createQuery(hql);
		List<Doctorwork> doctorworks = query.list();
		if(!doctorworks.isEmpty()){
			return doctorworks.get(0);
		}else{
			return null;
		}
	}
	public boolean updateDoctorWork(Doctorwork doctorwork){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(doctorwork);
		transaction.commit();
		session.close();
		return true;
	}
	public Doctorwork getDoctorWorkById(Integer id){
		Session session = HibernateUtil.getSession();
		String hql = "from Doctorwork where id = " + id;
		Query query = session.createQuery(hql);
		List<Doctorwork> doctorworks = query.list();
		if(!doctorworks.isEmpty()){
			return doctorworks.get(0);
		}else {
			return null;
		}
	}
	public boolean deleteDoctorWorkById(Integer id){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String hql = "delete Doctorwork w where w.id = " + id;
		Query query = session.createQuery(hql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return true;
	}
}
