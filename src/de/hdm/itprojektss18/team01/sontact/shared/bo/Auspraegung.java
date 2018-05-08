package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Auspraegungsklasse.
 */

public class Auspraegung extends BusinessObject implements Participation {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariablen der Klasse Auspraegung
	 */
	private String wert;
	private int eigenschaftId;
	private int kontaktId;
	private int ownerId;
	private Berechtigung berechtigung;
	
	/**
	 * Getter- und Setter-Methoden zum Setzen und Auslesen der Werte
	 */
	
	public String getWert() {
		return wert;	
	}
	
	public void setWert (String wert) {
		this.wert = wert;
	}
	
	public int getEigenschaftId() {
		return eigenschaftId;
	}
	
	public void setEigenschaftId (int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}
	
	public int getKontaktId () {
		return kontaktId;
	}
	
	public void setKontaktId (int kontaktId) {
		this.kontaktId = kontaktId;
	}

	@Override
	public Berechtigung getBerechtigung() {
		return berechtigung;
	}
	
	public void setBerechtigung(Berechtigung berechtigung) {
		this.berechtigung = berechtigung;
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	@Override
	public int getOwner() {
		return kontaktId; 
	}

	@Override
	public char getType() {
		return 'a';
	}
	
}
