package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;

/** 
 * *Die Mapper-Klasse <code>Berechtigung</code> geh�rt der Datenbankschicht
 *  an und stellt eine objektorientierten Sicht der Applikationslogik auf 
 *  die relationale Datenbank dar. Die Mapperklasse verf�gt �ber Methoden 
 *  die es erm�glichen Objekte zu erstellen, bearbeiten, suchen oder zu 
 *  l�schen (insert, update, search, delete). Zus�tzlich k�nnen aus 
 *  Datenbank-Tupel Java-Instanzen erzeugt werden und umgekehrt 
 *  --> bidirektional.
 * 
 * @author Miescha
 *
 */

public class BerechtigungMapper {
	
/**
 * Die Klasse BerechtigungMapper wird nur einmalig instanziiert, weshalb 
 * von der <b>Singleton</b>-Eigenschaft gesprochen wird. Ein Singleton, gilt als 
 * Entwurfsmuster uns stellt sicher, dass von einer Klasse genau ein Objekt 
 * existiert. 
 * <p>
 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer 
 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert
 * die einzige Instanz dieser Klasse.
 * 
 * @see berechtigungMapper()
 * 
 *  @author Thies
 * 
 */
	private static BerechtigungMapper berechtigungMapper = null;
	
	/**
	 * Geschuetzer Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BerechtigungMapper () {
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BerechtigungMapper.berechtigungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine einzige
	 * Instanz von <code>BerechtigungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BerechtigungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	 * 
	 * @return DAS <code>BerechtigungMapper</code>-Objekt.
	 * @see BerechtigungMapper
	 */
	public static BerechtigungMapper berechtigungMapper() {
		if (berechtigungMapper == null) {
			berechtigungMapper = new BerechtigungMapper();
		}
		return berechtigungMapper;
	}
	
	/**
	 * Ein Berechtigung-Objekt der Datenbank hinzufuegen.
	 * 
	 * @param berechtigung, mit einem zugeteilten <code>enum</code>, dieser die 
	 * 		  berechtigungsstufe aufweist    
	 * @param objekt, das ausgew�hlte BusinessObjekt, welches einem Nutzer zugewiesen wird. 
	 * @return Nutzer-Objekt, der eine Berechtigung auf ein Objekt erh�lt
	 */
	
	

}
