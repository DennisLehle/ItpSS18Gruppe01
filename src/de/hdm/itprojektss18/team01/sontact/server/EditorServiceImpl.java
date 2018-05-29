package de.hdm.itprojektss18.team01.sontact.server;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18.team01.sontact.server.db.AuspraegungMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.BerechtigungMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.EigenschaftMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.KontaktMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.KontaktlisteKontaktMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.KontaktlistenMapper;
import de.hdm.itprojektss18.team01.sontact.server.db.NutzerMapper;
import de.hdm.itprojektss18.team01.sontact.shared.EditorService;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

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
	 * Die Mapperklasse wird referenziert, die KontaktlistenKontakt-Objekten der
	 * Datenbank vergleicht.
	 */
	private KontaktlisteKontaktMapper klkMapper = null;
	/**
	 * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit der
	 * Datenbank vergleicht.
	 */

	/**
	 * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private EigenschaftMapper eMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Auspraegung-Objekte mit der Datenbank
	 * vergleicht.
	 */
	private AuspraegungMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die Berechtigung-Objekte mit der
	 * Datenbank vergleicht.
	 */
	private BerechtigungMapper bMapper = null;

	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Initialisierung
	 * *************************************************************************
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
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Initialisierung
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Nutzer-Objekt
	 * *************************************************************************
	 */
	/**
	 * Neues Nutzer-Objekt wird angelegt und in der Datenbank gespeichert.
	 */
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);

		/*
		 * Es wird eine vorl�ufige Id gesetzt die anschlie�end in der Datenbank nach
		 * Verf�gbarkeit angepasst wird.
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
	 */
	// public void setNutzer(Nutzer n) throws IllegalArgumentException {
	// init(); nutzer = n;
	// }

	/**
	 * Diese Methode sucht den Nutzer anhand der Emailadresse raus �berpr�fung
	 * Nutzer vorhanden
	 * 
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Nutzer getUserByGMail(String email) throws IllegalArgumentException {

		init();

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

		init();
		
//		//Verkn�pfung aus Zwischentabelle KontaktlisteKontakt l�schen
//		this.klkMapper.removeKontaktFromKontaktliste(kl, k);

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
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Methoden fuer Nutzer-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Kontakt-Objekte
	 * *************************************************************************
	 */

	/**
	 * Erzeugen eines neuen Kontakts der direkt in die Default Kontaktliste des
	 * jeweiligen Nutzers gespeichert wird ("Alle Kontakte").
	 * 
	 */
	public void createKontakt(String vorname, String nachname, Nutzer n) 
			throws IllegalArgumentException {

		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		kontakt.setIdentifier('k');

		kontakt.setId(1);

		// Kontaktliste und Kontakt der zwischen Tabelle hinzufügen.
		this.addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Alle Kontakte"), 
				kMapper.insert(kontakt));
		

	}

	/**
	 * Erzeugen eines neuen Kontakts bei der Registrierung plus anlegen einer
	 * Default Kontaktliste die dann zusammen mit dem erstellten Kontakt-Objekt in
	 * die zwischen Tabelle KontaktlisteKontakt.
	 * 
	 */
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException {

		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		kontakt.setIdentifier('r');

		kontakt.setId(1);


		// Kontakt in db vorhanden...
		this.kMapper.insert(kontakt);

		// Den zuvor eingespeicherten Kontakt mit identifier r aus der Db lesen und
		// speichern.
		Kontakt k = getOwnKontakt(n);

		// Default Kontaktliste erstellen. (Alle Kontakte)
		createKontaktlisteRegistrierung(n);

		// Kontaktliste und Kontakt der zwischen Tabelle hinzufügen.
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
		
		init();
		return this.kMapper.findKontaktById(id);
	}

	/**
	 * Auslesen des eigenen Kontaktes.
	 * 
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
	public Vector<Kontakt> getKontaktByName(String name, Nutzer n) throws IllegalArgumentException {
		
		init();
		
		return this.kMapper.findKontaktByName(name, n);
	}

	/**
	 * Auslesen aller Kontakte welche im Eigentum sind.
	 * 
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n) throws IllegalArgumentException {
		
		init(); 
		return this.kMapper.findAllByOwner(n.getId());
	}

	/**
	 * Setzten der Zuordnung eines Kontakts zur einer Kontaktliste
	 * 
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) 
			throws IllegalArgumentException {
		init();
		this.klkMapper.addKontaktToKontaktliste(kl, k);
	}

	/**
	 * Aufhebung der Zuordnung eines Kontakts zur einer Kontaktliste
	 * 
	 * @param k
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) 
			throws IllegalArgumentException {
		init();
		this.klkMapper.removeKontaktFromKontaktliste(kl, k);
	}

	/**
	 * Es werden alle Kontakte mit dessen Inhalt aufgerufen. Der Inhalt der Kontakte zeichnet sich 
	 * durch die Eigenschaften des jeweiligen Kontakts, sowie der dazugeh�rigen Auspr�gungen aus. 
	 * 
	 */
	public Vector<Kontakt> getAllKontakteByInhalt(Nutzer n, Auspraegung a, Eigenschaft e)
			throws IllegalArgumentException {

		Vector<Kontakt> kv = new Vector<Kontakt>();

		for (int i = 0; i < kv.size(); i++) {
			if (kv != null) {

				Kontakt k = new Kontakt();
				k.setId(kv.elementAt(i).getId());
				k.setVorname(k.getVorname());
				k.setNachname(k.getNachname());
				k.setOwnerId(k.getOwnerId());
				kv.addElement(k);
			}
			this.aMapper.findAuspraegungByKontakt(a.getKontaktId());
			this.eMapper.findEigenschaftById(e.getId());
		}

		return kv;
	}


	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Methoden fuer Kontakt-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Kontaktlisten-Objekte
	 * *************************************************************************
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
		
		init();

		// Alle Kontakte der Kontaktliste aus der DB entfernen.
		Vector<Kontakt> removeAllKontakte = klkMapper.findAllKontakteByKontaktliste(kl.getId());

		if (removeAllKontakte != null) {
			for (Kontakt k : removeAllKontakte) {
				this.klkMapper.removeKontaktFromKontaktliste(kl, k);
				
				//Kontakte werden permanent aus der Db entfernt. Dies soll nicht geschehen da wir nur die Verbdinung
				//des Kontaktes aus der Kontaktliste entfernen aber nicht den Kontakt an sich.
			//	this.removeKontakt(k);

			}
		}

		this.klMapper.delete(kl);
	}

	/**
	 * Alle Kontaktlisten eines Nutzers anhand OwnerId
	 * 
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException {
		//init();
		return this.klMapper.findKontaktlistenByOwner(n.getId());
	}

	/**
	 * Filtert f�r eine spezielle Kontaktliste, dessen Kontakte heraus.
	 * 
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteByKontaktliste(int kontaktlisteId) 
			throws IllegalArgumentException {
		return this.klkMapper.findAllKontakteByKontaktliste(kontaktlisteId);

	}

	/**
	 * Kontaktliste "Alle Kontakte" für den Nutzer in der Db finden
	 * (DefaultKontaktliste).
	 */

	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel) 
			throws IllegalArgumentException {
		
		init(); 
		
		return this.klMapper.findByTitel(n, titel);

	}

	/**
	 * Findet eine Kontaktliste anhand der Id Für TreeViewModel benötigt.
	 * 
	 * @param id
	 * @return
	 */
	public Kontaktliste getKontaktlisteById(int id) {
		
		init();
		
		return this.klMapper.findById(id);
	}

	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Methoden fuer Kontaktlisten-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Eigenschaft-Objekte
	 * *************************************************************************
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
	 * Gibt eine Eigenschaft anhand ihrer ID zur�ck.
	 * 
	 * @return Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft getEigenschaftById(int eigenschaftId) throws IllegalArgumentException {

		init();

		return this.eMapper.findEigenschaftById(eigenschaftId);
	}

	/**
	 * Gibt die vordefinierte Auswahl der Eigenschaften zur�ck.
	 * 
	 * @return Vector <Eigenschaft>
	 * @throws IllegalArgumentException
	 */

	public Vector<Eigenschaft> getEigenschaftAuswahl() throws IllegalArgumentException {

		init();

		return this.eMapper.findEigenschaftAuswahl();
	}

	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Methoden fuer Eigenschaft-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Auspraegung-Objekte
	 * *************************************************************************
	 */

	/**
	 * Erzeugen einer neuen Auspraegung.
	 * 
	 */

	public Auspraegung createAuspraegung(String wert, int eigenschaftId, 
			int kontaktId, Nutzer n /** int ownerId */
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
	public Auspraegung getAuspraegungById(int auspraegungId) throws IllegalArgumentException {

		init();

		return this.aMapper.findAuspraegungById(auspraegungId);
	}

	/**
	 * Gibt alle Auspraegungen eines Kontakts zur�ck.
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId) 
			throws IllegalArgumentException {

		init();

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

	public Eigenschaft getEigenschaftForAuspraegung(int eigenschaftId) throws IllegalArgumentException {
		return eMapper.findEigenschaftForAuspraegung(eigenschaftId);
		
	}
	/**
	 * Das Entfernen einer selbstdefinierten Eigenschaft mit ihren Auspraegungen des
	 * Kontakts.
	 */

	// getAllAuspraegungenByEigenschaft (?)

	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Ende: Methoden fuer Auspraegung-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT, Beginn: Methoden fuer Berechtigung-Objekte
	 * *************************************************************************
	 */

	/**
	 * Erstellung einer neuen Berechtigung. Es wird eine neue Berechtigung f�r
	 * eine neue Teilhaberschaft an einem bestimmten Objekt erteilt.
	 * 
	 * @param ownerId
	 * @param receiverId
	 * @param objectId
	 * @param type
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Berechtigung createBerechtigung(int ownerId, int receiverId, 
			int objectId, char type)
			throws IllegalArgumentException {

		init();

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
	 * Es wird eine Berechtigung f�r ein bestimmtes Objekt erteilt. Das
	 * tats�chlich geteilte Objekt wird angesprochen und als Typ identifiziert. Es
	 * wird eine Berechtigung f�r ein bestimmtes Objekt erteilt. Das tats�chlich
	 * geteilte Objekt wird angesprochen und als Typ identifiziert.
	 * 
	 * @param ownerId
	 * @param receiverId
	 * @param objectId
	 * @param type
	 * @throws IllegalArgumentException
	 */
	// CHECK
	public void shareObject(int ownerId, int receiverId, int objectId, char type) 
			throws IllegalArgumentException {
		init();
		if (type == 'l') {
			this.createBerechtigung(ownerId, receiverId, objectId, type);
			Vector<Kontakt> kv = this.getKontakteByKontaktliste(objectId);
			for (int k = 0; k < kv.size(); k++) {
				if (kv != null) {
					this.createBerechtigung(ownerId, receiverId, kv.elementAt(k).getId(), 
							kv.elementAt(k).getType());

					Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(kv.elementAt(k).getId());
					for (int a = 0; a < av.size(); a++) {
						if (av != null) {
							this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(),
									av.elementAt(a).getType());
						}
					}
				}
			}
		} else if (type == 'k') {
			this.createBerechtigung(ownerId, receiverId, objectId, type);
			// -> Checkboxen?
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(objectId);
			for (int a = 0; a < av.size(); a++) {
				if (av != null) {
					this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(), 
							av.elementAt(a).getType());
				}
			}
		} else if (type == 'a') {
			this.createBerechtigung(ownerId, receiverId, objectId, type);
		}
	}

	/**
	 * Das Loeschen einer Berechtigung. Diese Methode hebt die Berechtigung einer
	 * Teilhaberschaft zu einem bestimmten Objekttyp auf. Es werden z.B. alle
	 * abh�ngigen Objekte einer Kontaktliste, also Kontakte angesprochen, die
	 * wiederum Auspr�gungen beinhalten. Alle Objekte werden fortlaufend von der
	 * Berechtigung gel�st. Das Loeschen einer Berechtigung. Diese Methode hebt
	 * die Berechtigung einer Teilhaberschaft zu einem bestimmten Objekttyp auf. Es
	 * werden z.B. alle abh�ngigen Objekte einer Kontaktliste, also Kontakte
	 * angesprochen, die wiederum Auspr�gungen beinhalten. Alle Objekte werden
	 * fortlaufend von der Berechtigung gel�st.
	 * 
	 * @param b
	 * @throws IllegalArgumentException
	 */

	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException {
		init();

		// CHECK
		if (b.getType() == 'l') {

			// Erstellen des Kontaktvektors, um alle Berechtigungen der Kontakte einer
			// Kontaktliste zu erhalten
			Vector<Kontakt> kv = this.getKontakteByKontaktliste(b.getObjectId());

			for (int k = 0; k < kv.size(); k++) {
				if (kv != null) {

					// Erstellen des Auspraegungsvektors, um alle Berechtigungen der Auspraegung
					// eines Kontakts zu erhalten
					Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt
							(kv.elementAt(k).getId());

					for (int a = 0; a < av.size(); a++) {
						if (av != null) {

							// Abruf der Berechtigung auf die einzelnen Auspraegungs-Objekte 
							//innerhalb des Vektors
							Berechtigung b2 = new Berechtigung();
							b2.setOwnerId(b.getOwnerId());
							b2.setReceiverId(b.getReceiverId());
							b2.setObjectId(av.elementAt(a).getId());
							b2.setType(av.elementAt(a).getType());

							// L�schen der Vektoreintr�ge und den Berechtigungen
							this.bMapper.delete(b2);
						}
					}

					// Abruf der Berechtigung auf die einzelnen Kontakts-Objekte innerhalb des
					// Vektors
					Berechtigung b1 = new Berechtigung();
					b1.setOwnerId(b.getOwnerId());
					b1.setReceiverId(b.getReceiverId());
					b1.setObjectId(kv.elementAt(k).getId());
					b1.setType(kv.elementAt(k).getType());

					// L�schen der Vektoreintr�ge und den Berechtigungen
					this.bMapper.delete(b1);
				}
			}

			// L�schen der Kontaktlisten-Berechtigung
			this.bMapper.delete(b);

		} else if (b.getType() == 'k') {
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(b.getObjectId());
			for (int a = 0; a < av.size(); a++) {
				if (av != null) {
					Berechtigung b2 = new Berechtigung();
					b2.setOwnerId(b.getOwnerId());
					b2.setReceiverId(b.getReceiverId());
					b2.setObjectId(av.elementAt(a).getId());
					b2.setType(av.elementAt(a).getType());
					this.bMapper.delete(b2);
				}
			}

			this.bMapper.delete(b);

		} else if (b.getType() == 'a') {
			this.bMapper.delete(b);
		}
	}

	/**
	 * Gibt alle Objekt-Berechtigungen �ber jene Objekte aus, welche vom Nutzer
	 * geteilt wurden.
	 * 
	 * @param ownerId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int ownerId) 
			throws IllegalArgumentException {
		init();
		
		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByOwner(ownerId);
		return b;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen �ber jene Objekte aus, welche mit dem
	 * Nutzer geteilt wurden.
	 * 
	 * @param receiverId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int receiverId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByReceiver(receiverId);
		return b;
	}

	/*
	 * ************************************************************************* **
	 * ABSCHNITT ENDE: Berechtigungs-Objekte
	 * *************************************************************************
	 */
	/*
	 * ************************************************************************* **
	 * ABSCHNITT BEGINN: Abruf der geteilten Objekte
	 * *************************************************************************
	 */

	// noch offen (...)

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche vom Nutzer geteilt wurden.
	 * 
	 * @param ownerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontakt> getAllSharedKontakteByOwner(int ownerId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(ownerId);
		Vector<Kontakt> kv = new Vector<Kontakt>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && ownerId == bv.elementAt(b).getOwnerId() 
					&& bv.elementAt(b).getType() == 'k') {

				this.getKontaktById(bv.elementAt(b).getObjectId());

				Kontakt k = new Kontakt();
				k.setId(bv.elementAt(b).getObjectId());
				k.setVorname(k.getVorname());
				k.setNachname(k.getNachname());
				k.setErstellDat(k.getErstellDat());
				k.setModDat(k.getModDat());
				k.setOwnerId(k.getOwnerId());
				kv.addElement(k);
			}
		}

		return kv;
	}

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche mit dem Nutzer geteilt
	 * wurden.
	 * 
	 * @param receiverId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int receiverId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(receiverId);
		Vector<Kontakt> kv = new Vector<Kontakt>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && receiverId == bv.elementAt(b).getReceiverId() 
					&& bv.elementAt(b).getType() == 'k') {

				this.getKontaktById(bv.elementAt(b).getObjectId());

				Kontakt k = new Kontakt();
				k.setId(bv.elementAt(b).getObjectId());
				k.setVorname(k.getVorname());
				k.setNachname(k.getNachname());
				k.setErstellDat(k.getErstellDat());
				k.setModDat(k.getModDat());
				k.setOwnerId(k.getOwnerId());
				kv.addElement(k);
			}
		}

		return kv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche vom Nutzer geteilt
	 * wurden.
	 * 
	 * @param ownerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int ownerId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(ownerId);
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		for (int b = 0; b < bv.size(); b++) {

			if (bv != null && ownerId == bv.elementAt(b).getOwnerId() 
					&& bv.elementAt(b).getType() == 'l') {

				this.getKontaktlisteById(bv.elementAt(b).getObjectId());

				Kontaktliste kl = new Kontaktliste();
				kl.setId(bv.elementAt(b).getObjectId());
				kl.setTitel(kl.getTitel());
				kl.setOwnerId(kl.getOwnerId());
				klv.addElement(kl);
			}
		}

		return klv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche mit dem Nutzer
	 * geteilt wurden.
	 * 
	 * @param receiverId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int receiverId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(receiverId);
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		for (int b = 0; b < bv.size(); b++) {

			if (bv != null && receiverId == bv.elementAt(b).getReceiverId() 
					&& bv.elementAt(b).getType() == 'l') {
				this.getKontaktlisteById(bv.elementAt(b).getObjectId());

				Kontaktliste kl = new Kontaktliste();
				kl.setId(bv.elementAt(b).getObjectId());
				kl.setTitel(kl.getTitel());
				kl.setOwnerId(kl.getOwnerId());
				klv.addElement(kl);
			}
		}

		return klv;
	}

	/**
	 * Gibt alle Kontakte einer geteilten Kontakliste aus.
	 * 
	 * @param kontaktlisteId
	 * @return Vector<Kontakt>
	 * 
	 */
	public Vector<Kontakt> getAllSharedKontakteBySharedKontaktliste(int kontaktlisteId) {

		Vector<Berechtigung> bv = this.bMapper.findAll();
		Vector<Kontakt> kv = new Vector<Kontakt>();
		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && bv.elementAt(b).getType() == 'l') {
				this.getKontakteByKontaktliste(bv.elementAt(b).getObjectId());

				Kontakt k = new Kontakt();
				k.setId(bv.elementAt(b).getObjectId());
				k.setVorname(k.getVorname());
				k.setNachname(k.getNachname());
				k.setErstellDat(k.getErstellDat());
				k.setModDat(k.getModDat());
				k.setOwnerId(k.getOwnerId());
				kv.addElement(k);

			}

		}
		return null;
	}

	/**
	 * Gibt alle geteilten Auspraegungen zu einem Kontakt aus.
	 * 
	 * @param kontaktlisteId
	 * @return
	 */
	public Vector<Auspraegung> getAllSharedAuspraegungenByKontakt(int kontaktId) {

		Vector<Berechtigung> bv = this.bMapper.findAll();
		Vector<Auspraegung> av = new Vector<Auspraegung>();
		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && bv.elementAt(b).getType() == 'a') {
				this.getAuspraegungById(bv.elementAt(b).getObjectId());

				Auspraegung a = new Auspraegung();
				a.setId(bv.elementAt(b).getObjectId());
				a.setWert(a.getWert());
				a.setEigenschaftId(a.getEigenschaftId());
				a.setKontaktId(a.getKontaktId());
				a.setOwnerId(a.getOwnerId());
				av.addElement(a);
			}

		}
		return null;
	}

	/*
	 * ************************************************************************* 
	 * ABSCHNITT ENDE: Abruf der geteilten Objekte
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT BEGINN: Sonstiges
	 * *************************************************************************
	 */

	/**
	 * Gibt �ber R�ckgabewert boolean den Status eines Objektes (Kontakt k, Kontaktliste l oder
	 * Auspraegung a) zur�ck, ob dieser geteilt ist.
	 */
	public boolean getStatusForObject(int objectId) throws IllegalArgumentException {

		Vector<Berechtigung> bv = this.bMapper.findAll();
		for (int b = 0; b < bv.size(); b++) {
			if (objectId == bv.elementAt(b).getObjectId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Aktualisierung des Modifikationsdatums.
	 */
	public void saveModifikationsdatum(int id) throws IllegalArgumentException {
		init();
		this.kMapper.updateModifikationsdatum(id);
	}

	/**
	 * F�r die Suchfunktion, m�ssen alle m�glichen Suchkombinationen abgedeckt
	 * werden, damit die Suche benutzerfreundlich gew�hrleistet werden kann.
	 */
	public Vector<Kontakt> Suche (Nutzer n, Berechtigung b, Auspraegung a, Eigenschaft e) 
			throws IllegalArgumentException {
		
	Vector <Kontakt> kv = this.getAllKontakteByInhalt(n, a, e);
	Vector <Kontakt> ks = this.getAllSharedKontakteBySharedKontaktliste(b.getReceiverId());
	
	Vector<Kontakt> k = new Vector<Kontakt>();
	k.addAll(kv);
	k.addAll(ks);
	
	//TODO  --> this.kMapper.Suchfunktion  
		return k; 	
	}

	/*
	 * ************************************************************************* **
	 * ABSCHNITT ENDE: Sonstiges
	 * *************************************************************************
	 */

}
