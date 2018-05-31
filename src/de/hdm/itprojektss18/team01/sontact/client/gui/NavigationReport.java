package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;


/**
 * Navigation der GUI für den ReportGenerator.
 * Gleicher aufbau wie Editor Navigation.
 * 
 * @see Navigation
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */
public class NavigationReport extends VerticalPanel{
	

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGeneratorService();
	/**
	 * Buttons der Navigation. 
	 * final, damit Callbacks sie verändern können.
	 */
	final Button showAllKontakteReport = new Button("Alle Kontakte Anzeigen");
	final Button showAllKontakteNachBestimmtenAusp = new Button("Kontakte nach Ausprägungen");
	final Button showReportNotVisitedButton = new Button("Alle geteilten Kontakte anzeigen");
	
	

	public NavigationReport(final Nutzer n){
		
		VerticalPanel vp = new VerticalPanel();
		/**
		 * Styling der Buttons
		 */
		showAllKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachBestimmtenAusp.setStyleName("ButtonStyle");
		showReportNotVisitedButton.setStyleName("ButtonStyle");
	
		
		showAllKontakteReport.setPixelSize(150, 40);
		showAllKontakteNachBestimmtenAusp.setPixelSize(150, 40);
		showReportNotVisitedButton.setPixelSize(150, 40);
	

		/**
		 * Button zur Anzeige der Navigation anheften
		 */
		vp.add(showAllKontakteReport);
		vp.add(showAllKontakteNachBestimmtenAusp);
		vp.add(showReportNotVisitedButton);
	
		RootPanel.get("navigatorR").add(vp);

		
		/**
		 * ClickHandler für den "Meine Kontaktliste" Button.
		 */
		showAllKontakteReport.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("contentR").clear();
				


			}
		});
		
		/**
		 * ClickHandler für den "Meine Kontaktliste" Button.
		 */
		showAllKontakteNachBestimmtenAusp.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("contentR").clear();
			


			}
		});
		
		/**
		 * ClickHandler für den "Meine Kontaktliste" Button.
		 */
		showReportNotVisitedButton.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("contentR").clear();
			


			}
		});
	}
	
}
