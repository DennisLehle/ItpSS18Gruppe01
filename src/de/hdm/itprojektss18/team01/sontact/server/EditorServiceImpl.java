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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Implementierung des serverseitigen RPC-Services fuer den Editor.
 */
public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {

	public EditorServiceImpl() throws IllegalArgumentException {

	}

	/**
	 * Serialisierung
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die Mapperklasse wird referenziert, die das <code>Nutzer</code>-Objekt mit der
	 * Datenbank vergleicht.
	 */
	private NutzerMapper nMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das <code>Kontakt</code>-Objekt mit der
	 * Datenbank vergleicht.
	 */
	private KontaktMapper kMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das <code>Kontaktliste</code>-Objekt mit
	 * der Datenbank vergleicht.
	 */
	private KontaktlistenMapper klMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject
	 * <code>KontaktlisteKontakt</code> mit der Datenbank vergleicht.
	 */
	private KontaktlisteKontaktMapper klkMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <code>Eigenschaft</code> mit
	 * der Datenbank vergleicht.
	 */
	private EigenschaftMapper eMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <code>Auspraegung</code> mit
	 * der Datenbank vergleicht.
	 */
	private AuspraegungMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die das Businessobject <code>Berechtigung</code> mit
	 * der Datenbank vergleicht.
	 */
	private BerechtigungMapper bMapper = null;

	/*
	 * *****************************************************************************
	 * **** ABSCHNITT ANFANG: Initialisierung
	 * *****************************************************************************
	 * ****
	 */

	/**
	 * Vollstaendiger Satz von Mappern mit deren Hilfe die Administratorenklasse
	 * EditorServiceImpl-Klasse mit der Datenbank kommunizieren kann.
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
	 * *****************************************************************************
	 * ABSCHNITT ENDE: Initialisierung
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: NUTZER
	 * *****************************************************************************
	 */

	/**
	 * Erzeugen eines neuen Nutzers, dieser wird angelegt und anschliessend in der
	 * DB gespeichert.
	 * 
	 * @param email des Nutzers
	 * @return Nutzer
	 */
	public Nutzer createNutzer(String email) throws IllegalArgumentException {

		// Das Erstellen eines Nutzerobjektes.
		Nutzer nutzer = new Nutzer();

		// Hier wird die (vorausgesetzte) gMail-Adresse des Nutzers hinterlegt.
		nutzer.setEmailAddress(email);

		// Setzen einer vorlaeufigen Id, die in der DB nach Verfuegbarkeit mit der
		// naechst hoeheren ID angepasst wird.
		nutzer.setId(1);

		// Speichern und Eintragen des erstellten Nutzers in der DB.
		return this.nMapper.insert(nutzer);
	}

	/**
	 * Gibt alle Nutzer des Systems zurueck.
	 */
	public Vector<Nutzer> findAllNutzer (){
		return this.nMapper.findAll();
	}

	/**
	 * Anhand der identifizierenden Emailaddresse wird ueberprueft, ob diese als
	 * Nutzer bereits existiert.
	 * 
	 * @param email des Nutzers
	 * @return Nutzer
	 */
	public Nutzer getUserByGMail(String email) throws IllegalArgumentException {

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
	 * Auslesen eines Nutzers anhand der id.
	 */
	public Nutzer getNutzerById(int nutzerId) throws IllegalArgumentException {

		return this.nMapper.findNutzerById(nutzerId);
	}

	/**
	 * Ein Nutzer wird mit allen Zusammenhaengenden Objekten aus der Datenbank
	 * geloescht.
	 * 
	 * @param n der Nutzer der geloescht werden soll
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {

		/*
		 * entfernt alle Berechtigungen auf Kontakt-, Kontaktlisten- oder Aupraegungs-
		 * Objekte, welche nutzerseitig gesetzt sind
		 */
		Vector<Berechtigung> bvo = this.getAllBerechtigungenByOwner(n.getId());
		if (bvo != null) {
			for (Berechtigung b : bvo) {
				this.bMapper.delete(b);
			}
		}

		/*
		 * entfernt alle Kontaktlisten, welche mit dem Nutzer in einer
		 * Eigentumsbeziehung stehen sowie alle Eintraege der Kontaktliste (aus
		 * KontaktlisteKontakt) und Berechtigungen auf die Kontaktliste
		 */
		Vector<Kontaktliste> klv = this.getKontaktlistenByOwner(n);
		if (klv != null) {
			for (Kontaktliste kl : klv) {
				this.deleteKontaktliste(kl);
			}
		}

		/*
		 * entfernt alle Kontakte, welche mit dem Nutzer in einer Eigentumsbeziehung
		 * stehen sowie alle Auspraegungen eines jeden Kontaktes, alle
		 * Kontaktlisteneintr�ge und alle Berechtigungen auf den Kontakt
		 */
		Vector<Kontakt> kv = this.getAllKontakteByOwner(n);
		if (kv != null) {
			for (Kontakt k : kv) {
				this.deleteKontakt(k);
			}
		}

		/*
		 * entfernt alle Berechtigungen auf Kontakt-, Kontaktliste- oder Auspraegungs-
		 * Objekte, welche fuer den Nutzer gesetzt sind
		 */
		Vector<Berechtigung> bvr = this.getAllBerechtigungenByReceiver(n.getId());
		if (bvr != null) {
			for (Berechtigung b : bvr) {
				this.bMapper.delete(b);
			}
		}

		// entfernen des Nutzer
		this.nMapper.delete(n);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: NUTZER
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: KONTAKT
	 * *****************************************************************************
	 */

	/**
	 * Erzeugen eines neuen Kontakts, dieser wird angelegt und anschliessend in der
	 * DB gespeichert. Der Kontakt wird automatisch der Default Kontaktliste eines
	 * Nutzers "Meine Kontakte" zugeordnet und steht dem Nutzer nun zu weiteren
	 * Funktionen zur Verfuegung.
	 * 
	 * @param vorname, nachname, n des Kontakt der erstellt wird
	 * @return k1 der erstellte Kontakt
	 */
	public Kontakt createKontakt(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException {

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

		Kontakt k1 = new Kontakt();
		k1 = kMapper.insert(k);

		// Kontaktliste und Kontakt werden der Zwischentabelle hinzugefuegt.
		this.addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Meine Kontakte"), k1);
		return k1;
	}

	/**
	 * Erzeugen eines neuen Kontakts f�r den Nutzers bei der Systemregistrierung.
	 * Dieser Kontakt stellt eine Art "Visitenkarte" des eingeloggten Nutzers dar,
	 * die er mit seinen Kontaktdaten nach belieben bef�llen kann. Gleichzeitig
	 * wird die Default Kontaktliste "Meine Kontakte" angelegt, worin der Kontakt
	 * des Nutzers und alle weiteren, die vom Nutzer angelegt werden, gespeichert
	 * sind.
	 * 
	 * @param vorname, nachname, n
	 * @return k
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
		Kontakt k = this.kMapper.insert(kontakt);

		// Gespeicherter Kontakt nach Objekttypen <'r'> auslesen.
		// Kontakt k = getOwnKontakt(n);

		// Erzeugung der Default-Kontaktliste <Meine Kontakte>.
		createKontaktlisteRegistrierung(n);

		// Kontaktliste und Kontakt der Zwischentabelle hinzufuegen.
		addKontaktToKontaktliste(findKontaktlisteByTitel(n, "Meine Kontakte"), k);

		return k;
	}

	/**
	 * Modifikation eines Kontakts.
	 * 
	 * @param k Kontakt der modifiziert wird
	 * @return k Rueckgabe des modifizierten Kontakts
	 */
	public Kontakt saveKontakt(Kontakt k) throws IllegalArgumentException {

		// Modifikationsdatum wird aktualisiert.
		k.setModDat(new Timestamp(System.currentTimeMillis()));
		return kMapper.update(k);
	}

	/**
	 * Loeschen eines Kontakts mit seinen Auspraegungen seinen
	 * Kontaktlistenzugehoerigkeiten und seinen Berechtigungseintraegen
	 * 
	 * @param k der zu loeschende Kontakt
	 */
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException {

		/*
		 * zunaechst Abruf aller Berechtgigungen: Abgleich aller Berechtigungen
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
	 */
	public Kontakt getKontaktById(int id) throws IllegalArgumentException {

		return this.kMapper.findKontaktById(id);
	}

	/**
	 * Auslesen des Registierungs- Kontaktformulars eines Nutzers.
	 * 
	 * @param n Nutzer der gesucht werden soll
	 * @return Rueckgabe des Kontaktes
	 */
	public Kontakt getOwnKontakt(Nutzer n) throws IllegalArgumentException {

		return this.kMapper.findNutzerKontaktByIdentifier(n.getId());
	}

	/**
	 * Ausgabe aller Kontakte, bei welchen der Nutzer als Eigentuemer hinterlegt
	 * ist.
	 * 
	 * @param n Nutzer von dem alle seine Kontakte gesucht werden sollen
	 * @return  Rueckgabe aller Kontakte des Nutzers
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n)
			throws IllegalArgumentException {

		return this.kMapper.findAllByOwner(n.getId());
	}


	/**
	 * Zuweisung eines Kontakt zu einer Kontaktliste.
	 * 
	 * @param kl, k der Kontakt der zu einer Kontakltiste zugewiesen werden soll
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k)
			throws IllegalArgumentException {

		this.klkMapper.addKontaktToKontaktliste(kl, k);
	}

	/**
	 * Aufhebung der Zuweisung eines Kontakts zu einer Kontaktliste
	 * 
	 * @param kl, k Kontakt aus einer Kontaktliste loeschen
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k)
			throws IllegalArgumentException {

		this.klkMapper.removeKontaktFromKontaktliste(kl, k);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /KONTAKT
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: KONTAKTLISTE
	 * *****************************************************************************
	 */

	/**
	 * Erzeugen einer neuen Kontaktliste, diese wird angelegt und anschliessend in
	 * der DB gespeichert. Die Kontaktliste steht dem Nutzer nun zu weiteren
	 * Funktionen zur Verfuegung.
	 * 
	 * @param titel, n der Nutzer der eine Kontakltiste mit einem Titel erzeugt
	 * @return kl Rueckgabe der Kontakltiste die erzeugt wurde
	 */
	public Kontaktliste createKontaktliste(String titel, Nutzer n)
			throws IllegalArgumentException {

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
	 * Erzeugen der Default Kontaktliste "Meine Kontakte" des Nutzer. Diese wird
	 * angelegt und anschliessend in der DB gespeichert. Die Kontaktliste bildet den
	 * Speicherort aller angelegten Kontakte.
	 * 
	 * @param n der Nutzer der sie erzeugt
	 * @return kl Ruckegabe der Kontaktliste
	 */
	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n) throws IllegalArgumentException {

		// Erstellen der Default-Kontaktliste 'Meine Kontakte'
		Kontaktliste kl = new Kontaktliste();
		kl.setTitel("Meine Kontakte");
		kl.setOwnerId(n.getId());
		kl.setId(1);

		// Erstellen der Defaukt-Kontaktliste 'Mit mir geteilte Kontakte'
		createKontaktliste("Mit mir geteilte Kontakte", n);

		return this.klMapper.insert(kl);
	}
	
	
	/**
	 * Pruefung ob ein Kontakt in einer Kontaktliste bereits vorhanden ist.
	 * Ist dieser schon vorhanden wird true zurcükgegeben.
	 * 
	 * @param kontaktlisteId der Kontaktliste die ueberprueft wird.
	 * @param k Vector von Kontakten die mit der Kontaktliste abgeglichen werden.
	 * @return check 
	 */
	public boolean checkKontaktliste(int kontaktlisteId, Vector<Kontakt> k) {
		Boolean check = false;
		//Leeren Vector erstellen
		Vector<Kontakt> kontakte = new Vector<Kontakt>();
		//Alle gefundenen Kontakte der Kontaktliste dem Vector hinzufügen.
		kontakte.addAll(this.getKontakteByKontaktliste(kontaktlisteId));
		

			for (int j = 0; j <= k.size(); j++) {
				if(kontakte.contains(k.elementAt(j))){
					check = true;
				} else {
					check = false;
				}
			}
		
		return check;
			
		}
		
	/**
	 * Speichern einer modifizierten Kontaktliste
	 * 
	 * @param kl die Kontakltiste die modifiziert wurde
	 */
	public void saveKontaktliste(Kontaktliste kl) throws IllegalArgumentException {

		this.klMapper.update(kl);
	}

	/**
	 * Loeschen einer Kontaktliste. Eine Kontaktliste wird mit allen
	 * zusammenhaengenden Objekten aus der DB entfernt.
	 * 
	 * @param kl Kontkaliste die geloescht werden soll
	 */
	public void deleteKontaktliste(Kontaktliste kl) throws IllegalArgumentException {

		/*
		 * zunaechst Abruf aller Berechtgigungen: Abgleich aller Berechtigungen
		 * (objektId) mit der Id der Kontaktliste bei Uebereinstummung entfernen der
		 * Berechtigungen fuer die Kontaktliste mit allen zugehoerigen Berechtigungen
		 * auf die Kontakte und Auspraegungen der Kontaktliste kl
		 */
		Vector<Berechtigung> bv = this.bMapper.findAll();
		for (Berechtigung b : bv) {
			if (b.getObjectId() == kl.getId()) {
				this.deleteBerechtigung(b);
			}
		}

		// entfernen alle Kontakt-Eintr�ge der Kontaktliste kl aus
		// <code>KontaktlisteKontakt</code>
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
	 * Auslesen einer Kontaktliste anhand id.
	 * 
	 * @param id der Kontaktliste die Sie eindeutig macht
	 * @return Kontaktliste
	 */
	public Kontaktliste getKontaktlisteById(int id) throws IllegalArgumentException {

		return this.klMapper.findById(id);
	}

	/**
	 * 
	 * Ausgabe Kontaktliste nach Name
	 */
	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel) throws IllegalArgumentException {

		return this.klMapper.findByTitel(n, titel);
	}

	/**
	 * Ausgabe aller Kontaktlisten eines Nutzers
	 * 
	 * @param n Nutzer von dem die Kontakltisten ausgegeben werden sollen
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException {
		
		Vector<Kontaktliste> owenNShared = new Vector<Kontaktliste>();
		owenNShared.addAll(this.klMapper.findKontaktlistenByOwner(n.getId()));
		
		if(this.getAllSharedKontaktlistenByReceiver(n.getId()) != null) {
		owenNShared.addAll(this.getAllSharedKontaktlistenByReceiver(n.getId()));
		}
		
		return owenNShared;
		
	}

	/**
	 * Ausgabe der Kontakte einer Kontaktliste
	 * 
	 * @param kontaktlisteId Kontaktliste in der gesucht werden soll
	 */
	public Vector<Kontakt> getKontakteByKontaktliste(int kontaktlisteId) throws IllegalArgumentException {

		return this.klkMapper.findAllKontakteByKontaktliste(kontaktlisteId);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /KONTAKTLISTE
	 * *****************************************************************************
	 */ 

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: EIGENSCHAFT & AUSPRAEGUNG
	 * *****************************************************************************
	 */

	/**
	 * Erzeugen einer Eigenschaft, welche angelegt und anschliessend in der DB
	 * gespeichert wird. Der Nutzer kann hiermit eigene Eigenschaften anlegen und
	 * mit Auspraegungen vervollstaendigen.
	 * 
	 * @param bezeichnung Name der Eigenschaft die erzeugt wird
	 * @return e EIgenschaft die in der db anglegt wurde
	 */
	public Eigenschaft createEigenschaft(String bezeichnung)
			throws IllegalArgumentException {
		Eigenschaft e = new Eigenschaft();
		e.setBezeichnung(bezeichnung);
		e.setId(1);
		return this.eMapper.insert(e);
	}

	/**
	 * Erzeugen von mehereren Eigenschaften, welche angelegt und anschliessend in der DB
	 * gespeichert werden.
	 * 
	 */
	public Vector<Eigenschaft> createEigenschaftV(Vector<String> bezeichnung)
			throws IllegalArgumentException {

		Vector<Eigenschaft> ev = new Vector<Eigenschaft>();

		for (int i = 0; i < bezeichnung.size(); i++) {

			Eigenschaft e = new Eigenschaft();
			e.setBezeichnung(bezeichnung.elementAt(i));
			e.setId(1);

			ev.add(this.eMapper.insert(e));
		}

		return ev;
	}

	/**
	 * Speichern einer modifizierten Eigenschaft.
	 */
	public Eigenschaft saveEigenschaft(Eigenschaft e)
			throws IllegalArgumentException {

		return eMapper.update(e);
	}

	/**
	 * Loeschen einer Eigenschaft.
	 */
	public void deleteEigenschaft(Eigenschaft e)
			throws IllegalArgumentException {

		this.eMapper.delete(e);
	}

	/**
	 * Gibt eine Eigenschaft anhand ihrer ID zurueck.
	 * 
	 * @return Eigenschaft
	 */
	public Eigenschaft getEigenschaftById(int eigenschaftId)
			throws IllegalArgumentException {

		return this.eMapper.findEigenschaftById(eigenschaftId);
	}

	/**
	 * Gibt eine vordefinierte Auswahl an Eigenschaften zuruck.
	 * 
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl()
			throws IllegalArgumentException {

		return this.eMapper.findEigenschaftAuswahl();
	}

	// -> ?
	/**
	 * Auslesen einer Eigenschaft anhand der uebergebener Bezeichnung.
	 */
	public Eigenschaft getEigenschaftByBezeichnung(String bezeichnung)
			throws IllegalArgumentException {

		return this.eMapper.findEigenschaft(bezeichnung);
	} // -> ?

	/**
	 * Erzeugen einer neuen Auspraegung, welche angelegt und anschliessend in der DB
	 * gespeichert wird. Der Nutzer kann hiermit seine Kontakte mit Auspraegungen
	 * bereichern und erhaelt die Moeglichkeit "Kontaktinformationen" in Form von
	 * Auspraegungen zu hinterlegen.
	 * 
	 * @param wert, eigenschaftId, kontaktId, ownerId 
	 * @return a Auspraegung die erzeigt werden soll
	 */
	public Auspraegung createAuspraegung(String wert, int eigenschaftId, int kontaktId, int ownerId)
			throws IllegalArgumentException {

		// Erstellen eines Auspraegung Objektes
		Auspraegung a = new Auspraegung();
		a.setWert(wert);
		a.setEigenschaftId(eigenschaftId);
		a.setKontaktId(kontaktId);
		a.setOwnerId(ownerId);
		a.setId(1);

		// Anpassung des Modifikationsdatums des Kontakt Objektes
		saveModifikationsdatum(a.getKontaktId());

		// Speichern der Auspraegung
		return this.aMapper.insert(a);
	}

	/**
	 * Erzeugen einer neuen Auspraegung fuer eine vom Nutzer definierte Eigenschaft.
	 * Diese wird angelegt und anschliessend in der DB gespeichert. Der Nutzer kann
	 * hiermit in einem Zuge Eigenschaften anlegen und mit einem Auspraegungswert
	 * befuellen.
	 * 
	 * @param bezeichnung, wert, k
	 */
	public void createAuspraegungForNewEigenschaft(Vector<String> bezeichnung, Vector<String> wert, Kontakt k, int ownerId)
			throws IllegalArgumentException {

		// Erzeugung von leeren Vectoren
		Vector<Eigenschaft> eigene = new Vector<Eigenschaft>();
		Vector<Integer> id = new Vector<Integer>();

		// Erstellung der Eigenschaften, Hinzufuegen zum Eigenschaftsvector
		for (int i = 0; i < bezeichnung.size(); i++) {
			Eigenschaft e = new Eigenschaft();
			e.setBezeichnung(bezeichnung.elementAt(i));
			e.setId(1);

			eigene.add(this.eMapper.insert(e));
		}

		// Eigenschaft-Id´s werden dem Integer-Vector hinzugefuegt
		for (int i = 0; i < eigene.size(); i++) {
			id.add(eigene.elementAt(i).getId());
		}

		// Erstellung der Auspreagung zur erstellten Eigenschaft
		for (int j = 0; j < wert.size(); j++) {
			this.createAuspraegung(wert.elementAt(j), id.elementAt(j), k.getId(), ownerId);
		}
	}

	/**
	 * Modifikation einer Auspraegung.
	 */
	public void saveAuspraegung(Vector<Auspraegung> a)
			throws IllegalArgumentException {

		// this.saveModifikationsdatum(a.getKontaktId());

		for (int i = 0; i < a.size(); i++) {
			this.aMapper.update(a.elementAt(i));
		}

	}

	/**
	 * Loeschen einer Auspraegung.
	 * 
	 * @param a Auspraegung die geloescht werden soll
	 */
	public void deleteAuspraegung(Auspraegung a)
			throws IllegalArgumentException {

		this.saveModifikationsdatum(a.getKontaktId());
		this.aMapper.delete(a);
	}

	/**
	 * Loeschen einer Auspraegung anhand der id.
	 * 
	 * @param auspraegungId Auspraegung die geloescht werden soll
	 * @param kontaktId von dem das Modifikationsdatum aktualisiert werden soll
	 */
	public void deleteAuspraegungById(int auspraegungId, int kontaktId)
			throws IllegalArgumentException {
		this.saveModifikationsdatum(kontaktId);
		this.aMapper.deleteById(auspraegungId);
	} 

	/**
	 * Auslesen einer Auspraegung anhand id.
	 */
	public Auspraegung getAuspraegungById(int auspraegungId)
			throws IllegalArgumentException {

		return this.aMapper.findAuspraegungById(auspraegungId);
	}

	/**
	 * Gibt alle Auspraegungen eines Kontaktes zurueck
	 * 
	 * @return Vector von Auspraegungen
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId)
			throws IllegalArgumentException {

		return this.aMapper.findAuspraegungByKontakt(kontaktId);
	}

	/**
	 * Gibt alle Auspraegungen eines Kontaktes zurueck
	 */
	public Vector<Relatable> getAllAuspraegungenByKontaktRelatable(int kontaktId)
			throws IllegalArgumentException {

		return this.aMapper.findAuspraegungByKontaktRelatable(kontaktId);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /EIGENSCHAFT & AUSPRAEGUNG
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: BERECHTIGUNG
	 * *****************************************************************************
	 */

	/**
	 * Erstellung einer neuen Berechtigung. Es wird eine neue Berechtigung fuer eine
	 * Teilhaberschaft an einem bestimmten Objekt (Kontaktliste, Kontakt oder
	 * Auspraegung) erteilt. Die Berechtigung ergibt sich aus (Sender) ownerId und
	 * (Empfaenger) receiverId, welche die jeweiligen Nutzer referenzieren, sowie
	 * die objectId, welche sich auf das geteilte Objekt bezieht und in Verbindung
	 * mit dem type eindeutig ist.
	 * 
	 * 
	 * @param ownerId, receiverId, objectId, type
	 * @return b
	 */
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException {

		// Erzeugen eines neuen Berechtigung Objektes
		Berechtigung b = new Berechtigung();
		b.setId(1);
		b.setOwnerId(ownerId);
		b.setReceiverId(receiverId);
		b.setObjectId(objectId);
		b.setType(type);

		// Speichern der Berechtigung
		return this.bMapper.insert(b);
	}

	/**
	 * Das Teilen eines Objektes (Kontaktliste, Kontakt oder Auspraegung) mit einem
	 * anderen Nutzer. Diese Methode setzt die Berechtigung und damit die
	 * Teilhaberschaft zu einem bestimmten Objekt, weches mit einem anderen Nutzer
	 * geteilt werden soll. Es werden alle abhaengigen Objekte wie bspw. bei einer
	 * Kontaktliste die Kontakte, welche wiederum Auspraegungen beinhalten
	 * ber�cksichtigt. Alle abhaengingen Objekte werden fortlaufend mit den
	 * Parametern Sender, Empfaenger, sowie ObjektId, welches in Verbindung mit type
	 * des Objekts eindeutig ist, eingetragen.
	 * 
	 * @param ownerId, receiverId, objectId, type
	 */
	public void shareObject(int ownerId, int receiverId, int objectId, char type, Vector<Relatable> avshare)
			throws IllegalArgumentException {

		init();
		// Pruefung des Objekt-Typs
		if (type == 'l') {
			// Eintrag der Berechtigung fuer das Kontaktlisten-Objekt (ObjectId)
			this.createBerechtigung(ownerId, receiverId, objectId, type);

			// Vektor mit allen Kontakten, welcher der Kontaktliste l angehoeren (ObjectId)
			Vector<Kontakt> kv = this.getKontakteByKontaktliste(objectId);

			// Schleife welche alle Kontaktobjekte des Vektors durchgeht
			for (int k = 0; k < kv.size(); k++) {
				if (kv != null) {
					// Eintrag der Berechtigung fuer das Kontakt-Objekt (ObjectId)
					this.createBerechtigung(ownerId, receiverId, kv.elementAt(k).getId(), kv.elementAt(k).getType());

					// Vektor welcher alle Auspraegungsobjekte des Kontaktes k enthaelt
					Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(kv.elementAt(k).getId());

					// Schleife welche alle Auspraegungsobjekte des Kontaktes k durchgeht
					for (int a = 0; a < av.size(); a++) {
						if (av != null) {
							// Eintrag der Berechtigung fuer das Auspraegungsobjekt (ObjectId)
							this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(),
									av.elementAt(a).getType());
							
							if (av.elementAt(a).getStatus() == false) {
								Auspraegung aus= new Auspraegung();
								aus.setId(av.elementAt(a).getId());
								aus.setStatus(true);
								this.aMapper.setStatusTeilung(aus);
							}
						}
					}
				}
			}
		} else if (type == 'k') {
			
			/*
			 * Erstellen der Eigentum-, Teilhaber-Objekten und die Setzung 
			 * des Statuses ob eine Eigenschaft bereits geteilt wurde, anfang wird
			 * dieses auf false gesetzt.
			 */
			Nutzer n = this.getNutzerById(receiverId);
			Nutzer nn =  this.getNutzerById(ownerId);
			Kontakt k = this.getKontaktById(objectId);
			Boolean status = false;

			//Leeren Vector erstellen
			Vector<Berechtigung> ber = new Vector<Berechtigung>();
					
			//Alle gefundenen Berechtigungen dem Vector hinzufuegen
			ber.addAll(this.getAllBerechtigungenByOwner(nn.getId()));
			
			for (int i = 0; i < ber.size(); i++) {
				if(ber.elementAt(i).getObjectId() == k.getId() && ber.elementAt(i).getType() == 'k' && ber.elementAt(i).getReceiverId() == receiverId) {
					//Erstellen der Berechtigung fuer die zusaetzlich geteilte Eigenschaft
					this.createBerechtigungForEigenschaft(avshare, ownerId, receiverId);	
					//Status auf true setzen wenn der Kontakt vorhanden ist.
					status = true;
				
				}
			}
			//Ist der Status false wird der ganze Kontakt mit den geaehlten Eigenschaften geteilt.
			if(status == false) {
			// Eintrag der Berechtigung fuer das Kontakt-Objekt (ObjectId)
			this.createBerechtigung(ownerId, receiverId, objectId, type);
			this.addKontaktToKontaktliste(this.findKontaktlisteByTitel(n, "Mit mir geteilte Kontakte"), k);
			
			//Alle Auspraegungen des Kontakts dem Vector hinzufuegen
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(objectId);
			
			/*
			 * Ist der uebergebene Vector avshare leer werden die zuvor aus der Db
			 * gelesenen Eigenschaften des Kontakts der geteilt werden soll fuer
			 * die Teilung uebergeben und geteilt.
			 */
			if(avshare.isEmpty()) {
				for (int as = 0; as < av.size(); as++) {

					// Eintrag der Berechtigung fuer das zu teilende Auspraegungsobjekt
					this.createBerechtigung(ownerId, receiverId, av.elementAt(as).getId(),
							av.elementAt(as).getType());
					// Setzen eines Statuses fuer die Ausprägung.
					if (av.elementAt(as).getStatus() == false) {
						Auspraegung aus = new Auspraegung();
						aus.setId(av.elementAt(as).getId());
						aus.setStatus(true);
						this.aMapper.setStatusTeilung(aus);
					}
				} 
			} else {

			// Schleife welche alle Aauspraegungsobjekte des Kontaktes k durchgeht
			for (int a = 0; a < av.size(); a++) {

				// Schleife welche alle selektierten/ zu teilenden Aauspraegungsobjekte des Kontaktes k durchgeht
				for (int as = 0; as < avshare.size(); as++) {
 
					// Abgleich der zwei Vektoren mit ihren Auspraegungsobjekten bzw. ihren ids
					if (avshare != null && av.elementAt(a).getId() == avshare.elementAt(as).getId()) {
						// Eintrag der Berechtigung f�r das zu teilende Auspraegungsobjekt
						this.createBerechtigung(ownerId, receiverId, av.elementAt(a).getId(),
								av.elementAt(a).getType());
						// Setzen eines Statuses für die Ausprägung.
						if (avshare.elementAt(as).getStatus() == false) {
							Auspraegung aus = new Auspraegung();
							aus.setId(avshare.elementAt(as).getId());
							aus.setStatus(true);
							this.aMapper.setStatusTeilung(aus);
						}
					}
				}
			}
					
		}
	} 
			
		 } else if (type == 'a') {
			// Eintrag der Berechtigung fuer ein einzelnes zu teilendes
			// Auspraegungsobjekt(profilaktisch)
			this.createBerechtigung(ownerId, receiverId, objectId, type);
		}
	}
	
	/**
	 * Methode zum teilen von einzelnen Eigenschaften nachdem der Kontakt schon
	 * mit einerm Nutzer geteilt wurde.
	 * 
	 * @param avshare - Vector von Eigenschaften
	 * @param ownerId - id des Eigentuemers
	 * @param receiverId - id des Empfaengers
	 */
	public void createBerechtigungForEigenschaft(Vector<Relatable> avshare, int ownerId, int receiverId) {
		
		for (int i = 0; i < avshare.size(); i++) {
			//Berechtigung fuer das einzelne Eigenschafts-Objekt erstellen
			this.createBerechtigung(ownerId, receiverId, avshare.elementAt(i).getId(), 'a');
			
			// Setzen eines Statuses fuer die Eigenschaft setzen.
			if (avshare.elementAt(i).getStatus() == false) {
				Auspraegung aus = new Auspraegung();
				aus.setId(avshare.elementAt(i).getId());
				aus.setStatus(true);
				this.aMapper.setStatusTeilung(aus);
			}
			
		}
		
	}
	
	/**
	 * Diese Mehtode prueft ob Kontakte die sich in einer Kontaktliste
	 * befinden bereits geteilt wurden oder nicht. Wenn diese geteilt sind,
	 * wird der Status des Kontakts auf true gesetzt.
	 * 
	 * @param statusObjects Vector von Kontkten die auf Teilung geprueft werden
	 */
	public Vector<Kontakt> statusSharingKontakt(Vector<Kontakt> statusObjects){
		
		//Leerer Vector von Kontakten
		Vector<Kontakt> ko = new Vector<Kontakt>();
		//Uebergebener Vector von Kontakten prufen ob sie geteilt sind
		for (int i = 0; i < statusObjects.size(); i++) {
			//Ist der Kontakt geteilt wird der Status auf true gesetzt
			if(this.getStatusForObject(statusObjects.elementAt(i).getId(), statusObjects.elementAt(i).getType())== true) {
				statusObjects.elementAt(i).setStatus(true);
				ko.add(statusObjects.elementAt(i));
				//Ansonsten wird er einfach wie er ist hinzugefuegt
			} else {
				ko.add(statusObjects.elementAt(i));
			}
		}
		//Rueckgabe des Vectors der Kontakte
		return ko;	
	}
	
	/**
	 * Diese Mehtode prueft ob Kontaktlisten bereits geteilt wurden oder nicht.
	 * Wenn diese geteilt sind, wird der Status der Kontaktliste auf true gesetzt.
	 * 
	 * @param statusKontaktlisteObjects Vector von Kontaktlisten die auf Teilung geprueft werden sollen
	 */
	public Vector<Kontaktliste> statusSharingKontaktliste(Vector<Kontaktliste> statusKontaktlisteObjects){
		
		//Leerer Vector von Kontakten
		Vector<Kontaktliste> kl = new Vector<Kontaktliste>();
		//Uebergebener Vector von Kontakten prufen ob sie geteilt sind
				for (int i = 0; i < statusKontaktlisteObjects.size(); i++) {
					//Ist der Kontakt geteilt wird der Status auf true gesetzt
					if(this.getStatusForObject(statusKontaktlisteObjects.elementAt(i).getId(), statusKontaktlisteObjects.elementAt(i).getType())== true) {
						statusKontaktlisteObjects.elementAt(i).setStatus(true);
						kl.add(statusKontaktlisteObjects.elementAt(i));
						//Ansonsten wird er einfach wie er ist hinzugefuegt
					} else {
						kl.add(statusKontaktlisteObjects.elementAt(i));
					}
				}
		//Rueckgabe der Kontaktlisten.
		return statusKontaktlisteObjects;
	}
		
	/**
	 * Das Loeschen einer Berechtigung. Diese Methode hebt die Berechtigung und
	 * damit die Teilhaberschaft zu einem bestimmten Objekt auf. Es werden alle
	 * abhaengigen Objekte wie bspw. bei einer Kontaktliste die Kontakte, welche
	 * wiederum Auspraegungen beinhalten ber�cksichtigt. Alle Objekte werden
	 * fortlaufend von der Teilhaberschaft geloest ergo werden alle Berechtigungen
	 * auf die entsprechenden Objekte einzeln geloescht.
	 * 
	 * @param b Berechtigung die geloescht werden soll
	 */
	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException {

		Vector<Berechtigung> ber = new Vector<Berechtigung>();
		 ber.addAll(this.getAllBerechtigungenByOwner(b.getOwnerId()));
		 
		// Pruefung des Objekttypes
		if (b.getType() == 'l') {

			// Vektor welcher alle Kontakte der Kontaktliste enthaelt
			Vector<Kontakt> kv = this.getKontakteByKontaktliste(b.getObjectId());
			// Schleife welche alle Kontakte der Kontaktliste durchlaeuft
			for (int k = 0; k < kv.size(); k++) {
				if (kv != null) {

					// Vektor welcher alle Auspraegungen des Kontaktes enthaelt
					Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(kv.elementAt(k).getId());
					// Schleife welche alle Auspraegungen des Kontaktes durchlaeuft
					for (int a = 0; a < av.size(); a++) {
						if (av != null) {

							// Berechtigungsobjekt fuer die Auspraegung
							Berechtigung b2 = new Berechtigung();
							b2.setOwnerId(b.getOwnerId());
							b2.setReceiverId(b.getReceiverId());
							b2.setObjectId(av.elementAt(a).getId());
							b2.setType(av.elementAt(a).getType());

							// Loeschen der Berechtigung fuer die Auspraegung
							this.bMapper.delete(b2);
							//Setzen des Statuses wenn keine Auspraegung mehr in der Tabelle vorhanden ist.
									for (int i = 0; i < ber.size(); i++) {
										 if(b2.getObjectId() == ber.elementAt(i).getObjectId() && b2.getType() == ber.elementAt(i).getType()) {
											 Auspraegung aus = new Auspraegung();
											 aus.setId(ber.elementAt(i).getObjectId());
											 aus.setStatus(false);
											 this.aMapper.setStatusTeilung(aus);
										 } 
									}
							}
					}

					// Berechtigungsobjekt f�r den Kontakt
					Berechtigung b1 = new Berechtigung();
					b1.setOwnerId(b.getOwnerId());
					b1.setReceiverId(b.getReceiverId());
					b1.setObjectId(kv.elementAt(k).getId());
					b1.setType(kv.elementAt(k).getType());

					// Loeschen der Berechtigung fuer den Kontakt
					this.bMapper.delete(b1);
					
				}
			}
			// Loeschen des Berechtigung fuer die Kontaktliste
			this.bMapper.delete(b);

		} else if (b.getType() == 'k') {

			// Vektor welcher alle Auspraegungen des Kontaktes enthaelt
			Vector<Auspraegung> av = this.getAllAuspraegungenByKontakt(b.getObjectId());
			// Schleife welche alle Auspraegungen des Kontaktes durchlaeuft
			for (int a = 0; a < av.size(); a++) {
				if (av != null) {

					// Berechtigungsobjekt fuer die Auspraegung
					Berechtigung b2 = new Berechtigung();
					b2.setOwnerId(b.getOwnerId());
					b2.setReceiverId(b.getReceiverId());
					b2.setObjectId(av.elementAt(a).getId());
					b2.setType(av.elementAt(a).getType());

					// Loeschen der Berechtigung fuer die Auspraegung
					this.bMapper.delete(b2);
					
					//Setzen des Statuses wenn keine Auspraegung mehr in der Tabelle vorhanden ist.
					for (int i = 0; i < ber.size(); i++) {
						 if(b2.getObjectId() == ber.elementAt(i).getObjectId() && b2.getType() == ber.elementAt(i).getType()) {
							 Auspraegung aus = new Auspraegung();
							 aus.setId(ber.elementAt(i).getObjectId());
							 aus.setStatus(false);
							 this.aMapper.setStatusTeilung(aus);
						 } 
					}
				}
			}

			// Loeschen der Berechtigung f�r den Kontakt
			this.bMapper.delete(b);

			Nutzer n = new Nutzer();
			n.setId(b.getReceiverId());

			Kontakt k = new Kontakt();
			k.setId(b.getObjectId());

			this.removeKontaktFromKontaktliste(findKontaktlisteByTitel(n, "Mit mir geteilte Kontakte"), k);
			
		} else if (b.getType() == 'a') {

			// Loeschen der Berechtigung fuer eine einzelne Auspraegung
			this.bMapper.delete(b);
			
			//Setzen des Statuses wenn keine Auspraegung mehr in der Tabelle vorhanden ist.
			for (int i = 0; i < ber.size(); i++) {
				 if(b.getObjectId() == ber.elementAt(i).getObjectId() && b.getType() == ber.elementAt(i).getType()) {
					 Auspraegung aus = new Auspraegung();
					 aus.setId(ber.elementAt(i).getObjectId());
					 aus.setStatus(false);
					 this.aMapper.setStatusTeilung(aus);
				 } 
			}

		}
	}

	/**
	 * Diese Methode prueft ob der Nutzer seinen Kontakt, eine Berechtigung oder 
	 * allgemein seinen Kontakt permanent aus der Kontaktliste entfernen will.
	 * 
	 * @param ko Vector von Kontakten die geloscht werden sollen
	 * @param kl Kontaktliste in der man die Kontakte entfernen will
	 * @param nutzer der aktuell eingeloggte Nutzer.
	 * @deprecated Veraltet da angepasst.
	 */
	public void deleteKontaktFromKontaktliste(Vector<Kontakt> ko, Kontaktliste kl, Nutzer nutzer) {
		
		for (int i = 0; i < ko.size(); i++) {
		/*
		 * Prüfung ob man der Owner der Kontaktliste ist und ob es sich um die
		 * Kontaktliste "Mit mir geteilte Kontakte" handelt um die Berechtigung zu
		 * löschen. Berechtigung kann nur aus der mit mir geteilte Kontakteliste
		 * entfernt werden, handelt es sich nicht um diese Liste wird nur der Kontakt
		 * aus der Kontaktliste entfernt.
		 * 
		 * Dies dient dazu, weil man Kontakte auch nur aus einer Kontaktliste entfernen
		 * will ohne die Intension die Berechtigung zu entfernen.
		 */
		if (nutzer.getId() != ko.elementAt(i).getOwnerId()
				&& kl.getTitel() == "Mit mir geteilte Kontakte") {
			// Nutzer Cookies setzen und dann per Nutzer holen.

			/*
			 * Es werden alle Berechtigungen geholt die mit dem Nutzer geteilt wurden und
			 * wenn es eine Übereinstimmung gibt wird die Berechtigung entfernt.
			 */
			System.out.println("berechtigung");
				Berechtigung b = new Berechtigung();
				b.setObjectId(ko.elementAt(i).getId());
				b.setOwnerId(ko.elementAt(i).getOwnerId());
				b.setReceiverId(nutzer.getId());
				b.setType('k');

				this.deleteBerechtigung(b);

			/*
			 * Ist man Owner des Kontakts und befindet sich der Kontakt in der Liste
			 * "Meine Kontakte" wird er permanent gelöscht.
			 */
		} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() == nutzer.getId()) {
			System.out.println("hallo eigentlich");
			this.deleteKontakt(ko.elementAt(i));
			/*
			 * Case 1: Ist man nicht der Owner und befindet sich in der Kontaktliste
			 * "Meine Kontakte" wird der Kontakt nur aus der Kontaktliste entfernt.
			 * 
			 * Case 2: Befindet man sich nicht in der Kontaktliste
			 * "Mit mir geteilte Kontakte" oder ist der Owner des Kontakts wird der Kontakt
			 * nur aus der Kontaktliste entfernt.
			 */
		} else if (kl.getTitel() == "Meine Kontakte" && ko.elementAt(i).getOwnerId() != nutzer.getId()
				|| kl.getTitel() != "Mit mir geteilte Kontakte"
						&& ko.elementAt(i).getOwnerId() != nutzer.getId()) {

			System.out.println("hallo");
			this.removeKontaktFromKontaktliste(kl, ko.elementAt(i));
			/*
			 * Ist man Owner und will einen Kontakt aus einer NICHT Standard Kontaktliste
			 * löschen.
			 */
		} else if (kl.getTitel() != "Meine Kontakte"
				&& ko.elementAt(i).getOwnerId() == nutzer.getId() || kl.getTitel() != "Meine Kontakte"
						&& ko.elementAt(i).getOwnerId() != nutzer.getId()) {
			System.out.println("hallo2");
	
			this.removeKontaktFromKontaktliste(kl, ko.elementAt(i));

		
		// Löschen von Kontakten aus der Kontaktliste als Teilhaber, wenn der Titel der
		// Liste nicht ""Mit mir geteilte Kontakte" heißt.
		}else if (nutzer.getId() != ko.elementAt(i).getOwnerId()
				&& kl.getTitel() != "Mit mir geteilte Kontakte") {
			System.out.println("hallo3");
			this.removeKontaktFromKontaktliste(kl, ko.elementAt(i));
			}
		}
	}
	
	
	/**
	 * Methode zum entfernen von Teilhaberschaften an einem Kontakt in
	 * der "Mit mir geteilte" Kontakteliste.
	 * 
	 * @param ko Vector von zu loeschenden Teilhaberschaften
	 * @param n akteull eingeloggter Nutzer (Teilhbaer)
	 */
	public void deleteBerechtigungReceiver(Vector<Kontakt> ko, Nutzer n) {
		// Leeren Vector erstellen
		Vector<Berechtigung> ber = new Vector<Berechtigung>();
		// Alle Berechtigungen des Teilhabers in den Vector speichern
		ber.addAll(this.getAllBerechtigungenByReceiver(n.getId()));

		// Schleife welche Berechtigungs-Vector durlaeuft.
		for (int i = 0; i < ber.size(); i++) {
			// Schleife welche den Kontakt-Vector durlaeuft.
			for (int j = 0; j < ko.size(); j++) {
				// Abfrage ob es sich um dieses Objekt mit dem richtigen Type handelt
				if (ber.elementAt(i).getObjectId() == ko.elementAt(j).getId() && ber.elementAt(i).getType() == 'k') {
					Berechtigung b = new Berechtigung();
					b.setObjectId(ko.elementAt(j).getId());
					b.setOwnerId(ko.elementAt(j).getOwnerId());
					b.setReceiverId(n.getId());
					b.setType('k');

					// Bei Uebereinstimmung wird Berechtigung entfernt.
					this.deleteBerechtigung(b);
				}
			}
		}
	}
	
	/**
	 * Methode zum entfernen von Teilhaberschaften an einer Kontaktliste als 
	 * 
	 * @param kl Kontaktliste von der die Teilhaberschaft entfernt werden soll.
	 * @param n aktuell eingeloggter Nutzer (Teilhbaer)
	 */
	public void deleteBerechtigungReceiverKontaktliste(Kontaktliste kl, Nutzer n) {
		// Leeren Vector erstellen
		Vector<Berechtigung> ber = new Vector<Berechtigung>();
		// Alle Berechtigungen des Teilhabers in den Vector speichern
		ber.addAll(this.getAllBerechtigungenByReceiver(n.getId()));

		// Schleife welche Berechtigungs-Vector durlaeuft.
		for (int i = 0; i < ber.size(); i++) {
				// Abfrage ob es sich um dieses Objekt mit dem richtigen Type handelt
				if (ber.elementAt(i).getObjectId() == kl.getId() && ber.elementAt(i).getType() == 'l') {
					Berechtigung b = new Berechtigung();
					b.setObjectId(kl.getId());
					b.setOwnerId(kl.getOwnerId());
					b.setReceiverId(n.getId());
					b.setType('l');

					// Bei Uebereinstimmung wird Berechtigung entfernt.
					this.deleteBerechtigung(b);
				}
		}	
	}
	
	/**
	 * Methode zum Auflisen von Teilhaberschaften.
	 * Die Initiative geht hier vom Nutzer der Eigentuemer einer
	 * Kontaktliste oder Kontakt ist aus.
	 * 
	 * Es werden alle Berechtigungen aufgehoben die der Eigentuemer dem Teilhaber
	 * uebergeben hat.
	 * 
	 * @param nutzer Vevtor von Nutzern bei denen die Teilhaberschaft entfernt werden soll
	 * @param kl die Kontaktliste bei der die Teilhaberschaft fuer einen Nutzer entfernt werden soll.
	 * @param k der Kontakt bei dem die Teilhaberschaft entfernt werden soll
	 * @param n aktuell eingeloggter Nutzer
	 * 
	 */
	public void deleteBerechtigungOwner(Vector<Nutzer> nutzer, Kontaktliste kl, Kontakt k, Nutzer n) {

		// Wenn eine Kontaktliste uebergebenw wurde
		if (kl != null) {
			// Schleife durchlaufen mit allen uebergebenen Nutzern
			for (int i = 0; i < nutzer.size(); i++) {
				// Berechtigungs-Objekt erzeugen
				Berechtigung b = new Berechtigung();
				b.setObjectId(kl.getId());
				b.setOwnerId(n.getId());
				b.setReceiverId(nutzer.elementAt(i).getId());
				b.setType(kl.getType());

				// Loeschung der Teilhaberschaft duchfuehren
				this.deleteBerechtigung(b);
			}

		} else if (k != null) {
			// Schleife durchlaufen mit allen uebergebenen Nutzern
			for (int i = 0; i < nutzer.size(); i++) {
				// Berechtigungs-Objekt erzeugen
				Berechtigung b = new Berechtigung();
				b.setObjectId(k.getId());
				b.setOwnerId(n.getId());
				b.setReceiverId(nutzer.elementAt(i).getId());
				b.setType(k.getType());

				// Loeschung der Teilhaberschaft duchfuehren
				this.deleteBerechtigung(b);

			}
		}

	}
	
	/**
	 * Gibt alle Objekt-Berechtigungen ueber jene Objekte aus, welche vom Nutzer
	 * geteilt wurden.
	 * 
	 * @param nutzerId id des Owners von geteilten Objekten
	 * @return b die Berechtigungen des Nutzers
	 */
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int nutzerId)
			throws IllegalArgumentException {

		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByOwner(nutzerId);
		return b;
	}

	/**
	 * Gibt alle Objekt-Berechtigungen ueber jene Objekte aus, welche mit dem Nutzer
	 * geteilt wurden.
	 * 
	 * @param nutzerId des nutzers mit dem geteilt wurde
	 * @return b die geteilte Berechtigung 
	 */
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int nutzerId)
			throws IllegalArgumentException {

		Vector<Berechtigung> b = this.bMapper.findAllBerechtigungenByReceiver(nutzerId);
		return b;
	}

	/**
	 * Gibt alle Nutzer zurueck, mit welchem ein bestimmtes Objekt (Konakt,
	 * Kontaktliste oder Auspraegung) geteilt wurde. (Relevant fuer die Aufhebaung/
	 * Loeschung einer Teilhaberschaft als Owner).
	 * 
	 * @param objectId, type, n
	 */
	public Vector<Nutzer> sharedWith(int objectId, char type, Nutzer n) throws IllegalArgumentException {

		// Vektor mit allen Berechtigungen welche Nutzerseitig gesetzt wurden
		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(n.getId());

		// Leerer Vektor fuer Nutzerobjekten mit welchen die �bergebene objectId
		// geteilt wurde
		Vector<Nutzer> nv = new Vector<Nutzer>();

		// Schleife welche alle Berechtigungen, die Nutzerseitig gesetzt wurden,
		// durchgeht
		for (Berechtigung b : bv) {

			// Abgleich der objektId und des types um die Eindeutigkeit zu gewaehrleisten
			if (bv != null && b.getObjectId() == objectId && b.getType() == type) {

				// Nutzerobjekt/ receiver dem Vektor hinzufuegen
				nv.add(this.getNutzerById(b.getReceiverId()));
			}
		}

		// Rueckgabe des Vektors mit den Nutzerobjekten mit welchen die uebergebene
		// objectId geteilt wurde
		return nv;
	}

	public Vector<Nutzer> sharedWithEmail(Nutzer n) throws IllegalArgumentException {

		// Vektor mit allen Berechtigungen welche Nutzerseitig gesetzt wurden
		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(n.getId());

		// Leerer Vektor fuer Nutzerobjekten mit welchen die �bergebene objectId
		// geteilt wurde
		Vector<Nutzer> nv = new Vector<Nutzer>();

		// Schleife welche alle Berechtigungen, die Nutzerseitig gesetzt wurden,
		// durchgeht
		for (Berechtigung b : bv) {

			// Abgleich der objektId und des types um die Eindeutigkeit zu gewaehrleisten
			if (bv != null) {

				// Nutzerobjekt/ receiver dem Vektor hinzufuegen
				nv.add(this.getNutzerById(b.getReceiverId()));
			}
		}
		return nv;
	}
	
	/**
	 * Diese Methode loescht alle Berechtigungen der einzelnen Eigenschaften, wenn der
	 * Eigentuemer diese loescht.
	 * 
	 * @param n aktuell eingeloggter Nutzer (Eigentuemer)
	 * @param objectId die ausgewahelte Eigenschaft die geloescht werden soll.
	 */
	public void deleteAllBerechtigungenByOwner(Nutzer n, int objectId) {
		//Leeren Vector erstellen
		Vector<Berechtigung> ber = new Vector<Berechtigung>();
		
		//Alle gefundenen Berechtigungen dem Vector hinzufuegen
		ber.addAll(this.getAllBerechtigungenByOwner(n.getId()));
		
		for (int i = 0; i < ber.size(); i++) {
			if(ber.elementAt(i).getObjectId() == objectId && ber.elementAt(i).getType() == 'a') {
				Berechtigung b = new Berechtigung();
				b.setObjectId(objectId);
				b.setOwnerId(n.getId());
				b.setReceiverId(ber.elementAt(i).getReceiverId());
				b.setType('a');
				this.deleteBerechtigung(b);
			}
		}
	
	}
	

	/**
	 * Diese Methode loescht alle Berechtigungen der einzelnen Eigenschaften, wenn der
	 * Teilhaber diese loescht.
	 * 
	 * @param n aktuell eingeloggter Nutzer (Teilhaber)
	 * @param objectId die ausgewahelte Eigenschaft die geloescht werden soll.
	 */
	public void deleteAllBerechtigungenByReceiver(Nutzer n, int objectId) {
		//Leeren Vector erstellen
		Vector<Berechtigung> ber = new Vector<Berechtigung>();
		
		//Alle gefundenen Berechtigungen dem Vector hinzufuegen
		ber.addAll(this.getAllBerechtigungenByReceiver(n.getId()));
		
		//Berechtigungen durchgehn und alle Auspraegungen entfernen.
		for (int i = 0; i < ber.size(); i++) {
			if(ber.elementAt(i).getObjectId() == objectId && ber.elementAt(i).getType() == 'a') {
				Berechtigung b = new Berechtigung();
				b.setObjectId(objectId);
				b.setOwnerId(ber.elementAt(i).getOwnerId());
				b.setReceiverId(n.getId());
				b.setType('a');
				this.deleteBerechtigung(b);
			}
		}
	}
	


	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /BERECHTIGUNG
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: ABRUF DER GETEILTE OBJEKTE
	 * *****************************************************************************
	 */

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche vom Nutzer geteilt wurden.
	 * 
	 * @param nutzerId des Nutzers
	 */
	public Vector<Kontakt> getAllSharedKontakteByOwner(int nutzerId)
			throws IllegalArgumentException {

		// Vektor welcher alle nutzerseitig gesetzten Berechtigungen enthaelt
		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(nutzerId);

		// Leerer Vektor f�r alle nutzerseitig geteilten Kontakte
		Vector<Kontakt> kv = new Vector<Kontakt>();

		// Schleife welche alle nutzerseitig gesetzten Berechtigungen durchlaeuft
		for (int b = 0; b < bv.size(); b++) {

			// Abgleich des Nutzers als Owner sowie des Objekttypes um die Eindeutigkeit zu
			// gewaehrleisten
			if (bv != null && nutzerId == bv.elementAt(b).getOwnerId() && bv.elementAt(b).getType() == 'k') {

				// Erstellen des Kontaktobjekts
				Kontakt k = this.getKontaktById(bv.elementAt(b).getObjectId());
				if (!kv.contains(k)) {
					kv.addElement(k);
				}

			}
		}

		// Rueckgabe des Vektors welcher die vom Nutzer geteilten Kontakte enthaelt
		return kv;
	}

	/**
	 * Gibt alle <code>Kontakt</code>-Objekte aus, welche mit dem Nutzer geteilt
	 * wurden.
	 * 
	 * @param nutzerId des Nutzers mit dem geteilt wurde
	 * @return Vector von Kontakten
	 */
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int nutzerId)
			throws IllegalArgumentException {

		// Vektor welcher alle fuer den Nutzer gesetzten Berechtigungen enthaelt
		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(nutzerId);

		// Leerer Vektor fuer alle mit dem Nutzer geteilten Kontakte
		Vector<Kontakt> kv = new Vector<Kontakt>();

		// Schleife welche alle f�r den Nutzer gesetzten Berechtigungen durchlaeuft
		for (int b = 0; b < bv.size(); b++) {

			// Abgleich des Nutzers als Receiver sowie des Objekttypes um die Eindeutigkeit
			// zu gewaehrleisten
			if (bv != null && nutzerId == bv.elementAt(b).getReceiverId() && bv.elementAt(b).getType() == 'k') {

				// Erstellen des Kontaktobjekts
				Kontakt k = this.getKontaktById(bv.elementAt(b).getObjectId());

				// Kontakt dem Vektor hinzufuegen
				kv.addElement(k);
			}
		}

		// Rueckgabe des Vektors welcher die mit dem Nutzer geteilten Kontakte enthaelt
		return kv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche vom Nutzer geteilt
	 * wurden.
	 * 
	 * @param nutzerId des Nutzers
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int nutzerId)
			throws IllegalArgumentException {

		// Vektor welcher alle nutzerseitig gesetzten Berechtigungen enthaelt
		Vector<Berechtigung> bv = this.getAllBerechtigungenByOwner(nutzerId);

		// Leerer Vektor fuer alle nutzerseitig geteilten Kontaktlisten
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		// Schleife welche alle nutzerseitig gesetzten Berechtigungen durchlaeuft
		for (int b = 0; b < bv.size(); b++) {

			// Abgleich des Nutzers als Owner sowie des Objekttypes um die Eindeutigkeit zu
			// gewaehrleisten
			if (bv != null && nutzerId == bv.elementAt(b).getOwnerId() && bv.elementAt(b).getType() == 'l') {

				// Erstellen des Kontaktlistenobjekts
				Kontaktliste kl = this.getKontaktlisteById(bv.elementAt(b).getObjectId());

				// Kontaktliste dem Vektor hinzufuegen
				klv.addElement(kl);
			}
		}

		// Rueckgabe des Vektors welcher die vom Nutzer geteilten Kontaktlisten enthaelt
		return klv;
	}

	/**
	 * Gibt alle <code>Kontaktliste</code>-Objekte aus, welche mit dem Nutzer
	 * geteilt wurden.
	 * 
	 * @param nutzerId des Nutzers mit dem Kontaktlisten geteilt wurden
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int nutzerId)
			throws IllegalArgumentException {

		// Vektor welcher alle fuer den Nutzer gesetzten Berechtigungen enthaelt
		Vector<Berechtigung> bv = this.getAllBerechtigungenByReceiver(nutzerId);

		// Leerer Vektor fuer alle mit dem Nutzer geteilten Kontaktlisten
		Vector<Kontaktliste> klv = new Vector<Kontaktliste>();

		// Schleife welche alle fuer den Nutzer gesetzten Berechtigungen durchlaeuft
		for (int b = 0; b < bv.size(); b++) {

			// Abgleich des Nutzers als Receiver sowie des Objekttypes um die Eindeutigkeit
			// zu gewaehrleisten
			if (bv != null && nutzerId == bv.elementAt(b).getReceiverId() && bv.elementAt(b).getType() == 'l') {

				// Erstellen des Kontaktlistenobjekts
				Kontaktliste kl = this.getKontaktlisteById(bv.elementAt(b).getObjectId());

				// Kontaktliste dem Vektor hinzufuegen
				klv.addElement(kl);
			}
		}

		// Rueckgabe des Vektors welcher die mit dem Nutzer geteilten Kontaktlisten
		// enthaelt
		return klv;
	}

	/**
	 * Gibt alle geteilten Auspraegungen zu einem geteilten Kontakt k mit einem
	 * Nutzer n aus.
	 * 
	 * @param k, n
	 * @return avshare
	 */
	public Vector<Relatable> getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n)
			throws IllegalArgumentException {

		// Vektor welcher alle gesetzten Berechtigungen enthaelt
		Vector<Berechtigung> bv = this.bMapper.findAll();

		// Vektor welche alle Auspraegungen eines Kontaktes enthaelt
		Vector<Relatable> av = this.getAllAuspraegungenByKontaktRelatable(k.getId());

		// Leerer Vektor fuer alle geteilten Auspraegungen des Kontaktes k
		Vector<Relatable> avshare = new Vector<Relatable>();

		// Schleife welche alle Auspraegungen des Kontaktes k durchlaeuft
		for (Relatable a : av) {

			// Schleife welche alle gesetzten Berechtigungen durchlaeuft
			for (Berechtigung b : bv) {

				/*
				 * Abgleich der Id der Auspraegung a mit der objectId der gesetzten
				 * Berechtigungen b sowie Objekttyp und Nutzer um die Eindeutigkeit
				 * zugewaehrleisten
				 */
				if (a.getId() == b.getObjectId() && b.getReceiverId() == n.getId() || a.getOwnerId() == n.getId() && k.getId() == b.getObjectId()) {

					// Abruf der Auspraegung
					this.getAuspraegungById(a.getId());

					// Auspraegung dem Vektor hinzufuegen
					avshare.addElement(a);
					
				}
			}
		}
		

		// Rueckgabe Vektor welcher alle geteilten Auspraegungen des Kontaktes k
		// enthaelt
		return avshare;
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /ABRUF DER GETEILTE OBJEKTE
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: SUCHE
	 * *****************************************************************************
	 */

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Kontakte nach dem
	 * Namen und gibt diese zurueck. Hierbei wird Vor- und Nachname des Kontaktes
	 * mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param name, vom Nutzer uebergebener String
	 * @param n Nutzer
	 * @return Vector von Kontakten
	 */
	public Vector<Kontakt> getKontakteByName(String name, Nutzer n)
			throws IllegalArgumentException {

		return this.kMapper.findKontakteByName(name, n);
	}

	/**
	 * Durchsucht sowohl eigene als auch mit dem Nutzer geteilte Auspraegungen nach
	 * dem Wert und gibt diesen zurueck. Hierbei wird die Auspraegung eines
	 * Kontaktes mit dem vom Nutzer uebergebenem String abgeglichen.
	 * 
	 * @param wert vom Nutzer uebergebener String
	 * @param n Nutzer
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
	 * @param bezeichnung, n, vom Nutzer uebergebener String
	 * @param n Nutzer
	 */
	public Vector<Kontakt> getKontakteByEigenschaft(String bezeichnung, Nutzer n)
			throws IllegalArgumentException {

		return this.kMapper.findKontakteByEigenschaft(bezeichnung, n);
	}

	/**
	 * Durchsucht den angesprochenen Kontakt nach einem bestimmten uebergebenen
	 * Wert der Eigenschaft und Auspraegung und gibt diesen zurueck. Hierbei wird die Auspraegung
	 * und die Eigenschaft mit der dazugehoerigen Eigenschaft dem Kontakt zurueckgegeben.
	 * 
	 * @param wert, bezeichnung vom Nutzer uebergebener String
	 */
	
	public Vector<Kontakt> getKontaktByAusEig(String bezeichnung, String wert, Nutzer n)
			throws IllegalArgumentException {
		
		return this.kMapper.findKontakteByAusEig(bezeichnung, wert, n);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: SUCHE
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: HILFSMETHODEN
	 * *****************************************************************************
	 */

	/**
	 * Durchsucht den angesprochenen Kontakt nach einem bestimmten �bergebenen
	 * Wert der Auspraegung und gibt diesen zurueck. Hierbei wird die Auspraegung
	 * mit der dazugehoerigen Eigenschaft dem Kontakt zurueckgegeben.
	 * 
	 * @param wert, vom Nutzer uebergebener String
	 * @return Vector von Auspraegungen
	 */
	public Vector<Auspraegung> getAuspraegungByWert(String wert) throws IllegalArgumentException {

		return this.aMapper.findAuspraegungByWert(wert);
	}
	
	
	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: SUCHE
	 * *****************************************************************************
	 */

	/*
	 * *****************************************************************************
	 * ABSCHNITT ANFANG: SONSTIGES
	 * *****************************************************************************
	 */

	/**
	 * Prueft ueber einen boolean, ob sich ein Objekt (Kontakt k, Kontaktliste l
	 * oder Auspraegung a) sich in einem geteilten Status befindet.
	 */
	public boolean getStatusForObject(int objectId, char type) throws IllegalArgumentException {

		// Vektor welcher alle Berechtigungen zu allen Objekten enthaelt
		Vector<Berechtigung> bv = this.bMapper.findAll();

		// Schleife welche alle Berechtigungsobjekte durchlaeuft
		for (Berechtigung b : bv) {

			// Abgleich der objectId und type um die Eindeutigkeit zu gewaehrleisten
			if (objectId == b.getObjectId() && type == b.getType()) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Aktualisierung des Modifikationsdatums.
	 */
	public void saveModifikationsdatum(int id) throws IllegalArgumentException {

		this.kMapper.updateModifikationsdatum(id);
	}

	/*
	 * *****************************************************************************
	 * ABSCHNITT ENDE: /SONSTIGES
	 * *****************************************************************************
	 */

}
