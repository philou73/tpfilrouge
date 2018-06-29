package com.pge.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.pge.beans.Client;

import eu.medsea.mimeutil.MimeUtil;

public final class CreationClientForm {
    private static final String CHAMP_CHOIX         = "selectionClient";
    private static final String CHAMP_SELECT_CLIENT = "selectClient";
    private static final String CHAMP_NOM           = "nomClient";
    private static final String CHAMP_PRENOM        = "prenomClient";
    private static final String CHAMP_ADRESSE       = "adresseClient";
    private static final String CHAMP_TELEPHONE     = "telephoneClient";
    private static final String CHAMP_EMAIL         = "emailClient";
    private static final String SESSION_CLIENTS     = "listeClients";
    private static final String CHAMP_IMAGE         = "imageClient";

    private static final int    TAILLE_TAMPON       = 10240;                        // 10ko

    private String              resultat;
    private Map<String, String> erreurs             = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Client creerClient( HttpServletRequest request, String chemin ) {
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String adresse = getValeurChamp( request, CHAMP_ADRESSE );
        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String image = null;

        Client client = new Client();

        /*
         * On vérifie si l'utilisateur a sélectionné un client existant. Si oui,
         * on le charge, sinon, on gère la création d'un nouveau client
         */
        if ( request.getParameter( CHAMP_CHOIX ) != null ) {
            String selectClient = getValeurChamp( request, CHAMP_SELECT_CLIENT );

            // On doit récupérer le client en session
            HttpSession session = request.getSession();
            @SuppressWarnings( "unchecked" )
            Map<String, Client> listeClients = (Map<String, Client>) session.getAttribute( SESSION_CLIENTS );

            client = (Client) listeClients.get( selectClient );
        } else {
            // On gère la création d'un nouveau client
            try {
                validationNom( nom );
            } catch ( Exception e ) {
                setErreur( CHAMP_NOM, e.getMessage() );
            }
            client.setNom( nom );

            try {
                validationPrenom( prenom );
            } catch ( Exception e ) {
                setErreur( CHAMP_PRENOM, e.getMessage() );
            }
            client.setPrenom( prenom );

            try {
                validationAdresse( adresse );
            } catch ( Exception e ) {
                setErreur( CHAMP_ADRESSE, e.getMessage() );
            }
            client.setAdresse( adresse );

            try {
                validationTelephone( telephone );
            } catch ( Exception e ) {
                setErreur( CHAMP_TELEPHONE, e.getMessage() );
            }
            client.setTelephone( telephone );

            try {
                validationEmail( email );
            } catch ( Exception e ) {
                setErreur( CHAMP_EMAIL, e.getMessage() );
            }
            client.setEmail( email );

            try {
                image = validationImage( request, chemin );
            } catch ( FormValidationException e ) {
                setErreur( CHAMP_IMAGE, e.getMessage() );
            }
            client.setImage( image );

            if ( erreurs.isEmpty() ) {
                resultat = "Succès de la création du client.";
            } else {
                resultat = "Échec de la création du client.";
            }
        }

        return client;
    }

    private void validationNom( String nom ) throws Exception {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un nom d'utilisateur." );
        }
    }

    private void validationPrenom( String prenom ) throws Exception {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new Exception( "Le prénom d'utilisateur doit contenir au moins 2 caractères." );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null ) {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer une adresse de livraison." );
        }
    }

    private void validationTelephone( String telephone ) throws Exception {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Le numéro de téléphone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new Exception( "Le numéro de téléphone doit contenir au moins 4 chiffres." );
            }
        } else {
            throw new Exception( "Merci d'entrer un numéro de téléphone." );
        }
    }

    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    private String validationImage( HttpServletRequest request, String chemin ) throws FormValidationException {
        /*
         * Récupération du contenu du champ image du formulaire. Il faut ici
         * utiliser la méthode getPart()
         */
        String nomFichier = null;
        InputStream contenuFichier = null;
        try {
            Part part = request.getPart( CHAMP_IMAGE );
            nomFichier = getNomFichier( part );

            /*
             * Si la méthode getNomFichier() a renvoyé quelque chose, il s'agit
             * donc d'un champ de type fichier
             */
            if ( nomFichier != null && !nomFichier.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une raison
                 * mystique le chemin du fichier local à la machine du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne sélectionner que le nom et
                 * l'extension du fichier, et de se débarrasser du superflu.
                 */
                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

                /* Récupération du contenu du fichier */
                contenuFichier = part.getInputStream();

                /* Extraction du type MIME du fichier depuis l'InputStream */
                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

                /*
                 * Si le fichier est bien une image, alors son en-tête MIME
                 * commence par la chaîne "image"
                 */
                if ( mimeTypes.toString().startsWith( "image" ) ) {
                    /* Ecriture du fichier sur le disque */
                    ecrireFichier( contenuFichier, nomFichier, chemin );
                } else {
                    throw new FormValidationException( "Le fichier envoyé doit être une image." );
                }

            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception renvoyée si la taille dépasse la limite autorisée dans
             * la section <multipart-config> de la déclaration de la servlet
             * dans le fichier web.xml
             */
            e.printStackTrace();
            throw new FormValidationException( "Le fichier ne doit pas dépasser 1Mo." );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e.printStackTrace();
            throw new FormValidationException( "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data.
             */
            e.printStackTrace();
            throw new FormValidationException(
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );

        }

        return nomFichier;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

    /*
     * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
     * "content-disposition", et de vérifier si le paramètre "filename" y est
     * présent. Si oui, alors le champ traité est de type File et la méthode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la méthode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /*
         * Boucle sur chacun des paramètres de l'entête "content-disposition"
         */
        if ( part != null ) {
            for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
                /* Recherche de l'éventuelle présence du paramètre "filename" */
                if ( contentDisposition.trim().startsWith( "filename" ) ) {
                    /*
                     * Si filename est présent, alors renvoi de sa valeur,
                     * c'est-à-dire du nom du fichier sans guillemets
                     */
                    return contentDisposition.substring( contentDisposition.indexOf( "=" ) + 1 ).trim().replace( "\"",
                            "" );
                }
            }
        }
        /* Pour finir, si on n'a pas trouvé le paramètre "filename" */
        return null;
    }

    /*
     * Méthode utilitaire pour écrire un fichier passé en paramètre avec le
     * répertoire donné et le nom donné
     */
    private static void ecrireFichier( InputStream contenuFichier, String nomFichier, String chemin )
            throws FormValidationException {
        /* Préparation des flux */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouverture des fichiers */
            entree = new BufferedInputStream( contenuFichier, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ), TAILLE_TAMPON );
            /*
             * Lit le fichier reçu et écrit son contenu sur le disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            @SuppressWarnings( "unused" )
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon );
            }
        } catch ( Exception e ) {
            throw new FormValidationException( "Erreur lors de l'écriture du fichier sur le disque" );
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }
}