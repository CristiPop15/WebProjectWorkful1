<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>${modifica}</title> 
</head>
<body>

	
   <h1>Modifica ${modifica} </h1>
   
   <form action="adaugare/${modifica}">
		<select name="item" id="dropdown">
		    <c:forEach items="${afisare}" var="afis">
	    		<option value="${afis}">${afis}</option>
			</c:forEach>
		</select>
		<br>		

		<input type="text" name="deAdaugat">
		<input type="submit" value="Adaugare"/>
		<button type="submit" formaction="stergere/${modifica}">Stergere</button>
	</form> 
	 
	 <form action="index">
	 	<input type="submit" value="Back to index"/>
	 </form>

</body>
</html>