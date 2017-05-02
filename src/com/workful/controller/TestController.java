package com.workful.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workful.handler.DBHandler;
import com.workful.templates.CommonFields;
import com.workful.templates.CurrentPerson;

@Controller
@RequestMapping("/")
public class TestController {
	

	//DB handler
	private DBHandler db = DBHandler.getInstance();
		
	private String email;
	private String imgPath;
	private int id;
	private CurrentPerson current;
	
	private CurrentPerson getPerson(){
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(email);
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		return new CurrentPerson(id,email,imgPath);
		
	}
	
	@RequestMapping("/ceva")
	public ModelMap skillLvl(ModelMap model){
		
		current = getPerson();
		
		
		//ArrayList<CommonFields> skills = db.getSkillFromCat(db.getWorkerCategory(current.getId()));
		
		//model.addAttribute("skills", skills);
		
		return model;
		
	}
	
	
	@RequestMapping("/altceva")
	public void update_skills(@RequestParam Map<String, String> params, ModelMap model){
		
		current = getPerson();
		int profileId = db.getWorkerId(current.getId());
		
		System.out.println(params.toString());
		System.out.println(current.getId());
		System.out.println(params.get(String.valueOf(8)));
	//	ArrayList<CommonFields> skills = db.getSkillFromCat(db.getWorkerCategory(current.getId()));
/*
		for(CommonFields skill: skills){
			System.out.println(params.get(skill.getId()));
			db.updateProfileSkills(Integer.parseInt(params.get(String.valueOf(skill.getId()))), 
					skill.getId(), profileId);
		*/
		}
	}
	
	
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

