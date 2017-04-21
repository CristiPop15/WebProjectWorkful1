<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Remove</title> 
</head>
<body>

<h1>Select city to be deleted from ${regionName}</h1>

<form action="${pageContext.request.contextPath}/admin/deleteFromRegion">
		<select name="item" id="dropdown">
		    <c:forEach items="${list}" var="afis">
	    		<option value="${afis.getId()}">${afis.getName()}</option>
			</c:forEach>
		</select>
		<br>	
		<input type="hidden" name="region" value="${region}"/>
			

		<input type="submit" value="Stergere" ${disabled}/>
		<input type="submit" value="Back to index" formaction="${pageContext.request.contextPath}/admin/index"/>
		
</form> 

</body>
</html>