package de.hdm.itprojektss18.team01.sontact.server.db;

import java.sql.Connection;

/** 
 * *Die Mapper-Klasse <code>Berechtigung</code> gehört der Datenbankschicht
 *  an und stellt eine objektorientierten Sicht der Applikationslogik auf 
 *  die relationale Datenbank dar. Die Mapperklasse verfügt über Methoden 
 *  die es ermöglichen Objekte zu erstellen, bearbeiten, suchen oder zu 
 *  löschen (insert, update, search, delete). Zusätzlich können aus 
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
	 * Geschuetzer Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BerechtigungMapper () {
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BerechtigungMapper.berechtigungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
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
	 * @param objekt, das ausgewählte BusinessObjekt, welches einem Nutzer zugewiesen wird. 
	 * @return Nutzer-Objekt, der eine Berechtigung auf ein Objekt erhält
	 */
	
	

}
