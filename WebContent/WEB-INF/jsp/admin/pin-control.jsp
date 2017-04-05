<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   


<html>
<head>
	<title>PIN</title> 
</head>
<body>


   <h1>Enter PIN</h1>
	<p style="color:red;">${error}</p>

<form:form action="new-admin" method="POST">
		<input type="password" name="new-admin">
		<input type="submit" value="Enter"/>
</form:form>

<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>

	<br>
	<hr>
	<br>
<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>
</body>
</html>