package com.hospital.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Doctor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "doctor", catalog = "hospitalsystem")
public class Doctor implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9142701695320731322L;
	private Integer id;
	private Integer sectionId;
	private String doctorname;
	private String doctorpwd;
	private String doctorgender;
	private Date birthday;
	private String school;
	private Integer worktime;
	private String title;
	private String skill;


	// Constructors

	/** default constructor */
	public Doctor() {
	}

	/** full constructor */
	public Doctor(Integer sectionId, String doctorname,String doctorpwd, String doctorgender,Date birthday,
			String school, Integer worktime, String title, String skill) {
		this.sectionId = sectionId;
		this.doctorname = doctorname;
		this.doctorpwd = doctorpwd;
		this.doctorgender = doctorgender;
		this.birthday = birthday;
		this.school = school;
		this.worktime = worktime;
		this.title = title;
		this.skill = skill;
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

	@Column(name = "sectionId")
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", sectionId=" + sectionId + ", doctorname=" + doctorname + ", doctorgender="
				+ doctorgender + ", birthday=" + birthday + ", school=" + school + ", worktime=" + worktime + ", title="
				+ title + ", skill=" + skill + "]";
	}

	@Column(name = "doctorname", length = 16)
	public String getDoctorname() {
		return doctorname;
	}
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
	@Column(name = "doctorpwd", length = 16)
	public void setDoctorpwd(String doctorpwd) {
		this.doctorpwd = doctorpwd;
	}
	public String getDoctorpwd() {
		return doctorpwd;
	}

	@Column(name = "doctorgender", length = 8)
	public String getDoctorgender() {
		return doctorgender;
	}
	public void setDoctorgender(String doctorgender) {
		this.doctorgender = doctorgender;
	}
	
	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "school", length = 32)
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "worktime")
	public Integer getWorktime() {
		return this.worktime;
	}

	public void setWorktime(Integer worktime) {
		this.worktime = worktime;
	}

	@Column(name = "title", length = 32)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "skill", length = 32)
	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}


}