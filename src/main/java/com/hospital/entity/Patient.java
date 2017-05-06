package com.hospital.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Patient entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "patient", catalog = "hospitalsystem")
public class Patient implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6607858810390040533L;
	private Integer id;
	private String username;
	private String password;
	private String truename;
	private String patientgender;
	private Integer patientage;
	private String description;

	// Constructors

	/** default constructor */
	public Patient() {
	}

	/** full constructor */
	public Patient(String username, String password, String truename,
			String patientgender, Integer patientage, String description) {
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.patientgender = patientgender;
		this.patientage = patientage;
		this.description = description;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", length = 16)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 16)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "truename", length = 16)
	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	@Column(name = "patientgender", length = 8)
	public String getPatientgender() {
		return patientgender;
	}
	public void setPatientgender(String patientgender) {
		this.patientgender = patientgender;
	}

	@Column(name = "patientage")
	public Integer getPatientage() {
		return patientage;
	}
	public void setPatientage(Integer patientage) {
		this.patientage = patientage;
	}

	@Column(name = "description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", username=" + username + ", password=" + password + ", truename=" + truename
				+ ", patientgender=" + patientgender + ", patientage=" + patientage + ", description=" + description
				+ "]";
	}


	

}