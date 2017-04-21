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
  
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
    
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
  <link href="${pageContext.request.contextPath}/resources/css/form.css" rel="stylesheet">   
  
  
</head>

<body>
<div class="text-left" style="text-align:center;">
<img alt="" src="${profile.getImgPath() }">
<br>
<label>${profile.getTitle()}</label>
<br>
<label>${profile.getName()} ${profile.getSurname()}</label>
<br>
<label>${profile.getCategoryId()}</label>
<br>
<label>${profile.getCityId()}</label>
<br>
<label>${profile.getDescription()}</label>
<br>
<label>${profile.getTelephone()}</label>

	
</div>

<div>
<h2>Skills</h2>
<c:forEach items="${skills}" var="skill">
		
		<p>${skill.getName() } --> ${skill.getLevel() }/5</p>
		<br>
		
	</c:forEach>
</div>

<div>
<h2>Comments</h2>
<p>${nocomment }</p>
<c:forEach items="${comments}" var="comment">
		<img src="${comment.getAccount_img() }" class="img-circle" width="50" height="50"/>				
		<p>${comment.getAccount_name() }</p>
		<p>${comment.getText() }</p>
		<p>${comment.getRating() }/5</p>
		<p>${comment.getDate() }</p>
		
		<br>
		
</c:forEach>
	
	 <c:if test="${user=='1'}">
		<div class="container">
		  <!-- Trigger the modal with a button -->
		  <button type="button" class="button" data-toggle="modal" data-target="#myModal">Add Comment</button>
		
		  <!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
				  <label>Comentariu pentru: ${profile.getName()} ${profile.getSurname()}</label>
		        </div>
		        
		        <form action="add-comment">
		        <div class="modal-body">
		          <p>Adauga comentariu</p>
		          
		          	<label>Nota:</label>
					<input type="number" required name="nota" min="1" max="5">
									
					<textarea rows="10" required class="form-control" name="comment"></textarea>
		          	<input type="hidden" name="profile_id" value="${profile.getId() }">
		         	
		        </div>
		        <div class="modal-footer">
		          <input type="submit"  class="btn btn-default"/>
		        </div>
		         </form>
		      </div>
		      
		    </div>
		  </div>
		  
		</div>
	 </c:if>
	

</div>


</body>
</html>
