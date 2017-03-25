<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>${type}</title> 
</head>
<body>

<h1>Select ${choice} to be deleted</h1>
<form action="delete/${choice}">
		<select name="item" id="dropdown">
		    <c:forEach items="${list}" var="afis">
	    		<option value="${afis}">${afis}</option>
			</c:forEach>
		</select>
		<br>		

		<input type="submit" value="Stergere"/>
</form> 

<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>

</body>