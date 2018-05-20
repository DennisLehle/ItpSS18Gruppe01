package de.hdm.itprojektss18.team01.sontact.server;


import java.sql.Timestamp;
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
	   * Die Mapperklasse wird referenziert, die Kontaktlisten-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private KontaktlisteKontaktMapper klkMapper = null;
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
	   * ABSCHNITT, Beginn: Methoden fuer Nutzer-Objekt
	   * ***************************************************************************
	   */
	/**
	 * Neues Nutzer-Objekt wird angelegt und in der Datenbank gespeichert.
	 */
	public Nutzer createNutzer(String emailAddress) 
			throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);
		
		/* Es wird eine vorlï¿½ufige Id gesetzt die anschlieï¿½end in der Datenbank
		 * nach Verfï¿½gbarkeit angepasst wird.
		 */
		nutzer.setId(1);
		init();
		//Einfï¿½gen und Speichern in der Datenbank.
		return this.nMapper.insert(nutzer);
	}
	
	/**
	 * Nutzer
	 * @param n
	 
	public void setNutzer(Nutzer n) throws IllegalArgumentException {
		init();
		nutzer = n;
	}
	
	**/
	
	/**
	 * Diese Methode sucht den Nutzer anhand der Emailadresse raus
	 * ï¿½berprï¿½fung Nutzer vorhanden
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Nutzer findNutzerByEmail (String email) throws IllegalArgumentException {
		try {
			if (nMapper.findUserByGMail(email) == null) {
				return null;
				
			} else {
				return nMapper.findUserByGMail(email);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	/**
	 * Ein Nutzer wird mit all seinen Objekten aus der Datenbank gelï¿½scht.
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
	
		// Alle Auspraegungen der Kontakte, welche im Eigentumsverhï¿½ltnis mit dem Nutzer stehen, aus der DB entfernen 
		this.aMapper.deleteAllByOwner(n);
	
		//Alle Kontakte, welche im Eigentumsverhï¿½ltnis mit dem Nutzer stehen, aus der DB entfernen	
		this.kMapper.deleteAllByOwner(n);
		
		//Alle Kontaktlisten, welche im Eigentumsverhï¿½ltnis mit dem Nutzer stehen, aus der DB entfernen	
		this.klMapper.deleteAllByOwner(n);
		
		//Alle Von- + Mit- Berechtigungen aus der DB entfernen	
		// TO-DO ...

		//Loeschen des Nutzers
		this.nMapper.delete(n);
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
	public Kontakt createKontakt(String vorname, String nachname, Nutzer n)
					throws IllegalArgumentException { 
		init();
				
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		
		kontakt.setId(1);
		
		kontakt = this.kMapper.insert(kontakt);
		return kontakt;
	}
	
	/**
	 * Speichern eines modifizierten Kontakts
	 * 
	 */
	public Kontakt saveKontakt(Kontakt k) throws IllegalArgumentException {
		init();
		k.setModDat(new Timestamp(System.currentTimeMillis()));
		return kMapper.update(k);
	}
	
	/**
	 * Loeschen eines Kontakts mit seinen Auspraegungen und seinen Kontaktlistenzugehörigkeiten 
	 * 
	 */
	public void removeKontakt(Kontakt k) throws IllegalArgumentException {
		
		// Zunaechst alle Auspraegungen des Kontakts aus der DB entfernen.
		Vector <Auspraegung> deleteAllAuspraegungen = getAllAuspraegungenByKontakt(k);
		if (deleteAllAuspraegungen != null) {
			for (Auspraegung a : deleteAllAuspraegungen) {
				this.aMapper.delete(a);
			}
		}
				
		this.klkMapper.deleteKontaktFromAllLists(k);
		
		
		// (!) Berechtigungen fuer den Kontakt entfernen -> ownerGesetteteBerechtigugen 
		
		
		this.kMapper.delete(k);
	}
	
	/**
	 * removeBerechtigung() -> Wenn ein Teilhaber den Kontakt loescht.
	 */
	
	
	
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
	// Beschrï¿½nkung auf eigene Kontakte?
	public Vector<Kontakt> getKontaktByName(String name) throws IllegalArgumentException {
		return this.kMapper.findKontaktByName(name);
	}
	
	/**
	 * Auslesen aller Kontakte welche im Eigentum sind.
	 * 
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n) throws IllegalArgumentException {
		return this.kMapper.findKontaktByNutzerId(n.getId()); 
	}

	/**
	 * Setzten der Zuordnung eines Kontakts zur einer Kontaktliste
	 * 
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException {
		init();
		this.klkMapper.addKontaktToKontaktliste(kl, k);
	}
	
	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException {
		init();
		this.klkMapper.removeKontaktFromKontaktliste(kl, k);
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
	public Kontaktliste createKontaktliste (String titel, Nutzer n)
					throws IllegalArgumentException { 
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel(titel);
		kontaktliste.setOwnerId(n.getId());
		kontaktliste.setId(1);
		
		return this.klMapper.insert(kontaktliste);
	}
	
	/**
	 * Speichern einer modifizierten Kontaktliste
	 * 
	 */
	public Kontaktliste saveKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		init();
		return klMapper.update(kl);
	}
		
	/**
	 * Loeschen einer Kontaktliste.
	 * 
	 */
	public void deleteKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		
		// Alle Kontakte der Kontaktliste aus der DB entfernen.
		Vector <Kontakt> removeAllKontakte = klkMapper.findAllKontakteByKontaktliste(kl.getId());
		if (removeAllKontakte != null) {
			for (Kontakt k : removeAllKontakte) {
				this.klkMapper.removeKontaktFromKontaktliste(kl, k);
				this.removeKontakt(k);
				
			}
		}
		
		this.klMapper.delete(kl);
	}
	
	/**
	 * Alle Kontaktlisten eines Nutzers anhand OwnerId
	 * 
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException {
		init();
		return this.klMapper.findKontaktlistenByOwner(n.getId());
	}
	
	/**
	 * Filtert fï¿½r eine spezielle Kontaktliste, dessen Kontakte heraus. 
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> getKontakteByKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		return this.klkMapper.findAllKontakteByKontaktliste(kl.getId());
		
	}

	/**
	 * Alle Kontaktlisten anhand ihrem Titel
	 */
	// Beschrï¿½nkung auf eigene Kontaktlisten?
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
		init();
		
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
	
	/**
	 * Gibt die vordefinierte Auswahl der Eigenschaften zurück.
	 * @return
	 * @throws IllegalArgumentException
	 * @throws  
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl() throws IllegalArgumentException {
		return this.eMapper.findEigenschaftAuswahl();
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
	
	public Auspraegung createAuspraegung (String wert, int eigenschaftId, 
			int kontaktId, Nutzer n /**int ownerId*/) throws IllegalArgumentException { 
		
		init();
		
		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);
		a.setOwnerId(n.getId() /**ownerId*/);
		
		a.setId(1);
				
		this.saveModifikationsdatum(a.getKontaktId());
		return this.aMapper.insert(a);
	
	}

	/**
	 * Speichern einer modifizierten Auspraegung.
	 * 
	 */
	public Auspraegung saveAuspraegung (Auspraegung a) throws IllegalArgumentException {
		init();
		this.saveModifikationsdatum(a.getKontaktId());
		return aMapper.update(a);
	}
	
	/**
	 *Loeschen einer Auspraegung.
	 * 
	 */
	public void deleteAuspraegung (Auspraegung a) throws IllegalArgumentException {
		init();
		this.saveModifikationsdatum(a.getKontaktId());
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
	 * Gibt alle Auspraegungen eines Kontakts zurï¿½ck.
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(Kontakt k) throws IllegalArgumentException {
		return this.aMapper.findAuspraegungByKontakt(k);
	}

	
	
	/**Eine neue Eigenschaft fï¿½r eine neue Ausprï¿½gung setzen 
	 *
	 */
	public void createAuspraegungForNewEigenschaft (Eigenschaft e, Auspraegung a, Nutzer n) {
		
		init();

		this.createEigenschaft(e.getBezeichnung());
		this.createAuspraegung(a.getWert(), e.getId(), a.getKontaktId(), n);
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
	 * Wie lï¿½sen wir das mit den ï¿½bergabeparameter fï¿½r Kontakt, Kontaktliste oder Ausprï¿½gung? Mï¿½ssen diese ï¿½berhaupt ï¿½bergeben werden?
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

	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Sonstiges
	   * ***************************************************************************
	   */
	
	public void saveModifikationsdatum(int id) throws IllegalArgumentException {
		init();
		this.kMapper.updateModifikationsdatum(id);
	}
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Sonsitges
	   * ***************************************************************************
	   */
	
	
	
}


