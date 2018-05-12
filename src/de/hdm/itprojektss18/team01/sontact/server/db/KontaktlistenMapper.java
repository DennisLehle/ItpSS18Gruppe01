package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;



/**
 * Die Klasse <code>KontaklistenMapper</code> mappt auf der Datenbank alle
 * Kontaktlisten eines Nutzers.
 * F�r weitere Informationen:
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
	 * Einf�gen eines Kontaktlisten-Objekts in die Datenbank.
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
			     
				// R�ckgabe beinhaltet nur eine Tupel
			    if(rs.next()){
			    	  	
			    	// kl enth�lt den bisher maximalen, nun um 1 inkrementierten Prim�rschl�ssel
			    	kl.setId(rs.getInt("maxid") + 1);	    	  	
			    	
					// INSERT-Statement anlegen
			    	PreparedStatement prestmt = con.
			    			prepareStatement("INSERT INTO Kontaktliste (id, titel, ownerid) "
			    	  				+ "VALUES ('"
			    	  				+ kl.getId() + "', '" 
									+ kl.getTitel() + "', '"
									+ kl.getOwnerId() + "')");

			    	// INSERT-Statement ausf�hren
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
		public Kontaktliste update(Kontaktliste kl)  {
			String sql = "UPDATE Kontaktliste SET titel=?, ownerid=? WHERE id=?";
			
			// DBConnection herstellen
			Connection con = DBConnection.connection();
			
			try {
				PreparedStatement stmt = con.prepareStatement(sql);
		    	
		    	stmt.setString(1, kl.getTitel());
		    	stmt.setInt(2, kl.getOwnerId());
		    	stmt.setInt(3, kl.getId());
		    	stmt.executeUpdate();
		    	
		    	System.out.println("Updated");

				// Dem SQL Statement wird der lokalen Variable �bergeben
	//			PreparedStatement prestmt = con.prepareStatement(
	//					"UPDATE Kontaktliste SET " 
	//					+ "id = '" + kl.getId() + "', "
	//					+ "titel = '" + kl.getTitel() + "', "
	//					+ "ownerid = '" + kl.getOwnerId() + "')");

				// INSERT-Statement ausf�hren
	//			prestmt.execute();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return kl;
		}

		/**
		 * L�schen eines Kontaktlisten-Objekts aus der Datenbank.
		 * 
		 * @param kontaktliste
		 * @return void
		 */

		public void delete(Kontaktliste kl) {

			// DBConnection herstellen
			Connection con = DBConnection.connection();

			try {

				// Dem SQL Statement wird der lokalen Variable �bergeben
				PreparedStatement prestmt = con.prepareStatement(
						"DELETE FROM Kontaktliste WHERE id = "
						+ kl.getId());
				
				// DELETE-Statement ausf�hren
				prestmt.execute();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		/**
		 * Findet Kontaktlisten-Objekte eines Owners die er erstellt hat.
		 * Dieses Statement schließt die "Default Kontaktliste" aus.
		 * 
		 * @param kontaktliste
		 * @return 
		 * @return 
		 * @return void
		 */
		 public Vector<Kontaktliste> findOwnersKontaktliste(Kontaktliste kl) {
			 
			    Connection con = DBConnection.connection();
			    Vector<Kontaktliste> result = new Vector<Kontaktliste>();

			    try {
			    	PreparedStatement stmt = con.prepareStatement("SELECT * FROM `Kontaktliste` WHERE `ownerid` AND NOT `titel` = 'DefaultKl'");

			      ResultSet rs = stmt.executeQuery();

			      // Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
			      while (rs.next()) {
			        Kontaktliste kl1 = new Kontaktliste();
			        kl1.setId(rs.getInt("id"));
			        kl1.setTitel(rs.getString("titel"));
			        kl1.setOwnerId(rs.getInt("ownerid"));
			     

			        // Hinzufügen des neuen Objekts zum Ergebnisvektor
			        result.addElement(kl1);
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }

			    // Ergebnisvektor zurückgeben 
			    return result;
			  }
		 
		 /**
		  * Findet ein Default Kontaktlisten-Objekt eines Nutzers.
		  * Diese Default Kontaktliste wird beim registrieren erzeugt um Kontakte speichern zu können.
		  * @param ownerId
		  * @return
		  */
		 public Kontaktliste findOwnersDefaultKontaktliste(Kontaktliste kl) {
			 
			    Connection con = DBConnection.connection();

			    try {
			    	PreparedStatement stmt = con.prepareStatement("SELECT * FROM `Kontaktliste` WHERE `ownerid` AND `titel` = 'DefaultKl'");

			      ResultSet rs = stmt.executeQuery();

			      // Für jeden Eintrag im Suchergebnis wird nun ein Kontaktlisten-Objekt erstellt.
			      Kontaktliste kl1 = new Kontaktliste();
			      if (rs.next()) {
			       
			        kl1.setId(rs.getInt("id"));
			        kl1.setTitel(rs.getString("titel"));
			        kl1.setOwnerId(rs.getInt("ownerid"));
			     

			        // Hinzufügen des neuen Objekts zum Ergebnisvektor
			        return kl1;
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }
				return null;
			  }

		
		/**
		 * Ruft alle Kontaktlisten auf die in der Db gespeichert sind.
		 * 		 
		 * @return Kontaktliste
		 */
		public Vector<Kontaktliste> findAll() {

			// DBConnection herstellen
			Connection con = DBConnection.connection();
			
			Vector<Kontaktliste> result = new Vector<Kontaktliste>();

			try {
				
				// SQL-Statement anlegen
				PreparedStatement prestmt = con.prepareStatement(
				"SELECT * FROM Kontaktliste " + "ORDER BY id");

				ResultSet rs = prestmt.executeQuery();
				//Jeder Treffer erzeugt eine neue Instanz als Suchergebnis.
				while (rs.next()) {
					Kontaktliste kl = new Kontaktliste();
					kl.setId(rs.getInt("id"));
					kl.setTitel(rs.getString("titel"));
					kl.setOwnerId(rs.getInt("ownerid"));

					// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
					 result.addElement(kl);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			// R�ckgabe des Ergebnisvektors
			return result;
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
						"SELECT * FROM Kontaktliste WHERE id =" + id);
							
				// SQL Statement wird als Query an die DB geschickt und 
				//in die R�ckgabe von rs gespeichert 
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
		 * Das insert fügt einen Kontakt zu einer Kontaktliste hinzu.
		 * 
		 * @param k Kontakt der einer Kontaktliste zugewiesen werden soll.
		 * @param kl Zu welcher Kontaktliste der Kontakt hinzugefügt werden soll.
		 */
		public void insertKontakt(Kontakt k, Kontaktliste kl) {
			
			KontaktMapper.kontaktMapper().addKontaktToKontaktliste(k, kl);
		
		}
		
		/**
		 * Methode zum löschen eines Kontakts aus einer Kontaktliste.
		 * 
		 * @param kl aus der der Kontakt entfertn werden soll.
		 */
		public void deleteKontakt(Kontakt k) {
			
			KontaktMapper.kontaktMapper().deleteKontaktFromKontaktliste(k);
			
		}
		
		/**
		 * Diese Erweiterungs Methode filtert Kontakte für eine Kontaktliste heraus.
		 * Diese können für eine spezifische Kontaktliste <code>findOwnersKontaktliste()</code> 
		 * oder auch für die Default Kontaktliste <code>findOwnersDefaultKontaktliste()</code> genutzt werden.
		 * Das Ergebnis ist ein Vektor von Kontakten für eine Kontaktliste.
		 * 
		 * @see findOwnersKontaktliste(), findOwnersDefaultKontaktliste()
		 * @param kl
		 * @return
		 */
		public Vector<Kontakt> getKontakte(Kontaktliste kl) {
		    /*
		     * Wir spechen hier den KontaktMapper an um darüber für die Kontaktliste die ausgewähöt wurde
		     * die passenden Kontakte herauszufiltern. Dies wird für die Default Kontaktliste und für die 
		     * angelegte Kontaktliste benötigt.
		     */
		    return KontaktMapper.kontaktMapper().findKontakteVonOwner(kl.getOwnerId(), kl.getId()); 
		  }
}