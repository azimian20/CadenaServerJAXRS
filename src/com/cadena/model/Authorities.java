package com.cadena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_AUTHORITIES")
public class Authorities extends DomainEntity{
  
  @Column(name = "C_AUTHORITY")
  private String authority;

  @ManyToOne
  @JoinColumn(name = "C_USER")
  private User user;

public String getAuthority() {
	return authority;
}

public void setAuthority(String authority) {
	this.authority = authority;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}


}