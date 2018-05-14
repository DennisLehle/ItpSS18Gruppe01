package de.hdm.itprojektss18.team01.sontact.server;


import java.sql.Date;
import java.util.Vector;

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
	 * Ein Nutzer wird mit all seinen Objekten aus der Datenbank gelöscht.
	 */
	public void deleteNutzer(Kontakt k) throws IllegalArgumentException {
	
		// Löschen der Berechtigungsstufe muss berrücksichtigt werden.
		Vector <Auspraegung> deleteAllAuspraegungen = getAllAuspraegungenByKontakt(k);
		if (deleteAllAuspraegungen != null) {
			for (Auspraegung a : deleteAllAuspraegungen) {
				this.aMapper.delete(a);
			}
		}
		//Wie werden Eigenschaften berücksichtigt beim Löschen?		
		this.kMapper.deleteAll(k);
				
		this.klMapper.deleteAll(k.getOwnerId());
		
		//Übergabe des CurrentUsers
		this.nMapper.delete(nutzer);
				}
			
		// Alle Auspraegungen der Kontakte des Nutzers aus der DB entfernen
		
		// Alle Kontakte des Nutzers aus der DB entfernen 
		
		// Alle Auspraegungen des Nutzers entfernen 
		
		// Den Nutzer als eigener Kontakt aus der DB entfernen 
		
		// Den Nutzer selbst aus der DB entfernen
		
		// (?)
		
	
	
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
	public Kontakt createKontakt (String vorname, String nachname, Nutzer nutzer)
					throws IllegalArgumentException { 
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(currentDate);
		kontakt.setModDat(currentDate);
		kontakt.setOwnerId(nutzer.getId());
		
		kontakt.setId(1);
		return this.kMapper.insert(kontakt);
	}
	
	/**
	 * Speichern eines modifizierten Kontakts
	 * 
	 */
	public Kontakt saveKontakt (Kontakt k) throws IllegalArgumentException {

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
	public void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl) throws IllegalArgumentException {
		this.kMapper.addKontaktToKontaktliste(k, kl);
	}
	
	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontakt k) throws IllegalArgumentException {
		this.kMapper.removeKontaktFromKontaktliste(k);
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
	
	
	/**
	 * Erzeugen einer neuen Kontaktliste.
	 * 
	 */
	public Kontaktliste createKontaktliste (String titel, int ownerId)
					throws IllegalArgumentException { 
				
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel(titel);
		
		//CurrentUser übergeben
		kontaktliste.setOwnerId(ownerId);
		
		kontaktliste.setId(1);
		return this.klMapper.insert(kontaktliste);
	}
	
	/**
	 * Speichern einer modifizierten Kontaktliste
	 * 
	 */
	public Kontaktliste saveKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		
		return klMapper.update(kl);
	}
		
	/**
	 * Loeschen einer Kontaktliste.
	 * 
	 */
	public void deleteKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		
		// Alle Kontakte der Kontaktliste aus der DB entfernen.
		Vector <Kontakt> removeAllKontakte = klMapper.getKontakte(kl);
		if (removeAllKontakte != null) {
			for (Kontakt k : removeAllKontakte) {
				this.kMapper.removeKontaktFromKontaktliste(k);
			}
		}

		this.klMapper.delete(kl);
	}
	
	/**
	 * Alle Kontaktlisten eines Nutzers anhand OwnerId
	 * 
	 */
	public Vector<Kontaktliste> getAllKontaktlisteByOwner (int ownerId) throws IllegalArgumentException {
		return this.klMapper.findOwnersKontaktliste(ownerId);
	}
	
	// getAllKontakteByKontaktliste() // gettAllKontakteByKontaktlisteId()
	
	/**
	 * Filtert für eine spezielle Kontaktliste, dessen Kontakte heraus. 
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> getAllKontakteByKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		return this.klMapper.getKontakte(kl);
		
	}

	/**
	 * Alle Kontaktlisten anhand ihrem Titel
	 */
	
	public Vector <Kontaktliste> findKontaktlisteByTitel (String titel) throws IllegalArgumentException {
		return this.klMapper.findByTitel(titel);
	}

	
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
	
	
	/**
	 * Erzeugen einer Eigenschaft.
	 */
	
	public Eigenschaft createEigenschaft (String bezeichnung)
			throws IllegalArgumentException { 
		
		Eigenschaft e = new Eigenschaft();
		e.setBezeichnung(bezeichnung);
		
		e.setId(1);
		
		return this.eMapper.insert(e);
	}
	
	
	/**
	 * Speichern einer modifizierten Eigenschaft.
	 * 
	 */
	public Eigenschaft saveEigenschaft (Eigenschaft e) throws IllegalArgumentException {

		return eMapper.update(e);
	}	
	
	
	/**
	 *Loeschen einer Eigenschaft.
	 * 
	 */
	public void deleteEigenschaft (Eigenschaft e) throws IllegalArgumentException {

		this.eMapper.delete(e);
	}

	
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
	
	/**
	 * Erzeugen einer neuen Auspraegung.
	 * 
	 */
	
	public Auspraegung createAuspraegung (String wert, int eigenschaftId, int kontaktId, int ownerId, Berechtigung berechtigung) throws IllegalArgumentException { 
		
		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);
		a.setOwnerId(nutzer.getId());
		a.setBerechtigung(berechtigung);
		
		a.setId(1);
		return this.aMapper.insert(a);
	}

	
	/**
	 * Speichern einer modifizierten Auspraegung.
	 * 
	 */
	public Auspraegung saveAuspraegung (Auspraegung a) throws IllegalArgumentException {

		return aMapper.update(a);
	}
	
	/**
	 *Loeschen einer Auspraegung.
	 * 
	 */
	public void deleteAuspraegung (Auspraegung a) throws IllegalArgumentException {

		this.aMapper.delete(a);
	}
	
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
	
	/**
	 * Wie lösen wir das mit den Übergabeparameter für Kontakt, Kontaktliste oder Ausprägung? Müssen diese überhaupt übergeben werden?
	 * @param holderId
	 * @param receiverId
	 * @param objectId
	 * @param type
	 * @param berechtigungsstufe
	 * @param kontaktId
	 * @param kontaktlisteId
	 * @param auspraegungId
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	public Berechtigung createBerechtigung (int holderId, int receiverId, int objectId, char type, int berechtigungsstufe) throws IllegalArgumentException { 
		
		Berechtigung b = new Berechtigung();
		
		b.setHolderId(holderId);
		b.setReceiverId(receiverId);
		b.setObjectId(objectId);
		b.setType(type);
		b.setBerechtigungsstufe(berechtigungsstufe);
		
		b.setId(1);
		return this.bMapper.insert(b);
	}
	
	
	/**
	 * Das Loeschen einer Berechtigung.
	 * @param b
	 * @throws IllegalArgumentException
	 */
	
	public void deleteBerechtigung (Berechtigung b) throws IllegalArgumentException {

		this.bMapper.delete(b);
	}
	
	
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


