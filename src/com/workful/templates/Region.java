package com.workful.templates;

public class Region implements CommonFields{
	
	private int regionId;
	private String regionName;
	
	public int getId(){
		return regionId;
	}
	
	public String getName(){
		return regionName;
	}
	
	public void setRegionId(int regionId){
		this.regionId = regionId;
	}
	public void setRegionName(String regionName){
		this.regionName = regionName;
	}
	
}
