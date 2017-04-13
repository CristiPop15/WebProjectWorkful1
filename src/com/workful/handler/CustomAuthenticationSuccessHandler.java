package com.workful.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;



public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
			throws IOException, ServletException {
    
		DBHandler db = DBHandler.getInstance();
		
		String email = arg2.getName();
		
		int id = db.getPersonId(email);
		String imgPath = db.getImagePath(id);
		if(imgPath == null)
			imgPath = "../resources/img/default.png";
		
		System.out.println(email);
		
		
		
		SavedRequestAwareAuthenticationSuccessHandler sc = new SavedRequestAwareAuthenticationSuccessHandler();
		sc.setDefaultTargetUrl("http://localhost:8080/WebProjectWorkful/index");
		sc.onAuthenticationSuccess(arg0, arg1, arg2);
		
	}

}
