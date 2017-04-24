package com.hospital.entity;

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
	private Section section;
	private String name;
	private String gender;
	private Integer age;
	private String school;
	private Integer worktime;
	private String title;
	private String skill;
	private Set<Doctorwork> doctorworks = new HashSet<Doctorwork>(0);
	private Set<Orders> orderses = new HashSet<Orders>(0);

	// Constructors

	/** default constructor */
	public Doctor() {
	}

	/** full constructor */
	public Doctor(Section section, String name, String gender, Integer age,
			String school, Integer worktime, String title, String skill,
			Set<Doctorwork> doctorworks, Set<Orders> orderses) {
		this.section = section;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.school = school;
		this.worktime = worktime;
		this.title = title;
		this.skill = skill;
		this.doctorworks = doctorworks;
		this.orderses = orderses;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectionId")
	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Column(name = "name", length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender", length = 8)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
	public Set<Doctorwork> getDoctorworks() {
		return this.doctorworks;
	}

	public void setDoctorworks(Set<Doctorwork> doctorworks) {
		this.doctorworks = doctorworks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
	public Set<Orders> getOrderses() {
		return this.orderses;
	}

	public void setOrderses(Set<Orders> orderses) {
		this.orderses = orderses;
	}

}