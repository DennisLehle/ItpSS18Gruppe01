package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.HTMLReportWriter;
import de.hdm.itprojektss18.team01.sontact.shared.report.PlainTextReportWriter;


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
	
	final Button showAllKontakteNachBestimmtenAusp = new Button("Kontakte nach Ausprägungen");
	final Button showReportNotVisitedButton = new Button("Alle geteilten Kontakte anzeigen");
	Nutzer nutzer = new Nutzer();
	VerticalPanel contentPanel = new VerticalPanel();
	
	
	

	public NavigationReport(final Nutzer n){
		final Button showAllKontakteReport = new Button("Meine Kontakte Anzeigen");
		final Button searchButton = new Button("Suche Starten!");

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
				final HTMLReportWriter writer = new HTMLReportWriter();
			//	final PlainTextReportWriter write3r = new PlainTextReportWriter();
				ClientsideSettings.getReportGeneratorService().createAlleKontakteReport(n, new AsyncCallback <AlleKontakteReport>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(AlleKontakteReport result) {
						
						RootPanel.get("contentR").clear();
						//write3r.process(result);
					writer.process(result);
					
					
					RootPanel.get("contentR").add(new HTML (writer.getReportText()));
						
					}
					
					
				});
				
				
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
				HorizontalPanel hp = new HorizontalPanel();
				TextBox eingabe = new TextBox();
				ListBox auswahl = new ListBox();
				Button btn = new Button("Report starten !");
				auswahl.addItem("Eigenschaft");
				auswahl.addItem("Ausprägung");
				
				Window.alert("hey");
				hp.add(btn);
				hp.add(auswahl);
				hp.add(eingabe);
				
				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						
//						final HTMLReportWriter writer = new HTMLReportWriter();
//						//	final PlainTextReportWriter write3r = new PlainTextReportWriter();
//							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(tb.getValue(), n, new AsyncCallback <AlleKontakteReport>() {
//
//								@Override
//								public void onFailure(Throwable caught) {
//									// TODO Auto-generated method stub
//									
//								}
//
//								@Override
//								public void onSuccess(AlleKontakteReport result) {
//									
//									RootPanel.get("contentR").clear();
//									//write3r.process(result);
//								writer.process(result);
//								
//								
//								RootPanel.get("contentR").add(new HTML (writer.getReportText()));
//									
//								}
//								
//								
//							});
//							
						
					}
					
					
					
				});
				

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
	
	class createAllKontakteReportCallback implements
	AsyncCallback<AlleKontakteReport> {
		@Override
		public void onFailure(Throwable error) {
			Window.alert("Profile Generation failed: " + error.getMessage());
			

		}

		@Override
		public void onSuccess(AlleKontakteReport report) {
			
			if (report != null) {
				
				/*
				 * Um den Report in HTML-Text zu überführen benötigen wir einen HTMLReportWriter
				 */
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
			
				
			
				/*
				 * Wir leeren das subPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
				 */
			//	reportPanel.clear();
				/*
				 * Wir befüllen das subPanel mit dem HTML-Text den wir vom ReporWriter erhalten
				 */
				RootPanel.get("contentR").add(new HTML(writer.getReportText()));
				//Window.alert(writer.getReportText());
				/*
				 * Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
				 */
			//	RootPanel.get("contentR").add(reportPanel);
			}
		//	reportPanel.add((IsWidget) this);
		}
		
		
	}	

	
}
