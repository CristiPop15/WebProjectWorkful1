package com.workful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

	@RequestMapping("/comment")
	public String hello(){
		return "hello";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/fail-to-login")
	 public String loginerror(ModelMap model) {
	 
	  model.addAttribute("error", "Bad credentials");
	  return "login";
	 
	 }
	
	 @RequestMapping(value="/logout")
	 public String logout(ModelMap model) {
		 model.addAttribute("msg", "Succesful logout");
	  return "login";
	 
	 }
	 
	 @RequestMapping("/403")
		public String accessDenied(){
			return "403";
		}
}
