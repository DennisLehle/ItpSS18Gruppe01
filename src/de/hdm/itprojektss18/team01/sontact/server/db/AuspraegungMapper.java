package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Die Klasse <code>AuspraegungMapper</code> mappt auf der Datenbank alle
 * Auspraegungen einer Eigenschaft.
 * Fï¿½r weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Kevin Batista
 *
 */
public class AuspraegungMapper {

	private static AuspraegungMapper auspraegungMapper = null;

	/**
	 * Geschï¿½tzter Konstruktor
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
	 * Einfï¿½gen eines Auspraegung-Objekts in die Datenbank.
	 * @param a das zu speichernde Objekt
	 * @return das bereits ï¿½bergebene Objekt, 
	 * jedoch mit ggf. korrigierter <code>id</code>.
	 */	
	
	public Auspraegung insert(Auspraegung a){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query für die Abfrage der hoechsten ID (Primärschlüssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM auspraegung";
		
		
		//Query für den Insert
		String insertSQL = "INSERT INTO auspraegung (id, wert, eigenschaftid, kontaktid, ownerid) VALUES (?,?,?,?,?)";		
		
		
	    //Query für die Aktualisierung des Modifikationsdatums von dem dazugehörigen Kontakt
//		String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	a.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    
		    
		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, a.getId());
		    stmt.setString(2, a.getWert());
		    stmt.setInt(3, a.getEigenschaftId());
		    stmt.setInt(4, a.getKontaktId());
		    stmt.setInt(5, a.getOwnerId());
		    
		    
		    //INSERT-Query ausführen
		    stmt.executeUpdate();
		    
		    
		    //UPDATE-Statement des Modifikationsdatums setzen
//			stmt = con.prepareStatement(sqlDat);
			

		    //Setzen der ? Platzhalter als VALUES
//			stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
//	    	stmt.setInt(2, a.getKontaktId());
	    	
		    
		    //UPDATE-Query ausfuehren
//	    	stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return a;
	}	

	
	/**
	 * Aktualisierung eines Auspraegung-Objekts in der Datenbank.
	 * @param a das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter ï¿½bergebene Objekt
	 */
	
	public Auspraegung update (Auspraegung a) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		String updateSQL = "UPDATE auspraegung SET wert=?, eigenschaftid=?, kontaktid=?, ownerid=? WHERE id=?";
		
		
	    //Query für die Aktualisierung des Modifikationsdatums von dem dazugehörigen Kontakt
//		String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";		
		
		try {
	
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);
			
			stmt.setString(1, a.getWert());
			stmt.setInt(2, a.getEigenschaftId());
			stmt.setInt(3, a.getKontaktId());
			stmt.setInt(4, a.getOwnerId());
			stmt.setInt(5, a.getId());
			
			stmt.executeUpdate(); 
			
			System.out.println("Updated");
			
			
		    //UPDATE-Statement des Modifikationsdatums setzen
//			stmt = con.prepareStatement(sqlDat);
			

		    //Setzen der ? Platzhalter als VALUES
//			stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
//	    	stmt.setInt(2, a.getKontaktId());
	    	
		    
		    //UPDATE-Query ausfuehren
//	    	stmt.executeUpdate();
		
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
		
		return a;
	}	
	
	
	/**
	 * Lï¿½schen eines Auspraegung-Objekts aus der Datenbank.
	 * @param a
	 */
	
	public void delete (Auspraegung a) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		
		String deleteSQL = "DELETE FROM auspraegung WHERE id=?";
		
		
	    //Query für die Aktualisierung des Modifikationsdatums von dem dazugehörigen Kontakt
//		String sqlDat = "UPDATE kontakt SET modifikationsdatum=? WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, a.getId());
			
			stmt.executeUpdate();
			
			
		    //UPDATE-Statement des Modifikationsdatums setzen
//			stmt = con.prepareStatement(sqlDat);
			

		    //Setzen der ? Platzhalter als VALUES
//			stmt.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
//	    	stmt.setInt(2, a.getKontaktId());
	    	
		    
		    //UPDATE-Query ausfuehren
//	    	stmt.executeUpdate();
			
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
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
			
		    // Statement ausfï¿½hren
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
			
		    // Statement ausfï¿½hren
		    ResultSet rs = prestmt.executeQuery();
		    
		    while (rs.next()) {
		    	Auspraegung a = new Auspraegung();
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setOwnerId(rs.getInt("ownerid"));
				
		        // Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
		        result.addElement(a);
				
			}
			
		} catch (Exception exeception) {
			exeception.printStackTrace();
		}
		
		return result;
		
	}

	public Vector<Auspraegung> findAuspraegungByKontakt(int kontaktId){
		Connection con = DBConnection.connection();
		Vector<Auspraegung> result = new Vector<Auspraegung>();
		
		try {
			// SQL-Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Auspraegung WHERE kontaktid = " + kontaktId);
			
		    // Statement ausfï¿½hren
		    ResultSet rs = prestmt.executeQuery();
		    
		    while (rs.next()) {
		    	Auspraegung a = new Auspraegung();
				a.setId(rs.getInt("id"));
				a.setWert(rs.getString("wert"));
				a.setEigenschaftId(rs.getInt("eigenschaftid"));
				a.setKontaktId(rs.getInt("kontaktid"));
				a.setOwnerId(rs.getInt("ownerid"));
				
		        // Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
		        result.addElement(a);
				
			}
			
		} catch (Exception exeception) {
			exeception.printStackTrace();
		}
		
		return result;
		
	}
	
	public void deleteAllByOwner(Nutzer n) {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"DELETE FROM Auspraegung WHERE ownerid=" 
				+ n.getId());
		
		//Statement als Query an die DB schicken
		prestmt.execute();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
}
