package de.hdm.itprojektss18.team01.sontact.shared;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.bo.*;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;



@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	void init();
	
	public AlleKontakteNachEigenschaftenReport createAuspraegungReport(String listboxwert, String eingabe, Nutzer n) 
			throws IllegalArgumentException;
	
	public AlleKontakteReport createAlleKontakteReport(Nutzer n) 
			throws IllegalArgumentException;
	
	public AlleGeteiltenKontakteReport createAlleGeteilteReport(int receiverId, Nutzer n, String eigenschaft, String auspraegung) 
			throws IllegalArgumentException;

	
}
