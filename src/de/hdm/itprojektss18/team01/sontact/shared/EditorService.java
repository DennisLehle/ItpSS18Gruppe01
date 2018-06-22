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
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#init();
	 */
	public void init()
			throws IllegalArgumentException;
	
	
 /* ********************************************************************** *
  * ABSCHNITT: NUTZER								     				   *
  * ********************************************************************** */
		
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createNutzer(String emailAddress);
	 */
	public Nutzer createNutzer(String emailAddress)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#findNutzerByEmail(String email);
	 */
	public Nutzer getUserByGMail(String email)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getNutzerById(int nutzerId);
	 */
	public Nutzer getNutzerById(int nutzerId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteNutzer(Nutzer n);
	 */
	public void deleteNutzer(Nutzer n)
			throws IllegalArgumentException;
	
	
 /* ********************************************************************** *
  * ABSCHNITT: KONTAKT								     				   *
  * ********************************************************************** */
		
	/**
	 * @see de.hdm.itprojektss18.team01.serverEditor.ServiceImpl
	 * 		#createKontakt(String vorname, String nachnamen Nutzer n);
	 */
	public Kontakt createKontakt(String vorname, String nachname, Nutzer n )
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.serverEditor.ServiceImpl
	 * 		#createKontaktRegistrierung(String vorname, String nachname, Nutzer n);
	 */
	public Kontakt createKontaktRegistrierung(String vorname, String nachname, Nutzer n)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#saveKontakt(Kontakt k);
	 */
	public Kontakt saveKontakt(Kontakt k)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#removeKontakt(Kontakt k);
	 */
	public void deleteKontakt(Kontakt k)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontaktById(int id);
	 */
	public Kontakt getKontaktById(int id) 
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getOwnKontakt(Nutzer n)
	 */
	public Kontakt getOwnKontakt(Nutzer n)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllKontakteByOwner(Nutzer n)
	 */
	public Vector<Kontakt> getAllKontakteByOwner(Nutzer n)
			throws IllegalArgumentException;

	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllKontakteByNutzer(Nutzer n);
	 */
	public Vector<Kontakt> getAllKontakteByNutzer(Nutzer n)
			throws IllegalArgumentException;
	
	
	/** 
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#addKontaktToKontaktliste(Kontakt k, Kontaktliste kl);
	 */
	public void addKontaktToKontaktliste(Kontaktliste kl, Kontakt k)
			throws IllegalArgumentException;

	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#removeKontaktFromKontaktliste(Kontakt k);
	 */
	public void removeKontaktFromKontaktliste(Kontaktliste kl, Kontakt k)
			throws IllegalArgumentException;

	
 /* ********************************************************************** *
  * ABSCHNITT: KONTAKTLISTE							     				   *
  * ********************************************************************** */
		
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createKontaktlisteRegistrierung(Nutzer n);
	 */
	public Kontaktliste createKontaktlisteRegistrierung(Nutzer n)
			throws IllegalArgumentException;;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createKontaktliste(String titel, Nutzer n);
	 */
	public Kontaktliste createKontaktliste(String titel, Nutzer n)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#saveKontaktliste(Kontaktliste kl);
	 */
	public void saveKontaktliste(Kontaktliste kl)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteKontaktliste(Kontaktliste kl);
	 */
	public void deleteKontaktliste(Kontaktliste kl)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontaktlisteById(int id);
	 */
	public Kontaktliste getKontaktlisteById(int id)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#findKontaktlisteByTitel(Nutzer n, String titel);
	 */
	public Kontaktliste findKontaktlisteByTitel(Nutzer n, String titel)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontaktlisteByOwner(Nutzer n);
	 */
	public Vector<Kontaktliste> getKontaktlistenByOwner(Nutzer n)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontakteByKontaktliste(Kontaktliste kl);
	 */
	public Vector<Kontakt> getKontakteByKontaktliste(int kontaktlisteId)
			throws IllegalArgumentException;	
	
	
 /* ********************************************************************** *
  * ABSCHNITT: EIGENSCHAFT & AUSPRAEGUNG				     			   *
  * ********************************************************************** */
		
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createEigenschaft(String bezeichnung);
	 */
	public Eigenschaft createEigenschaft(String bezeichnung)
			throws IllegalArgumentException;
	
	public Vector<Eigenschaft> createEigenschaftV(Vector<String> bezeichnung)
			throws IllegalArgumentException;

	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#saveEigenschaft(Eigenschaft e);
	 */	
	public Eigenschaft saveEigenschaft(Eigenschaft e)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteEigenschaft(Eigenschaft e);
	 */
	public void deleteEigenschaft(Eigenschaft e)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getEigenschaftById(int eigenschaftId);
	 */
	public Eigenschaft getEigenschaftById(int eigenschaftId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getEigenschaftAuswahl();
	 */
	public Vector<Eigenschaft> getEigenschaftAuswahl()
			throws IllegalArgumentException;
	
	
//	-> ?
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#findEigenschaftByBezeichnung(String bezeichnung);
	 */
	public Eigenschaft getEigenschaftByBezeichnung(String bezeichnung)
			throws IllegalArgumentException;
//	-> ?	
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createAuspraegung(String wert, int eigenschaftId, int kontaktId, int ownerId);
	 */
	public Auspraegung createAuspraegung(String wert, int eigenschaftId, int kontaktId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * #deleteEigenschaftcreateAuspraegungForNewEigenschaft(String bezeichnung, String wert, Kontakt k);
	 */
	public void createAuspraegungForNewEigenschaft(Vector<String> bezeichnung, Vector<String> wert, Kontakt k)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#saveAuspraegung(Auspraegung a);
	 */
	public void saveAuspraegung(Vector<Auspraegung> a)
			throws IllegalArgumentException;

	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteAuspraegung(Auspraegung a);
	 */
	public void deleteAuspraegung(Auspraegung a)
			throws IllegalArgumentException;

	
//	-> ?
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteAuspraegung(Auspraegung a);
	 */
	public void deleteAuspraegungById(int auspraegungId)
			throws IllegalArgumentException;
//	-> ?
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAuspraegungById(Auspraegung a);
	 */
	public Auspraegung getAuspraegungById(int auspraegungId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllAuspraegungenByKontakt(int kontaktId);
	 */
	public Vector<Auspraegung> getAllAuspraegungenByKontakt(int kontaktId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllAuspraegungenByKontaktRelatable(int kontaktId);
	 */
	public Vector<Relatable> getAllAuspraegungenByKontaktRelatable(int kontaktId)
			throws IllegalArgumentException;
	
		
//	-> ?	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getEigenschaftForAuspraegung(int eigenschaftId);
	 */
	public Eigenschaft getEigenschaftForAuspraegung(int eigenschaftId)
			throws IllegalArgumentException;
//	->			
	
		
 /* ********************************************************************** *
  * ABSCHNITT: BERECHTIGUNG							     				   *
  * ********************************************************************** */
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#createBerechtigung(int ownerId, int receiverId, int objectId, char type);
	 */
	public Berechtigung createBerechtigung(int ownerId, int receiverId, int objectId, char type)
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 *		 #shareObject (int ownerId, int receiverId, int objectId, char type, Vector<Relatable> avhsare);
	 */	
	public void shareObject(int ownerId, int receiverId, int objectId, char type, Vector<Relatable> avhsare)
				throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#deleteBerechtigung(Berechtigung b);
	 */
	public void deleteBerechtigung(Berechtigung b)
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllBerechtigungenByOwner(int ownerId);
	 */
	public Vector<Berechtigung> getAllBerechtigungenByOwner(int ownerId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllBerechtigungenByReceiver(int receiver);
	 */
	public Vector<Berechtigung> getAllBerechtigungenByReceiver(int receiverId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#sharedWith(int objectId, char type, Nutzer n);
	 */
	public Vector<Nutzer> sharedWith(int objectId, char type, Nutzer n)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#sharedWithEmail(Nutzer n);
	 */
	public Vector<Nutzer> sharedWithEmail(Nutzer n)
			throws IllegalArgumentException;
	
 /* ********************************************************************** *
  * ABSCHNITT: ABRUF DER GETEILTEN OBJEKTE			     				   *
  * ********************************************************************** */
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllSharedKontakteByOwner(int ownerId);
	 */
	public Vector<Kontakt> getAllSharedKontakteByOwner(int ownerId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllSharedKontakteByReceiver(int receiverId);
	 */
	public Vector<Kontakt> getAllSharedKontakteByReceiver(int receiverId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllSharedKontaktlistenByOwner(int ownerId);
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByOwner(int ownerId)
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllSharedKontaktlistenByReceiver(int receiverId);
	 */
	public Vector<Kontaktliste> getAllSharedKontaktlistenByReceiver(int receiverId) 
			throws IllegalArgumentException;
	
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n);
	 */
	public Vector<Relatable> getAllSharedAuspraegungenByKontaktAndNutzer(Kontakt k, Nutzer n)
			throws IllegalArgumentException;

	
 /* ********************************************************************** *
  * ABSCHNITT: SUCHE								     				   *
  * ********************************************************************** */
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontakteByName(String name, Nutzer n);
	 */
	public Vector<Kontakt> getKontakteByName(String name, Nutzer n)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontakteByAuspraegung(String wert, Nutzer n);
	 */
	public Vector<Kontakt> getKontakteByAuspraegung(String wert, Nutzer n) 
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getKontakteByEigenschaft(String bezeichnung, Nutzer n);
	 */
	public Vector<Kontakt> getKontakteByEigenschaft(String bezeichnung, Nutzer n) 
			throws IllegalArgumentException;
	
/* ********************************************************************** *
 * ABSCHNITT: REPORT								     				  *
 * ********************************************************************** */
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getAuspraegungByWert(String wert);
	 */
	public Vector<Auspraegung> getAuspraegungByWert(String wert)
			throws IllegalArgumentException;	
	
	
 /* ********************************************************************** *
  * ABSCHNITT: SONSTIGES								     			   *
  * ********************************************************************** */
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#getStatusForObject(int objectId, char type) ;
	 */
	public boolean getStatusForObject(int objectId, char type) 
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl
	 * 		#saveModifikationsdatum(int id);
	 */
	public void saveModifikationsdatum(int id)
			throws IllegalArgumentException;
	
	
}
