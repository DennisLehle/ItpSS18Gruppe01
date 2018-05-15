package de.hdm.itprojektss18.team01.sontact.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

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
	void setNutzer(Nutzer n, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#deleteNutzer(Nutzer n);
	 */
	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	
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

	
	// Abschnitt Kontaktliste:

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#methodenName();
	 */


	
	
	
	
	
	
	
	



	
}
