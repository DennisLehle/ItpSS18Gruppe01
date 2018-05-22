package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class Eigenschaft extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen
	 */
	private String bezeichnung;
	Auspraegung a;
	

	/**
	 * Getter- und Setter zum Setzen und Auslesen der Werte
	 * 
	 * @return
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public Auspraegung getAuspraegung() {
		return a;
	}
	
	public void setAuspraegung(Auspraegung a) {
		this.a = a;
	}

}
