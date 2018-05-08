package de.hdm.itprojektss18.team01.sontact.server.db;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public class MapperTestKlasse {

	public static void main(String[] args) {
	
	
		
//		Nutzer n = new Nutzer();
//		
//		n.setId(1);
//		n.setEmailAddress("dennisLehle@gmail.com");
//		
//		NutzerMapper.nutzerMapper().insert(n);
//		

		
		// Kontakt erstellen der dann einer default Kontaktliste hinzugefügt wird.
		// Somit FS bez keine Problem da der Kontakt automatisch immer eine default nummer hat
		// Im Code  muss so realisiert werden dass wenn Nutzer sich als Kontakt anlegt das erste eine KL erstellt wird die einen
		// oder keinen Namen besitzt.
		
//		Kontakt k = new Kontakt();
//		
//		k.setId(1);
//		k.setVorname("Des");
//		k.setNachname("Lele");
//		k.setOwnerId(1);
//		k.setKontaktlisteId(1);
//		KontaktMapper.kontaktMapper().insertKontakt(k);
		
//Erstellen einer neuen Kl die noch keine Kontakte enthält.. Vom Nutzer der schon Kontakt ist und eine KL haben will..

//	Kontaktliste kl = new Kontaktliste();
//	
//	kl.setId(3); // Erstelle eine KL 
//	kl.setTitel("Geschäft"); // Setzte Titel
//	kl.setOwnerId(1); // Dennis Lehle ist der inhaber 
//	
//	KontaktlistenMapper.kontaktlistenMapper().insert(kl);
	
		

		
// Jetzt wollen wir dem Kontakt Eigenschaften angeben mit Ausprägungen.
// Hier wird der Besitzer des Kontaktes direkt angegeben und die Kontakt Id welche die Ausprägungen angehören.
// Pro Auspraegung die angelegt wird wird ein neues Objekt angelegt.. in diesem Fall müssen es 2 sein...
//		
//		Auspraegung a = new Auspraegung();
		//Auspraegung a2 = new Auspraegung();
//		
//		a.setId(1);
//		a.setKontaktId(1);
//		a.setOwnerid(1);
		
//      a2.setId(1);
//		a2.setKontaktId(1);
//		a2.setOwnerid(1);
//		
//		//See oben
//		a2.setEigenschaftId(5);
//		a2.setWert("0711/222222");
//		
//		// Homepage
//		a.setEigenschaftId(3);
//		a.setWert("www.StudenDer-Hdm@hdm.de");
//		
//		
//		AuspraegungMapper.auspraegungMapper().insert(a);
//		AuspraegungMapper.auspraegungMapper().insert(a2);
	

		
		// Jetzt wollen wir die Kontakte, Kontaktlisten oder Auspraegungen.
		// Dazu wird die Berechtigung benötigt..
		// Hier wird eine Berechtigung angelegt für einen geteilten Kontakt mit einem anderen Nutzer.
		//
//		
//		Berechtigung b = new Berechtigung();
//		
//		//Default immer eine 1
//		b.setId(1);
//		//Owner der teilen will oder der jenige der ein geteiltes Objekt weiter teilenw will. Dennis
//		b.setHolderId(1);
//		// Id wer das Objekt erhalten soll. Kevin
//		b.setReceiverId(2);
//		// Teilen des Kontakts Des Lele
//		b.setKontaktid(1);
//		// Wird angegeben wenn eine Kontaktliste geteilt werden soll. ABer auch bei Teilung Kontakt aber die Default KL wird gesucht und eingefügt.
//		b.setKontaktlisteid(2);
//		// Welche Auspraegungen geteilt werdens sollen.
//		// Da Kontakt mehrere Ausprägungen haben können die geteilt werden können, existieren daher mehrere Einträge in der Datenbank...
//		b.setAuspraegungid(2);
//		// Welcher Tyo das Objekt hat.
//		b.setType('k');
//		// Berechtigung wird gesetzt...In diesem Fall bekommt der Kontakt Kevin nur Rechte zum lesen mehr nicht...
//		b.setBerechtigungsstufe(1);
//		
//		
//		BerechtigungMapper.berechtigungMapper().insert(b);
//		
//		
//		
		Kontakt k = new Kontakt();
		
		k.setId(2);
		k.setNachname("Bayrak");
		k.setOwnerId(1);
		k.setKontaktlisteId(1);
		
		KontaktMapper.kontaktMapper().update(k);
		
		
		
		
		
	}

}
