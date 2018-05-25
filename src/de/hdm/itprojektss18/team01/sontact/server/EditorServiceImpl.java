package de.hdm.itprojektss18.team01.sontact.server;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojektss18.team01.sontact.server.db.*;
import de.hdm.itprojektss18.team01.sontact.shared.*;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;


public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {

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
	 * Die Mapperklasse wird referenziert, die Nutzer-Objekte mit der Datenbank
	 * vergleicht.
	 */
	private NutzerMapper nMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Kontakt-Objekte mit der Datenbank
	 * vergleicht.
	 */
	private KontaktMapper kMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Kontaktlisten-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private KontaktlistenMapper klMapper = null;

	/**
	   * Die Mapperklasse wird referenziert, die KontaktlistenKontakt-Objekten 
	   * der Datenbank vergleicht. 
	   */
	private KontaktlisteKontaktMapper klkMapper = null;
	/**
	   * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	
	/**
	 * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private EigenschaftMapper eMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Auspraegung-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private AuspraegungMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Berechtigung-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private BerechtigungMapper bMapper = null;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Initialisierung
	 * *************************************************************************
	 * **
	 */

	/**
	 * Vollstaendiger Satz von Mappern mit deren Hilfe EditorServiceImpl mit der
	 * Datenbank kommunizieren kann.
	 */
	public void init() throws IllegalArgumentException {
		this.nMapper = NutzerMapper.nutzerMapper();
		this.kMapper = KontaktMapper.kontaktMapper();
		this.klMapper = KontaktlistenMapper.kontaktlistenMapper();
		this.klkMapper = KontaktlisteKontaktMapper.kontaktlisteKontaktMapper();
		this.eMapper = EigenschaftMapper.eigenschaftMapper();
		this.aMapper = AuspraegungMapper.auspraegungMapper();
		this.bMapper = BerechtigungMapper.berechtigungMapper();
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Initialisierung
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Nutzer-Objekt
	 * *************************************************************************
	 * **
	 */
	/**
	 * Neues Nutzer-Objekt wird angelegt und in der Datenbank gespeichert.
	 */
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);

		/*
		 * Es wird eine vorl�ufige Id gesetzt die anschlie�end in der
		 * Datenbank nach Verf�gbarkeit angepasst wird.
		 */
		nutzer.setId(1);
		init();
		// Einf�gen und Speichern in der Datenbank.
		return this.nMapper.insert(nutzer);
	}

	/**
	 * Nutzer
	 * 
	 * @param n
	 * 
	 *            public void setNutzer(Nutzer n) throws
	 *            IllegalArgumentException { init(); nutzer = n; }
	 * 
	 **/

	/**
	 * Diese Methode sucht den Nutzer anhand der Emailadresse raus
	 * �berpr�fung Nutzer vorhanden
	 * 
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
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

		// Alle Auspraegungen der Kontakte, welche im Eigentumsverh�ltnis mit
		// dem Nutzer stehen, aus der DB entfernen
		this.aMapper.deleteAllByOwner(n);

		// Alle Kontakte, welche im Eigentumsverh�ltnis mit dem Nutzer stehen,
		// aus der DB entfernen
		this.kMapper.deleteAllByOwner(n);

		// Alle Kontaktlisten, welche im Eigentumsverh�ltnis mit dem Nutzer
		// stehen, aus der DB entfernen
		this.klMapper.deleteAllByOwner(n);

		// Alle Von- + Mit- Berechtigungen aus der DB entfernen
		// TO-DO ...

		// Loeschen des Nutzers
		this.nMapper.delete(n);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Nutzer-Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Kontakt-Objekte
	 * *************************************************************************
	 * **
	 */

	/**
	 * Erzeugen eines neuen Kontakts der direkt in die Default Kontaktliste des jeweiligen Nutzers gespeichert wird ("Alle Kontakte").
	 * 
	 */
	public void createKontakt(String vorname, String nachname, Nutzer n) throws IllegalArgumentException {
		
		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		kontakt.setIdentifier('k');

		kontakt.setId(1);
		

		//Kontaktliste und Kontakt der zwischen Tabelle hinzufügen.
		addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Alle Kontakte"), kMapper.insert(kontakt));

	}
	
	/**
	 * Erzeugen eines neuen Kontakts bei der Registrierung plus anlegen einer Default Kontaktliste die dann zusammen
	 * mit dem erstellten Kontakt-Objekt in die zwischen Tabelle KontaktlisteKontakt.
	 * 
	 */
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n) throws IllegalArgumentException {
		
		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		kontakt.setIdentifier('r');

		kontakt.setId(1);
		init();
		
		//Kontakt in db vorhanden...
		this.kMapper.insert(kontakt);
		
		// Den zuvor eingespeicherten Kontakt mit identifier r aus der Db lesen und speichern.
		Kontakt k =getOwnKontakt(n);
		
		//Default Kontaktliste erstellen. (Alle Kontakte)
		createKontaktlisteRegistrierung(n);
		
		//Kontaktliste und Kontakt der zwischen Tabelle hinzufügen.
		addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Alle Kontakte"), k);
		
		
		return k;
		

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
	 * Loeschen eines Kontakts mit seinen Auspraegungen seinen
	 * Kontaktlistenzugeh�rigkeiten und seinen Teilhaberschaften/ Berechtigungen 
	 * 
	 */
	public void removeKontakt(Kontakt k) throws IllegalArgumentException {

		// Zunaechst alle Auspraegungen des Kontakts aus der DB entfernen.
		Vector<Auspraegung> deleteAllAuspraegungen = getAllAuspraegungenByKontakt(k.getId());
		if (deleteAllAuspraegungen != null) {
			for (Auspraegung a : deleteAllAuspraegungen) {
				this.aMapper.delete(a);
			}
		}			
		this.klkMapper.deleteKontaktFromAllLists(k);
		
		
		// (!) Berechtigungen fuer den Kontakt entfernen -> ownerGesetteteBerechtigugen 

		// (!) Berechtigungen fuer den Kontakt entfernen ->
		// ownerGesetteteBerechtigugen

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
	 * Auslesen des eigenen Kontaktes.
	 * @param n
	 * @return
	 */
	public Kontakt getOwnKontakt(Nutzer n) {
		
		return this.kMapper.findNutzerKontaktByIdentifier(n.getId());
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
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException {
		init();
		this.klkMapper.addKontaktToKontaktliste(kl, k);
	}

	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste
	 * 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException {
		init();
		this.klkMapper.removeKontaktFromKontaktliste(kl, k);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Kontakt-Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Kontaktlisten-Objekte
	 * *************************************************************************
	 * **
	 */

	/**
	 * Erzeugen einer neuen Kontaktliste.
	 * 
	 */
	public Kontaktliste createKontaktliste(String titel, Nutzer n) throws IllegalArgumentException {
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel(titel);
		kontaktliste.setOwnerId(n.getId());
		kontaktliste.setId(1);

		return this.klMapper.insert(kontaktliste);
	}

	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n) {
		
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel("Alle Kontakte");
		kontaktliste.setOwnerId(n.getId());
		kontaktliste.setId(1);
		
		 return this.klMapper.insert(kontaktliste);
		
	}
	/**
	 * Speichern einer modifizierten Kontaktliste
	 * 
	 */
	public void saveKontaktliste(Kontaktliste kl) throws IllegalArgumentException {
		init();
		klMapper.update(kl);
	}

	/**
	 * Loeschen einer Kontaktliste.
	 * 
	 */
	public void deleteKontaktliste(Kontaktliste kl) throws IllegalArgumentException {

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
	 * Filtert f�r eine spezielle Kontaktliste, dessen Kontakte heraus.
	 * 
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> getKontakteByKontaktliste (int kontaktlisteId) throws IllegalArgumentException {
		return this.klkMapper.findAllKontakteByKontaktliste(kontaktlisteId);
		
	}

	/**
	 * Kontaktliste "Alle Kontakte" für den Nutzer in der Db finden (DefaultKontaktliste).
	 */
	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel) throws IllegalArgumentException {
		return this.klMapper.findByTitel(n, titel);
	}
	
	/**
	 * Findet eine Kontaktliste anhand der Id
	 * Für TreeViewModel benötigt.
	 * @param id
	 * @return
	 */
	public Kontaktliste findKontaktlisteById(int id) {
		return this.klMapper.findById(id);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Kontaktlisten-Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Eigenschaft-Objekte
	 * *************************************************************************
	 * **
	 */

	/**
	 * Erzeugen einer Eigenschaft.
	 */

	public Eigenschaft createEigenschaft(String bezeichnung) throws IllegalArgumentException {
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
	public Eigenschaft saveEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		init();
		return eMapper.update(e);
	}

	/**
	 * Loeschen einer Eigenschaft.
	 * 
	 */
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		init();
		this.eMapper.delete(e);
	}

	/**
	 * Gibt die vordefinierte Auswahl der Eigenschaften zur�ck. @return @throws
	 * IllegalArgumentException @throws
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl() throws IllegalArgumentException {
		return this.eMapper.findEigenschaftAuswahl();
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Eigenschaft-Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Auspraegung-Objekte
	 * *************************************************************************
	 * **
	 */

	/**
	 * Erzeugen einer neuen Auspraegung.
	 * 
	 */

	public Auspraegung createAuspraegung(String wert, int eigenschaftId, int kontaktId,
			Nutzer n /** int ownerId */
	) throws IllegalArgumentException {

		init();

		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);
		a.setOwnerId(n.getId() /** ownerId */
		);

		a.setId(1);

		this.saveModifikationsdatum(a.getKontaktId());
		return this.aMapper.insert(a);

	}

	/**
	 * Speichern einer modifizierten Auspraegung.
	 * 
	 */
	public Auspraegung saveAuspraegung(Auspraegung a) throws IllegalArgumentException {
		init();
		this.saveModifikationsdatum(a.getKontaktId());
		return aMapper.update(a);
	}

	/**
	 * Loeschen einer Auspraegung.
	 * 
	 */
	public void deleteAuspraegung(Auspraegung a) throws IllegalArgumentException {
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
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId) throws IllegalArgumentException {
		return this.aMapper.findAuspraegungByKontakt(kontaktId);
	}

	/**
	 * Eine neue Eigenschaft f�r eine neue Auspr�gung setzen
	 *
	 */
	public void createAuspraegungForNewEigenschaft(Eigenschaft e, Auspraegung a, Nutzer n) {

		init();

		this.createEigenschaft(e.getBezeichnung());
		this.createAuspraegung(a.getWert(), e.getId(), a.getKontaktId(), n);
	}
	
	/**
	 * Das Entfernen einer selbstdefinierten Eigenschaft mit ihren Auspraegungen des Kontakts.
	 */
	

	// getAllAuspraegungenByEigenschaft (?)

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Auspraegung-Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden fuer Berechtigung-Objekte
	 * *************************************************************************
	 * **
	 */
	
	/**
	 * Erstellung einer neuen Berechtigung. Es wird eine neue Berechtigung f�r eine 
	 * neue Teilhaberschaft an einem bestimmten Objekt erteilt. 
	 * 
	 * @param ownerId
	 * @param receiverId
	 * @param objectId
	 * @param type
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException {
	
			Berechtigung b = new Berechtigung();
			b.setId(1);
			b.setOwnerId(ownerId);
			b.setReceiverId(receiverId);
			b.setObjectId(objectId);
			b.setType(type);
			
			init();
			return this.bMapper.insert(b);
		}
	

	/**
	 * Es wird eine Berechtigung f�r ein bestimmtes Objekt erteilt. 
	 * Das tats�chlich geteilte Objekt wird angesprochen und als Typ identifiziert. 
	 * 
	 * @param ownerId
	 * @param receiverId
	 * @param objectId
	 * @param type
	 * @throws IllegalArgumentException
	 */
	
	public void shareObject(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException {
		init(); 
//		switch (type) {		
//		case 1:
		if (type == 'l') {
			
			this.createBerechtigung(ownerId, receiverId, objectId, type);
				
			Vector<Kontakt> kv = this.getKontakteByKontaktliste(objectId);
			for (int k=0; k < kv.size(); k++)  {
				if (kv != null ) {
				this.createBerechtigung(ownerId, receiverId, kv.elementAt(k).getId(), kv.elementAt(k).getType());
				
				Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(kv.elementAt(k).getId());
				for (int a = 0; a < av.size(); a++) {
					if(av != null) {
						this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(), av.elementAt(a).getType());
						}	
					}
				}		
			}		
		} else //
//		break;
//		case 2:
		if (type == 'k') {
		
			this.createBerechtigung(ownerId, receiverId, objectId, type);
			// -> 
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(objectId);
			for (int a=0; a < av.size(); a++) {
				if(av != null) {
					this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(), av.elementAt(a).getType());	
				}
			}
		} // break;
		}	
//	} 
	
	/**
	 * Das Loeschen einer Berechtigung. Diese Methode hebt die Berechtigung einer Teilhaberschaft
	 * zu einem bestimmten Objekttyp auf. Es werden z.B. alle abh�ngigen Objekte einer
	 * Kontaktliste, also Kontakte angesprochen, die wiederum Auspr�gungen beinhalten. 
	 * Alle Objekte werden fortlaufend von der Berechtigung gel�st. 
	 * @param b
	 * @throws IllegalArgumentException
	 */

	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException {

		if (b.getType() == 'l') {
		 b.setObjectId('l');
		Vector<Kontakt> kv = this.getKontakteByKontaktliste(b.getObjectId());
		for (int k=0; k < kv.size(); k++)  {
			if (kv != null ) {
				kv.elementAt(k).getId();
				kv.elementAt(k);
			} this.bMapper.delete(b);
			
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(kv.elementAt(k).getId());
			for (int a = 0; a < av.size(); a++) {
				if(av != null) {
					 av.elementAt(a).getId(); 
					 av.elementAt(a).getType();
					this.bMapper.delete(b);
		}
				} }
		}
		else
		if (b.getType() == 'k') {
			b.setObjectId('k');
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(b.getObjectId());
			for (int a=0; a < av.size(); a++) {
				if(av != null) {		 
					av.elementAt(a).getId();
					av.elementAt(a).getType();	
				this.bMapper.delete(b);
			
							
				}}}}
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Berechtigung-Objekte
	 * *************************************************************************
	 * **
	 */
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Abruf der geteilten Objekte
	 * *************************************************************************
	 * **
	 */

	// getAllSharedKontakteWith()
	public Vector<Berechtigung> getAllSharedKontakteWith(int ownerId) throws IllegalArgumentException {
		
		Vector<Berechtigung> b = this.bMapper.findAllSharedKontakteWith(ownerId);
	        return b;
	    }

	// getAllSharedKontakteFrom()
	public Vector<Berechtigung> getAllSharedKontakteFrom(int receiverId) throws IllegalArgumentException {
		
		Vector<Berechtigung> b = this.bMapper.findAllSharedKontakteFrom(receiverId);
	        return b;
	    }
	

	/** getAllSharedKontaktlistenWith(), Methode wird nicht ben�tigt!
	 * 
	 */
	public Vector<Berechtigung> getAllSharedKontaktlistenWith(int ownerId) throws IllegalArgumentException {
		
	        return null;
	    }

	// getAllSharedKontaktlistenFrom()
	
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Abruf der geteilten Objekte
	 * *************************************************************************
	 * **
	 */
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Share-Methoden
	 * *************************************************************************
	 * **
	 */
	

	// shareObjectWith() Ein Objekt mit einem Empf�nger teilen. Davor �berpr�fen, ob bereits
		//eine Berechtigungszugriff vorhanden sei, falls nicht die Berechtigung hinzuzuf�gen.
//	public Berechtigung shareObjectWith(int ownerId, int receiverId, int objectId, char type,
//			int berechtigungsstufe) throws IllegalArgumentException {
//		
//		Berechtigung b = bMapper.findById(berechtigungsstufe);
//        if (bMapper.findById(berechtigungsstufe) == null) {	
//        	this.createBerechtigung(ownerId, receiverId, objectId, type, 3);
//        }
//        else return bMapper.findById(berechtigungsstufe);
//        
//        b.setOwnerId(ownerId);
//        b.setReceiverId(receiverId);
//        b.setObjectId(objectId);
//        b.setType(type);
//        b.setBerechtigungsstufe(berechtigungsstufe);
//        
//        return this.bMapper.insert(b);
//	}     

//	// shareObjectWith() Ein Objekt mit einem Empf�nger teilen. Davor �berpr�fen, ob bereits
//		//eine Berechtigungszugriff vorhanden sei, falls nicht die Berechtigung hinzuzuf�gen.
//	public Berechtigung shareObjectWith(int ownerId, int receiverId, int objectId, char type,
//			int berechtigungsstufe) throws IllegalArgumentException {
//		
//		Berechtigung b = bMapper.findById(berechtigungsstufe);
//        if (bMapper.findById(berechtigungsstufe) == null) {	
//        	this.createBerechtigung(ownerId, receiverId, objectId, type, 3);
//        }
//        else return bMapper.findById(berechtigungsstufe);
//        
//        b.setOwnerId(ownerId);
//        b.setReceiverId(receiverId);
//        b.setObjectId(objectId);
//        b.setType(type);
//        b.setBerechtigungsstufe(berechtigungsstufe);
//        
//        return this.bMapper.insert(b);
//	}     

	
/**	
	//getType-Methode zum identifizieren der ObjektId -- ?
 	public Berechtigung getType (char type, int objectId, int kontaktId, int kontaktlisteId,
 			int auspraegungId) throws IllegalArgumentException {
 	
 		
 	Berechtigung o = new Berechtigung(o.getObjectId());
 	Berechtigung k = this.bMapper.findById(k.getKontaktId());
 	Berechtigung kl = this.bMapper.findById(kl.getKontaktlisteId());
 	Berechtigung a = this.bMapper.findById(a.getAuspraegungId());
 	
 	switch (objectId) {
 	case kontaktId : 
 		if (k.equals(o))
 			return;
 			break;
 	case kontaktlisteId : 
 		if (kl.equals(o))
 			return;
 			break;
 	case auspraegungId : 
 		if (a.equals(o))
			return ;
			break;
 	}
    
 	}
 
 /**

	// removeSharedObjectWith() --> Delete Berechtigung, siehe oben.
 	


	/**
	//Es kann ein Status f�r ein Objekt gesetzt werden, dieser darauf verweist, ob das Objekt im Eigentum
	 * eines Nutzers ist oder jedoch wem das jeweilige Objekt zugeteilt wird. 
     **/
	public void getStatusForObject( int ownerId, int receiverId, int objectId, char type) 
			throws IllegalArgumentException {
		
		//findBerechtigungById() ?! 
		//Um die Zugeh�rigkeit des Objekts (Kontakt, Kontaktliste, Auspr�gung) zu erhalten? 
		//Objekt z.B. "a" -> getOwner() / getShareWith()
		//GUI -> ruft bei jedem Objekt diese Methode auf und setzt den Status.
		
	return;
	}

/** Das Objekt wird geteilt und durch die vollst�ndige R�ckgabe des geteilten Objekts, 
 * mit einer Berechtigung versehen. 
 * Wurde noch nicht getestet!
 * 	
 * @param ownerId
 * @param receiverId
 * @param objectId
 * @param type
 * @throws IllegalArgumentException
 */
	
	public void shareThisObject(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException {
		init();  
		
		Berechtigung b = new Berechtigung();
		b.getOwnerId();
		b.getReceiverId();
		b.getObjectId();
		b.getType();
		
		Kontaktliste kl = new Kontaktliste();
		kl.getId();
		
		Kontakt k = new Kontakt();
		k.getId();
				
		if(type== 'l') { 
			this.getListenstruktur(kl.getId(), k.getId());
		}
 
		if (type == 'k') {
			this.createBerechtigung(ownerId, receiverId, objectId, type);
	
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(objectId);
			for (int a=0; a < av.size(); a++) {
				if(av != null) {
					this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(),
							av.elementAt(a).getType());	
				}
			}
		}
	}		

/**
 * Nach Aufruf des Kontaktlistenkontakts den Titel der �bergebenen kontaktlistenId zur�ckgeben.
**/
//	
//	public void getListenbezeichnung (int kontaktlisteId, String titel) throws IllegalArgumentException {
//
//		KontaktlisteKontakt klk  = new KontaktlisteKontakt();
//		klk.setKontaktlisteId(klk.getKontaktlisteId());
//		
//		if (klk.equals(kontaktlisteId)) {
//			
//		}
//		this.klMapper.findByTitel(titel);
//		
//		return;
//	}
	
/**
 * Nach Aufruf des Kontaktlistenkontakts den Kontakt mit Auspraegung der �bergebenen kontaktId zur�ckgeben.
**/
	
	public void getListenstruktur (int kontaktlisteId, int kontaktId) throws IllegalArgumentException {

		KontaktlisteKontakt klk  = new KontaktlisteKontakt();
		klk.setKontaktId(klk.getKontaktId());
		
		Kontakt k = new Kontakt();
		k.setId(klk.getKontaktId());
		
		Berechtigung b = new Berechtigung();
		b.getOwnerId();
		b.getReceiverId();
		b.getObjectId();
		b.getType();
		
		this.getKontakteByKontaktliste(kontaktlisteId);
		Vector<Auspraegung> Auspraegung = getAllAuspraegungenByKontakt(kontaktId);
		
		if (klk.equals(kontaktId)) {
			for (Auspraegung a : Auspraegung) {
				this.getAllAuspraegungenByKontakt(a.getId());
				
				this.createBerechtigung(b.getOwnerId(), b.getReceiverId(), 
						b.getObjectId(), b.getType());			
			}
		
		}
	}
	
/*
 * *************************************************************************
 * ** ABSCHNITT, Ende: Share-Methoden
 * *************************************************************************
 * **
 */

/*
 * *************************************************************************
 * ** ABSCHNITT, Beginn: Sonstiges
 * *************************************************************************
 * **
 */
//Aktualisiere das Modifikationsdatum

public void saveModifikationsdatum(int id) throws IllegalArgumentException {
	init();
	this.kMapper.updateModifikationsdatum(id);
}


// Suchfunktion 


/**
 * Ende
 */

}

