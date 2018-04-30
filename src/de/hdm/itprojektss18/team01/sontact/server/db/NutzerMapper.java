package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * * Die Mapper-Klasse stellt Methoden zur Verfügung die
 * <code>Profil</code>-Objekte auf eine relationale Datenbank abbildet. Die
 * Methoden bieten die Möglichkeit Objekte aus der Datenbank zu suchen, sie zu
 * erzeugen und zu löschen. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * Diese Mapper-Klasse besitzt Singleton-Eigenschaften und wird nur einmal
 * mithilfe der Methode <code>profilMapper()</code> initialisiert. Der
 * Konstruktor ist bewusst durch <code>protected</code> geschützt, damit nur
 * eine einzige Instanz der Klasse exisitert.
 * 
 * @author Thies
 *
 */
public class NutzerMapper {
	
	private static NutzerMapper nutzerMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new" 
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerMapper(){	
	};
	
	/**
	 * Prüfung ob diese Klasse schon existiert.
	 * Und Methoden dieser Klasse sollen nur über diese statische Methode aufgerufen werden
	 * @return profilMapper
	 * @see profilMapper
	 */
	public static NutzerMapper nutzerMapper(){
		if (nutzerMapper == null){
			nutzerMapper = new NutzerMapper();
		}
		
		return nutzerMapper;
	}
	
	 /**
     * Einfuegen eines <code>Profil</code>-Objekts in die Datenbank. Dabei wird
     * auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
     * berichtigt.
     *
     * @param pro das zu speichernde Objekt
     * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
     * <code>id</code>.
     * 
     * @author thies
     */
	 public Nutzer insert(Nutzer n){
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
			              + "FROM merkzettel ");
			     	
			      if(rs.next()){
			    	  	/**
						 * Varaible merk erhält den höchsten Primärschlüssel inkrementiert um 1
						 */
			    	  	n.setId(rs.getInt("maxid") + 1);	    	  	
			    	  	/**
			    	  	 * Durchführen der Einfüge Operation via Prepared Statement
			    	  	 */
			    	  		PreparedStatement stmt1 = con.prepareStatement(
			    	  				"INSERT INTO nutzer (, , ) "
			    	  				+ "VALUES (?,?,?) ",
			    	  				Statement.RETURN_GENERATED_KEYS);
			    	  				stmt1.setInt(1, n.getId());
			    	 
	    	  				
			    	  				stmt1.executeUpdate();
			      }
			}
			catch(SQLException e2){
				e2.printStackTrace();
			}
			return n;
			
		}

}
	