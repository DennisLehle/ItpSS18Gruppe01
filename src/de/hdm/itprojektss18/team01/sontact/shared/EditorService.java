package de.hdm.itprojektss18.team01.sontact.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Interface fuer RPC-Service.
 *
 */
@RemoteServiceRelativePath("editorservice")
public interface EditorService extends RemoteService {

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl #init();
	 */
	public void init() throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: NUTZER *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createNutzer(String emailAddress);
	 * @param emailAddress
	 *            des Nutzer
	 * @return ersteller Nutzer
	 */
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #findAllNutzer();
	 * @return alle Nutzer
	 */
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getUserByGmail(String email);
	 * @param email
	 *            Email Nutzers
	 * @return nutzer
	 * 
	 */
	public Nutzer getUserByGMail(String email) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getNutzerById(int nutzerId);
	 * @param nutzerId
	 *           NutzerId des Nutzers
	 * @return nutzer
	 * 
	 */
	public Nutzer getNutzerById(int nutzerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteNutzer(Nutzer n);
	 * @param n - aktuell eingeloggter Nutzer
	 * 
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: KONTAKT *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createKontakt(String vorname, String nachnamen Nutzer n);
	 * @param vorname des Kontakts
	 * @param nachname des Kontakts
	 * @param n - aktuell eingeloggter Nutzer
	 * @return erstellter Kontakt
	 * 
	 */
	public Kontakt createKontakt(String vorname, String nachname, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createKontaktRegistrierung(String vorname, String nachname, Nutzer n);
	 * 
	 * @param vorname des eigenen Kontakts (Regestrierung)
	 * @param nachname des eigenen Kontakts (Regestrierung)
	 * @param n aktuell eingeloggter Nutzer
	 * @return Kontakt
	 * 
	 */
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #saveKontakt(Kontakt k);
	 * @param k zu aktualisierender Kontakt
	 * @return Kontakt
	 * 
	 */
	public Kontakt saveKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #removeKontakt(Kontakt k);
	 * @param k der zu loeschende Kontakt
	 * 
	 */
	public void deleteKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontaktById(int id);
	 * @param id des zu findenden Kontakts
	 * @return Kontakt
	 * 
	 */
	public Kontakt getKontaktById(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getOwnKontakt(Nutzer n)
	 * @param n aktuell eingeloggter Nutzer
	 * @return Kontakt
	 * 
	 */
	public Kontakt getOwnKontakt(Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllKontakteByOwner(Nutzer n)
	 * @param n aktuell eingeloggter Nutzer
	 * @return alle Kontakte des Eigentuemers
	 * 
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #addKontaktToKontaktliste(Kontakt k, Kontaktliste kl);
	 * @param kl die Kontaktliste
	 * @param k Kontakt der zur Kontaktliste hinzugefuegt wird
	 * 
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #removeKontaktFromKontaktliste(Kontakt k);
	 * @param kl die Kontaktliste
	 * @param k Kontakt der aus der Kontaktliste enterfernt wird
	 * 
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: KONTAKTLISTE *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createKontaktlisteRegistrierung(Nutzer n);
	 * @param n aktuell eingeloggter Nutzer
	 * @return Defaultkontaktliste die alle eignen Kontakte anzeigt
	 * 
	 */
	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n) throws IllegalArgumentException;;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createKontaktliste(String titel, Nutzer n);
	 * @param titel - der Titel der Kontaktliste
	 * @param n aktuell eingeloggter Nutzer
	 * @return erstellte Kontaktliste
	 * 
	 */
	public Kontaktliste createKontaktliste(String titel, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #saveKontaktliste(Kontaktliste kl);
	 * @param kl - Kontaktliste die upgedated wird
	 * 
	 */
	public void saveKontaktliste(Kontaktliste kl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteKontaktliste(Kontaktliste kl);
	 * @param kl - Zu loeschende Kontaktliste
	 * 
	 */
	public void deleteKontaktliste(Kontaktliste kl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontaktlisteById(int id);
	 * @param id der gesuchten Kontaktliste
	 * @return Kontaktliste
	 * 
	 */
	public Kontaktliste getKontaktlisteById(int id) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #findKontaktlisteByTitel(Nutzer n, String titel);
	 * @param n aktuell eingeloggter Nutzer
	 * @param titel - Titel dr Kontaktliste
	 * @return Kontaktliste
	 * 
	 */
	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontaktlisteByOwner(Nutzer n);
	 * @param n aktuell eingeloggter Nutzer
	 * @return Kontaktliste
	 * 
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontakteByKontaktliste(Kontaktliste kl);
	 * @param kontaktlisteId der verwendeten Kontaktliste
	 * @return Kontakte der Kontaktliste
	 * 
	 */
	public Vector<Kontakt> getKontakteByKontaktliste(int kontaktlisteId) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: EIGENSCHAFT & AUSPRAEGUNG *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createEigenschaft(String bezeichnung);
	 * @param bezeichnung der Eigenschaft
	 * @return erstellte Eigenschaft
	 * 
	 */
	public Eigenschaft createEigenschaft(String bezeichnung) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 * @param bezeichnung der Eigenschafz
	 * @return Alle Eigenschaften die erzeugt wurden
	 */
	public Vector<Eigenschaft> createEigenschaftV(Vector<String> bezeichnung) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #saveEigenschaft(Eigenschaft e);
	 * @param e die zu aktualisierende Eigenschaft (update)
	 * @return Eigenschaft
	 * 
	 */
	public Eigenschaft saveEigenschaft(Eigenschaft e) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteEigenschaft(Eigenschaft e);
	 * @param e die zu loeschende Eigenschaft
	 * 
	 */
	public void deleteEigenschaft(Eigenschaft e) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getEigenschaftById(int eigenschaftId);
	 * @param eigenschaftId der Eigenschaft
	 * @return Eigenschaft
	 * 
	 */
	public Eigenschaft getEigenschaftById(int eigenschaftId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getEigenschaftAuswahl();
	 * @return alle Eigenschaften 
	 * 
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl() throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #findEigenschaftByBezeichnung(String bezeichnung);
	 * @param bezeichnung der Eigenschaft
	 * @return Eigenschaft
	 * 
	 */
	public Eigenschaft getEigenschaftByBezeichnung(String bezeichnung) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createAuspraegung(String wert, int eigenschaftId, int kontaktId, int
	 *      ownerId);
	 * @param wert der Eigenschaft
	 * @param eigenschaftId - id der Eigenschaft
	 * @param kontaktId - id des Kontakts
	 * @param ownerId - id des Eigentuemers
	 * @return die erstelle Auspraegung
	 * 
	 */
	public Auspraegung createAuspraegung(String wert, int eigenschaftId, int kontaktId, int ownerId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteEigenschaftcreateAuspraegungForNewEigenschaft(String bezeichnung,
	 *      String wert, Kontakt k);
	 * @param bezeichnung der Eigenschaft
	 * @param wert der Eigenschaft
	 * @param k - der Kontakt fuer den die Eigenschaft und Auspraegung erstellt wird
	 * @param ownerId - id des Eigentuemers
	 * 
	 */
	public void createAuspraegungForNewEigenschaft(Vector<String> bezeichnung, Vector<String> wert, Kontakt k,
			int ownerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #saveAuspraegung(Auspraegung a);
	 * @param a die zu aktualisierenden Auspraegungen (update)
	 * 
	 */
	public void saveAuspraegung(Vector<Auspraegung> a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 * @param a die zu loeschende Auspraegung
	 * 
	 */
	public void deleteAuspraegung(Auspraegung a) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteAuspraegungById(auspraegungId, kontaktId);
	 * @param auspraegungId - id der Auspraegung die geloescht werden soll
	 * @param kontaktId - id des Kontakt bei dem das Modifikationdatum aktualisiert wird
	 * 
	 */
	public void deleteAuspraegungById(int auspraegungId, int kontaktId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAuspraegungById(Auspraegung a);
	 * @param auspraegungId - id der Auspraegung
	 * @return gesuchte Auspraegung
	 * 
	 */
	public Auspraegung getAuspraegungById(int auspraegungId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllAuspraegungenByKontakt(int kontaktId);
	 * @param kontaktId - id des Kontakts fuer den alle Auspraegungen gesucht werden
	 * @return alle Auspraegungen des Kontakts
	 * 
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllAuspraegungenByKontaktRelatable(int kontaktId);
	 * @param kontaktId - id des Kontakts fuer den alle Auspraegungen gesucht werden
	 * @return alle Auspraegungen des Kontakts
	 * 
	 */
	public Vector<Relatable> getAllAuspraegungenByKontaktRelatable(int kontaktId) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: BERECHTIGUNG *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createBerechtigung(int ownerId, int receiverId, int objectId, char
	 *      type);
	 * @param ownerId - Eigentuemer
	 * @param receiverId - Empfaenger
	 * @param objectId - Objekt 
	 * @param type - Objekttyp 
	 * @return Berechtigung
	 * 
	 */
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 * @param ownerId - Eigentuemer
	 * @param receiverId - Empfaenger
	 * @param objectId - Objekt
	 * @param type - Objekttyp 
	 * @param avhsare - alle Auspraegungen die zum Teilen ausgewaehlt wurden
	 */
	void shareObject(int ownerId, int receiverId, int objectId, char type, Vector<Relatable> avhsare);

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteBerechtigung(Berechtigung b);
	 * @param b - die zu loeschende Berechtigung
	 * 
	 */
	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteAllBerechtigungenByOwner(Nutzer n, Berechtigung b);
	 * @param n - der aktuell eingeloggte Nutzer
	 * @param objectId - das zuloeschende Objekt
	 * 
	 */
	public void deleteAllBerechtigungenByOwner(Nutzer n, int objectId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #deleteAllBerechtigungenByReceiver(Nutzer n, Berechtigung b);
	 * @param n - der aktuell eingeloggte Nutzer
	 * @param objectId - das zuloeschende Objekt
	 * 
	 */
	public void deleteAllBerechtigungenByReceiver(Nutzer n, int objectId) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #createBerechtigungForEigenschaft(Vectoravshare, int ownerId, int receiverId);
	 * @param avshare - Vector von Eigenschaften
	 * @param ownerId - id des Eigentuemers
	 * @param receiverId - id des Empfaengers
	 * 
	 */
	public void createBerechtigungForEigenschaft(Vector<Relatable> avshare, int ownerId, int receiverId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllBerechtigungenByOwner(int ownerId);
	 * @param ownerId - id des Eigentuemers
	 * @return alle Berechtigungen des Eigentuemers
	 * 
	 */
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int ownerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllBerechtigungenByReceiver(int receiver);
	 * @param receiverId - id des Empfaengers
	 * @return alle Berechtigungen des Empfaengers
	 * 
	 */
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int receiverId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #sharedWith(int objectId, char type, Nutzer n);
	 * @param objectId - Objekt (Kontakt oder Kontaktliste)
	 * @param type - Objekttyp
	 * @param n - aktuell eingeloggter Nutzer
	 * @return Alle Nutzer mit denen ein Kontakt oder Kontaktliste geteilt wurde
	 * 
	 */
	public Vector<Nutzer> sharedWith(int objectId, char type, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #sharedWithEmail(Nutzer n);
	 * @param n - aktuell eingeloggter Nutzer
	 * @return Alle Nutzer mit denen ein Kontakt oder Kontaktliste geteilt wurde  
	 * 
	 */
	public Vector<Nutzer> sharedWithEmail(Nutzer n) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: ABRUF DER GETEILTEN OBJEKTE *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllSharedKontakteByOwner(int ownerId);
	 * @param ownerId - id des Eigentuemers
	 * @return alle Kontakte die ein Eigentuermer geteilt hat
	 * 
	 */
	public Vector<Kontakt> getAllSharedKontakteByOwner(int ownerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllSharedKontakteByReceiver(int receiverId);
	 * @param receiverId - id des Empfaengers
	 * @return alle geteilten Kontakte welche mit dem Nutzer geteilt wurden
	 * 
	 */
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int receiverId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllSharedKontaktlistenByOwner(int ownerId);
	 * @param ownerId - id des Eigentuemers
	 * @return alle geteilten Kontaktlisten welche mit dem Nutzer geteilt wurden
	 * 
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int ownerId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllSharedKontaktlistenByReceiver(int receiverId);
	 * @param receiverId - id des Empfaengers
	 * @return alle geteilten Kontaktlisten welche mit dem Nutzer geteilt wurden
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int receiverId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n);
	 * @param k - Kontakt
	 * @param n - aktuell eingeloggter Nutzer
	 * @return alle geteilten Auspraegungen zu einem geteilten Kontakt k mit einem Nutzer n aus
	 * 
	 */
	public Vector<Relatable> getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n)
			throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: SUCHE *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontakteByName(String name, Nutzer n);
	 * @param name des Kontakts
	 * @param n - aktuell eigenloggter Nutzer
	 * @return  alle Kontakte des Nutzer welche einen Treffen auf den uebergebenen String haben
	 * 
	 */
	public Vector<Kontakt> getKontakteByName(String name, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontakteByAuspraegung(String wert, Nutzer n);
	 * @param wert der Eigenschaft (Auspraegung)
	 * @param n - aktuell eingeloggter Nutzer
	 * @return Kontakte die eine bestimmte Auspraegungen besitzen
	 * 
	 */
	public Vector<Kontakt> getKontakteByAuspraegung(String wert, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontakteByEigenschaft(String bezeichnung, Nutzer n);
	 * @param bezeichnung - die Bezeichnung Eigenschaft
	 * @param n- aktuell eingeloggter Nutzer
	 * @return Kontakte die eine bestimmte Eigenschaft besitzen
	 * 
	 */
	public Vector<Kontakt> getKontakteByEigenschaft(String bezeichnung, Nutzer n) throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: REPORT *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getAuspraegungByWert(String wert);
	 * @param wert der Eigenschaft (Auspraegung)
	 * @return Auspraegungen die einen bestimmten Wert besitzen
	 * 
	 */
	public Vector<Auspraegung> getAuspraegungByWert(String wert) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getKontaktByAusEig(String wert, String bezeichnung, Nutzer n);
	 * @param bezeichnung der Eigenschaft
	 * @param wert der Eigenschaft (Aupraegung)
	 * @param n - aktuell eingeloggter Nutzer
	 * @return Kontakte die eine bestimmte Eigenacht und Auspraegung besitzen
	 * 
	 */
	public Vector<Kontakt> getKontaktByAusEig(String bezeichnung, String wert, Nutzer n)
			throws IllegalArgumentException;

	/*
	 * ********************************************************************** *
	 * ABSCHNITT: SONSTIGES *
	 * **********************************************************************
	 */

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #getStatusForObject(int objectId, char type) ;
	 * @param objectId - id des Objekts (Kontaktliste, Kontakt oder Auspraegung)
	 * @param type - Typ des Objekts
	 * @return Status ob ein Objekt geteilt wurde oder nicht
	 * 
	 */
	public boolean getStatusForObject(int objectId, char type) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.sontact.server.EditorServiceImpl
	 *      #saveModifikationsdatum(int id);
	 * @param id des Kontakts
	 * 
	 */
	public void saveModifikationsdatum(int id) throws IllegalArgumentException;

}
