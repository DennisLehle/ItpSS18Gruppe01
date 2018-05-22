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
				PreparedStatement prestmt1 = con.prepareStatement(
						"INSERT INTO Auspraegung (id, wert, eigenschaftid, kontaktid, ownerid) "
								+ "VALUES('" 
								+ a.getId() + "', '" 
								+ a.getWert() + "', '"
								+ a.getEigenschaftId() + "', '" 
								+ a.getKontaktId() + "', '" 
								+ a.getOwnerId() + "')");
		
				// INSERT-Statement ausfuehren
				prestmt1.execute();
				
				/**
				// Modifikationsdatum des dazugehörigen Kontakts aktualisieren
				String sql = "UPDATE Kontakt SET modifikationsdatum=? WHERE id=?";
				PreparedStatement prestmt2 = con.prepareStatement(sql);

				prestmt2.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
		    	prestmt2.setInt(2, a.getKontaktId());
		    	prestmt2.executeUpdate();
				**/
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return a;

	}
	
	/**
	 * Aktualisierung eines Auspraegung-Objekts in der Datenbank.
	 * @param a das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter ï¿½bergebene Objekt
	 */
	public Auspraegung update(Auspraegung a) {
		
		Connection con = DBConnection.connection();
		 
		 try {
			// UPDATE-Statement anlegen
			 String sql1 = "UPDATE Auspraegung SET wert=?, eigenschaftid=?, kontaktid=?, ownerid=? WHERE id=?"; 
			 PreparedStatement stmt = con.prepareStatement(sql1);
			
			 stmt.setString(1, a.getWert());
		     stmt.setInt(2, a.getEigenschaftId());
		     stmt.setInt(3, a.getKontaktId());
		     stmt.setInt(4, a.getOwnerId());
		   	 stmt.setInt(5, a.getId());
		   	 
		   	 //UPDATE Statement ausfuehren
		   	 stmt.executeUpdate();
		   	 
		   	/**
			// Modifikationsdatum des dazugehörigen Kontakts aktualisieren
			String sql2 = "UPDATE Kontakt SET modifikationsdatum=? WHERE id=?";
			PreparedStatement prestmt2 = con.prepareStatement(sql2);

			prestmt2.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
		    prestmt2.setInt(2, a.getKontaktId());
		    prestmt2.executeUpdate();
		    **/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 return a;
	}
	
	/**
	 * Lï¿½schen eines Auspraegung-Objekts aus der Datenbank.
	 * @param a
	 */
	public void delete(Auspraegung a){
		
		Connection con = DBConnection.connection();
		
		try {
			/**
			// Modifikationsdatum des dazugehörigen Kontakts aktualisieren
			String sql = "UPDATE Kontakt SET modifikationsdatum=? WHERE id=?";
			PreparedStatement prestmt1 = con.prepareStatement(sql);
			prestmt1.setTimestamp(1, new Timestamp (System.currentTimeMillis()));
		    prestmt1.setInt(2, a.getKontaktId());
		    prestmt1.executeUpdate();
		    **/
		    
			// DELETE-Statement anlegen
			PreparedStatement prestmt2 = con.prepareStatement("DELETE FROM Auspraegung "
					+ "WHERE id = " + a.getId());
			
			// DELETE-Statement ausfuehren
			prestmt2.execute();
					
			
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
