package de.hdm.itprojektss18.team01.sontact.server.db;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

public class MapperTestKlasse {

	public static void main(String[] args) {
	
		
	Nutzer n = new Nutzer();
	n.setId(110000000);
	n.setEmailAddress("gmx.de");
	
	NutzerMapper.nutzerMapper().insert(n);
	
	
	Kontakt k = new Kontakt();
	k.setId(1);
	k.setVorname("Manuel");
	k.setNachname("Mueller");
	k.setKontaktlisteId(1);
	k.setOwnerId(1);
	
	KontaktMapper.kontaktMapper().insertKontakt(k);

	
	Kontaktliste kl = new Kontaktliste();
	kl.setId(1);
	kl.setTitel("friends");
	kl.setOwnerId(1);
	
	KontaktlistenMapper.kontaktlistenMapper().insert(kl);
	
	
	
		
		
		
		
		
		
	}

}
