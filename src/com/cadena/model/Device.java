package com.cadena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_DEVICE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Device extends DomainEntity {

	@Column(name = "C_NAME")
	@Size(max = 255, min = 2)
	private String name;

	@Column(name = "C_MODEL")
	private String model;

	@Column(name = "C_PHONENUMBER")
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "C_USER")
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
