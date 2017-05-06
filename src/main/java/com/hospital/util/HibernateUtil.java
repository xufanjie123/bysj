package com.hospital.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

//public class HibernateUtil {
//	private static Configuration config;
//	private static ServiceRegistry serviceRegistry;
//	private static SessionFactory sessionFactory;
//	static	{
//		config = new Configuration().configure();
//		serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
//		sessionFactory = config.buildSessionFactory(serviceRegistry);
//	}
//	public static SessionFactory getSessionFactory(){
//		return sessionFactory;
//	}
//	public static Session getSession(){
//		return sessionFactory.openSession();
//	}
//	public static void closeSession(Session session){
//		if(session != null){
//			session.close();
//		}
//	}
//}


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private HibernateUtil(){

    }
    public static Session getSession(){
        return getSessionFactory().openSession();
    }
    public static SessionFactory getSessionFactory(){
        if(sessionFactory==null)
        {
            synchronized (HibernateUtil.class)
            {
                if (sessionFactory==null)
                {
                    Configuration cfg=new Configuration().configure();
                    ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
                    sessionFactory=cfg.buildSessionFactory(serviceRegistry);
                }
            }
            return sessionFactory;
        }
        else {
            return sessionFactory;
        }
    }
}
