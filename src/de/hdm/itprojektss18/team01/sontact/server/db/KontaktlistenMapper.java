package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse <code>KontaklistenMapper</code> mappt auf der Datenbank alle
 * Kontaktlisten eines Nutzers. Fuer weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 */

public class KontaktlistenMapper {

	private static KontaktlistenMapper kontaktlistenMapper = null;

	protected KontaktlistenMapper() {
	}

	public static KontaktlistenMapper kontaktlistenMapper() {
		if (kontaktlistenMapper == null) {
			kontaktlistenMapper = new KontaktlistenMapper();
		}
		return kontaktlistenMapper;
	}

	/**
	 * Einfuegen eines Kontaktlisten-Objekts in die Datenbank.
	 * 
	 * @param kl
	 * @return Kontaktliste
	 */

	public Kontaktliste insert(Kontaktliste kl) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM kontaktliste";

		// Query fuer den Insert
		String insertSQL = "INSERT INTO kontaktliste (id, titel, ownerid) VALUES (?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// ...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
			if (rs.next()) {
				kl.setId(rs.getInt("maxid") + 1);
			}

			// Jetzt erfolgt der Insert
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? Platzhalter als Values
			stmt.setInt(1, kl.getId());
			stmt.setString(2, kl.getTitel());
			stmt.setInt(3, kl.getOwnerId());

			// INSERT-Query ausfuehren
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return kl;
	}

	/**
	 * Aktualisierung eines Kontaktlisten-Objekts in der Datenbank.
	 * 
	 * @param kl
	 * @return Kontaktliste
	 */

	public Kontaktliste update(Kontaktliste kl) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Update
		String updateSQL = "UPDATE kontaktliste SET titel=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, kl.getTitel());
			stmt.setInt(2, kl.getId());

			// UPDATE-Query ausfuehren
			stmt.executeUpdate();

			System.out.println("Updated");

		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}

		return kl;
	}

	/**
	 * Loeschen eines Kontaktlisten-Objekts aus der Datenbank.
	 * 
	 * @param kl
	 */
	
	public void delete(Kontaktliste kl) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Delete
		String deleteSQL = "DELETE FROM kontaktliste WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, kl.getId());

			// DELETE-Query ausfuehren
			stmt.executeUpdate();
		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Loeschen aller <code>Kontaktlisten</code>-Objekte die einem
	 * <code>Owner</code> zugewiesen sind.
	 * 
	 * @param n
	 */

	public void deleteAllByOwner(Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Delete
		String deleteSQL = "DELETE FROM kontaktliste WHERE ownerid=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, n.getId());

			// DELETE-Query ausfuehren
			stmt.executeUpdate();
		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Die Methode findKontaklistenByOwner findet alle Kontaktlisten-Objekte eines
	 * Owners die er selbst erstellt hat.
	 * 
	 * @param ownerId
	 * @return Vector von Kontaktlisten
	 */
	public Vector<Kontaktliste> findKontaktlistenByOwner(int ownerId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontaktliste WHERE ownerid=?";

		// Erstellung des Ergebnisvektors
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, ownerId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setTitel(rs.getString("titel"));
				kl.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(kl);
			}
			return result;
		}

		catch (SQLException e2) {

			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Findet eine Default Kontaktlisten-Objekt eines Nutzers. Diese Default
	 * Kontaktliste wird beim registrieren erzeugt um Kontakte speichern zu können.
	 * 
	 * @param nutzerId
	 * @return Kontaktlisten-Objekte
	 */

	public Kontaktliste findOwnersDefaultKontaktliste(int nutzerId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontaktliste WHERE ownerid=? AND titel = 'Meine Kontakte'";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();

				// Setzen der Attribute den Datens�tzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setTitel(rs.getString("titel"));
				kl.setOwnerId(rs.getInt("ownerid"));

				return kl;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Die Methode findAll ruft alle Kontaktlisten auf die in der DB gespeichert
	 * sind.
	 * 
	 * @return Vector von <code>Kontaktliste</code>-Objekten.
	 */

	public Vector<Kontaktliste> findAll() {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontaktliste ORDER BY titel";

		// Erstellung des Ergebnisvektors
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setTitel(rs.getString("titel"));
				kl.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(kl);
			}
			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Findet ein bestimmtes Kontaktlisten-Objekt anhand der ID aus der Datenbank.
	 * 
	 * @param id 
	 * @return Kontaktliste
	 */

	public Kontaktliste findById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontaktliste WHERE id=? ORDER BY id";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setTitel(rs.getString("titel"));
				kl.setOwnerId(rs.getInt("ownerid"));

				return kl;
			}
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Findet ein <code>Kontaktliste</code>-Objekt anahnd seines Titels in der DB.
	 * 
	 * @param n das <code>Nutzer</code>-Objekt 
	 * 
	 * @param titel des zu suchenden <code>Kontaktliste</code>-Objekts.
	 * 
	 * @return das gefundene <code>Kontaktliste</code>-Objekt.
	 */

	public Kontaktliste findByTitel(Nutzer n, String titel) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontaktliste WHERE ownerid=? AND titel=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, n.getId());
			stmt.setString(2, titel);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setTitel(rs.getString("titel"));
				kl.setOwnerId(rs.getInt("ownerid"));

				return kl;
			}
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}
}