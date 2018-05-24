package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class KontaktlisteKontakt extends BusinessObject implements Participation {
	
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

	
	
	
	
	
	
	
		
	@Override
	public int getOwner() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public char getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Berechtigung getBerechtigung() {
		// TODO Auto-generated method stub
		return null;
	} 

}
