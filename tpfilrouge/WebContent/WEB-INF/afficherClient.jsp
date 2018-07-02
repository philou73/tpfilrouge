<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Affichage d'un client</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div class="container">
        	<div class="alert alert-success">${ form.resultat }</div>
            <p>Nom : <c:out value="${ client.nom }"/></p>
            <p>Prénom : <c:out value="${ client.prenom }"/></p>
            <p>Adresse : <c:out value="${ client.adresse }"/></p>
            <p>Numéro de téléphone : <c:out value="${ client.telephone }"/></p>
            <p>Email : <c:out value="${ client.email }"/></p>
            <p>Image : <c:out value="${ client.image }"/></p>
        </div>
    </body>
</html>