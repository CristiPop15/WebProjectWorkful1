package com.workful.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workful.handler.DBHandler;
import com.workful.templates.CommonFields;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	private static final String REGION = "judet";
	private static final String CITY = "oras";
	private static final String CATEGORY = "categorie";
	private static final String SKILLS = "aptitudini";
	
	private static final String BACK_TO_INDEX = "redirect:/admin/index";

	private static final String DISABLED = "disabled";
	private static final String ENABLED = "enabled";




	DBHandler db = DBHandler.getInstance();
	private ArrayList<CommonFields> list = new ArrayList<CommonFields>();

	
	
	@RequestMapping("index")
	public String index(){
		return "admin/index";
	}
	
	@RequestMapping("afisare")
	public ModelAndView controlPanel(@RequestParam("type") String choiceType, 
			@RequestParam("choice") String choice){
		
		ArrayList<CommonFields> list = getList(choice);
		ModelAndView model = null;
		
		if(!choice.equals(CITY)){
			if(choiceType.equals("add")){
				model = new ModelAndView("admin/add");
				model.addObject("type",choiceType);
				model.addObject("choice",choice);
			}else if(choiceType.equals("delete")){
				model = new ModelAndView("admin/delete");
				model.addObject("choice", choice);
				model.addObject("list", list);
				model.addObject("disabled",list.isEmpty() ? DISABLED:ENABLED);
			}else if(choiceType.equals("show")){
				model = new ModelAndView("admin/show");
				model.addObject("choice", choice);
				model.addObject("list", getList(choice));
				return model;
			}
		}
		else{
			model = new ModelAndView("admin/intermediateSelection");
			model.addObject("type", choiceType);
			model.addObject("choice", choice);
			model.addObject("list", list);
			model.addObject("disabled", list.isEmpty() ? DISABLED:ENABLED);
			}
		
		return model;
	}
	
	@RequestMapping("add/judet")
	public String addRegion(@RequestParam("new") String newValue){
		db.addRegion(newValue);	
		return BACK_TO_INDEX;			

		}
	
	@RequestMapping("delete/judet")
	public String removeRegion(@RequestParam("item") String toBeDeleted){
		db.removeRegion(Integer.parseInt(toBeDeleted));	
		return BACK_TO_INDEX;			
		}
	
	@RequestMapping("add/oras")
	public ModelAndView addCity(@RequestParam("item") String region){
		ModelAndView model = new ModelAndView("admin/addCityToRegion");
		model.addObject("region", region);
		model.addObject("regionName", db.getRegionName(Integer.parseInt(region)));
		return model;
		}
	
	@RequestMapping("addNewCity")
	public String addNewCityToRegion(@RequestParam("new") String newCity, @RequestParam("region") String toRegion){
		db.addCity(newCity, Integer.parseInt(toRegion));
		return BACK_TO_INDEX;
	}

	@RequestMapping("delete/oras")
	public ModelAndView removeCity(@RequestParam("item") String toBeDeleted){
		ArrayList<CommonFields> list = db.getCity(Integer.parseInt(toBeDeleted));
		ModelAndView model = new ModelAndView("admin/removeCityFromRegion");
		model.addObject("list", list);
		model.addObject("regionName", db.getRegionName(Integer.parseInt(toBeDeleted)));
		model.addObject("region", Integer.parseInt(toBeDeleted));
		model.addObject("disabled", list.isEmpty() ? DISABLED:ENABLED);
		return model;
		}
	
	@RequestMapping("deleteFromRegion")
	public String removeCityFromRegion(@RequestParam("item") String cityToDelete, @RequestParam("region") String regionFrom){
		db.removeCity(Integer.parseInt(cityToDelete), Integer.parseInt(regionFrom));
		return BACK_TO_INDEX;			
	}
	
	@RequestMapping("show/oras")
	public ModelAndView showCity(@RequestParam("item") String region){
		ModelAndView model = new ModelAndView("admin/show");
		model.addObject("choice", db.getRegionName(Integer.parseInt(region)));
		model.addObject("list", db.getCity(Integer.parseInt(region)));
		return model;
	}
	
		@RequestMapping("add/categorie")
	public String addCategory(@RequestParam("new") String toBeModified){
			db.addCategory(toBeModified);		
			return BACK_TO_INDEX;				
			}
		
		@RequestMapping("delete/categorie")
	public String removeCategory(@RequestParam("item") String toBeDeleted){
			db.removeCategory(Integer.parseInt(toBeDeleted));
			return BACK_TO_INDEX;				
			}
	
	@RequestMapping("add/aptitudini")
	public String addSkill(@RequestParam("new") String toBeModified){
			db.addSkill(toBeModified);		
			return BACK_TO_INDEX;				
			}
		
	@RequestMapping("delete/aptitudini")
	public String removeSkill(@RequestParam("item") String toBeDeleted){
			db.removeSkill(Integer.parseInt(toBeDeleted));
			return BACK_TO_INDEX;				
			}
		
	@RequestMapping("intermediateSelection")
	public ModelAndView categorySelectionForSkill(@RequestParam("type") String type){
			ArrayList<CommonFields>list = getList(CATEGORY);
			ModelAndView model = new ModelAndView("admin/intermediateSelection");
			model.addObject("type",type);
			model.addObject("choice", "skillToCategory");
			model.addObject("list",list);
			model.addObject("disabled", list.isEmpty() ? DISABLED:ENABLED );
			return model;
		}
		
	@RequestMapping("add/skillToCategory")
	public ModelAndView addSkillToCategoryModel(@RequestParam(value="success", required=false, defaultValue="firstTime") String success,
			@RequestParam("item") String categoryChoice){
			
		ModelAndView model = new ModelAndView("admin/addSkillsToCategories");
		model.addObject("categoryChoiceName", db.getCategoryName(Integer.parseInt(categoryChoice)));
		model.addObject("categoryChoice", categoryChoice);
		model.addObject("defaultSkills", db.getSkillFromCat(Integer.parseInt(categoryChoice)));
		model.addObject("newSkills", db.getSkills());
		model.addObject("type", "add");

			
			if(success.equals("false"))
				model.addObject("Ermessage", "Error, skill already on list");
			else if(success.equals("success"))
				model.addObject("message", "Added");
			
			
			return model;
		}
		
	@RequestMapping("delete/skillToCategory")
	public ModelAndView deleteSkillFromCategoryModel(@RequestParam(value="success", required=false, defaultValue="firstTime") String success,
			@RequestParam("item") String categoryChoice){
			
		ArrayList<CommonFields> list = db.getSkillFromCat(Integer.parseInt(categoryChoice));
			
		ModelAndView model = new ModelAndView("admin/deleteSkillsFromCategory");
		model.addObject("skills", list);
		model.addObject("categoryChoiceName",db.getCategoryName(Integer.parseInt(categoryChoice)));
		model.addObject("categoryChoice",categoryChoice);
		model.addObject("disabled", list.isEmpty() ? "disabled":"enabled");
		model.addObject("type", "delete");


		if(!success.equals("firstTime"))
			model.addObject("message", "Deleted");
	
		return model;
			}
		
		@RequestMapping("deleteSkillsFromCategory")
		public String deleteSkillFromCategory(@RequestParam("item") String skill,
				@RequestParam("category") String category){
			db.deleteSkillFromCategory(Integer.parseInt(category), Integer.parseInt(skill));
			return "redirect:/admin/delete/skillToCategory?success=success&item="+category;
		}
		
		@RequestMapping("addSkillsToCategories")
		public String addSkillToCategory(@RequestParam("item") String skill,
				@RequestParam("category") String category){
			if(db.insertSkillForCategory(Integer.parseInt(skill), Integer.parseInt(category)))
				return "redirect:/admin/add/skillToCategory?success=success&item="+category;
			else 
				return "redirect:/admin/add/skillToCategory?success=false&item="+category;
		}
		

	private ArrayList<CommonFields> getList(String choice){
		list.clear();
		
		if(choice.equals(REGION)){
			list =  db.getRegion();
		}
		else if(choice.equals(CATEGORY)){
			list =  db.getCategory();
		}
		else if(choice.equals(SKILLS)){
			list = db.getSkills();
		}
		else if(choice.equals(CITY)){
			list = db.getRegion();
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
