<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ include file="../navigation/navbar-home.jsp" %>


<html>
<head>
<title>Login Page</title>

<!-- form.css import-->
<link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">      

<!-- All the files that are required -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

</head>
<body>
<!-- Error/Successful logout -->
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

<!-- LOGIN FORM -->
<div class="text-center" style="text-align:center;">
	<div class="logo"><strong>login</strong></div>
	<!-- Main Form -->
	<div class="form-1">
		<form id="form" name='loginForm' class="text-left" action="<c:url value='/login' />" method="POST">
			<div class="main-form">
			<button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
			
				<div class="group">
					<div class="form-group">
						<label for="lg_username">Mail</label>
						<i class="fa fa-envelope"></i>
						<input type="text" required class="form-control" id="lg_username" name="username" >
					</div>
					<div class="form-group">
						<label for="lg_password">Parola</label>
						<i class="fa fa-lock"></i>
						<input type="password" required class="form-control" id="lg_password" name="password">
					</div>
					
				</div>
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
			<div class="etc-form">
				<p>ati uitat parola? <a href="<c:url value='/password-recover' />">click aici</a></p>
				<p>creeaza cont nou? <a href="<c:url value='/register' />">cont nou</a></p>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			
		</form>
	</div>
	
</div>

		
</body>
</html>