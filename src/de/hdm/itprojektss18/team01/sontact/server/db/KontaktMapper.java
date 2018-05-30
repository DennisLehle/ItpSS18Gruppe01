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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Mapper-Klasse, die <code>Kontakt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfï¿½gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelï¿½scht werden kï¿½nnen. Das Mapping ist bidirektional. D.h., Objekte kï¿½nnen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author Thies
 */

public class KontaktMapper {

	/**
	 * Die Klasse KontaktMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fï¿½r
	 * sï¿½mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @see kontaktMapper()
	 */

	private static KontaktMapper kontaktMapper = null;

	/**
	 * Geschï¿½tzter Konstruktor - verhindert die Mï¿½glichkeit, mit "new" neue
	 * Instanzen dieser Klasse zu erzeugen
	 */

	protected KontaktMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>KontaktMapper.kontaktMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafï¿½r sorgt, dass nur eine einzige
	 * Instanz von <code>KontaktMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> KontaktMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	 * 
	 * @return DAS <code>KontaktMapper</code>-Objekt.
	 * @see kontaktMapper
	 */

	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null) {
			kontaktMapper = new KontaktMapper();
		}

		return kontaktMapper;
	}

	/**
	 * Einfï¿½gen eins <code>Kontakt</code> in die Datenbank. Dabei wird auch der
	 * Primï¿½rschlï¿½ssel des ï¿½bergebenen Objekt geprï¿½ft und ggf. berichtigt.
	 * 
	 * @param k
	 *            das zu speichernde Objekt
	 * @return das bereits ï¿½bergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	
	public Kontakt insert(Kontakt k){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query für die Abfrage der hoechsten ID (Primärschlüssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM kontakt";
		
		
		//Query für den Insert
		String insertSQL = "INSERT INTO kontakt (id, vorname, nachname, erstellungsdatum, modifikationsdatum, ownerid, identifier) VALUES (?,?,?,?,?,?,?)";		
		
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	k.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    

		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, k.getId());
		    stmt.setString(2, k.getVorname());
		    stmt.setString(3, k.getNachname());
		    stmt.setTimestamp(4, k.getErstellDat());
		    stmt.setTimestamp(5, k.getModDat());
		    stmt.setInt(6, k.getOwnerId());
		    stmt.setString(7, String.valueOf(k.getIdentifier()));
		   
		    
		    //INSERT-Query ausführen
		    stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return k;
	}	


	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param k
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter ï¿½bergebene Objekt
	 */
	
	public Kontakt update (Kontakt k) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String updateSQL = "UPDATE kontakt SET vorname=?, nachname=?, ownerid=?, modifikationsdatum=? WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);
			
		    stmt.setInt(5, k.getId());
		    stmt.setString(1, k.getVorname());
		    stmt.setString(2, k.getNachname());
		    stmt.setInt(3, k.getOwnerId());
		    stmt.setTimestamp(4, k.getModDat());
			
			stmt.executeUpdate(); 
			
			System.out.println("Updated");
		
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
		
		return k;
	}


	/**
	 * Lï¿½schen der Daten eines <code>Kontakt</code>-Objekts aus der Datenbank.
	 * 
	 * @param k
	 *            das aus der DB zu lï¿½schende "Objekt"
	 */
	
	public void delete (Kontakt k) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM kontakt WHERE id=?";
		
		try {
			
			con = DBConnection.connection();
			
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, k.getId());
			
			stmt.executeUpdate();
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
			
		}
	}


	// ZUORDNUNG IM KLK-MAPPER (!)
	/**
	 * public void deleteKontaktFromAllLists(Kontakt k) { Connection con =
	 * DBConnection.connection();
	 * 
	 * try { //SQL Statement anlegen PreparedStatement prestmt =
	 * con.prepareStatement( "DELETE FROM KontaktlisteKontakt WHERE kontaktid=" +
	 * k.getId());
	 * 
	 * //Statement als Query an die DB schicken prestmt.execute(); }
	 * 
	 * catch (SQLException e2){ e2.printStackTrace(); } }
	 **/

	/**
	 * Loeschen aller <code>Kontakt</code>-Objekte die einem <code>Owner</code>
	 * zugewiesen sind.
	 * 
	 * @param k
	 *            das aus der DB zu loeschende "Objekt"
	 */
	
	public void deleteAllByOwner (Nutzer n) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM kontakt WHERE ownerid=?";
		
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


	// ZUORDNUNG IM KLK-MAPPER (!)

	/**
	 * Zuordnung eines <code>Kontakt</code>-Objekts einer spezifische
	 * <code>Kontaktliste</code>.
	 * 
	 * @param k
	 *            der <code>Kontakt</code>, kl die <code>Kontaktliste</code>
	 *
	 *            public Kontakt addKontaktToKontaktliste(Kontakt k, Kontaktliste
	 *            kl) { Connection con = DBConnection.connection();
	 * 
	 *            try { PreparedStatement prestmt = con.prepareStatement( //"UPDATE
	 *            `Kontakt` SET`kontaktlisteid` = " + kl.getId() //+" WHERE `id` = "
	 *            + k.getId()); "INSERT INTO KontaktlisteKontakt (kontaktlisteid,
	 *            kontaktid) VALUES('" + kl.getId() + "', '" + k.getId() + "')");
	 * 
	 *            // Statement ausfï¿½hren prestmt.execute(); } catch (SQLException
	 *            e2) { e2.printStackTrace(); } return k;
	 * 
	 *            }
	 * 
	 **/
	/**
	 * Entfernen eines <code>Kontakt</code>-Objekts aus einer
	 * <code>Kontaktliste</code>.
	 * 
	 * @param Id
	 *            des Kontakts, kontaktlisteId der Kontaktliste in welche der
	 *            Kontakt gespeichert ist.
	 *
	 *            public void removeKontaktFromKontaktliste(Kontakt k, Kontaktliste
	 *            kl) { Connection con = DBConnection.connection();
	 * 
	 *            try { PreparedStatement prestmt = con.prepareStatement(
	 * 
	 *            //"UPDATE `Kontakt` SET `kontaktlisteid`= NULL WHERE `id`=" // +
	 *            k.getId() // + " AND `kontaktlisteid`=" // + kl.getId());
	 * 
	 *            "DELETE FROM KontaktlisteKontakt WHERE kontaktlisteid= " +
	 *            kl.getId() + " AND kontaktid= " + k.getId());
	 * 
	 *            // Statement ausfï¿½hren prestmt.execute();
	 * 
	 *            }
	 * 
	 *            catch (SQLException e2){ e2.printStackTrace(); } }
	 * 
	 **/

	/**
	 * Auslesen aller Kontakte eines durch Fremdschlï¿½ssel (ownerId) gegebenen
	 * Kontakts.
	 * 
	 * @see findKontaktByNutzerId
	 * @param int
	 *            ownerId fï¿½r zugehï¿½rige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Nutzer
	 *         reprï¿½sentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefï¿½llter oder ggf. auch leerer Vektor zurï¿½ckgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findAllByOwner(int ownerId) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM Kontakt WHERE ownerid=?";
		
		//Erstellung des Ergebnisvektors
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, ownerId);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				
				// Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
				result.addElement(k);
			}
		}
		
		catch (SQLException e2) {
			
			e2.printStackTrace();
			return null;
		}
		
		return result;
	}


	/**
	 * Diese Methode wird benÃ¶tigt um den Kontakt eines Nutzers zu identifizieren
	 * mit dem er sich registriert hat. DafÃ¼r wird der identifier r benÃ¶tigt der fÃ¼r
	 * Registrierung steht. Mit der nutzerId und dem identifier kann der
	 * <code>Kontakt</code> des Nutzers eindeutig identifiziert werden.
	 * 
	 * @param ownerId
	 * @return
	 */
	
	public Kontakt findNutzerKontaktByIdentifier(int nutzerId) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM kontakt WHERE ownerid=? AND identifier= 'r'";
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				k.setIdentifier(rs.getString("identifier").charAt(0));
				
				return k;
				
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	
		return null;
	}


	// Methoden checken ob sie funktionieren und ob benÃ¶tigt wird.....!!

	/**
	 * Suchen eines Kontaktes mit vorgegebener KontaktID. Da diese eindeutig ist,
	 * wird genau ein Objekt zurï¿½ckgegeben.
	 * 
	 * @param id
	 *            Primï¿½rschlï¿½sselattribut (->DB)
	 * @return Konto-Objekt, das dem ï¿½bergebenen Schlï¿½ssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	
	public Kontakt findKontaktById(int id) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM kontakt WHERE id=? ORDER BY id";
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				
				return k;
			}
		} 
		
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return null;
	}


	/**
	 * Auslesen aller Kontakte mit einem speziellen Vornamen
	 * 
	 * @see findKontaktByVorname
	 * @param String vorname fuer zugehoerige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen
	 *         repraesentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findKontaktByVorname(String vorname, Nutzer n){
		
		Connection con = null; 
		PreparedStatement stmt = null; 
		
		String selectByKey = "SELECT * FROM kontakt WHERE vorname=? AND ownerid=? ORDER BY vorname";
		
		//Vector erzeugen, der die Kontaktdatensätze aufnehmen kann
		Vector <Kontakt> result = new Vector<Kontakt>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, vorname);	
			stmt.setInt(2, n.getId());	
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife für das Durchlaufen vieler Zeilen
			//Schreiben der Objekt-Attribute aus ResultSet
			while (rs.next()) {
				
				Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				
				//Statt return wird hier der Vektor erweitert
				result.addElement(k);
				
			}
			
			return result;
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Auslesen aller Kontakte mit einem speziellen Nachnamen
	 * 
	 * @see findKontaktByNachname
	 * @param String nachname fuer zugehoerige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen
	 *         repraesentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findKontaktByNachname(String nachname, Nutzer n){
		
		Connection con = null; 
		PreparedStatement stmt = null; 
		
		String selectByName = "SELECT * FROM kontakt WHERE nachname =? AND ownerid=?  ORDER BY nachname";
		
		//Vector erzeugen, der die Kontaktdatensätze aufnehmen kann
		Vector <Kontakt> result = new Vector<Kontakt>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByName);
			stmt.setString(1, nachname);
			stmt.setInt(2, n.getId());	
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife für das Durchlaufen vieler Zeilen
			//Schreiben der Objekt-Attribute aus ResultSet
			while (rs.next()) {
				
				Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				
				//Statt return wird hier der Vektor erweitert
				result.addElement(k);
				
			}
			
			return result;
		}
		
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Auslesen aller Kontakte eines durch Fremdschluessel (kontaktlistenId)
	 * gegebenen Kontakts.
	 * 
	 * @see findKontaktByKontaktliste
	 * @param int
	 *            kontaktlistenId fï¿½r zugehï¿½rige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch die gegebene Kontaktliste
	 *         reprï¿½sentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefï¿½llter oder ggf. auch leerer Vektor zurï¿½ckgeliefert.
	 * 
	 *
	 * 
	 *         public Vector<Kontakt> findKontaktByKontaktliste (int kontaktlisteId)
	 *         { Connection con = DBConnection.connection();
	 * 
	 *         try { Vector<Kontakt> list = new Vector<Kontakt>();
	 * 
	 *         //SQL Statement anlegen PreparedStatement prestmt =
	 *         con.prepareStatement( "SELECT * FROM kontakt WHERE kontaktlisteid=" +
	 *         kontaktlisteId); //Statement muss noch angepasst werden, aufgrund der
	 *         Zwischentabelle // "SELECT * FROM kontaktliste WHERE kontaktlisteid="
	 *         // + kontaktlisteId);
	 * 
	 *         //Statement als Query an die DB schicken ResultSet result =
	 *         prestmt.executeQuery();
	 * 
	 *         //Ergebnistuppel in Objekt umwandeln Kontakt k = new Kontakt(); while
	 *         (result.next()){ k.setId(result.getInt("id"));
	 *         k.setVorname(result.getString("vorname"));
	 *         k.setNachname(result.getString("nachname"));
	 *         k.setKontaktlisteId(result.getInt("kontaktlisteid"));
	 *         k.setOwnerId(result.getInt("ownerid")); }
	 * 
	 *         return list;
	 * 
	 *         } catch (SQLException e2){ e2.printStackTrace(); } return null; }
	 * 
	 **/

	public int updateModifikationsdatum(int id) {

		Connection con = DBConnection.connection();

		try {
			// Modifikationsdatum des dazugehï¿½rigen Kontakts aktualisieren
			String sql2 = "UPDATE Kontakt SET modifikationsdatum=? WHERE id=?";
			PreparedStatement prestmt2 = con.prepareStatement(sql2);

			prestmt2.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			prestmt2.setInt(2, id);
			prestmt2.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

}
