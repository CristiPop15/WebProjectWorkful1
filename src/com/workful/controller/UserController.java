package com.workful.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workful.templates.Date;
import com.workful.templates.Person;

@Controller
@RequestMapping("/")
public class UserController {
	
	
	@RequestMapping("/comment")
	public String hello(){
		return "hello";
	}
	
	
	 @RequestMapping("/username")
	 @ResponseBody
	  public String currentUserName(Authentication authentication) {
	     
		 return Date.currentDate();
		 
		 //return authentication.getName();
	    }
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
}
