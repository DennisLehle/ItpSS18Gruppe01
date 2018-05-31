package de.hdm.itprojektss18.team01.sontact.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteByNutzerReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AllKontakteReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAllKontakteByReport(Nutzer n, AsyncCallback<AllKontakteByNutzerReport> callback);

	void createAllKontakteReport(Kontakt k, AsyncCallback<AllKontakteReport> callback);

}
