package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.cloud.sql.jdbc.ResultSet;
import com.google.cloud.sql.jdbc.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;

/** 
 * *Die Mapper-Klasse <code>Berechtigung</code> geh�rt der Datenbankschicht
 *  an und stellt eine objektorientierten Sicht der Applikationslogik auf 
 *  die relationale Datenbank dar. Die Mapperklasse verf�gt �ber Methoden 
 *  die es erm�glichen Objekte zu erstellen, bearbeiten, suchen oder zu 
 *  l�schen (insert, update, search, delete). Zus�tzlich k�nnen aus 
 *  Datenbank-Tupel Java-Instanzen erzeugt werden und umgekehrt 
 *  --> bidirektional.
 * 
 * @author Miescha
 *
 */

public class BerechtigungMapper {
	
/**
 * Die Klasse BerechtigungMapper wird nur einmalig instanziiert, weshalb 
 * von der <b>Singleton</b>-Eigenschaft gesprochen wird. Ein Singleton, gilt als 
 * Entwurfsmuster uns stellt sicher, dass von einer Klasse genau ein Objekt 
 * existiert. 
 * <p>
 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer 
 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert
 * die einzige Instanz dieser Klasse.
 * 
 * @see berechtigungMapper()
 * 
 *  @author Thies
 * 
 */
	private static BerechtigungMapper berechtigungMapper = null;
	
	/**
	 * Geschuetzer Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BerechtigungMapper () {
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BerechtigungMapper.berechtigungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine einzige
	 * Instanz von <code>BerechtigungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BerechtigungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	 * 
	 * @return DAS <code>BerechtigungMapper</code>-Objekt.
	 * @see BerechtigungMapper
	 */
	public static BerechtigungMapper berechtigungMapper() {
		if (berechtigungMapper == null) {
			berechtigungMapper = new BerechtigungMapper();
		}
		return berechtigungMapper;
	}
	
	/**
	 * Ein neues Berechtigung-Objekt der Datenbank hinzufuegen.
	 * 
	 * @param berechtigung, mit einem zugeteilten <code>enum</code>, dieser die 
	 * 		  berechtigungsstufe aufweist    
	 * @param sender, der Nutzer als Sender
	 * @param nutzer, der Nutzer als Empfaenger  
	 * @return b, hinzugef�gtes Berechtigung-Objekt.
	 */
	
	public Berechtigung insertBerechtigung(Berechtigung b) {
		/**
		 * Herstellung einer Datenbankverbindung.
		 */
		Connection con = DBConnection.connection();
		
		try {
			/**
			 * Anlegen eines leeren SQL-Statements, mittels der Java-Datenbankkonnektivitaet 
			 * (JDBC), welches eine Anwendungsprogrammierschnittstelle, die es erm�glicht 
			 * eine Verbindung zur Datenbank herzustellen.
			 */
			java.sql.Statement stmt = con.createStatement();
			java.sql.Statement stmtSender =  con.createStatement();
			java.sql.Statement stmtUser =  con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels. Hierbei wird die
			 * aktuelle id um eins erhoeht (+1), um dann das Statement ausfuellen 
			 * und als Query an die Datenbank zu senden.
			 */
			java.sql.ResultSet rs = stmt.executeQuery("SELECT MAX (id) AS maxid FROM berechtigung");
			
			if(rs.next()) {
				b.setId(rs.getInt("maxid") + 1);
			}
			stmt = (Statement) con.createStatement();
			stmtSender =  con.createStatement();
			stmtUser = con.createStatement();
						
			/**
			 * SQL-Anweisung zum Einfuegen des neuen Berechtigung-Tupels in die Datenbank.
			 */
			stmt.executeUpdate("INSERT INTO berechtigung(id, senderId, userId, objectId, objectType, berechtigungsstufe)"
					+ "VALUES ("+b.getId() + ",'"+b.getSenderId() + "', "+b.getObjectId() 
					+ "', +" +b.getObjectType() + "', " +b.getBerechtigungsstufe() + ")");
			
			stmtSender.executeUpdate("INSERT INTO sender_berechtigungsobjekt(id, senderId, objectId, objectType, berechtigungsstufe)"
					+ "VALUES ("+b.getId() + ",'"+b.getSenderId() + "', "+b.getObjectId() 
					+ "', +" +b.getObjectType() + "', " +b.getBerechtigungsstufe() + ")");
			
			stmtUser.executeUpdate("INSERT INTO user_berechtigungsobjekt(id, userId, objectId, objectType, berechtigungsstufe)"
					+ "VALUES ("+b.getId() + ",'"+b.getSenderId() + "', "+b.getObjectId() 
					+ "', +" +b.getObjectType() + "', " +b.getBerechtigungsstufe() + ")");
			
		/**
		 * Bei einem Aufruf des <code>printStackTrace</code> wird gew�hrleistet, dass
		 * Fehler konkreter analysiert werden. Dies wird durch die Funktion unterst�tzt,
		 * die Informationen �ber den genauen Fehlerstandort und der Herkunft vermittelt. 
		 */
			
		} catch (SQLException e2) {
		e2.printStackTrace();
		}
		return b;
		}
	/**
	 * Aktualisiert ein Berechtigung-Objekt in der Datenbank. 
	 * 
	 * @param berechtigung
	 * @return b 
	 */
	public Berechtigung saveBerechtigung(Berechtigung b) {
		
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			/**
			 * Benoetigte SQL-Anweisung, die den Datensatz des uebergebenen Objekts 
			 * in der Datenbank aktualisiert. 
			 */
			stmt.executeUpdate("UPDATE berechtigung SET berechtigungsstufe= " 
			 + b.getBerechtigungsstufe() + ", senderId= '" + b.getSenderId()
			 + ", userId= '" + b.getUserId() + ", objectId= '" + b.getObjectId() 
			 + "WHERE id= " + b.getId());
			
			/**
			 * Bei einem Aufruf des <code>printStackTrace</code> wird gew�hrleistet, dass
			 * Fehler konkreter analysiert werden. Dies wird durch die Funktion unterst�tzt,
			 * die Informationen �ber den genauen Fehlerstandort und der Herkunft vermittelt. 
			 */
				
			} catch (SQLException e2) {
			e2.printStackTrace();
			}
			return b;
			}
			
	}

	
	
