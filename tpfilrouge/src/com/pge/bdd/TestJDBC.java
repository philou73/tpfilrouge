package com.pge.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TestJDBC {
    /* La liste qui contiendra le retour de la requête */
    private List<String> messages    = new ArrayList<String>();
    String               url         = "jdbc:mysql://localhost:3306/bdd_sdzee?serverTimezone=Europe/Paris";
    String               utilisateur = "java";
    String               motDePasse  = "&Java01";

    public List<String> executerTests( HttpServletRequest request ) {
        /* Ici nous placerons le code de nos manipulations */
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        // On charge le driver (une seule fois ici)
        try {
            messages.add( "Chargement du driver" );
            Class.forName( "com.mysql.jdbc.Driver" );
            messages.add( "Driver chargé" );
        } catch ( ClassNotFoundException e ) {
            // Il faut traiter l'erreur
            messages.add( "Problème de chargement du driver... : <br />" + e.getMessage() );
        }

        // On tente la connexion
        try {
            messages.add( "Connexion à la base de données" );
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            messages.add( "Connexion effectuée !" );

            // On place ici les requêtes à la Base de données
            // Création de l'objet statement qui gère les requêtes

            statement = connexion.createStatement();
            messages.add( "Objet requête créé !" );

            /*
             * Exécution d'une requête d'écriture avec renvoi de l'id
             * auto-généré
             */
            /* Récupération des paramètres d'URL saisis par l'utilisateur */
            String paramEmail = request.getParameter( "email" );
            String paramMotDePasse = request.getParameter( "motdepasse" );
            String paramNom = request.getParameter( "nom" );

            int statut = statement.executeUpdate(
                    "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES ('" + paramEmail
                            + "', MD5('" + paramMotDePasse + "'), '" + paramNom + "', NOW());",
                    Statement.RETURN_GENERATED_KEYS );

            /* Formatage pour affichage dans la JSP finale. */
            messages.add( "Résultat de la requête d'insertion : " + statut + "." );

            /* Récupération de l'id auto-généré par la requête d'insertion. */
            resultat = statement.getGeneratedKeys();
            /*
             * Parcours du ResultSet et formatage pour affichage de la valeur
             * qu'il contient dans la JSP finale.
             */
            while ( resultat.next() ) {
                messages.add( "ID retourné lors de la requête d'insertion :" + resultat.getInt( 1 ) );
            }

            // Appel d'un select
            resultat = statement.executeQuery( "SELECT id, email, mot_de_passe, nom FROM utilisateur;" );
            messages.add( "Requête exécutée" );

            /*
             * Récupération du résultat de la requête
             */
            while ( resultat.next() ) {
                int idUtilisateur = resultat.getInt( "id" );
                String emailUtilisateur = resultat.getString( "email" );
                String motDePasseUtilisateur = resultat.getString( "mot_de_passe" );
                String nomUtilisateur = resultat.getString( "nom" );

                /*
                 * Maintenant, on va créer une nouvelle occurence dans messages
                 */
                messages.add( "Données récupérées : id = " + idUtilisateur + ", email = " + emailUtilisateur
                        + ", mot de passe = " + motDePasseUtilisateur + ", nom = " + nomUtilisateur + "." );
            }

        } catch ( SQLException e ) {
            // Gérer les erreurs ici
            messages.add( "Erreur SQL rencontrée : <br />" + e.getMessage() );
        } finally {
            if ( resultat != null ) {
                try {
                    resultat.close();
                } catch ( SQLException ignore ) {

                }
            }
            if ( statement != null ) {
                try {
                    statement.close();
                } catch ( SQLException ignore ) {

                }
            }
            if ( connexion != null ) {
                try {
                    // Fermeture de la connexion
                    connexion.close();
                } catch ( SQLException ignore ) {
                    //
                }
            }
        }
        return messages;
    }

}
