package com.hospital.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Doctor;
import com.hospital.entity.PageBean;
import com.hospital.util.HibernateUtil;
import com.hospital.util.StringUtil;

@Repository
public class DoctorDao {
	public List<Doctor> getDoctors(Doctor doctor,PageBean pageBean){
		StringBuffer sb = new StringBuffer("from Doctor ");
		if(!StringUtil.isEmpty(doctor.getDoctorname())){
			sb.append("where doctorname like '%" + doctor.getDoctorname() + "%' ");
		}
		if(!StringUtil.isEmpty(doctor.getDoctorgender())){
			sb.append("where doctorgender = '" + doctor.getDoctorgender() + "' ");
		}
		if(doctor.getSectionId() != null){
			sb.append("where sectionId = '" + doctor.getSectionId() + "' ");
		}
		String hql = sb.toString();
		System.out.println(hql);
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		if(pageBean != null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getRows());
		}
		List<Doctor> doctorList = query.list();
		session.close();
		return doctorList;
	}
	public int getDoctorCount(Doctor doctor){
		StringBuffer sb = new StringBuffer("select count(*) from Doctor ");
		if(!StringUtil.isEmpty(doctor.getDoctorname())){
			sb.append("where doctorname like '%" + doctor.getDoctorname() + "%' ");
		}
		if(!StringUtil.isEmpty(doctor.getDoctorgender())){
			sb.append("where doctorgender = '" + doctor.getDoctorgender() + "' ");
		}
		if(doctor.getSectionId() != null){
			sb.append("where sectionId = '" + doctor.getSectionId() + "' ");
		}
		String hql = sb.toString();
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		int count = Integer.parseInt(query.list().get(0).toString());
		return count;
	}
	public boolean addDoctor(Doctor doctor){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(doctor);
		transaction.commit();
		session.close();
		return true;
	}
	public boolean updateDoctor(Doctor doctor){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(doctor);
		transaction.commit();
		session.close();
		return true;
	}
	public boolean deleteDoctorById(int id){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String hql = "delete Doctor d where d.id = " + id;
		Query query = session.createQuery(hql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return true;
	}
}
