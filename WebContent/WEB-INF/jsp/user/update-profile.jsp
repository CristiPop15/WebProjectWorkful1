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
	<div class="logo"><h1 style="color:black"><strong> UPDATE PROFILE</strong></h1></div>
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

<!-- ========================================== Modal content ================================= -->

<c:if test="${not empty popup}">

	<form action="delete-profile">
		<div class="modal-content">
		  <div class="modal-header">
			<div class="logo">Delete account?</div>
	  	  </div>
	 
	   
	   	  <div class="modal-body">
	   	    	<button formaction="<c:url value="update-profile"/>" class="close"><i class="fa fa-times"></i></button>
	   	  
	    		<button type="submit" class="yes"><i class="fa fa-chevron-right"></i></button>
		 </div>
		</div>
		
	</form>
</c:if>

<!--  ======================== DELETE PROFILE ========================================== -->

<div class="text-center" style="text-align:center;">
	<div class="logo">delete profile?</div>
	<!-- Main Form -->
	<div class="form-1">
		<form class="text-left" action="update-profile">		
			<div class="main-form">
				<input type="hidden" value="popup" name="popup">
				<button type="submit" class="delete-button"><i class="fa fa-times"></i></button>
			</div>
		</form>
		
	</div>
</div>




</body>
</html>