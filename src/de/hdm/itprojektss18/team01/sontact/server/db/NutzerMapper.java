package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * * Die Mapper-Klasse stellt Methoden zur Verfuegung die
 * <code>Profil</code>-Objekte auf eine relationale Datenbank abbildet. Die
 * Methoden bieten die Moeglichkeit Objekte aus der Datenbank zu suchen, sie zu
 * erzeugen und zu loeschen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author Thies
 *
 */
public class NutzerMapper {

	private static NutzerMapper nutzerMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <new> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerMapper() {
	};

	/**
	 * Pruefung ob diese Klasse schon existiert. Und Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 * 
	 */
	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}

		return nutzerMapper;
	}

	/**
	 * Einfuegen eines <code>Nutzer</code>-Objekts in die Datenbank. Dabei wird auch
	 * der Primaerschluessel des uebergebenen Objekts geprueft und ggf. berichtigt.
	 *
	 * @param n
	 *            das zu speichernde Objekt
	 * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 * 
	 * @author thies
	 */
	public Nutzer insert(Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM nutzer";

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String insertSQL = "INSERT INTO nutzer (id, email) VALUES (?,?)";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
			if (rs.next()) {
				n.setId(rs.getInt("maxid") + 1);
			}

			// Jetzt erfolgt das Einfuegen des Objekts
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? als Platzhalter fuer den Wert
			stmt.setInt(1, n.getId());
			stmt.setString(2, n.getEmailAddress());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();

			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return n;
	}

	/**
	 * /** Suche nach einem <code>Nutzer</code>-Objekts aus der Datenbank.
	 *
	 * @param id
	 * @return das gesuchte Objekt
	 */
	public Nutzer findNutzerById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM nutzer WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				n.setId(rs.getInt("id"));
				n.setEmailAddress(rs.getString("email"));

				return n;
			}
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Loeschen eines Nutzer-Objekts aus der Datenbank.
	 * 
	 * @param n
	 */
	public void delete(Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		String deleteSQL = "DELETE FROM nutzer WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Vorbereitung des SQL-Statements
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, n.getId());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Der eingeloggte Nutzer wird anhand der uebergebenen Email identifiziert und
	 * zurueckgegeben.
	 * 
	 * @param email
	 * @return Nutzer
	 */

	public Nutzer findUserByGMail(String email) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM nutzer WHERE email=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, email);

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				n.setId(rs.getInt(1));
				n.setEmailAddress(rs.getString(2));

				return n;
			}
		}

		catch (SQLException e2) {
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	
	public Vector<Nutzer> findAll() {

		Connection con = null;
		PreparedStatement stmt = null;
		Vector<Nutzer> nutzer = new Vector<Nutzer>();

		// SQL-Anweisung zum auslesen des Nutzertupels aus der DB
		String selectByKey = "SELECT * FROM nutzer ";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			// Aufbereitung des vorbereitenden Statements
			stmt = con.prepareStatement(selectByKey);
		

			// Ausfuehren des SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				n.setId(rs.getInt(1));
				n.setEmailAddress(rs.getString(2));

				nutzer.add(n);
				
			}
		}

		catch (SQLException e2) {
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
			e2.printStackTrace();
			return null;
		}

		return nutzer;
	}
	
}

// /**
// * Auslesen des dazugehörigen Kontakt Objekts des jeweiligen Nutzers der sich
// * in das System einloggt.
// * @param n
// * @return
// */
// public Kontakt getNutzerAsKontakt(Nutzer n) {
//
// /*
// * Wir greifen auf den <code>KontaktMapper</code> zurück
// * der uns zum Nutzer der sich einloggt den passenden Kontakt (Sich selbst)
// * zurückgibt.
// */
//
// return KontaktMapper.kontaktMapper().findKontaktById()
// }
