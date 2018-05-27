package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * * Die Mapper-Klasse stellt Methoden zur VerfÃ¼gung die
 * <code>Profil</code>-Objekte auf eine relationale Datenbank abbildet. Die
 * Methoden bieten die MÃ¶glichkeit Objekte aus der Datenbank zu suchen, sie zu
 * erzeugen und zu lÃ¶schen. Das Mapping ist bidirektional. D.h., Objekte
 * kÃ¶nnen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * Diese Mapper-Klasse besitzt Singleton-Eigenschaften und wird nur einmal
 * mithilfe der Methode <code>profilMapper()</code> initialisiert. Der
 * Konstruktor ist bewusst durch <code>protected</code> geschÃ¼tzt, damit nur
 * eine einzige Instanz der Klasse exisitert.
 * 
 * @author Thies
 *
 */
public class NutzerMapper {

	private static NutzerMapper nutzerMapper = null;

	/**
	 * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit "new" neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerMapper() {
	};

	/**
	 * PrÃ¼fung ob diese Klasse schon existiert. Und Methoden dieser Klasse
	 * sollen nur Ã¼ber diese statische Methode aufgerufen werden
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
	
	public Nutzer insert(Nutzer n){
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		
		//Query für die Abfrage der hoechsten ID (Primärschlüssel) in der Datenbank
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM nutzer";
		
		
		//Query für den Insert
		String insertSQL = "INSERT INTO nutzer (id, email) VALUES (?,?)";		
		
		
		
		try {
			
			con = DBConnection.connection(); 
			stmt = con.prepareStatement(maxIdSQL);
			

			//MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();
			
			
			//...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
		    if(rs.next()){
		    	n.setId(rs.getInt("maxId")+1);
		    }	   
		    
		    	
			//Jetzt erfolgt der Insert
		    stmt = con.prepareStatement(insertSQL);
		    
		    
		    //Setzen der ? Platzhalter als Values
		    stmt.setInt(1, n.getId());
		    stmt.setString(2, n.getEmailAddress());
		    
		    
		    //INSERT-Query ausführen
		    stmt.executeUpdate();
		    
		    
		} catch (SQLException e2) {
			e2.printStackTrace();
			}			
		
		return n;
	}	
	
	
	/**
	 * Löschen eines NutzersObjekts aus der Datenbank.
	 * @param n
	 */
	
	public void delete (Nutzer n) {
		
		Connection con = null; 
		PreparedStatement stmt = null;
		
		String deleteSQL = "DELETE FROM nutzer WHERE id=?";
		
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
	 * Anhand dieser Methode werden Nutzer die sich einloggen mit ihrer Email
	 * Identifiziert und zurÃ¼ckgegeben.
	 * 
	 * @param emailadress
	 * @return
	 */
	
	public Nutzer findUserByGMail(String email) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		String selectByKey = "SELECT * FROM nutzer WHERE email=?";
		
		
		try {
			
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setString(1, email);
			
			//Execute SQL Statement
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				//Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();
				
				//Setzen der Attribute den Datensätzen aus der DB entsprechend
				n.setId(rs.getInt(1));
				n.setEmailAddress(rs.getString(2));
				
				return n;
			}
		}
		
		catch (SQLException e2) {
			
			e2.printStackTrace();
			return null;
		}
		
		return null;
	}
	
}

// /**
// * Auslesen des dazugehÃ¶rigen Kontakt Objekts des jeweiligen Nutzers der sich
// * in das System einloggt.
// * @param n
// * @return
// */
// public Kontakt getNutzerAsKontakt(Nutzer n) {
//
// /*
// * Wir greifen auf den <code>KontaktMapper</code> zurÃ¼ck
// * der uns zum Nutzer der sich einloggt den passenden Kontakt (Sich selbst)
// * zurÃ¼ckgibt.
// */
//
// return KontaktMapper.kontaktMapper().findKontaktById()
// }
