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
import com.workful.templates.Comment;
import com.workful.templates.CommonFields;
import com.workful.templates.ListObject;
import com.workful.templates.Profile;
import com.workful.templates.Region;
import com.workful.templates.RestAccountInfo;
import com.workful.templates.SearchObj;
import com.workful.templates.SearchResult;
import com.workful.templates.Skill;
import com.workful.templates.SkillLvl;

public class DBHandler {

	//statement used for executing the query
    private Statement st;
    
    //result set to browse through the result of the query
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
	  
	    public int getAccountId(String email){
	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
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

	    public int getWorkerId(int accountId){
	    	Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
	    	
	    	int id_muncitor = 0;
	        String query = "SELECT id_muncitor FROM muncitor WHERE id_cont="+accountId+"";
	       try {
	    	   result = statement.executeQuery(query);
	           if(result.next()) {
	        	   id_muncitor = result.getInt("id_muncitor");
	           }
	       }
	       catch (Exception e){
	           e.printStackTrace();
	       }finally{
	    	   if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
	       }
	       
	        return id_muncitor;
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
    	

    	Connection connection = null;
    	Statement statement = null;
    	ResultSet result = null;
    	
    	try {
    		connection = dataSource.getConnection();
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	    	
    	String skillName = null;
        String query = "SELECT nume_aptitudine FROM aptitudini WHERE id_aptitudine="+skillId;
        try {
        	result = statement.executeQuery(query);
            if(result.next()) {
            	skillName = result.getString("nume_aptitudine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
        	 if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
		      if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
		      if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				       }
  
        return skillName;
    }
    
    public String getCategoryName(int categoryId){

    	Connection connection = null;
    	Statement statement = null;
    	ResultSet result = null;
    	
    	try {
    		connection = dataSource.getConnection();
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	String categoryName = null;
        String query = "SELECT nume_categorie FROM categorie WHERE id_categorie="+categoryId;
        try {
        	result = statement.executeQuery(query);
            if(result.next()) {
            	categoryName = result.getString("nume_categorie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
        	 if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	         if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	         if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
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

    	Connection connection = null;
    	Statement statement = null;
    	ResultSet result = null;
    	
    	try {
    		connection = dataSource.getConnection();
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	String cityName = null;
        String query = "SELECT nume_oras FROM oras WHERE id_oras="+cityId;
        try {
        	result = statement.executeQuery(query);
            if(result.next()) {
            	cityName = result.getString("nume_oras");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{	    	   
        	 if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	         if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	         if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	       }

  
        return cityName;
    }
    
    private String getEmail(int id_cont){
    	Connection connection = null;
    	Statement statement = null;
    	ResultSet result = null;
    	
    	try {
    		connection = dataSource.getConnection();
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}	    	
    	String account_email = null;
        String query = "SELECT email FROM cont WHERE id_cont="+id_cont;
       try {
    	   result = statement.executeQuery(query);
           if(result.next()) {
        	   account_email = result.getString("email");
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }finally{
    	   if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
       }
       
       System.out.println(account_email);
       
        return account_email;
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
	    
	  //------------------------------------- SEARCH ----------------------------------
    
  //use to get search result
    public SearchObj getSearchResult(int cityId, int categoryId, String searchQuery, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE id_categorie="+categoryId+
            		" AND id_oras="+cityId+" AND "
            		+ "titlu LIKE '%"+searchQuery+"%' LIMIT "+limit*10+",10";
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	searchResult.setEmail(getEmail(res.getInt("id_cont")));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }
        
        obj.setList(listaProfil);
        obj.setRows(getRowCount(cityId, categoryId, searchQuery));

        return obj;
    }
    
  //use to get search result without query
    public SearchObj getSearchResult(int cityId, int categoryId, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE id_categorie="+categoryId+
            		" AND id_oras="+cityId+" LIMIT "+limit*10+",10";
            
            res = st.executeQuery(query);
            
            System.out.println(query);
            
            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	searchResult.setEmail(getEmail(res.getInt("id_cont")));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }
        
        obj.setList(listaProfil);
        obj.setRows(getRowCount(cityId, categoryId));
        return obj;
    }
	    
  //use to get search result with query only
    public SearchObj getSearchResult(String searchQuery, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE titlu LIKE '%"+searchQuery+"%' LIMIT "+limit*10+",10";
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	searchResult.setEmail(getEmail(res.getInt("id_cont")));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }

        obj.setList(listaProfil);
        obj.setRows(getRowCount(searchQuery));
    	
        System.out.println(obj.toString());
        
        return obj;
    }

  //use to get search result from query and category
    public SearchObj getSearchResult(int categoryId, String searchQuery, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE titlu LIKE '%"+searchQuery+"%' "
            				+ " AND id_categorie="+categoryId+" LIMIT "+limit*10+",10";
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }

        obj.setList(listaProfil);
        obj.setRows(getRowCount(categoryId, searchQuery));
        
        return obj;
    }
    
  //use to get search result from query and city
    public SearchObj getSearchCityResult(int cityId, String searchQuery, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE titlu LIKE '%"+searchQuery+"%' "
            				+ " AND id_oras="+cityId+" LIMIT "+limit*10+",10";
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }
        
        obj.setList(listaProfil);
        obj.setRows(getRowCountC(cityId, searchQuery));

        return obj;
    }
    
  //use to get search result from city only
    public SearchObj getSearchCityResult(int cityId, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE id_oras="+cityId+" LIMIT "+limit*10+",10";
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }

        obj.setList(listaProfil);
        obj.setRows(getRowCountC(cityId));
        
        return obj;
    }
    
  //use to get search result from category only
    public SearchObj getSearchResult(int categoryId, int limit) {
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor WHERE id_categorie="+categoryId+" LIMIT "+limit*10+",10";

        
            res = st.executeQuery(query);
            
            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));

            	listaProfil.add(searchResult);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();
	       }
        
        
        obj.setList(listaProfil);
        obj.setRows(getRowCount(categoryId));

        return obj;
    }
    
    //if user does not provide anything
    public SearchObj getAllSearchResult(int limit){
    	
    	createStatement();
    	
    	SearchObj obj= new SearchObj();
    	
        ArrayList<SearchResult> listaProfil = new ArrayList<SearchResult>();

        try {
            String query = "SELECT id_cont, id_muncitor, id_categorie, id_oras, name, prenume, titlu "
            		+ "FROM muncitor  LIMIT "+limit*10+",10";
           
            res = st.executeQuery(query);

            System.out.println(query);


            while(res.next()){
            	SearchResult searchResult = new SearchResult();
            	
            	searchResult.setId(res.getInt("id_muncitor"));
            	searchResult.setCity(getCityName(res.getInt("id_oras")));
            	searchResult.setCategory(getCategoryName(res.getInt("id_categorie")));
            	searchResult.setName((res.getString("name")));
            	searchResult.setSurname(res.getString("prenume"));
            	searchResult.setTitle(res.getString("titlu"));
            	if(getImagePath(res.getInt("id_cont"))== null)
            		searchResult.setImgPath("./resources/img/default.png");
            	else
            		searchResult.setImgPath(getImagePath(res.getInt("id_cont")));


            	listaProfil.add(searchResult);
                }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare");
        }
        finally{
	    	   closeStatement();
	       }
        System.out.println(listaProfil);

        
        obj.setList(listaProfil);
        obj.setRows(getRowCount());

        return obj;
    }

    
    //------------------------------------- SEARCH END ----------------------------------
    
        
    
	  //------------------------------------- ROW COUNT ----------------------------------
	  
    	private int getRowCount(int cityId, int categoryId, String searchQuery){
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
    		
    		int nr = 0;
    		        	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_categorie="+categoryId+
                		" AND id_oras="+cityId+" AND "
                		+ "titlu LIKE '%"+searchQuery+"%'";
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
	            
            }
            
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}
    	
    	private int getRowCount(int cityId, int categoryId){
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
    		
    		int nr = 0;
    		        	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_categorie="+categoryId+
                		" AND id_oras="+cityId;
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
	         }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}
    
    	private int getRowCount(String searchQuery){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
	    	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE titlu LIKE '%"+searchQuery+"%'";
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            
            }
            
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}

    	private int getRowCount(int categoryId, String searchQuery){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
	    	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_categorie="+categoryId+
                		" AND titlu LIKE '%"+searchQuery+"%'";
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}
    	
    	private int getRowCountC(int cityId, String searchQuery){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}	
	    	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_oras="+cityId+" AND "
                		+ "titlu LIKE '%"+searchQuery+"%'";
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
           
            }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}

    	private int getRowCountC(int cityId){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_oras="+cityId;
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
           }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}
    	
    	private int getRowCount(int categoryId){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}        	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor WHERE id_categorie="+categoryId;
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
              }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}
    	
    	private int getRowCount(){
    		int nr = 0;
    		
    		Connection connection = null;
	    	Statement statement = null;
	    	ResultSet result = null;
	    	
	    	try {
	    		connection = dataSource.getConnection();
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}        	
            try {
                String query = "SELECT COUNT(*) AS count "
                		+ "FROM muncitor";
                
                result = statement.executeQuery(query);


                if(result.next()){
                	nr = result.getInt("count");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
            	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
	            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
             }
            System.out.println("Rows: "+nr);

    		
    		return nr;
    	}

  	  //-------------------------------------END ROW COUNT ----------------------------------

    	
    	public Profile getProfile(int id){
    		
    		Profile p = new Profile();
    		
    		createStatement();

            try {
                String query = "SELECT * from muncitor WHERE id_muncitor="+id;
                res = st.executeQuery(query);

                System.out.println(query);


                if(res.next()){
                	
                	p.setId(res.getInt("id_muncitor"));
                	p.setCityId(getCityName(res.getInt("id_oras")));
                	p.setCategoryId(getCategoryName(res.getInt("id_categorie")));
                	p.setName((res.getString("name")));
                	p.setSurname(res.getString("prenume"));
                	p.setTitle(res.getString("titlu"));
                	p.setDescription(res.getString("descriere"));
                	p.setTelephone(res.getString("telefon"));
                	if(getImagePath(res.getInt("id_cont"))== null)
                		p.setImgPath("./resources/img/default.png");
                	else
                		p.setImgPath(getImagePath(res.getInt("id_cont")));

                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
    	    	   closeStatement();
    	       }
    		
    		
    		return p;
    	}

    	public ArrayList<SkillLvl> getProfileSkills(int id) {
    		createStatement();
	    	
	        ArrayList<SkillLvl> skills = new ArrayList<SkillLvl>();
	
	        try {
	            String query = "SELECT * FROM aptitudinimuncitor "
	            		+ "WHERE id_muncitor="+id;
	            res = st.executeQuery(query);
	
	
	            while(res.next()){
	                SkillLvl skill= new SkillLvl();
	                
	                skill.setName(getSkillName(res.getInt("id_aptitudine")));
	                skill.setLevel(res.getInt("nivel"));
	                
	                skills.add(skill);
	
	                }
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        finally{
		    	   closeStatement();

		       }
	
	        return skills;
		}
    	
      //------------------------------------- ROW COUNT END ----------------------------------

    
    
	    //use to get all cities from a region (providing region name)
 	    public ArrayList<Object> getCity(int regiuneId) {
	    	createStatement();
	    	
	        ArrayList<Object> listaOrase = new ArrayList<Object>();
	
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
	    
	  //use to get all cities
	    public ArrayList<CommonFields> getAllCities() {
	    	createStatement();
	    	
	        ArrayList<CommonFields> listaOrase = new ArrayList<CommonFields>();
	
	        try {
	            String query = "SELECT id_oras, nume_oras FROM oras ORDER BY nume_oras ASC";
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
	    public ArrayList<Object> getRegion() {

	    	createStatement();
	    	
	        ArrayList<Object>listaRegiuni = new ArrayList<Object>();
	
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
	    public ArrayList<Object> getCategory(){

	    	createStatement();
	    	
	        ArrayList<Object> list = new ArrayList<Object>();
	        
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
		public ArrayList<Object> getSkillFromCat(int cat) {

			createStatement();
			
	        ArrayList<Object>skillList = new ArrayList<Object>();

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
	    public ArrayList<Object> getSkills(){

	    	createStatement();

	    	ArrayList<Object> skills = new ArrayList<Object>();
	    
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
    public boolean login(String email, String password){

    	createStatement();
    	
        boolean succesfulLogin = false;

        try {
            String query = "SELECT * FROM cont WHERE email='"+email+"' AND parola='"+password+"'";
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

   public String getPassword(String email){
	   
	   createStatement();
	   String password = null;
   	
       try {
           String query = "SELECT parola FROM cont WHERE email='"+email+"'";
           res = st.executeQuery(query);
           if (res.next()) {
               password = res.getString("parola");
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
       finally{
	    	   closeStatement();

	       }
       
       System.out.println("Password "+password);
       return password;
   }
    
    public boolean deleteWorkerProfile(int workerId){
    	
    	createStatement();
    	
    	boolean success = true;
    			
		try {
			String query = "DELETE FROM muncitor WHERE id_muncitor="+workerId;
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
            success=false;
        }
        finally{
	    	   closeStatement();
	       }
		
		System.out.println("worker profile deleted");
		
		return success;
    }
    
    
     @SuppressWarnings("null")
	public Profile getWorkerProfile(int accountId){
    	createStatement();
    	
		Profile profil = null;
		
		try {
			String query = "SELECT * FROM muncitor WHERE id_cont="+accountId;
            res = st.executeQuery(query);
            if (res.next()) {
                profil.setCategoryId(String.valueOf(res.getInt("id_categorie")));
                profil.setCityId(String.valueOf(res.getInt("id_oras")));
                profil.setDescription(res.getString("descriere"));
                profil.setName(res.getString("name"));
                profil.setSurname(res.getString("prenume"));
                profil.setTelephone(res.getString("telefon"));
                profil.setTitle(res.getString("titlu"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();

	       }
		
		return profil;
	}
    
    public RestAccountInfo getAccountInfo(int id_account){
    	 createStatement();
  	   RestAccountInfo account = new RestAccountInfo();
     	
         try {
             String query = "SELECT email FROM cont WHERE id_cont="+id_account;
             res = st.executeQuery(query);
             if (res.next()) {
                 account.setEmail(res.getString("email"));
                 account.setId(id_account);
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         finally{
  	    	   closeStatement();

  	       }
         
         String full = getFullName(id_account);
         
         account.setFull_name(full == null ? "":full);
         
         
         System.out.println("Account info: "+account.toString());
         return account;
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
    	
    	Connection connection = null;
    	Statement statement = null;
    	ResultSet result = null;
    	
    	try {
    		connection = dataSource.getConnection();
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}	
    	
		String path = null;
		
		try {
			String query = "SELECT cale_imagine from imagini where id_cont="+id;
			result = statement.executeQuery(query);
            if (result.next()) {
                path = result.getString("cale_imagine");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        	if (result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
	       }
		
		
		return path;
	}

    public int getWorkerCategory(int accountId){
    	createStatement();
    	
    	int categoryId = 0;
    	
		try {
			String query = "SELECT id_categorie FROM muncitor WHERE id_cont="+accountId;
            res = st.executeQuery(query);
            if (res.next()) {
                categoryId = res.getInt("id_categorie");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();

	       }
		
		return categoryId;
	}
    
    public String getFullName(int accountId){
    	
    	int profile_id = getWorkerId(accountId);
    	String full_name = null;
    	
    	createStatement();
    	
    	try {
            String query = "SELECT name, prenume FROM muncitor WHERE id_muncitor="+profile_id;
            res = st.executeQuery(query);
            if (res.next()) {
                full_name = res.getString("name")+" "+res.getString("prenume");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
 	    	   closeStatement();

 	       }
    	
    	return full_name;
    }
    
    //used to register new user profile
    public boolean registerNewProfile(Profile profile, int id){

    	createStatement();
    	
    	System.out.println("Register nw profile");
    	
        boolean register=true;
        int success=0;
        
            String query = "INSERT INTO muncitor (id_categorie, id_oras, id_cont, titlu, telefon, descriere, name, prenume ) "
            		+ "VALUES("+Integer.parseInt(profile.getCategoryId())+","+Integer.parseInt(profile.getCityId())+","
            				+id+",'"+profile.getTitle()+"','"+profile.getTelephone()+"','"+profile.getDescription()+"',"
            						+ "'"+profile.getName()+"','"+profile.getSurname()+"')";
            
        try {
        	
        	System.out.println(query);

        	
            success = st.executeUpdate(query);
            insertIntoProfileSkills(getWorkerId(id), getSkillFromCat(Integer.parseInt(profile.getCategoryId())));
            
        	System.out.println("End ----- Register nw profile");

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

    
    private void insertIntoProfileSkills(int id, ArrayList<Object> arrayList) {
    	
    	for(Object skills: arrayList){
    		
    		createStatement();
    		
        	System.out.println("Default skill level "+((Skill) skills).getName());

        	
            String query = "INSERT INTO aptitudinimuncitor (id_aptitudine, id_muncitor, nivel)" +
                    " VALUES("+((Skill) skills).getId()+","+id+","+1+")";
    	    try {
    	    	
            	System.out.println(query);

    	    	
    	        st.executeUpdate(query);
            	System.out.println("End ----- Default skill level "+((Skill) skills).getName());

    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    finally{
    	    	   closeStatement();
    	    }

    	}
    	
    	
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

   
    //------------------Comments--------------------------------
    public ArrayList<Comment> getComment(int id_profile){
    	
    	createStatement();
    	
        ArrayList<Comment> comments = new ArrayList<Comment>();

        try {
            String query = "SELECT * FROM comentarii INNER JOIN cont ON(comentarii.id_cont = cont.id_cont)"
            		+ " WHERE "
            		+ "id_muncitor="+id_profile+" LIMIT 0,5";
            res = st.executeQuery(query);

            System.out.println(query);

            while(res.next()){
                Comment comment = new Comment();
                comment.setId_muncitor(res.getInt("id_muncitor"));
                comment.setRating(res.getInt("rating"));
                comment.setDate(res.getString("data_comentariu"));
                comment.setText(res.getString("text_comentariu"));
                
                comment.setAccount_name(res.getString("email"));
                
                if(getImagePath(res.getInt("id_cont"))== null)
            		comment.setAccount_img("./resources/img/default.png");
            	else
            		comment.setAccount_img(getImagePath(res.getInt("id_cont")));

                
                comments.add(comment);

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();

	       }
        return comments;
    }

    public ArrayList<Comment> getAllComments(int id_profile){
    	
    	createStatement();
    	
        ArrayList<Comment> comments = new ArrayList<Comment>();

        try {
            String query = "SELECT * FROM comentarii INNER JOIN cont ON(comentarii.id_cont = cont.id_cont)"
            		+ " WHERE "
            		+ "id_muncitor="+id_profile;
            res = st.executeQuery(query);

            System.out.println(query);

            while(res.next()){
                Comment comment = new Comment();
                comment.setId_muncitor(res.getInt("id_muncitor"));
                comment.setRating(res.getInt("rating"));
                comment.setDate(res.getString("data_comentariu"));
                comment.setText(res.getString("text_comentariu"));
                
                comment.setAccount_name(res.getString("email"));
                
                if(getImagePath(res.getInt("id_cont"))== null)
            		comment.setAccount_img("./resources/img/default.png");
            	else
            		comment.setAccount_img(getImagePath(res.getInt("id_cont")));

                
                comments.add(comment);

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
	    	   closeStatement();

	       }
        return comments;
    }
    
    public void addComment(int id, int profile_id, int notaN, String text) {
    	createStatement();
    	
    	String query = "INSERT INTO comentarii(id_cont, id_muncitor, rating, text_comentariu,"
    			+ " data_comentariu) VALUES("+id+","+profile_id+","+notaN+",'"+text+"','"+Date.currentDate()+"')";
    	
    	try {
    		st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    
    
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
	 
	  public boolean updateProfileSkills(int nivel, int skillId, int profileId){
		  
		  createStatement();
	    	
	    	System.out.println("Register nw profile");
	    	
	        boolean update=true;
	        int success=0;
	        
	            String query = "UPDATE aptitudinimuncitor SET nivel ="+nivel+" WHERE "
	            		+ "id_aptitudine = "+skillId+" and id_muncitor = "+profileId;
	            
	        try {
	        	
	        	System.out.println(query);
	        	
	            success = st.executeUpdate(query);
	            

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally{
		    	   closeStatement();

		       }

	        if(success == 0){
	        	update=false;
	            }


	        return update;
		  
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
			
            if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
            if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			
		}

		private void createStatement(){
			try {
				con = dataSource.getConnection();
				st = con.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
            
	       
	       
		}

		

		

	
		

}
