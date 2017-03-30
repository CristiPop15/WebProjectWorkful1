package com.workful.templates;

public class City implements CommonFields{
	private int cityId;
	private String cityName;
	private int regionId;
	
	public int getId(){
		return cityId;}
	public String getName(){
		return cityName;}
	public int getRegionId(){
		return regionId;}
	
	public void setCityId(int cityId){
		this.cityId = cityId;
	}
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	public void setRegionId(int regionId){
		this.regionId = regionId;
	}
}
