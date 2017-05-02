package com.workful.controller;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.workful.handler.DBHandler;
import com.workful.handler.EmailVerification;
import com.workful.templates.Account;
import com.workful.templates.AccountRegistration;
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


	//registration error redirect
	private final String REG_ERROR = "redirect:/admin/fail-to-register?problem=";
		
	//registration errors
	private final String INVALID_EMAIL = "invalid";
	private final String EMAIL_ALREADY_IN_USE = "used";
	private final String PASSWORD_MISMATCH = "mismatch";
	private final String REGISTRATION_FAILED = "fail";


	DBHandler db = DBHandler.getInstance();
	private ArrayList<Object> list = new ArrayList<Object>();

	
	/**
	 *  ADMIN
	 * @return
	 */
	
	@RequestMapping("index")
		public String index(){
		return "admin/index";
	}
	
	@RequestMapping("afisare")
		public ModelAndView controlPanel(@RequestParam("type") String choiceType, 
			@RequestParam("choice") String choice){
		
		ArrayList<Object> list = getList(choice);
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
		ArrayList<Object> list = db.getCity(Integer.parseInt(toBeDeleted));
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
			ArrayList<Object>list = getList(CATEGORY);
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
			
		ArrayList<Object> list = db.getSkillFromCat(Integer.parseInt(categoryChoice));
			
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
		
		
		private ArrayList<Object> getList(String choice){
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

		
	/**
	 * 	===================== REGISTER NEW ADMIN ==============================
	 * 
	 */
		
	//pin auth to ensure that the admin is genuine
	@RequestMapping(value = "pin-control")
	public String pin_control(@RequestParam(value="error", required=false) String error, ModelMap model){
		model.addAttribute("error", error);
		return "admin/pin-control";
	}	
		
	// after the admin passes the pin auth
	//form to add a new admin
	@RequestMapping("new-admin")
	public String newAdmin(@RequestParam("new-admin") String newAdmin){
		int pin = 0;
		
		try{
			pin = Integer.parseInt(newAdmin);
		}
		catch(NumberFormatException nfe){
			return "redirect:pin-control?error=Access Denied";
		}
		if(pin == 1515)
			return "admin/new-admin";
		return "redirect:pin-control?error=Access Denied";
	}

 	//if errors appear during the registration proces
	@RequestMapping("/fail-to-register")
	public String registerError(ModelMap model, @RequestParam("problem")String problem){
		 	if(problem.equals(INVALID_EMAIL))
		 		model.addAttribute("error", "Email format error. Check if email is correct");
		 	else if(problem.equals(EMAIL_ALREADY_IN_USE))
		 		model.addAttribute("error", "Email already in use. Please use another email or log in");
		 	else if(problem.equals(PASSWORD_MISMATCH))
		 		model.addAttribute("error", "Password mismatch");
		 	else if(problem.equals(REGISTRATION_FAILED))
		 		model.addAttribute("error", "Registration failed. Please try again");
			return "admin/new-admin";
		}
	 
	//registration
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("registration") AccountRegistration accReg, ModelMap model){
		 
	 /**
	  * ========FIRST we check if the info sent is good=======
	 */
		 
		 //if email is not formed as it should be
		 if(!EmailVerification.validateEmail(accReg.getEmail())){
			 //redirect back to register page with invalid_email error
			 return REG_ERROR+INVALID_EMAIL;
		 }
		 //if email already in use
		 else if(db.searchForEmail(accReg.getEmail())){
			 //redirect back to register page with email-in-use error
			 return REG_ERROR+EMAIL_ALREADY_IN_USE;
		 }
		 //if password and confirm password doesn't match
		 else if(!accReg.getPassword().equals(accReg.getConfirmPassword())){
			 //redirect back to register page with mismatch error
			 return REG_ERROR+PASSWORD_MISMATCH;
		 }
		 
		 
	 /**
	 * ========if all the info sent is good proceed to create new account =======
	 */
		 //store in db
		 Account newAccount = new Account();
		 
		// encript password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(accReg.getPassword());
		
		 newAccount.setEmail(accReg.getEmail());
		 newAccount.setPassword(hashedPassword);
		 newAccount.setRegistrationDate();
		 
		 if(!db.registerNewAdminAccount(newAccount))
			 return REG_ERROR+REGISTRATION_FAILED;
		
		 return BACK_TO_INDEX;
		}
		
	
	
}
