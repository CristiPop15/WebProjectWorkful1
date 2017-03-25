<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Workful</title> 
	<link href="${pageContext.request.contextPath}/resources/css/design.css" rel="stylesheet">      
	</head>
<body>
   <h2>Alegeti judetul</h2>
   
	<form action="admin/index">
	  <select name="item">
	    <c:forEach items="${regions}" var="region">
    		<option value="${region}">${region}</option>
		</c:forEach>
	  </select>
	  <input type="submit" value="Submit">
	</form>   
</body>
</html>