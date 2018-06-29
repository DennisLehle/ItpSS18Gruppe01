package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class Eigenschaft extends BusinessObject implements Relatable  {

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

	//Wert der Eigenschaft
	@Override
	public String getWert() {
		
		return null;
	}

	@Override
	public boolean getStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getOwnerId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
