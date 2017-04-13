package com.workful.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workful.handler.DBHandler;
import com.workful.templates.CommonFields;


@Controller
@RequestMapping("/")
public class UserController {
	
	private DBHandler db = DBHandler.getInstance();
	
	Authentication auth;
	
	@RequestMapping("/enrole")
	public String enroleAsWorker(){
		return "user/enrole";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(value="category", required=false, defaultValue="category")String category,
			@RequestParam(value="region", required=false, defaultValue="region")String region,
			ModelMap model){
		

		model.addAttribute("region", region);
		model.addAttribute("category", category);
		return "search";
	}
	
	@RequestMapping("/index")
	public String index(ModelMap model, @RequestParam(value = "show", required=false, defaultValue="not")String show){
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		
		//if user calls for filters
			if(!show.equals("not")){
				
				model.addAttribute("showFilters", "show");
				
			}		
			
			//set regions for filter in index(modal) 
			ArrayList<CommonFields> modelFilterRegions = db.getRegion();
			model.addAttribute("region", modelFilterRegions);
			
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

	
	
}
