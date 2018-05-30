package de.hdm.itprojektss18.team01.sontact.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Asynchrones Gegenst�ck des Interfaces <code>EditorService</code>
 * @author Yakup Kanal
 *
 */

public interface EditorServiceAsync {

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#init();
	 */
	void init(AsyncCallback<Void> callback);

	
	// Abschnitt Nutzer:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createNutzer(String emailAddress);
	 */
	void createNutzer(String emailAddress, AsyncCallback<Nutzer> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#setNutzer(Nutzer n);
	 */
	
	//void setNutzer(Nutzer n, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteNutzer(Nutzer n);
	 */
	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#findNutzerByEmail(String email);
	 */
	void getUserByGMail(String email, AsyncCallback<Nutzer> callback);

	
	// Abschnitt Kontakt:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createKontakt(String vorname, String nachname);
	 */
	void createKontakt(String vorname, String nachname, Nutzer n, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#saveKontakt(Kontakt k);
	 */
	void saveKontakt(Kontakt k, AsyncCallback<Kontakt> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontakt(Kontakt k);
	 */
	void removeKontakt(Kontakt k, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontaktById(int id);
	 */
	void getKontaktById(int id, AsyncCallback<Kontakt> callback);

	void getAllKontakteByOwner(Nutzer n, AsyncCallback<Vector<Kontakt>> callback);
	
	void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontaktFromKontaktliste(Kontakt k);
	 */
	void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k, AsyncCallback<Void> callback);


	void createKontaktliste(String titel, Nutzer n, AsyncCallback<Kontaktliste> callback);


	void saveKontaktliste(Kontaktliste kl, AsyncCallback<Void> callback);


	void deleteKontaktliste(Kontaktliste kl, AsyncCallback<Void> callback);


	void getKontaktlistenByOwner(Nutzer n, AsyncCallback<Vector<Kontaktliste>> callback);


	void getKontakteByKontaktliste(int kontaktlisteId, AsyncCallback<Vector<Kontakt>> callback);

	void findKontaktlisteByTitel(Nutzer n, String titel, AsyncCallback<Kontaktliste> callback);



	void createEigenschaft(String bezeichnung, AsyncCallback<Eigenschaft> callback);


	void saveEigenschaft(Eigenschaft e, AsyncCallback<Eigenschaft> callback);


	void deleteEigenschaft(Eigenschaft e, AsyncCallback<Void> callback);
	
	void createAuspraegungForNewEigenschaft(Eigenschaft e, Auspraegung a, Nutzer n,
			AsyncCallback<Void> callback);


	void getEigenschaftAuswahl(AsyncCallback<Vector<Eigenschaft>> callback);


	void createAuspraegung(String wert, int eigenschaftId, int kontaktId, Nutzer n,
			AsyncCallback<Auspraegung> callback);


	void saveAuspraegung(Auspraegung a, AsyncCallback<Auspraegung> callback);


	void deleteAuspraegung(Auspraegung a, AsyncCallback<Void> callback);


	void getAuspraegungById(int auspraegungId, AsyncCallback<Auspraegung> callback);


	void getAllAuspraegungenByKontakt(int kontaktId, AsyncCallback<Vector<Auspraegung>> callback);


	void saveModifikationsdatum(int id, AsyncCallback<Void> callback);


	void getOwnKontakt(Nutzer n, AsyncCallback<Kontakt> callback);

	void shareObject(int ownerId, int receiverId, int objectId, char type, AsyncCallback<Void> callback);


	void createBerechtigung(int ownerId, int receiverId, int objectId, char type, AsyncCallback<Berechtigung> callback);


	void deleteBerechtigung(Berechtigung b, AsyncCallback<Void> callback);


	void getKontaktlisteById(int id, AsyncCallback<Kontaktliste> callback);


	void createKontaktlisteRegistrierung(Nutzer n, AsyncCallback<Kontaktliste> callback);


	void createKontaktRegistrierung(String vorname, String nachname, Nutzer n, AsyncCallback<Kontakt> callback);

	
	// Abschnitt Kontaktliste:

	void getAllBerechtigungenByOwner(int ownerId, AsyncCallback<Vector<Berechtigung>> callback);


	void getAllBerechtigungenByReceiver(int receiverId, AsyncCallback<Vector<Berechtigung>> callback);


	void getAllSharedKontakteByOwner(int ownerId, AsyncCallback<Vector<Kontakt>> callback);


	void getAllSharedKontakteByReceiver(int receiverId, AsyncCallback<Vector<Kontakt>> callback);


	void getAllSharedKontaktlistenByOwner(int ownerId, AsyncCallback<Vector<Kontaktliste>> callback);


	void getAllSharedKontaktlistenByReceiver(int receiverId, AsyncCallback<Vector<Kontaktliste>> callback);


	void getAllSharedKontakteBySharedKontaktliste(int kontaktlisteId, AsyncCallback<Vector<Kontakt>> callback);


	void getStatusForObject(int objectId, AsyncCallback<Boolean> callback);


	void getEigenschaftForAuspraegung(int eigenschaftId, AsyncCallback<Eigenschaft> callback);


	void sucheKontakt(String vorname, String nachname, String wert, String bezeichnung, Nutzer n,
			AsyncCallback<Vector<Kontakt>> callback);


	void getAuspraegungByWert(String wert, Nutzer n, AsyncCallback<Vector<Auspraegung>> callback);


	void getEigenschaftByBezeichnung(String bezeichnung, AsyncCallback<Vector<Eigenschaft>> callback);


	void getKontaktByVorname(String vorname, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);


	void getKontaktByNachname(String nachname, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);



	
	
	
	
	
	
	
	



	
}
