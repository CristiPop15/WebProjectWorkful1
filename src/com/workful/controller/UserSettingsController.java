package com.workful.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.workful.handler.DBHandler;
import com.workful.handler.ImageHandler;
import com.workful.templates.CommonFields;
import com.workful.templates.CurrentPerson;
import com.workful.templates.Profile;

@Controller
@RequestMapping("/user")
public class UserSettingsController {
		
	//DB handler
	private DBHandler db = DBHandler.getInstance();
		
	private String email;
	private String imgPath;
	private int id;
	private CurrentPerson current;
	
	
			
	//URL to redirect when update is successful or an errror appeared
	private final String ERROR = "redirect:/user/settings-error?error=";
	private final String SUCCESS = "redirect:/user/settings-success?success=";


	private final String newPath = "http://localhost:8080/Image/getThumbImage?path=E:/ImgApp/";
	
	//for redirecting
	private final String userSettings = "user/";
	private final String userRedirect = "redirect:/user/";

	
	
	
	//---------ERRORS-------------------------------------------
	private final String PASSWORD_MISMATCH = "mismatch";
	private final String NEW_PASSWORD_INCORRECT = "incorrect";
	private final String SOME_ERROR = "some error";
	private final String IMAGE_ERROR = "image error";
	private final String NO_IMAGE_ERROR = "no image error";



	
	//---------SUCCESSFUL UPDATES-------------------------------------------
	private final String PASSWORD_SUCCESS = "password success";
	private final String USER_DELETED = "user delete";


	
// =================================== METHODS ======================================

	//the view
	@RequestMapping("/settings")
	public String showSettings(ModelMap model, @RequestParam(value="error", required=false, defaultValue="false")String error,
			@RequestParam(value="success", required=false, defaultValue="false")String success){
		
		current = getPerson();
				
		
		if(!(error.equals("false"))){
			model.addAttribute("error", error);
			return userSettings+"settings";

		}
		if(!(success.equals("false"))){
			model.addAttribute("msg", success);
			return userSettings+"settings";
		}
		
		model.addAttribute("path", current.getImgPath());
		
		return userSettings+"settings";
	}
	
	
	/**
	 * Change Password
	 * @param old
	 * @param newPassword
	 * @param confirm
	 * @return
	 */
	@RequestMapping(value = "/passwordc", method = RequestMethod.POST)
	public String changePassword(@RequestParam("new")String newPassword,
			@RequestParam("confirm")String confirm){
		
		current = getPerson();
		
				
		//if the new password is not the same as the confirmed one
		if(!(newPassword.equals(confirm))){
			return ERROR+PASSWORD_MISMATCH;
		}
		
		
		//encoding the new password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(newPassword);
		
	
	
		//update
		if(db.updateUserPassword(current.getEmail(), hashedPassword))
			return SUCCESS+PASSWORD_SUCCESS;
		
		//return error if something happened
		else return ERROR+SOME_ERROR;
			
	}
	
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/update-image", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("image") MultipartFile file) {

		current = getPerson();

		if (!file.isEmpty()) {
			
			byte[] bytes;
				try {
					bytes = file.getBytes();
					try{
						ImageIO.read(new ByteArrayInputStream(bytes));
					}catch(IIOException e){
						return ERROR+NO_IMAGE_ERROR;
					}
						
					
					ImageHandler.saveImage(bytes, current.getEmail());
					db.updateImage(newPath+email+".png", current.getId());

				} catch (IOException e) {
					e.printStackTrace();
				}

				return userRedirect+"settings";
			

		}else
			return ERROR+IMAGE_ERROR;
		
	}
	
	
	
	
	
	/**
	 * Delete user account
	 * @return
	 */
	@RequestMapping("delete-account")
	public String delete_account(){
		
		current = getPerson();
		
		if(db.deleteUser(current.getEmail())){
			ImageHandler.deleteImage(current.getEmail());
			return SUCCESS+USER_DELETED;
		}
		return ERROR+SOME_ERROR;
		
		
	}
	
	
	//====================== Errors handler ============================
	@RequestMapping("/settings-error")
	public ModelMap setting_error(ModelMap model, @RequestParam("error")String error){
		
		current = getPerson();
		
		model.addAttribute("path", current.getImgPath());

		if(error.equals(PASSWORD_MISMATCH))
			model.addAttribute("error", "Password missmatch");
		if(error.equals(NEW_PASSWORD_INCORRECT)){
			model.addAttribute("error", "New Password the same as the last one");
		}
		if(error.equals(SOME_ERROR)){
			model.addAttribute("error", "An error occured. Please try again");
		}
		if(error.equals(IMAGE_ERROR)){
			model.addAttribute("error", "No image selected. Please choose an image");
		}
		if(error.equals(NO_IMAGE_ERROR)){
			model.addAttribute("error", "An unknown type of image. Please select jpg and png images");
		}
		
		return model;
	}
	
	
	
	//====================== Successful updates handler ============================
	@RequestMapping("/settings-success")
	public String setting_success(ModelMap model, @RequestParam("success")String success){
		
		current = getPerson();
		
		if(success.equals(PASSWORD_SUCCESS)){
			model.addAttribute("msg", "Password changed successfully. Please log out and log in with new password");
			model.addAttribute("path", current.getImgPath());
		}
		if(success.equals(USER_DELETED))
			return "redirect:/logout";
		
		model.addAttribute("path", current.getImgPath());
	
		
		return userSettings+"settings";
	}
	
	
	
	
	/*
	 *  ///////////////////  //////  PROFILE  ////////   ////////////////////	
	 * */
	

	@RequestMapping("/enrole")
	public String enroleAsWorker(ModelMap model, @RequestParam(value = "error", required=false, defaultValue="no")String error){
		
		current = getPerson();
		
		if(!(db.getWorkerId(current.getId()) == 0))
			return "redirect:/user/update-profile";
		
		model.addAttribute("path", current.getImgPath());
		model.addAttribute("city",db.getAllCities());
		model.addAttribute("category", db.getCategory());

		if(!error.equals("no"))
			model.addAttribute("error", error);
		
		return "user/enrole";
	}
	
	
	@RequestMapping("/create-profile")
	public String profileWorker(ModelMap model, @ModelAttribute("profileForm") Profile profile, 
			@RequestParam("cityId") String city, @RequestParam("categoryId")String category){
		
		current = getPerson();
		
		
		model.addAttribute("path", current.getImgPath());
		model.addAttribute("city",db.getAllCities());
		model.addAttribute("category", db.getCategory());
		
		
		profile.setCity(city);
		profile.setCategory(category);
		
		if(db.registerNewProfile(profile, current.getId()))
			return "redirect:/user/skill-level";
		
		return "user/enrole?error=Some error occured";
	}
	
	@RequestMapping("skill-level")
	public ModelMap skillLvl(ModelMap model){
		
		current = getPerson();
		
		model.addAttribute("path", current.getImgPath());
		
		ArrayList<Object> skills = db.getSkillFromCat(db.getWorkerCategory(current.getId()));
		
		model.addAttribute("skills", skills);
		
		return model;
		
	}

	@RequestMapping("/update-skills")
	public String update_skills(@RequestParam Map<String, String> params, ModelMap model){
		
		current = getPerson();
		int profileId = db.getWorkerId(current.getId());
		
		
		ArrayList<Object> skills = db.getSkillFromCat(db.getWorkerCategory(current.getId()));

		for(Object skill: skills){
			System.out.println(params.get(((CommonFields) skill).getId()));
			db.updateProfileSkills(Integer.parseInt(params.get(String.valueOf(((CommonFields) skill).getId()))), 
					((CommonFields) skill).getId(), profileId);
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping("/update-profile")
	public ModelMap update_profile(ModelMap model, @RequestParam(value="popup", required=false, defaultValue="no")String popup){
		
		current = getPerson();
		
		model.addAttribute("path", current.getImgPath());
		
		if(!(popup.equals("no")))
			model.addAttribute("popup", popup);
		
		return model;
	}
	
	@RequestMapping("/delete-profile")
	public String delete_profile(){
		
		current = getPerson();
		
		db.deleteWorkerProfile(db.getWorkerId(current.getId()));
		
		return "redirect:/index";
	}
	
	
	private CurrentPerson getPerson(){
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		return new CurrentPerson(id,email,imgPath);
		
	}
}
