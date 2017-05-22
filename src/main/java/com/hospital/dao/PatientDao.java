package com.hospital.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.hospital.controller.LoginPageController;
import com.hospital.entity.Doctor;
import com.hospital.entity.PageBean;
import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;
import com.hospital.util.StringUtil;

@Repository
public class PatientDao {
	public Patient getPatientById(int id){
		Session session = HibernateUtil.getSession();
		String hql = "from Patient where id = " + id;
		Query query = session.createQuery(hql);
		List<Patient> patients = query.list();
		if(!patients.isEmpty()){
			return patients.get(0);
		}else {
			return null;
		}
	}
	public Patient getPatient(String name){
		Session session = HibernateUtil.getSession();
		String hql = "from Patient where username = " + "'" +name + "'";
		Query query = session.createQuery(hql);
		List<Patient> patients = query.list();
		session.close();
		if(patients.size() == 1)
			return patients.get(0);
		else 
			return null;
	}
	public boolean addPatient(Patient patient){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(patient);
		transaction.commit();
		session.close();
		return true;
	}
	public List<Patient> getPatients(Patient patient,PageBean pageBean){
		StringBuffer sb = new StringBuffer("from Patient ");
		boolean flag = false;
		if(!StringUtil.isEmpty(patient.getUsername())){
			sb.append("where username like '%" + patient.getUsername() + "%' ");
			flag = true;
		}
		if(!StringUtil.isEmpty(patient.getTruename())){
			if(flag == true){
				sb.append("and truename like '%" + patient.getTruename() + "%' ");
			}
			else {
				sb.append("where truename like '%" + patient.getTruename() + "%' ");
				flag = true;
			}
		}
		if(!StringUtil.isEmpty(patient.getPatientgender())){
			if(flag == true){
				sb.append("and patientgender = '" + patient.getPatientgender() + "' ");
			}
			else {
				sb.append("where patientgender = '" + patient.getPatientgender() + "' ");
				flag = true;
			}
		}
		if(patient.getPatientage() != null){
			if(flag == true){
				sb.append("and patientage = " + patient.getPatientage());
			}
			else {
				sb.append("where patientage = " + patient.getPatientage());
				flag = true;
			}
		}
		String hql = sb.toString();
		System.out.println(hql);
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		if(pageBean != null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getRows());
		}
		List<Patient> patientList = query.list();
		session.close();
		return patientList;
	}
	public int getPatientCount(Patient patient){
		StringBuffer sb = new StringBuffer("select count(*) from Patient ");
		boolean flag = false;
		if(!StringUtil.isEmpty(patient.getUsername())){
			sb.append("where username like '%" + patient.getUsername() + "%' ");
			flag = true;
		}
		if(!StringUtil.isEmpty(patient.getTruename())){
			if(flag == true){
				sb.append("and truename like '%" + patient.getTruename() + "%' ");
			}
			else {
				sb.append("where truename like '%" + patient.getTruename() + "%' ");
				flag = true;
			}
		}
		if(!StringUtil.isEmpty(patient.getPatientgender())){
			if(flag == true){
				sb.append("and patientgender = '" + patient.getPatientgender() + "' ");
			}
			else {
				sb.append("where patientgender = '" + patient.getPatientgender() + "' ");
				flag = true;
			}
		}
		if(patient.getPatientage() != null){
			if(flag == true){
				sb.append("and patientage = " + patient.getPatientage());
			}
			else {
				sb.append("where patientage = " + patient.getPatientage());
				flag = true;
			}
		}
		String hql = sb.toString();
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		int count = Integer.parseInt(query.list().get(0).toString());
		return count;
	}
	public boolean updatePatient(Patient patient){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(patient);
		transaction.commit();
		session.close();
		return true;
	}
	public boolean deletePatientById(int id){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String hql = "delete Patient p where p.id = " + id;
		Query query = session.createQuery(hql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return true;
	}
}
