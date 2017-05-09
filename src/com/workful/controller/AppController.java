package com.workful.controller;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workful.handler.DBHandler;
import com.workful.templates.Acc;
import com.workful.templates.Account;
import com.workful.templates.ObjectList;
import com.workful.templates.RestAccountInfo;
import com.workful.templates.SearchObj;

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

	@RequestMapping(value = "/register-new-account", method = RequestMethod.POST)
	public int registerNewAccount(@RequestBody Acc account){
		
		System.out.println("restful web service ----- Register new Account ");
		System.out.println("email: "+account.getEmail()+" -- password "+account.getPassword());


		final int FAIL = 0;
		final int SUCCESS = 1;
		final int EMAIL_IN_USE = 2;
		
		if(db.searchForEmail(account.getEmail()))
			return EMAIL_IN_USE;
		
		//store in db
		 Account newAccount = new Account();
		 
		// encript password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(account.getPassword());
		
		 newAccount.setEmail(account.getEmail());
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
		
		
		// verify password
		if (BCrypt.checkpw(password, db.getPassword(email)))
		    return db.getAccountInfo(db.getAccountId(email));
		else
		    return null;
		
	}
	
	
	
	/*
	 * 
	@RequestMapping("/register-new-profile")
	public boolean registerNewProfile(){
		
	}
		
		*/
	
	@RequestMapping("/search")
	public SearchObj search(@RequestParam(value = "city", required=false, defaultValue="0")String city,
			@RequestParam(value = "category", required=false, defaultValue="0")String category, 
			@RequestParam(value = "query", required=false, defaultValue="query")String searchQuery,
			@RequestParam(value = "limit", required=false, defaultValue="0")String lim){
	
		System.out.println(String.format("city = %s, category = %s, query = %s ", city,category,searchQuery));
		
		int limit = Integer.parseInt(lim);
		int cityId = Integer.parseInt(city);
		int categoryId = Integer.parseInt(category);

		SearchObj result = new SearchObj();		
		
		//if all fields are provided
		if(cityId != 0 && categoryId !=0 && !(searchQuery.equals("query")))	{
			System.out.println("All fields");
			return db.getSearchResult(cityId, categoryId, searchQuery, limit);

		}
		//if only the query is provided
		else if(cityId == 0 && categoryId ==0 && !(searchQuery.equals("query"))){
			System.out.println("Only query");
			return db.getSearchResult(searchQuery, limit);
		}else if(cityId != 0 && categoryId !=0 && (searchQuery.equals("query"))){
			System.out.println("City & category");
			return db.getSearchResult(cityId,  categoryId, limit);
		}
			
		return result;
		
	}
	
	}
	
	
	

