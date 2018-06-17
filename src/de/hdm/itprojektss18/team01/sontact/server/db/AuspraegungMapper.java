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
 * Auspraegungen einer Eigenschaft. F�r weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Kevin Batista
 *
 */
public class AuspraegungMapper {

	private static AuspraegungMapper auspraegungMapper = null;

	/**
	 * Gesch�tzter Konstruktor
	 */
	protected AuspraegungMapper() {

	}

	/**
	 * statische Methode zur Erzeugung von Instanzen, stellt die
	 * Singleton-Eingeschaft sicher.
	 */

	public static AuspraegungMapper auspraegungMapper() {

		if (auspraegungMapper == null) {
			auspraegungMapper = new AuspraegungMapper();
		}

		return auspraegungMapper;

	}

	/**
	 * Einf�gen eines Auspraegung-Objekts in die Datenbank.
	 * 
	 * @param a
	 *            das zu speichernde Objekt
	 * @return das bereits �bergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
public Auspraegung insert (Auspraegung aus) {
		
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Prüfen, welches der momentan höchste Primärschlüsselwert ist
			 */	
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM auspraegung ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhält den höchsten Primärschlüssel, um 1 inkrementiert
				 */
				aus.setId(rs.getInt("maxid")+1);
				
				/**
				 * Durchführung der Update-Operation via Prepared Statement
				 */
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO auspraegung(id, wert, eigenschaftid, kontaktid)" 
						+ " VALUES(?,?,?,?) ",
						
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1,  aus.getId());
				stmt1.setString(2, aus.getWert());
				stmt1.setInt(3, aus.getEigenschaftId());
				stmt1.setInt(4, aus.getKontaktId());
				
				System.out.println(stmt);
				stmt1.executeUpdate();
		}
		}
		catch(SQLException e2){
			e2.printStackTrace();
		}
	
		return aus;	
}

	/**
	 * Aktualisierung eines Auspraegung-Objekts in der Datenbank.
	 * 
	 * @param a
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter �bergebene Objekt
	 */

	public Auspraegung update(Auspraegung a) {

		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE auspraegung SET wert=?, eigenschaftid=?, kontaktid=? WHERE id=?";

		// Query f�r die Aktualisierung des Modifikationsdatums von dem dazugeh�rigen
		// Kontakt
		// String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, a.getWert());
			stmt.setInt(2, a.getEigenschaftId());
			stmt.setInt(3, a.getKontaktId());
			stmt.setInt(4, a.getId());

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

		catch (SQLException e2) {
			e2.printStackTrace();

		}

		return a;
	}

	/**
	 * L�schen eines Auspraegung-Objekts aus der Datenbank.
	 * 
	 * @param a
	 */

	public void delete(Auspraegung a) {

		Connection con = null;
		PreparedStatement stmt = null;

		String deleteSQL = "DELETE FROM auspraegung WHERE id=?";

		// Query f�r die Aktualisierung des Modifikationsdatums von dem dazugeh�rigen
		// Kontakt
		// String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";

		try {

			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, a.getId());

			stmt.executeUpdate();

			// UPDATE-Statement des Modifikationsdatums setzen
			// stmt = con.prepareStatement(sqlDat);

			// Setzen der ? Platzhalter als VALUES
			// stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
			// stmt.setInt(2, a.getKontaktId());

			// UPDATE-Query ausfuehren
			// stmt.executeUpdate();

		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	/**
	 * Löscht eine Ausprägung anhand der übergebenen Id des selectionModels.
	 * @param a
	 */
	public void deleteById(int auspraegungId) {

		Connection con = null;
		PreparedStatement stmt = null;

		String deleteSQL = "DELETE FROM auspraegung WHERE id=?";

		try {

			con = DBConnection.connection();

			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, auspraegungId);

			stmt.executeUpdate();

		}

		catch (SQLException e2) {
			e2.printStackTrace();

		}
	}

	
	/**
	 * Auspraegung anhand der eindeutig bestimmtbaren ID finden
	 */

	public Auspraegung findAuspraegungById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		String selectByKey = "SELECT * FROM auspraegung WHERE id=? ORDER BY id";

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datens�tzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				return a;
			}
		}

		catch (SQLException e2) {

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

		String selectByKey = "SELECT * FROM auspraegung WHERE eigenschaftid=? AND kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Auspraegung> result = new Vector<Auspraegung>();

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, e.getId());
			stmt.setInt(2, k.getId());

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datens�tzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);
			}
		}

		catch (SQLException e2) {

			e2.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Mapper aufruf um Ausprägungen eines Kontaktes herauszlesen.
	 * 
	 * @param kontaktId
	 * @return
	 */
	public Vector<Relatable> findAuspraegungByKontaktRelatable(int kontaktId) {

		Connection con = null;
		PreparedStatement stmt = null;

		String selectByKey = "SELECT auspraegung.id, auspraegung.wert, auspraegung.eigenschaftid, auspraegung.kontaktid, eigenschaft.bezeichnung\r\n"
				+ "FROM auspraegung\r\n" + "INNER JOIN eigenschaft \r\n"
				+ "ON auspraegung.eigenschaftid = eigenschaft.id \r\n" + "WHERE auspraegung.kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Relatable> result = new Vector<Relatable>();

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, kontaktId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datens�tzen aus der DB entsprechend

				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setBezeichnung(rs.getString("bezeichnung"));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);

			}

		}

		catch (SQLException e2) {

			e2.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Ausprägungen eines Kontaktes herauslesen.
	 * 
	 * @param kontaktId
	 * @return
	 */
	public Vector<Auspraegung> findAuspraegungByKontakt(int kontaktId) {

		Connection con = null;
		PreparedStatement stmt = null;

		String selectByKey = "SELECT * FROM auspraegung WHERE kontaktid=?";

		// Erstellung des Ergebnisvektors
		Vector<Auspraegung> result = new Vector<Auspraegung>();

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, kontaktId);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Auspraegung a = new Auspraegung();

				// Setzen der Attribute den Datens�tzen aus der DB entsprechend
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(a);
			}
		}

		catch (SQLException e2) {

			e2.printStackTrace();
			return null;
		}

		return result;
	}
	
	/** Suche nach dem wert einer Auspraegung f�r die Ausgabe der Auspraegung innerhalb des Reports  
	 * @param wert
	 * @return
	 */
	
public Vector<Auspraegung> findAuspraegungByWert(String wert){
	Connection con = null;
	PreparedStatement stmt = null;
	
	Vector<Auspraegung> result = new Vector<Auspraegung>();
		try {			
		con = DBConnection.connection();
		stmt = con.prepareStatement(
				"SELECT * FROM auspraegung"
						+ "JOIN eigenschaft ON auspraegung.eigenschaftid = eigenschaft.id "
						+ "JOIN kontakt ON auspraegung.kontaktid = kontakt.id" 
						+ "WHERE wert like '%" + wert + "%'" );
		

		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			
			// Ergebnis-Tupel in Objekt umwandeln
			Auspraegung a = new Auspraegung();

			// Setzen der Attribute den Datens�tzen aus der DB entsprechend
			a.setId(rs.getInt("id"));
			a.setWert(rs.getString("wert"));
			a.setEigenschaftId(rs.getInt("eigenschaftid"));
			a.setKontaktId(rs.getInt("kontaktid"));

			// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
			result.addElement(a);
			
		}
		return result;
		}
		
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
