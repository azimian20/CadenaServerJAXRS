package com.cadena.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_LOCATION")
@Inheritance(strategy = InheritanceType.JOINED)
public class Location extends DomainEntity{

	@Column(name = "C_NMEAGPS")
	@Size(max = 255, min = 2, message = "{person.rawGps.invalid}")
	private String nmeaGps;

	@Column(name = "C_GMAPGPS")
	@Size(max = 255, min = 2, message = "{person.processedGps.invalid}")
	private String gMapGps;

	@Column(name = "C_DATETIME")
	private Date dateTime;
	
	@Column(name = "C_MESSAGEID")
	private String messageId ;
	
	@Column(name = "C_DEVICEID")
	private Long deviceId ;
	
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getNmeaGps() {
		return nmeaGps;
	}

	public void setNmeaGps(String nmeaGps) {
		this.nmeaGps = nmeaGps;
	}

	public String getgMapGps() {
		return gMapGps;
	}

	public void setgMapGps(String gMapGps) {
		this.gMapGps = gMapGps;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}



}
