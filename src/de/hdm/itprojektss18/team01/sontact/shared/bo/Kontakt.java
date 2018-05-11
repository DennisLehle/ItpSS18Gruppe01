package de.hdm.itprojektss18.team01.sontact.shared.bo;

import java.sql.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Realisierung einer exemplarischen Kontaktklasse.
 */

public class Kontakt extends BusinessObject implements Participation {

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontakt
	 */
	private String vorname;
	private String nachname;
	private Date erstellDat;
	private Date modDat;
	private int ownerId;
	private int kontaktlisteId;
	private Berechtigung berechtigung;

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

	public Date getErstellDat() {
		return erstellDat;
	}

	public void setErstellDat(Date erstellDat) {
		this.erstellDat = erstellDat;
	}

	public Date getModDat() {
		return modDat;
	}

	public void setModDat(Date modDat) {
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

	@Override
	public char getType() {
		return 'k';
	}

	@Override
	public Berechtigung getBerechtigung() {
		return berechtigung;
	}

	public void setBerechtigung(Berechtigung berechtigung) {
		this.berechtigung = berechtigung;
	}

	@Override
	public int getOwner() {
		return ownerId;
	}

}
