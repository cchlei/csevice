package com.trgis.trmap.userauth.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QTManager implements Serializable {

	private Long id;
	
	private String username;
	
	private String salt;

	public QTManager() {
	}

	public QTManager(Long id, String username, String salt) {
		this.id = id;
		this.username = username;
		this.salt = salt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}
