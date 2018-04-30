package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Auspraegungsklasse.
 */
//TODO implements Participation
public class Auspraegung extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariablen der Klasse Auspraegung
	 */
	private String wert;
	int eigenschaftId;
	int kontaktId;
	
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
	
}
