package com.workful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class UserController {

	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}
}
