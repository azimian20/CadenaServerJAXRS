package com.cadena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends DomainEntity{

	@Column(name = "C_FNAME")
	@Size(max = 255, min = 2, message = "{person.fname.invalid}")
	private String fName;

	@Column(name = "C_LNAME")
	@Size(max = 255, min = 2, message = "{person.lname.invalid}")
	private String lName;

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
}
