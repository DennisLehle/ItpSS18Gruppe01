package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;



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
	
	public Kontaktliste insert (Kontaktliste kl) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query f�r die Abfrage der hoechsten ID (Prim�rschl�ssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM kontaktliste";
		
		
		//Query f�r den Insert
		String insertSQL = "INSERT INTO Kontaktliste (id, titel, ownerid) VALUES (?,?,?)";		
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	kl.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    

		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, kl.getId());
		    stmt.setString(2, kl.getTitel());
		    stmt.setInt(3, kl.getOwnerId());
		   
		    
		    //INSERT-Query ausf�hren
		    stmt.executeUpdate();
		    
		    
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
	
	public Kontaktliste update (Kontaktliste kl) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String updateSQL = "UPDATE Kontaktliste SET titel=? WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);
			
			stmt.setString(1, kl.getTitel());
		    stmt.setInt(2, kl.getId());
			
			stmt.executeUpdate(); 
			
			System.out.println("Updated");
		
		}
		
		catch (SQLException e2) {
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
	
	public void delete (Kontaktliste kl) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM kontaktliste WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, kl.getId());
			
			stmt.executeUpdate();
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
	}

	
		/**
		 * Loeschen aller <code>Kontaktlisten</code>-Objekte die einem <code>Owner</code> zugewiesen sind. 
		 * 
		 * @param ownerId
		 * @return void
		 */
	
	public void deleteAllByOwner (Nutzer n) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM kontaktliste WHERE ownerid=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, n.getId());
			
			stmt.executeUpdate();
		}
		
		catch (SQLException e2) {
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
	
	public Vector <Kontaktliste> findKontaktlistenByOwner (int ownerId) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM kontaktliste WHERE ownerid=?";
		
		//Erstellung des Ergebnisvektors
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, ownerId);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
		        kl.setId(rs.getInt("id"));
		        kl.setTitel(rs.getString("titel"));
		        kl.setOwnerId(rs.getInt("ownerid"));
				
				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(kl);
			}
			return result;
		}
		
		catch (SQLException e2) {
			
			e2.printStackTrace();
		}
		
		return null;
	}
		 
		 /**
		  * Findet ein Default Kontaktlisten-Objekt eines Nutzers.
		  * Diese Default Kontaktliste wird beim registrieren erzeugt um Kontakte speichern zu können.
		  * @param ownerId
		  * @return
		  * 
		  * Kommentar Melanie: Brauchen wir die?
		  */
	
	public Kontaktliste findOwnersDefaultKontaktliste(int nutzerId) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM kontaktliste WHERE ownerid=? AND titel = 'Alle Kontakte'";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
		        kl.setId(rs.getInt("id"));
		        kl.setTitel(rs.getString("titel"));
		        kl.setOwnerId(rs.getInt("ownerid"));	
		        
		        return kl;
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	
		return null;
	}
	
	
		/**
		 * Ruft alle Kontaktlisten auf die in der Db gespeichert sind.
		 * 		 
		 * @return Kontaktliste
		 * 
		 * Kommentar Melanie: Brauchen wir die?
		 */
	
	public Vector <Kontaktliste> findAll() {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM kontaktliste ORDER BY titel";
		
		//Erstellung des Ergebnisvektors
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
		        kl.setId(rs.getInt("id"));
		        kl.setTitel(rs.getString("titel"));
		        kl.setOwnerId(rs.getInt("ownerid"));
				
				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(kl);
			}
			return result;
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
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
		 * Diese Erweiterungs Methode filtert Kontakte für eine Kontaktliste heraus.
		 * Diese können für eine spezifische Kontaktliste <code>findOwnersKontaktliste()</code> 
		 * oder auch für die Default Kontaktliste <code>findOwnersDefaultKontaktliste()</code> genutzt werden.
		 * Das Ergebnis ist ein Vektor von Kontakten für eine Kontaktliste.
		 * 
		 * @see findOwnersKontaktliste(), findOwnersDefaultKontaktliste()
		 * @param kl
		 * @return
		 
		public Vector<Kontakt> getKontakteByKontaktliste(Kontaktliste kl) {
		    /*
		     * Wir spechen hier den KontaktMapper an um darüber für die Kontaktliste die ausgewähöt wurde
		     * die passenden Kontakte herauszufiltern. Dies wird für die Default Kontaktliste und für die 
		     * angelegte Kontaktliste benötigt.
		     *
		    return KontaktMapper.kontaktMapper().findAllKontakteByKontaktliste(kl.getId());
**/		 
	
			
		
		/**
		 * Diese Methode gibt eine Kontaktliste anahnd des Titels für den Nutzer heraus.
		 * Der Titel lautet hier "Alle Kontakte" da diese für jeden einzelnen Kontakt angelegt wird.
		 * Kontakte werden immer bei Add dieser Default Kontaktliste hinzugefügt.
		 * 
		 * @see findById
		 * @param titel
		 * @return Kontaktliste
		 */
		public Kontaktliste findByTitel(Nutzer n, String titel) {
			
			// DBConnection herstellen
			Connection con = DBConnection.connection();

			try {
			
							
				// SQL-Statement anlegen
				PreparedStatement prestmt = con.prepareStatement(
						"SELECT * FROM `kontaktliste` WHERE `ownerid` ="+n.getId() + " AND `titel` = \"Alle Kontakte\" ");
		
										
				// SQL Statement wird als Query an die DB geschickt und 
				//in die R�ckgabe von rs gespeichert 
				ResultSet rs = prestmt.executeQuery();
							
							
				// Ergebnis-Tupel in Objekt umwandeln
				Kontaktliste kl = new Kontaktliste(); 
				if (rs.next()) {
					kl.setId(rs.getInt("id"));
					kl.setTitel(rs.getString("titel"));
					kl.setOwnerId(rs.getInt("ownerid"));		
				}
							
				return kl;
			} 
				
			catch (SQLException e2) {
				e2.printStackTrace();
			}
			return null;
		}
}