package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.KontaktlisteKontakt;

public class KontaktlisteKontaktMapper {
	
	public static KontaktlisteKontaktMapper kontaktlisteKontaktMapper = null;

	protected KontaktlisteKontaktMapper() {	
		
	}
	
	public static KontaktlisteKontaktMapper kontaktlisteKontaktMapper() {
		if(kontaktlisteKontaktMapper == null) {
			kontaktlisteKontaktMapper = new KontaktlisteKontaktMapper();
		}
		return kontaktlisteKontaktMapper;
	}
	
	
	/**
	 * Zuordnen eins <code>Kontakt</code> Objekts zur einer <code>Kontaktliste</code>.
	 * Dabei wird die Zuordnung �ber einen zusammengesetzten Primaerschluessel - sprich 
	 * der des Kontakts und der der Kontaktliste - zugeordnet.
	 * 
	 * @param k f�r das Kontaktobjekt, kl f�r das Kontaktlistenobjekt
	 */
	
	public KontaktlisteKontakt addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) {
		Connection con = DBConnection.connection();
		
		try {

				PreparedStatement  prestmt = con.prepareStatement(
						"INSERT INTO KontaktlisteKontakt (kontaktlisteid, kontaktid) VALUES ('" 
								+ kl.getId() + "', '" 
								+ k.getId()	+ "')"); 
							
				// INSERT-Statement ausf�hren
				prestmt.execute();
				
				}
		
			catch (SQLException e2) {
				e2.printStackTrace();
			}
		return null;
		}

	
//	/**
//	 * Updaten der Zuordnung eines Kontakts einer Kontaktliste (eingeklammert)
//	 * @see insert 
//	 * @param k das Objekt, das in die DB geschrieben werden soll
//	 * @return das als Parameter �bergebene Objekt
//	 */
//	
//	public void update(Kontaktliste kl, Kontakt k) {
//		String sql = "UPDATE KontaktlisteKontakt SET kontaktlisteid=?, kontaktid=? WHERE kontaktlisteid=?, kontaktid=?";
//		
//		Connection con = DBConnection.connection();
//		
//		try {
//			PreparedStatement stmt = con.prepareStatement(sql);
//	    	
//	   
//	    	stmt.setInt(1, kl.getId());
//	    	stmt.setInt(2, k.getId());
//	    	
//	    	stmt.setInt(3, kl.getId());
//	    	stmt.setInt(4, k.getId());
//
//	    	stmt.executeUpdate();
//	   
//		}
//		
//		catch (SQLException e2){
//			e2.printStackTrace();
//		}
//		/**
//		 * Um Analogie zu insertKontakt(Kontakt k) zu wahren, geben wir k zur�ck
//		 */
//	}
	
	
	/**
	 * Loeschen der Zuordnung eines Kontakts einer Kontaktliste 
	 * @see insert 
	 * @param k das aus der DB zu l�schende "Objekt"
	 */
	
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"DELETE FROM KontaktlisteKontakt WHERE kontaktlisteid= " + kl.getId()
				 + " AND kontaktid = " + k.getId());
		
		//Statement als Query an die DB schicken
		prestmt.execute();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Gibt alle Kontakte einer Kontaktliste aus 
	 *
	 * @param Eindeutliger Schluessel der Kontaktliste 
	 * @return Kontakte einer Kontaktliste
	 */
	 public Vector<Kontakt> findAllKontakteByKontaktliste(int kontaktlisteId) {
		 
		    Connection con = DBConnection.connection();
		    Vector<Kontakt> result = new Vector<Kontakt>();

		    try {
		    	PreparedStatement stmt = con.prepareStatement(
	    			
		    			"SELECT kontaktlistekontakt.kontaktlisteid, kontaktliste.titel, kontakt.id, kontakt.vorname, kontakt.nachname," +
		    			 		 "kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid " +
		    			         "FROM kontaktlistekontakt " +
		    			         "JOIN kontakt ON kontaktlistekontakt.kontaktid = kontakt.id " +
		    			         "JOIN kontaktliste ON kontaktlistekontakt.kontaktlisteid = kontaktliste.id " +
		    			         "WHERE kontaktlistekontakt.kontaktlisteid = " + kontaktlisteId);
		    	
		    			
		    	//Anpassung Statement f�r Zwischentabelle KontaktlisteKontak

		      ResultSet rs = stmt.executeQuery();

		      // Für jeden Eintrag im Suchergebnis wird nun ein Objekt erstellt.

		     
		      Kontaktliste kl = new Kontaktliste();
		      kl.setTitel("titel");
		    	
		      while (rs.next()) {
		    				      
			      Kontakt k = new Kontakt();
			      k.setId(rs.getInt("id"));
			      k.setVorname(rs.getString("vorname"));
			      k.setNachname(rs.getString("nachname"));
			      k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
			      k.setModDat(rs.getTimestamp("modifikationsdatum"));
			      k.setOwnerId(rs.getInt("ownerid"));
			     

		     

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.addElement(k);
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }
	
	 
	 /**
	  * Entfernt den Kontakt aus allen Kontaktlisten im Zuge einer Loeschoperation des Kontakts.
	  * @param k
	  */
	 public void deleteKontaktFromAllLists(Kontakt k) {
		Connection con = DBConnection.connection();
			
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"DELETE FROM KontaktlisteKontakt WHERE kontaktid=" 
				+ k.getId());
			
		//Statement als Query an die DB schicken
		prestmt.execute();
		}
			
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
