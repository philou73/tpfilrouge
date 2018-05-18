package com.pge.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Commande;
import com.pge.beans.Client;
import com.pge.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {
    /**
     * 
     */
    private static final long  serialVersionUID = 1526257567954287841L;
    public static final String ATT_COMMANDE     = "commande";
    public static final String ATT_FORM         = "form";
    public static final String ATT_CLIENTS		= "listeClients";
    private static final String SESSION_CLIENT	= "listeClients";
    private static final String SESSION_COMMANDE = "listeCommandes";

    public static final String VUE_SUCCES       = "/WEB-INF/afficherCommande.jsp";
    public static final String VUE_FORM         = "/WEB-INF/creerCommande.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
    	// TP fil rouge partie 4 : on envoie la liste des clients pour les afficher dans un select et pouvoir en sélectionner un
    	HttpSession session = request.getSession();
    	
    	// On récupère la liste des clients présente en session et on la passe en attribut de la requête
    	Map<String, Client> listeClients = (Map<String, Client>) session.getAttribute(SESSION_CLIENT);
		request.setAttribute(ATT_CLIENTS, listeClients);
		
		// Forward vers la vue de création d'une commande
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        CreationCommandeForm form = new CreationCommandeForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Commande commande = form.creerCommande( request );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_COMMANDE, commande );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
        	// On stocke le client en session
        	HttpSession session = request.getSession();
        	Map<String, Client> listeClients = (Map<String,Client>)session.getAttribute(SESSION_CLIENT);
        	if ( listeClients == null ) {
        		listeClients = new HashMap<String, Client>();
        	}
        	listeClients.put(commande.getClient().getNom(), commande.getClient());
        	session.setAttribute(SESSION_CLIENT, listeClients);
        	
        	// On stocke la commande en session
        	Map<String, Commande> listeCommandes = (Map<String, Commande>) session.getAttribute(SESSION_COMMANDE);
        	if (listeCommandes == null) {
        		listeCommandes = new HashMap<String, Commande>();
        	}
        	listeCommandes.put(commande.getDate(), commande);
        	session.setAttribute(SESSION_COMMANDE, listeCommandes);

        	// On stocke également dans la requête la liste des clients pour 
            /* Si aucune erreur, alors affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}