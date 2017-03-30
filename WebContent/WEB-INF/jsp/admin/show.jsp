<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>${type}</title> 
</head>
<body>

<h1>List ${choice}</h1>

		<ul>
			<c:forEach var="choiceList" items="${list}">
				<li>${choiceList.getName()}</li>
			</c:forEach>
		</ul>
		
		<form action="${pageContext.request.contextPath}/admin/index">
			<input type="submit" value="Back to index"/>
		</form>
 

</body>