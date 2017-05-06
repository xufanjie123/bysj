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
 * Section entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "section", catalog = "hospitalsystem")
public class Section implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4708239671652649284L;
	private Integer id;
	private String sectionname;

	// Constructors

	/** default constructor */
	public Section() {
	}

	/** full constructor */
	public Section(String sectionname) {
		this.sectionname = sectionname;
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

	@Column(name = "sectionname", length = 16)
	public String getSectionname() {
		return sectionname;
	}
	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", sectionname=" + sectionname + "]";
	}



	

	
}