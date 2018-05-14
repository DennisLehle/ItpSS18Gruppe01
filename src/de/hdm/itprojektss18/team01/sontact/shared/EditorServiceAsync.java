package de.hdm.itprojektss18.team01.sontact.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public interface EditorServiceAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzer(String emailAddress, AsyncCallback<Nutzer> callback);

	void deleteNutzer(Kontakt kontakt, AsyncCallback<Void> callback);
	
	void createKontakt(String vorname, String nachname, Nutzer nutzer, AsyncCallback<Kontakt> callback);

	void saveKontakt(Kontakt k, AsyncCallback<Kontakt> callback);

	void removeKontakt(Kontakt k, AsyncCallback<Void> callback);

	void getKontaktById(int id, AsyncCallback<Kontakt> callback);

	void getKontaktByName(String name, AsyncCallback<Vector<Kontakt>> callback);

	void getAllKontakteByOwner(Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl, AsyncCallback<Void> callback);

	void removeKontaktFromKontaktliste(Kontakt k, AsyncCallback<Void> callback);

	
	
	



	
}
