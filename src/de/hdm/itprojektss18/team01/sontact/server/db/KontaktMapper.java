package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse <code>KontaktMapper</code> mappt auf der Datenbank alle Kontakte
 * eines Nutzers. Fuer weitere Informationen:
 * 
 * @see NutzerMapper
 *
 */

public class KontaktMapper {

	private static KontaktMapper kontaktMapper = null;

	protected KontaktMapper() {
	}

	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null) {
			kontaktMapper = new KontaktMapper();
		}

		return kontaktMapper;
	}

	/**
	 * Einfuegen eines Kontakt-Objekts in die Datenbank.
	 * 
	 * @param kontakt
	 * @return kontakt
	 */

	public Kontakt insert(Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM kontakt";

		// Query fuer den Insert
		String insertSQL = "INSERT INTO kontakt (id, vorname, nachname, erstellungsdatum, modifikationsdatum, ownerid, identifier) VALUES (?,?,?,?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// ...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
			if (rs.next()) {
				k.setId(rs.getInt("maxid") + 1);
			}

			// Jetzt erfolgt der Insert
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? Platzhalter als Values
			stmt.setInt(1, k.getId());
			stmt.setString(2, k.getVorname());
			stmt.setString(3, k.getNachname());
			stmt.setTimestamp(4, k.getErstellDat());
			stmt.setTimestamp(5, k.getModDat());
			stmt.setInt(6, k.getOwnerId());
			stmt.setString(7, String.valueOf(k.getIdentifier()));

			// INSERT-Query ausfuehren
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return k;
	}

	/**
	 * Aktualisierung eines Kontakt-Objekts in der Datenbank.
	 * 
	 * @param kontakt
	 * @return kontakt
	 */

	public Kontakt update(Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Update
		String updateSQL = "UPDATE kontakt SET vorname=?, nachname=?, ownerid=?, modifikationsdatum=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			// Setzen der ? Platzhalter als Values
			stmt.setString(1, k.getVorname());
			stmt.setString(2, k.getNachname());
			stmt.setInt(3, k.getOwnerId());
			stmt.setTimestamp(4, k.getModDat());
			stmt.setInt(5, k.getId());

			// UPDATE-Query ausfuehren
			stmt.executeUpdate();

			System.out.println("Updated");

		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}

		return k;
	}

	/**
	 * Loeschen eines Kontakt-Objekts aus der Datenbank.
	 * 
	 * @param kontakt
	 * @return void
	 */

	public void delete(Kontakt k) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Delete
		String deleteSQL = "DELETE FROM kontakt WHERE id=?";

		try {
			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, k.getId());

			// DELETE-Query ausfuehren
			stmt.executeUpdate();
		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Loeschen aller <code>Kontakt</code>-Objekte die einem <code>Owner</code>
	 * zugewiesen sind.
	 * 
	 * @param k
	 *            das aus der DB zu loeschende "Objekt"
	 */

	public void deleteAllByOwner(Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Delete
		String deleteSQL = "DELETE FROM kontakt WHERE ownerid=?";

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
	 * Die Methode findAllByOwner gibt alle Kontakte des Nutzers zurueck wo er
	 * Eigentuemer ist.
	 * 
	 * @param ownerId
	 * @return <Vector< mit Kontakten
	 */

	public Vector<Kontakt> findAllByOwner(int nutzerId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontakt WHERE ownerid=?";

		// Erstellung des Ergebnisvektors
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				k.setIdentifier(rs.getString("identifier").charAt(0));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);
			}
		}

		catch (SQLException e2) {

			e2.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Diese Methode wird benoetigt um den Kontakt eines Nutzers zu identifizieren
	 * mit dem er sich registriert hat. Dafuer wird der identifier r benoetigt der
	 * fuer Registrierung steht. Mit der nutzerId und dem identifier kann der
	 * <code>Kontakt</code> des Nutzers eindeutig identifiziert werden.
	 * 
	 * @param ownerId
	 * @return kontakt
	 */

	public Kontakt findNutzerKontaktByIdentifier(int nutzerId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontakt WHERE ownerid=? AND identifier= 'r'";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				k.setIdentifier(rs.getString("identifier").charAt(0));

				return k;

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	// Methoden checken ob sie funktionieren und ob benötigt wird.....!!

	/**
	 * Suchen eines Kontakt-Objekts mit vorgegebener KontaktId. Da diese eindeutig
	 * ist, wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primaerschluesselattribut (->DB)
	 * 
	 * @return Konto-Objekt, das dem uebergebenen Schluessel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */

	public Kontakt findKontaktById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontakt WHERE id=? ORDER BY id";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				// k.setIdentifier(rs.getString("identifier").charAt(0));

				return k;
			}
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Auslesen aller Kontakte mit einem speziellen Vornamen
	 * 
	 * @see findKontaktByVorname
	 * 
	 * @param String
	 *            vorname fuer zugehoerige Kontakte
	 * 
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen
	 *         repraesentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 * 
	 */

	public Vector<Kontakt> findKontaktByVorname(String vorname, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM kontakt WHERE vorname=? AND ownerid=? ORDER BY vorname";

		// Vector erzeugen, der die Kontaktdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, vorname);
			stmt.setInt(2, n.getId());

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			// While Schleife fuer das Durchlaufen vieler Zeilen
			// Schreiben der Objekt-Attribute aus ResultSet
			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);

			}

			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Auslesen aller Kontakte mit einem speziellen Nachnamen
	 * 
	 * @see findKontaktByNachname
	 * 
	 * @param String
	 *            nachname fuer zugehoerige Kontakte
	 * 
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen
	 *         repraesentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 * 
	 */

	public Vector<Kontakt> findKontaktByNachname(String nachname, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByName = "SELECT * FROM kontakt WHERE nachname =? AND ownerid=?  ORDER BY nachname";

		// Vector erzeugen, der die Kontaktdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByName);
			stmt.setString(1, nachname);
			stmt.setInt(2, n.getId());

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			// While Schleife fuer das Durchlaufen vieler Zeilen
			// Schreiben der Objekt-Attribute aus ResultSet
			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);

			}

			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	// Alternative:
	// ##################################################################################################

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach dem
	 * Namen und gibt diese zurueck. Hierbei wird Vor- und Nachname des Kontaktes
	 * mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param String
	 *            name, Nutzer n
	 * 
	 * @return Vector<Kontakt>
	 * 
	 */

	public Vector<Kontakt> findKontakteByName(String name, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// String selectByName = "SELECT * FROM kontakt WHERE ownerid=? AND vorname like
		// '%?%' OR nachname like '%?%' "
		// + "UNION "
		// + "SELECT kontakt.id, kontakt.vorname, kontakt.nachname,
		// kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid,
		// kontakt.identifier "
		// + "FROM kontakt INNER JOIN berechtigung ON kontakt.id = berechtigung.objectid
		// "
		// + "WHERE berechtigung.receiverid=? AND berechtigung.type = 'k' "
		// + "AND vorname like '%?%' OR nachname like '%?%'";

		// Vector erzeugen, der die Kontaktdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement("SELECT * FROM kontakt " + "WHERE ownerid = " + n.getId()
					+ " AND vorname like '%" + name + "%' OR nachname like '%" + name + "%' " + "UNION "
					+ "SELECT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier "
					+ "FROM kontakt " + "INNER JOIN berechtigung " + "ON kontakt.id = berechtigung.objectid "
					+ "WHERE berechtigung.receiverid = " + n.getId() + " AND berechtigung.type = 'k' "
					+ "AND vorname like '%" + name + "%' OR nachname like '%" + name + "%'");
			// stmt.setInt(1, n.getId());
			// stmt.setString(2, name);
			// stmt.setString(3, name);
			// stmt.setInt(4, n.getId());
			// stmt.setString(5, name);
			// stmt.setString(6, name);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);
			}

			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach deren
	 * Auspraegung und gibt diese zurueck. Hierbei wird die Auspraegung des
	 * Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param String
	 *            wert, Nutzer n
	 * 
	 * @return Vector<Kontakt>
	 * 
	 */

	public Vector<Kontakt> findKontakteByAuspraegung(String wert, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Vector erzeugen, der die Kontaktdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(
					"SELECT DISTINCT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier FROM auspraegung "
							+ "INNER JOIN kontakt " + "ON kontakt.id = auspraegung.kontaktid "
							+ "WHERE kontakt.ownerid = " + n.getId() + " AND wert like '%" + wert + "%'" + " UNION "
							+ "SELECT DISTINCT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier "
							+ "FROM auspraegung " + "INNER JOIN kontakt " + "ON kontakt.id = auspraegung.kontaktid "
							+ "INNER JOIN berechtigung " + "ON berechtigung.objectid = auspraegung.kontaktid "
							+ "WHERE berechtigung.receiverid = " + n.getId() + " AND wert like '%" + wert + "%'");

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);
			}

			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach deren
	 * Eigenschaften und gibt diese zurueck. Hierbei wird die Auspraegung des
	 * Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param String
	 *            wert, Nutzer n
	 * 
	 * @return Vector<Kontakt>
	 * 
	 */

	public Vector<Kontakt> findKontakteByEigenschaft(String bezeichnung, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Vector erzeugen, der die Kontaktdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(
					"SELECT DISTINCT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier FROM eigenschaft "
							+ "INNER JOIN auspraegung " + "ON auspraegung.eigenschaftid = eigenschaft.id "
							+ "INNER JOIN kontakt " + "ON kontakt.id = auspraegung.kontaktid "
							+ "WHERE kontakt.ownerid = " + n.getId() + " AND bezeichnung like '%" + bezeichnung + "%'"
							+ " UNION "
							+ "SELECT DISTINCT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier "
							+ "FROM eigenschaft " + "INNER JOIN auspraegung "
							+ "ON auspraegung.eigenschaftid = eigenschaft.id " + "INNER JOIN kontakt "
							+ "ON kontakt.id = auspraegung.kontaktid " + "INNER JOIN berechtigung "
							+ "ON berechtigung.objectid = auspraegung.kontaktid " + "WHERE berechtigung.receiverid = "
							+ n.getId() + " AND bezeichnung like '%" + bezeichnung + "%'");

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);
			}

			return result;
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	// Alternative End
	// ##############################################################################################

	/**
	 * Aktualisierung des Modifikationsdatums.
	 * 
	 * @param id
	 *            des Kontaktes
	 * @return
	 */

	public int updateModifikationsdatum(int id) {

		Connection con = DBConnection.connection();

		try {
			// Modifikationsdatum des dazugehoerigen Kontakts aktualisieren
			String sql2 = "UPDATE Kontakt SET modifikationsdatum=? WHERE id=?";

			PreparedStatement prestmt2 = con.prepareStatement(sql2);

			prestmt2.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			prestmt2.setInt(2, id);

			// UPDATE-Query ausfuehren
			prestmt2.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

}
