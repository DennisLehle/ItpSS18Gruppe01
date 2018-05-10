package de.hdm.itprojektss18.team01.sontact.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojektss18.team01.sontact.server.db.*;
import de.hdm.itprojektss18.team01.sontact.shared.*;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;




public class EditorServiceImpl extends RemoteServiceServlet implements EditorService{
	public EditorServiceImpl() throws IllegalArgumentException {
}

	/**
	 * Serialisierung 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ein spezielles Nutzer-Objekt wird referenziert.  
	 */
	private Nutzer nutzer = null;
		
	/**
	   * Die Mapperklasse wird referenziert, die Nutzer-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private NutzerMapper nMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Kontakt-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private KontaktMapper kMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Kontaktlisten-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private KontaktlistenMapper klMapper = null;
	
	/**
	   * Die Mapperklasse wird referenziert, die Eigenschafts-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private EigenschaftMapper eMapper = null;

	/**
	   * Die Mapperklasse wird referenziert, die Auspraegung-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private AuspraegungMapper aMapper = null;

	/**
	   * Die Mapperklasse wird referenziert, die Berechtigung-Objekte mit 
	   * der Datenbank vergleicht. 
	   */
	private BerechtigungMapper bMapper = null;
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Initialisierung
	   * ***************************************************************************
	   */
	/**
	 * Vollstaendiger Satz von Mappern mit deren Hilfe EditorServiceImpl
	 * mit der Datenbank kommunizieren kann.
	 */
	public void init() throws IllegalArgumentException {
		this.nMapper = NutzerMapper.nutzerMapper();
	    this.kMapper = KontaktMapper.kontaktMapper();
	    this.klMapper = KontaktlistenMapper.kontaktlistenMapper();
	    this.eMapper = EigenschaftMapper.eigenschaftMapper();
	    this.aMapper = AuspraegungMapper.auspraegungMapper();
	    this.bMapper = BerechtigungMapper.berechtigungMapper();
	}
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Initialisierung
	   * ***************************************************************************
	   */
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Nutzer-Objekte
	   * ***************************************************************************
	   */
	/**
	 * Neues Nutzer-Objekt wird angelegt und in der Datenbank gespeichert.
	 */
	public Nutzer createNutzer(String emailAddress) 
			throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmailAddress(emailAddress);
		
		/* Es wird eine vorläufige Id gesetzt die anschließend in der Datenbank
		 * nach Verfügbarkeit angepasst wird.
		 */
		nutzer.setId(1);
		
		//Einfügen und Speichern in der Datenbank.
		return this.nMapper.insert(nutzer);
	}
	
	/**
	 * Nutzer-Objekt wird entfernt und aus der Datenbank gelöscht.
	 */
	public void deleteNutzer(Kontakt kontakt) {
		
	}
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Nutzer-Objekte
	   * ***************************************************************************
	   */
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Kontakt-Objekte
	   * ***************************************************************************
	   */
	public Kontakt createKontakt (String vorname, String nachname, DateTimeFormat erstellDat,
			DateTimeFormat modDat, int ownerId, int kontaktlisteId, Berechtigung berechtigung)
					throws IllegalArgumentException {
		
		Kontakt kontakt = new Kontakt();
		kontakt.setNachname(nachname);
		kontakt.setErstellDat(erstellDat);
		kontakt.setModDat(modDat);
		kontakt.setOwnerId(ownerId);
		kontakt.setKontaktlisteId(kontaktlisteId);
		kontakt.setBerechtigung(berechtigung);
		
		
		kontakt.setId(1);
		return this.kMapper.insert(kontakt);
		
	
	}
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Kontakt-Objekte
	   * ***************************************************************************
	   */
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Kontaktlisten-Objekte
	   * ***************************************************************************
	   */
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Kontaktlisten-Objekte
	   * ***************************************************************************
	   */
	
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Eigenschaft-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Eigenschaft-Objekte
	   * ***************************************************************************
	   */
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Auspraegung-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Auspraegung-Objekte
	   * ***************************************************************************
	   */
	
	
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden fuer Berechtigung-Objekte
	   * ***************************************************************************
	   */
	/*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden fuer Berechtigung-Objekte
	   * ***************************************************************************
	   */
	
}


