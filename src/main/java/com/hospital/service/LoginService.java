package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.AdminDao;
import com.hospital.dao.PatientDao;
import com.hospital.entity.Admin;
import com.hospital.entity.Patient;

@Service
public class LoginService {
	@Autowired
	private PatientDao patientDao;
	@Autowired
	private AdminDao adminDao;
	public Patient getPatient(String name){
		return patientDao.getPatient(name);
	};
	public void addPatient(Patient patient){
		patientDao.addPatient(patient);
	}
	public Admin getAdmin(String name){
		return adminDao.getAdmin(name);
	}
}
