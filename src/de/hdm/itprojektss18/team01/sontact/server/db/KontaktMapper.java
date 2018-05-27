package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
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

	public Kontakt insert(Kontakt k) {
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();

			// Statement als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Kontakt");

			// R�ckgabe beinhaltet nur eine Tupel
			if (rs.next()) {

				// b enth�lt den bisher maximalen, nun um 1 inkrementierten Prim�rschl�ssel
				k.setId(rs.getInt("maxid") + 1);
				;

				PreparedStatement prestmt = con.prepareStatement(
						"INSERT INTO Kontakt (id, vorname, nachname, erstellungsdatum, modifikationsdatum, ownerid, identifier)"
								+ "VALUES('" + k.getId() + "', '" + k.getVorname() + "', '" + k.getNachname() + "', '"
								+ k.getErstellDat() + "', '" + k.getModDat() + "', '" + k.getOwnerId() + "', '"
								+ k.getIdentifier() + "')");

				// INSERT-Statement ausf�hren
				prestmt.execute();

			}
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

	public Kontakt update(Kontakt k) {
		String sql = "UPDATE Kontakt SET  vorname=?, nachname=?, ownerid=?, modifikationsdatum=? WHERE id=?";

		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, k.getVorname());
			stmt.setString(2, k.getNachname());
			stmt.setInt(3, k.getOwnerId());
			stmt.setTimestamp(4, k.getModDat());
			stmt.setInt(5, k.getId());
			stmt.executeUpdate();

		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Um Analogie zu insertKontakt(Kontakt k) zu wahren, geben wir k zur�ck
		 */
		return k;
	}

	/**
	 * L�schen der Daten eines <code>Kontakt</code>-Objekts aus der Datenbank.
	 * 
	 * @param k
	 *            das aus der DB zu l�schende "Objekt"
	 */

	public void delete(Kontakt k) {
		Connection con = DBConnection.connection();

		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("DELETE FROM Kontakt WHERE id=" + k.getId());

			// Statement als Query an die DB schicken
			prestmt.execute();
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

	public void deleteAllByOwner(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("DELETE FROM Kontakt WHERE ownerid=" + n.getId());

			// Statement als Query an die DB schicken
			prestmt.execute();
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
	 *            // Statement ausf�hren prestmt.execute(); } catch (SQLException
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
	 *            // Statement ausf�hren prestmt.execute();
	 * 
	 *            }
	 * 
	 *            catch (SQLException e2){ e2.printStackTrace(); } }
	 * 
	 **/

	/**
	 * Auslesen aller Kontakte eines durch Fremdschl�ssel (ownerId) gegebenen
	 * Kontakts.
	 * 
	 * @see findKontaktByNutzerId
	 * @param int
	 *            ownerId f�r zugeh�rige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Nutzer
	 *         repr�sentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gef�llter oder ggf. auch leerer Vektor zur�ckgeliefert.
	 * 
	 */

	public Vector<Kontakt> findAllByOwner(int ownerId) {

		// Leerer Kontakt Vektor für ale Kontakte eines Nutzers.
		Vector<Kontakt> result = new Vector<Kontakt>();

		// Datenbank Verbdinung aufbauen.
		Connection con = DBConnection.connection();

		try {

			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Kontakt " + "WHERE ownerid =" + ownerId);

			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();

			while (rs.next()) {
				Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));

				// Kontakt Objekte des Nutzers in einen Kontakt Vektor speichern.
				result.addElement(k);
			}

			return result;

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
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

		// Datenbank Verbdinung aufbauen.
		Connection con = DBConnection.connection();

		try {

			// SQL Statement anlegen
			PreparedStatement prestmt = con
					.prepareStatement("SELECT * FROM Kontakt " + "WHERE ownerid =" + nutzerId + " AND identifier = 'r'");

			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();

			while (rs.next()) {
				Kontakt k = new Kontakt();
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
				k.setIdentifier(rs.getString("identifier").charAt(0));
				// Kontakt Objekte des Nutzers in einen Kontakt Vektor speichern.
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
		// DBConnection holen
		Connection con = (Connection) DBConnection.connection();

		try {

			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Kontakt WHERE id=" + id);

			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();

			// Ergebnistuppel in Objekt umwandeln
			Kontakt k = new Kontakt();
			while (rs.next()) {
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
			}
			return k;
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Auslesen aller Kontakte eines durch ein weiteres Attribut (Name) gegebenen
	 * Kontakts.
	 * 
	 * @see findKontaktByName
	 * @param String
	 *            name f�r zugeh�rige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch den gegebenen Namen
	 *         repr�sentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gef�llter oder ggf. auch leerer Vektor zur�ckgeliefert.
	 * 
	 */

	public Vector<Kontakt> findKontaktByName(String name) {
		Connection con = DBConnection.connection();

		try {
			Vector<Kontakt> list = new Vector<Kontakt>();

			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("SELECT * FROM Kontakt WHERE nachname like %'" + name
					+ "'% OR vorname like %'" + name + "'% WHERE ownerid = " + // id des owner? // + JOIN auf Berechtigung
																			// und zurueck auf Kontakt -> +
					"' ORDER BY nachname");

			// Statement als Query an die DB schicken
			ResultSet rs = prestmt.executeQuery();

			// Ergebnistuppel in Objekt umwandeln
			Kontakt k = new Kontakt();
			while (rs.next()) {
				k.setId(rs.getInt("id"));
				k.setVorname(rs.getString("vorname"));
				k.setNachname(rs.getString("nachname"));
				k.setErstellDat(rs.getTimestamp("erstellungsdatum"));
				k.setModDat(rs.getTimestamp("modifikationsdatum"));
				k.setOwnerId(rs.getInt("ownerid"));
			}
			return list;
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Auslesen aller Kontakte eines durch Fremdschl�ssel (kontaktlistenId)
	 * gegebenen Kontakts.
	 * 
	 * @see findKontaktByKontaktliste
	 * @param int
	 *            kontaktlistenId f�r zugeh�rige Kontakte
	 * @return ein Vektor mit Kontakt-Objekten, die durch die gegebene Kontaktliste
	 *         repr�sentiert werden. Bei evtl. Exceptions wird ein partiell
	 *         gef�llter oder ggf. auch leerer Vektor zur�ckgeliefert.
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
