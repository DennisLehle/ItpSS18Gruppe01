package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;

/**
 * <code>EigenschaftMapper</code>, welcher <code>Eigenschaft</code>-Objekte
 * auf der Datenbank abbildet. Für weitere Informationen: 
 * 
 * @see NuterMapper 
 * @author Yakup
 *
 */
public class EigenschaftMapper {
	
	public static EigenschaftMapper eigenschaftMapper = null;

	protected EigenschaftMapper() {	
		
	}
	
	public static EigenschaftMapper eigenschaftMapper() {
		if(eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
	}
	
	/**
	 * Hinzufügen einer neuen Eigenschaft in die Datenbank. Der Nutzer erhaelt die Funktion um selbst Eigenschaften definieren zu koennen.
	 * @param e
	 * @return e
	 */
	
	public Eigenschaft insert(Eigenschaft e){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query für die Abfrage der hoechsten ID (Primärschlüssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM eigenschaft";
		
		
		//Query für den Insert
		String insertSQL = "INSERT INTO eigenschaft (id, bezeichnung) VALUES (?,?)";		
		
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	e.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    
		    
		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, e.getId());
		    stmt.setString(2, e.getBezeichnung());
		    
		    
		    //INSERT-Query ausführen
		    stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return e;
	}	
	

	
	/**
	 * Aktualisieren eines Eigenschafts-Objekt
	 * @param e
	 * @return e
	 */
	
	public Eigenschaft update (Eigenschaft e) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String updateSQL = "UPDATE eigenschaft SET bezeichnung=? WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);
			
			stmt.setString(1, e.getBezeichnung());
			stmt.setInt(2, e.getId());
			
			stmt.executeUpdate(); 
			
			System.out.println("Updated");
		
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
		
		return e;
	}
	

	
	/**
	 * Löschen eines Eigenschaft-Objekts aus der Datenbank.
	 * @param e
	 */
	
	public void delete (Eigenschaft e) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM eigenschaft WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, e.getId());
			
			stmt.executeUpdate();
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
	}
	

	
	/**
	 * Auslesen einer Eigenschaft anhand id.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */
	
	public Eigenschaft findEigenschaftById(int id) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM eigenschaft WHERE id=? ORDER BY id";
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				e.setId(rs.getInt(1));
				e.setBezeichnung(rs.getString(2));
				
				return e;
			}
		}
		
		catch (SQLException e2) {
			
			e2.printStackTrace();
			return null;
		}
		
		return null;
	}
	

	
	/**
	 * Auslesen aller Eigenschaften welche beim Anlegen einer neuen Auspraegung 
	 * eines Kontakt-Objekts zur Verfügung stehen. 
	 * 
	 * @return Eigenschaften
	 * @throws SQLException
	 */
	
	public Vector <Eigenschaft> findEigenschaftAuswahl(){
		
		Connection con = null; 
		PreparedStatement stmt = null; 
		
		String selectByAuswahl = "SELECT * FROM eigenschaft WHERE id BETWEEN 1 AND 17";
		
		//Vector erzeugen, der die Eigenschaftsdatensätze mit ID 1-17 aufnehmen kann
		Vector <Eigenschaft> result = new Vector<Eigenschaft>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife für das Durchlaufen vieler Zeilen
			//Schreiben der Objekt-Attribute aus ResultSet
			while (rs.next()) {
				
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt(1));
				e.setBezeichnung(rs.getString(2));
				
				//Statt return wird hier der Vektor erweitert
				result.addElement(e);
			}
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// findEigenschaftForAuspraegung() evlt. in AuspraegungMapper uebernehmen.
	
	/**
	 * Gibt die Eigenschaft zur einer Auspraegung eines Kontaktes zurück.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */
	
	public Eigenschaft findEigenschaftForAuspraegung (Auspraegung a) {
		
		Connection con = null;
		PreparedStatement stmt = null; 
		
		String selectByAuswahl = "SELECT * FROM eigenschaft WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			stmt.setInt(1, a.getEigenschaftId());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {

				//Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				e.setId(rs.getInt(1));
				e.setBezeichnung(rs.getString(2));
				
				return e; 		
			}
		}
		
		catch (SQLException e2) {
			
			e2.printStackTrace();
			return null;
		}
		
		return null; 
	}

}
