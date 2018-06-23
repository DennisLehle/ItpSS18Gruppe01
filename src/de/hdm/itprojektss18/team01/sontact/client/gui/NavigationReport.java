package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.HTMLReportWriter;

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
	final Button showAllKontakteReport = new Button("Alle Kontakte");
	final Button showAllKontakteNachBestimmtenAusp = new Button("Alle Kontakte nach bestimmten Eigenschaften");
	final Button showAlleGeteiltenKontakteReport = new Button("Alle geteilten Kontakte nach bestimmten Teilhabern");
	Nutzer nutzer = new Nutzer();
	VerticalPanel contentPanel = new VerticalPanel();
	Kontakt k = new Kontakt();

	public NavigationReport(final Nutzer n) {
		VerticalPanel vp = new VerticalPanel();
		/**
		 * Styling der Buttons
		 */
		showAllKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachBestimmtenAusp.setStyleName("ButtonStyle");
		showAlleGeteiltenKontakteReport.setStyleName("ButtonStyle");

		showAllKontakteReport.setPixelSize(160, 60);
		showAllKontakteNachBestimmtenAusp.setPixelSize(160, 60);
		showAlleGeteiltenKontakteReport.setPixelSize(160, 60);

		/**
		 * Button zur Anzeige der Navigation anheften
		 */
		vp.add(showAllKontakteReport);
		vp.add(showAllKontakteNachBestimmtenAusp);
		vp.add(showAlleGeteiltenKontakteReport);

		RootPanel.get("navigatorR").add(vp);


		/**
		 * ClickHandler für den Report um "Alle Kontakte" zu erstellen.
		 */
		showAllKontakteReport.addClickHandler(new ClickHandler() {
			/**
			 * Interface clickhandler wird als anonyme klasse erstellt und realisert die on
			 * click methode, die auf einen klick wartet und dann ausgeführt wird wenn der
			 * Button geklickt wird.
			 */
			public void onClick(ClickEvent event) {
				final HTMLReportWriter writer = new HTMLReportWriter();
				
				ClientsideSettings.getReportGeneratorService().createAlleKontakteReport(n,
						new AsyncCallback<AlleKontakteReport>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(AlleKontakteReport result) {
//								MessageBox.alertWidget("Hinweis", "In diesem Report finden Sie eine �bersicht all Ihrer Kontakte. "
//										+ "Dies beinhaltet alle Ihre Kontakte und alle Kontakte die mit Ihnen geteilt wurden.");
								RootPanel.get("contentR").clear();							
								writer.process(result);

								RootPanel.get("contentR").add(new HTML("<div align=\"center\">"+ writer.getReportText() + "</div>"));

							}

						});

			}
		});

		/**
		 * ClickHandler für den Report um "Bestimmte Eigenschaften" zu erstellen.
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
				Button btn = new Button("Report generieren");
				auswahl.addItem("");
				auswahl.addItem("Eigenschaft");
				auswahl.addItem("Auspraegung");
				
				eingabe.setStyleName("contentR");
				auswahl.setStyleName("contentR");
				btn.setStyleName("contentR");
				RootPanel.get("contentR").add(new HTML("<div align=\"center\"> <H3>Alle Kontakte nach bestimmten Eigenschaften</H3></div>"));
				RootPanel.get("contentR").add(new HTML("<div align=\"center\"> Bitte legen Sie anhand der Auswahl, "
						+ "die Kontakteigenschaften Ihrer Kontakte fest, wonach der Report generiert wird. </div>"));

				hp.add(auswahl);
				hp.add(eingabe);
				hp.add(btn);

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (eingabe.getValue() == "") {
							Window.alert("Bitte geben sie etwas in das Textfeld ein.");
						} else if (auswahl.getSelectedItemText() == "Eigenschaft" && eingabe.getValue() != "") {

							final HTMLReportWriter writer = new HTMLReportWriter();
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

											RootPanel.get("contentR").add(new HTML("<div align=\"center\">"+ writer.getReportText() + "</div>"));

										}

									});

						}

						else if (auswahl.getSelectedItemText() == "Auspraegung" && eingabe.getValue() != "") {

							final HTMLReportWriter writer = new HTMLReportWriter();

							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(),
									null, n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											if (result == null) {
												Window.alert(" Es existieren keine Kontakte mit diesen Auspraegungen");
											}
											RootPanel.get("contentR").clear();
											writer.process(result);

											RootPanel.get("contentR").add(new HTML("<div align=\"center\">"+ writer.getReportText() + "</div>"));

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
				ListBox emailGeteiltenutzer = new ListBox();

				Button btn = new Button("Report generieren");
				RootPanel.get("contentR").add(new HTML("<div align=\"center\"> <H3> Alle geteilten Kontakte nach Teilhabern </H3> </div>"));
				RootPanel.get("contentR").add(new HTML(" <div align=\"center\"> </tr><tr><td> Bitte bestimmen Sie den teilhabenden Nutzer, "
						+ "wonach der Report generiert wird. </tr><tr><td> </div>"));

				hp.add(emailGeteiltenutzer);
				hp.add(btn);

				// Alle Kontakte mit denen der Nutzer Kontakte geteilt hat werden der Listbox
				// hinzugefügt.
				ev.sharedWithEmail(n, new AsyncCallback<Vector<Nutzer>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage();

					}

					@Override
					public void onSuccess(Vector<Nutzer> result) {

						if (result != null) {

							for (int i = 0; i < result.size(); i++) {
								emailGeteiltenutzer.addItem(result.elementAt(i).getEmailAddress());
								// ABfrage ob EMail-Adresse schon vorhanden ist oder nicht.
//								if (emailGeteiltenutzer.getItemText(i) == result.elementAt(i).getEmailAddress()) {
//									emailGeteiltenutzer.removeItem(i);
//								}
							}

						}

					}
				});

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						ev.getUserByGMail(emailGeteiltenutzer.getSelectedValue(), new AsyncCallback<Nutzer>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(Nutzer result) {
								
								final HTMLReportWriter writer = new HTMLReportWriter();

								ClientsideSettings.getReportGeneratorService().createAlleGeteilteReport(result.getEmailAddress(),
										 n, new AsyncCallback<AlleGeteiltenKontakteReport>() {

											@Override
											public void onFailure(Throwable caught) {
												caught.getMessage().toString();

											}

											@Override
											public void onSuccess(AlleGeteiltenKontakteReport result) {

												if (result == null) {
													Window.alert(" Es existieren keine Kontakte mit diesen Ausprägungen");
												}
												RootPanel.get("contentR").clear();
												writer.process(result);

												RootPanel.get("contentR").add(new HTML("<div align=\"center\">"+ writer.getReportText() + "</div>"));

											}

										});

								
							}

						});

					}
				});
			}

		});
	}

}
