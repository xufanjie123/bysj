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
 * Orders entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "orders", catalog = "hospitalsystem")
public class Orders implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4272183558742761200L;
	private Integer id;
	private Integer patientId;
	private Integer doctorId;
	private Date ordertime;
	private Integer waitnum;

	// Constructors

	/** default constructor */
	public Orders() {
	}

	/** full constructor */
	public Orders(Integer patientId, Integer doctorId, Date ordertime,
			Integer waitnum) {
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.ordertime = ordertime;
		this.waitnum = waitnum;
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

	@Column(name = "patientId")
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	@Column(name = "doctorId")
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ordertime", length = 10)
	public Date getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	@Column(name = "waitnum")
	public Integer getWaitnum() {
		return this.waitnum;
	}

	public void setWaitnum(Integer waitnum) {
		this.waitnum = waitnum;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", patientId=" + patientId + ", doctorId=" + doctorId + ", ordertime=" + ordertime
				+ ", waitnum=" + waitnum + "]";
	}
	
	
}