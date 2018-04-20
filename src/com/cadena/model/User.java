package com.cadena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_USER")
public class User extends Person {

	@Column(name = "C_USERNAME", unique = true)
	@Size(max = 255, min = 3, message = "{user.name.invalid}")
	private String username;

	@Column(name = "C_EMAIL", unique = true)
	private String email;

	@Column(name = "C_PASSWORD")
	private String password;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//	@OneToMany(cascade = CascadeType.ALL)
//	private List<Authorities> authorities = new ArrayList<>();

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	//private List<Device> devices = new ArrayList<>();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
