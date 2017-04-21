<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   

	

<html>
<head>

	<title>Workful</title> 
	<link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">   
	<link href="${pageContext.request.contextPath}/resources/css/modal-delete-account.css" rel="stylesheet">      
	   
	
	<!-- All the files that are required -->
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	   
</head>
<body>

<div class="text-center" style="text-align:center;">
	<div class="logo"><h1 style="color:black"><strong>Skill Level</strong></h1></div>
</div>

<!-- Error/Successful -->
<div class="text-center" style="text-align:center;">
	<div class="form-1">
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if>
	</div>
</div>

<c:url var="update_url"  value="/altceva" />

<div class="text-center" style="text-align:center;">

	<div class="form-1">
		<form:form class="text-left" action="${update_url}" method="get">		
					<div class="main-form">						
						
						<button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
					
						<div class="group">
						
								<c:forEach var="skill" items="${skills}">
									<div class="form-group">
								
									  <label>${skill.getName()}</label>
									  <input type="number" required name="${skill.getId()}" min="1" max="5">
									
									</div>
								</c:forEach>
							
						</div>
						<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
						
					</div>
					
		</form:form>
	</div>
</div>
	<!-- end:Form -->


