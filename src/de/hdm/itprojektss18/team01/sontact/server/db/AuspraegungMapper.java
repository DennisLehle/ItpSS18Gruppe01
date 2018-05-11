package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;


/**
 * Die Klasse <code>AuspraegungMapper</code> mappt auf der Datenbank alle
 * Auspraegungen einer Eigenschaft.
 * F�r weitere Informationen:
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
	 * @param a das zu speichernde Objekt
	 * @return das bereits �bergebene Objekt, 
	 * jedoch mit ggf. korrigierter <code>id</code>.
	 */	
	public Auspraegung insert(Auspraegung a) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();
		
		try {
			// Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Auspraegung");
			
			if (rs.next()) {
				a.setId(rs.getInt("maxid") + 1);
				
				// INSERT-Statement anlegen
				PreparedStatement prestmt = con
						.prepareStatement("INSERT INTO Auspraegung (id, wert, eigenschaftid, kontaktid, ownerid) "
								+ "VALUES('" 
								+ a.getId() + "', '" 
								+ a.getWert() + "', '"
								+ a.getEigenschaftId() + "', '" 
								+ a.getKontaktId() + "', '" 
								+ a.getOwnerId() + "')");
		
				// INSERT-Statement ausf�hren
				prestmt.execute();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return a;

	}
	
	/**
	 * Aktualisierung eines Auspraegung-Objekts in der Datenbank.
	 * @param a das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter �bergebene Objekt
	 */
	public Auspraegung update(Auspraegung a) {
		String sql = "UPDATE Auspraegung SET  wert=?, eigenschaftid=?, kontaktid=?, ownerid=? WHERE id=?"; 
		Connection con = DBConnection.connection();
		 
		 try {
			// UPDATE-Statement anlegen
			 PreparedStatement stmt = con.prepareStatement(sql);
			
			 stmt.setString(1, a.getWert());
		     stmt.setInt(2, a.getEigenschaftId());
		     stmt.setInt(3, a.getKontaktId());
		     stmt.setInt(4, a.getOwnerId());
		   	 stmt.setInt(5, a.getId());
		   	 
		   	 //UPDATE Statement ausf�hren
		   	 stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 return a;
	}
	
	/**
	 * L�schen eines Auspraegung-Objekts aus der Datenbank.
	 * @param a
	 */
	public void delete(Auspraegung a){
		
		Connection con = DBConnection.connection();
		
		try {
			// DELETE-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("DELETE FROM Auspraegung "
					+ "WHERE id = " + a.getId());
			
			// DELETE-Statement ausf�hren
			prestmt.execute();
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Auspraegung anhand der eindeutig bestimmtbaren ID finden
	 */
	public Auspraegung findAuspraegungById(int id){
		
		Connection con = DBConnection.connection();
		
		try {
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Auspraegung WHERE id = " + id);
			
		    // Statement ausf�hren
		    ResultSet rs = prestmt.executeQuery();
		    
		    if (rs.next()) {
		    	Auspraegung a = new Auspraegung();
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setOwnerId(rs.getInt("ownerid"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

		
	}
	
	
	/**
	 * Auspraegungen zu einem bestimmten Kontakt abrufen. 
	 * @param Eigenschaft
	 * @param Kontakt
	 * @return Auspraegungsobjekte
	 */
	public Vector<Auspraegung> findAuspraegungByEigenschaft(Eigenschaft e, Kontakt k){
		Connection con = DBConnection.connection();
		Vector<Auspraegung> result = new Vector<Auspraegung>();
		
		try {
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Auspraegung WHERE eigenschaftid = " + e.getId() + "AND kontaktid " + k.getId());
			
		    // Statement ausf�hren
		    ResultSet rs = prestmt.executeQuery();
		    
		    while (rs.next()) {
		    	Auspraegung a = new Auspraegung();
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setOwnerId(rs.getInt("ownerid"));
				
		        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
		        result.addElement(a);
				
			}
			
		} catch (Exception exeception) {
			exeception.printStackTrace();
		}
		
		return result;
		
	}

	public Vector<Auspraegung> findAuspraegungByKontakt(Kontakt k){
		Connection con = DBConnection.connection();
		Vector<Auspraegung> result = new Vector<Auspraegung>();
		
		try {
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Auspraegung WHERE kontaktid = " + k.getId());
			
		    // Statement ausf�hren
		    ResultSet rs = prestmt.executeQuery();
		    
		    while (rs.next()) {
		    	Auspraegung a = new Auspraegung();
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setOwnerId(rs.getInt("ownerid"));
				
		        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
		        result.addElement(a);
				
			}
			
		} catch (Exception exeception) {
			exeception.printStackTrace();
		}
		
		return result;
		
	}
}
