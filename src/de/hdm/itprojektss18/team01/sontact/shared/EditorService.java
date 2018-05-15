package de.hdm.itprojektss18.team01.sontact.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Interface für RPC-Service
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
	 */
	public void setNutzer(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#init();
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
	
	
	// Abschnitt Kontakt:
	
	/**
	 * @see de.hdm.itprojektss18.team01.serverEditor.ServiceImpl#createKontakt(String vorname, String nachname);
	 */
	public Kontakt createKontakt(String vorname, String nachname) throws IllegalArgumentException;
	
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
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#getAllKontakteByOwner();
	 */
	public Vector<Kontakt> getAllKontakteByOwner() throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#addKontaktToKontaktliste(Kontakt k, Kontaktliste kl);
	 */
	public void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl) throws IllegalArgumentException;

	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#removeKontaktFromKontaktliste(Kontakt k);
	 */
	public void removeKontaktFromKontaktliste(Kontakt k) throws IllegalArgumentException;
	
	
	// Abschnitt Kontaktliste:
	
	/**
	 * @see de.hdm.itprojektss18.team01.server.EditorServiceImpl#methodenName();
	 */

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
