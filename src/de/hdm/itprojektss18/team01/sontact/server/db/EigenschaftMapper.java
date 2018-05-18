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
		
		// DBConnection herstellen
		Connection con = DBConnection.connection();
		
		try {
			// Leeres SQL Statement anlegen
		    Statement stmt = con.createStatement();

			// Statement als Query an die DB schicken
		    ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");
		     
			// Rï¿½ckgabe beinhaltet nur eine Tupel
		    if(rs.next()){
		    	  	
		    	// kl enthï¿½lt den bisher maximalen, nun um 1 inkrementierten Primï¿½rschlï¿½ssel
		    	e.setId(rs.getInt("maxid") + 1);	    	  	
		    	
				// INSERT-Statement anlegen
		    	PreparedStatement prestmt = con.prepareStatement(
		    			"INSERT INTO Eigenschaft (id, bezeichnung) "
		    	  				+ "VALUES ('"
		    	  				+ e.getId() + "', '" 
								+ e.getBezeichnung());

		    	// INSERT-Statement ausfï¿½hren
				prestmt.execute();
			}
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
	
	public Eigenschaft update(Eigenschaft e)  {
		String sql = "UPDATE Eigenschaft SET bezeichnung=? WHERE id=?";
		
		// DBConnection herstellen
		Connection con = DBConnection.connection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
	    	
	    	stmt.setString(1, e.getBezeichnung());
	    	stmt.executeUpdate();
	    	
	    	System.out.println("Updated");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return e;
	}

	
	/**
	 * Löschen eines Eigenschaft-Objekts aus der Datenbank.
	 * @param e
	 */
	
	public void delete(Eigenschaft e) {

		// DBConnection herstellen
		Connection con = DBConnection.connection();

		try {

			// Dem SQL Statement wird der lokalen Variable ï¿½bergeben
			PreparedStatement prestmt = con.prepareStatement(
					"DELETE FROM Eigenschaft WHERE id = "
					+ e.getId());
			
			// DELETE-Statement ausfï¿½hren
			prestmt.execute();
			
		} catch (SQLException e2) {
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
		// DBConnection herstellen
		Connection con = DBConnection.connection();
		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Eigenschaft where id = " + id);
			
			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();
			
			// Da Id Primaerschluessel, besteht Rückgabe aus nur einer Tuppel
			if (rs.next()) {
				// Ergebnistuppel in Objekt umwandeln 
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));
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
	public Vector <Eigenschaft> findEigenschaftAuswahl() {
		// DBConnection holen
		Connection con = DBConnection.connection();
		// Ergebnisvektor anlegen
		Vector <Eigenschaft> eigenschaften = new Vector <Eigenschaft>();
		
		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Eigenschaft WHERE id BETWEEN 1 AND 17");
			
			// Statement als Query an die DB schicken
			ResultSet result = prestmt.executeQuery();
		
			// Alle Ergebnistuppeln in Objekte umwandeln 
			while (result.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(result.getInt("id"));
				e.setBezeichnung(result.getString("bezeichnung"));
				
				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				eigenschaften.add(e);	
			}
		} 
		catch (SQLException e2) {
			e2.printStackTrace();				
		}	
		return eigenschaften;
	}
	
	
	// findEigenschaftForAuspraegung() evlt. in AuspraegungMapper uebernehmen.
	
	/**
	 * Gibt die Eigenschaft zur einer Auspraegung eines Kontaktes zurück.
	 * 
	 * @param id
	 * @return Eigenschaft
	 */
	public Eigenschaft findEigenschaftForAuspraegung(Auspraegung a) {
		// DBConnection holen
		Connection con = DBConnection.connection();
		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
				"SELECT * FROM Eigenschaft WHERE id =" + a.getEigenschaftId());
			
			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();
			
			// Da Id Primaerschluessel, besteht Rückgabe aus nur einer Tuppel
			if (rs.next()) {
				// Ergebnistuppel in Objekt umwandeln 
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setBezeichnung(rs.getString("bezeichnung"));
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
