package com.workful.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.workful.handler.DBHandler;
import com.workful.handler.ImageHandler;
import com.workful.templates.CurrentPerson;

@Controller
@RequestMapping("/user")
public class UserSettingsController {
		
	//DB handler
	private DBHandler db = DBHandler.getInstance();
		
	private String email;
	private String imgPath;
	private int id;
	private CurrentPerson current;
	
	
	//Constructor
	public UserSettingsController(){

		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
	}

	
			
	//URL to redirect when update is successful or an errror appeared
	private final String ERROR = "redirect:/user/settings-error?error=";
	private final String SUCCESS = "redirect:/user/settings-success?success=";


	private final String newPath = "http://localhost:8080/Image/getThumbImage?path=E:/ImgApp/";
	
	//for redirecting
	private final String userSettings = "user/";
	
	
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
		
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		
		System.out.println(email);
				
		
		if(!(error.equals("false"))){
			model.addAttribute("error", error);
			return userSettings+"settings";

		}
		if(!(success.equals("false"))){
			model.addAttribute("msg", success);
			return userSettings+"settings";
		}
		
		model.addAttribute("path", imgPath);
		
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
		
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		
				
		//if the new password is not the same as the confirmed one
		if(!(newPassword.equals(confirm))){
			return ERROR+PASSWORD_MISMATCH;
		}
		
		
		//encoding the new password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(newPassword);
		
	
	
		//update
		if(db.updateUserPassword(email, hashedPassword))
			return SUCCESS+PASSWORD_SUCCESS;
		
		//return error if something happened
		else return ERROR+SOME_ERROR;
			
	}
	
	
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/update-image", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("image") MultipartFile file) {

		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		

		if (!file.isEmpty()) {
			
			byte[] bytes;
				try {
					bytes = file.getBytes();
					try{
						ImageIO.read(new ByteArrayInputStream(bytes));
					}catch(IIOException e){
						return ERROR+NO_IMAGE_ERROR;
					}
						
					
					ImageHandler.saveImage(bytes, email);
					db.updateImage(newPath+email+".png", id);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return userSettings+"settings";
			

		}else
			return ERROR+IMAGE_ERROR;
		
	}
	
	
	/**
	 *  Ask user if he is certain that he wants to delete his account
	 * @param model
	 * @return
	 */
	@RequestMapping("delete-account-question")
	public String delete_account_question(ModelMap model){
		
		model.addAttribute("path", imgPath);
		model.addAttribute("popup","true");
		return userSettings+"settings";

	}
	
	
	/**
	 * Delete user account
	 * @return
	 */
	@RequestMapping("delete-account")
	public String delete_account(){
		
		
		if(db.deleteUser(email)){
			return SUCCESS+USER_DELETED;
		}
		return ERROR+SOME_ERROR;
		
		
	}
	
	
	//====================== Errors handler ============================
	@RequestMapping("/settings-error")
	public String setting_error(ModelMap model, @RequestParam("error")String error){
		
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		
		model.addAttribute("path", imgPath);

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
		
		return userSettings+"settings";
	}
	
	
	
	//====================== Successful updates handler ============================
	@RequestMapping("/settings-success")
	public String setting_success(ModelMap model, @RequestParam("success")String success){
		
		try{
			email = SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		id = db.getPersonId(email);
		imgPath = db.getImagePath(id);
		
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		
		if(success.equals(PASSWORD_SUCCESS)){
			model.addAttribute("msg", "Password changed successfully. Please log out and log in with new password");
			model.addAttribute("path", current.getImgPath());
		}
		if(success.equals(USER_DELETED))
			return "redirect:/logout";
		
		model.addAttribute("path", imgPath);
	
		
		return userSettings+"settings";
	}
	
}
