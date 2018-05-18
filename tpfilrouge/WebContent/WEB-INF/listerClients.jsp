<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TP Fil rouge - Liste des clients existants</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css"/>" />
	</head>
	<body>
		<c:import url="/inc/menu.jsp" />
		<div class="container">
			<c:choose>
				<c:when test="${ !empty listeClients }">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th scope="col">Nom</th>
								<th scope="col">Prénom</th>
								<th scope="col">Adresse</th>
								<th scope="col">Téléphone</th>
								<th scope="col">Email</th>
								<th scope="col">Suppression</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ listeClients }" var="client" varStatus="boucle" >
								<tr>
									<td><c:out value="${ client.value.nom }"/></td>
									<td><c:out value="${ client.value.prenom }"/></td>
									<td><c:out value="${ client.value.adresse }"/></td>
									<td><c:out value="${ client.value.telephone }"/></td>
									<td><c:out value="${ client.value.email }"/></td>
									<td class="text-center"><a href="<c:url value="/suppressionClient" />?suppression=${client.value.nom}"><img src="<c:url value="/inc/supprimer.png"/>" /></a>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<span class="info">Aucun client enregistré</span>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>