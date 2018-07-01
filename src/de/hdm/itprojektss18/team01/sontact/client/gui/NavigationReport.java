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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachTeilhabernReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleGeteiltenKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteNachEigenschaftenReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18.team01.sontact.shared.report.HTMLReportWriter;

/**
 * Die Navigation der GUI fuer den ReportGenerator. Aufbau aehnelt der
 * Editor-Navigation.
 * 
 * @see Navigation
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle, Miescha Gotthilf-Afshar
 *
 */
public class NavigationReport extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGeneratorService();

	/**
	 * Buttons der Navigation werden <code>final</code> gesetzt, damit die
	 * asynchronen Aufrufe sie anpassen koennen.
	 */
	final Button showAllKontakteReport = new Button("Alle Kontakte");
	final Button showAllKontakteNachBestimmtenAusp = new Button(
			"Kontakte nach bestimmten Eigenschaften und Auspraegungen");
	final Button showAllGeteiltenKontakteReport = new Button("Alle geteilten Kontakte");
	final Button showAllKontakteNachTeilhabendernReport = new Button("Kontakte nach bestimmten Teilhabern");

	Nutzer nutzer = new Nutzer();
	VerticalPanel contentPanel = new VerticalPanel();
	Kontakt k = new Kontakt();

	/**
	 * ScrollPanel fuer den navigierenden Report.
	 */
	ScrollPanel sc = new ScrollPanel();

	public NavigationReport(final Nutzer n) {
		VerticalPanel vp = new VerticalPanel();

		/**
		 * Styling und Festlegung der Buttons.
		 */
		showAllKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachBestimmtenAusp.setStyleName("ButtonStyle");
		showAllGeteiltenKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachTeilhabendernReport.setStyleName("ButtonStyle");

		showAllKontakteReport.setPixelSize(200, 80);
		showAllKontakteNachBestimmtenAusp.setPixelSize(200, 80);
		showAllGeteiltenKontakteReport.setPixelSize(200, 80);
		showAllKontakteNachTeilhabendernReport.setPixelSize(200, 80);

		/**
		 * Buttons werden der Anzeige innerhalb der Navigation angeheftet.
		 */
		vp.add(showAllKontakteReport);
		vp.add(showAllKontakteNachBestimmtenAusp);
		vp.add(showAllGeteiltenKontakteReport);
		vp.add(showAllKontakteNachTeilhabendernReport);

		RootPanel.get("navigatorR").add(vp);

		/**
		 * ClickHandler fuer den ersten Report, der die Klasse
		 * <code>Alle Kontakte</code> aufruft.
		 */
		showAllKontakteReport.addClickHandler(new ClickHandler() {

			/**
			 * Das Interface <code>Clickhandler</code> wird als anonyme Klasse erstellt und
			 * realisert die aufrufende <code>OnClick</code> Methode. Diese auf den Klick
			 * wartet, um dann diese Aktion ueber den Button aufzurufen.
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

								// Der Inhalt wird geleert und der HTML-process wird uebergeben.
								RootPanel.get("contentR").clear();
								writer.process(result);

								HTML html = new HTML("<div align=\"center\">" + writer.getReportText() + "</br></div>");
								ScrollPanel sc = new ScrollPanel(html);

								// Der Report wird dem Scrollpanel uebergeben
								sc.setSize("950px", "550px");
								sc.setVerticalScrollPosition(10);
								RootPanel.get("contentR").add(sc);

							}

						});

			}
		});

		/**
		 * ClickHandler fuer den zweiten Report, der die Klasse
		 * <code>Bestimmte Eigenschaften</code> aufruft.
		 */
		showAllKontakteNachBestimmtenAusp.addClickHandler(new ClickHandler() {

			/**
			 * Das Interface <code>Clickhandler</code> wird als anonyme Klasse erstellt und
			 * realisert die aufrufende <code>OnClick</code> Methode. Diese auf den Klick
			 * wartet, um dann diese Aktion ueber den Button aufzurufen.
			 */
			public void onClick(ClickEvent event) {
				RootPanel.get("contentR").clear();
				HorizontalPanel hp = new HorizontalPanel();

				// Filterung nach Eigenschaft
				TextBox eingabe = new TextBox();
				ListBox auswahl = new ListBox();
				auswahl.addItem("Eigenschaft");

				// Filterung nach Auspraegung
				TextBox eingabe1 = new TextBox();
				ListBox auswahl1 = new ListBox();
				auswahl1.addItem("Auspraegung");

				// Erstellung der Eigenschaften und Auspraegung Buttons
				Button btn = new Button("Report generieren");

				eingabe.setStyleName("contentR");
				auswahl.setStyleName("contentR");
				btn.setStyleName("contentR");
				eingabe1.setStyleName("contentR");
				auswahl1.setStyleName("contentR");
				eingabe1.setStyleName("contentR");
				auswahl1.setStyleName("contentR");
				btn.setStyleName("contentR");

				// Dem Rootpanel wird die neue Html-Seite uebergeben
				RootPanel.get("contentR").add(
						new HTML("<div=><h3>Kontakte nach bestimmten Eigenschaften und Auspraegungen</h3></br></div>"));
				RootPanel.get("contentR").add(new HTML("<div=><h6> Bitte geben Sie mindestens eine Eigenschaft "
						+ "oder eine Auspraegung an, um die entsprechenden Kontakte zu erhalten.. <h6> </div>"));
				RootPanel.get("contentR").add(new HTML("</br>"));
				RootPanel.get("contentR").add(new HTML("</br>"));
				RootPanel.get("contentR").add(new HTML("</br>"));

				// Dem Horizontal Panel werden die definierten Elemente hinzugefuegt
				hp.add(auswahl);
				hp.add(eingabe);
				hp.add(auswahl1);
				hp.add(eingabe1);
				hp.add(btn);

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					// Die Aktion des ClickHandlers ueber den Button wird festgelegt und uebergibt
					// bei Erfolg die Ausgabe des Reports.
					@Override
					public void onClick(ClickEvent event) {
						if (eingabe.getValue() == "" && eingabe1.getValue() == "") {
							Window.alert("Bitte geben sie etwas in das Textfeld ein.");
						} else if (auswahl.getSelectedItemText() == "Eigenschaft" && eingabe.getValue() != "") {

							final HTMLReportWriter writer = new HTMLReportWriter();
							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(),
									eingabe1.getValue(), n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										// Aufruf des prozessierenden Reports
										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											RootPanel.get("contentR").clear();
											writer.process(result);

											HTML html = new HTML(
													"<div align=\"center\">" + writer.getReportText() + "</br></div>");

											// Der Report wird dem Scrollpanel hinzugefuegt
											ScrollPanel sc = new ScrollPanel(html);
											sc.setSize("950px", "470px");
											sc.setVerticalScrollPosition(10);

											RootPanel.get("contentR").add(sc);
										}

									});
							// Die Aktion des ClickHandlers ueber den Button wird festgelegt und uebergibt
							// bei Erfolg die Ausgabe des Reports.
						} else if (auswahl1.getSelectedItemText() == "Auspraegung" && eingabe1.getValue() != "") {

							final HTMLReportWriter writer = new HTMLReportWriter();
							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(),
									eingabe1.getValue(), n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										// Aufruf des prozessierenden Reports
										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											RootPanel.get("contentR").clear();
											writer.process(result);

											HTML html = new HTML(
													"<div align=\"center\">" + writer.getReportText() + "</div>");

											// Der Report wird dem Scrollpanel hinzugefuegt
											ScrollPanel sc = new ScrollPanel(html);
											sc.setSize("950px", "470px");
											sc.setVerticalScrollPosition(10);
											RootPanel.get("contentR").add(sc);
										}

									});
						}

					}

				});

			}
		});

		/**
		 * ClickHandler fuer den dritten Report, der die Klasse
		 * <code>AlleGeteiltenKontakte</code> aufruft.
		 */
		showAllGeteiltenKontakteReport.addClickHandler(new ClickHandler() {

			/**
			 * Das Interface <code>Clickhandler</code> wird als anonyme Klasse erstellt und
			 * realisert die aufrufende <code>OnClick</code> Methode. Diese auf den Klick
			 * wartet, um dann diese Aktion ueber den Button aufzurufen.
			 */
			public void onClick(ClickEvent event) {
				final HTMLReportWriter writer = new HTMLReportWriter();

				ClientsideSettings.getReportGeneratorService().createAlleGeteiltenReport(n,
						new AsyncCallback<AlleGeteiltenKontakteReport>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(AlleGeteiltenKontakteReport result) {

								RootPanel.get("contentR").clear();
								writer.process(result);

								HTML html = new HTML("<div align=\"center\">" + writer.getReportText() + "</br></div>");
								ScrollPanel sc = new ScrollPanel(html);

								sc.setSize("950px", "470px");
								sc.setVerticalScrollPosition(10);
								RootPanel.get("contentR").add(sc);
							}

						});

			}
		});

		/**
		 * ClickHandler fuer den vierten Report, der die Klasse
		 * <code>SaemtlicheKontakteNachTeilhabern</code> aufruft.
		 */
		showAllKontakteNachTeilhabendernReport.addClickHandler(new ClickHandler() {

			/**
			 * Das Interface <code>Clickhandler</code> wird als anonyme Klasse erstellt und
			 * realisert die aufrufende <code>OnClick</code> Methode. Diese auf den Klick
			 * wartet, um dann diese Aktion ueber den Button aufzurufen.
			 */
			public void onClick(ClickEvent event) {

				RootPanel.get("contentR").clear();
				HorizontalPanel hp = new HorizontalPanel();
				ListBox emailGeteiltenutzer = new ListBox();

				Button btn = new Button("Report generieren");
				RootPanel.get("contentR")
						.add(new HTML("<div=\"center\"><h3>Saemtliche Kontakte nach bestimmten Teilhabern</h3></div>"));
				RootPanel.get("contentR")
						.add(new HTML(" <div=><h6>Bitte nennen Sie die G-Mail-Adresse des Nutzers, um die mit "
								+ "ihm geteilten Kontakte zu erhalten.</h6></div>"));
				RootPanel.get("contentR").add(new HTML("</br>"));
				RootPanel.get("contentR").add(new HTML("</br>"));
				RootPanel.get("contentR").add(new HTML("</br>"));

				hp.add(emailGeteiltenutzer);
				hp.add(btn);

				// Alle Kontakte des Nutzers die geteilt wurden, werden der Listbox
				// hinzugefuegt.
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
								// if (emailGeteiltenutzer.getItemText(i) ==
								// result.elementAt(i).getEmailAddress()) {
								// emailGeteiltenutzer.removeItem(i);
								// }
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

								ClientsideSettings.getReportGeneratorService().createNachTeilhabernReport(
										result.getEmailAddress(), n,
										new AsyncCallback<AlleKontakteNachTeilhabernReport>() {

											@Override
											public void onFailure(Throwable caught) {
												caught.getMessage().toString();

											}

											@Override
											public void onSuccess(AlleKontakteNachTeilhabernReport result) {

												if (result == null) {
													Window.alert(
															" Es existieren keine Kontakte mit diesen Ausprägungen");
												}
												RootPanel.get("contentR").clear();
												writer.process(result);

												HTML html = new HTML("<div align=\"center\">" + writer.getReportText()
														+ "</br></div>");
												ScrollPanel sc = new ScrollPanel(html);

												sc.setSize("950px", "470px");
												sc.setVerticalScrollPosition(10);
												RootPanel.get("contentR").add(sc);

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
