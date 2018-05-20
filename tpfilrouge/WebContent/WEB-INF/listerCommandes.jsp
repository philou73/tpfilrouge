<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
	<head>
		<meta charset="UTF-8">
		<title>TP Fil rouge - Liste des commandes existantes</title>
		<link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css" />" >
	</head>
	<body>
		<c:import url="/inc/menu.jsp"></c:import>
		<div class="container">
			<c:choose>
				<c:when test="${ !empty listeCommandes }">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th scope="col">Client</th>
								<th scope="col">Date</th>
								<th scope="col">Montant</th>
								<th scope="col">Mode paiement</th>
								<th scope="col">Statut paiement</th>
								<th scope="col">Mode livraison</th>
								<th scope="col">Statut livraison</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ listeCommandes }" var="commande" >
								<tr>
									<td><c:out value="${ commande.value.client.nom}" /> <c:out value="${commande.value.client.prenom}"/></td>
									<td><c:out value="${ commande.value.date }"/></td>
									<td><c:out value="${ commande.value.montant }"/></td>
									<td><c:out value="${ commande.value.modePaiement }"/></td>
									<td><c:out value="${ commande.value.statutPaiement }"/></td>
									<td><c:out value="${ commande.value.modeLivraison }"/></td>
									<td><c:out value="${ commande.value.statutLivraison }"/></td>
									<td><a href="<c:url value="/suppressionCommande"><c:param name="suppression" value="${ commande.key }" /></c:url>" ><img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" /></a>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<span class="info">Aucune commande enregistrée</span>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>