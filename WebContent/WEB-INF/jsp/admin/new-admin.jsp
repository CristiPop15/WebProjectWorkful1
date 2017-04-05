<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@page session="true"%>
<html>
<head>
<title>Register</title>

</head>
<body>

<!-- Errors -->
<div class="text-center" style="text-align:center;">
	<div class="login-form-1">
			<c:if test="${not empty error}">
				<div class="error" style="color:red;">${error}</div>
			</c:if>
	</div>
</div>


<!-- REGISTRATION FORM -->

	<form:form action="registration" method="POST">
			
			<!-- E-MAIL -->	
				<div>
				  	<label>Email</label>  
					<input type="text" required name="email">
				</div>
					
					<!-- PASSWORD -->					
					<div>
						<label>Parola</label>
						<input type="password" required name="password">
					</div>
					
					<!-- CONFIRM PASSWORD -->
					<div>
						<label>Confirmare Parola</label>  
						<input type="password" required name="confirmPassword">
					</div>
					
				<button type="submit">Add</button>
			
		</form:form>
	<br>
	<hr>
	<br>
<form action="${pageContext.request.contextPath}/admin/index">
		<input type="submit" value="Back to index"/>
	</form>
</body>
</html>