<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TP Fil rouge - Liste des clients existants</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
	</head>
	<body>
		<c:import url="/inc/menu.jsp" />
		<c:choose>
			<c:when test="${ !empty listeClients }">
				<table>
					<thead>
						<tr>
							<th>Nom</th>
							<th>Prénom</th>
							<th width="50%">Adresse</th>
							<th>Téléphone</th>
							<th>Email</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ listeClients }" var="client" varStatus="boucle" >
							<tr class="${ boucle.index %2 == 0 ? 'pair' : 'impair' }">
								<td><c:out value="${ client.value.nom }"/></td>
								<td><c:out value="${ client.value.prenom }"/></td>
								<td><c:out value="${ client.value.adresse }"/></td>
								<td><c:out value="${ client.value.telephone }"/></td>
								<td><c:out value="${ client.value.email }"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<span class="info">Aucun client enregistré</span>
			</c:otherwise>
		</c:choose>
	</body>
</html>