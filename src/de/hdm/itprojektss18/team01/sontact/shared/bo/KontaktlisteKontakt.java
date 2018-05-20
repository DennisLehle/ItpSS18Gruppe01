package de.hdm.itprojektss18.team01.sontact.shared.bo;

public class KontaktlisteKontakt extends BusinessObject implements Participation {
	
	private static final long serialVersionUID = 1L;

	private int kontaktlisteid = 0; 
	private int kontaktid = 0;
	
	
	public int getKontaktlisteid() {
		return kontaktlisteid;
	}

	public void setKontaktlisteid(int kontaktlisteid) {
		this.kontaktlisteid = kontaktlisteid;
	}

	public int getKontaktid() {
		return kontaktid;
	}

	public void setKontaktid(int kontaktid) {
		this.kontaktid = kontaktid;
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
