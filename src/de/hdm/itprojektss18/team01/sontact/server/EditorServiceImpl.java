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
		
		/* Es wird eine vorl�ufige Id gesetzt die anschlie�end in der Datenbank
		 * nach Verf�gbarkeit angepasst wird.
		 */
		nutzer.setId(1);
		init();
		//Einf�gen und Speichern in der Datenbank.
		return this.nMapper.insert(nutzer);
	}
	
	/**
	 * Nutzer
	 * @param n
	 */
	public void setNutzer(Nutzer n) throws IllegalArgumentException {
		init();
		nutzer = n;
	}
	
	/**
	 * Diese Methode sucht den Nutzer anhand der Emailadresse raus
	 * �berpr�fung Nutzer vorhanden
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
	 * Ein Nutzer wird mit all seinen Objekten aus der Datenbank gel�scht.
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
	
		// Alle Auspraegungen der Kontakte, welche im Eigentumsverh�ltnis mit dem Nutzer stehen, aus der DB entfernen 
		this.aMapper.deleteAllByOwner(nutzer);
	
		//Alle Kontakte, welche im Eigentumsverh�ltnis mit dem Nutzer stehen, aus der DB entfernen	
		this.kMapper.deleteAllByOwner(nutzer);
		
		//Alle Kontaktlisten, welche im Eigentumsverh�ltnis mit dem Nutzer stehen, aus der DB entfernen	
		this.klMapper.deleteAllByOwner(nutzer);
		
		//Alle Von- + Mit- Berechtigungen aus der DB entfernen	
		// TO-DO ...

		//Loeschen des Nutzers
		this.nMapper.delete(nutzer);
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
	public Kontakt createKontakt(String vorname, String nachname)
					throws IllegalArgumentException { 
		init();
				
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(nutzer.getId());
		
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
	 * Loeschen eines Kontakts
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
	// Beschr�nkung auf eigene Kontakte?
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
	public void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl) throws IllegalArgumentException {
		init();
		this.kMapper.addKontaktToKontaktliste(k, kl);
	}
	
	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontakt k) throws IllegalArgumentException {
		init();
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
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel(titel);
		kontaktliste.setOwnerId(nutzer.getId());
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
		Vector <Kontakt> removeAllKontakte = klMapper.getKontakteByKontaktliste(kl);
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
	public Vector<Kontaktliste> getKontaktlistenByOwner() throws IllegalArgumentException {
		return this.klMapper.findKontaktlistenByOwner(nutzer.getId());
	}
	
	/**
	 * Filtert f�r eine spezielle Kontaktliste, dessen Kontakte heraus. 
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> getKontakteByKontaktliste (Kontaktliste kl) throws IllegalArgumentException {
		return this.klMapper.getKontakteByKontaktliste(kl);
		
	}

	/**
	 * Alle Kontaktlisten anhand ihrem Titel
	 */
	// Beschr�nkung auf eigene Kontaktlisten?
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
			int kontaktId, int ownerId) throws IllegalArgumentException { 
		
		init();
		
		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);
		a.setOwnerId(nutzer.getId());
		
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
	 * Gibt alle Auspraegungen eines Kontakts zur�ck.
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(Kontakt k) throws IllegalArgumentException {
		return this.aMapper.findAuspraegungByKontakt(k);
	}

	/**Eine neue Eigenschaft f�r eine neue Auspr�gung setzen 
	 *
	 */
		public Auspraegung createAuspraegungForNewEigenschaft (String wert, 
				int kontaktId, int ownerId, int eigenschaftId) {
			
			init();
			
			Eigenschaft e = new Eigenschaft();
			eMapper.insert(e);
			
			
			Auspraegung a = new Auspraegung();
			a.setWert(wert);
			a.setKontaktId(kontaktId);
			a.setOwnerId(nutzer.getId());
			a.setEigenschaftId(eigenschaftId);
			
			
			a.setId(1);
					
			this.saveModifikationsdatum(a.getKontaktId());
			return this.aMapper.insert(a);
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
	 * Wie l�sen wir das mit den �bergabeparameter f�r Kontakt, Kontaktliste oder Auspr�gung? M�ssen diese �berhaupt �bergeben werden?
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
	
	public int saveModifikationsdatum(int id) {
		init();
		return kMapper.updateModifikationsdatum(id);
		
	}

	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Sonsitges
	   * ***************************************************************************
	   */
	
	
	
}


