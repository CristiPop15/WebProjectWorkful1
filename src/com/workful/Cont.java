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
	
	private static final String backToIndex="redirect:/admin/index";




	DBHandler db = DBHandler.getInstance();
	private ArrayList<String> list = new ArrayList<String>();

	
	
	@RequestMapping("index")
	public String index(){
		return "admin/index";
	}
	
	@RequestMapping("afisare")
	public ModelAndView controlPanel(@RequestParam("type") String choiceType, 
			@RequestParam("choice") String choice){
		
		
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
			model = new ModelAndView("admin/intermediateSelection");
			model.addObject("type", choiceType);
			model.addObject("choice", choice);
			model.addObject("list", getStringList(region));
			}
		
		return model;
	}
	
	@RequestMapping("add/judet")
	public String addRegion(@RequestParam("new") String newValue){
		db.addRegion(newValue);	
		return backToIndex;			

		}
	
	@RequestMapping("delete/judet")
	public String removeRegion(@RequestParam("item") String toBeDeleted){
		db.removeRegion(toBeDeleted);		
		return backToIndex;			
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
		return backToIndex;
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
		return backToIndex;			
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
			return backToIndex;				
			}
		
		@RequestMapping("delete/categorie")
	public String removeCategory(@RequestParam("item") String toBeDeleted){
			db.removeCategory(toBeDeleted);
			return backToIndex;				
			}
	
		@RequestMapping("add/aptitudini")
	public String addSkill(@RequestParam("new") String toBeModified){
			db.addSkill(toBeModified);		
			return backToIndex;				
			}
		
		@RequestMapping("delete/aptitudini")
	public String removeSkill(@RequestParam("item") String toBeDeleted){
			db.removeSkill(toBeDeleted);
			return backToIndex;				
			}
		@RequestMapping("intermediateSelection")
	public ModelAndView categorySelectionForSkill(@RequestParam("type") String type){
			ModelAndView model = new ModelAndView("admin/intermediateSelection");
			model.addObject("type",type);
			model.addObject("choice", "skillToCategory");
			model.addObject("list",getStringList(category));
			return model;
		}
		
		@RequestMapping("add/skillToCategory")
		public ModelAndView addSkillToCategoryModel(@RequestParam(value="success", required=false, defaultValue="firstTime") String success,
				@RequestParam("item") String categoryChoice){
			
			ModelAndView model = new ModelAndView("admin/addSkillsToCategories");
			model.addObject("categoryChoice", categoryChoice);
			model.addObject("defaultSkills", db.getSkillFromCat(categoryChoice));
			model.addObject("newSkills", db.getSkills());
			model.addObject("type", "add");

			
			if(success.equals("false"))
				model.addObject("Ermessage", "Error, skill may already be in list");
			else if(success.equals("success"))
				model.addObject("message", "Added");
			
			
			return model;
		}
		
		@RequestMapping("delete/skillToCategory")
		public ModelAndView deleteSkillFromCategoryModel(@RequestParam(value="success", required=false, defaultValue="firstTime") String success,
				@RequestParam("item") String categoryChoice){
			
			
			ModelAndView model = new ModelAndView("admin/deleteSkillsFromCategory");
			model.addObject("skills", db.getSkillFromCat(categoryChoice));
			model.addObject("categoryChoice",categoryChoice);
			model.addObject("type", "delete");


			if(!success.equals("firstTime"))
				model.addObject("message", "Deleted");

			
			return model;
		}
		
		@RequestMapping("deleteSkillsFromCategory")
		public String deleteSkillFromCategory(@RequestParam("item") String skill,
				@RequestParam("category") String category){
			db.deleteSkillFromCategory(category, skill);
			return "redirect:/admin/delete/skillToCategory?success=success&item="+category;
		}
		
		@RequestMapping("addSkillsToCategories")
		public String addSkillToCategory(@RequestParam("item") String skill,
				@RequestParam("category") String category){
			if(db.insertSkillForCategory(skill, category))
				return "redirect:/admin/add/skillToCategory?success=success&item="+category;
			else 
				return "redirect:/admin/add/skillToCategory?success=false&item="+category;
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
