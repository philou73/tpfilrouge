package com.pge.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Commande;

public class SuppressionCommande extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PARAM_CLE_COMMANDE = "suppression";
	private static final String SESSION_COMMANDES = "listeCommandes";
	private static final String VUE_COMMANDES 	= "/listerCommandes";

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String cleCommande = request.getParameter(PARAM_CLE_COMMANDE);
		
		//Suppression de la commande 
		HttpSession session = request.getSession();
		Map<String, Commande> listeCommandes = (Map<String, Commande>)session.getAttribute(SESSION_COMMANDES); 
		listeCommandes.remove(cleCommande);
		
		//Remplacement de la liste des commandes en session
		session.setAttribute(SESSION_COMMANDES, listeCommandes);
		
		// Affichage de la vue liste des commandes, par redirection
		response.sendRedirect(request.getContextPath() + VUE_COMMANDES);
	}
}
