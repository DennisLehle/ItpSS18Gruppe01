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
	 * Die Mapperklasse wird referenziert, die das Businessobject <Nutzer> mit der 
	 * Datenbank vergleicht.
	 */
	private NutzerMapper nMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <Kontakt> mit der 
	 * Datenbank vergleicht.
	 */
	private KontaktMapper kMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <Kontaktliste> mit der
	 * Datenbank vergleicht.
	 */
	private KontaktlistenMapper klMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <KontaktlisteKontakt> 
	 * mit der Datenbank vergleicht.
	 */
	private KontaktlisteKontaktMapper klkMapper = null;
	
	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <Eigenschaft> mit der
	 * Datenbank vergleicht.
	 */
	private EigenschaftMapper eMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <Auspraegung> mit der 
	 * Datenbank vergleicht.
	 */
	private AuspraegungMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <Berechtigung> mit der
	 * Datenbank vergleicht.
	 */
	private BerechtigungMapper bMapper = null;
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Initialisierung
	 * *************************************************************************
	 */

	/**
	 * Vollstaendiger Satz von Mappern mit deren Hilfe die Administratorenklasse 
	 * <EditorServiceImpl> mit der Datenbank kommunizieren kann.
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
	 * ABSCHNITT - Ende: Initialisierung
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Methoden fuer Nutzer
	 * *************************************************************************
	 */
	
	/**
	 * Erzeugen eines neuen Nutzers, dieser angelegt und anschlieï¿½end in der DB
	 * gespeichert wird.
	 * 
	 * @param String
	 *            emailAdress
	 * @return Nutzer
	 */
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException {
		init();
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfï¿½gbarkeit angepasst
		// wird.
		nutzer.setId(1);

		// Speichern eines Nutzers in der DB.
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
	 * Anhand der identifizierenden Emailaddresse wird der Nutzer ï¿½berprï¿½ft, ob 
	 * der Nutzer unter der hinterlegten Emailadresse existiert. 
	 * @param email
	 * @return Nutzer
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
	 * Ein Nutzer wird mit all seinen hinterlegten Objekten aus der Datenbank geloescht.
	 * @param Nutzer 
	 * @return void
	 */	
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
		
		/* entfernt alle Berechtigungen auf Kontakt-, Kontaktlisten- oder Aupraegungs- Objekte,
		 * welche nutzerseitig gesetzt sind
		 */
		Vector<Berechtigung> bvo = this.getAllBerechtigungenByOwner(n.getId());
		if(bvo != null) {
			for(Berechtigung b : bvo) {
				this.bMapper.delete(b);
			}
		}
		
		/* entfernt alle Kontaktlisten, welche mit dem Nutzer in einer Eigentumsbeziehung stehen 
		 * sowie alle Eintraege der Kontaktliste (aus KontaktlisteKontakt)
		 * und Berechtigungen auf die Kontaktliste
		 */
		Vector<Kontaktliste> klv = this.getKontaktlistenByOwner(n);
		if(klv != null) {
			for(Kontaktliste kl : klv) {
				this.deleteKontaktliste(kl);
			}
		}
		
		/* entfernt alle Kontakte, welche mit dem Nutzer in einer Eigentumsbeziehung stehen 
		 * sowie alle Auspraegungen eines jeden Kontaktes, 
		 * alle Kontaktlisteneinträge und alle Berechtigungen auf den Kontakt
		 */
		Vector<Kontakt> kv = this.getAllKontakteByOwner(n);
		if(kv != null) {
			for(Kontakt k: kv) {
				this.deleteKontakt(k);
			}
		}
		
		/* entfernt alle Berechtigungen auf Kontakt-, Kontaktliste- oder Auspraegungs- Objekte,
		 * welche fuer den Nutzer gesetzt sind

		 */
		Vector<Berechtigung> bvr = this.getAllBerechtigungenByReceiver(n.getId());
		if(bvr != null) {
			for(Berechtigung b : bvo) {
				this.bMapper.delete(b);
			}
		}
		// entfernen des Nutzer 
		this.nMapper.delete(n);		
	}
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Ende: Methoden fuer Nutzer
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Methoden fuer Kontakt
	 * *************************************************************************
	 */

	/**
	 * Erzeugen eines neuen Kontakts der direkt in die Default Kontaktliste des
	 * jeweiligen Nutzers gespeichert wird ("Alle Kontakte").
	 * @param String vorname
	 * @param String nachname
	 * @param Nutzer 
	 * @return void
	 * 
	 */
	public void createKontakt(String vorname, String nachname, Nutzer n) 
			throws IllegalArgumentException {
		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		
		//Setzen des Erstellungsdatums vom Typ Date. 
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		
		//Zeitgleiches setzen des Modifikationsdatums vom Typ Date. 
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		
		//Kontakt wird mit einem Objektypen vom Datentyp Char gekennzeichnet.
		kontakt.setIdentifier('k');
		
		//Setzen einer vorlaeufigen Id, die in der DB nach Verfï¿½gbarkeit angepasst wird.		 
		kontakt.setId(1);

		// Kontaktliste und Kontakt werden der Zwischentabelle hinzugefuegt.
		this.addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Alle Kontakte"), 
				kMapper.insert(kontakt));
		}

	/**
	 * Erzeugen eines neuen Kontakts bei der Systemregistrierung. Gleichzeitig wird eine 
	 * Default-Kontaktliste angelegt, worin der Kontakt gespeichert wird. 
	 * @param String vorname 
	 * @param String nachname
	 * @param Nutzer n 
	 * @return Kontakt kontakt
	 */
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException {
		init();
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		
		//Setzen des Erstellungsdatums vom Typ Date. 
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		
		//Zeitgleiches setzen des Modifikationsdatums vom Typ Date. 
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		kontakt.setOwnerId(n.getId());
		
		//Kontakt wird mit einem Objektypen vom Datentyp Char gekennzeichnet.
		kontakt.setIdentifier('r');

		kontakt.setId(1);
		this.kMapper.insert(kontakt);

		//Gespeicherter Kontakt nach Objekttypen <'r'> auslesen.
		Kontakt k = getOwnKontakt(n);

		//Erzeugung der Default-Kontaktliste <Alle Kontakte>.
		createKontaktlisteRegistrierung(n);

		// Kontaktliste und Kontakt der Zwischentabelle hinzufuegen.
		addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Alle Kontakte"), k);

		return k;
	}

	/**
	 * Aktualisierung eines modifizierten Kontakts.
	 * @param Kontakt k 
	 * @return Kontakt 
	 */
	public Kontakt saveKontakt(Kontakt k) throws IllegalArgumentException {
		init();
		//Modifikationsdatum wird aktualisiert. 
		k.setModDat(new Timestamp(System.currentTimeMillis()));
		return kMapper.update(k);
	}

	/**
	 * Loeschen eines Kontakts mit seinen Auspraegungen seinen
	 * Kontaktlistenzugehoerigkeiten und seinen Berechtigungen
	 * 
	 * @param Kontakt k
	 * @return void
	 */
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException {

		/*
		 * zunächst Abruf aller Berechtgigungen: Abgleich aller Berechtigungen
		 * (objektId) mit der Id des Kontaktes bei Übereinstummung entfernen der
		 * Berechtigungen für den Kontakt mit allen zugehörigen Berechtigungen die
		 * Auspraegungen des Kontaktes k
		 */
		Vector<Berechtigung> bv = this.bMapper.findAll();
		for (Berechtigung b : bv) {
			if (b.getObjectId() == k.getId()) {
				this.deleteBerechtigung(b);
			}
		}

		// entfernen des Kontaktes aus allen Kontatklisten
		this.klkMapper.deleteKontaktFromAllLists(k);

		// entfernen jeder einzelnen Auspraegung a des Kontaktes k
		Vector<Auspraegung> av = getAllAuspraegungenByKontakt(k.getId());
		if (av != null) {
			for (Auspraegung a : av) {
				this.aMapper.delete(a);
			}
		}

		// entfernen des Kontaktes
		this.kMapper.delete(k);
	}
	

	/**
	 * Auslesen eines Kontaktes anhand seiner Id.
	 * 
	 */
	public Kontakt getKontaktById(int id) throws IllegalArgumentException {
		init();
		return this.kMapper.findKontaktById(id);
	}

	/**
	 * Auslesen des Registierungs- Kontaktformulars eines Nutzers.
	 * @param n
	 * @return
	 */
	public Kontakt getOwnKontakt(Nutzer n) {
		return this.kMapper.findNutzerKontaktByIdentifier(n.getId());
	}

	/**
	 * Auslesen der Kontakte anhand des Vornamens. Bei der Eingabe eines Vornamens 
	 * wird in der Ausgabe eine Liste an Kontakten zurï¿½ckgegeben, die mit dem 
	 * Vornamen zu identifizieren sind. 
	 *@param String vorname 
	 *@param Nutzer n
	 *@return Vector <Kontakt>
	 */
	public Vector<Kontakt> getKontaktByVorname(String vorname, Nutzer n) 
			throws IllegalArgumentException {
		init();
		return this.kMapper.findKontaktByVorname(vorname, n);
	}
	
	/**
	 * Auslesen der Kontakte anhand des Nachnamens. Bei der Eingabe eines Nachnamens 
	 * wird in der Ausgabe eine Liste an Kontakten zurï¿½ckgegeben, die mit dem 
	 * Nachnamen zu identifizieren sind. 
	 * *@param String nachname 
	 *@param Nutzer n
	 *@return Vector <Kontakt>
	 */
	public Vector<Kontakt> getKontaktByNachname(String nachname, Nutzer n) 
			throws IllegalArgumentException {
		init();
		return this.kMapper.findKontaktByNachname(nachname, n);
	}

	/**
	 * Auslesen aller Kontakte, bei diesen der Nutzer als Eigentuemer hinterlegt ist.
	 * @param Nutzer n 
	 * @return Vector <Kontakt>
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n) throws IllegalArgumentException {
		init(); 
		return this.kMapper.findAllByOwner(n.getId());
	}

	/**
	 * Zuweisung eines Kontakt zu einer Kontaktliste.
	 * @param Kontaktliste kl 
	 * @param Kontakt k 
	 * @return void
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) 
			throws IllegalArgumentException {
		init();
		this.klkMapper.addKontaktToKontaktliste(kl, k);
	}

	/**
	 * Aufhebung der Zuweisung eines Kontakts zu einer Kontaktliste
	 * @param Kontaktliste kl
	 * @param Kontakt k
	 * @return void
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) 
			throws IllegalArgumentException {
		init();
		this.klkMapper.removeKontaktFromKontaktliste(kl, k);
	}

	/**
	 * Es werden alle Kontakte mit dem zugehoerigen Inhalt aufgerufen. Der Inhalt 
	 * der Kontakte zeichnet sich durch die Eigenschaften des jeweiligen Kontakts, 
	 * sowie der dazugehoerigen Auspraegungen aus. 
	 * @param Nutzer n 
	 * @param Auspraegung a
	 * @param Eigenschaft e
	 * @return kv
	 */
	public Vector<Kontakt> getAllKontakteByInhalt(Nutzer n, Auspraegung a, Eigenschaft e)
			throws IllegalArgumentException {
	
		Vector<Kontakt> kv = new Vector<Kontakt>();

		//Pruefung ob der Kontakt existiert.
		for (int i = 0; i < kv.size(); i++) {
			if (kv != null) {
				Kontakt k = new Kontakt();
				
				//Der Kontakt wird ausgelesen und identifziert.
				k.setId(kv.elementAt(i).getId());
				k.setVorname(k.getVorname());
				k.setNachname(k.getNachname());
				k.setOwnerId(k.getOwnerId());
				
				//Der Liste wird der Kontakt zurueckgegeben.
				kv.addElement(k);
			}
			//die Inhalte werden zugehoerig abgerufen.
			this.aMapper.findAuspraegungByKontakt(a.getKontaktId());
			this.eMapper.findEigenschaftById(e.getId());
		}
		return kv;
	}
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Ende: Methoden fuer Kontakt
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Methoden fuer Kontaktlisten
	 * *************************************************************************
	 */
	/**
	 * Erzeugen einer neuen Kontaktliste.
	 * @param String titel
	 * @param Nutzer n 
	 * @return Kontaktliste
	 */
	public Kontaktliste createKontaktliste(String titel, Nutzer n) throws IllegalArgumentException {
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel(titel);
		kontaktliste.setOwnerId(n.getId());
		
		//Setzen einer vorlaeufigen Id, die in der DB nach Verfï¿½gbarkeit angepasst wird.		 
		kontaktliste.setId(1);
		return this.klMapper.insert(kontaktliste);
	}

	/**
	 * Erzeugen der Default-Kontaktliste, die bei der Registrierung erstellt wird.
	 * @param Nutzer n 
	 * @return Kontaktliste
	 */
	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n) {
		init();
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setTitel("Alle Kontakte");
		kontaktliste.setOwnerId(n.getId());
		
		//Setzen einer vorlaeufigen Id, die in der DB nach Verfï¿½gbarkeit angepasst wird.		 
		kontaktliste.setId(1);
		return this.klMapper.insert(kontaktliste);

	}

	/**
	 * Speichern einer modifizierten Kontaktliste
	 * @param Kontaktliste kl
	 * @return void
	 */
	public void saveKontaktliste(Kontaktliste kl) throws IllegalArgumentException {
		init();
		klMapper.update(kl);
	}

	/**
	 * Loeschen einer Kontaktliste. Eine Kontaktliste wird mit allen
	 * zusammenhaengenden Objekten aus der DB entfernt.
	 * 
	 * @param Kontaktliste kl
	 * @return void
	 */
	public void deleteKontaktliste(Kontaktliste kl) throws IllegalArgumentException {
		init();
		
		/* zunächst Abruf aller Berechtgigungen:
		 * Abgleich aller Berechtigungen (objektId) mit der Id der Kontaktliste
		 * bei Übereinstummung entfernen der Berechtigungen für die Kontaktliste mit allen 
		 * zugehörigen Berechtigungen auf die Kontakte und Auspraegungen der Kontaktliste kl
		 */
		Vector<Berechtigung> bv = this.bMapper.findAll();
		for(Berechtigung b : bv) {
			if(b.getObjectId() == kl.getId()) {
				this.deleteBerechtigung(b);
			}	
		}

		// entfernen alle Kontakt-Einträge der Kontaktliste kl aus <code>KontaktlisteKontakt</code>
		Vector<Kontakt> kv = klkMapper.findAllKontakteByKontaktliste(kl.getId());
		if (kv != null) {
			for (Kontakt k : kv) {
				this.klkMapper.removeKontaktFromKontaktliste(kl, k);
			}
		}
		// entfernen der leeren Kontaktliste 
		this.klMapper.delete(kl);
	}

	/**
	 * Alle Kontaktlisten eines Nutzers anhand der OwnerId auslesen.
	 * @param Nutzer n
	 * @return Vector <Kontaktliste>
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException {
		//init();
		return this.klMapper.findKontaktlistenByOwner(n.getId());
	}

	/**
	 * Filtert fuer eine spezielle Kontaktliste, dessen Kontakte heraus.
	 * @param kl
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteByKontaktliste(int kontaktlisteId) 
			throws IllegalArgumentException {
		return this.klkMapper.findAllKontakteByKontaktliste(kontaktlisteId);
	}

	/**
	 * Kontaktliste "Alle Kontakte" fÃ¼r den Nutzer in der Db finden
	 * (DefaultKontaktliste).
	 */

	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel) 
			throws IllegalArgumentException {
		
		init(); 
		
		return this.klMapper.findByTitel(n, titel);

	}

	/**
	 * Alle Kontaktlisten werden anhand der Id ausgelesen.
	 * @param id
	 * @return
	 */
	public Kontaktliste getKontaktlisteById(int id) {
		init();
		return this.klMapper.findById(id);
	}
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Ende: Methoden fuer Kontaktlisten
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Beginn: Methoden fuer Eigenschaft
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
	 * Gibt eine Eigenschaft anhand ihrer ID zurï¿½ck.
	 * 
	 * @return Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft getEigenschaftById(int eigenschaftId) throws IllegalArgumentException {

		init();

		return this.eMapper.findEigenschaftById(eigenschaftId);
	}
	
	/**
	 * Auslesen der Eigenschaft anhand der Bezeichnung.
	 */
	public Vector<Eigenschaft> getEigenschaftByBezeichnung(String bezeichnung) 
			throws IllegalArgumentException {
		
		init();
		return this.eMapper.findEigenschaftByBezeichnung(bezeichnung);
	}

	/**
	 * Gibt die vordefinierte Auswahl der Eigenschaften zurï¿½ck.
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
			int kontaktId) throws IllegalArgumentException {

		init();

		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);

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
	 *Eine Auspraegung wird aus einem Kontakt herausgelï¿½scht. 
	 * 
	 */
	public void deleteAuspraegung(Auspraegung a) throws IllegalArgumentException {
		init();
		//das Modifikationsdatum des zugehï¿½rigen Kontakts wird gelï¿½scht. 
		this.saveModifikationsdatum(a.getKontaktId());
		
		//Aufruf der DB-Methode zum entfernen des Wertes einer Auspraegung. 
		this.aMapper.delete(a);
	}

	/**
	 * Auslesen einer bestimmten Auspraegung anhand der zugehï¿½rigen Objektid.
	 * 
	 */
	public Auspraegung getAuspraegungById(int auspraegungId) throws IllegalArgumentException {

		init();

		return this.aMapper.findAuspraegungById(auspraegungId);
	}

	/**
	 * Auslesen der Eigenschaft anhand der definierten Bezeichnung.
	 */
	public Vector<Auspraegung> getAuspraegungByWert(String wert, Nutzer n) 
			throws IllegalArgumentException {
		
		init();
		return this.aMapper.findAuspraegungByWert(wert, n);
	}
	
	/**
	 * Ruft eine Liste aller Auspraegungen des zugehoerigen Kontakts auf.
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId) 
			throws IllegalArgumentException {

		init();
		return this.aMapper.findAuspraegungByKontakt(kontaktId);
	}

	/**
	 * Eine neue Eigenschaft fuer eine neue Auspraegung setzen
	 *
	 */
	public void createAuspraegungForNewEigenschaft(String bezeichnung, String wert, Kontakt k)
			throws IllegalArgumentException {

		init();

		Eigenschaft e = this.createEigenschaft(bezeichnung);
		this.createAuspraegung(wert, e.getId(), k.getId());
		
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
	 * Erstellung einer neuen Berechtigung. Es wird eine neue Berechtigung fï¿½r
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
	 * Es wird eine Berechtigung fï¿½r ein bestimmtes Objekt erteilt. Das
	 * tatsï¿½chlich geteilte Objekt wird angesprochen und als Typ identifiziert. Es
	 * wird eine Berechtigung fï¿½r ein bestimmtes Objekt erteilt. Das tatsï¿½chlich
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
	 * abhaengigen Objekte einer Kontaktliste, also Kontakte angesprochen, die
	 * wiederum Auspraegungen beinhalten. Alle Objekte werden fortlaufend von der
	 * Berechtigung geloest. 
	 * 
	 * @param b
	 * @throws IllegalArgumentException
	 */

	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException {
		init();

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

							// Lï¿½schen der Vektoreintrï¿½ge und den Berechtigungen
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

					// Lï¿½schen der Vektoreintrï¿½ge und den Berechtigungen
					this.bMapper.delete(b1);
				}
			}

			// Lï¿½schen der Kontaktlisten-Berechtigung
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
	 * Gibt alle Objekt-Berechtigungen ï¿½ber jene Objekte aus, welche vom Nutzer
	 * geteilt wurden.
	 * 
	 * @param ownerId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int nutzerId) 
			throws IllegalArgumentException {
		init();
		
		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByOwner(nutzerId);
		return b;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen ï¿½ber jene Objekte aus, welche mit dem
	 * Nutzer geteilt wurden.
	 * 
	 * @param receiverId
	 * @return Berechtigungen
	 */
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int nutzerId) 
			throws IllegalArgumentException {
		init();

		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByReceiver(nutzerId);
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

	// Check noch offen (...)

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche vom Nutzer geteilt wurden.
	 * 
	 * @param ownerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontakt> getAllSharedKontakteByOwner(int nutzerId) throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(nutzerId);
		Vector<Kontakt> kv = new Vector<Kontakt>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && nutzerId == bv.elementAt(b).getOwnerId()&& bv.elementAt(b).getType() == 'k') {
				Kontakt k = this.getKontaktById(bv.elementAt(b).getObjectId());
				kv.addElement(k);
			}
		}

		return kv;
	}

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche mit dem Nutzer geteilt
	 * wurden.
	 * 
	 * @param nutzerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int nutzerId) throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(nutzerId);
		Vector<Kontakt> kv = new Vector<Kontakt>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && nutzerId == bv.elementAt(b).getReceiverId() && bv.elementAt(b).getType() == 'k') {
				Kontakt k = this.getKontaktById(bv.elementAt(b).getObjectId());
				kv.addElement(k);
			}
		}

		return kv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche vom Nutzer geteilt
	 * wurden.
	 * 
	 * @param nutzerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int nutzerId) throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(nutzerId);
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && nutzerId == bv.elementAt(b).getOwnerId() && bv.elementAt(b).getType() == 'l') {
				Kontaktliste kl = this.getKontaktlisteById(bv.elementAt(b).getObjectId());
				klv.addElement(kl);
			}
		}

		return klv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche mit dem Nutzer
	 * geteilt wurden.
	 * 
	 * @param nutzerId
	 * @return Vector<Kontakt>
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int nutzerId) throws IllegalArgumentException {
		init();

		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(nutzerId);
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		for (int b = 0; b < bv.size(); b++) {
			if (bv != null && nutzerId == bv.elementAt(b).getReceiverId() && bv.elementAt(b).getType() == 'l') {
				Kontaktliste kl = this.getKontaktlisteById(bv.elementAt(b).getObjectId());
				klv.addElement(kl);
			}
		}

		return klv;
	}
	
	/**
	 * Gibt alle geteilten Auspraegungen zu einem geteilten Kontakt k mit einem Nutzer n aus.
	 * 
	 * @param Kontakt k, Nutzer n 
	 * @return Vector<Auspraegung>
	 */
	public Vector<Auspraegung> getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n) {
		init();

		Vector<Berechtigung> bv = this.bMapper.findAll();
		Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(k.getId());
		
		Vector<Auspraegung> avshare = new Vector<Auspraegung>();
		
		for (Auspraegung a : av) {
		
			for (Berechtigung b : bv) {
			
				if (a.getId() == b.getObjectId() && b.getReceiverId() == n.getId()) {
				
					this.getAuspraegungById(a.getId());
					avshare.addElement(a);
				}
			}
		}

		return avshare;
	}
	
	
	
	
//	--> ?
	
//	/**
//	 * Suchte fÃ¼r den Nutzer eine einzelne Berechtigung fÃ¼r einen einzelnen Kontakt heraus den
//	 * er zuvor selektiert hat. Damit man ihn auch einzeln entfernen kann.
//	 */
//	public Berechtigung getABerechtigungByReceiver(Nutzer n) throws IllegalArgumentException {
//		return this.bMapper.findASingleBerechtigung(n.getId());
//	}
	
	
	
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
	 * Prüft über einen boolean, ob sich ein Objekt (Kontakt k, Kontaktliste l oder
	 * Auspraegung a) sich in einem geteilten Status befindet.
	 */
	public boolean getStatusForObject(int objectId) throws IllegalArgumentException {

		// Auslesen alle Berechtigungen
		Vector<Berechtigung> bv = this.bMapper.findAll();

		// Abgleich der ObjectId mit den geteilten Objekte
		for (Berechtigung b : bv) {
			if (objectId == b.getObjectId()) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Aktualisierung des Modifikationsdatums, durch aufrufen der Methode.
	 */
	public void saveModifikationsdatum(int id) throws IllegalArgumentException {
		init();
		this.kMapper.updateModifikationsdatum(id);
	}
	
	
	
	
	

	@Override
	public Vector<Kontakt> sucheKontakt(String vorname, String nachname, String wert, String bezeichnung, Nutzer n)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	// --> ?
	@Override
	public Berechtigung getABerechtigungByReceiver(Nutzer n) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	/*
	 * ************************************************************************* **
	 * ABSCHNITT ENDE: Sonstiges
	 * *************************************************************************
	 */

}
