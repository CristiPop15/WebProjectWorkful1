package com.workful.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workful.handler.DBHandler;
import com.workful.templates.CommonFields;


@Controller
@RequestMapping("/")
public class UserController {
	
	private Authentication auth;
	private DBHandler db = DBHandler.getInstance();
	
	@RequestMapping("/comment")
	public String hello(){
		return "hello";
	}
	
	
	@RequestMapping("/index")
	public String index(ModelMap model, @RequestParam(value = "show", required=false, defaultValue="not")String show){
		
		//if user calls for filters
		if(!show.equals("not")){
			
			//set regions for filter in index(modal) 
			ArrayList<CommonFields> modelFilterRegions = db.getRegion();
			model.addAttribute("region", modelFilterRegions);
			
			//set categories for filter in index(modal) 
			ArrayList<CommonFields> modelFilterCategory = db.getCategory();
			model.addAttribute("category", modelFilterCategory);
			
			
		}		

		
		//get current user
		auth = SecurityContextHolder.getContext().getAuthentication();

			//if no user is logged in return normal navbar
			if (!(auth instanceof AnonymousAuthenticationToken)) {
			    return "index";
			}
		//when the user is logged in return custom navbar 
		model.addAttribute("user", "1");
		return "index";
	}
	
}
