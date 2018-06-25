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
 * Navigation der GUI fuer den ReportGenerator. Aufbau aehnelt der
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
	final Button showAllKontakteNachBestimmtenAusp = new Button("Kontakte nach bestimmten Eigenschaften und Auspraegungen");
	final Button showAllGeteiltenKontakteReport = new Button("Alle geteilten Kontakte");
	final Button showAllKontakteNachTeilhabendernReport = new Button("Kontakte nach bestimmten Teilhabern");

	Nutzer nutzer = new Nutzer();
	VerticalPanel contentPanel = new VerticalPanel();
	Kontakt k = new Kontakt();

	/**
	 * ScrollPanel fuer den navigierenden Baum.
	 */
	ScrollPanel sc = new ScrollPanel();

	public NavigationReport(final Nutzer n) {
		VerticalPanel vp = new VerticalPanel();

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		/**
		 * Styling und Festlegung der Buttons.
		 */
		showAllKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachBestimmtenAusp.setStyleName("ButtonStyle");
		showAllGeteiltenKontakteReport.setStyleName("ButtonStyle");
		showAllKontakteNachTeilhabendernReport.setStyleName("ButtonStyle");

		showAllKontakteReport.setPixelSize(160, 80);
		showAllKontakteNachBestimmtenAusp.setPixelSize(160, 80);
		showAllGeteiltenKontakteReport.setPixelSize(160, 80);
		showAllKontakteNachTeilhabendernReport.setPixelSize(160, 80);

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
								// MessageBox.alertWidget("Hinweis", "In diesem Report finden Sie eine ‹bersicht
								// all Ihrer Kontakte. "
								// + "Dies beinhaltet alle Ihre Kontakte und alle Kontakte die mit Ihnen geteilt
								// wurden.");
								RootPanel.get("contentR").clear();
								writer.process(result);

								// sc.add();

								RootPanel.get("contentR")
										.add(new HTML("<div align=\"center\">" + writer.getReportText() + "</div>"));

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
				TextBox eingabe = new TextBox();
				ListBox auswahl = new ListBox();
				Button btn = new Button("Report generieren");
//				auswahl.addItem("");
				auswahl.addItem("Eigenschaft");
//				auswahl.addItem("Auspraegung");
//				auswahl.addItem("Test Eig+Aus");
				
				//Test Melanie
				TextBox eingabe1 = new TextBox();
				ListBox auswahl1 = new ListBox();
//				Button btn1 = new Button("Report generieren");
//				auswahl1.addItem("");
//				auswahl1.addItem("Eigenschaft");
				auswahl1.addItem("Auspraegung");
//				auswahl.addItem("Test Eig+Aus");

				eingabe.setStyleName("contentR");
				auswahl.setStyleName("contentR");
				btn.setStyleName("contentR");
				
				eingabe1.setStyleName("contentR");
				auswahl1.setStyleName("contentR");
				
				RootPanel.get("contentR").add(new HTML(
						"<div align=\"center\"> <H3>Saemtliche Kontakte nach bestimmten Eigenschaften</H3></div>"));
				RootPanel.get("contentR").add(new HTML("<div align=\"center\"> Bitte legen Sie anhand der Auswahl, "
						+ "die Kontakteigenschaften Ihrer Kontakte fest, wonach sich der Report generieren soll. </div>"));

				hp.add(auswahl);
				hp.add(eingabe);
				//Test Melanie
				hp.add(auswahl1);
				hp.add(eingabe1);
//				hp.add(btn);
				
//				// Test Melanie 
				eingabe1.setStyleName("contentR");
				auswahl1.setStyleName("contentR");
				btn.setStyleName("contentR");
//				RootPanel.get("contentR").add(new HTML(
//						"<div align=\"center\"> <H3>Saemtliche Kontakte nach bestimmten Eigenschaften</H3></div>"));
//				RootPanel.get("contentR").add(new HTML("<div align=\"center\"> Bitte legen Sie anhand der Auswahl, "
//						+ "die Kontakteigenschaften Ihrer Kontakte fest, wonach sich der Report generieren soll. </div>"));
//
				hp.add(auswahl1);
				hp.add(eingabe1);
				hp.add(btn);
				
				// Bestehend

				RootPanel.get("contentR").add(hp);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (eingabe.getValue() == "" && eingabe1.getValue() == "") {
							Window.alert("Bitte geben sie etwas in das Textfeld ein.");
						} else if (auswahl.getSelectedItemText() == "Eigenschaft" && eingabe.getValue() != "" &&
								auswahl1.getSelectedItemText() == "Auspraegung" && eingabe1.getValue() != "") {

							final HTMLReportWriter writer = new HTMLReportWriter();
							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(),
									eingabe1.getValue(), n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {

											RootPanel.get("contentR").clear();
											writer.process(result);

											RootPanel.get("contentR").add(new HTML(
													"<div align=\"center\">" + writer.getReportText() + "</div>"));

										}

									});

						}

//						else if (auswahl.getSelectedItemText() == "Eigenschaft" && eingabe.getValue() != "" && 
//								auswahl1.getSelectedItemText() == "Auspraegung" && eingabe1.getValue() != "") {
//
//							final HTMLReportWriter writer = new HTMLReportWriter();
//
//							ClientsideSettings.getReportGeneratorService().createAuspraegungReport(eingabe.getValue(), eingabe1.getValue(),
//									n, new AsyncCallback<AlleKontakteNachEigenschaftenReport>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											caught.getMessage().toString();
//
//										}
//
//										@Override
//										public void onSuccess(AlleKontakteNachEigenschaftenReport result) {
//
//											if (result == null) {
//												Window.alert(" Es existieren keine Kontakte mit diesen Auspraegungen");
//											}
//											RootPanel.get("contentR").clear();
//											writer.process(result);
//
//											RootPanel.get("contentR").add(new HTML(
//													"<div align=\"center\">" + writer.getReportText() + "</div>"));
//
//										}
//
//									});
//
//						}
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
								// MessageBox.alertWidget("Hinweis", "In diesem Report finden Sie eine ‹bersicht
								// all Ihrer Kontakte. "
								// + "Dies beinhaltet alle Ihre Kontakte und alle Kontakte die mit Ihnen geteilt
								// wurden.");
								RootPanel.get("contentR").clear();
								writer.process(result);

								// sc.add();

								RootPanel.get("contentR")
										.add(new HTML("<div align=\"center\">" + writer.getReportText() + "</div>"));

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
						.add(new HTML("<div align=\"center\"> <H3> Saemtliche Kontakte nach Teilhabern </H3> </div>"));
				RootPanel.get("contentR").add(
						new HTML(" <div align=\"center\"> </tr><tr><td> Bitte bestimmen Sie einen teilhabenden Nutzer, "
								+ "wonach sich der Report generieren soll. </tr><tr><td> </div>"));

				hp.add(emailGeteiltenutzer);
				hp.add(btn);

				// Alle Kontakte mit denen der Nutzer Kontakte geteilt hat werden der Listbox
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
															" Es existieren keine Kontakte mit diesen Auspr√§gungen");
												}
												RootPanel.get("contentR").clear();
												writer.process(result);

												RootPanel.get("contentR").add(new HTML(
														"<div align=\"center\">" + writer.getReportText() + "</div>"));

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
