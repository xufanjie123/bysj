package com.hospital.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Admin;
import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;

@Repository
public class AdminDao {
	public Admin getAdmin(String name){
		Session session = HibernateUtil.getSession();
		String hql = "from Admin where username = " + "'" +name + "'";
		Query query = session.createQuery(hql);
		List<Admin> admins = query.list();
		session.close();
		if(admins.size() == 1)
			return admins.get(0);
		else 
			return null;
	}
}
