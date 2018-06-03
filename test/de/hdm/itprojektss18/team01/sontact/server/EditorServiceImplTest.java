package de.hdm.itprojektss18.team01.sontact.server;


import org.junit.jupiter.api.Test;

import com.google.gwt.junit.client.GWTTestCase;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


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
 * Bitte aktuelle Datenbank aus dem Drive entnehmen (sontact_V5)
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
	 * CHECK MELANIE
	 */
	
	public void createNutzer() {
		
		//Variable anlegen die übergeben werden soll.
//		String email = "kan.kup@gmail.com";
//		String email1 = "mieschaafshar@gmail.com";
		String email2 = "mmiedl93@gmail.com";
				
		//Verbindung zur Test Klasse herstellen 
		EditorServiceImpl editor = new EditorServiceImpl();
		
		//Prüfen ob die Verbindung steht. Kann auch nur bis hier her durchgeführt werden um JUnit Test positiv zu sehen.
		assertNotNull(editor);
		
		//Variable übergeben die Eingefügt werden soll/sollen.
//		editor.createNutzer(email);
//		editor.createNutzer(email1);
		editor.createNutzer(email2);
	}
	
	/**
	 * Test Case für die L�schung eines Nutzers aus unserer Datenbank.
	 * UNCHECK --> KontaktlisteKontaktMapper nicht ber�cksichtigt 
	 */

	public void deleteNutzer() {
		
		Nutzer n = new Nutzer();
		n.setId(3);
			
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteNutzer(n);
		
	}
	
	public void share() {
		EditorServiceImpl editor = new EditorServiceImpl();
	
		editor.shareObject(1, 2, 2, 'k');
	}
	
	
	/**
	 * Test Case für das Auslesen eines Nutzers anhand seiner GMail Adresse.
	 * CHECK MELANIE
	 */

	public void getUserByGmail() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getUserByGMail("mmiedl93@gmail.com"));
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
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		editor.createKontakt("Test", "Melanie", n);
//		editor.createKontakt("Petra", "Pfiffig", n);
//		editor.createKontakt("Lisa", "Lustig", n);
	}
	
	
	public void createKontaktRegistrierung() {
		
		Nutzer n = new Nutzer();
		n.setId(3);
		n.setEmailAddress("mmiedl93@gmail.com");
		
		EditorServiceImpl editor = new EditorServiceImpl();
		editor.createKontaktRegistrierung("Melanie", "Miedl", n);
		
	}
	
	
	public void createKontakt1() {
		EditorServiceImpl editor = new EditorServiceImpl();
		Kontakt k = new Kontakt();
		k.setVorname("Kevin");
		k.setNachname("Batista");
		
		Nutzer n = new Nutzer();
		n.setId(5);
		n.setEmailAddress("helloFresh@hdm.de");
		
	
		editor.createKontakt("Kevin", "Batista", n);
		
		
	}

	
	/**
	 * Test Case für das Updaten eines Kontakts.
	 * CHECK
	 */
 
	public void saveKontakt() {
	
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(5);
		k.setVorname("Hansi");
		k.setNachname("Greteli");
		k.setOwnerId(1);
	
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.saveKontakt(k);
	}
	
	
	public void getAllKontakteByOwner() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllKontakteByOwner(n));
		
		
	}
	
	
	public void getKontaktById() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getKontaktById(3));
	}
	

	public void getKontaktByName() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getKontakteByName("petra", n));
		
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
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();

			
//		//Max Mustermann
//		editor.createAuspraegung("0176/ 232222", 6 , 1 , n);
//		
//		editor.createAuspraegung("0172/ 333333", 6 , 1 , n);
//	
//		editor.createAuspraegung("maxmustermann@gmx.de", 14 , 1 , n);
//		
//	
//		//Petra Pfiffig
//		
//		editor.createAuspraegung("0178/ 52555", 6 , 2 , n);
//		
//		editor.createAuspraegung("0172/ 44444", 6 , 2 , n);
//		
//		editor.createAuspraegung("petrapfiffig@web.de", 14 , 2 , n);
//				
//		//Lisa Luftig
//		
//		editor.createAuspraegung("0152/ 71777", 6 , 3, n);
//		
//		editor.createAuspraegung("0152/ 11111", 6 , 3 , n);
//		
//		editor.createAuspraegung("lisaluftig@yahoo.de", 14 , 3 , n);
		
		//Test Melanie
		
		editor.createAuspraegung("0715135858", 5, 4);
		
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
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteAuspraegung(a);
	}
	
	
	/**
	 * Test Case fuer das Auslesen einer Auspraegung anhand ihrer Id
	 * CHECK MELANIE
	 */

	public void getAuspraegungById() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAuspraegungById(8));	
		
	}
	
	/**
	 * Test Case fuer das Auslesen Auspraegungen anhand ihrer KontaktId
	 * CHECK MELANIE
	 */

	public void getAllAuspraegungenByKontakt() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllAuspraegungenByKontakt(3));	
		
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
	 * Trotzdem testen, auch wenn nicht ben�tigt? So haben wir Gewissheit, dass es funktioniert.
	 * CHECK MELANIE
	 */
	
	public void getEigenschaftById() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getEigenschaftById(17));
		
	}
	
	/**
	 * Test Case fuer das Erhalten der Eigenschaften mit ID 1-17
	 * CHECK MELANIE
	 */
	
	public void getEigenschaftAuswahl() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		//Keine Instanziierung von Eigenschaftsobjekten f�r die Ausgabe notwendig.
//		
//		Eigenschaft e = new Eigenschaft();
//		e.setId(3);
//		
//		Eigenschaft e1 = new Eigenschaft();
//		e1.setId(5);
//		
//		Eigenschaft e2 = new Eigenschaft();
//		e2.setId(18);
		
		
		System.out.println(editor.getEigenschaftAuswahl());
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
		
		Kontakt k = new Kontakt();
		k.setId(3);
		
		
		//Anlegen der dazugeh�rigen Auspraegung		
		editor.createAuspraegungForNewEigenschaft(e.getBezeichnung(), a.getWert(), k);
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
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		//Ich erstelle eine Kontaktliste und �bergebe einen Nutzer
		editor.createKontaktliste("Privat", n);
//		editor.createKontaktliste("Freunde", n);
	}
	
	/**
	 * Test Case fuer das Erstellen einer Kontaktliste
	 * CHECK
	 */
	
	public void saveKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(4);
		kl.setTitel("Freizeit");

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.saveKontaktliste(kl);
	}
	
	/**
	 * Test Case fuer das L�schen einer Kontaktliste
	 * CHECK MELANIE
	 */
	
	public void deleteKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(4);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteKontaktliste(kl);
		
	}
	

/**
 * Test Case fuer die Zuweisung eines Kontakts zu einer Kontaktliste
 * CHECK MELANIE
 */

	public void addKontaktToKontaktliste() {

		Nutzer n = new Nutzer();
		n.setId(1);
		
		Kontaktliste kl = new Kontaktliste();
		kl.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.addKontaktToKontaktliste(kl, k);
		
	}
	
	
	/**
	 * Test Case fuer das Entfernen eines Kontakts aus einer Kontaktliste
	 * CHECK MELANIE
	 */

	public void removeKontaktFromKontaktliste() {
		
		Nutzer n = new Nutzer();
		n.setId(1);
				
		Kontaktliste kl = new Kontaktliste();
		kl.setId(1);
		
		Kontakt k = new Kontakt();
		k.setId(2);

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.removeKontaktFromKontaktliste(kl, k);
		
	}
	
	/**
	 * Test Case fuer das Auslesen aller Kontakte eines Owners
	 *
	 */
	
	public void getKontaktlistenByOwner() {
		
		Nutzer n = new Nutzer();
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getKontaktlistenByOwner(n));
	}
	
	
	/**
	 * Test Case fuer das Auslesen einer Kontaktliste anhand ihrer ID
	 * CHECK
	 */

	public void getKontaktlisteById() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getKontaktlisteById(3));
		
	}
	
	/**
	 * Test Case fuer das Auslesen einer Kontaktliste anhand ihrer ID
	 * CHECK
	 */
	
	public void findKontaktlisteByTitel() {
		
		Nutzer n = new Nutzer();
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.findKontaktlisteByTitel(n, "Alle Kontakte"));
		
	}
	
	/**
	 * Test Case fuer das L�schen eines Kontakts
	 * Muss noch angepasst werden
	 * UNCHECK
	 */

	public void removeKontakt() {
		Kontakt k = new Kontakt();
		k.setId(5);

		EditorServiceImpl editor = new EditorServiceImpl();
		
		editor.deleteKontakt(k);
	}
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Anfang: Methoden fuer Berechtigungs-Objekte Test
	   * ***************************************************************************
	   */
	
	/**
	 * Berechtigungseintrag in der Tabelle Berechtigung f�r Objekt K, KL oder A
	 * IMPL-Methode createBerechtigung();
	 */
	// CHECK
	@Test
	public void createBerechtigung() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Nutzer n1 = new Nutzer();
		n1.setId(1);
		
		Nutzer n2 = new Nutzer();
		n2.setId(1);
				
		Kontaktliste kl = new Kontaktliste();
		kl.setId(6);
						
//		Kontaktliste kl1 = new Kontaktliste();
//		kl1.setId(2);
//		kl1.setTitel("Freunde");
//		kl1.setOwnerId(n1.getId());
		
//		Kontakt k = new Kontakt();
//		k.setId(1);
//		
//		Kontakt k1 = new Kontakt();
//		k1.setId(2);
//		
//		Kontakt k2 = new Kontakt();
//		k2.setId(3);
//		
//		Kontakt k3 = new Kontakt();
//		k3.setId(4);
//		
		Kontakt k4 = new Kontakt();
		k4.setId(1);

		
		Auspraegung a1 = new Auspraegung();
		Auspraegung a2 = new Auspraegung();
		a1.setId(1);
		a2.setId(2);
		
		editor.createBerechtigung(n1.getId(), n2.getId(), a1.getId(), a2.getType());
//		editor.createBerechtigung(n1.getId(), n2.getId(), kl1.getId(), kl1.getType());
		
		
//		editor.createBerechtigung(n1.getId(), n2.getId(), k.getId(), k.getType());
//		editor.createBerechtigung(n1.getId(), n2.getId(), k1.getId(), k.getType());
//		editor.createBerechtigung(n1.getId(), n2.getId(), k2.getId(), k.getType());
//		editor.createBerechtigung(n1.getId(), n2.getId(), k3.getId(), k.getType());
		//editor.createBerechtigung(n1.getId(), n2.getId(), k4.getId(), k4.getType());
		
//		editor.createBerechtigung(n1.getId(), n2.getId(), a1.getId(), a1.getType());
//		editor.createBerechtigung(n1.getId(), n2.getId(), a2.getId(), a2.getType());
		
	}
	
	/**
	 * Berechtigungseintrag in der Tabelle Berechtigung f�r Objekt K, KL oder A entfernen.
	 * IMPL-Methode deleteBerechtigung();
	 * CHECK Melanie
	 */
	
//	public void deleteBerechtigung() {
//		
//		Berechtigung b = new Berechtigung();
//		b.setId(8);
//		b.setType('l');
//		
//		EditorServiceImpl editor = new EditorServiceImpl();
//		
//		editor.deleteBerechtigung(b);
//	}
	
	
	/**
	 * Test ShareKontakt:
	 * IMPL-Methode shareObject();
	 */
	// CHECK
@Test
	public void shareObject() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Nutzer n1 = new Nutzer();
		Nutzer n2 = new Nutzer();
<<<<<<< HEAD
		//Yakup
			n1.setId(2);
			//Miescha
			n2.setId(1);
=======
			n1.setId(1);
			n2.setId(2);
>>>>>>> refs/heads/master
			
		Kontaktliste kl = new Kontaktliste(); 
			kl.setId(1);
			kl.setOwnerId(n1.getId());
					
		Kontakt k1 = new Kontakt(); 
<<<<<<< HEAD
		k1.setId(5);
		
	
//		
//		Kontakt k2 = new Kontakt(); 
//			k2.setId(2);
//			k2.setOwnerId(n1.getId());
//
//		KontaktlisteKontakt klk1 = new KontaktlisteKontakt(); 
//			klk1.setKontaktlisteId(kl.getId());
//			klk1.setKontaktId(k1.getId());
//		
//		KontaktlisteKontakt klk2 = new KontaktlisteKontakt(); 
//			klk2.setKontaktlisteId(kl.getId());
//			klk2.setKontaktId(k2.getId());
//
//		Auspraegung a1 = new Auspraegung(); 
//			a1.setId(7);
//			a1.setKontaktId(k1.getId());
//		Auspraegung a2 = new Auspraegung(); 
//			a2.setId(8);
//			a1.setKontaktId(k1.getId());
			
		editor.shareObject(n1.getId(), n2.getId(), k1.getId(), k1.getType());
=======
		Kontakt k2 = new Kontakt();
			k1.setId(1);
			k1.setOwnerId(n1.getId()); 
			k2.setId(4);
			k2.setOwnerId(n2.getId());
			
			Auspraegung a = new Auspraegung();
			Auspraegung a1 = new Auspraegung();
			a.setId(5);
			a.setKontaktId(2);
			a1.setId(4);
			a1.setKontaktId(2);

		editor.shareObject(n2.getId(), n1.getId(), a.getId(), a.getType());
>>>>>>> refs/heads/master
	}

	/**
	 * Test DeleteBerechtigung:
	 * IMPL-Methode deleteBerechtigung();
	 */
	
	public void deleteBerechtigung() {
		EditorServiceImpl editor = new EditorServiceImpl();
		
		Berechtigung b = new Berechtigung();
		b.setId(9);
	
		
//		Nutzer n1 = new Nutzer();
//		Nutzer n2 = new Nutzer();
//			n1.setId(1);
//			n2.setId(2);
//			
//		Kontaktliste kl = new Kontaktliste(); 
//			kl.setId(2);
//			kl.setOwnerId(n1.getId());	
//			
//		Kontakt k1 = new Kontakt(); 
//		k1.setId(3);
//			k1.setOwnerId(n1.getId());
//		
//		Kontakt k2 = new Kontakt(); 
//			k2.setId(2);
//			k2.setOwnerId(n1.getId());
//
//		KontaktlisteKontakt klk1 = new KontaktlisteKontakt(); 
//			klk1.setKontaktlisteId(kl.getId());
//			klk1.setKontaktId(k1.getId());
//		
//		KontaktlisteKontakt klk2 = new KontaktlisteKontakt(); 
//			klk2.setKontaktlisteId(kl.getId());
//			klk2.setKontaktId(k2.getId());
//
//		Auspraegung a1 = new Auspraegung(); 
//			a1.setId(7);
//			a1.setKontaktId(k1.getId());
//		Auspraegung a2 = new Auspraegung(); 
//			a2.setId(8);
//			a1.setKontaktId(k1.getId());
//			
//		Berechtigung b = new Berechtigung();
//		//b.setId(1);
//		b.setOwnerId(n1.getId());
//		b.setReceiverId(n2.getId());
//		b.setObjectId(kl.getId());
//		b.setType('l');
		
		editor.deleteBerechtigung(b);
	}
	
	/**
	 * Die Ausgabe aller Eintr�ge in der Berechtigungstabelle nach der OwnerId
	 * IMPL-Methode getAllBerechtigungenByOwner();
	 * CHECK
	 */
	

	public void getAllBerechtigungenByOwner() {
		Nutzer n = new Nutzer();
		n.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllBerechtigungenByOwner(n.getId()));
		
	}
	
	/**
	 * Die Ausgabe aller Eintr�ge in der Berechtigungstabelle nach der ReceiverId
	 * IMPL-Methode getAllBerechtigungenByReceiver();
	 * CHECK
	 */
	
	public void getAllBerechtigungenByReceiver() {
		Nutzer n = new Nutzer();
		n.setId(1);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllBerechtigungenByReceiver(n.getId()));
		
	}
	

	/**
	 * Test getAllSharedKontakteByOwner:
	 * IMPL-Methode getAllSharedKontakteByOwner();
	 */
	// CHECK
	
	public void getAllSharedKontakteByOwner ()  {			
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllSharedKontakteByOwner(3));
}

	/**
	 * Test getAllSharedKontaktReceiver:
	 * IMPL-Methode getAllSharedKontaktlistenByReceiver();
	 **/
	// CHECK
	
	public void getAllSharedKontakteByReceiver ()  {
			
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllSharedKontakteByReceiver(2));
			
}

	
	
	/**
	 * Test getAllSharedKontaktlistenByOwner:
	 * IMPL-Methode getAllSharedKontaktlistenByOwner();
	 */
	//CHECK
	
	
	public void getAllSharedKontaktlistenByOwner() {
		
		Nutzer n1 = new Nutzer();
			n1.setId(3);
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllSharedKontaktlistenByOwner(n1.getId()));
		
		}
	
	/**
	 * Test getAllSharedKontaktlistenByReceiver:
	 * IMPL-Methode getAllSharedKontaktlistenByReceiver();
	 */
	//CHECK
	
	public void getAllSharedKontaktlistenByReceiver() {
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getAllSharedKontaktlistenByReceiver(2));
		
		}

	
	/**
	 * Test getAllSharedAuspraegungenByKontakt:
	 * IMPL-Methode getAllSharedAuspraegungenByKontakt();
	 */
	
	
	public void getAllSharedAuspraegungenByKontaktAndNutzer() {
		
		Nutzer n = new Nutzer();
		n.setId(2);
		
		Kontakt k = new Kontakt();
		k.setId(4);

		EditorServiceImpl editor = new EditorServiceImpl();
			
		System.out.println(editor.getAllSharedAuspraegungenByKontaktAndNutzer(k, n));
			
	}
	
// Suche: ###############################################################################################################################
	
	/**
	 * Ausgabe aller eigenen und mit dem Nutzer geteilten Kontakte nach Name.
	 * Hierbei Abgleich zwischen dem vom Nutzer uebergebenem String und dem Vor- und Nachnamen des Kontaktes
	 * IMPL-Methode getKontakteByname();
	 */
	//CHECK
	@Test
	public void getKontakteByname() {
		Nutzer n = new Nutzer();
		n.setId(1);
		
		String string = "ig";
		
		EditorServiceImpl editor = new EditorServiceImpl();
		
		System.out.println(editor.getKontakteByName(string, n));
		
	}
	
	
	

}
	
	

	
	
	
	
	
	
	















	
	

