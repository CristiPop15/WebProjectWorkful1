<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<c:choose>
    <c:when test="${user=='1'}">
        <c:if test="${not empty path}">
	    	<jsp:include page="./navigation/navbar-user.jsp" >
			  <jsp:param name="img-path" value="${path}" />
			</jsp:include>	
    	</c:if>
        <br />
    </c:when>    
    <c:otherwise>
   		 <%@ include file="./navigation/navbar.jsp" %>
    </c:otherwise>
</c:choose>

<html lang="en">
<head>
  <title>Workful</title>
    
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
  <link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">   
  
  
</head>

<body>
<div class="text-center" style="text-align:center;">

<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
</c:if>
</div>

<div class="text-left" style="text-align:center;">

<c:forEach items="${result}" var="afis">
	<form action="profile-view">
		<div class="main-form">
			<img alt="blabla" src="${afis.getImgPath()}" width="80" height="80"/>	
			<label>${afis.getSurname()} ${afis.getName()}</label>
			
			<br>
			<label>${afis.getTitle()}</label> 
			<br>
			<label>${afis.getCity()}</label>
			<br>
			<label>${afis.getCategory()}</label>
			<input type="hidden" value=${afis.getId() } name="id">
			<br>
		
		<input type="submit" value="More..">
		</div>
		
	</form>
			<hr>
		
</c:forEach>
	
</div>
	
	
<ul class="pagination">
  	<c:forEach items="${pagination}" var="a">
		
		<li><a href="#">${a}</a></li>
		
	</c:forEach>
</ul>
	
</body>
</html>
