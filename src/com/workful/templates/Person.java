package com.workful.templates;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 5950169519310163575L;


    private String profileImgPath;
    private String name;
    private String description;
    private String phoneNumber;
    private String category;
    private String region;
    private String city;
    private String emai;
    private String payingMethod;
    private String price;
    private int voters;
    private String password;
    private String title;

    public Person() {

    }


    //GETTERS


    public String getTitle() {
        return title;
    }

    public String getPassword() {
        return password;
    }

    public String getCategory() {
        return category;
    }

    public int getVoters() {
        return voters;
    }

    public String getPrice() {
        return price;
    }

    public String getPayingMethod() {
        return payingMethod;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmai() {
        return emai;
    }

    public String getDescription() {
        return description;
    }

   

    public String getRegion() {
        return region;
    }

    public String getProfileImg() {
        return profileImgPath;
    }




    //SETTERS


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVoters(int voters) {
        this.voters = voters;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPayingMethod(String payingMethod) {
        this.payingMethod = payingMethod;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRegion(String region) {
        this.region = region;
    }



    public void setProfileImg(String profileImg) {
        this.profileImgPath = profileImg;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
