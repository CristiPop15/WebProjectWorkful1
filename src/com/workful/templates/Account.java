package com.workful.templates;

public class Account {
	
	private String email, password, registrationDate;

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate() {
		this.registrationDate = Date.currentDate();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
