package com.workful.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workful.handler.DBHandler;
import com.workful.templates.Comment;
import com.workful.templates.CommonFields;
import com.workful.templates.CurrentPerson;
import com.workful.templates.Profile;
import com.workful.templates.SearchObj;
import com.workful.templates.SkillLvl;

@Controller
@RequestMapping("/")
public class SearchController {
	
		//DB handler
		private DBHandler db = DBHandler.getInstance();
			
		private String email;
		private String imgPath;
		private int id;
		private CurrentPerson current;
		

		
		Authentication auth;
		
		private CurrentPerson getPerson(){
			try{
				email = SecurityContextHolder.getContext().getAuthentication().getName();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			id = db.getPersonId(email);
			imgPath = db.getImagePath(id);
			
			if(imgPath == null)
				imgPath = "./resources/img/default.png";
			
			return new CurrentPerson(id,email,imgPath);
			
		}
		
		
	@RequestMapping("/search")
	public ModelMap search(ModelMap model, @RequestParam(value = "city", required=false, defaultValue="0")String city,
			@RequestParam(value = "category", required=false, defaultValue="0")String category, 
			@RequestParam(value = "query", required=false, defaultValue="query")String searchQuery,
			@RequestParam(value = "limit", required=false, defaultValue="0")String lim){
		
		
		current = getPerson();
		
		//if there is no user logged in return normal index
		auth = SecurityContextHolder.getContext().getAuthentication();

		if(!(auth instanceof AnonymousAuthenticationToken)){
				model.addAttribute("user","1");
				}
		
		model.addAttribute("path", current.getImgPath());
		
		int limit = Integer.parseInt(lim);
		int cityId = Integer.parseInt(city);
		int categoryId = Integer.parseInt(category);

		SearchObj result = new SearchObj();		
		
		//if all fields are provided
		if(cityId != 0 && categoryId !=0 && !(searchQuery.equals("query")))	{
			System.out.println("All fields");
			result = db.getSearchResult(cityId, categoryId, searchQuery, limit);
			System.out.println("All fields--end");

		}
		//if only the query is provided
		else if(cityId == 0 && categoryId ==0 && !(searchQuery.equals("query"))){
			System.out.println("Only query");
			result = db.getSearchResult(searchQuery, limit);
			System.out.println("Only query--end");

		}
		//if only the city is provided
		else if(cityId != 0 && categoryId ==0 && searchQuery.equals("query")){
			System.out.println("Only city");
			result = db.getSearchCityResult(cityId, limit);
			System.out.println("Only city---end");

		}	
		
		//if only the category is provided
		else if(cityId == 0 && categoryId !=0 && searchQuery.equals("query")){
			System.out.println("Only category");
			result = db.getSearchResult(categoryId, limit);
			System.out.println("Only category--end");

		}
		//if the city and the category is provided
		else if(cityId != 0 && categoryId !=0 && searchQuery.equals("query")){
			System.out.println("city & category");
			result = db.getSearchResult(cityId, categoryId, limit);
			System.out.println("city & category--end");

		}
		//if the city and the query is provided
		else if(cityId != 0 && categoryId ==0 && !(searchQuery.equals("query"))){
			System.out.println("city & query");
			result = db.getSearchCityResult(cityId, searchQuery, limit);
			System.out.println("city & query--end");

		}
		//if the category and the query is provided
		else if(cityId == 0 && categoryId !=0 && !(searchQuery.equals("query"))){
			System.out.println("query & category");
			result = db.getSearchResult(categoryId, searchQuery, limit);
			System.out.println("query & category--end");

		}
		//if user doesn't provide any info
		else{
			result = db.getAllSearchResult(limit);
			System.out.println("No info");
		}			
		
		//set regions for filter in index(modal) 
		ArrayList<CommonFields> modelFilterCities = db.getAllCities();
		model.addAttribute("city", modelFilterCities);
		
		//set categories for filter in index(modal) 
		ArrayList<Object> modelFilterCategory = db.getCategory();
		model.addAttribute("category", modelFilterCategory);
		
		//if no results are found
		if(result.getRows() == 0)
			model.addAttribute("msg", "No results found");
		
		else{
			model.addAttribute("result", result.getList());
			model.addAttribute("pagination", getPagination(limit, result.getRows()));
		}
		return model;
		
	}

	//TODO
	@SuppressWarnings("unused")
	private List<Integer> getPagination(int limit, int rows) {
		List<Integer> row = new ArrayList<>();
		if(limit<5){
			for(int i=0; i<7 && i<(rows/10); i++, limit++)
				row.add(limit);
			return row;
		}
		if((limit+3)>=(rows/10)){
			for(int i=7; i>=0; i--,rows--){
				row.add(rows);
				return row;
			}
		}
		for(int i=(limit-3), j=0; i<limit+3 && i<=rows; i++, j++ );
			
			
		return row;
	}

	@RequestMapping("/profile-view")
	public ModelMap profile_view(ModelMap model, @RequestParam("id")String idS){
		int id = Integer.parseInt(idS);
		ArrayList<SkillLvl> skills = new ArrayList<>();
		ArrayList<Comment> comments = new ArrayList<>();

		
		//if there is no user logged in return normal index
		auth = SecurityContextHolder.getContext().getAuthentication();

		if(!(auth instanceof AnonymousAuthenticationToken)){
			
			model.addAttribute("user","1");			
			current = getPerson();
			model.addAttribute("path", current.getImgPath());
		
		}
		
		Profile p = db.getProfile(id);
		
		model.addAttribute("profile", p);
		
		skills = db.getProfileSkills(id);
		
		model.addAttribute("skills",skills);
		
		comments = db.getComment(id);

		if(comments.isEmpty()){
			model.addAttribute("nocomment", "No comments");
			System.out.println("No comments");

		}
		else
			model.addAttribute("comments",comments);
		
		return model;
	}
	
	@RequestMapping("/add-comment")
	public String add_comment(@RequestParam("comment")String text,
			@RequestParam("profile_id")String profile, @RequestParam("nota")String nota){
		
		current = getPerson();
		int profile_id = Integer.parseInt(profile);
		int notaN = Integer.parseInt(nota);
		
		db.addComment(current.getId(), profile_id, notaN, text);
		
		return "redirect:/profile-view?id="+profile_id;
	}
	
}
