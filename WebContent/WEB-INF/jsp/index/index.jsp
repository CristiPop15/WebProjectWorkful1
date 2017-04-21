<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${user=='1'}">
        <c:if test="${not empty path}">
	    	<jsp:include page="../navigation/navbar-user.jsp" >
			  <jsp:param name="img-path" value="${path}" />
			</jsp:include>	
    	</c:if>
        <br />
    </c:when>    
    <c:otherwise>
   		 <%@ include file="../navigation/navbar.jsp" %>
    </c:otherwise>
</c:choose>

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

	<div class="container">


<!--  ======================== SEARCH FORM ========================================== -->

<div class="text-center" style="text-align:center;">
	<div class="logo">search</div>
	<!-- Main Form -->
	<div class="form-1">
	
		<form class="text-left" action="search">		
			<div class="main-form">
		
				<!--  button for filter and show modal with filters -->
				<!-- Trigger the modal with a button -->
				
				<button type="button" class="filter" data-toggle="modal" data-target="#myModal"><i class="fa fa-filter"></i></button>
				
				<div class="group">
					<div class="form-group">
						<input type="text" class="form-control" name="query">
					</div>
				</div>
				<button type="submit" class="button"><i class="fa fa-search"></i></button>
			</div>
		</form>
	</div>
	<!-- end:Main Form -->
</div>



	
<!-- ========================================== Modal content ================================= -->
		  		
		  <!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
				  <label>Filters</label>
		        </div>
		        
		        <form action="search">
		        <div class="modal-body">
		         <div class="form-group">
		         <div class="group">
		         
					<div class="form-group">
						<label>city</label>
						<br>
						<!-- CATEGORY filter -->
						<select class="rounded" name="city">
							<option selected disabled>Oras</option>
						
						    <c:forEach items="${city}" var="afis">
					    		<option value="${afis.getId()}">${afis.getName()}</option>
							</c:forEach>
						</select>
				   </div>	
	
					<div class="form-group">
						<label>category</label>
						<br>
						<!-- CATEGORY filter -->
						<select class="rounded" name="category">
							<option selected disabled>Categorie</option>
						
						    <c:forEach items="${category}" var="afis">
					    		<option value="${afis.getId()}">${afis.getName()}</option>
							</c:forEach>
						</select>
				   </div>	
				  </div>				  
		        </div>
		        </div>
		        <div class="modal-footer">
		          <input type="submit"  class="btn btn-default"/>
		        </div>
		         </form>
		      </div>
		      
		    </div>
		  </div>
		  
		</div>

</body>
</html>