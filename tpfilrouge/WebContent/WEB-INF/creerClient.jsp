<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un client</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div class="container col-xs-8">
<!-- 	        <div class="row"> -->
<!-- 	        	<div class="col-xs-8"> -->
	        		<h3>Informations client</h3>
		            <form method="post" action="<c:url value="/creationClient"/>">
	                    <c:import url="/inc/inc_client_form.jsp" />
		                <p class="info">${ form.resultat }</p>
		                <div>
			                <button class="btn btn-success" type="submit">Enregistrer</button>
			                <button class="btn btn-light" type="reset">Remettre à zéro</button>
		                </div>
		            </form>
<!-- 	            </div> -->
<!-- 	        </div> -->
        </div>
    </body>
</html>