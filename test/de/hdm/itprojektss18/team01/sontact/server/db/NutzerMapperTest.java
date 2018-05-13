package de.hdm.itprojektss18.team01.sontact.server.db;

import org.junit.jupiter.api.Test;

import com.google.gwt.junit.client.GWTTestCase;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Diese Klasse dient zum Test der Mapper Klasse <code>NutzerMapper</code>
 * Für mehr Informationen zu JUnit...
 * 
 * @see EditorServiceImplTest
 * @author Dennis Lehle
 */
public class NutzerMapperTest extends GWTTestCase {

	/**
	 * Methode muss implementiert werden um auf Modul festlegen zu können.
	 * Hier bei den Mappern benötigen wir kein Modul da wir direkt in der untersten Schicht arbeiten.
	 * Eintragungen/Auslesungen gehen hier direkt ohne Umwege zur Datenbank.
	 * 
	 * Daher null...
	 */
	public String getModuleName() {
		return null;
	}

	/**
	 * Dient zur Prüfung ob tests funktionieren.
	 */
	@Test
	public void testSimple() {
		assertTrue(true);
	}
	
	/**
	 * Einfügen eines Nutzers in die Datenbank - Test
	 * CHECK
	 */
	@Test
	public void insertNutzer() {
		Nutzer n = new Nutzer();
		n.setId(1);
		n.setEmailAddress("helloFresh@hdm.de");
		
		NutzerMapper nmapper = new NutzerMapper();
		nmapper.insert(n);
		
	}
	
	
	
	
}
