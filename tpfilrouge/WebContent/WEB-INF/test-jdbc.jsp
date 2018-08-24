<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Tests JDBC</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css" />" />
	</head>
	<body>
		<h1>Tests JDBC</h1>
		
		<c:forEach items="${ messages }" var="message" varStatus="boucle">
			<p>{ $boucle.count }. ${ message }</p> 
		</c:forEach>
		<p>Fin du tableau</p> 
	</body>
</html>