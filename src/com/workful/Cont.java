package com.workful;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/*")
public class Cont {
	
	private static final String region="judet";
	private static final String city="oras";
	private static final String category="categorie";
	private static final String skills="aptitudini";



	DBHandler db = DBHandler.getInstance();
	private ArrayList<String> list = new ArrayList<String>();

	
	
	@RequestMapping("index")
	public String index(){
		return "admin/index";
	}
	
	@RequestMapping("afisare")
	public ModelAndView afisare(@RequestParam("type") String choiceType, @RequestParam("choice") String choice){
		ModelAndView model = null;
		
		if(!choice.equals(city)){
			if(choiceType.equals("add")){
				model = new ModelAndView("admin/add");
				model.addObject("type",choiceType);
				model.addObject("choice",choice);
			}else if(choiceType.equals("delete")){
				model = new ModelAndView("admin/delete");
				model.addObject("choice", choice);
				model.addObject("list", getStringList(choice));
			}else if(choiceType.equals("show")){
				model = new ModelAndView("admin/show");
				model.addObject("choice", choice);
				model.addObject("list", getStringList(choice));
				return model;
			}
		}
		else{
			model = new ModelAndView("admin/selectRegion");
			model.addObject("type", choiceType);
			model.addObject("choice", choice);
			model.addObject("list", getStringList(region));
			}
		
		return model;
	}
	
	@RequestMapping("add/judet")
	public String addRegion(@RequestParam("new") String newValue){
		db.addRegion(newValue);	
		return "redirect:/admin/index";			

		}
	
	@RequestMapping("delete/judet")
	public String removeRegion(@RequestParam("item") String toBeDeleted){
		db.removeRegion(toBeDeleted);		
		return "redirect:/admin/index";			
		}
	
	@RequestMapping("add/oras")
	public ModelAndView addCity(@RequestParam("item") String region){
		ModelAndView model = new ModelAndView("admin/addCityToRegion");
		model.addObject("region", region);
		return model;
		}
	
	@RequestMapping("addNewCity")
	public String addNewCityToRegion(@RequestParam("new") String newCity, @RequestParam("region") String toRegion){
		db.addCity(newCity, toRegion);
		return "redirect:/admin/index";
	}

	@RequestMapping("delete/oras")
	public ModelAndView removeCity(@RequestParam("item") String toBeDeleted){
		ModelAndView model = new ModelAndView("admin/removeCityFromRegion");
		model.addObject("list", getStringList(toBeDeleted));
		return model;
		}
	
	@RequestMapping("deleteFromRegion")
	public String removeCityFromRegion(@RequestParam("item") String cityToDelete, @RequestParam("region") String regionFrom){
		db.removeCity(cityToDelete, regionFrom);
		return "redirect:/admin/index";			
	}
	
	@RequestMapping("show/oras")
	public ModelAndView showCity(@RequestParam("item") String region){
		ModelAndView model = new ModelAndView("admin/show");
		model.addObject("choice", region);
		model.addObject("list", getStringList(region));
		return model;
	}
	
		@RequestMapping("add/categorie")
	public String addCategory(@RequestParam("new") String toBeModified){
			db.addCategory(toBeModified);		
			return "redirect:/admin/index";				
			}
		
		@RequestMapping("delete/categorie")
	public String removeCategory(@RequestParam("item") String toBeDeleted){
			db.removeCategory(toBeDeleted);
			return "redirect:/admin/index";				
			}
	
		@RequestMapping("add/aptitudini")
	public String addSkill(@RequestParam("new") String toBeModified){
			db.addSkill(toBeModified);		
			return "redirect:/admin/index";				
			}
		
		@RequestMapping("delete/aptitudini")
	public String removeSkill(@RequestParam("item") String toBeDeleted){
			db.removeSkill(toBeDeleted);
			return "redirect:/admin/index";				
			}
		
	@RequestMapping("AddSkillsToCategories")	
	public ModelAndView addSkillsToCat(@RequestParam(value="success", required=false, defaultValue="firstTime")
			String success, @RequestParam(value="categories", required=false, defaultValue="no") String showSkill){
		
		ModelAndView model = new ModelAndView("admin/addSkillsToCategories");
		if(success.equals("firstTime")){
			model.addObject("category", db.getCategory());
			model.addObject("skill", getStringList(skills));
			
			model.addObject("list", db.getSkillFromCat(showSkill));
			
		}else{
			model.addObject("message","Added");
			model.addObject("category", db.getCategory());
			model.addObject("skill", getStringList(skills));
			
			model.addObject("list", db.getSkillFromCat(showSkill));
			
		}
		return model;
	}
	
	@RequestMapping("showSkills")
	public String showSkills(@RequestParam("categories") String category){
		return "redirect:/admin/AddSkillsToCategories?categories="+category;
	}
	
	@RequestMapping("skillAddedToCat")
	public String skillAddedToCat(@RequestParam("categories") String category, @RequestParam("skills") String skill){
		db.insertSkillForCategory(skill, category);
		return "redirect:/admin/AddSkillsToCategories?success=success";
	} 
		
	private ArrayList<String> getStringList(String choice){
		list.clear();
		
		if(choice.equals(region)){
			list = (ArrayList<String>) db.getRegion();
		}
		else if(choice.equals(category)){
			list = db.getCategory();
		}
		else if(choice.equals(skills)){
			list = db.getSkills();
		}
		
		
		return list;
	}	
	
	
	/*
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
