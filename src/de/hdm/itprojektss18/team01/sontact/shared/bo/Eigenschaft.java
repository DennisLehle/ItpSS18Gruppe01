package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class Eigenschaft extends BusinessObject implements Relatable  {

	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariablen
	 */
	private String bezeichnung;
	private Auspraegung a;
	
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

	public String getWert() {
		
		return null;
	}

	@Override
	public boolean getStatus() {
	
		return false;
	}

	@Override
	public int getOwnerId() {
		
		return 0;
	}

	@Override
	public char getType() {

		return 'a';
	}

}
