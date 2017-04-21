package com.workful.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workful.handler.DBHandler;
import com.workful.templates.CommonFields;
import com.workful.templates.CurrentPerson;
import com.workful.templates.Profile;


@Controller
@RequestMapping("/")
public class UserController {
	
	private DBHandler db = DBHandler.getInstance();
	
	private String email;
	private String imgPath;
	private int id;
	private CurrentPerson current;
	
	
	Authentication auth;
	
		
	@RequestMapping("/index")
	public String index(ModelMap model){
		
		auth = SecurityContextHolder.getContext().getAuthentication();
			
			
			//set regions for filter in index(modal) 
			ArrayList<CommonFields> modelFilterCities = db.getAllCities();
			model.addAttribute("city", modelFilterCities);
			
			//set categories for filter in index(modal) 
			ArrayList<CommonFields> modelFilterCategory = db.getCategory();
			model.addAttribute("category", modelFilterCategory);
	
		//if there is no user logged in return normal index
		if(auth instanceof AnonymousAuthenticationToken){
			return "index/index";
		}	
		
		//if there is a user logged in return custom navigation bar
		int id = 0;
		String imgPath = null;
		String name = null;
		try{
			name = auth.getName();
			id = db.getPersonId(name);
			imgPath = db.getImagePath(id);
			if(imgPath==null)
				imgPath = "./resources/img/default.png";
		}catch(Exception e){e.printStackTrace();}
		
		
		model.addAttribute("path", imgPath);
		
		//when the user is logged in return custom navbar 
		model.addAttribute("user", "1");
		
		
		
		return "index/index";
	}

	private CurrentPerson getPerson(){
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		return new CurrentPerson(id,email,imgPath);
		
	}
	
}
