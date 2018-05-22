package de.hdm.itprojektss18.team01.sontact.server;


import org.junit.jupiter.api.Test;
import com.google.gwt.junit.client.GWTTestCase;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.KontaktlisteKontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/*
 * Test
 */

/**
 * Test der Impl Klasse <code>EditorServiceImpl</code> Methoden.
 * 
 * Hinweis:
 * Um Methoden der Impl <code>EditorServiceImpl</code> prüfen zu können.
 * Muss in jeder Methode vor dem return Statement (kurz vor ENDE der Methode) -> die <code>init()</code> Methode reingeschrieben werden.
 * Sonst wird als Ergebnis eine NullPointerException ausgworfen.
 * 
 * Es reicht nur das simple reinschreiben von -> init();
 * Zusätlich muss die Datenbank eingerichtet sein damit alles durchläuft bist zum DB Eintrag.
 * 
 * ACHTUNG:
 * Bitte aktuelle Datenbank aus dem Drive entnehmen (Sontact_V2)
 * 
 * 
 * Nutzung von JUnit kurze Erklärung:
 * - Jede Methode die getestet wird hat ein "@Test" über dem Methodenkopf stehen. 
 * - Methoden sind grundsätzlich void zu deklarieren.
 * - Test ausführen -> rechts Klick auf <code>EditorServiceImplTest</code> -> Run as -> GWT JUnit Test
 * 
 * Für mehr Information GWT StockWatcher JUnit Test nachschauen.
 *   
 * @author Dennis Lehle
 */
class EditorServiceImplTest extends GWTTestCase {

	/**
	 * Methode muss implementiert werden um auf die Klasse welche getestet werden soll zugreifen zu können.
	 * Es wird das Module der gwt.xml Datei eingetragen.
	 */
	public String getModuleName() {
		return "de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl";
	}
	
	/**
	 * Dient zur Prüfung ob tests funktionieren.
	 */
	@Test
	public void testSimple() {
		assertTrue(true);
	}
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Nutzer-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Test Case für die Erstellung eines Nutzers.
	 * CHECK
	 */
	
	public void createNutzer() {
		//Variable anlegen die übergeben werden soll.
		String email = "kan.kup@gmail.com";
		String email1 = "mieschaafshar@gmail.com";
		//Verbindung zur Test Klasse herstellen 
		EditorServiceImpl editor = new EditorServiceImpl();
		//Prüfen ob die Verbindung steht. Kann auch nur bis hier her durchgeführt werden um JUnit Test positiv zu sehen.
		assertNotNull(editor);
		//Variable übergeben die Eingefügt werden soll/sollen.
		editor.createNutzer(email);
		editor.createNutzer(email1);
	}
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Kontakt-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Test Case für die Erstellung eines Kontakts.
	 * Anmerkung: Wird der Kontakt in die Default-Kontaktliste gespeichert?
	 * CHECK
	 */
	
	public void createKontakt() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		//Ich erstelle einen Kontakt und übergebe einen Nutzer in dem Fall mich selbst.
		//editor.createKontakt("Melanie", "Musterchen", n);
		editor.createKontakt("Petra", "Pfiffig", n);
		editor.createKontakt("Lisa", "Lustig", n);
	}
	
	/**
	 * Test Case für das Updaten eines Kontakts.
	 * CHECK
	 */
	 	
	public void saveKontakt() {
	
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(3);
		k.setVorname("Lisa");
		k.setNachname("Luftig");
		k.setOwnerId(1);
	
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.saveKontakt(k);
	}
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Auspraegungs-Objekte Test
	   * ***************************************************************************
	   */
		
		/**
		 * Test Case fuer das Erstellen einer Auspraegung
		 * CHECK
		 */
	
	public void createAuspraegung() {
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();

			
		//Max Mustermann
		editor.createAuspraegung("0176/ 232222", 6 , 1 , n);
		
		editor.createAuspraegung("0172/ 333333", 6 , 1 , n);
		
		editor.createAuspraegung("maxmustermann@gmx.de", 14 , 1 , n);
		
	
		//Petra Pfiffig
		
		editor.createAuspraegung("0178/ 52555", 6 , 2 , n);
		
		editor.createAuspraegung("0172/ 44444", 6 , 2 , n);
		
		editor.createAuspraegung("petrapfiffig@web.de", 14 , 2 , n);
				
		//Lisa Luftig
		
		editor.createAuspraegung("0152/ 71777", 6 , 3, n);
		
		editor.createAuspraegung("0152/ 11111", 6 , 3 , n);
		
		editor.createAuspraegung("lisaluftig@yahoo.de", 14 , 3 , n);
	}
	
	/**
	 * Test Case fuer das Bearbeiten einer Auspraegung
	 * CHECK
	 */
	
	public void saveAuspraegung() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Auspraegung a = new Auspraegung();
		a.setId(9);
		a.setWert("lisaluftig@outlook.de");
		a.setKontaktId(3);
		a.setEigenschaftId(14);
		a.setOwnerId(1);

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.saveAuspraegung(a);
	}
	
	/**
	 * Test Case fuer das L�schen einer Auspraegung
	 * CHECK
	 */
	
	public void deleteAuspraegung() {
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Auspraegung a = new Auspraegung();
		a.setId(9);
		a.setWert("lisaluftig@outlook.de");
		a.setKontaktId(3);
		a.setEigenschaftId(14);
		a.setOwnerId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteAuspraegung(a);
	}
	
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Eigenschaft-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Test Case fuer das Erstellen einer Eigenschaft
	 * CHECK
	 */
	
	public void createEigenschaft() {

		
		EditorServiceImpl editor = new EditorServiceImpl();

		
		editor.createEigenschaft("Lieblingssport");			
	}
	
	/**
	 * Test Case fuer das Loeschen einer Eigenschaft
	 * UNCHECK
	 */

	 
	public void deleteEigenschaft() {
		
		Eigenschaft e = new Eigenschaft();
		e.setId(18);
		//e.setBezeichnung("Lieblingssport");
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteEigenschaft(e);			
	}
	
	
	/**
	 * Test Case fuer das Erstellen einer Eigenschaft und einer Auspraegung
	 * CHECK
	*/
	
	public void createAuspraegungForNewEigenschaft() {
		Nutzer n = new Nutzer();
		n.setId(1);

		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		//Anlegen der Eigenschaft
		Eigenschaft e = new Eigenschaft();
		e.setBezeichnung("Schuhgr��e");
		e.setId(18);
		
		//Anlegen der Auspraegung
		Auspraegung a = new Auspraegung();
		a.setWert("38");
		a.setEigenschaftId(e.getId());
		a.setKontaktId(3);
		a.setOwnerId(1);
		
		
		//Anlegen der dazugeh�rigen Auspraegung		
		editor.createAuspraegungForNewEigenschaft(e, a, n);
		}
	
		
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Kontaktlisten-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Test Case fuer das Erstellen einer Kontaktliste
	 * CHECK
	 */
	
	public void createKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		//Ich erstelle eine Kontaktliste und �bergebe einen Nutzer
		editor.createKontaktliste("Familie", n);
		editor.createKontaktliste("Freunde", n);
	}
	
	/**
	 * Test Case fuer das Erstellen einer Kontaktliste
	 * CHECK
	 */
	
	public void saveKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(1);
		kl.setTitel("Geschaeft");

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.saveKontaktliste(kl);
	}
	

/**
 * Test Case fuer die Zuweisung eines Kontakts zu einer Kontaktliste
 * 
 */
// OFFEN //
	public void addKontaktToKontaktliste() {
	
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(2);
		
		KontaktlisteKontakt klk = new KontaktlisteKontakt();
		klk.setKontaktlisteid(kl.getId());
		klk.setKontaktid(k.getId());
		
		EditorServiceImpl editor = new EditorServiceImpl();

		editor.addKontaktToKontaktliste(kl, k);
	}
	
	
	/**
	 * Test Case fuer das Entfernen eines Kontakts aus einer Kontaktliste
	 *
	 */
// OFFEN //
	public void removeKontaktFromKontaktliste() {
		
		Kontakt k = new Kontakt();
		k.setId(2);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(1);
		
		KontaktlisteKontakt klk = new KontaktlisteKontakt();
		klk.setKontaktlisteid(kl.getId());
		klk.setKontaktid(k.getId());
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.removeKontaktFromKontaktliste(kl, k);
		
	}
	
	/**
	 * Test Case fuer das Entfernen eines Kontakts aus einer Kontaktliste
	 *
	 */
// OFFEN //
	public void getKontaktlistenByOwner() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.getKontaktlistenByOwner(n);
	}
	
	
	/**
	 * Test Case fuer das L�schen eines Kontakts
	 * Muss noch angepasst werden
	 * UNCHECK
	 */
// OFFEN // 	
	public void removeKontakt() {
		Kontakt k = new Kontakt();
		k.setId(3);

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.removeKontakt(k);
	}
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Berechtigungs-Objekte Test
	   * ***************************************************************************
	   */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}