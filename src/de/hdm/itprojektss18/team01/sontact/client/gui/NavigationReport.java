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
import de.hdm.itprojektss18.team01.sontact.shared.EditorService;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.HTMLReportWriter;
import de.hdm.itprojektss18.team01.sontact.shared.report.PlainTextReportWriter;

/**
 * Navigation der GUI für den ReportGenerator. Gleicher aufbau wie Editor
 * Navigation.
 * 
 * @see Navigation
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */
public class NavigationReport extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGeneratorService();
	/**
	 * Buttons der Navigation. final, damit Callbacks sie verändern können.
	 */

	final Button showAllKontakteNachBestimmtenAusp = new Button("Kontakte nach Ausprägungen");
	final Button showAlleGeteiltenKontakteReport = new Button("Alle geteilten Kontakte anzeigen");
	Nutzer nutzer = new Nutzer();
	VerticalPanel contentPanel = new VerticalPanel();
	Kontakt k = new Kontakt();

	public NavigationReport(final Nutzer n) {
		final Button showAllKontakteReport = new Button("Meine Kontakte Anzeigen");
		final Button searchButton = new Button("Suche Starten!");

		VerticalPanel vp = new VerticalPanel();
		/**
		 * Styling der Buttons
		 */
		showAllKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachBestimmtenAusp.setStyleName("ButtonStyle");
		showAlleGeteiltenKontakteReport.setStyleName("ButtonStyle");

		showAllKontakteReport.setPixelSize(150, 40);
		showAllKontakteNachBestimmtenAusp.setPixelSize(150, 40);
		showAlleGeteiltenKontakteReport.setPixelSize(150, 40);

		/**
		 * Button zur Anzeige der Navigation anheften
		 */
		vp.add(showAllKontakteReport);
		vp.add(showAllKontakteNachBestimmtenAusp);
		vp.add(showAlleGeteiltenKontakteReport);

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
				// final PlainTextReportWriter write3r = new PlainTextReportWriter();
				ClientsideSettings.getReportGeneratorService().createAlleKontakteReport(n,
						new AsyncCallback<AlleKontakteReport>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(AlleKontakteReport result) {

								RootPanel.get("contentR").clear();
								// write3r.process(result);
								writer.process(result);

								RootPanel.get("contentR").add(new HTML(writer.getReportText()));

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
				RootPanel.get("contentR").clear();
				HorizontalPanel hp = new HorizontalPanel();
				TextBox eingabe = new TextBox();
				ListBox auswahl = new ListBox();
				Button btn = new Button("Report starten !");
				auswahl.addItem("Eigenschaft");
				auswahl.addItem("Auspraegung");

				Window.alert("hey");
				hp.add(btn);
				hp.add(auswahl);
				hp.add(eingabe);

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (auswahl.getSelectedItemText() == "Eigenschaft") {

							final HTMLReportWriter writer = new HTMLReportWriter();

							// final PlainTextReportWriter writer = new PlainTextReportWriter();

							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(null,
									eingabe.getValue(), n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											RootPanel.get("contentR").clear();
											writer.process(result);

											RootPanel.get("contentR").add(new HTML(writer.getReportText()));

										}

									});

						}

						else if (auswahl.getSelectedItemText() == "Auspraegung") {

							final HTMLReportWriter writer = new HTMLReportWriter();

							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(),
									null, n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											RootPanel.get("contentR").clear();
											writer.process(result);

											RootPanel.get("contentR").add(new HTML(writer.getReportText()));

										}

									});

						}
					}

				});

			}
		});

		/**
		 * ClickHandler ür elle Kontakte die mit einem bestimmten Nutzer geteilt wurden
		 * auszugeben.
		 */
		showAlleGeteiltenKontakteReport.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("contentR").clear();
				HorizontalPanel hp = new HorizontalPanel();
				TextBox eingabe = new TextBox();
				ListBox auswahl = new ListBox();
				TextBox email = new TextBox();

				Button btn = new Button("Report starten !");
				auswahl.addItem("");
				auswahl.addItem("Eigenschaft");
				auswahl.addItem("Auspraegung");
			

				Window.alert("hey");
				hp.add(btn);
				hp.add(email);
				hp.add(auswahl);
				hp.add(eingabe);

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						// Nutzer pe Mail finden.
						ev.getUserByGMail(email.getValue(), new AsyncCallback<Nutzer>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(Nutzer result) {
								
										/*
										 * Bei eingabe des Teilhabenden Nutzers wird überprüft ob eine weitere Filterung
										 * angegben wurde. Daraufhin wird das jeweilige Ergebnis zurückgegeben. 
										 */
								if(auswahl.getSelectedItemText() == "" || eingabe.getValue() == "") {
											final HTMLReportWriter writer = new HTMLReportWriter();

											// final PlainTextReportWriter writer = new PlainTextReportWriter();

											ClientsideSettings.getReportGeneratorService().createAlleGeteilteReport(result.getId(), n, null, null, new AsyncCallback<AlleGeteiltenKontakteReport>() {
												

														@Override
														public void onFailure(Throwable caught) {
															caught.getMessage().toString();

														}

														@Override
														public void onSuccess(AlleGeteiltenKontakteReport result) {

															RootPanel.get("contentR").clear();
															writer.process(result);

															RootPanel.get("contentR").add(new HTML(writer.getReportText()));

														}

													});
										}
								
									//Filtert nach bestimmten Eigenschaften eines Teilhabenden Nutzers.
										else if (auswahl.getSelectedItemText() == "Eigenschaft" && result != null && eingabe.getValue() == "" ) {

											final HTMLReportWriter writer = new HTMLReportWriter();

											// final PlainTextReportWriter writer = new PlainTextReportWriter();

											ClientsideSettings.getReportGeneratorService().createAlleGeteilteReport(result.getId(), n, eingabe.getValue(), null, new AsyncCallback<AlleGeteiltenKontakteReport>() {

														@Override
														public void onFailure(Throwable caught) {
															caught.getMessage().toString();

														}

														@Override
														public void onSuccess(AlleGeteiltenKontakteReport result) {

															RootPanel.get("contentR").clear();
															writer.process(result);

															RootPanel.get("contentR").add(new HTML(writer.getReportText()));

														}

													});
											//Filtert nach bestimmten Ausprägungen eines Teilhabenden Nutzers.
										} else if(auswahl.getSelectedItemText() == "Auspraegung" && result != null && eingabe.getValue() == "" ) {
											
											final HTMLReportWriter writer = new HTMLReportWriter();

											// final PlainTextReportWriter writer = new PlainTextReportWriter();

											ClientsideSettings.getReportGeneratorService().createAlleGeteilteReport(result.getId(), n, null, eingabe.getValue(), new AsyncCallback<AlleGeteiltenKontakteReport>() {

														@Override
														public void onFailure(Throwable caught) {
															caught.getMessage().toString();

														}

														@Override
														public void onSuccess(AlleGeteiltenKontakteReport result) {

															RootPanel.get("contentR").clear();
															writer.process(result);

															RootPanel.get("contentR").add(new HTML(writer.getReportText()));

														}

													});
											
										}
										
								

							}

						});

					}
				});
			}

		});
	}

}
