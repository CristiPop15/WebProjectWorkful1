package com.workful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
	
	/*
	
	// To return static html page
	@RequestMapping("/ceva")
	public ModelAndView cevaaa(){
		ModelAndView model = new ModelAndView("redirect:/resources/index.html");
		model.addObject("ceva","bbbbbbbbbbbbb");
		return model;
	} 

	
	
	
	
	
	//REST Services to be used by app
	@RequestMapping("/personjson")
	@ResponseBody
	public Person metoda(){
		Person p = new Person();
		p.setCity("Cluj");
		return p;
	}
	
	
	//Services to be used by browser
	@RequestMapping(value = "/index")
	public ModelAndView met(){
		ModelAndView model = new ModelAndView("index");
		ArrayList<String> lista = new ArrayList<String>();
		lista = (ArrayList<String>) db.getRegion();
		model.addObject("regions", lista);
		return model;
	}
	
	@RequestMapping("/afisare")
	public ModelMap altaMetoda(@RequestParam("item") String regiune, ModelMap model){
		ArrayList<String> lista = (ArrayList<String>)db.getCity(regiune);
		model.addAttribute("region", regiune);
		model.addAttribute("orase", lista);
		return model;
	}
	
	
	//exemplu metoda jsp&json
	@RequestMapping("/hello")
	public String something(ModelMap model){
		Person p = new Person();
		p.setCity("Cluj");
		model.addAttribute("model", p);
		return "hello";
	}
	*/
}
