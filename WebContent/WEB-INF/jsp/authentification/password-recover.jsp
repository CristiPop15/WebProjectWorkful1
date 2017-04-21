<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ include file="../navigation/navbar-home.jsp" %>

<html>
<head>
<title>Password Recovery Page</title>

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

<!-- FORGOT PASSWORD FORM -->
<div class="text-center" style="text-align:center;">
	<div class="logo">forgot password</div>
	<!-- Main Form -->
	<div class="form-1">
		<form id="forgot-password-form" class="text-left" action="recover-password">
			<div class="etc-form">
				<p>When you fill in your registered email address, you will be sent instructions on how to reset your password.</p>
			</div>
			<div class="main-form">
			<button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
			
				<div class="group">
					<div class="form-group">
						<label>Email address</label>
						<input type="text" required class="form-control" name="email">
					</div>
				</div>
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
			<div class="etc-form">
				<p>already have an account? <a href="<c:url value='/login' />">login here</a></p>
				<p>new user? <a href="<c:url value='/register' />">create new account</a></p>
			</div>
		</form>
	</div>
	<!-- end:Main Form -->
</div>

</body>
</html>