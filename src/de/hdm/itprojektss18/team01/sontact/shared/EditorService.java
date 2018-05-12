package de.hdm.itprojektss18.team01.sontact.shared;


import java.sql.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;





@RemoteServiceRelativePath("editorservice")
public interface EditorService extends RemoteService{
	
	
	public void init() throws IllegalArgumentException;
	
	public Nutzer createNutzer(String emailAddress) throws IllegalArgumentException;
	
	public void deleteNutzer(Kontakt kontakt) throws IllegalArgumentException;

	public Kontakt createKontakt (String vorname, String nachname, Date erstellDat, Date modDat,
			int ownerId, int kontaktlisteId, Berechtigung berechtigung) throws IllegalArgumentException;
	
	public Kontakt saveKontakt (Kontakt k) throws IllegalArgumentException;
	
	public void removeKontakt (Kontakt k) throws IllegalArgumentException;
	
	public Kontakt getKontaktById(int id) throws IllegalArgumentException;
	
	public Vector<Kontakt> getKontaktByName(String name) throws IllegalArgumentException;
	
	public Vector<Kontakt> getAllKontakteByOwner (Nutzer n) throws IllegalArgumentException;
	
	public void addKontaktToKontaktliste(Kontakt k, Kontaktliste kl) throws IllegalArgumentException;

	public void removeKontaktFromKontaktliste(Kontakt k) throws IllegalArgumentException;
	

}
