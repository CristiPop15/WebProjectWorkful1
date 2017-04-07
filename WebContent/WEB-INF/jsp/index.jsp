<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${user=='1'}">
        <%@ include file="navigation/navbar.jsp" %>
        <br />
    </c:when>    
    <c:otherwise>
        <%@ include file="navigation/navbar-user.jsp" %>
	  <br />
    </c:otherwise>
</c:choose>

<html>
<head>

	<title>Workful</title> 
	<link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">   
	<link href="${pageContext.request.contextPath}/resources/css/modal-index.css" rel="stylesheet">      
	   
	
	<!-- All the files that are required -->
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	   
</head>
<body>
<!-- SEARCH FORM -->
<div class="text-center" style="text-align:center;">
	<div class="logo">search</div>
	<!-- Main Form -->
	<div class="form-1">
	
		<form class="text-left" action="#">		
			<div class="main-form">
		
				<!--  button for filter and show modal with filters -->
				<button class="filter" formaction="<c:url value='/index'/>"><i class="fa fa-filter"></i></button>
				<input type="hidden" name="show" value="show"/>
				
				<div class="group">
					<div class="form-group">
						<input type="text" class="form-control" name="#">
					</div>
				</div>
				<button type="submit" class="button"><i class="fa fa-search"></i></button>
			</div>
		</form>
	</div>
	<!-- end:Main Form -->
</div>


<c:if test="${not empty region}">
	
	<!-- Modal content -->
	<form action="#">
		<div class="modal-content">
		  <div class="modal-header">
		    <button formaction="<c:url value="/index"/>" class="close"><i class="fa fa-times"></i></button>
			<div class="logo">select filters</div>
	  	  </div>
	   
	   	  <div class="modal-body">
	    
	    	<!-- REGION filter -->
			<select class="rounded" name="region">
	    		<option selected disabled>Judet</option>
			    <c:forEach items="${region}" var="afis">
		    		<option value="${afis.getId()}">${afis.getName()}</option>
				</c:forEach>
			</select>
			<br>
			<hr>
	
			
			<!-- CATEGORY filter -->
			<select class="rounded" name="category">
	    		<option selected disabled>Categorie</option>
			    <c:forEach items="${category}" var="afis">
		    		<option value="${afis.getId()}">${afis.getName()}</option>
				</c:forEach>
			</select>
		 </div>
	  	
	  	 <div class="modal-footer">
			<button type="submit" class="close"><i class="fa fa-search"></i></button>
	   	 </div>
		</div>
	</form>
</c:if>

</body>
</html>