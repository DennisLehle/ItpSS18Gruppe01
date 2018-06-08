package de.hdm.itprojektss18.team01.sontact.server.db;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Window;

import de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;


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
	public void suche() {
		EditorServiceImpl editor = new EditorServiceImpl();
	Nutzer n = new Nutzer();
	n.setId(1);
n.setEmailAddress("kan.kup@gmail.com");
		System.out.println(editor.getKontakteBySuche("name", "petra", n));
		
		
	}
	
	
	
	
}
