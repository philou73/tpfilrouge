<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/bootstrap/css/bootstrap.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div class="container">
            <form method="post" action="<c:url value="/creationCommande"/> ">
                <%-- Petite astuce ici : placer le client accessible via  
                 l'EL ${ commande.client } dans une variable "client" de 
                 portée request, pour que le code du fragment fonctionne
                 à la fois depuis le formulaire de création d'un client 
                 et depuis celui de création d'une commande. --%>
                <c:set var="client" value="${ commande.client }" scope="request" />
                <h6>Informations client</h6>
                <c:choose>
                	<c:when test="${ !empty listeClients }">
		                <div id="choixClient" >
		                	<div class="form-group row">
								<label class="col-sm-3 col-form-label col-form-label-sm" for="selectionClient">Sélection d'un client existant</label>
			                    <div class="col-sm-8">
									<input onClick="showHideClients()" class="form-check-input" type="checkbox" id="selectionClient" name="selectionClient" value="" />
									<span class="erreur">${form.erreurs['dateCommande']}</span>
								</div>
							</div>
		                </div>
		                <div id="listeClients" style="display: none;">
		                	<div class="form-group row">
		                		<label for="selectClient" class="col-sm-3 col-form-label col-form-label-sm">Choix du client</label>
		                		<select class="form-control col-sm-8" name="selectClient" id="selectClient"> 
		                			<option> -- Sélectionnez un client --</option>
		                			<c:forEach var="client" items="${listeClients}">
		                				<option value="<c:out value="${client.value.nom}" />"><c:out value="${client.value.prenom}" /> <c:out value="${client.value.nom}" /></option>
		                			</c:forEach>
		                		</select>
		                	</div>
		                </div>
					</c:when>
	            </c:choose>
           	    <c:import url="/inc/inc_client_form.jsp" />
                
                <h6>Informations commande</h6>
				<div class="form-group row">
					<label class="col-sm-3 col-form-label col-form-label-sm" for="dateCommande">Date <span class="requis">*</span></label>
                    <div class="col-sm-4">
						<input class="form-control form-control-sm" type="text" id="dateCommande" name="dateCommande" value="<c:out value="${commande.date}"/>" size="30" maxlength="30" disabled />
						<span class="erreur">${form.erreurs['dateCommande']}</span>
					</div>
				</div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label col-form-label-sm" for="montantCommande">Montant <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control form-control-sm" type="text" id="montantCommande" name="montantCommande" value="<c:out value="${commande.montant}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['montantCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label col-form-label-sm" for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control form-control-sm" type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${commande.modePaiement}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['modePaiementCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label col-form-label-sm" for="statutPaiementCommande">Statut du paiement</label>
                    <div class="col-sm-4">
	                    <input class="form-control form-control-sm" type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${commande.statutPaiement}"/>" size="30" maxlength="30" />
	                    <!-- <span class="erreur">${form.erreurs['statutPaiementCommande']}</span>-->
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label col-form-label-sm" for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control form-control-sm" type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${commande.modeLivraison}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['modeLivraisonCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label col-form-label-sm" for="statutLivraisonCommande">Statut de la livraison</label>
                    <div class="col-sm-4">
	                    <input class="form-control form-control-sm" type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${commande.statutLivraison}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['statutLivraisonCommande']}</span>
	                </div>
                </div>
                <p class="info">${ form.resultat }</p>
                <div>
	                <button class="btn btn-success" type="submit">Enregistrer</button>
	                <button class="btn btn-light" type="reset">Remettre à zéro</button>
                </div>
            </form>
        </div>
		<script>
			function showHideClients() {
				var choix = document.getElementById("selectionClient");
				var divSelect = document.getElementById("listeClients")
				var listeClients = document.getElementById("nouveauClient");
				if ( choix.checked == true) {
					divSelect.style.display = "block";
					listeClients.style.display = "none";
				} else {
					divSelect.style.display = "none";
					listeClients.style.display = "block";
				}
			}
		</script>
    </body>
</html>