<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Delete</title> 
</head>
<body>

	
   <h1>Delete skills from ${categoryChoiceName} </h1>
   
	<p style="color:red;">${message}</p>
   
   <form action="${pageContext.request.contextPath}/admin/deleteSkillsFromCategory">
   	<input type="hidden" name="category" value="${categoryChoice}"/>
		
		<select name="item">
		    <c:forEach items="${skills}" var="skill">
	    		<option value="${skill.getId()}">${skill.getName()}</option>
			</c:forEach>
		</select>
		
		<input type="submit" value="Delete" ${disabled}/>		
	</form>
	
	<form action="${pageContext.request.contextPath}/admin/intermediateSelection">
		<input type="submit" value="Back"/>
		<input type="hidden" name="type" value="${type}"/>
	</form>
	
	<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>

</body>
</html>