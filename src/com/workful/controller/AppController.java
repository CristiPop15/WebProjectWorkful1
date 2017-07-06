package com.workful.controller;


import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workful.handler.DBHandler;
import com.workful.handler.ImageHandler;
import com.workful.templates.Acc;
import com.workful.templates.Account;
import com.workful.templates.AppProfile;
import com.workful.templates.Comm;
import com.workful.templates.ObjectList;
import com.workful.templates.Profile;
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
	
	 
	@RequestMapping(value = "/register-new-profile", method = RequestMethod.POST)
	public boolean registerNewProfile(@RequestBody AppProfile appProfile){
		 System.out.println("Register new profile");
		 System.out.println(appProfile.toString());
		 
		 if(db.getWorkerProfile(appProfile.getProfile().getId()) == null){

			try {
				ImageHandler.saveImage(appProfile.getImage_bytes(), appProfile.getProfile().getEmail());
				db.registerNewProfile(appProfile.getProfile(), appProfile.getProfile().getId());
	
				ImageHandler.saveImage(appProfile.getImage_bytes(), appProfile.getProfile().getEmail());
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Catch");
			}
		
			return true;
		 }
		 else 
			 return false;
		 }

			
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
	
	@RequestMapping("/get-profile")
	public Profile getProfile(@RequestParam("id")String ids){
		int id = Integer.parseInt(ids);
		
		return db.getProfile(id);
	}
	
	@RequestMapping("/get-skills")
	public ArrayList<Object> getSkills(@RequestParam("category")String category){
		
		System.out.println("skills from category - "+category);

		
		ArrayList<Object> skills = new ArrayList<Object>();
		
		skills.add(db.getSkillFromCat(Integer.parseInt(category)));
		System.out.println(skills);
		return skills;
	}
	
	
	@RequestMapping(value = "/get-comments")
	public Comm getComments(@RequestParam("id")String id){
		 System.out.println("Get Comments");
		 System.out.println(db.getComment(Integer.valueOf(id)));

		 Comm comments = new Comm();
		 comments.comments.addAll(db.getComment(Integer.valueOf(id)));
		return comments;
	}
	
	@RequestMapping(value = "/add-comment")
	public int addComment(@RequestParam("id")String id, @RequestParam("profile")String profile_id,
			@RequestParam("nota")String nota, @RequestParam("text")String text){
		 System.out.println("add Comments");
		 System.out.println(id);
		 System.out.println(profile_id);
		 System.out.println(nota);
		 System.out.println(text);

		db.addComment(Integer.valueOf(id), Integer.valueOf(profile_id), Integer.valueOf(nota), text);
		
		 System.out.println("after");

		return 1;
	}

	@RequestMapping(value = "/delete-profile")
	public boolean deleteProfile(@RequestParam("id")String id){
		 System.out.println("Delete Profile - "+id);
		 
		 return db.deleteWorkerProfile1(Integer.parseInt(id));
		 
	}
	
	@RequestMapping(value = "/delete-account")
	public boolean deleteAccount(@RequestParam("email")String email){
		 System.out.println("Delete Account - "+email);
		 
		 return db.deleteUser(email);
		 
	}
	
}
	
	
	

