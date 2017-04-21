<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   



<c:if test="${not empty path}">
   	<jsp:include page="../navigation/navbar-user.jsp" >
	   <jsp:param name="img-path" value="${path}" />
	</jsp:include>	
</c:if>
		 			

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
	<div class="logo"><h1 style="color:black"><strong>PROFILE</strong></h1></div>
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

<c:url var="create_url"  value="/user/create-profile" />


<!--  ======================== PROFILE FORM ========================================== -->

<div class="text-center" style="text-align:center;">
	<div class="logo">create profile</div>
	
	<!-- Main Form -->
	<div class="form-1">
	
		<form:form class="text-left" modelAtribute="profileForm" action="${create_url}" method="get">		
			<div class="main-form">						
				
				<button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
			
				<div class="group">
				
					<div class="form-group">
						<label>name</label>
						<input type="text" required class="form-control" name="name">
					</div>
					<div class="form-group">
						<label>surname</label>
						<input type="text" required class="form-control" name="surname">
					</div>
					<div class="form-group">
						<label>title</label>
						<input type="text" required class="form-control" name="title">
					</div>
					
					<div class="form-group">
						<label>city</label>
						<br>
				    	<!-- City filter -->
						<select class="rounded" name="cityId">
						    <c:forEach items="${city}" var="afis">
					    		<option value="${afis.getId()}">${afis.getName()}</option>
							</c:forEach>
						</select>
					</div>
	
					<div class="form-group">
						<label>category</label>
						<br>
						<!-- CATEGORY filter -->
						<select class="rounded" name="categoryId">
						    <c:forEach items="${category}" var="afis">
					    		<option value="${afis.getId()}">${afis.getName()}</option>
							</c:forEach>
						</select>
				   </div>
				   
				   <div class="form-group">
						<label>  telephone</label>
						<input type="tel" required pattern="[0-9]{10}" maxlength="10" class="form-control" name="telephone">
					</div>
				   
				   	<div class="form-group">
						<label>about you</label>
						<textarea rows="10" required class="form-control" name="description"></textarea>
					</div>
					
					
				</div>
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
		</form:form>
		
	</div>
	<!-- end:Form -->
</div>

<br>

<!-- ========================================== Image change ================================= -->

<div class="text-center" style="text-align:center;">
	<div class="logo">Change Picture</div>
	<!-- Main Form -->
	<div class="form-1">
		
		<form action="./update-image?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
			<div class="main-form">
			
				<label>changing the picture will automatically change your account picture</label>
				
				<input type="file" name="image">
				
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
		</form>
		
	</div>
</div>


<br>


</body>
</html>