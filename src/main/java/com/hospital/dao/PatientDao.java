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
import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;

@Repository
public class PatientDao {
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
	public void addPatient(Patient patient){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(patient);
		transaction.commit();
		session.close();
	}
}
