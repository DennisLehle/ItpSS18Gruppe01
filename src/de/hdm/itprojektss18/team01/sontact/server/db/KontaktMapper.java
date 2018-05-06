package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;


	/**
	 * Mapper-Klasse, die <code>Kontakt</code>-Objekte auf eine relationale
	 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
	 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert 
	 * und gelöscht werden können. Das Mapping ist bidirektional. D.h., 
	 * Objekte können in DB-Strukturen und DB-Strukturen in Objekte umgewandelt 
	 * werden.
	 * 
	 * @author Thies
	 */

public class KontaktMapper {
	
	
	/**
	 * Die Klasse KontaktMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @see kontaktMapper()
	 */
	
	private static KontaktMapper kontaktMapper = null;
	
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new" 
	 * neue Instanzen dieser Klasse zu erzeugen
	 */
	
	protected KontaktMapper(){
		
	}
	
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>KontaktMapper.kontaktMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
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
		if(kontaktMapper == null) {
			kontaktMapper = new KontaktMapper();
		}
		
		return kontaktMapper;
	}
	
	
	/**
	 * Suchen eines Kontaktes mit vorgegebener KontaktID. Da diese eindeutig ist, 
	 * wird genau ein Objekt zurückgegeben.
	 * 
	 * @param id Primärschlüsselattribut (->DB)
	 * @return Konto-Objekt, das dem übergebenen Schlüssel entspricht, null bei nicht 
	 * vorhandenem DB-Tupel.
	 */
	
	public Kontakt findKontaktById (int id) throws SQLException {
		//DBConnection holen		
		Connection con = (Connection) DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT id, vorname, nachname FROM kontakt where id=" 
				+ id);
		
		//Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		//Ergebnistuppel in Objekt umwandeln
		Kontakt k = new Kontakt();
		while (result.next()){
			k.setId(result.getInt("id"));
			k.setVorname(result.getString("vorname"));
			k.setNachname(result.getString("nachname"));
		}

		return k;
		
		}
		catch (SQLException e2){
			e2.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Auslesen aller Kontakte eines durch ein weiteres Attribut (Name) 
	 * gegebenen Kontakts.
	 * 
	 * @see findKontaktByName
	 * @param String name für zugehörige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen 
	 * repräsentiert werden. 
	 * Bei evtl. Exceptions wird ein partiell gefüllter oder ggf. auch leerer 
	 * Vektor zurückgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findKontaktByName (String nachname, String vorname) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		Vector<Kontakt> list = new Vector<Kontakt>();
		
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT vorname, nachname FROM Kontakt WHERE nachname=" 
				+ nachname 
				+ "vorname=" 
				+ vorname + 
				" ORDER BY nachname");
		
		//Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		//Ergebnistuppel in Objekt umwandeln
		Kontakt k = new Kontakt();
		while (result.next()){
			k.setVorname(result.getString("vorname"));
			k.setNachname(result.getString("nachname"));
			}	
		
		return list;
		
		}
		catch (SQLException e2){
			e2.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Auslesen aller Kontakte eines durch Fremdschlüssel (kontaktlistenId) gegebenen Kontakts.
	 * 
	 * @see findKontaktByKontaktliste
	 * @param int kontaktlistenId für zugehörige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch die gegebene Kontaktliste repräsentiert werden. 
	 * Bei evtl. Exceptions wird ein partiell gefüllter oder ggf. auch leerer Vektor zurückgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findKontaktByKontaktliste (int kontaktlisteId) throws SQLException{
		Connection con = DBConnection.connection();
		
		try {
			Vector<Kontakt> list = new Vector<Kontakt>();
		
			//SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT kontaktlisteid FROM kontakt WHERE kontaktlisteid=" 
					+ kontaktlisteId);
		
			//Statement als Query an die DB schicken
			ResultSet result = prestmt.executeQuery();
		
			//Ergebnistuppel in Objekt umwandeln
			Kontakt k = new Kontakt();
			while (result.next()){
				k.setKontaktlisteId(result.getInt("kontaktlisteid"));
			}
		
			return list;
			
			}
			catch (SQLException e2){
				e2.printStackTrace();
			}
		return null;
	}
	
	
	/**
	 * Auslesen aller Kontakte eines durch Fremdschlüssel (ownerId) gegebenen Kontakts.
	 * 
	 * @see findKontaktByNutzerId
	 * @param int ownerId für zugehörige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Nutzer repräsentiert werden. 
	 * Bei evtl. Exceptions wird ein partiell gefüllter oder ggf. auch leerer Vektor zurückgeliefert.
	 * 
	 */
	
	public Vector<Kontakt> findKontaktByNutzerId (int ownerId) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		Vector<Kontakt> list = new Vector<Kontakt>();
		
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT ownerid FROM kontakt "
				+ "WHERE ownerid=" 
				+ ownerId);
		
		//Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		//Ergebnistuppel in Objekt umwandeln
		Kontakt k = new Kontakt();
		while (result.next()){
			k.setOwnerId(result.getInt("ownerid"));
		}
			
		return list;
		
		}
		catch (SQLException e2){
			e2.printStackTrace();
		}
		
		return null;
	}

	
	/**
	 * Einfügen eins <code>Kontakt</code> in die Datenbank. Dabei wird auch der 
	 * Primärschlüssel des übergebenen Objekt geprüft und ggf. berichtigt.
	 * @param k das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter <code>id</code>.
	 */
	
	public Kontakt insertKontakt (Kontakt k) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
			//SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement(
					"SELECT MAX(id) AS maxid " 
					+ "FROM kontakt");
			
			//Statement als Query an die DB schicken
			prestmt.executeQuery();
			
			//Zunächst wird geprüft, welches der momentan höchste Primärschlüsselwert ist.
			ResultSet rs = prestmt.executeQuery();
			
			//Wenn etwas zurückgegeben wird, kann dies nur einzeilig sein
			if (rs.next()){
				
				//k erhält den bisher maximalen, nun um 1 inkrementierten Primärschlüssel.
				k.setId(rs.getInt("maxid")+1);
				
				//Jetzt erst erfolgt die tatsächliche Einfügeoperation
				prestmt.executeUpdate();
			}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Rückgabe des evtl. korrigierten Accounts.
		 * 
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen Objekte übergeben werden, wäre die Anpassung
		 * des Kontakt-Objekts auch ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die explizite
		 * Rückgabe von k ist eher ein Stilmittel, um zu signalisieren, dass sich das Objekt evtl. im Laufe der Methode 
		 * verändert hat.
		 */
		return k;
	}
	
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param k das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	
	public Kontakt update (Kontakt k) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"UPDATE kontakt SET "
				+ "id =" + k.getId() + ","
				+ "vorname =" + k.getVorname() + ","
				+ "nachname =" + k.getNachname() + ","
				+ "modifikationsdatum =" + k.getModDat() + ","
				+ "ownerId=\"" + k.getOwnerId() + ","
				+ "kontaktlisteid =" + k.getKontaktlisteId());
		
		//Statement als Query an die DB schicken
		prestmt.executeQuery();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
		/**
		 * Um Analogie zu insertKontakt(Kontakt k) zu wahren, geben wir k zurück
		 */
		return k;
	}
	
	
	/**
	 * Löschen der Daten eines <code>Kontakt</code>-Objekts aus der Datenbank.
	 * @param k das aus der DB zu löschende "Objekt"
	 */
	
	public void deleteKontakt(Kontakt k) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"DELETE FROM Kontakt " 
				+ "WHERE id=" 
				+ k.getId());
		
		//Statement als Query an die DB schicken
		prestmt.executeQuery();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}

	
	/**
	 * Einfügen eines <code>Kontakt</code>-Objekts in eine <code>Kontaktliste</code>. 
	 * @param k das einzufügende Objekt, kl die betreffende Kontaktliste
	 */
	
	public void insertKontaktToKontaktliste(Kontakt k, int kontaktlisteId) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"INSERT INTO Kontakt(kontaktlisteId) WHERE id=" 
				+ k.getId() 
				+ " VALUES (" 
				+ kontaktlisteId 
				+ ")");
		
		//Statement als Query an die DB schicken
		prestmt.executeQuery();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Entfernen eines <code>Kontakt</code>-Objekts aus einer <code>Kontaktliste</code>. 
	 * @param k das zu entfernende Objekt, kl die betreffende Kontaktliste
	 */
	
	public void deleteKontaktFromKontaktliste(Kontakt k, int kontaktlisteId) throws SQLException {
		Connection con = DBConnection.connection();
		
		try {
		//SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"DELETE FROM Kontakt(kontaktlisteId) WHERE id=" 
				+ k.getId() 
				+ " VALUES (" 
				+ kontaktlisteId 
				+ ")");
		
		//Statement als Query an die DB schicken
		prestmt.executeQuery();
		}
		
		catch (SQLException e2){
			e2.printStackTrace();
		}
	}
	
	
}

