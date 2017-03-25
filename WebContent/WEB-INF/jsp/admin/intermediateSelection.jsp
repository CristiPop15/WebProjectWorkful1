<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Select</title> 
</head>
<body>

   <h1>Select ${choice}</h1>

<form action="${type}/${choice}">
		<select name="item" id="dropdown">
		    <c:forEach items="${list}" var="afis">
	    		<option value="${afis}">${afis}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Go"/>
</form>

</body>