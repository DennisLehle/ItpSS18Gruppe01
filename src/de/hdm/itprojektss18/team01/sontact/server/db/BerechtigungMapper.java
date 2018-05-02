package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.cloud.sql.jdbc.ResultSet;
import com.google.cloud.sql.jdbc.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;

/** 
 * *Die Mapper-Klasse <code>Berechtigung</code> gehört der Datenbankschicht
 *  an und stellt eine objektorientierten Sicht der Applikationslogik auf 
 *  die relationale Datenbank dar. Die Mapperklasse verfügt über Methoden 
 *  die es ermöglichen Objekte zu erstellen, bearbeiten, suchen oder zu 
 *  löschen (insert, update, search, delete). Zusätzlich können aus 
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
	 * Geschuetzer Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BerechtigungMapper () {
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BerechtigungMapper.berechtigungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
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
	 * @param objekt, das ausgewählte BusinessObjekt, welches einem Nutzer zugewiesen wird. 
	 * @return Nutzer-Objekt, der eine Berechtigung auf ein Objekt erhält
	 */
	
	public Berechtigung insertBerechtigung(Berechtigung b) {
		/**
		 * Herstellung einer Datenbankverbindung.
		 */
		Connection con = DBConnection.connection();
		
		try {
			/**
			 * Anlegen eines leeren SQL-Statements, mittels der Java-Datenbankkonektivitaet 
			 * (JDBC), welches eine Anwendungsprogrammierschnittstelle, die es ermöglicht 
			 * eine Verbindung zur Datenbank herzustellen.
			 */
			Statement stmt = (Statement) con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugefügten Primärschlüssels. Hierbei wird die
			 * aktuelle id um eins erhoeht (+1), um dann das Statement ausfuellen 
			 * und als Query an die Datenbank zu senden.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX (id) AS maxid FROM berechtigung");
			
			if(rs.next()) {
				b.setId(rs.getInt("maxid") + 1);
			}
			stmt = (Statement) con.createStatement();
			
			/**
			 * SQL-Anweisung zum Einfuegen des neuen Berechtigung-Tupels in die Datenbank.
			 */
			stmt.executeUpdate("INSERT INTO b(id, senderId, userId, objectId, objectType)"
					+ "VALUES ("+b.getId() + ",'"+b.getSenderId() + "', "+b.getObjectId() 
					+ "', +" +b.getObjectType() + ")");
			
		/**
		 * Bei einem Aufruf des <code>printStackTrace</code> wird gewährleistet, dass
		 * Fehler konkreter analysiert werden. Dies wird durch die Funktion unterstützt,
		 * die Informationen über den genauen Fehlerstandort und der Herkunft vermittelt. 
		 */
			
		} catch (SQLException e2) {
		e2.printStackTrace();
		}
		return b;
		}
}
	
	
