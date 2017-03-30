<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
	<title>Workful</title> 
	</head>
<body>
   <a href="<c:url value="/logout" />" > Logout</a>  
	
	<br>
   <h1>Workful ControlPanel</h1>
   
   
   <h2 style="text-decoration:underline">Modifica:</h2>
   
   <strong>Individual changes</strong>
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
	<hr>
	<br>
	<strong>Group changes</strong>
	<form action="intermediateSelection">
		<table>
			<tr>
				<td>
	   				<select name="type">
	    				<option value="add">Adauga</option>
	    				<option value="delete">Sterge</option>
	   				</select>		
				</td>
				<td> <p>Aptitudini la Categorii</p> </td>
			</tr>
			<tr>
				<td><input type="submit" value="Enter"></td>
			</tr>
		</table>
	</form>
	<hr>

	   
</body>
</html>