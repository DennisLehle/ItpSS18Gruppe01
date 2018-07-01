package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Kontaktlistenklasse. 
 */

public class Kontaktliste extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontaktliste
	 */
	private String titel;
	private int ownerId;
	private char type = 'l';

	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public char getType() {
		return type;
	}


}