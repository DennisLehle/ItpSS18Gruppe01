package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;

/**
 * Die Klasse <code>BerechtigungMapper</code> mappt auf der Datenbank alle
 * Berechtigungen eines Nutzers zu den eigenen oder geteilten Kontakt-Objekten.
 * F�r weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Miescha
 */

public class BerechtigungMapper {

	private static BerechtigungMapper berechtigungMapper = null;

	protected BerechtigungMapper() {
	}

	public static BerechtigungMapper berechtigungMapper() {
		if (berechtigungMapper == null) {
			berechtigungMapper = new BerechtigungMapper();
		}
		return berechtigungMapper;
	}

	/**
	 * Einf�gen eines Berechtigung-Objekts in die Datenbank.
	 * 
	 * @param berechtigung
	 * @return Berechtigung
	 */
	public Berechtigung insert(Berechtigung b) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();

			// Statement als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Berechtigung");

			// R�ckgabe beinhaltet nur eine Tupel
			if (rs.next()) {

				// b enth�lt den bisher maximalen, nun um 1 inkrementierten Prim�rschl�ssel
				b.setId(rs.getInt("maxid") + 1);

				// INSERT-Statement anlegen
				PreparedStatement prestmt = con
						.prepareStatement("INSERT INTO Berechtigung (id, ownerid, receiverid, objectid, type) VALUES ('" 
								+ b.getId() + "', '" 
								+ b.getOwnerId() + "', '"
								+ b.getReceiverId() + "', '" 
								+ b.getObjectId() + "', '"
								+ b.getType() + "')");
								

				// INSERT-Statement ausf�hren
				prestmt.execute();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return b;
	}

	/**
	 * Aktualisierung eines Berechtigung-Objekts in der Datenbank.
	 * 
	 * @param berechtigung
	 * @return Berechtigung
	 */
	public Berechtigung update(Berechtigung b) {
		String sql = "UPDATE Berechtigung SET  kontaktid=?, kontaktlisteid=?, auspraegungid=?, ownerid=?, receiverid=?, type=?, berechtigungsstufe=? WHERE id=?";
		
		// DBConnection herstellen
		Connection con = DBConnection.connection();
		
		try {
		// Angelegten String sql dem PreparedStatement übergeben.
		PreparedStatement stmt = con.prepareStatement(sql);
	    	
	   
	    	stmt.setInt(1, b.getKontaktId());
	    	stmt.setInt(2, b.getKontaktlisteId());
	    	stmt.setInt(3, b.getAuspraegungId());
	    	stmt.setInt(4, b.getOwnerId());
	    	stmt.setInt(5, b.getReceiverId());
	    	stmt.setInt(6, b.getType());
	    	stmt.setInt(7, b.getBerechtigungsstufe());
	   
	    	stmt.setInt(8, b.getId());
	    	stmt.executeUpdate();
	    	
	    	System.out.println("Updated");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return b;
	}

	/**
	 * L�schen eines Berechtigung-Objekts aus der Datenbank.
	 * 
	 * @param berechtigung
	 * @return void
	 */

	public void delete (Berechtigung b) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		try {
			// Dem SQL Statement wird der lokalen Variable �bergeben
			PreparedStatement prestmt = con.prepareStatement(
					" DELETE FROM Berechtigung WHERE "
					+ " objectid = " + b.getId() 		
					+ " AND receiverid = " + b.getId() 
					+ " AND ownerid = " + b.getId()
					+ " AND id = " + b.getId()
					+ " AND type = " + b.getId());
			
			
			// DELETE-Statement ausf�hren
			prestmt.execute();
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Findet ein bestimmtes Berechtigung-Objekt aus der Datenbank.
	 * 
	 * @param berechtigung
	 * @return void
	 */
	public Berechtigung findById(int id) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		try {
			
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Berechtigung WHERE id = " + id);
						
			// SQL Statement wird als Query an die DB geschickt und 
			//in die R�ckgabe von rs gespeichert 
			ResultSet rs = prestmt.executeQuery();
			
			Berechtigung b = new Berechtigung();
			
			// Ergebnis-Tupel in Objekt umwandeln
			if (rs.next()) {
				b.setId(rs.getInt("id"));
				b.setBerechtigungsstufe(rs.getInt("berechtigungsstufe"));
				b.setObjectId(rs.getInt("object"));
				b.setType(rs.getString("type").charAt(0));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));		
			}
			
			return b;
		} 
		catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
	}

	/**
	 * Ruft eine Liste auf die alle Kontakte aufzeigt die ein Nutzer
	 * <code>holderId</code> mit anderen Nutzern <code>receiverId</code> geteilt hat.
	 * 
	 * @param holderId
	 * @return Berechtigungen
	 */

	public Vector<Berechtigung> findAllSharedKontakteWith(int ownerId) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();
		
		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {
			
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
			"SELECT * FROM Berechtigung WHERE ownerid=" + ownerId); //+ "AND type= 'k'");

			ResultSet rs = prestmt.executeQuery();
			//Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
			while (rs.next()) {
				Berechtigung b = new Berechtigung();
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				 result.addElement(b);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// R�ckgabe des Ergebnisvektors
		return result;
	}

	/**
	 * Ruft einen Liste auf die alle Kontakte aufzeigt die ein Nutzer
	 * <code>receiverId</code> durch einen anderen Nutzer <code>holderId</code> geteilt
	 * bekommen hat.
	 * 
	 * @param receiverId
	 * @return Berechtigungen
	 */

	public Vector<Berechtigung> findAllSharedKontakteFrom(int receiverId) {
		// DBConnection herstellen
				Connection con = DBConnection.connection();
				
				Vector<Berechtigung> result = new Vector<Berechtigung>();
		try {
			
			// SQL-Statement anlegen
						PreparedStatement prestmt = con.prepareStatement(
								"SELECT * FROM Berechtigung WHERE receiverid=" + receiverId); //+ "AND type= 'k'
						
						ResultSet rs = prestmt.executeQuery();

			//Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
			while (rs.next()) {
				Berechtigung b = new Berechtigung();
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));
				

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		//R�ckgabe des Ergebnisvektors
		return result;
	}
}
