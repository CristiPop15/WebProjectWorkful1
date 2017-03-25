<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Add</title> 
</head>
<body>

	
   <h1>Add skills to ${categoryChoice} </h1>
   
	<p style="color:green;">${message}</p>
	<p style="color:red;">${Ermessage}</p>
	
   
   <form action="${pageContext.request.contextPath}/admin/addSkillsToCategories">
   	<input type="hidden" name="category" value="${categoryChoice}"/>
   	
   	
   	<table border="1">
   		<tr>
   			<td><p>Skills for ${categoryChoice}:</p></td>
   			<td>Select skill to be added:</td>
   		</tr>
		<tr>   	
	   		<td>
				<ul>
					<c:forEach var="choiceList" items="${defaultSkills}">
						<li>${choiceList}</li>
					</c:forEach>
				</ul>
			</td>
			<td><select name="item">
				    <c:forEach items="${newSkills}" var="skills">
		   		 		<option value="${skills}">${skills}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		
	</table>
	
	<input type="submit" value="Add"/>		
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