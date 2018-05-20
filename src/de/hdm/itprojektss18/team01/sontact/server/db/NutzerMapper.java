package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * * Die Mapper-Klasse stellt Methoden zur Verfügung die
 * <code>Profil</code>-Objekte auf eine relationale Datenbank abbildet. Die
 * Methoden bieten die Möglichkeit Objekte aus der Datenbank zu suchen, sie zu
 * erzeugen und zu löschen. Das Mapping ist bidirektional. D.h., Objekte
 * können in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * Diese Mapper-Klasse besitzt Singleton-Eigenschaften und wird nur einmal
 * mithilfe der Methode <code>profilMapper()</code> initialisiert. Der
 * Konstruktor ist bewusst durch <code>protected</code> geschützt, damit nur
 * eine einzige Instanz der Klasse exisitert.
 * 
 * @author Thies
 *
 */
public class NutzerMapper {

	private static NutzerMapper nutzerMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new" neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerMapper() {
	};

	/**
	 * Prüfung ob diese Klasse schon existiert. Und Methoden dieser Klasse
	 * sollen nur über diese statische Methode aufgerufen werden
	 * 
	 * @return profilMapper
	 * @see profilMapper
	 */
	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}

		return nutzerMapper;
	}

	/**
	 * Einfuegen eines <code>Nutzer</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	 * berichtigt.
	 *
	 * @param n
	 *            das zu speichernde Objekt
	 * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 * 
	 * @author thies
	 */
	public Nutzer insert(Nutzer n) {

		// DbConnection herstellen
		Connection con = DBConnection.connection();

		/**
		 * Try und Catch gehören zum Exception Handling Try = Versuch erst dies
		 * Catch = Wenn Try nicht geht versuch es so ..
		 */
		try {
			// Anglegen eines leeren Statements
			Statement stmt = con.createStatement();

			// Statement als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Nutzer ");

			// RÜckgabe enthält nur ein Tupel
			if (rs.next()) {

				// Enthält den maximalen, nun um 1 inkrementierten
				// Primärschlüssel
				n.setId(rs.getInt("maxid") + 1);

				PreparedStatement prestmt = con.prepareStatement("INSERT INTO Nutzer (id, email " + ") VALUES('"
						+ n.getId() + "', '" + n.getEmailAddress() + "')");

				// INSERT-Statement ausf�hren
				prestmt.execute();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return n;
	}

	public void delete(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			// SQL Statement anlegen
			PreparedStatement prestmt = con.prepareStatement("DELETE FROM Nutzer WHERE id=" + n.getId());

			// Statement als Query an die DB schicken
			prestmt.execute();
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Anhand dieser Methode werden Nutzer die sich einloggen mit ihrer Email
	 * Identifiziert und zurückgegeben.
	 * 
	 * @param emailadress
	 * @return
	 */
	public Nutzer findUserByGMail(String emailadress) {

		Connection con = DBConnection.connection();
		/**
		 * Try und Catch geh�ren zum Exception Handling Try = Versuch erst dies
		 * Catch = Wenn Try nicht geht versuch es so ..
		 */

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Nutzer WHERE email=?");
			stmt.setString(1, emailadress);

			/**
			 * Statement ausf�llen und an die DB senden
			 */
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Nutzer n = new Nutzer();
				n.setId(rs.getInt("id"));
				n.setEmailAddress(rs.getString("email"));

				return n;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
}

// /**
// * Auslesen des dazugehörigen Kontakt Objekts des jeweiligen Nutzers der sich
// * in das System einloggt.
// * @param n
// * @return
// */
// public Kontakt getNutzerAsKontakt(Nutzer n) {
//
// /*
// * Wir greifen auf den <code>KontaktMapper</code> zurück
// * der uns zum Nutzer der sich einloggt den passenden Kontakt (Sich selbst)
// * zurückgibt.
// */
//
// return KontaktMapper.kontaktMapper().findKontaktById()
// }
