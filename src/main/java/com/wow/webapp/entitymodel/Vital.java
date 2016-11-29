package com.wow.webapp.entitymodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vitals")
public class Vital {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "property")
	private String property;
	
	@Column(name = "value")
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookingId",nullable=true)
	private Slot bookingId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_on")
	private Date inserted_on;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
		

	public Slot getBookingId() {
		return bookingId;
	}

	public void setBookingId(Slot bookingId) {
		this.bookingId = bookingId;
	}

	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}

	public Date getModified_on() {
		return modified_on;
	}

	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}

	
}
