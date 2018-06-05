package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Mapper-Klasse, die <code>Kontakt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verf�gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gel�scht werden k�nnen. Das Mapping ist bidirektional. D.h., Objekte k�nnen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author Thies
 */

public class KontaktMapper {

	/**
	 * Die Klasse KontaktMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	 * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @see kontaktMapper()
	 */

	private static KontaktMapper kontaktMapper = null;

	/**
	 * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit "new" neue
	 * Instanzen dieser Klasse zu erzeugen
	 */

	protected KontaktMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>KontaktMapper.kontaktMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine einzige
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
	 * Einf�gen eins <code>Kontakt</code> in die Datenbank. Dabei wird auch der
	 * Prim�rschl�ssel des �bergebenen Objekt gepr�ft und ggf. berichtigt.
	 * 
	 * @param k
	 *            das zu speichernde Objekt
	 * @return das bereits �bergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	
	public Kontakt insert(Kontakt k){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query f�r die Abfrage der hoechsten ID (Prim�rschl�ssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM kontakt";
		
		
		//Query f�r den Insert
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
		   
		    
		    //INSERT-Query ausf�hren
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
	 * @return das als Parameter �bergebene Objekt
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
	 * L�schen der Daten eines <code>Kontakt</code>-Objekts aus der Datenbank.
	 * 
	 * @param k
	 *            das aus der DB zu l�schende "Objekt"
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

	/**
	 * Gibt Meine Kontakte des Nutzers aus. 
	 * @param ownerId
	 * @return
	 */
	
	public Vector<Kontakt> findAllByOwner(int nutzerId) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM Kontakt WHERE ownerid=?";
		
		//Erstellung des Ergebnisvektors
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, nutzerId);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Kontakt k = new Kontakt();
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				//k.setIdentifier(rs.getString("identifier").charAt(0));
				
				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
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
	 * Diese Methode wird benötigt um den Kontakt eines Nutzers zu identifizieren
	 * mit dem er sich registriert hat. Dafür wird der identifier r benötigt der für
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
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
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


	// Methoden checken ob sie funktionieren und ob benötigt wird.....!!

	/**
	 * Suchen eines Kontaktes mit vorgegebener KontaktID. Da diese eindeutig ist,
	 * wird genau ein Objekt zur�ckgegeben.
	 * 
	 * @param id
	 *            Prim�rschl�sselattribut (->DB)
	 * @return Konto-Objekt, das dem �bergebenen Schl�ssel entspricht, null bei
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
				
				//Setzen der Attribute den Datens�tzen aus der DB entsprechend
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
		
		//Vector erzeugen, der die Kontaktdatens�tze aufnehmen kann
		Vector <Kontakt> result = new Vector<Kontakt>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, vorname);	
			stmt.setInt(2, n.getId());	
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife f�r das Durchlaufen vieler Zeilen
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
		
		//Vector erzeugen, der die Kontaktdatens�tze aufnehmen kann
		Vector <Kontakt> result = new Vector<Kontakt>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByName);
			stmt.setString(1, nachname);
			stmt.setInt(2, n.getId());	
			
			ResultSet rs = stmt.executeQuery();
			
			
			//While Schleife f�r das Durchlaufen vieler Zeilen
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
	

// Alternative: ##################################################################################################

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach dem Namen und gibt diese zurueck. 
	 * Hierbei wird Vor- und Nachname des Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * @param String name, Nutzer n
	 * @return Vector<Kontakt>
	 * 
	 */
	public Vector<Kontakt> findKontakteByName(String name, Nutzer n) {

		Connection con = null;
		PreparedStatement stmt = null;

//		String selectByName = "SELECT * FROM kontakt WHERE ownerid=? AND vorname like '%?%' OR nachname like '%?%' "
//				+ "UNION "
//				+ "SELECT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier "
//				+ "FROM kontakt INNER JOIN berechtigung ON kontakt.id = berechtigung.objectid "
//				+ "WHERE berechtigung.receiverid=? AND berechtigung.type = 'k' "
//				+ "AND vorname like '%?%' OR nachname like '%?%'";

		Vector<Kontakt> result = new Vector<Kontakt>();

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(
					"SELECT * FROM kontakt "
					+ "WHERE ownerid = " + n.getId()
					+ " AND vorname like '%" + name + "%' OR nachname like '%" + name + "%' "
					+ "UNION "
					+ "SELECT kontakt.id, kontakt.vorname, kontakt.nachname, kontakt.erstellungsdatum, kontakt.modifikationsdatum, kontakt.ownerid, kontakt.identifier "
					+ "FROM kontakt "
					+ "INNER JOIN berechtigung "
					+ "ON kontakt.id = berechtigung.objectid "
					+ "WHERE berechtigung.receiverid = " + n.getId() + " AND berechtigung.type = 'k' "
					+ "AND vorname like '%" + name + "%' OR nachname like '%" + name + "%'");
			
//			stmt.setInt(1, n.getId());
//			stmt.setString(2, name);
//			stmt.setString(3, name);
//			stmt.setInt(4, n.getId());
//			stmt.setString(5, name);
//			stmt.setString(6, name);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

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
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach dem Namen und gibt diese zurueck. 
	 * Hierbei wird Vor- und Nachname des Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * @param String name, Nutzer n
	 * @return Vector<Kontakt>
	 * 
	 */
	public Vector<Kontakt> findKontakteByAuspraegung(String wert, Nutzer n ) {

		Connection con = null;
		PreparedStatement stmt = null;
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(
					"SELECT auspraegung.id, auspraegung.wert, auspraegung.eigenschaftid, "
					+ "auspraegung.kontaktid "
					+ "FROM auspraegung "
					+ "INNER JOIN kontakt "
					+ "ON kontakt.ownerid " + n.getId()		
					+ "WHERE kontakt.ownerid = " + n.getId()
					+ "AND wert LIKE '%" + wert + "%' "
					+ "UNION "
					+ "SELECT auspraegung.id, auspraegung.wert, "
					+ "auspraegung.eigenschaftid, auspraegung.kontaktid "
					+ "FROM auspraegung "
					+ "INNER JOIN berechtigung "
					+ "ON auspraegung.id = berechtigung.objectid "
					+ "WHERE berechtigung.receiverid = " + n.getId() + " AND berechtigung.type = 'a' "
					+ "AND wert LIKE '%" + wert + "%' ");
			
//			stmt.setInt(1, n.getId());
//			stmt.setString(2, name);
//			stmt.setString(3, name);
//			stmt.setInt(4, n.getId());
//			stmt.setString(5, name);
//			stmt.setString(6, name);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
			Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

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
	 * Auslesen aller Eigenschaften mit einer speziellen Bezeichnung
	 * 
	 * @see findEigenschaftByBezeichnung
	 * @param String bezeichnung fuer zugehoerige Eigenschaften
	 * @return ein Vektor mit Eigenschaften-Objekten, die durch die gegebene Bezeichnung
	 *         repraesentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 * 
	 */
	//ToDo
	public Vector<Kontakt> findKontakteByEigenschaft(String bezeichnung, Nutzer n){
		
		Connection con = null; 
		PreparedStatement stmt = null; 
		
		String selectByKey = "SELECT * FROM eigenschaft"
				+ "JOIN auspraegung ON eigenschaft.id = auspraegung.eigenschaftid "
				+ "JOIN kontakt ON auspraegung.kontaktid = kontakt.id" 
				+ "WHERE bezeichnung=? " ;
		
		//Vector erzeugen, der die Eigenschaftsdatensaetze aufnehmen kann
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, bezeichnung);		

			
			ResultSet rs = stmt.executeQuery();
			
			//Fuer jeden Eintrag im Suchergebnis wird nun ein Objekt erstellt
		    Eigenschaft e = new Eigenschaft();
		    e.setBezeichnung("bezeichnung");
			
			//While Schleife f�r das Durchlaufen vieler Zeilen
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

// Alternative End ##############################################################################################

	
	
	/**
	 * Aktualisierung des Modifikationsdatums. 
	 * 
	 * @param id des Kontaktes
	 * @return
	 */
	
	public int updateModifikationsdatum(int id) {

		Connection con = DBConnection.connection();

		try {
			// Modifikationsdatum des dazugeh�rigen Kontakts aktualisieren
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
