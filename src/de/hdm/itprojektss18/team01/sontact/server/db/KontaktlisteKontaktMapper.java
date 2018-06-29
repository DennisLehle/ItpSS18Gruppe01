package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.KontaktlisteKontakt;

public class KontaktlisteKontaktMapper {

	public static KontaktlisteKontaktMapper kontaktlisteKontaktMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <new> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected KontaktlisteKontaktMapper() {

	}

	/**
	 * Pruefung ob diese Klasse schon existiert. Und Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 * 
	 */
	public static KontaktlisteKontaktMapper kontaktlisteKontaktMapper() {
		if (kontaktlisteKontaktMapper == null) {
			kontaktlisteKontaktMapper = new KontaktlisteKontaktMapper();
		}
		return kontaktlisteKontaktMapper;
	}

	/**
	 * Zuordnen eins <code>Kontakt</code> Objekts zur einer
	 * <code>Kontaktliste</code>. Dabei wird die Zuordnung �ber einen
	 * zusammengesetzten Primaerschluessel - sprich der des Kontakts und der der
	 * Kontaktliste - zugeordnet.
	 * 
	 * @param k
	 *            fuer das Kontaktobjekt, kl fuer das Kontaktlistenobjekt
	 */

	public KontaktlisteKontakt addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen der neuen Zuordnung in die DB
		String addKontakt = "INSERT INTO kontaktlistekontakt (kontaktlisteid, kontaktid) VALUES (?,?)";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(addKontakt);

			// Setzen der ? als Platzhalter fuer den Wert
			stmt.setInt(1, kl.getId());
			stmt.setInt(2, k.getId());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();

			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;

	}

	/**
	 * Loeschen der Zuordnung eines Kontakts zu einer Kontaktliste
	 * 
	 * @see insert
	 * @param k
	 *            das aus der DB zu loeschende "Objekt"
	 */

	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum l�schen des Tupels aus der DB
		String deleteSQL = "DELETE FROM kontaktlistekontakt WHERE kontaktlisteid=? AND kontaktid =?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, kl.getId());
			stmt.setInt(2, k.getId());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Gibt alle Kontakte einer uebergebenen Kontaktliste aus der Datenbank aus.
	 *
	 * @param kontaktlisteId
	 * @return Vector<Kontakte>
	 */

	public Vector<Kontakt> findAllKontakteByKontaktliste(int kontaktlisteId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT kontaktlistekontakt.kontaktlisteid, kontaktliste.titel, kontakt.id, kontakt.vorname, kontakt.nachname, "
				+ "kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier " + "FROM kontaktlistekontakt "
				+ "JOIN kontakt " + "ON kontaktlistekontakt.kontaktid = kontakt.id " + "JOIN kontaktliste "
				+ "ON kontaktlistekontakt.kontaktlisteid = kontaktliste.id "
				+ "WHERE kontaktlistekontakt.kontaktlisteid= " + kontaktlisteId;

		// Vector erzeugen, der die Kontakte einer Kontaktliste speichert
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			// Fuer jeden Eintrag im Suchergebnis wird nun ein Objekt erstellt
			Kontaktliste kl = new Kontaktliste();
			kl.setTitel("titel");

			// Schleife fuer das durchlaufen aller Zeilen
			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				k.setIdentifier(rs.getString("identifier").charAt(0));

				// Statt return wird hier der Vektor erweitert
				result.addElement(k);
			}
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Entfernt den Kontakt aus allen Kontaktlisten im Zuge einer Loeschoperation
	 * des Kontakts.
	 * 
	 * @param k
	 */
	public void deleteKontaktFromAllLists(Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Tupels aus der DB
		String deleteSQL = "DELETE FROM kontaktlistekontakt WHERE kontaktid=?";

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, k.getId());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	// /**
	// * Updaten der Zuordnung eines Kontakts einer Kontaktliste (eingeklammert)
	// * @see insert
	// * @param k das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter �bergebene Objekt
	// */
	//
	// public void update(Kontaktliste kl, Kontakt k) {
	// String sql = "UPDATE KontaktlisteKontakt SET kontaktlisteid=?, kontaktid=?
	// WHERE kontaktlisteid=?, kontaktid=?";
	//
	// Connection con = DBConnection.connection();
	//
	// try {
	// PreparedStatement stmt = con.prepareStatement(sql);
	//
	//
	// stmt.setInt(1, kl.getId());
	// stmt.setInt(2, k.getId());
	//
	// stmt.setInt(3, kl.getId());
	// stmt.setInt(4, k.getId());
	//
	// stmt.executeUpdate();
	//
	// }
	//
	// catch (SQLException e2){
	// e2.printStackTrace();
	// }
	// /**
	// * Um Analogie zu insertKontakt(Kontakt k) zu wahren, geben wir k zur�ck
	// */
	// }

}
