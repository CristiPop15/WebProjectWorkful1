<%@page contentType="text/html; charset=UTF-8"%>

<html>
<head>
	<title>Workful</title> 
	</head>
<body>
   <h1>Workful index</h1>
   
   <h2>Modifica:</h2>
   
   <form action="afisare">
	   <select name="type">
	    	<option value="add">Adauga</option>
	    	<option value="delete">Sterge</option>
	    	<option value="show">Afiseaza</option>
	   </select>
	    <select name="choice">
	    	<option value="judet">Judete</option>
	    	<option value="oras">Orase</option>
	    	<option value="categorie">Categorii</option>
	    	<option value="aptitudini">Aptitudini</option>	    	
	   </select>
	  <input type="submit" value="Enter">
	</form>
	
	<br>
	
	<form action="AddSkillsToCategories">
		<input type="submit" value="Adauga Aptitudini la Categorii"/>
	</form>
	
	   
</body>
</html>