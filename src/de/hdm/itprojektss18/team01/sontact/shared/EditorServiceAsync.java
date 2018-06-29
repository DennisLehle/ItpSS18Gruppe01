package de.hdm.itprojektss18.team01.sontact.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Asynchrones Gegenstueck des Interfaces <code>EditorService</code>
 *
 */

public interface EditorServiceAsync {

	void init(AsyncCallback<Void> callback);

	void getNutzerById(int nutzerId, AsyncCallback<Nutzer> callback);

	void createNutzer(String emailAddress, AsyncCallback<Nutzer> callback);

	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	void getUserByGMail(String email, AsyncCallback<Nutzer> callback);

	void createKontakt(String vorname, String nachname, Nutzer n, AsyncCallback<Kontakt> callback);

	void saveKontakt(Kontakt k, AsyncCallback<Kontakt> callback);

	void deleteKontakt(Kontakt k, AsyncCallback<Void> callback);

	void getKontaktById(int id, AsyncCallback<Kontakt> callback);

	void getAllKontakteByOwner(Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k, AsyncCallback<Void> callback);

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

	void createAuspraegungForNewEigenschaft(Vector<String> bezeichnung, Vector<String> wert, Kontakt k, int ownerId,
			AsyncCallback<Void> callback);

	void getEigenschaftAuswahl(AsyncCallback<Vector<Eigenschaft>> callback);

	void createAuspraegung(String wert, int eigenschaftId, int kontaktId, int ownerId,
			AsyncCallback<Auspraegung> callback);

	void saveAuspraegung(Vector<Auspraegung> a, AsyncCallback<Void> callback);

	void deleteAuspraegung(Auspraegung a, AsyncCallback<Void> callback);

	void deleteAuspraegungById(int auspraegungId, AsyncCallback<Void> callback);

	void getAuspraegungById(int auspraegungId, AsyncCallback<Auspraegung> callback);

	void getAllAuspraegungenByKontakt(int kontaktId, AsyncCallback<Vector<Auspraegung>> callback);

	void getAllAuspraegungenByKontaktRelatable(int kontaktId, AsyncCallback<Vector<Relatable>> callback);

	void saveModifikationsdatum(int id, AsyncCallback<Void> callback);

	void getOwnKontakt(Nutzer n, AsyncCallback<Kontakt> callback);

	void shareObject(int ownerId, int receiverId, int objectId, char type, Vector<Relatable> avhsare, AsyncCallback<Void> callback);

	void createBerechtigung(int ownerId, int receiverId, int objectId, char type, AsyncCallback<Berechtigung> callback);

	void deleteBerechtigung(Berechtigung b, AsyncCallback<Void> callback);

	void getKontaktlisteById(int id, AsyncCallback<Kontaktliste> callback);

	void createKontaktlisteRegistrierung(Nutzer n, AsyncCallback<Kontaktliste> callback);

	void createKontaktRegistrierung(String vorname, String nachname, Nutzer n, AsyncCallback<Kontakt> callback);

	void getAllBerechtigungenByOwner(int ownerId, AsyncCallback<Vector<Berechtigung>> callback);

	void getAllBerechtigungenByReceiver(int receiverId, AsyncCallback<Vector<Berechtigung>> callback);

	void getAllSharedKontakteByOwner(int ownerId, AsyncCallback<Vector<Kontakt>> callback);

	void getAllSharedKontakteByReceiver(int receiverId, AsyncCallback<Vector<Kontakt>> callback);

	void getAllSharedKontaktlistenByOwner(int ownerId, AsyncCallback<Vector<Kontaktliste>> callback);

	void getAllSharedKontaktlistenByReceiver(int receiverId, AsyncCallback<Vector<Kontaktliste>> callback);

	void getStatusForObject(int objectId, char type, AsyncCallback<Boolean> callback);

	void getEigenschaftForAuspraegung(int eigenschaftId, AsyncCallback<Eigenschaft> callback);

	void getKontakteByName(String name, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void getKontakteByAuspraegung(String wert, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void getKontakteByEigenschaft(String bezeichnung, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void getEigenschaftByBezeichnung(String bezeichnung, AsyncCallback<Eigenschaft> callback);

	void getAllKontakteByNutzer(Nutzer n, AsyncCallback<Vector<Kontakt>> callback);

	void sharedWith(int objectId, char type, Nutzer n, AsyncCallback<Vector<Nutzer>> callback);
	
	void sharedWithEmail(Nutzer n, AsyncCallback<Vector<Nutzer>> callback);

	void getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n, AsyncCallback<Vector<Relatable>> callback);

	void getEigenschaftById(int eigenschaftId, AsyncCallback<Eigenschaft> callback);

	void getAuspraegungByWert(String wert, AsyncCallback<Vector<Auspraegung>> callback);

	void createEigenschaftV(Vector<String> bezeichnung, AsyncCallback<Vector<Eigenschaft>> callback);

	void findAllNutzer(AsyncCallback<Vector<Nutzer>> callback);
	
	void getKontaktByAusEig(String bezeichnung, String wert, Nutzer n, AsyncCallback<Vector<Kontakt>> callback);


}
