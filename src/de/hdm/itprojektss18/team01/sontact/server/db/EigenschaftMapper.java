package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;

/**
 * <code>EigenschaftMapper</code>, welcher <code>Eigenschaft</code>-Objekte auf
 * der Datenbank abbildet. Für weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Yakup
 *
 */
public class EigenschaftMapper {

	public static EigenschaftMapper eigenschaftMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <new> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected EigenschaftMapper() {

	}

	/**
	 * Pruefung ob diese Klasse schon existiert. Und Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 * 
	 */
	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
	}

	/**
	 * Hinzufügen einer neuen Eigenschaft in die Datenbank. Der Nutzer erhaelt die
	 * Funktion um selbst Eigenschaften definieren zu koennen.
	 * 
	 * @param e
	 * @return e
	 */
	public Eigenschaft insert(Eigenschaft e) {

		// Aufbau der DB-Verbindung
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM eigenschaft ");

			if (rs.next()) {

				// Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
				e.setId(rs.getInt("maxid") + 1);

				// SQL-Anweisung zum Einfuegen des Tupels in die DB
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO eigenschaft(id, bezeichnung)" + "VALUES(?, ?) ",

						Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, e.getId());
				stmt1.setString(2, e.getBezeichnung());

				System.out.println(stmt);

				// Ausfuehren des SQL-Statements
				stmt1.executeUpdate();
			}
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return e;
	}

	/**
	 * Aktualisieren eines Eigenschafts-Objekt
	 * 
	 * @param e
	 * @return e
	 */
	public Eigenschaft update(Eigenschaft e) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des Tupels in die DB
		String updateSQL = "UPDATE eigenschaft SET bezeichnung=? WHERE id=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, e.getBezeichnung());
			stmt.setInt(2, e.getId());

			// Ausfuehren des SQL-Statements
			stmt.executeUpdate();

			System.out.println("Updated");

		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}

		return e;
	}

	/**
	 * Loeschen eines Eigenschaft-Objekts aus der Datenbank.
	 * 
	 * @param e
	 */

	public void delete(Eigenschaft e) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Loeschen des Tupels in die DB
		String deleteSQL = "DELETE FROM eigenschaft WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, e.getId());

			// Ausfuehren des SQL-Statements
			stmt.executeUpdate();
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Auslesen einer Eigenschaft anhand der uebergebenen id.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */

	public Eigenschaft findEigenschaftById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Loeschen des Tupels in die DB
		String selectByKey = "SELECT * FROM eigenschaft WHERE id=? ORDER BY id";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));

				return e;
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
	 * Auslesen aller Eigenschaften welche beim Anlegen einer neuen Auspraegung
	 * eines Kontakt-Objekts zur Verfuegung stehen.
	 * 
	 * @return Eigenschaften
	 * @throws SQLException
	 */

	public Vector<Eigenschaft> findEigenschaftAuswahl() {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Auslesen des Tupels aus der DB
		String selectByAuswahl = "SELECT * FROM eigenschaft WHERE id BETWEEN 1 AND 19";

		// Vector erzeugen, der die Eigenschaftsdatensaetze aufnehmen kann
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);

			ResultSet rs = stmt.executeQuery();

			// Schleife die Zeile pro Zeile durchlaeuft
			while (rs.next()) {

				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(e);
			}
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	// findEigenschaftForAuspraegung() evlt. in AuspraegungMapper uebernehmen.

	/**
	 * Gibt die Eigenschaft zur einer Auspraegung eines Kontaktes zurueck.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */

	public Eigenschaft findEigenschaftForAuspraegung(int eigenschaftId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Auslesen des Tupels aus der DB
		String selectByAuswahl = "SELECT * FROM eigenschaft WHERE id = ?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			stmt.setInt(1, eigenschaftId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();

				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));
				return e;
			}
		}

		catch (SQLException e2) {
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Suche nach der bezeichnung einer Eigenschaft fuer die Ausgabe der Eigenschaft
	 * innerhalb des Reports
	 * 
	 * @param wert
	 * @return
	 */

	public Vector<Eigenschaft> findEigenschaftByBezeichnung(String bezeichnung) {

		Connection con = null;
		PreparedStatement stmt = null;

		Vector<Eigenschaft> result = new Vector<Eigenschaft>();
		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// SQL-Anweisung zum Auslesen des Tupels aus der DB
			stmt = con.prepareStatement(
					"SELECT * FROM eigenschaft" + "JOIN auspraegung ON eigenschaft.id = auspraegung.eigenschaftid "
							+ "JOIN kontakt ON auspraegung.kontaktid = kontakt.id" + "WHERE bezeichnung like '%"
							+ bezeichnung + "%'");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(e);

			}
			return result;
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Eine Eigenschaft anhand der Bezeichnung auslesen
	 * 
	 * @param Bezeichnung
	 *            der Eigenschaft
	 * @return
	 */
	public Eigenschaft findEigenschaft(String bezeichnung) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Auslesen des Tupels aus der DB
		String selectByAuswahl = "SELECT * FROM eigenschaft WHERE bezeichnung=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			stmt.setString(1, bezeichnung);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));
				return e;
			}
		}

		catch (SQLException e2) {
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return null;
	}

}
