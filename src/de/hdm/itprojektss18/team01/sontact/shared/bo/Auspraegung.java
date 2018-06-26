package de.hdm.itprojektss18.team01.sontact.shared.bo;

/**
 * Realisierung einer exemplarischen Auspraegungsklasse.
 */

public class Auspraegung extends BusinessObject implements Relatable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzvariablen der Klasse Auspraegung
	 */
	private String wert;
	private int eigenschaftId;
	private int kontaktId;
	private boolean status;
	private Eigenschaft eigenschaft = new Eigenschaft();
	private char type = 'a';
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

	public char getType() {
		return type;
	}
	//Setzen der Eigenschaftsbezeichung.
	public void setBezeichnung(String bez) {
		this.eigenschaft.setBezeichnung(bez);
	}
	//Bezeichnung der Eigenschaft
	@Override
	public String getBezeichnung() {
		return this.eigenschaft.getBezeichnung();
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
