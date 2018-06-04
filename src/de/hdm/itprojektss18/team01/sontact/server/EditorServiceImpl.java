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

//	/**
//	 * Ein spezielles Nutzer-Objekt wird referenziert.
//	 */
//	private Nutzer nutzer = null;

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
	 * ABSCHNITT - Beginn Methoden fuer das Erstellen von Objekten: 
	 * 
	 * Nutzer
	 * Kontakt + KontaktRegistrierung
	 * Kontaktliste + KontaktlisteRegistrierung
	 * Eigenschaft
	 * Auspraegung + Auspraegung fuer neue Eigenschaft
	 * Berechtigung
	 * 
	 * *************************************************************************
	 */

	
	
	/**
	 * Erzeugen eines neuen Nutzers, dieser wird angelegt und anschliessend in der DB
	 * gespeichert.
	 * 
	 * @param String emailAdress
	 * @return Nutzer
	 */
	
	public Nutzer createNutzer(String emailAddress) 
			throws IllegalArgumentException {
		
		init();
		
		// Das Erstellen eines Nutzerobjektes.
		Nutzer nutzer = new Nutzer();
		
		// Hier wird die (vorausgesetzte) gMail-Adresse des Nutzers hinterlegt.
		nutzer.setEmailAddress(emailAddress);

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.
		nutzer.setId(1);

		// Speichern und Eintragen des erstellten Nutzers in der DB.
		return this.nMapper.insert(nutzer);
	}
	
	
	/**
	 * Erzeugen eines neuen Kontakts, dieser wird angelegt und anschliessend 
	 * in der DB gespeichert. Der Kontakt wird automatisch der Default Kontaktliste 
	 * eines Nutzers "Meine Kontakte" zugeordnet und steht dem Nutzer nun 
	 * zu weiteren Funktionen zur Verfuegung.
	 * 
	 * @param String vorname
	 * @param String nachname
	 * @param Nutzer n
	 * @return void
	 */
	
	public void createKontakt(String vorname, String nachname, Nutzer n) 
			throws IllegalArgumentException {
		
		init();
		
		// Das Erstellen eines Kontakt Objektes mit Vor- und Nachnamen.
		Kontakt k = new Kontakt();
		k.setVorname(vorname);
		k.setNachname(nachname);
		
		// Setzen des Erstellungsdatums vom Typ Date. 
		k.setErstellDat(new Timestamp(System.currentTimeMillis()));
		
		// Zeitgleiches setzen des Modifikationsdatums vom Typ Date. 
		k.setModDat(new Timestamp(System.currentTimeMillis()));
		
		// Das Setzen der Owner-ID nach dem eingeloggten Nutzer.
		k.setOwnerId(n.getId());
		
		// Kontakt wird mit einem Objektypen vom Datentyp Char gekennzeichnet.
		k.setIdentifier('k');
		
		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.	 
		k.setId(1);

		// Kontaktliste und Kontakt werden der Zwischentabelle hinzugefuegt.
		this.addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Meine Kontakte"), 
				kMapper.insert(k));
	}
	
	
	/**
	 * Erzeugen eines neuen Kontakts f�r den Nutzers bei der Systemregistrierung. 
	 * Dieser Kontakt stellt eine Art "Visitenkarte" des eingeloggten Nutzers dar, 
	 * die er mit seinen Kontaktdaten nach belieben bef�llen kann.
	 * Gleichzeitig wird die Default Kontaktliste "Meine Kontakte" angelegt, 
	 * worin der Kontakt des Nutzers und alle weiteren, 
	 * die vom Nutzer angelegt werden, gespeichert sind.
	 * 
	 * @param String vorname 
	 * @param String nachname
	 * @param Nutzer n 
	 * @return Kontakt k1
	 */
	
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException {
		
		init();
		
		// Das Erstellen eines Kontakt Objektes mit Vor- und Nachnamen.
		Kontakt kontakt = new Kontakt();
		kontakt.setVorname(vorname);
		kontakt.setNachname(nachname);
		
		// Setzen des Erstellungsdatums vom Typ Date. 
		kontakt.setErstellDat(new Timestamp(System.currentTimeMillis()));
		
		// Zeitgleiches setzen des Modifikationsdatums vom Typ Date. 
		kontakt.setModDat(new Timestamp(System.currentTimeMillis()));
		
		// Das Setzen der Owner-ID nach dem eingeloggten Nutzer.
		kontakt.setOwnerId(n.getId());
		
		// Kontakt wird mit einem Objektypen vom Datentyp Char gekennzeichnet.
		kontakt.setIdentifier('r');

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.
		kontakt.setId(1);
		
		// Speichern und Eintragen des erstellten Kontakts in der DB.
		this.kMapper.insert(kontakt);

		// Gespeicherter Kontakt nach Objekttypen <'r'> auslesen.
		Kontakt k = getOwnKontakt(n);

		// Erzeugung der Default-Kontaktliste <Meine Kontakte>.
		createKontaktlisteRegistrierung(n);

		// Kontaktliste und Kontakt der Zwischentabelle hinzufuegen.
		addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Meine Kontakte"), k);

		return k;
	}
	
	
	/**
	 * Erzeugen einer neuen Kontaktliste, diese wird angelegt und anschliessend in der DB
	 * gespeichert. Die Kontaktliste steht dem Nutzer nun zu weiteren Funktionen zur Verfuegung.
	 * 
	 * @param String titel
	 * @param Nutzer n 
	 * @return Kontaktliste
	 */
	
	public Kontaktliste createKontaktliste(String titel, Nutzer n) 
			throws IllegalArgumentException {
		
		init();
		
		// Das Erstellen eines Kontaktlisten Objektes mit Titel und Owner-ID.
		Kontaktliste kl = new Kontaktliste();
		kl.setTitel(titel);
		kl.setOwnerId(n.getId());
		
		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.
		kl.setId(1);
		
		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		return this.klMapper.insert(kl);
	}
	

	/**
	 * Erzeugen der Default Kontaktliste "Meine Kontakte" des Nutzer. 
	 * Diese wird angelegt und anschliessend in der DB gespeichert. 
	 * Die Kontaktliste bildet den Speicherort aller angelegten Kontakte.
	 * 
	 * @param Nutzer n 
	 * @return Kontaktliste
	 */
	
	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n) 
			throws IllegalArgumentException {
		
		init();
		
		// Das Erstellen eines Kontaktlisten Objektes mit Titel "Meine Kontakte" und Owner-ID.
		Kontaktliste kl = new Kontaktliste();
		kl.setTitel("Meine Kontakte");
		kl.setOwnerId(n.getId());
		kl.setId(1);
		
		//Das Erstellen eines Kontaktlisten Objektes mit Titel "Mit mir geteilte Kontakte" und Owner-ID.
		Kontaktliste kl1 = new Kontaktliste();
		kl.setTitel("Mit mir geteilte Kontakte");
		kl.setOwnerId(n.getId());
		kl1.setId(1);
		
		//Liste in die Db einfügen.
		this.klMapper.insert(kl1);
		
		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		return this.klMapper.insert(kl);
	}
	
	
	/**
	 * Erzeugen einer Eigenschaft, welche angelegt und anschliessend in der DB
	 * gespeichert wird. Der Nutzer kann hiermit eigene Eigenschaften anlegen und mit Auspraegungen
	 * vervollstaendigen.
	 * 
	 * @param String bezeichnung
	 * @return Eigenschaft
	 */
	
	public Eigenschaft createEigenschaft(String bezeichnung) 
			throws IllegalArgumentException {
		
		init();

		// Das Erstellen eines Eigenschaft Objektes mit Bezeichnung.
		Eigenschaft e = new Eigenschaft();
		e.setBezeichnung(bezeichnung);

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.
		e.setId(1);

		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		return this.eMapper.insert(e);
	}

	
	/**
	 * Erzeugen einer neuen Auspraegung, welche angelegt und anschliessend in der DB
	 * gespeichert wird. Der Nutzer kann hiermit seine Kontakte mit Auspraegungen bereichern 
	 * und erhaelt die Moeglichkeit "Kontaktinformationen" in Form von Auspraegungen zu hinterlegen.
	 * 
	 * @param String wert
	 * @param int eigenschaftId
	 * @param int kontaktId
	 * @return Auspraegung
	 */

	public Auspraegung createAuspraegung(String wert, int eigenschaftId, 
			int kontaktId) throws IllegalArgumentException {

		init();

		// Das Erstellen eines Auspraegung Objektes mit Wert, EigenschaftId und KontaktId.
		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der 
		// naechst hoeheren ID angepasst wird.
		a.setId(1);

		// Anpassung des Modifikationsdatums des Kontakt Objektes
		this.saveModifikationsdatum(a.getKontaktId());
		
		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		return this.aMapper.insert(a);
	}
	
	
	/**
	 * Erzeugen einer neuen Auspraegung fuer eine vom Nutzer definierte Eigenschaft.
	 * Diese wird angelegt und anschliessend in der DB gespeichert.
	 * Der Nutzer kann hiermit in einem Zuge Eigenschaften anlegen und mit einem Auspraegungswert befuellen.
	 * 
	 * @param String bezeichnung
	 * @param String wert
	 * @param Kontakt k
	 * @return void
	 */
	
	public void createAuspraegungForNewEigenschaft(String bezeichnung, String wert, Kontakt k)
			throws IllegalArgumentException {

		init();
		
		// Erzeugen eines neuen Eigenschaft Objektes durch Aufruf der Methode <createEigenschaft>.
		Eigenschaft e = this.createEigenschaft(bezeichnung);
		
		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		this.createAuspraegung(wert, e.getId(), k.getId());
	}

	
	/**
	 * Erstellung einer neuen Berechtigung. Es wird eine neue Berechtigung fuer
	 * eine neue Teilhaberschaft an einem bestimmten Objekt erteilt.
	 * 
	 * @param int ownerId
	 * @param int receiverId
	 * @param int objectId
	 * @param char type
	 * @return Berechtigung
	 */
	
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException {

		init();

		// Erzeugen eines neuen Berechtigung Objektes mit OwnerId, ReceiverId, ObjectId und Type.
		Berechtigung b = new Berechtigung();
		b.setId(1);
		b.setOwnerId(ownerId);
		b.setReceiverId(receiverId);
		b.setObjectId(objectId);
		b.setType(type);

		// Speichern und Eintragen der erstellen Kontaktliste in der DB.
		return this.bMapper.insert(b);
	}
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT - Ende: Methoden fuer das Erstellen von Objekten
	 * *************************************************************************
	 */

	/**
	 * Anhand der identifizierenden Emailaddresse wird der Nutzer �berpr�ft, ob 
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
	 * Diese Methode identifiziert einen Nutzer anhand seiner Id.
	 */
	public Nutzer findNutzerById(int nutzerId) throws IllegalArgumentException{
		return this.nMapper.findNutzerById(nutzerId);
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
		
		/* entfernt Meine Kontakte, welche mit dem Nutzer in einer Eigentumsbeziehung stehen 
		 * sowie alle Auspraegungen eines jeden Kontaktes, 
		 * alle Kontaktlisteneintr�ge und alle Berechtigungen auf den Kontakt
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
			for(Berechtigung b : bvr) {
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
		 * zun�chst Abruf aller Berechtgigungen: Abgleich aller Berechtigungen
		 * (objektId) mit der Id des Kontaktes bei �bereinstummung entfernen der
		 * Berechtigungen f�r den Kontakt mit allen zugeh�rigen Berechtigungen die
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
		
		/* zun�chst Abruf aller Berechtgigungen:
		 * Abgleich aller Berechtigungen (objektId) mit der Id der Kontaktliste
		 * bei �bereinstummung entfernen der Berechtigungen f�r die Kontaktliste mit allen 
		 * zugeh�rigen Berechtigungen auf die Kontakte und Auspraegungen der Kontaktliste kl
		 */
		Vector<Berechtigung> bv = this.bMapper.findAll();
		for(Berechtigung b : bv) {
			if(b.getObjectId() == kl.getId()) {
				this.deleteBerechtigung(b);
			}	
		}

		// entfernen alle Kontakt-Eintr�ge der Kontaktliste kl aus <code>KontaktlisteKontakt</code>
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
	 * Kontaktliste "Meine Kontakte" für den Nutzer in der Db finden
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
	 * Speichern einer modifizierten Auspraegung.
	 * 
	 */
	public Auspraegung saveAuspraegung(Auspraegung a) throws IllegalArgumentException {
		init();
		this.saveModifikationsdatum(a.getKontaktId());
		return aMapper.update(a);
	}

	/**
	 *Eine Auspraegung wird aus einem Kontakt herausgel�scht. 
	 * 
	 */
	public void deleteAuspraegung(Auspraegung a) throws IllegalArgumentException {
		init();
		//das Modifikationsdatum des zugeh�rigen Kontakts wird gel�scht. 
		this.saveModifikationsdatum(a.getKontaktId());
		
		//Aufruf der DB-Methode zum entfernen des Wertes einer Auspraegung. 
		this.aMapper.delete(a);
	}

	/**
	 * Auslesen einer bestimmten Auspraegung anhand der zugeh�rigen Objektid.
	 * 
	 */
	public Auspraegung getAuspraegungById(int auspraegungId) throws IllegalArgumentException {

		init();

		return this.aMapper.findAuspraegungById(auspraegungId);
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
		
			Nutzer n = new Nutzer();
			n.setId(receiverId);
		
			Kontakt k = new Kontakt();
			k.setId(objectId);
			
			//Kontakt der Kontaktliste des Receivers hinzufügen die er abruft.
			this.addKontaktToKontaktliste(this.findKontaktlisteByTitel(n, "Mit mir geteilte Kontakte"), k);
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
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int nutzerId) 
			throws IllegalArgumentException {
		init();
		
		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByOwner(nutzerId);
		return b;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen �ber jene Objekte aus, welche mit dem
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
		
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT ENDE: Abruf der geteilten Objekte
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT BEGINN: Suchfunktion
	 * *************************************************************************
	 */
	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach dem 
	 * Namen und gibt diese zurueck. Hierbei wird Vor- und Nachname des Kontaktes mit 
	 * dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param name, vom Nutzer uebergebener String
	 * @param n Nutzer
	 * @return Vector<Kontakt>
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteByName(String name, Nutzer n) 
			throws IllegalArgumentException {
		init();
		return this.kMapper.findKontakteByName(name, n);
	}
	
	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Auspraegungen nach 
	 * dem Wert und gibt diesen zurueck. Hierbei wird die Auspraegung eines Kontaktes 
	 * mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param wert, vom Nutzer uebergebener String
	 * @param n Nutzer
	 * @return Vector<Kontakt>
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteByAuspraegung(String wert, Nutzer n) 
			throws IllegalArgumentException {
		
		return this.kMapper.findKontakteByAuspraegung(wert, n);
	}

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte EIgenschaften nach 
	 * der Bezeichnung und gibt diesen zurueck. Hierbei wird die Eigenschaft eines 
	 * Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param wert, vom Nutzer uebergebener String
	 * @param n Nutzer
	 * @return Vector<Kontakt>
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteByEigenschaft(String bezeichnung, Nutzer n) 
			throws IllegalArgumentException {
		
		return this.kMapper.findKontakteByEigenschaft(bezeichnung, n);
	}
		
	/**
	 * Die Suche nach Kontaktnamen, Auspraegungen oder Eigenschaften wird in der 
	 * folgenden Methode zusammengefasst aufgerufen. Je nach Treffer der Sucheingabe, 
	 * wird das aufgerufene Objekt zur�ckgeben. 
	 * 
	 * @param listBoxWert, testBoxWert
	 * @param n Nutzer
	 * @return Vector<Kontakt>
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> getKontakteBySuche(String listBoxWert, String testBoxWert,
			Nutzer n) throws IllegalArgumentException {
		init();

		if (listBoxWert == "name") {
			return this.getKontakteByName(testBoxWert, n);

		} else if (listBoxWert == "auspraegung") {
			return this.getKontakteByAuspraegung(testBoxWert, n);

		} else if (listBoxWert == "eigenschaft") {
			return this.kMapper.findKontakteByEigenschaft(testBoxWert, n);
			
		} else

			return null;
	}

	/*
	 * ************************************************************************* 
	 * ABSCHNITT Ende - Suchfunktion 
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* 
	 * ABSCHNITT Beginn -  Sonstiges
	 * *************************************************************************
	 */

	/**
	 * Prueft ueber einen boolean, ob sich ein Objekt (Kontakt k, Kontaktliste l oder
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
	
	
	/*
	 * ************************************************************************* **
	 * ABSCHNITT Ende - Sonstiges
	 * *************************************************************************
	 */
	
	/*
	 * ************************************************************************* **
	 * Auskommentierte Methoden
	 * *************************************************************************
	 */
	
	/**
	 * Nutzer
	 * 
	 * @param n
	 */
	// public void setNutzer(Nutzer n) throws IllegalArgumentException {
	// init(); nutzer = n;
	// }

	

}
