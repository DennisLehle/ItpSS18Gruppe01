package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class KontaktlisteKontakt extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	private int kontaktlisteId = 0; 
	private int kontaktId = 0;
	
	
	public int getKontaktlisteId() {
		return kontaktlisteId;
	}

	public void setKontaktlisteId(int kontaktlisteId) {
		this.kontaktlisteId = kontaktlisteId;
	}

	public int getKontaktId() {
		return kontaktId;
	}

	public void setKontaktId(int kontaktId) {
		this.kontaktId = kontaktId;
	}


}
