package de.hdm.itprojektss18.team01.sontact.server;


import org.junit.jupiter.api.Test;
import com.google.gwt.junit.client.GWTTestCase;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.KontaktlisteKontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Test der Impl Klasse <code>EditorServiceImpl</code> Methoden.
 * 
 * Hinweis:
 * Um Methoden der Impl <code>EditorServiceImpl</code> prÃ¼fen zu kÃ¶nnen.
 * Muss in jeder Methode vor dem return Statement (kurz vor ENDE der Methode) -> die <code>init()</code> Methode reingeschrieben werden.
 * Sonst wird als Ergebnis eine NullPointerException ausgworfen.
 * 
 * Es reicht nur das simple reinschreiben von -> init();
 * ZusÃ¤tlich muss die Datenbank eingerichtet sein damit alles durchlÃ¤uft bist zum DB Eintrag.
 * 
 * ACHTUNG:
 * Bitte aktuelle Datenbank aus dem Drive entnehmen (Sontact_V2)
 * 
 * 
 * Nutzung von JUnit kurze ErklÃ¤rung:
 * - Jede Methode die getestet wird hat ein "@Test" Ã¼ber dem Methodenkopf stehen. 
 * - Methoden sind grundsÃ¤tzlich void zu deklarieren.
 * - Test ausfÃ¼hren -> rechts Klick auf <code>EditorServiceImplTest</code> -> Run as -> GWT JUnit Test
 * 
 * FÃ¼r mehr Information GWT StockWatcher JUnit Test nachschauen.
 *   
 * @author Dennis Lehle
 */
class EditorServiceImplTest extends GWTTestCase {

	/**
	 * Methode muss implementiert werden um auf die Klasse welche getestet werden soll zugreifen zu kÃ¶nnen.
	 * Es wird das Module der gwt.xml Datei eingetragen.
	 */
	public String getModuleName() {
		return "de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl";
	}
	
	/**
	 * Dient zur PrÃ¼fung ob tests funktionieren.
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
	 * Test Case fÃ¼r die Erstellung eines Nutzers.
	 * CHECK
	 */
	
	public void createNutzer() {
		//Variable anlegen die Ã¼bergeben werden soll.
		String email = "kan.kup@gmail.com";
		String email1 = "mieschaafshar@gmail.com";
		//Verbindung zur Test Klasse herstellen 
		EditorServiceImpl editor = new EditorServiceImpl();
		//PrÃ¼fen ob die Verbindung steht. Kann auch nur bis hier her durchgefÃ¼hrt werden um JUnit Test positiv zu sehen.
		assertNotNull(editor);
		//Variable Ã¼bergeben die EingefÃ¼gt werden soll/sollen.
		editor.createNutzer(email);
		editor.createNutzer(email1);
	}
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Kontakt-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Test Case fÃ¼r die Erstellung eines Kontakts.
	 * Anmerkung: Wird der Kontakt in die Default-Kontaktliste gespeichert?
	 * CHECK
	 */
	
	public void createKontakt() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		editor.createKontakt("Max", "Mustermann", n);
		editor.createKontakt("Petra", "Pfiffig", n);
		editor.createKontakt("Lisa", "Lustig", n);
	}
	
	/**
	 * Test Case fÃ¼r das Updaten eines Kontakts.
	 * CHECK
	 */
 	
	public void saveKontakt() {
	
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(3);
		k.setVorname("Lisa");
		k.setNachname("Lustig");
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
	 * Test Case fuer das Löschen einer Auspraegung
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
	 * CHECK MELANIE
	 */
	
	public void createEigenschaft() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.createEigenschaft("Lieblingssport");			
	}
	
	/**
	 * Test Case fuer das Updaten einer Eigenschaft
	 * CHECK MELANIE
	 * Update der Bezeichnung + Ausgabe "Updated"
	 */
	

	public void saveEigenschaft() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Eigenschaft e = new Eigenschaft();
		e.setId(23);
		e.setBezeichnung("Lieblingsfarbe");
		
		editor.saveEigenschaft(e);
		
		
	}
	
	/**
	 * Test Case fuer das Loeschen einer Eigenschaft
	 * CHECK MELANIE
	 */

	public void deleteEigenschaft() {

		Eigenschaft e = new Eigenschaft();
		e.setId(23);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteEigenschaft(e);			
	}
	
	
	/**
	 * Test Case fuer das Erhalten einer Eigenschaft anhand ihrer ID
	 * Trotzdem testen, auch wenn nicht benötigt? So haben wir Gewissheit, dass es funktioniert.
	 * UNCHECK
	 */
	
//	public Eigenschaft findEigenschaftById() {
//		
//		EditorServiceImpl editor = new EditorServiceImpl();
//		
//	}
	
	/**
	 * Test Case fuer das Erhalten der Eigenschaften mit ID 1-17
	 * Trotzdem testen, auch wenn nicht benötigt? So haben wir Gewissheit, dass es funktioniert.
	 * UNCHECK
	 */
	
//	public Eigenschaft findEigenschaftByAuswahl() {
//		
//		EditorServiceImpl editor = new EditorServiceImpl();
//		
//	}
	
	/**
	 * Test Case fuer das Erhalten einer Eigenschaft, die einer einer Auspraegung eines Kontaktes zugeordnet ist.
	 * Trotzdem testen, auch wenn nicht benötigt? So haben wir Gewissheit, dass es funktioniert.
	 * UNCHECK
	 */
	
//	public Eigenschaft findEigenschaftForAuspraegung() {
//		
//		EditorServiceImpl editor = new EditorServiceImpl();
//		
//	}
	
	
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
		e.setBezeichnung("Schuhgröße");
		e.setId(18);
		
		//Anlegen der Auspraegung
		Auspraegung a = new Auspraegung();
		a.setWert("38");
		a.setEigenschaftId(e.getId());
		a.setKontaktId(3);
		a.setOwnerId(1);
		
		
		//Anlegen der dazugehörigen Auspraegung		
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
		
		//Ich erstelle eine Kontaktliste und übergebe einen Nutzer
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
	
	public void addKontaktToKontaktliste() {

		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(2);
		
		Kontakt k = new Kontakt();
		k.setId(2);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.addKontaktToKontaktliste(kl, k);
		
	}
	
	
	/**
	 * Test Case fuer das Entfernen eines Kontakts aus einer Kontaktliste
	 *
	 */

	
	public void removeKontaktFromKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
				
		Kontaktliste kl = new Kontaktliste();
		kl.setId(3);
		
		Kontakt k = new Kontakt();
		k.setId(1);

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.removeKontaktFromKontaktliste(kl, k);
		
	}
	
	/**
	 * Test Case fuer das Entfernen eines Kontakts aus einer Kontaktliste
	 *
	 */
	
	public void getKontaktlistenByOwner() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.getKontaktlistenByOwner(n);
	}
	
	
	/**
	 * Test Case fuer das Löschen eines Kontakts
	 * Muss noch angepasst werden
	 * UNCHECK
	 */
	
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
	
	/**
	 * Berechtigungseintrag in der Tabelle Berechtigung für Objekt K, KL oder A
	 * IMPL-Methode createBerechtigung();
	 */
	// CHECK
	
	public void shareObjectKontakt() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Nutzer n1 = new Nutzer();
		Nutzer n2 = new Nutzer();
			n1.setId(1);
			n2.setId(2);
				
		Kontaktliste kl = new Kontaktliste();
			kl.setId(1);
			kl.setTitel("Geschaeft");
			kl.setOwnerId(n1.getId());
		
		Kontakt k = new Kontakt();
			k.setId(1);
		
		Auspraegung a1 = new Auspraegung();
		Auspraegung a2 = new Auspraegung();
			a1.setId(7);
			a2.setId(8);
		
		editor.shareObject(n1.getId(), n2.getId(), k.getId(), k.getType());		
	}
	
	/**
	 * Test ShareKontakt:
	 * IMPL-Methode shareObject();
	 */
	// CHECK
	
	public void shareObjectListe() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Nutzer n1 = new Nutzer();
		Nutzer n2 = new Nutzer();
			n1.setId(1);
			n2.setId(2);
			
		Kontaktliste kl = new Kontaktliste(); 
			kl.setId(2);
			kl.setOwnerId(n1.getId());	
			
		Kontakt k1 = new Kontakt(); 
		k1.setId(3);
			k1.setOwnerId(n1.getId());
		
		Kontakt k2 = new Kontakt(); 
			k2.setId(2);
			k2.setOwnerId(n1.getId());

		KontaktlisteKontakt klk1 = new KontaktlisteKontakt(); 
			klk1.setKontaktlisteId(kl.getId());
			klk1.setKontaktId(k1.getId());
		
		KontaktlisteKontakt klk2 = new KontaktlisteKontakt(); 
			klk2.setKontaktlisteId(kl.getId());
			klk2.setKontaktId(k2.getId());

		Auspraegung a1 = new Auspraegung(); 
			a1.setId(7);
			a1.setKontaktId(k1.getId());
		Auspraegung a2 = new Auspraegung(); 
			a2.setId(8);
			a1.setKontaktId(k1.getId());
			
		editor.shareObject(n1.getId(), n2.getId(), kl.getId(), kl.getType());
	}

	/**
	 * Test Delete Berechtigung: 
	 * IMPL-Methode deleteBerechtigung();
	 */
	// CHECK
	@Test
	public void deleteBerechtigung() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Nutzer n1 = new Nutzer();
		Nutzer n2 = new Nutzer();
			n1.setId(1);
			n2.setId(2);
			
		Kontaktliste kl = new Kontaktliste(); 
			kl.setId(2);
			kl.setOwnerId(n1.getId());	
			
		Kontakt k1 = new Kontakt(); 
		k1.setId(3);
			k1.setOwnerId(n1.getId());
		
		Kontakt k2 = new Kontakt(); 
			k2.setId(1);
			k2.setOwnerId(n1.getId());

		KontaktlisteKontakt klk1 = new KontaktlisteKontakt(); 
			klk1.setKontaktlisteId(kl.getId());
			klk1.setKontaktId(k1.getId());
		
		KontaktlisteKontakt klk2 = new KontaktlisteKontakt(); 
			klk2.setKontaktlisteId(kl.getId());
			klk2.setKontaktId(k2.getId());

		Auspraegung a1 = new Auspraegung(); 
			a1.setId(7);
			a1.setKontaktId(k1.getId());
		Auspraegung a2 = new Auspraegung(); 
			a2.setId(8);
			a1.setKontaktId(k1.getId());
			
		Berechtigung b = new Berechtigung(); 
		b.setOwnerId(n1.getId());
		b.setReceiverId(n2.getId());
		b.setObjectId(kl.getId());
		b.setType(kl.getType());
			
		editor.deleteBerechtigung(b);
	}
	
	

	
	


}