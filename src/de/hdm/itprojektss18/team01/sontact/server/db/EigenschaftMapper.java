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
 * auf der Datenbank abbildet. F�r weitere Informationen: 
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
	 * Hinzuf�gen einer neuen Eigenschaft in die Datenbank. Der Nutzer erhaelt die Funktion um selbst Eigenschaften definieren zu koennen.
	 * @param e
	 * @return e
	 */
	
	public Eigenschaft insert(Eigenschaft e){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query f�r die Abfrage der hoechsten ID (Prim�rschl�ssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM Eigenschaft";
		
		
		//Query f�r den Insert
		String insertSQL = "INSERT INTO Eigenschaft (id, bezeichnung) VALUES (?,?)";		
		
		
		
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
		    
		    
		    //INSERT-Query ausf�hren
		    stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return e;
	}	
	
//	public Eigenschaft insert(Eigenschaft e){
//	
//	// DBConnection herstellen
//	Connection con = DBConnection.connection();
//	
//	try {
//		// Leeres SQL Statement anlegen
//	    Statement stmt = con.createStatement();
//
//		// Statement als Query an die DB schicken
//	    ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");
//	     
//		// R�ckgabe beinhaltet nur eine Tupel
//	    if(rs.next()){
//	    	  	
//	    	// kl enth�lt den bisher maximalen, nun um 1 inkrementierten Prim�rschl�ssel
//	    	e.setId(rs.getInt("maxid") + 1);	    	  	
//	    	
//			// INSERT-Statement anlegen
//	    	PreparedStatement prestmt = con.prepareStatement(
//	    			"INSERT INTO Eigenschaft (id, bezeichnung) "
//	    	  				+ "VALUES ('"
//	    	  				+ e.getId() + "', '" 
//							+ e.getBezeichnung() + "')");
//	    	
//
//	    	// INSERT-Statement ausf�hren
//			prestmt.execute();
//		}
//	} catch (SQLException e2) {
//		e2.printStackTrace();
//	}
//	return e;
//}	
	
	/**
	 * Aktualisieren eines Eigenschafts-Objekt
	 * @param e
	 * @return e
	 */
	
	public Eigenschaft update (Eigenschaft e) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String updateSQL = "UPDATE Eigenschaft SET bezeichnung=? WHERE id=?";
		
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
	
//	public Eigenschaft update(Eigenschaft e)  {
//		String sql = "UPDATE Eigenschaft SET bezeichnung=? WHERE id=?";
//		
//		// DBConnection herstellen
//		Connection con = DBConnection.connection();
//		
//		try {
//			PreparedStatement stmt = con.prepareStatement(sql);
//	    	
//	    	stmt.setString(1, e.getBezeichnung());
//	    	stmt.executeUpdate();
//	    	
//	    	System.out.println("Updated");
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		return e;
//	}

	
	/**
	 * L�schen eines Eigenschaft-Objekts aus der Datenbank.
	 * @param e
	 */
	
	public void delete (Eigenschaft e) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM Eigenschaft WHERE id=?";
		
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
	
//	public void delete(Eigenschaft e) {
//
//		// DBConnection herstellen
//		Connection con = DBConnection.connection();
//
//		try {
//
//			// Dem SQL Statement wird der lokalen Variable �bergeben
//			PreparedStatement prestmt = con.prepareStatement(
//					"DELETE FROM Eigenschaft WHERE id = "
//					+ e.getId());
//			
//			// DELETE-Statement ausf�hren
//			prestmt.execute();
//			
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}
	
	/**
	 * Auslesen einer Eigenschaft anhand id.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */
	
	public Eigenschaft findEigenschaftById(int id) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM Eigenschaft where id=? ORDER BY id";
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
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
	
//	public Eigenschaft findEigenschaftById(int id) {
//		// DBConnection herstellen
//		Connection con = DBConnection.connection();
//		try {
//			// SQL Statement anlegen
//			PreparedStatement prestmt = con.prepareStatement(
//					"SELECT * FROM Eigenschaft where id = " + id);
//			
//			// Statement als Query an die DB schicken
//			ResultSet rs = prestmt.executeQuery();
//			
//			// Da Id Primaerschluessel, besteht R�ckgabe aus nur einer Tuppel
//			if (rs.next()) {
//				// Ergebnistuppel in Objekt umwandeln 
//				Eigenschaft e = new Eigenschaft();
//				e.setId(rs.getInt("id"));
//				e.setBezeichnung(rs.getString("bezeichnung"));
//				return e;			
//			}
//		}
//		catch (SQLException e2) {
//			e2.printStackTrace();
//			return null;
//		}
//		return null;
//	}
	
	/**
	 * Auslesen aller Eigenschaften welche beim Anlegen einer neuen Auspraegung 
	 * eines Kontakt-Objekts zur Verf�gung stehen. 
	 * 
	 * @return Eigenschaften
	 * @throws SQLException
	 */
	
	public Vector <Eigenschaft> findEigenschaftAuswahl(){
		
		Connection con = null; 
		PreparedStatement stmt = null; 
		
		String selectByAuswahl = "SELECT * FROM Eigenschaft WHERE id BETWEEN 1 AND 17";
		
		//Vector erzeugen, der die Eigenschaftsdatens�tze mit ID 1-17 aufnehmen kann
		Vector <Eigenschaft> result = new Vector<Eigenschaft>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife f�r das Durchlaufen vieler Zeilen
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
	
//	public Vector <Eigenschaft> findEigenschaftAuswahl() {
//		// DBConnection holen
//		Connection con = DBConnection.connection();
//		// Ergebnisvektor anlegen
//		Vector <Eigenschaft> eigenschaften = new Vector <Eigenschaft>();
//		
//		try {
//			// SQL Statement anlegen
//			PreparedStatement prestmt = con.prepareStatement(
//					"SELECT * FROM Eigenschaft WHERE id BETWEEN 1 AND 17");
//			
//			// Statement als Query an die DB schicken
//			ResultSet result = prestmt.executeQuery();
//		
//			// Alle Ergebnistuppeln in Objekte umwandeln 
//			while (result.next()) {
//				Eigenschaft e = new Eigenschaft();
//				e.setId(result.getInt("id"));
//				e.setBezeichnung(result.getString("bezeichnung"));
//				
//				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
//				eigenschaften.add(e);	
//			}
//		} 
//		catch (SQLException e2) {
//			e2.printStackTrace();				
//		}	
//		return eigenschaften;
//	}
	
	
	// findEigenschaftForAuspraegung() evlt. in AuspraegungMapper uebernehmen.
	
	/**
	 * Gibt die Eigenschaft zur einer Auspraegung eines Kontaktes zur�ck.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */
	
	public Eigenschaft findEigenschaftForAuspraegung (Auspraegung a) {
		
		Connection con = null;
		PreparedStatement stmt = null; 
		
		String selectByAuswahl = "SELECT * FROM Eigenschaft WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByAuswahl);
			stmt.setInt(1, a.getEigenschaftId());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {

				//Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
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
	
	
//	public Eigenschaft findEigenschaftForAuspraegung(Auspraegung a) {
//		// DBConnection holen
//		Connection con = DBConnection.connection();
//		try {
//			// SQL Statement anlegen
//			PreparedStatement prestmt = con.prepareStatement(
//				"SELECT * FROM Eigenschaft WHERE id =" + a.getEigenschaftId());
//			
//			// Statement als Query an die DB schicken
//			ResultSet rs = prestmt.executeQuery();
//			
//			// Da Id Primaerschluessel, besteht R�ckgabe aus nur einer Tuppel
//			if (rs.next()) {
//				// Ergebnistuppel in Objekt umwandeln 
//				Eigenschaft e = new Eigenschaft();
//				e.setId(rs.getInt("id"));
//				e.setBezeichnung(rs.getString("bezeichnung"));
//				return e;			
//			}
//		}
//		catch (SQLException e2) {
//			e2.printStackTrace();
//			return null;
//		}
//		return null;
//	}

}
