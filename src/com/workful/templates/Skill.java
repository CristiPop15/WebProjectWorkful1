package com.workful.templates;

public class Skill implements CommonFields{
	
	private String skillName;
	private int skillId;
	
	public int getId(){
		return skillId;
	}
	public String getName(){
		return skillName;
	}
	
	public void setSkillName(String skillName){
		this.skillName = skillName;
	}
	
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}
	
}
