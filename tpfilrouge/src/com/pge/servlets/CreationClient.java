package com.pge.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Client;
import com.pge.forms.CreationClientForm;

@SuppressWarnings( "serial" )
public class CreationClient extends HttpServlet {
    /**
     * 
     */
    public static final String CHEMIN               = "chemin";
    public static final String ATT_CLIENT           = "client";
    public static final String ATT_FORM             = "form";
    public static final String SESSION_LIST_CLIENTS = "listeClients";

    public static final String VUE_SUCCES           = "/WEB-INF/afficherClient.jsp";
    public static final String VUE_FORM             = "/WEB-INF/creerClient.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /*
         * Lecture du paramètre chemin passé à la servlet lors de
         * l'initialisation
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        CreationClientForm form = new CreationClientForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Client client = form.creerClient( request, chemin );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
            /*
             * Si aucune erreur, alors : - enregistrement du client en session -
             * affichage de la fiche récapitulative
             */
            HttpSession session = request.getSession();
            @SuppressWarnings( "unchecked" )
            Map<String, Client> listeClients = (Map<String, Client>) session.getAttribute( SESSION_LIST_CLIENTS );
            /*
             * Si aucune Map n'existe, alors initialisation d'une nouvelle map
             */
            if ( listeClients == null ) {
                listeClients = new HashMap<String, Client>();
            }
            /* Ajout du client courant dans la Map */
            listeClients.put( client.getNom(), client );

            /* Enfin, ré-enregistrement de la map en session */
            session.setAttribute( SESSION_LIST_CLIENTS, listeClients );

            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}