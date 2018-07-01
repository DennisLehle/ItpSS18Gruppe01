package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;

/**
 * Die Klasse <code>BerechtigungMapper</code> bildet auf der Datenbank alle
 * Berechtigungen eines Nutzers zu den eigenen oder geteilten Objekten ab. Fuer
 * weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Miescha
 */

public class BerechtigungMapper {

	private static BerechtigungMapper berechtigungMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <new> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected BerechtigungMapper() {
	}

	/**
	 * Pruefung ob diese Klasse schon existiert. Und Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 */
	public static BerechtigungMapper berechtigungMapper() {
		if (berechtigungMapper == null) {
			berechtigungMapper = new BerechtigungMapper();
		}
		return berechtigungMapper;
	}

	/**
	 * Einfuegen eines <code>Berechtigung</code>-Objekts in die Datenbank. Dabei
	 * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	 * angepasst.
	 *
	 * @param b
	 * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>
	 */
	public Berechtigung insert(Berechtigung b) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM berechtigung";

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String insertSQL = "INSERT INTO berechtigung (id, ownerid, receiverid, objectid, type) VALUES (?,?,?,?,?)";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
			if (rs.next()) {
				b.setId(rs.getInt("maxid") + 1);
			}

			// Jetzt erfolgt das einspeichern des Objekts
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? als Platzhalter fuer den Wert
			stmt.setInt(1, b.getId());
			stmt.setInt(2, b.getOwnerId());
			stmt.setInt(3, b.getReceiverId());
			stmt.setInt(4, b.getObjectId());
			stmt.setString(5, String.valueOf(b.getType()));

			// INSERT-Query ausfuehren
			stmt.executeUpdate();

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return b;
	}

	/**
	 * Loeschen eines Berechtigung-Objekts aus der Datenbank.
	 * 
	 * @param b
	 */
	public void delete(Berechtigung b) {

		// Aufbau der DB-Verbindung
		Connection con = DBConnection.connection();

		try {
			// SQL-Anweisung zum auslesen des Berechtigungtupels aus der DB
			PreparedStatement prestmt = con.prepareStatement(" DELETE FROM berechtigung WHERE" + " ownerid = "
					+ b.getOwnerId() + " AND receiverid = " + b.getReceiverId() + " AND objectid = " + b.getObjectId()
					+ " AND type = '" + b.getType() + "'");

			// Ausfuehren des SQL Statement
			prestmt.execute();

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Berechtigung wird mithilfe der uebergebenen id aus der Datenbank
	 * ausgelesen und zurueckgegeben.
	 * 
	 * @param id
	 * @return Berechtigung
	 */
	public Berechtigung findById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Berechtigungtupels aus der DB
		String selectByKey = "SELECT * FROM berechtigung WHERE id=? ORDER BY id";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Berechtigung b = new Berechtigung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				return b;
			}
		}
		
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Findet bestimmte Berechtigung-Objekte aus der Datenbank.
	 * 
	 * @return Vector von Berechtigungen
	 */
	public Vector<Berechtigung> findAll() {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Berechtigungtupels aus der DB
		String selectAll = "SELECT * FROM berechtigung";

		// Vector erzeugen, der die Berechtigungsdatensaetze aufnehmen kann
		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectAll);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			// Schleife fuer das durchlaufen aller Zeilen
			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Berechtigung b = new Berechtigung();

				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Das Objekt wird dem Vektor hinzugefuegt
				result.addElement(b);
			}
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen ueber jene Objekte aus, welche vom Nutzer
	 * geteilt wurden.
	 * 
	 * @param nutzerId
	 * @return Vector von Berechtigungen
	 */
	public Vector<Berechtigung> findAllBerechtigungenByOwner(int nutzerId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Berechtigungtupels aus der DB
		String selectByKey = "SELECT *  FROM berechtigung WHERE ownerid=?";

		// Erstellung des Ergebnisvektors
		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Berechtigung b = new Berechtigung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		}

		catch (SQLException e2) {

			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen ueber jene Objekte aus, welche mit dem
	 * Nutzergeteilt wurden.
	 * 
	 * @param nutzerId
	 * @return Vector von Berechtigungen
	 */
	public Vector<Berechtigung> findAllBerechtigungenByReceiver(int nutzerId) {

		Connection con = null;
		PreparedStatement stmt = null;
		
		// Query fuer den Select
		String selectByKey = "SELECT * FROM berechtigung WHERE receiverid=?";

		// Erstellung des Ergebnisvektors
		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			
			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);

			// Ausfuehren des SQL Statements
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Berechtigung b = new Berechtigung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		}

		catch (SQLException e2) {
			
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return result;
	}
}
