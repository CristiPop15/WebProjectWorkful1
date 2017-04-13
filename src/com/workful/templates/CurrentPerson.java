package com.workful.templates;

public class CurrentPerson {
		
	private int id;
	private String email, imgPath;
	 
	
	public CurrentPerson(int id, String email, String imgPath) {
		this.id = id;
		this.email = email;
		this.imgPath = imgPath;	
		}

	public int getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getImgPath() {
		return imgPath;
	}
	
	
	
	

	
	

}
