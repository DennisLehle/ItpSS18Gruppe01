package de.hdm.itprojektss18.team01.sontact.shared;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachTeilhabernReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;



@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	void init();
	
	AlleKontakteNachEigenschaftenReport createAuspraegungReport(String listboxwert, String listboxwert1, 
			Nutzer n);
	
	public AlleKontakteReport createAlleKontakteReport(Nutzer n) 
			throws IllegalArgumentException;
	
	public AlleKontakteNachTeilhabernReport createNachTeilhabernReport(String email, Nutzer n) 
			throws IllegalArgumentException;
	
	public AlleGeteiltenKontakteReport createAlleGeteiltenReport(Nutzer n) 
			throws IllegalArgumentException;

	
}
