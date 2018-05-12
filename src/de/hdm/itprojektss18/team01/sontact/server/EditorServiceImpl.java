package de.hdm.itprojektss18.team01.sontact.server;

import java.sql.Date;
import java.util.Vector;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojektss18.team01.sontact.server.db.*;
import de.hdm.itprojektss18.team01.sontact.shared.*;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;




public class EditorServiceImpl extends RemoteServiceServlet implements EditorService{
	public EditorServiceImpl() throws IllegalArgumentException {
}

	/**
	 * Serialisierung 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ein spezielles Nutzer-Objekt wird referenziert.  
	 */
	private Nutzer nutzer = null;
		
	/**
	   * Die Mapperklasse wird referenziert, die Nutzer-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private NutzerMapper nMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Kontakt-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private KontaktMapper kMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Kontaktlisten-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private KontaktlistenMapper klMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private EigenschaftMapper eMapper = null;

	/**
	   * Die Mapperklasse wird referenziert, die Auspraegung-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private AuspraegungMapper aMapper = null;

	/**
	   * Die Mapperklasse wird referenziert, die Berechtigung-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private BerechtigungMapper bMapper = null;
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Initialisierung
	   * ***************************************************************************
	   */
	
	/**
	 * Vollstaendiger Satz von Mappern mit deren Hilfe EditorServiceImpl
	 * mit der Datenbank kommunizieren kann.
	 */
	public void init() throws IllegalArgumentException {
		this.nMapper = NutzerMapper.nutzerMapper();
	    this.kMapper = KontaktMapper.kontaktMapper();
	    this.klMapper = KontaktlistenMapper.kontaktlistenMapper();
	    this.eMapper = EigenschaftMapper.eigenschaftMapper();
	    this.aMapper = AuspraegungMapper.auspraegungMapper();
	    this.bMapper = BerechtigungMapper.berechtigungMapper();
	}
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Initialisierung
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Nutzer-Objekte
	   * ***************************************************************************
	   */
	/**
	 * Neues Nutzer-Objekt wird angelegt und in der Datenbank gespeichert.
	 */
	public Nutzer createNutzer(String emailAddress) 
			throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);
		
		/* Es wird eine vorläufige Id gesetzt die anschließend in der Datenbank
		 * nach Verfügbarkeit angepasst wird.
		 */
		nutzer.setId(1);
		
		//Einfügen und Speichern in der Datenbank.
		return this.nMapper.insert(nutzer);
	}
	
	/**
	 * Nutzer-Objekt wird entfernt und aus der Datenbank gelöscht.
	 */
	public void deleteNutzer(Kontakt kontakt) {
		
		// Alle Auspraegungen der Kontakte des Nutzers aus der DB entfernen
		
		// Alle Kontakte des Nutzers aus der DB entfernen 
		
		// Alle Auspraegungen des Nuters entfernen 
		
		// Den Nutzer als eigener Kontakt aus der DB entfernen 
		
		// Den Nutzer selbst aus der DB entfernen
		
		// (?)
		
	}
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Nutzer-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Kontakt-Objekte
	   * ***************************************************************************
	   */
	
	/**
	 * Erzeugen eines neuen Kontakts.
	 * 
	 */
	public Kontakt createKontakt (String vorname, String nachname, DateTimeFormat erstellDat,
			DateTimeFormat modDat, int ownerId, int kontaktlisteId, Berechtigung berechtigung)
					throws IllegalArgumentException { // muessten wir hier nicht ein KontaktObjekt uebergeben? 
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		Kontakt kontakt = new Kontakt();
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(currentDate);
		kontakt.setModDat(currentDate);
		kontakt.setOwnerId(ownerId);
		kontakt.setKontaktlisteId(kontaktlisteId);
		kontakt.setBerechtigung(berechtigung);
		
		
		kontakt.setId(1);
		return this.kMapper.insert(kontakt);
	}
	
	/**
	 * Speichern eines modifizierten Kontakts
	 * 
	 */
	public Kontakt saveKontakt (Kontakt k) throws IllegalArgumentException {

		Date currentDate = new Date(System.currentTimeMillis());
		k.setModDat(currentDate);
		
		return kMapper.update(k);
	}
	
	/**
	 * Loeschen eines Kontakts
	 * 
	 */
	public void removeKontakt (Kontakt k) throws IllegalArgumentException {
		
		// Zunaechst alle Auspraegungen des Kontakts aus der DB entfernen.
		Vector <Auspraegung> deleteAllAuspraegungen = getAllAuspraegungenByKontakt(k);
		if (deleteAllAuspraegungen != null) {
			for (Auspraegung a : deleteAllAuspraegungen) {
				this.aMapper.delete(a);
			}
		}
		
		this.kMapper.delete(k);
	}
	
	/**
	 * Auslesen eines Kontakts anhand seiner id (?)
	 * 
	 */
	public Kontakt getKontaktById(int id) throws IllegalArgumentException {
		return this.kMapper.findKontaktById(id);
	}
	
	/**
	 * Auslesen der Kontakte anhand des Namens.
	 */
	public Vector<Kontakt> getKontaktByName(String name) throws IllegalArgumentException {
		return this.kMapper.findKontaktByName(name);
	}
	
	/**
	 * Auslesen aller Kontakte welche im Eigentum sind.
	 * 
	 */
	public Vector<Kontakt> getAllKontakteByOwner (Nutzer n) throws IllegalArgumentException {
		return this.kMapper.findKontaktByNutzerId(n.getId()); 
	}

	/**
	 * Setzten der Zuordnung eines Kontakts zur einer Kontaktliste
	 * 
	 */
	public void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl) {
		this.kMapper.addKontaktToKontaktliste(k, kl);
	}
	
	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontakt k) {
		this.kMapper.deleteKontaktFromKontaktliste(k);
	}
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Kontakt-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Kontaktlisten-Objekte
	   * ***************************************************************************
	   */
	
	
	// createKontaktliste()
	
	
	// saveKontatliste()
	
	
	// removeKontaktliste()
	
	
	// gellAllKontaktlistenByOwner()
	
	
	// getAllKontakteByKontaktliste() // gettAllKontakteByKontaktlisteId()

	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Kontaktlisten-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Eigenschaft-Objekte
	   * ***************************************************************************
	   */
	
	
	// ...
	
	
	// ...
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Eigenschaft-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Auspraegung-Objekte
	   * ***************************************************************************
	   */
	
	
	// createAuspraegung()

	
	// saveAuspraegung()
	
	
	// removeAuspraegung()
	
	
	
	/**
	 * Auslesen einer bestimmten Auspraegung anhand der id.
	 * 
	 */
	public Auspraegung getAuspraegungById(Auspraegung a) throws IllegalArgumentException {
		return this.aMapper.findAuspraegungById(a.getId());
	}

	/**
	 * Gibt alle Auspraegungen eines Kontakts zurück.
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(Kontakt k) throws IllegalArgumentException {
		return this.aMapper.findAuspraegungByKontakt(k);
	}
	
	
	// getAllAuspraegungenByEigenschaft (?)
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Auspraegung-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Berechtigung-Objekte
	   * ***************************************************************************
	   */
	
	// createBerechtigung()
	
	
	// removeBerechtigung()
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Berechtigung-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Abruf der geteilten Objekte
	   * ***************************************************************************
	   */
	
	
	// getAllSharedKontakteWith()
	
	
	// getAllSharedKontakteFrom()
	
	
	// getAllSharedKontaktlistenWith()
	
	
	// getAllSharedKontaktlistenFrom()
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Abruf der geteilten Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Share-Methoden
	   * ***************************************************************************
	   */
	
	
	// shareObjectWith()
	
	
	
	// removeSharedObjectWith()
	
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Share-Methoden
	   * ***************************************************************************
	   */
	
	
}


