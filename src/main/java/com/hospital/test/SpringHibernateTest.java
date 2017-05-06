package com.hospital.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hospital.controller.LoginPageController;
import com.hospital.dao.PatientDao;
import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;

public class SpringHibernateTest {
	
	private ApplicationContext ctx = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
	@Test
	public void testHibernate(){
	}

}
