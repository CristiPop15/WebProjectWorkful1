<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<c:choose>
    <c:when test="${user=='1'}">
        <c:if test="${not empty path}">
	    	<jsp:include page="./navigation/navbar-user.jsp" >
			  <jsp:param name="img-path" value="${path}" />
			</jsp:include>	
    	</c:if>
        <br />
    </c:when>    
    <c:otherwise>
   		 <%@ include file="./navigation/navbar.jsp" %>
    </c:otherwise>
</c:choose>

<html lang="en">
<head>
  <title>Workful</title>
    
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
  <link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">   
  
  
</head>

<body>
<div class="text-center" style="text-align:center;">

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



<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
</c:if>
</div>

<div class="text-left" style="text-align:center;">

<c:forEach items="${result}" var="afis">
	<form action="profile-view">
		<div class="main-form">
			<img alt="blabla" src="${afis.getImgPath()}" width="80" height="80"/>	
			<label>${afis.getSurname()} ${afis.getName()}</label>
			
			<br>
			<label>${afis.getTitle()}</label> 
			<br>
			<label>${afis.getCity()}</label>
			<br>
			<label>${afis.getCategory()}</label>
			<input type="hidden" value=${afis.getId() } name="id">
			<br>
		
		<input type="submit" value="More..">
		</div>
		
	</form>
			<hr>
		
</c:forEach>
	
</div>
	
	
<div class="text-center" style="text-align:center;">
	<div class="form-1">
		<form class="text-left" action="search">	
			<input type="hidden" name="city" value="${cityP}"/><p>${cityP }</p>
			<input type="hidden" name="category" value="${categoryP}"/><p>${categoryP }</p>
			<input type="hidden" name="query" value="${queryP}"/><p>${queryP }</p>
			<input type="hidden" name="limit" value="${limitP }"/><p>${limit}</p>	
			<div class="main-form">			
			 <c:if test="${limit >1}">	
				<button type="submit" class="filter" ><i class="fa fa-filter"></i></button>
			</c:if>
			<c:if test="${empty end}">
				<button type="submit" class="button"><i class="fa fa-search"></i></button>
			</c:if>
			<p>${limit}</p>
			</div>
		</form>
	</div>
	<!-- end:Main Form -->
</div>
	
</body>
</html>
