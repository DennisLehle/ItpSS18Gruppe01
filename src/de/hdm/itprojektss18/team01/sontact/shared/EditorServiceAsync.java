package de.hdm.itprojektss18.team01.sontact.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Asynchrones Gegenstück des Interfaces <code>EditorService</code>
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
	void setNutzer(Nutzer n, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteNutzer(Nutzer n);
	 */
	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#findNutzerByEmail(String email);
	 */
	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);

	
	// Abschnitt Kontakt:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#createKontakt(String vorname, String nachname);
	 */
	void createKontakt(String vorname, String nachname, AsyncCallback<Kontakt> callback);

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

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getKontaktByName(String name);
	 */
	void getKontaktByName(String name, AsyncCallback<Vector<Kontakt>> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getAllKontakteByOwner();
	 */
	void getAllKontakteByOwner(AsyncCallback<Vector<Kontakt>> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#addKontaktToKontaktliste(Kontakt k, Kontaktliste kl);
	 */
	void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontaktFromKontaktliste(Kontakt k);
	 */
	void removeKontaktFromKontaktliste(Kontakt k, AsyncCallback<Void> callback);


	void createKontaktliste(String titel, int ownerId, AsyncCallback<Kontaktliste> callback);


	void saveKontaktliste(Kontaktliste kl, AsyncCallback<Kontaktliste> callback);


	void deleteKontaktliste(Kontaktliste kl, AsyncCallback<Void> callback);


	void getKontaktlistenByOwner(AsyncCallback<Vector<Kontaktliste>> callback);


	void getKontakteByKontaktliste(Kontaktliste kl, AsyncCallback<Vector<Kontakt>> callback);


	void findKontaktlisteByTitel(String titel, AsyncCallback<Vector<Kontaktliste>> callback);


	void createEigenschaft(String bezeichnung, AsyncCallback<Eigenschaft> callback);


	void saveEigenschaft(Eigenschaft e, AsyncCallback<Eigenschaft> callback);


	void deleteEigenschaft(Eigenschaft e, AsyncCallback<Void> callback);


	void createAuspraegung(String wert, int eigenschaftId, int kontaktId, int ownerId,
			AsyncCallback<Auspraegung> callback);


	void saveAuspraegung(Auspraegung a, AsyncCallback<Auspraegung> callback);


	void deleteAuspraegung(Auspraegung a, AsyncCallback<Void> callback);


	void getAuspraegungById(Auspraegung a, AsyncCallback<Auspraegung> callback);


	void getAllAuspraegungenByKontakt(Kontakt k, AsyncCallback<Vector<Auspraegung>> callback);

	
	// Abschnitt Kontaktliste:

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#methodenName();
	 */


	
	
	
	
	
	
	
	



	
}
