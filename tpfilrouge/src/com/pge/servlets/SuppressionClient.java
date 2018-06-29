package com.pge.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Client;
import com.pge.beans.Commande;

public class SuppressionClient extends HttpServlet {
    /**
     * 
     */
    private static final long   serialVersionUID  = 1L;
    private final static String SESSION_CLIENTS   = "listeClients";
    private final static String SESSION_COMMANDES = "listeCommandes";
    private final static String ATT_SUPPRESSION   = "suppression";
    private final static String VUE_LISTE         = "/listerClients";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String client = request.getParameter( ATT_SUPPRESSION );

        @SuppressWarnings( "unchecked" )
        Map<String, Client> listeClients = (Map<String, Client>) session.getAttribute( SESSION_CLIENTS );
        if ( listeClients.get( client ) != null ) {
            listeClients.remove( client );
        }

        // Suppression des commandes passées par les clients
        @SuppressWarnings( "unchecked" )
        Map<String, Commande> listeCommandes = (Map<String, Commande>) session.getAttribute( SESSION_COMMANDES );
        Map<String, Commande> newListeCommandes = new HashMap<String, Commande>();
        Set<String> keySet = listeCommandes.keySet();

        // listeCommandes.elements();
        for ( String key : keySet ) {
            Commande commande = listeCommandes.get( key );

            if ( !commande.getClient().getNom().equals( client ) ) {
                newListeCommandes.put( commande.getDate(), commande );
            }
        }
        // On replace la liste des clients en session
        session.setAttribute( SESSION_CLIENTS, listeClients );

        // On replace la liste des commandes en session
        session.setAttribute( SESSION_COMMANDES, newListeCommandes );

        // On affiche la vue listerClients.jsp en redirection
        response.sendRedirect( request.getContextPath() + VUE_LISTE );
    }
}
