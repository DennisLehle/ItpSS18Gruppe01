package de.hdm.itprojektss18.team01.sontact.shared;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18.team01.sontact.shared.bo.*;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteByNutzerReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteReport;



@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	void init();
	
	public AllKontakteByNutzerReport createAllKontakteByReport(Nutzer n) 
			throws IllegalArgumentException;
	
	public AllKontakteReport createAllKontakteReport(Kontakt k) 
			throws IllegalArgumentException;
	
}
