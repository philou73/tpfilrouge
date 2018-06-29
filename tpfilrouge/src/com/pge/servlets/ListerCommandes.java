package com.pge.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Commande;

public class ListerCommandes extends HttpServlet {
    /**
     * 
     */
    private static final long   serialVersionUID    = 1L;
    private static final String SESSION_COMMANDES   = "listeCommandes";
    private static final String ATT_LISTE_COMMANDES = "listeCommandes";
    private static final String VUE                 = "/WEB-INF/listerCommandes.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // On transmet à la vue la liste des commandes récupérées en session
        HttpSession session = request.getSession();
        @SuppressWarnings( "unchecked" )
        Map<String, Commande> listeCommandes = (Map<String, Commande>) session.getAttribute( SESSION_COMMANDES );
        request.setAttribute( ATT_LISTE_COMMANDES, listeCommandes );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
