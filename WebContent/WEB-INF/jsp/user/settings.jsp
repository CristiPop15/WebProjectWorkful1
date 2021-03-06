<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



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
	<div class="logo"><h1 style="color:black"><strong>SETTINGS</strong></h1></div>
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

<!--  ======================== PASSWORD UPDATE FORM ========================================== -->

<div class="text-center" style="text-align:center;">
	<div class="logo">password update</div>
	
	<!-- Main Form -->
	<div class="form-1">
	
		<form class="text-left" action="passwordc" method="post">		
			<div class="main-form">	
			  <button type="reset" class="filter"><i class="fa fa-repeat"></i></button>
								
				<div class="group">
				
					<div class="form-group">
						<label>New Password</label>
						<input type="password" required class="form-control" name="new">
					</div>
					<div class="form-group">
						<label>Confirm New Password</label>
						<input type="password" required class="form-control" name="confirm">
					</div>
					<input type="hidden"
					 name="${_csrf.parameterName}" 
					 value="${_csrf.token}"/>
					
				</div>
				<button type="submit" class="button"><i class="fa fa-chevron-right"></i></button>
			</div>
		</form>
		
	</div>
	<!-- end:Password Update Form -->
</div>

<br>

<!-- ========================================== Image change ================================= -->

<div class="text-center" style="text-align:center;">
	<div class="logo">Change Picture</div>
	<!-- Main Form -->
	<div class="form-1">
		
		<form action="./update-image?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
			<div class="main-form">

				
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
		    <form action="delete-account">
		    
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
				  <label>Delete account?</label>
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
	<div class="logo">delete account?</div>
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