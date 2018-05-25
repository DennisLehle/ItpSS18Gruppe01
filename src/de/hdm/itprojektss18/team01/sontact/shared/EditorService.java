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


/**
 * Interface f�r RPC-Service
 * 
 * @author Yakup Kanal 
 *
 */


@RemoteServiceRelativePath("editorservice")
public interface EditorService extends RemoteService{
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#init();
	 */
	public void init() throws IllegalArgumentException;
	
	
	// Abschnitt Nutzer:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createNutzer(String emailAddress);
	 */
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#setNutzer(Nutzer n);
	 *
	public void setNutzer(Nutzer n) throws IllegalArgumentException;
**/
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#init();
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#findNutzerByEmail(String email);
	 */
	public Nutzer findNutzerByEmail (String email) throws IllegalArgumentException;
	
	
	// Abschnitt Kontakt:
	
	/**
	 * @see de.hdm.itprojektss18.team01.serverEditor.ServiceImpl#createKontakt(String vorname, String nachname);
	 */
	public Kontakt createKontakt(String vorname, String nachname, Nutzer n ) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#saveKontakt(Kontakt k);
	 */
	public Kontakt saveKontakt(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontakt(Kontakt k);
	 */
	public void removeKontakt(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontaktById(int id);
	 */
	public Kontakt getKontaktById(int id) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontaktByName(String name);
	 */
	public Vector<Kontakt> getKontaktByName(String name) throws IllegalArgumentException;
	
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n) throws IllegalArgumentException;

	public Kontakt getOwnKontakt(Nutzer n) throws IllegalArgumentException;

	/**
	 * @return 
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#addKontaktToKontaktliste(Kontakt k, Kontaktliste kl);
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontaktFromKontaktliste(Kontakt k);
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k) throws IllegalArgumentException;
	
	
	// Abschnitt Kontaktliste:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createKontaktliste(String totel, int ownerId);
	 */
	public Kontaktliste createKontaktliste (String titel, Nutzer n) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#saveKontaktliste(Kontaktliste kl);
	 */
	public Kontaktliste saveKontaktliste (Kontaktliste kl) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteKontaktliste(Kontaktliste kl);
	 */
	public void deleteKontaktliste (Kontaktliste kl) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontaktlisteByOwner();
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n) throws IllegalArgumentException;
	
	
	public Kontaktliste getKontaktlisteById(int id) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontakteByKontaktliste(Kontaktliste kl);
	 */
	public Vector <Kontakt> getKontakteByKontaktliste (int kontaktlisteId) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#findKontaktlisteByTitel(String titel);
	 */
	public Vector <Kontaktliste> getKontaktlisteByTitel (String titel) throws IllegalArgumentException;
	
	
	
	
	
	// Abschnitt Eigenschaft:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createEigenschaft(String bezeichnung);
	 */
	public Eigenschaft createEigenschaft (String bezeichnung);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#saveEigenschaft(Eigenschaft e);
	 */	
	public Eigenschaft saveEigenschaft (Eigenschaft e) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteEigenschaft(Eigenschaft e);
	 */
	public void deleteEigenschaft (Eigenschaft e) throws IllegalArgumentException;
	
	
	
	public void createAuspraegungForNewEigenschaft(/**String bezeichnung, String wert, int eigenschaftId, int kontaktId, int ownerId*/ Eigenschaft e, Auspraegung a, Nutzer n) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getEigenschaftAuswahl();
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl() throws IllegalArgumentException;
	
	
	// Abschnitt Auspr�gung:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createAuspraegung
	 * (String wert, int eigenschaftId, int kontaktId, int ownerId);
	 */
	public Auspraegung createAuspraegung (String wert, int eigenschaftId, int kontaktId, Nutzer n) 
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#saveAuspraegung (Auspraegung a);
	 */
	public Auspraegung saveAuspraegung (Auspraegung a) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteAuspraegung (Auspraegung a);
	 */
	public void deleteAuspraegung (Auspraegung a) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getAuspraegungById (Auspraegung a);
	 */
	public Auspraegung getAuspraegungById(Auspraegung a) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getAllAuspraegungenByKontakt (Kontakt k);
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId) throws IllegalArgumentException;
		
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getAllAuspraegungenByKontakt (Kontakt k);
	 */
	
	public void saveModifikationsdatum(int id) throws IllegalArgumentException;
	
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException;
	
	public void shareObject(int ownerId, int receiverId, int objectId, char type)
				throws IllegalArgumentException;
	
	
	public void deleteBerechtigung(Berechtigung b) throws IllegalArgumentException;
	
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int ownerId) throws IllegalArgumentException;
	
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int receiverId) throws IllegalArgumentException;
	
	public Vector<Kontakt> getAllSharedKontakteByOwner(int ownerId) throws IllegalArgumentException;
	
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int receiverId) throws IllegalArgumentException;
	
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int ownerId) throws IllegalArgumentException;
	
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int receiverId) throws IllegalArgumentException;
	
	public Vector<Kontakt> getAllSharedKontakteBySharedKontaktliste(int kontaktlisteId);
	
	public boolean getStatusForObject(int objectId) throws IllegalArgumentException;
	
	
	
	
}
