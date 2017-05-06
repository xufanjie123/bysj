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

	@Override
	public String toString() {
		return "Doctorwork [id=" + id + ", doctorId=" + doctorId + ", workdate=" + workdate + ", orderNum=" + orderNum
				+ ", maxNum=" + maxNum + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719147945562016415L;
	private Integer id;
	private Integer doctorId;
	private Date workdate;
	private Integer orderNum;
	private Integer maxNum;

	// Constructors

	/** default constructor */
	public Doctorwork() {
	}

	/** full constructor */
	public Doctorwork(Integer doctorId, Date workdate, Integer orderNum,
			Integer maxNum) {
		this.doctorId = doctorId;
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

	@Column(name= "doctorId")
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
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