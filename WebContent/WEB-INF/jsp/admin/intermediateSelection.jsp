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
	    		<option value="${afis.getId()}">${afis.getName()}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Go" ${disabled}/>
</form>

<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>

</body>