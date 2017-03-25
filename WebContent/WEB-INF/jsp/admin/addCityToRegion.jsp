<%@page contentType="text/html; charset=UTF-8"%>

<html>
<head>
	<title>Add</title> 
</head>
<body>

   <h1>Add new city to ${region} </h1>

<form action="${pageContext.request.contextPath}/admin/addNewCity">
	<input type="text" name="new"/>
	<input type="hidden" name="region" value="${region}"/>
	<input type="submit" value="Save"/>
	
	<input type="submit" value="Back to index" formaction="${pageContext.request.contextPath}/admin/index"/>
	
</form>

</body>