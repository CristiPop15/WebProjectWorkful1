<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@page session="true"%>
<%@ include file="../navigation/navbar-home.jsp" %>

<html>
<head>
<title>Registration Page</title>

<!-- login.css import-->
<link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">      

<!-- All the files that are required -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

</head>
<body>


<!-- Errors -->
<div class="text-center" style="text-align:center;">
	<div class="form-1">
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
	</div>
</div>


<!-- REGISTRATION FORM -->
<div class="text-center" style="text-align:center;">
	<div class="logo">register</div>
	<!-- Main Form -->
	<div class="form-1">
		<form:form class="text-left" modelAtribute="registrationForm" action="registration" method="POST">
			<div class="main-form">
			<button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
			
				<div class="group">
				
				<!-- E-MAIL -->	
					<div class="form-group">
					  	<label>Email</label>  
						<input type="text" required class="form-control" id="email" name="email">
					</div>
					
					<!-- PASSWORD -->					
					<div class="form-group">
						<label>Parola</label>
						<input type="password" required class="form-control" id="password" name="password">
					</div>
					
					<!-- CONFIRM PASSWORD -->
					<div class="form-group">
						<label>Confirmare Parola</label>  
						<input type="password" required class="form-control" id="confirmPassword" name="confirmPassword">
					</div>
					
							
					<div class="form-group login-group-checkbox">
						<input type="checkbox" required class="" id="reg_agree" name="reg_agree">
						<label for="reg_agree">i agree with <a href="#">terms</a></label>
					</div>
				</div>
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
			<div class="etc-form">
				<p>already have an account? <a href="<c:url value='/login' />">login here</a></p>
			</div>
		</form:form>
	</div>
	<!-- end:Main Form -->
</div>

</body>
</html>