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
                <h3>Informations client</h3>
           	    <c:import url="/inc/inc_client_form.jsp" />
                
                <h3>Informations commande</h3>
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="dateCommande">Date <span class="requis">*</span></label>
                    <div class="col-sm-4">
						<input class="form-control" type="text" id="dateCommande" name="dateCommande" value="<c:out value="${commande.date}"/>" size="30" maxlength="30" disabled />
						<span class="erreur">${form.erreurs['dateCommande']}</span>
					</div>
				</div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label" for="montantCommande">Montant <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control" type="text" id="montantCommande" name="montantCommande" value="<c:out value="${commande.montant}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['montantCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label" for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control" type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${commande.modePaiement}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['modePaiementCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label" for="statutPaiementCommande">Statut du paiement</label>
                    <div class="col-sm-4">
	                    <input class="form-control" type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${commande.statutPaiement}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['statutPaiementCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label" for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <div class="col-sm-4">
	                    <input class="form-control" type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${commande.modeLivraison}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['modeLivraisonCommande']}</span>
	                </div>
                </div>
                    
				<div class="form-group row">
                    <label class="col-sm-3 col-form-label" for="statutLivraisonCommande">Statut de la livraison</label>
                    <div class="col-sm-4">
	                    <input class="form-control" type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${commande.statutLivraison}"/>" size="30" maxlength="30" />
	                    <span class="erreur">${form.erreurs['statutLivraisonCommande']}</span>
	                </div>
                </div>
                <p class="info">${ form.resultat }</p>
                <div class="navbar">
	                <button class="btn btn-success" type="submit">Enregistrer</button>
	                <button class="btn btn-light" type="reset">Remettre à zéro</button>
                </div>
            </form>
        </div>
    </body>
</html>