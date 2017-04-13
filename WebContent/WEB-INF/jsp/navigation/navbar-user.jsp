<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Workful</title>
  
  
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
  
  
</head>

<body>


<nav class="navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<c:url value='/index' />">Workful</a>
    </div>
    
    <ul class="nav navbar-nav navbar-right">
		<li style="padding:0px">
			<a style="padding:0px" href="<c:url value='/user/settings' />">  
				<img src="<%= request.getParameter("img-path")%>" class="img-circle" width="50" height="50"/>				
			</a>
       </li>  
       <li>
       	<a style="border-top:15px" href="<c:url value='/logout' />"><span class="glyphicon glyphicon-log-out"></span> Log out</a>
       </li>
    </ul>
  </div>
</nav>

</body>
</html>
