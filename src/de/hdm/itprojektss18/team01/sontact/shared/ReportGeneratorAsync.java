package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachTeilhabernReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;

/**
 * Asynchrones Gegenstueck des Interfaces <code>ReportGenerator</code>
 *
 */
public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAlleKontakteReport(Nutzer n, AsyncCallback<AlleKontakteReport> callback);

	void createAuspraegungReport(String listboxwert, String listboxwert1, Nutzer n,
			AsyncCallback<AlleKontakteNachEigenschaftenReport> callback);

	void createNachTeilhabernReport(String email, Nutzer n,
			AsyncCallback<AlleKontakteNachTeilhabernReport> callback);

	void createAlleGeteiltenReport (Nutzer n, AsyncCallback<AlleGeteiltenKontakteReport> callback);
	
	

	
	
	

}
