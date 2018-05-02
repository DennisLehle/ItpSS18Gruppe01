package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;


/**
 * * Die Mapper-Klasse stellt Methoden zur Verfügung die
 * <code>Kontaktlisten</code>-Objekte auf eine relationale Datenbank abbildet. Die
 * Methoden bieten die Möglichkeit Objekte aus der Datenbank zu suchen, sie zu
 * erzeugen und zu löschen. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * Diese Mapper-Klasse besitzt Singleton-Eigenschaften und wird nur einmal
 * mithilfe der Methode <code>kontaktlistenMapper()</code> initialisiert. Der
 * Konstruktor ist bewusst durch <code>protected</code> geschützt, damit nur
 * eine einzige Instanz der Klasse exisitert.
 * 
 * @author Ugur
 *
 */
public class KontaktlistenMapper {
	
private static KontaktlistenMapper kontaktlistenMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new" 
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected KontaktlistenMapper(){	
	};
	
	/**
	 * Prüfung ob diese Klasse schon existiert.
	 * Und Methoden dieser Klasse sollen nur über diese statische Methode aufgerufen werden
	 * @return kontaktlistenMapper
	 * @see kontaktlistenMapper
	 */
	public static KontaktlistenMapper kontaktlistenMapper(){
		if (kontaktlistenMapper == null){
			kontaktlistenMapper = new KontaktlistenMapper();
		}
		
		return kontaktlistenMapper;
	}
	
	 /**
     * Einfuegen eines <code>Kontaktlisten</code>-Objekts in die Datenbank. Dabei wird
     * auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
     * berichtigt.
     *
     * @param pro das zu speichernde Objekt
     * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
     * <code>id</code>.
     */
	 public Kontaktliste insert(Kontaktliste kl){
			/**
			 * Aufbau der DB Connection
			 */
			Connection con = DBConnection.connection();
			/**
			 * Try und Catch gehören zum Exception Handling 
			 * Try = Versuch erst dies 
			 * Catch = Wenn Try nicht geht versuch es so ..
			 */
			try {
			      Statement stmt = con.createStatement();
			      	/**
					 * Was ist der momentan höchste Primärschlüssel
					 */
			      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
			              + "FROM kontaktliste ");
			     	
			      if(rs.next()){
			    	  	/**
						 * Varaible merk erhält den höchsten Primärschlüssel inkrementiert um 1
						 */
			    	  	kl.setId(rs.getInt("maxid") + 1);	    	  	
			    	  	/**
			    	  	 * Durchführen der Einfüge Operation via Prepared Statement
			    	  	 */
			    	  		PreparedStatement stmt1 = con.prepareStatement(
			    	  				"INSERT INTO kontaktliste (, , ) "
			    	  				+ "VALUES (?,?,?) ",
			    	  				Statement.RETURN_GENERATED_KEYS);
			    	  				stmt1.setInt(1, kl.getId());
			    	 
	    	  				
			    	  				stmt1.executeUpdate();
			      }
			}
			catch(SQLException e2){
				e2.printStackTrace();
			}
			return kl;
			
		}

}
