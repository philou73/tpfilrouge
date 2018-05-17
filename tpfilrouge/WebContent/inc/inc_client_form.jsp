<%@ page pageEncoding="UTF-8" %>
<div class="form-group row">
	<label class="col-sm-3 col-form-label" for="nomClient">Nom <span class="requis">*</span></label>
	<div class="col-sm-8">
		<input type="text" placeholder="${form.erreurs['nomClient']}" class="form-control" name="nomClient" id="nomClient" value="<c:out value="${empty form.erreurs['nomClient'] ? client.nom : ''}"/>" maxlength="30">
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-3 col-form-label" for="prenomClient">Prénom</label>
	<div class="col-sm-8">
		<input type="text" placeholder="${form.erreurs['prenomClient']}" class="form-control" name="prenomClient" id="prenomClient" value="<c:out value="${empty form.erreurs['prenomClient'] ? client.prenom : ''}"/>" maxlength="30">
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-3 col-form-label" for="adresseClient">Adresse de livraison<span class="requis">*</span></label>
	<div class="col-sm-8">
		<input type="text" placeholder="${form.erreurs['adresseClient']}" class="form-control" name="adresseClient" id="nomClient" value="<c:out value="${empty form.erreurs['adresseClient'] ? client.adresse : ''}"/>" maxlength="100">
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-3 col-form-label" for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
	<div class="col-sm-8">
		<input type="text" placeholder="${form.erreurs['telephoneClient']}" class="form-control" id="telephoneClient" name="telephoneClient" value="<c:out value="${empty form.erreurs['telephoneClient'] ? client.telephone : ''}"/>" size="30" maxlength="15" />
	</div>
</div>

<div class="form-group row">
	<label class="col-sm-3 col-form-label" for="emailClient">Adresse email</label>
	<div class="col-sm-8">
		<input type="email" placeholder="${form.erreurs['emailClient']}" class="form-control" id="emailClient" name="emailClient" value="<c:out value="${empty form.erreurs['emailClient'] ? client.email : ''}"/>" size="30" maxlength="60" />
	</div>
</div>