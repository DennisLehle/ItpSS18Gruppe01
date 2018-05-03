package de.hdm.itprojektss18.team01.sontact.server.db;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


/**
 * Mapper-Klasse, die <code>Eigenschaft</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verf�gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gel�scht werden k�nnen. Das Mapping ist bidirektional. D.h., Objekte k�nnen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @author Thies
 */
public class EigenschaftMapper {
	
	/**
	 * Die Klasse EigenschaftMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	 * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @see eigenschaftMapper()
	 */
	public static EigenschaftMapper eigenschaftMapper = null;
	
	/**
	 * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit "new" 
	 * neue Instanzen dieser Klasse zu erzeugen
	 */
	protected EigenschaftMapper() {
		
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>EigenschaftMapper.eigenschaftMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine einzige
	 * Instanz von <code>EigenschaftMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> EigenschaftMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	 * 
	 * @return DAS <code>EigenschaftMapper</code>-Objekt.
	 * @see eigenschaftMapper
	 */
	public static EigenschaftMapper eigenschaftMapper() {
		if(eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}
		
		return eigenschaftMapper;
	}
	
	
	/**
	 * Auslesen einer Eigenschaft anhand Id.
	 * 
	 * @param id
	 * @return Eigenschaft
	 * @throws SQLException
	 */
	public Eigenschaft findEigenschaftById(int id) throws SQLException {
		// DBConnection holen
		Connection con = (Connection) DBConnection.connection();
		
		// SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT id, bezeichnung FROM Eigenschaft where id = " + id);
		
		// Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		// Ergebnistuppel in Objekt umwandeln 
		Eigenschaft eigenschaft = new Eigenschaft();
		while (result.next()){
			eigenschaft.setId(result.getInt("id"));
			eigenschaft.setBezeichnung(result.getString("bezeichnung"));
		}

		return eigenschaft;
	}
	
	/**
	 * Auslesen aller Eigenschaften welche zur Verf�gung stehen. 
	 * @return Eigenschaften
	 * @throws SQLException
	 */
	public Vector <Eigenschaft> findEigenschaftAuswahl() throws SQLException {
		// DBConnection holen
		Connection con = (Connection) DBConnection.connection();
		
		// SQL Statement anlegen
		PreparedStatement prestmt = con.prepareStatement(
				"SELECT * FROM Eigenschaft");
		
		// Statement als Query an die DB schicken
		ResultSet result = prestmt.executeQuery();
		
		// Vector anlegen
		Vector <Eigenschaft> eigenschaften = new Vector <Eigenschaft>();
		
		// Ergebnistuppeln in Objekt umwandeln 
		while (result.next()) {
			Eigenschaft eigenschaft = new Eigenschaft();
			eigenschaft.setId(result.getInt("id"));
			eigenschaft.setBezeichnung(result.getString("bezeichnung"));
			
			eigenschaften.add(eigenschaft);
		}
		
		return eigenschaften;
	}
	

	// findEigenschaftForAuspraegung(): notiz fuer mich: Join auf Auspraegung welche die ID fuer die Eigenschaft enthaelt)

	public Eigenschaft findEigenschaftForAuspraegung() {
		 return null;
	}
	
}
