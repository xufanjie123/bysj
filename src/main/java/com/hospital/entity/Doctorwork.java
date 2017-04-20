package com.hospital.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Doctorwork entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "doctorwork", catalog = "hospitalsystem")
public class Doctorwork implements java.io.Serializable {

	// Fields

	private Integer id;
	private Doctor doctor;
	private Date workdate;
	private Integer orderNum;
	private Integer maxNum;

	// Constructors

	/** default constructor */
	public Doctorwork() {
	}

	/** full constructor */
	public Doctorwork(Doctor doctor, Date workdate, Integer orderNum,
			Integer maxNum) {
		this.doctor = doctor;
		this.workdate = workdate;
		this.orderNum = orderNum;
		this.maxNum = maxNum;
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
	@JoinColumn(name = "doctorId")
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "workdate", length = 10)
	public Date getWorkdate() {
		return this.workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "maxNum")
	public Integer getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

}