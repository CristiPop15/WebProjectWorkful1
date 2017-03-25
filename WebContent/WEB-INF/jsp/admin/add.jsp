<%@page contentType="text/html; charset=UTF-8"%>

<html>
<head>
	<title>${type}</title> 
</head>
<body>

   <h1>Add new ${choice} </h1>

<form action="${type}/${choice}">
	<input type="text" name="new"/>
	<input type="hidden" name="${choice}"/>
	<input type="submit" value="Save"/>
</form>

</body>