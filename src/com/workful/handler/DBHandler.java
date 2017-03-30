package com.workful.handler;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.workful.controller.Person;
import com.workful.templates.Category;
import com.workful.templates.City;
import com.workful.templates.CommonFields;
import com.workful.templates.Region;
import com.workful.templates.Skill;

public class DBHandler {

	//statement used for executing the query
    private Statement st;
    
    //result set to browse through the result of the query(un fel de fetch array din php)
    private ResultSet res;
    
    //used for singleton design pattern
    //to ensure that only one instance of the dbHandler is created
    private static DBHandler dbHandler = new DBHandler();
    
    //method to return handler
    //Singleton Desing Pattern
    public static DBHandler getInstance(){
    	return dbHandler;
    }
   
    //Constructor
    //private constructor for Singleton Design Pattern
    private DBHandler() {
       
    	try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/workful", "root", "");
            st = con.createStatement();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //***********FROM HERE - METHODS TO QUERY THE DB*************
    
    
    ////------------------- GET IDs ------------------/////////

    ///use to get region's id providing region name
    //private because it's used within this class
    private int getRegionId(String regionName){
        int regionId = 0;
        String query = "SELECT id_regiune FROM regiune WHERE nume_regiune='"+regionName+"'";
       try {
           res = st.executeQuery(query);
           if(res.next()) {
               regionId = res.getInt("id_regiune");
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }
        return regionId;
    }

    
    ///use to get city's id providing city name
    private int getCityId(String cityName){
        int cityId = 0;
        String query = "SELECT id_oras FROM oras WHERE nume_oras='"+cityName+"'";
        try {
            res = st.executeQuery(query);
            if(res.next()) {
                cityId = res.getInt("id_oras");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cityId;
    }

    
    //use to get category id based on category title (provide category title)
    //private because it's only used by methods within this class
    //registerNewPerson()
    private int getCategoryId(String category){
        int categoryId=0;
        String query = "SELECT categorie.id_categorie FROM categorie WHERE nume_categorie='"+category+"'";
        try {
            res = st.executeQuery(query);
            if(res.next()) {
                categoryId = res.getInt("id_categorie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryId;
    }

    //use to get skill id based on skill title (provide skill title)
    //private because it's only used by methods within this class
    @SuppressWarnings("unused")
	private int getSkillId(String skill){
    	int skillId=0;
        String query = "SELECT id_aptitudine FROM aptitudini WHERE nume_aptitudine='"+skill+"'";
        try {
            res = st.executeQuery(query);
            if(res.next()) {
            	skillId = res.getInt("id_aptitudine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        return skillId;
    }
    
    public String getSkillName(int skillId){
    	String skillName = null;
        String query = "SELECT nume_aptitudine FROM aptitudini WHERE id_aptitudine="+skillId;
        try {
            res = st.executeQuery(query);
            if(res.next()) {
            	skillName = res.getString("nume_aptitudine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        return skillName;
    }
    
    public String getCategoryName(int categoryId){
    	String categoryName = null;
        String query = "SELECT nume_categorie FROM categorie WHERE id_categorie="+categoryId;
        try {
            res = st.executeQuery(query);
            if(res.next()) {
            	categoryName = res.getString("nume_categorie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        return categoryName;
    }

    public String getRegionName(int regionId){
    	String regionName = null;
        String query = "SELECT nume_regiune FROM regiune WHERE id_regiune="+regionId;
        try {
            res = st.executeQuery(query);
            if(res.next()) {
            	regionName = res.getString("nume_regiune");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        return regionName;
    }

    public String getCityName(int cityId){
    	String cityName = null;
        String query = "SELECT nume_oras FROM oras WHERE id_oras="+cityId;
        try {
            res = st.executeQuery(query);
            if(res.next()) {
            	cityName = res.getString("nume_regiune");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        return cityName;
    }
    
    //use to get search result
    //TODO modify
    public ArrayList<Person> getSearchResult(int idRegion, int idCity, String search){
        ArrayList<Person> arrayList = new ArrayList<Person>();

        String query = "SELECT persoana.nume, persoana.telefon, persoana.id_categorie, persoana.titlu, persoana.descriere, persoana.rating, persoana.voturi, persoana.imagine, persoana.email, oras.nume_oras, regiune.nume_regiune, categorie.nume_categorie " +
                "FROM persoana " +
                "INNER JOIN oras ON persoana.id_oras=oras.id_oras " +
                "INNER JOIN regiune ON persoana.id_regiune=regiune.id_regiune " +
                "INNER JOIN categorie ON persoana.id_categorie = categorie.id_categorie " +
                "WHERE (persoana.id_regiune="+idRegion+" AND persoana.id_oras="+idCity+" AND titlu LIKE '%"+search+"%')";
        try {
            res = st.executeQuery(query);
            while (res.next()){
                Person p = new Person();

                p.setCity(res.getString("nume_oras"));
                p.setRegion(res.getString("nume_regiune"));
                p.setName(res.getString("nume"));
                p.setDescription(res.getString("descriere"));
                p.setPhoneNumber(res.getString("telefon"));
                p.setVoters(res.getInt("voturi"));
//                p.setPrice(res.getString("pret"));
//                p.setPayingMethod(res.getString("monetizare"));
                p.setCategory(res.getString("nume_categorie"));
                //TODO set Title

                arrayList.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  arrayList;
    }

    
    //use to get all cities from a region (providing region name)
    public ArrayList<CommonFields> getCity(int regiuneId) {
        ArrayList<CommonFields> listaOrase = new ArrayList<CommonFields>();

        try {
            String query = "SELECT * FROM oras INNER JOIN regiune ON oras.id_regiune = regiune.id_regiune WHERE (regiune.id_regiune="+regiuneId+")";
            res = st.executeQuery(query);


            while(res.next()){
                City city = new City();
                city.setCityName(res.getString("nume_oras"));
                city.setCityId(res.getInt("id_oras"));
                listaOrase.add(city);

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaOrase;
    }
    
  
    
    
    //use to get a list with all the regions
    public ArrayList<CommonFields> getRegion() {
        ArrayList<CommonFields>listaRegiuni = new ArrayList<CommonFields>();

        try {
            String query = "SELECT * FROM regiune ORDER BY nume_regiune ASC";
            res = st.executeQuery(query);

            while(res.next()){
                Region regions = new Region();
                regions.setRegionName(res.getString("nume_regiune"));
                regions.setRegionId(res.getInt("id_regiune"));
                listaRegiuni.add(regions);
                }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaRegiuni;
    }

    //login
    //TODO modify
    boolean login(String email, String password){
        boolean succesfulLogin = false;

        try {
            String query = "SELECT * FROM persoana WHERE email='"+email+"' AND password='"+password+"')";
            res = st.executeQuery(query);
            if (res.next()) {
                succesfulLogin = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return succesfulLogin;
    }

    
    
    //used to get category list
    public ArrayList<CommonFields> getCategory(){
        ArrayList<CommonFields> list = new ArrayList<CommonFields>();
        
        String query = "SELECT * FROM categorie";
        try {
            res = st.executeQuery(query);
            while (res.next()){
            	Category category = new Category();
            	category.setCategoryId(res.getInt("id_categorie"));
            	category.setCategoryName(res.getString("nume_categorie"));
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    
    //used to get one person profile (muncitor)
    //for login purpose
    //TODO modify
    public Person getPerson(String email, String password){
        Person p = new Person();

        try{
            String query = "SELECT persoana.nume, persoana.titlu, persoana.telefon, persoana.descriere, persoana.rating, persoana.voturi, persoana.imagine, persoana.email, regiune.nume_regiune, oras.nume_oras, categorie.nume_categorie " +
                    "FROM persoana " +
                    "JOIN oras ON persoana.id_oras = oras.id_oras " +
                    "JOIN regiune ON persoana.id_regiune = regiune.id_regiune " +
                    "JOIN categorie ON persoana.id_categorie = categorie.id_categorie " +
                    "WHERE (email='"+email+"' AND password='"+password+"')";
            res = st.executeQuery(query);
            while (res.next()){
                p.setName(res.getString("nume"));
                p.setPhoneNumber(res.getString("telefon"));
                p.setDescription(res.getString("descriere"));
                p.setCity(res.getString("nume_oras"));
                p.setRegion(res.getString("nume_regiune"));
                p.setCategory(res.getString("nume_categorie"));
                p.setEmai(res.getString("email"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return p;
    }

    //TODO modify
    //used to register new user
    public boolean registerNewPerson(Person p){
        boolean register=true;
        int success=0;
        String img = null;

    /*    try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        int city = getCityId(p.getCity());
        int region = getRegionId(p.getRegion());
        int category = getCategoryId(p.getCategory());
            String query = "INSERT INTO persoana (nume, id_oras, id_regiune, id_categorie, titlu, telefon, descriere, rating, voturi, imagine, email, password )" +
                    " VALUES ('"+p.getName()+"',"+city+","+region+","+category+",'"+p.getTitle()+"','"+p.getPhoneNumber()+"', '"+p.getDescription()+"' , " +
                    ","+p.getVoters()+", '"+img+"' , '"+p.getEmai()+"' , '"+ p.getPassword() +"')";

        try {
            success = st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(success == 0){
            register=false;
            }


        return register;
    }

    //TODO
    //search for email
    //used for login
    boolean searchForEmail(String email){
        boolean exists=false;

        String query = "SELECT email FROM persoana WHERE email = '"+email+"'";

        try {
            res = st.executeQuery(query);
           // String s = res.getString("email");
            if(res.isBeforeFirst()){
                exists = true;
            }
            System.out.println("email verification__________>");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Email not in db!!!");
        }

        return exists;
    }

    //used for skills(aptitudini)
    //need to provide category name
    public ArrayList<CommonFields> getSkills(){

    	ArrayList<CommonFields> skills = new ArrayList<CommonFields>();
    
    	String query = "SELECT * FROM aptitudini";
    			
        try {
            res = st.executeQuery(query);
            while (res.next()){
            	Skill skill = new Skill();
            	skill.setSkillId(res.getInt("id_aptitudine"));
            	skill.setSkillName(res.getString("nume_aptitudine"));
                skills.add(skill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    	return skills;
    }

    //TODO comments
    
    
    
    //-------------FOR ADMIN----------------------//
    //add new category
    public void addCategory(String newCategory){
    	String query = "INSERT INTO categorie(nume_categorie) VALUES ('"+newCategory+"')";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //remove category
	public void removeCategory(int category){
		
		String query = "DELETE FROM categorie WHERE id_categorie="+category;
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//add new skill(aptitudini)
	public void addSkill(String newSkill){
		String query = "INSERT INTO aptitudini(nume_aptitudine) "
				+ "VALUES ('"+newSkill+"')";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	//remove skill(aptitudini)
	public void removeSkill(int skill){
		String query = "DELETE FROM aptitudini WHERE "
				+ "id_aptitudine="+skill;
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	//add new city to region
	public void addCity(String newCity, int region){
		String query = "INSERT INTO oras(nume_oras, id_regiune) VALUES ('"+newCity+"', "+region+")";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	    
	//remove city from region
	public void removeCity(int city, int region){
		String query = "DELETE FROM oras WHERE id_oras="+city+" "
				+ "AND id_regiune="+region;
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
		
	//add new region
	public void addRegion(String newRegion){
		String query = "INSERT INTO regiune(nume_regiune) VALUES ('"+newRegion+"')";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	    
	//remove region
	public void removeRegion(int region){

		String query = "DELETE FROM regiune WHERE id_regiune="+region+"";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//insert new skill into category
	public boolean insertSkillForCategory(int skillId, int categoryId){
		String query = "INSERT INTO categoriiaptitudini VALUES ("+categoryId+", "+skillId+")";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }
    	return true;
	}
	
	//delete skill from category
	public void deleteSkillFromCategory(int category, int skill){
		String query = "DELETE FROM categoriiaptitudini WHERE "
				+ "id_aptitudine="+skill+" AND id_categorie="+category;
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<CommonFields> getSkillFromCat(int cat) {
        ArrayList<CommonFields>skillList = new ArrayList<CommonFields>();

        try {
            String query = "SELECT aptitudini.nume_aptitudine, aptitudini.id_aptitudine"
            		+ " FROM aptitudini "
            		+ "JOIN categoriiaptitudini ON(aptitudini.id_aptitudine = categoriiaptitudini.id_aptitudine) "
            		+ "WHERE categoriiaptitudini.id_categorie = "+cat
            		+ " ORDER BY aptitudini.nume_aptitudine";
            res = st.executeQuery(query);

            while(res.next()){
            	Skill skill = new Skill();
            	skill.setSkillName(res.getString("nume_aptitudine"));
                skill.setSkillId(res.getInt("id_aptitudine"));
                skillList.add(skill);
                }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return skillList; 
	
	}
}
