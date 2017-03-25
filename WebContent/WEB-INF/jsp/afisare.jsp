<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<link href="${pageContext.request.contextPath}/resources/css/design.css" rel="stylesheet">      
<body>
	<h1>Orasele din judetul ${region} sunt:</h1>

		<ul>
			<c:forEach var="oras" items="${orase}">
				<li>${oras}</li>
			</c:forEach>
		</ul>
		
		<form action="index">
			<input type="submit" value="Back"/>
		</form>

</body>
</html>