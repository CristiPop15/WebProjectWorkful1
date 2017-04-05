package com.workful.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.workful.handler.DBHandler;
import com.workful.handler.EmailVerification;
import com.workful.templates.Account;
import com.workful.templates.AccountRegistration;

@Controller
@RequestMapping("/")
public class AuthentificationController {
	
	//for returning jsps from authentification folder
	private final String AUTH = "authentification/";
	
	//db
	private DBHandler db = DBHandler.getInstance();

	//registration error redirect
	private final String REG_ERROR = "redirect:/fail-to-register?problem=";
	
	//registration errors
	private final String INVALID_EMAIL = "invalid";
	private final String EMAIL_ALREADY_IN_USE = "used";
	private final String PASSWORD_MISMATCH = "mismatch";
	private final String REGISTRATION_FAILED = "fail";

	/**
	  * ============== LOGIN  ===========
	 */
	
	//login
	//when some1 is trying to login
	@RequestMapping("/login")
	public String login(){
		return AUTH+"login";
	}
	
	//loginerror
	//if the credentials are bad
	//email password mismatch
	//or no credentials at all
	@RequestMapping(value="/fail-to-login")
	 public String loginerror(ModelMap model) {
	 
	  model.addAttribute("error", "Bad credentials. Try Again");
	  return AUTH+"login";
	 
	 }
	
	//logout
	//when logging out clear session
	 @RequestMapping(value="/logout")
	 public String logout(ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		 HttpSession session= request.getSession(false);
		    SecurityContextHolder.clearContext();
		         session= request.getSession(false);
		        if(session != null) {
		            session.invalidate();
		        }
		        
		 model.addAttribute("msg", "Succesful logout");
	  return AUTH+"login";
	 
	 }
	 
	 //accessDenied
	 //access denied page
	 //when the account doesn't have enough authority to access a certain page
	 @RequestMapping("/403")
		public String accessDenied(){
			return AUTH+"403";
		}
	 
	 
	 /**
	  * ==============  REGISTRATION  =============
	 */
	 
	 @RequestMapping("/register")
		public String register(){
			return AUTH+"register";
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
			return AUTH+"register";
		}
	 
	 @RequestMapping(value = "/registration", method = RequestMethod.POST)
		public String registration(@ModelAttribute("registrationForm") AccountRegistration accReg, ModelMap model){
		 
	 /**
	  * ========FIRST we check if the info sent is good=======
	 */
		 
		 System.out.println(accReg.getEmail());
		 
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
		 
		 if(!db.registerNewAccount(newAccount))
			 return REG_ERROR+REGISTRATION_FAILED;
		
		 model.addAttribute("msg", "Account created. Please log in");
		 return AUTH+"login";
		}

	 
	 /**
	  * ==============  FORGOT PASSWORD  =============
	 */
	 
	 @RequestMapping("password-recover")
	 public String password(@RequestParam(value = "error", required=false) String error, ModelMap model){
		 model.addAttribute("error", error);
		 return AUTH+"password-recover";
	 }
	 
	 @RequestMapping("recover-password")
	 public String passwordRecovery(@RequestParam("email") String email){
		 System.out.println(email);
		 if(!db.searchForEmail(email))
			 return "redirect:password-recover?error=Email not registered";
		 return AUTH+"login";
	 }
	 
}
