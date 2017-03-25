<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Add</title> 
</head>
<body>

	
   <h1>Add skills to category </h1>
   
	<p style="color:green;">${message}</p>
   
   <form action=skillAddedToCat>
		<select name="categories">
		    <c:forEach items="${category}" var="categories">
	    		<option value="${categories}">${categories}</option>
			</c:forEach>
		</select>
		
		<select name="skills">
		    <c:forEach items="${skill}" var="afis">
	    		<option value="${afis}">${afis}</option>
			</c:forEach>
		</select>
		<input type="hidden" name="success" value="success"/>
		<input type="hidden" name="show" value="show"/>
		
		<br>		
	
		<input type="submit" value="Add"/>
		<input type="submit" value="Show"  formaction="showSkills"/>
	</form>
	
	
		<ul>
			<c:forEach var="showSkills" items="${list}">
				<li>${showSkills}</li>
			</c:forEach>
		</ul>
	
	
	<form action="index">
		<input type="submit" value="Back to index"/>
	</form>

</body>
</html>