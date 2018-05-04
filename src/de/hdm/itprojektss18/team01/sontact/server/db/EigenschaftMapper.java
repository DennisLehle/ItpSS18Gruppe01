package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;


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
	 * Auslesen einer Eigenschaft anhand Id.
	 * 
	 * @param id
	 * @return Eigenschaft
	 * @throws SQLException
	 */
	public Eigenschaft findEigenschaftById(int id) throws SQLException {
		// DBConnection holen
		Connection con = (Connection) DBConnection.connection();
		
		// SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT id, bezeichnung FROM Eigenschaft where id = " + id);
		
		// Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		// Ergebnistuppel in Objekt umwandeln 
		Eigenschaft e = new Eigenschaft();
		while (result.next()){
			e.setId(result.getInt("id"));
			e.setBezeichnung(result.getString("bezeichnung"));
		}

		return e;
	}
	
	
	/**
	 * Auslesen aller Eigenschaften welche zur Verfügung stehen. 
	 * @return Eigenschaften
	 * @throws SQLException
	 */
	public Vector <Eigenschaft> findEigenschaftAuswahl() throws SQLException {
		// DBConnection holen
		Connection con = (Connection) DBConnection.connection();
		
		// SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT * FROM Eigenschaft");
		
		// Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		// Vector anlegen
		Vector <Eigenschaft> eigenschaften = new Vector <Eigenschaft>();
		
		// Alle Ergebnistuppeln in Objekte umwandeln 
		while (result.next()) {
			Eigenschaft e = new Eigenschaft();
			e.setId(result.getInt("id"));
			e.setBezeichnung(result.getString("bezeichnung"));
			eigenschaften.add(e);
		}
		
		return eigenschaften;
	}
	

	// findEigenschaftForAuspraegung(): notiz fuer mich: Join auf Auspraegung welche die ID fuer die Eigenschaft enthaelt)

	public Eigenschaft findEigenschaftForAuspraegung() {
		 return null;
	}
	
}
