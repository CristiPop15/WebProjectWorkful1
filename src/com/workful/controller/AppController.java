package com.workful.controller;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workful.handler.DBHandler;
import com.workful.templates.Account;
import com.workful.templates.ObjectList;
import com.workful.templates.RestAccountInfo;

@RestController
@RequestMapping("/app")
public class AppController {
	
	DBHandler db = DBHandler.getInstance();
	ObjectList objList = new ObjectList();
	
	@RequestMapping("/city")
	public ObjectList getCities(@RequestParam("city") String city){
		
		System.out.println("restful web service ----- city list request, cityId= "+city);
		
		objList.setList(db.getCity(Integer.parseInt(city)));
		
		System.out.println(objList.getList());

		
		return objList;
	}
	
	
	@RequestMapping("/region")
	public ObjectList getRegions(){
		
		System.out.println("restful web service ----- region list request");
		

		
		objList.setList(db.getRegion());
		
		
		return objList;
	}
	
	
	@RequestMapping("/category")
	public ObjectList getCategories(){
		
		System.out.println("restful web service ----- category list request");
		

		objList.setList(db.getCategory());
		
		
		return objList;
	}

	@RequestMapping("/register-new-account")
	public int registerNewAccount(@RequestParam("email")String email,
			@RequestParam("password")String password){
		
		System.out.println("restful web service ----- Register new Account ");
		System.out.println("email: "+email+" -- password "+password);


		
		final int FAIL = 0;
		final int SUCCESS = 1;
		final int EMAIL_IN_USE = 2;
		
		if(db.searchForEmail(email))
			return EMAIL_IN_USE;
		
		//store in db
		 Account newAccount = new Account();
		 
		// encript password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		 newAccount.setEmail(email);
		 newAccount.setPassword(hashedPassword);
		 newAccount.setRegistrationDate();
		 
		 if(!db.registerNewAccount(newAccount))
			 return FAIL;
		 return SUCCESS;
		
	}

	@RequestMapping("/login")
	public RestAccountInfo login(@RequestParam("email")String email,
			@RequestParam("password")String password){
		
		System.out.println("restful web service ----- login");
		System.out.println("email: "+email+" -- password "+password);
		
		
		// encript password
		if (BCrypt.checkpw(password, db.getPassword(email)))
		    return db.getAccountInfo(db.getAccountId(email));
		else
		    return new RestAccountInfo();
		
		
	}
	
	
	
	/*
	 * 
	@RequestMapping("/register-new-profile")
	public boolean registerNewProfile(){
		
	}
	
	@RequestMapping("/search")
	public ObjectList search(){}
		
		*/
	
	}
	
	
	

