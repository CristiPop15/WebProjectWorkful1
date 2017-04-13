package com.workful.handler;

import java.sql.Statement;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.workful.templates.Account;
import com.workful.templates.Category;
import com.workful.templates.City;
import com.workful.templates.CommonFields;
import com.workful.templates.Person;
import com.workful.templates.Region;
import com.workful.templates.Skill;

public class DBHandler {

	//statement used for executing the query
    private Statement st;
    
    //result set to browse through the result of the query(un fel de fetch array din php)
    private ResultSet res;
    
    //connection pool
    private DataSourcePool dataSource;
    
    //used for singleton design pattern
    //to ensure that only one instance of the dbHandler is created
    private static DBHandler dbHandler = new DBHandler();
    
    //connection to db
    private Connection con;
    
    //method to return handler
    //Singleton Desing Pattern
    public static DBHandler getInstance(){
    	return dbHandler;
    }
   
    //Constructor
    //private constructor for Singleton Design Pattern
    private DBHandler() {
       
    	try {
			dataSource = DataSourcePool.getInstance();
		} catch (IOException | SQLException | PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   
    
    /**
    *********FROM HERE - METHODS TO QUERY THE DB*************
     */
    
    //=========================================================================
    
	    /**
	     * ------------------- GET IDs ------------------/////////
	     * @throws SQLException 
	     * 
	     */
	  
	    private int getAccountId(String email){
	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
	    	int accountId = 0;
	        String query = "SELECT id_cont FROM cont WHERE email='"+email+"'";
	       try {
	    	   result = statement.executeQuery(query);
	           if(result.next()) {
	        	   accountId = result.getInt("id_cont");
	           }
	       }
	       catch (Exception e){
	           e.printStackTrace();
	       }finally{
	    	   if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
	       }
	       
	       
	        return accountId;
	    	
	    }
	
	   //used when logging in
		public int getPersonId(String email) {
			
			return getAccountId(email);
		}

	    
	    
	    ///use to get region's id providing region name
	    //private because it's used within this class
	    private int getRegionId(String regionName){
	    	
	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	    	
	    	int regionId = 0;
	        String query = "SELECT id_regiune FROM regiune WHERE nume_regiune='"+regionName+"'";
	       try {
	    	   result = statement.executeQuery(query);
	           if(result.next()) {
	               regionId = result.getInt("id_regiune");
	           }
	       }
	       catch (Exception e){
	           e.printStackTrace();
	       }finally{
	    	   if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
	       }
	       
	        return regionId;
	    }
	
	    
	    ///use to get city's id providing city name
	    private int getCityId(String cityName) {
	    	
	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	int cityId = 0;
	        String query = "SELECT id_oras FROM oras WHERE nume_oras='"+cityName+"'";
	        try {
	        	result = statement.executeQuery(query);
	            if(result.next()) {
	                cityId = result.getInt("id_oras");
	            }
	        }
	        catch (Exception e){
	            e.printStackTrace();
	        }finally{
		      if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
		      if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
		      if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
					
		       }
	        
	        
	        return cityId;
	    }
	
	    
	    //use to get category id based on category title (provide category title)
	    //private because it's only used by methods within this class
	    //registerNewPerson()
	    private int getCategoryId(String category){

	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	        int categoryId=0;
	        String query = "SELECT categorie.id_categorie FROM categorie WHERE nume_categorie='"+category+"'";
	        try {
	        	result = statement.executeQuery(query);
	            if(result.next()) {
	                categoryId = result.getInt("id_categorie");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally{
			      if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
			      if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			      if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
						
			       }
	        
	
	        return categoryId;
	    }
	
	    //use to get skill id based on skill title (provide skill title)
	    //private because it's only used by methods within this class
		@SuppressWarnings("unused")
		private int getSkillId(String skill){

			Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	int skillId=0;
	        String query = "SELECT id_aptitudine FROM aptitudini WHERE nume_aptitudine='"+skill+"'";
	        try {
	        	result = statement.executeQuery(query);
	            if(result.next()) {
	            	skillId = result.getInt("id_aptitudine");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally{
			      if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
			      if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
			      if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
						
			       }
	       
	  
	        return skillId;
	    }
	 
	    /**
	     * -------------------END GET IDs ------------------/////////
     * 
     */
    
//=========================================================================

    
    /**
     * ------------------- GET Names ------------------/////////
     * @throws SQLException 
     * 
     */
    
    public String getSkillName(int skillId){
    	
    	createStatement();
    	
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
        finally{
	    	   closeStatement();
	       }
  
        return skillName;
    }
    
    public String getCategoryName(int categoryId){

    	createStatement();
    	
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
        finally{
	    	   closeStatement();

	       }
  
        return categoryName;
    }

    public String getRegionName(int regionId){

    	createStatement();
    	
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
        finally{
	    	   closeStatement();

	       }
  
        return regionName;
    }

    public String getCityName(int cityId){

    	createStatement();
    	
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
        finally{	    	   
        	closeStatement();

	       }

  
        return cityName;
    }
    
    /**
     * -------------------END GET Names ------------------/////////
     * 
     */
    
//=========================================================================
    
    /**
	     * -------------------Search. GET List's------------------/////////
     * @throws SQLException 
	     * 
	     */
	    
	    //use to get search result
	    //TODO modify
	    public ArrayList<Person> getSearchResult(int idRegion, int idCity, String search){
	    	
	    	createStatement();
	    	
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
	
	                arrayList.add(p);
	            }
	
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        finally{
		    	   closeStatement();

		       }
	        
	        return  arrayList;
	    }
	
	    
	    //use to get all cities from a region (providing region name)
	    public ArrayList<CommonFields> getCity(int regiuneId) {
	    	createStatement();
	    	
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
	        finally{
		    	   closeStatement();

		       }
	
	        return listaOrase;
	    }
	
	    
	    //use to get a list with all the regions
	    public ArrayList<CommonFields> getRegion() {

	    	createStatement();
	    	
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
	        finally{
		    	   closeStatement();

		       }
	
	        return listaRegiuni;
	    }
	    
	    //used to get category list
	    public ArrayList<CommonFields> getCategory(){

	    	createStatement();
	    	
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
	        finally{
		    	   closeStatement();

		       }
	
	        return list;
	    }
	    
	    //used to get skills from category providing category id
		public ArrayList<CommonFields> getSkillFromCat(int cat) {

			createStatement();
			
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
	        finally{
		    	   closeStatement();

		       }

	        return skillList; 
		
		}

		
		 //used for skills(aptitudini)
	    //need to provide category name
	    public ArrayList<CommonFields> getSkills(){

	    	createStatement();

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
	        finally{
	        	closeStatement();

		       }
	    
	    	return skills;
	    }

		
	    /**
	     * -------------------END GET List's------------------/////////
	     * @throws SQLException 
     * 
     */

//=========================================================================

    
    //login
    //TODO modify
    boolean login(String email, String password){

    	createStatement();
    	
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
        finally{
	    	   closeStatement();

	       }
        return succesfulLogin;
    }

    
    
    //Register new Account
    public boolean registerNewAccount(Account account){

    	createStatement();
    	
    	boolean success = true;
    	String query = "INSERT INTO cont(email, parola, data_inregistrare) VALUES ('"+account.getEmail()+"',"
    			+ "'"+account.getPassword()+"', '"+account.getRegistrationDate()+"')";
    	try {
    		st.executeUpdate(query);
    		setRole(getAccountId(account.getEmail()), "ROLE_USER");
    		
    		insertImage(getAccountId(account.getEmail()));

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
    	finally{
	    	   closeStatement();

	       }
    	
    	
    	return success;
    }
    
    private void setRole(int account_id, String role){

    	createStatement();
    	
    	String query = "INSERT INTO user_roles(cont_id, role) VALUES ("+account_id+", '"+role+"')";
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    public String getImagePath(int id) {
    	
    	createStatement();
    	
		String path = null;
		
		try {
			String query = "SELECT cale_imagine from imagini where id_cont="+id;
            res = st.executeQuery(query);
            if (res.next()) {
                path = res.getString("cale_imagine");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();

	       }
		
		
		return path;
	}



    
    //used to get one person profile (muncitor)
    //for login purpose
    //TODO modify
    public Person getPerson(String email, String password){

    	createStatement();
    	
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
        finally{
	    	   closeStatement();

	       }

        return p;
    }

    //TODO modify
    //used to register new user
    public boolean registerNewPerson(Person p){

    	createStatement();
    	
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
        finally{
	    	   closeStatement();

	       }

        if(success == 0){
            register=false;
            }


        return register;
    }

    
    //search for email
    //used for login
    public boolean searchForEmail(String email){

    	createStatement();
    	
        boolean exists=false;

        String query = "SELECT email FROM cont WHERE email = '"+email+"'";

        try {
            res = st.executeQuery(query);

            if(res.isBeforeFirst()){
                exists = true;
            }
            System.out.println("email verification__________>");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Email not in db!!!");
        }
        finally{	
        	closeStatement();

	       }

        return exists;
    }

   
    //TODO comments
    
    /**
     * ------------- FOR USER SETTINGS ----------------------//
     * @throws SQLException 
     * 
     */
    
	  public boolean updateUserPassword(String user, String hashedPassword) {

		  createStatement();
		  
			String query = "UPDATE cont SET parola = '"+hashedPassword+"' "
					+ "WHERE id_cont = "+getAccountId(user);
			try {
	    		st.executeLargeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
			finally{
		    	   closeStatement();

		       }
			return true;
		}

	  public boolean deleteUser(String user){

		  createStatement();
		  
		  String query = "DELETE FROM cont WHERE email='"+user+"'";
			try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
			finally{
		    	   closeStatement();
		       }
			return true;
		}
	    
	  public void updateImage(String path, int id){
		  
		  createStatement();
		  
		  String query = "UPDATE imagini SET cale_imagine = '"+path+"' WHERE "
		  		+ "id_cont = "+id;
		  
		  try{
			  st.executeUpdate(query);
			  System.out.println("path updated");
		  }catch(SQLException e){
			  e.printStackTrace();
		  }finally{
			  closeStatement();
		  }
	 }
	 
	  private void insertImage(int id){
		  
		  createStatement();
		  
		  String query = "INSERT INTO imagini(id_cont) VALUES("+id+")";
		  
		  try{
			  st.executeUpdate(query);
		  }catch(SQLException e){
			  e.printStackTrace();
		  }
		  
	  }
	 
	    /**
	     * ------------- END USER SETTINGS ----------------------// 
	     * 
	     */


	  
//*********************************************************************************

	  
    
    /**
	     * -------------FOR ADMIN----------------------//
	     * 
	     */
	   
    //====================================================================
    
    /**
     * --------------- ADD/CREATE ----------------------
     * @throws SQLException 
     */
    
    //Register new admin Account
    public boolean registerNewAdminAccount(Account account){

    	createStatement();
    	
    	boolean success = true;
    	String query = "INSERT INTO cont(email, parola, data_inregistrare) VALUES ('"+account.getEmail()+"',"
    			+ "'"+account.getPassword()+"', '"+account.getRegistrationDate()+"')";
    	try {
    		st.executeUpdate(query);
    		setRole(getAccountId(account.getEmail()), "ROLE_ADMIN");

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
    	finally{
	    	   closeStatement();

    	}
    	
    	
    	return success;
    }
    
    //add new category
    public void addCategory(String newCategory){

    	createStatement();
    	
    	String query = "INSERT INTO categorie(nume_categorie) VALUES ('"+newCategory+"')";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	finally{

	    	   closeStatement();
    	}
    }
    
    //add new skill(aptitudini)
  	public void addSkill(String newSkill){

  		createStatement();
  		
  			String query = "INSERT INTO aptitudini(nume_aptitudine) "
  					+ "VALUES ('"+newSkill+"')";
  	    	
  	    	try {
  	    		st.executeUpdate(query);
  	        } catch (SQLException e) {
  	            e.printStackTrace();
  	        }
  	    	finally{

 	    	   closeStatement();
  	    	}
  	    }
    	
  	//add new city to region
  	public void addCity(String newCity, int region){

  		createStatement();
  		
  				String query = "INSERT INTO oras(nume_oras, id_regiune) VALUES ('"+newCity+"', "+region+")";
  		    	
  		    	try {
  		    		st.executeUpdate(query);
  		        } catch (SQLException e) {
  		            e.printStackTrace();
  		        }
  		    	finally{

  		    	   closeStatement();
  		    	}
  			}
    
  	//add new region
  	public void addRegion(String newRegion){

  		createStatement();
  		
  			String query = "INSERT INTO regiune(nume_regiune) VALUES ('"+newRegion+"')";
  	    	
  	    	try {
  	    		st.executeUpdate(query);
  	        } catch (SQLException e) {
  	            e.printStackTrace();
  	        }
  	    	finally{

		    	   closeStatement();
		    	}
  	    }
  		
  	//insert new skill into category
  	public boolean insertSkillForCategory(int skillId, int categoryId){

  		createStatement();
  		
  			String query = "INSERT INTO categoriiaptitudini VALUES ("+categoryId+", "+skillId+")";
  	    	
  	    	try {
  	    		st.executeUpdate(query);
  	        } catch (SQLException e) {
  	        	e.printStackTrace();
  	            return false;
  	        }
  	    	finally{

		    	   closeStatement();
		    	}
  	    	return true;
  		}
  	
  	
  	/**
     * --------------- END ADD/CREATE ----------------------
     */
    
    
//====================================================================
    
    /**
     * --------------- REMOVE/DELETE ------------------------
     * @throws SQLException 
     */
	    
	    //remove category
		public void removeCategory(int category){
			
			createStatement();
			
			String query = "DELETE FROM categorie WHERE id_categorie="+category;
	    	
	    	try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	finally{

		    	   closeStatement();
		    	}
		}
				
	    
		//remove skill(aptitudini)
		public void removeSkill(int skill){

			createStatement();
			
			String query = "DELETE FROM aptitudini WHERE "
					+ "id_aptitudine="+skill;
	    	
	    	try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	finally{

		    	   closeStatement();
		    	}
		}
	
			    
		//remove city from region
		public void removeCity(int city, int region){

			createStatement();
			
			String query = "DELETE FROM oras WHERE id_oras="+city+" "
					+ "AND id_regiune="+region;
	    	
	    	try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	finally{

		    	   closeStatement();
		    	}
		}
			
		    
		//remove region
		public void removeRegion(int region){

			createStatement();
	
			String query = "DELETE FROM regiune WHERE id_regiune="+region+"";
	    	
	    	try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	finally{

		    	   closeStatement();
		    	}
		}
		
		
		//delete skill from category
		public void deleteSkillFromCategory(int category, int skill){
			
			createStatement();
			
			String query = "DELETE FROM categoriiaptitudini WHERE "
					+ "id_aptitudine="+skill+" AND id_categorie="+category;
	    	
	    	try {
	    		st.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    	finally{

		    	   closeStatement();
		    	}
		}


		
		/**
	     * --------------- END REMOVE/DELETE ------------------------
	     */

		
	//=========================== Pooling =======================
		private void closeStatement(){
			
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
            if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
            if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			
		}

		private void createStatement(){
			try {
				con = dataSource.getConnection();
				st = con.createStatement();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	       
	       
		}

	
		

}
