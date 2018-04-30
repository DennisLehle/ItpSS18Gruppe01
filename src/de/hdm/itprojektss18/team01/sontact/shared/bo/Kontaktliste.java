package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Kontaktlistenklasse. 
 */

public class Kontaktliste extends BusinessObject implements Participation {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen der Klasse Kontaktliste
	 */
	private String titel;
	int ownerId;
	private Berechtigung berechtigung;

	/**
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */
	
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
	@Override
	public char getObjectType() {
		return 'l';
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