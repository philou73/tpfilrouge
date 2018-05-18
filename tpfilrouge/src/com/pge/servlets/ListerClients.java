package com.pge.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pge.beans.Client;

public class ListerClients extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE = "/WEB-INF/listerClients.jsp";
	private static final String ATT_LISTE_CLIENTS = "listeClients";
	private static final String SESSION_CLIENTS = "listeClients";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
		// On récupère l'objet session
		HttpSession session = request.getSession();
		
		// On récupère la liste des clients en session
		Map<String,Client> listeClients = (Map<String,Client>) session.getAttribute(SESSION_CLIENTS);
		
		// On transmet la liste des clients à la vue par la requete
		request.setAttribute(ATT_LISTE_CLIENTS, listeClients);
		
		// On forward la request vers la vue listerClients.jsp
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
