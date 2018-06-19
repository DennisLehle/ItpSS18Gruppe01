package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Die Klasse <code>AuspraegungMapper</code> mappt auf der Datenbank alle
 * Auspraegungen einer Eigenschaft. Fuer weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Kevin Batista
 *
 */
public class AuspraegungMapper {

	private static AuspraegungMapper auspraegungMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <new> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected AuspraegungMapper() {

	}

	/**
	 * Pruefung ob diese Klasse schon existiert. Und Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 * 
	 */
	public static AuspraegungMapper auspraegungMapper() {

		if (auspraegungMapper == null) {
			auspraegungMapper = new AuspraegungMapper();
		}

		return auspraegungMapper;

	}

	public Auspraegung insert(Auspraegung a) {

		// Aufbau der DB-Verbindung
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM auspraegung ");

			if (rs.next()) {

				// Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
				a.setId(rs.getInt("maxid") + 1);

				// SQL-Anweisung zum Einfuegen des Tupels in die DB
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO auspraegung (id, wert, kontaktid, eigenschaftid)" + "VALUES (?, ?, ?, ?)",

						Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, a.getId());
				stmt1.setString(2, a.getWert());
				stmt1.setInt(3, a.getKontaktId());
				stmt1.setInt(4, a.getEigenschaftId());

				System.out.println(stmt);

				// Ausfuehren des SQL-Statements
				stmt1.executeUpdate();
			}
			// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return a;
	}

	// /**
	// * Einfuegen eines Auspraegung-Objekts in die Datenbank.
	// *
	// * @param aus
	// * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	// * <code>id</code>.
	// */
	// public Auspraegung insert(Auspraegung aus) {
	//
	// // Aufbau der DB-Verbindung
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// // Ausfuehren des SQL Statement
	// ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM
	// auspraegung ");
	//
	// if (rs.next()) {
	//
	// // Damit dieser daraufhin um 1 inkrementiert der ID des BO zugewiesen wird
	// aus.setId(rs.getInt("maxid") + 1);
	//
	// /**
	// * Durchführung der Update-Operation via Prepared Statement
	// */
	// PreparedStatement stmt1 = con.prepareStatement(
	// "INSERT INTO auspraegung(id, wert, eigenschaftid, kontaktid)" + "
	// VALUES(?,?,?,?) ",
	//
	// Statement.RETURN_GENERATED_KEYS);
	// stmt1.setInt(1, aus.getId());
	// stmt1.setString(2, aus.getWert());
	// stmt1.setInt(3, aus.getEigenschaftId());
	// stmt1.setInt(4, aus.getKontaktId());
	//
	// System.out.println(stmt);
	// stmt1.executeUpdate();
	// }
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// }
	//
	// return aus;
	// }
	//

	/**
	 * Aktualisierung eines Auspraegung-Objekts in der Datenbank.
	 * 
	 * @param a
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public Auspraegung update(Auspraegung a) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String updateSQL = "UPDATE auspraegung SET wert=?, eigenschaftid=?, kontaktid=? WHERE id=?";

		// Query f�r die Aktualisierung des Modifikationsdatums von dem
		// dazugeh�rigen
		// Kontakt
		// String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, a.getWert());
			stmt.setInt(2, a.getEigenschaftId());
			stmt.setInt(3, a.getKontaktId());
			stmt.setInt(4, a.getId());

			// Ausfuehren des SQL Statement
			stmt.executeUpdate();

			System.out.println("Updated");

			// UPDATE-Statement des Modifikationsdatums setzen
			// stmt = con.prepareStatement(sqlDat);

			// Setzen der ? Platzhalter als VALUES
			// stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
			// stmt.setInt(2, a.getKontaktId());

			// UPDATE-Query ausfuehren
			// stmt.executeUpdate();

		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}

		return a;
	}

	/**
	 * Loeschen eines Auspraegung-Objekts aus der Datenbank.
	 * 
	 * @param a
	 */
	public void delete(Auspraegung a) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String deleteSQL = "DELETE FROM auspraegung WHERE id=?";

		// Query f�r die Aktualisierung des Modifikationsdatums von dem
		// dazugeh�rigen
		// Kontakt
		// String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, a.getId());

			// SQL-Statement ausfuehren
			stmt.executeUpdate();

			// UPDATE-Statement des Modifikationsdatums setzen
			// stmt = con.prepareStatement(sqlDat);

			// Setzen der ? Platzhalter als VALUES
			// stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
			// stmt.setInt(2, a.getKontaktId());

			// UPDATE-Query ausfuehren
			// stmt.executeUpdate();
		}

		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Loescht eine Auspraegung anhand der uebergebenen Id aus dem Selectionmodel.
	 * 
	 * @param a
	 */
	public void deleteById(int auspraegungId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Loeschen des neuen Nutzertupels in die DB
		String deleteSQL = "DELETE FROM auspraegung WHERE id=?";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, auspraegungId);

			// SQL-Statement ausfuehren
			stmt.executeUpdate();

		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Sucht eine Auspraegung anhand der uebergebenen Id.
	 * 
	 * @param a
	 */
	public Auspraegung findAuspraegungById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Loeschen des neuen Nutzertupels in die DB
		String selectByKey = "SELECT * FROM auspraegung WHERE id=? ORDER BY id";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Ausfuehren des SQL Statements
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				return a;
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
	 * Auspraegungen zu einem bestimmten Kontakt abrufen.
	 * 
	 * @param Eigenschaft
	 * @param Kontakt
	 * @return Auspraegungsobjekte
	 */

	public Vector<Auspraegung> findAuspraegungByEigenschaft(Eigenschaft e, Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Loeschen des neuen Nutzertupels in die DB
		String selectByKey = "SELECT * FROM auspraegung WHERE eigenschaftid=? AND kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Auspraegung> result = new Vector<Auspraegung>();

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, e.getId());
			stmt.setInt(2, k.getId());

			// Ausfuehren des SQL Statements
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);
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
	 * Auspraegungen eines uebergebenen Kontaktes herauszlesen.
	 * 
	 * @param kontaktId
	 * @return
	 */
	public Vector<Relatable> findAuspraegungByKontaktRelatable(int kontaktId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Auslesen des Tupels aus der DB
		String selectByKey = "SELECT auspraegung.id, auspraegung.wert, auspraegung.eigenschaftid, auspraegung.kontaktid, eigenschaft.bezeichnung\r\n"
				+ "FROM auspraegung\r\n" + "INNER JOIN eigenschaft \r\n"
				+ "ON auspraegung.eigenschaftid = eigenschaft.id \r\n" + "WHERE auspraegung.kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Relatable> result = new Vector<Relatable>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, kontaktId);

			// Ausfuehren des SQL Statements
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setBezeichnung(rs.getString("bezeichnung"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);

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
	 * Auspraegungen eines Kontaktes herauslesen.
	 * 
	 * @param kontaktId
	 * @return
	 */
	public Vector<Auspraegung> findAuspraegungByKontakt(int kontaktId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum Auslesen des Tupels aus der DB
		String selectByKey = "SELECT * FROM auspraegung WHERE kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Auspraegung> result = new Vector<Auspraegung>();

		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, kontaktId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);
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
	 * Suche nach dem Wert einer Auspraegung fuer die Ausgabe der Auspraegung
	 * innerhalb des Reports
	 * 
	 * @param wert
	 * @return
	 */

	public Vector<Auspraegung> findAuspraegungByWert(String wert) {
		Connection con = null;
		PreparedStatement stmt = null;

		Vector<Auspraegung> result = new Vector<Auspraegung>();
		try {
			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			// SQL-Anweisung zum Auslesen des Tupels aus der DB
			stmt = con.prepareStatement("SELECT * FROM auspraegung"
					+ "JOIN eigenschaft ON auspraegung.eigenschaftid = eigenschaft.id "
					+ "JOIN kontakt ON auspraegung.kontaktid = kontakt.id" + "WHERE wert like '%" + wert + "%'");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);

			}
			return result;
		}
		// Aufruf des printStackTrace ermoeglicht, die Analyse von Fehlermeldungen.
		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	// public void deleteAllByOwner(Nutzer n) {
	//
	// Connection con = null;
	// PreparedStatement stmt = null;
	//
	// String selectByKey = "DELETE FROM Auspraegung WHERE ownerid=?";
	//
	// try {
	//
	// con = DBConnection.connection();
	//
	// stmt = con.prepareStatement(selectByKey);
	// stmt.setInt(1, n.getId());
	//
	// stmt.executeUpdate();
	// }
	//
	// catch (SQLException e2){
	// e2.printStackTrace();
	// }
	// }

}
