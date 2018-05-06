package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;

/**
 * Die Klasse <code>BerechtigungMapper</code> mappt auf der Datenbank alle
 * Berechtigungen eines Nutzers zu den eigenen oder geteilten Kontakt-Objekten.
 * Für weitere Informationen:
 * 
 * @see NutzerMapper
 * @author Ugur
 */


public class KontaktlistenMapper {
	
	private static KontaktlistenMapper kontaktlistenMapper = null;
	
	protected KontaktlistenMapper(){	
	}
	
	public static KontaktlistenMapper kontaktlistenMapper(){
		if (kontaktlistenMapper == null){
			kontaktlistenMapper = new KontaktlistenMapper();
		}
		return kontaktlistenMapper;
	}
	
	/**
	 * Einfügen eines Kontaktlisten-Objekts in die Datenbank.
	 * 
	 * @param kontaktliste
	 * @return Kontaktliste
	 */
	 public Kontaktliste insert(Kontaktliste kl){
			
			// DBConnection herstellen
			Connection con = DBConnection.connection();
			
			try {
				// Leeres SQL Statement anlegen
			    Statement stmt = con.createStatement();

				// Statement als Query an die DB schicken
			    ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Kontaktliste ");
			     
				// Rückgabe beinhaltet nur eine Tupel
			    if(rs.next()){
			    	  	
			    	// kl enthält den bisher maximalen, nun um 1 inkrementierten Primärschlüssel
			    	kl.setId(rs.getInt("maxid") + 1);	    	  	
			    	
					// INSERT-Statement anlegen
			    	PreparedStatement prestmt = con.
			    			prepareStatement("INSERT INTO Kontaktliste (id, titel, ownerid) "
			    	  				+ "VALUES ('"
			    	  				+ kl.getId() + "', '" 
									+ kl.getTitel() + "', '"
									+ kl.getOwnerId() + "')");

			    	// INSERT-Statement ausführen
					prestmt.execute();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return kl;
		}
	 
		/**
		 * Aktualisierung eines Kontaktlisten-Objekts in der Datenbank.
		 * 
		 * @param kontaktliste
		 * @return Kontaktliste
		 */
		public Kontaktliste update(Kontaktliste kl) throws SQLException {

			// DBConnection herstellen
			Connection con = DBConnection.connection();
			
			try {

				// Dem SQL Statement wird der lokalen Variable übergeben
				PreparedStatement prestmt = con.prepareStatement(
						"UPDATE Kontaktliste SET " 
						+ "id = '" + kl.getId() + "', "
						+ "titel = '" + kl.getTitel() + "', "
						+ "ownerid = '" + kl.getOwnerId() + "')");

				// INSERT-Statement ausführen
				prestmt.execute();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return kl;
		}
		
		/**
		 * Löschen eines Kontaktlisten-Objekts aus der Datenbank.
		 * 
		 * @param kontaktliste
		 * @return void
		 */

		public void delete(Kontaktliste kl) {

			// DBConnection herstellen
			Connection con = DBConnection.connection();

			try {

				// Dem SQL Statement wird der lokalen Variable übergeben
				PreparedStatement prestmt = con.prepareStatement(
						"DELETE FROM Kontaktliste WHERE id = "
						+ kl.getId());
				
				// DELETE-Statement ausführen
				prestmt.execute();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		/**
		 * Findet ein bestimmtes Kontaktlisten-Objekt aus der Datenbank.
		 * 
		 * @param kontaktliste
		 * @return void
		 */
		public Kontaktliste findById(int id) {

			// DBConnection herstellen
			Connection con = DBConnection.connection();

			try {
				
				// SQL-Statement anlegen
				PreparedStatement prestmt = con.prepareStatement(
						"SELECT * FROM Kontaktliste WHERE id = " + id);
							
				// SQL Statement wird als Query an die DB geschickt und 
				//in die Rückgabe von rs gespeichert 
				ResultSet rs = prestmt.executeQuery();
				
				Kontaktliste kl = new Kontaktliste();
				
				// Ergebnis-Tupel in Objekt umwandeln
				if (rs.next()) {
					kl.setId(rs.getInt("id"));
					kl.setTitel(rs.getString("titel"));
					kl.setOwnerId(rs.getInt("ownerid"));		
				}
				
				return kl;
			} 
			catch (SQLException e2) {
				e2.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Findet ein bestimmtes Kontaktlisten-Objekt aus der Datenbank.
		 * 
		 * @param kontaktliste
		 * @return void
		 */
		public Kontaktliste findByNutzerId(int id) {

			// DBConnection herstellen
			Connection con = DBConnection.connection();

			try {
				
				// SQL-Statement anlegen
				PreparedStatement prestmt = con.prepareStatement(
						"SELECT * FROM Kontaktliste WHERE id = " + id);
							
				// SQL Statement wird als Query an die DB geschickt und 
				//in die Rückgabe von rs gespeichert 
				ResultSet rs = prestmt.executeQuery();
				
				Kontaktliste kl = new Kontaktliste();
				
				// Ergebnis-Tupel in Objekt umwandeln
				if (rs.next()) {
					kl.setId(rs.getInt("id"));
					kl.setTitel(rs.getString("titel"));
					kl.setOwnerId(rs.getInt("ownerid"));		
				}
				
				return kl;
			} 
			catch (SQLException e2) {
				e2.printStackTrace();
				return null;
			}
		}
	 
}

