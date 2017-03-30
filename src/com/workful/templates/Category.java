package com.workful.templates;

public class Category implements CommonFields{

	private String categoryName;
	private int skillId;
	
	public int getId(){
		return skillId;
	}
	public String getName(){
		return categoryName;
	}
	
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public void setCategoryId(int skillId){
		this.skillId = skillId;
	}
}
