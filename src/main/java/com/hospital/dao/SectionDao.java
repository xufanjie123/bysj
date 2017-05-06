package com.hospital.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Section;
import com.hospital.util.HibernateUtil;

@Repository
public class SectionDao {
	public List<Section> getSections(){
		Session session = HibernateUtil.getSession();
		String hql = "from Section";
		Query query = session.createQuery(hql);
		List<Section> sectionList = query.list();
		session.close();
		return sectionList;
	}
	public String getSectionNameById(int id){
		Session session = HibernateUtil.getSession();
		String hql = "select sectionname from Section where id = " + id;
		Query query = session.createQuery(hql);
		List<String> strings = query.list();
		session.close();
		return strings.get(0);
	}
}
