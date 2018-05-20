package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;



/**
 * Klasse für den Aufbau zur Datenbank mittels JDBC.
 * 
 * @author Dennis Lehle
 */
public class DBConnection{

	private static Connection con = null;
	private static String googleUrl = "jdbc:google:mysql://NameDeployAppEngine:europe-west:LinkDeploy?user=root&password=1234";
	private static String localUrl = "jdbc:mysql://localhost:3306/itproj?user=root&password=";

	/**
      * Diese Methode gibt die aufgebaute DB-Verbindung zurück
      * @return con
      */
    public static Connection connection(){

    		/**
    		 *  Wenn es bisher keine Connection zur DB gab, ...
    		 */
        if (con == null) {
            String url = null;
            try {
            
            	if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {       
            		/**
                	 * Load the class that provides the new "jdbc:google:mysql://" prefix.
                	 */
                	Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;

                } else {
                	
                	/**
                 * Local MySQL instance to use during development.
                 */
                 Class.forName("com.mysql.jdbc.Driver");
                 url = localUrl;   
                 }
            	
                /**
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
            	con = DriverManager.getConnection(url);     
            	
           /**
            * con = (Connection) DriverManager.getConnection(googleUrl);
            */
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }

        /**
         * Zurückgegeben der Verbindung
         */
        return con;
    }
}