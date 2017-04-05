<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@page session="true"%>
<html>
<head>
<title>Registration Page</title>

<!-- login.css import-->
<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">      

<!-- All the files that are required -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

</head>
<body>


<!-- Errors -->
<div class="text-center" style="text-align:center;">
	<div class="login-form-1">
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
	</div>
</div>


<!-- REGISTRATION FORM -->
<div class="text-center" style="text-align:center;">
	<div class="logo">register</div>
	<!-- Main Form -->
	<div class="login-form-1">
		<form:form id="register-form" class="text-left" modelAtribute="registrationForm" action="registration" method="POST">
			<div class="login-form-main-message"></div>
			<div class="main-login-form">
				<div class="login-group">
				
				<!-- E-MAIL -->	
					<div class="form-group">
					  	<label for="reg_email" class="sr-only">Email</label>  
						<input type="text" required class="form-control" id="email" name="email">
					</div>
					
					<!-- PASSWORD -->					
					<div class="form-group">
						<label for="reg_password" class="sr-only">Parola</label>
						<input type="password" required class="form-control" id="password" name="password">
					</div>
					
					<!-- CONFIRM PASSWORD -->
					<div class="form-group">
						<label for="reg_password_confirm" class="sr-only">Confirmare Parola</label>  
						<input type="password" required class="form-control" id="confirmPassword" name="confirmPassword">
					</div>
					
							
					<div class="form-group login-group-checkbox">
						<input type="checkbox" required class="" id="reg_agree" name="reg_agree">
						<label for="reg_agree">i agree with <a href="#">terms</a></label>
					</div>
				</div>
				<button type="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
			</div>
			<div class="etc-login-form">
				<p>already have an account? <a href="<c:url value='/login' />">login here</a></p>
			</div>
		</form:form>
	</div>
	<!-- end:Main Form -->
</div>

</body>
</html>