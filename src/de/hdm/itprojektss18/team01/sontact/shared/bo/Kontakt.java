package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.sql.Date;
import java.sql.Timestamp;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Realisierung einer exemplarischen Kontaktklasse.
 */

public class Kontakt extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontakt
	 */
	private String vorname;
	private String nachname;
	private Timestamp erstellDat;
	private Timestamp modDat;
	private int ownerId;
	private int kontaktlisteId;
	private char identifier;
	private char type = 'k';

	/**
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Timestamp getErstellDat() {
		return erstellDat;
	}

	public void setErstellDat(Timestamp i) {
		this.erstellDat = i;
	}

	public Timestamp getModDat() {
		return modDat;
	}

	public void setModDat(Timestamp modDat) {
		this.modDat = modDat;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getKontaktlisteId() {
		return kontaktlisteId;
	}

	public void setKontaktlisteId(int kontaktlisteId) {
		this.kontaktlisteId = kontaktlisteId;
	}

	@Override
	public int getId() {
		return this.id;
	}


	public char getType() {
		return type;
	}


	public char getIdentifier() {
		return identifier;
	}

	public void setIdentifier(char identifier) {
		this.identifier = identifier;
	}
	
	

}
