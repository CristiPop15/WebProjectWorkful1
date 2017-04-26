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
	<div class="container">

<!-- ========================================== Modal content ================================= -->

		<!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    <form action="delete-profile">
		    
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
				  <label>Delete profile?</label>
		        </div>
	 
	   
	   	  <div class="modal-body">
	   	  
	    		<button type="submit" value="Yes">Yes</button>
		 </div>
		</div>
		
		</form>
		
		</div>
		
		
	</div>

<!--  ======================== DELETE ACCOUNT ========================================== -->

<div class="text-center" style="text-align:center;">
	<div class="logo">delete profile?</div>
	<!-- Main Form -->
	<div class="form-1">
			<div class="main-form">
				<button type="submit" class="delete-button" data-toggle="modal" data-target="#myModal"><i class="fa fa-times"></i></button>
			</div>
		
	</div>
	<!-- end:Password Update Form -->
</div>
</div>




</body>
</html>