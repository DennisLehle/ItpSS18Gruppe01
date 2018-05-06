package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;

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
	public Vector <Eigenschaft> findEigenschaftAuswahl() throws SQLException {
		// DBConnection holen
		Connection con = DBConnection.connection();
		// Ergebnisvektor anlegen
		Vector <Eigenschaft> eigenschaften = new Vector <Eigenschaft>();
		
		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT * FROM Eigenschaft");
			
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
