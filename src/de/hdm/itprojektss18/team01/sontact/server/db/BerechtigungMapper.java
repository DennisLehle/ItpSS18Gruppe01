package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;

/**
 * Die Klasse <code>BerechtigungMapper</code> bildet auf der Datenbank alle
 * Berechtigungen eines Nutzers zu den eigenen oder geteilten Objekten ab.
 * Fï¿½r weitere Informationen:
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
	 * Einfï¿½gen eines Berechtigung-Objekts in die Datenbank.
	 * 
	 * @param berechtigung
	 * @return Berechtigung
	 */
	public Berechtigung insert(Berechtigung b) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query für die Abfrage der hoechsten ID (Primärschlüssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM berechtigung";
		
		
		//Query für den Insert
		String insertSQL = "INSERT INTO berechtigung (id, ownerid, receiverid, objectid, type) VALUES (?,?,?,?,?)";		
		
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	b.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    
		  
		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, b.getId());
		    stmt.setInt(2, b.getOwnerId());
		    stmt.setInt(3, b.getReceiverId());
		    stmt.setInt(4, b.getObjectId());
		    stmt.setString(5, String.valueOf(b.getType()));
		    
		    
		    //INSERT-Query ausführen
		    stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return b;
	}	

	
	/**
	 * Lï¿½schen eines Berechtigung-Objekts aus der Datenbank.
	 * 
	 * @param berechtigung
	 * @return void
	 */

	public void delete (Berechtigung b) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM berechtigung WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, b.getId());
			
			stmt.executeUpdate();
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}

//		// DBConnection herstellen
//		Connection con = DBConnection.connection();
//
//		try {
//			// SQL Statement wird der lokalen Variable ï¿½bergeben
//			PreparedStatement prestmt = con.prepareStatement(
//					" DELETE FROM Berechtigung WHERE"
//						+ " ownerid = " + b.getOwnerId()
//						+ " AND receiverid = " + b.getReceiverId() 
//						+ " AND objectid = " + b.getObjectId() 		
//						+ " AND type = '" + b.getType() + "'");
//			
//			
//			// DELETE-Statement ausfï¿½hren
//			prestmt.execute();
//			
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
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
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Berechtigung WHERE id = " + id);

			// SQL Statement wird als Query an die DB geschickt und
			// in die Rï¿½ckgabe von rs gespeichert
			ResultSet rs = prestmt.executeQuery();

			Berechtigung b = new Berechtigung();

			// Ergebnis-Tupel in Objekt umwandeln
			if (rs.next()) {
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("object"));
				b.setType(rs.getString("type").charAt(0));
			}

			return b;
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Findet ein bestimmtes Berechtigung-Objekt aus der Datenbank.
	 * 
	 * @param berechtigung
	 * @return void
	 */
	public Vector<Berechtigung> findAll() {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {

			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Berechtigung");

			ResultSet rs = prestmt.executeQuery();
			// Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
			while (rs.next()) {
				Berechtigung b = new Berechtigung();
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rï¿½ckgabe des Ergebnisvektors
		return result;
	}

	
	/**
	 * Gibt alle Objekt-Berechtigungen ï¿½ber jene Objekte aus,
	 * welche vom Nutzer geteilt wurden.
	 * 
	 * @param ownerId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> findAllBerechtigungenByOwner(int nutzerId) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		Vector<Berechtigung> result = new Vector<Berechtigung>();

		try {

			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Berechtigung WHERE ownerid=" + nutzerId);

			ResultSet rs = prestmt.executeQuery();
			// Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
			while (rs.next()) {
				Berechtigung b = new Berechtigung();
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rï¿½ckgabe des Ergebnisvektors
		return result;
	}

	
	/**
	 * Gibt alle Objekt-Berechtigungen ï¿½ber jene Objekte aus,
	 * welche mit dem Nutzergeteilt wurden. 
	 * 
	 * @param receiverId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> findAllBerechtigungenByReceiver(int nutzerId) {
		// DBConnection herstellen
		Connection con = DBConnection.connection();

		Vector<Berechtigung> result = new Vector<Berechtigung>();
		try {

			// SQL-Statement anlegen
			PreparedStatement prestmt = con
					.prepareStatement("SELECT * FROM Berechtigung WHERE receiverid=" + nutzerId);

			ResultSet rs = prestmt.executeQuery();

			// Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
			while (rs.next()) {
				Berechtigung b = new Berechtigung();
				b.setId(rs.getInt("id"));
				b.setOwnerId(rs.getInt("ownerid"));
				b.setReceiverId(rs.getInt("receiverid"));
				b.setObjectId(rs.getInt("objectid"));
				b.setType(rs.getString("type").charAt(0));

				// Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
				result.addElement(b);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rï¿½ckgabe des Ergebnisvektors
		return result;
	}
	
	
//	--> ?
	
//	/**
//	 * Gibt eine spezielle Berechtigung eines Objekts zurÃ¼ck.
//	 * Welche mit dem Receiver geteilt wurde.
//	 * 
//	 * @param receiverId
//	 * @return Berechtigungen
//	 */
//	public Berechtigung findASingleBerechtigung(int receiverId) {
//		// DBConnection herstellen
//		Connection con = DBConnection.connection();
//
//		try {
//
//			// SQL-Statement anlegen
//			PreparedStatement prestmt = con
//					.prepareStatement("SELECT * FROM berechtigung WHERE receiverid =" + receiverId);
//
//			ResultSet rs = prestmt.executeQuery();
//
//			// Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
//			if(rs.next()) {
//				Berechtigung b = new Berechtigung();
//				b.setId(rs.getInt("id"));
//				b.setOwnerId(rs.getInt("ownerid"));
//				b.setReceiverId(rs.getInt("receiverid"));
//				b.setObjectId(rs.getInt("objectid"));
//				b.setType(rs.getString("type").charAt(0));
//				
//				return b;
//
//			}
//	
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		return null;
//	}
	
	
}
