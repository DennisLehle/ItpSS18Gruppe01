package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Wird aufgerufen wenn Nutzer noch kein eigenen Kontakt (Kontakt des Nutzers)
 * im System angelegt hat.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class RegistrierungsForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	Kontakt k = new Kontakt();
	Eigenschaft e = new Eigenschaft();

	Auspraegung a = new Auspraegung();
	Auspraegung a2 = new Auspraegung();

	Nutzer n = new Nutzer();

	/*
	 * Datenstruktur welche die Auswahleigenschaften beinhaltet Um diese nicht
	 * erneut über einen zusätzlichen RPC Aufruf bereitzustellen
	 */
	Vector<Eigenschaft> eigenschaften = new Vector<>();

	// TextBoxen
	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();

	// Labels
	Label gmail = new Label("Gmail-Adresse:");
	Label vorname = new Label("Vorname:");
	Label nachname = new Label("Nachname:");

	// Panel welches die Labels und die TextBoxen beinhaltet
	VerticalPanel vp = new VerticalPanel();

	// Hauptpanel fuer die Ansicht der Kontakterstellung
	VerticalPanel hauptPanel = new VerticalPanel();
	//// Hauptpanel fuer die Ansicht der Kontakteigenschaftsangaben
	VerticalPanel hauptPanel2 = new VerticalPanel();
	// ButtonPanel beinhaltet die Buttons bei der Kontakterstellungs-Ansicht
	HorizontalPanel BtnPanel = new HorizontalPanel();

	// Panels fuer die Anordnung der zwei FlexTables
	HorizontalPanel FlexTablePanel = new HorizontalPanel();
	VerticalPanel FlexPanelAusw = new VerticalPanel();
	VerticalPanel FlexPanelEigene = new VerticalPanel();

	// FlexTables
	FlexTable KontaktinfoTable = new FlexTable();
	FlexTable auswahlEigenschaftenTable = new FlexTable();
	FlexTable eigeneEigenschaftenTable = new FlexTable();

	// Buttons welche bei der Kontakterstellungs-Ansicht angezeigt werden
	// Diese sind dem obigen BtnPanel zugeordnet
	Button quitBtn = new Button("Abbrechen");
	Button weiterBtn = new Button("Weiter");

	// ScrollPanel fuer die die Ansicht der Kontakteigenschaftsangaben
	// Beinhaltet die zwei Flextables
	ScrollPanel sp = new ScrollPanel();

	/**
	 * Konstruktor
	 * 
	 */
	public RegistrierungsForm(Nutzer nutzer) {
		n = nutzer;

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		// Ueberschrift anzeigen
		RootPanel.get("contentHeader").add(new HTML("Kontakt erstellen"));

		// Je FlexTable wird ihrem eigenen VerticalPanel zugeordnet
		FlexPanelAusw.add(auswahlEigenschaftenTable);
		FlexPanelEigene.add(eigeneEigenschaftenTable);
		// Beide VerticalPanels werden einem HorizontalPanel zugeordnet
		FlexTablePanel.add(FlexPanelAusw);
		FlexTablePanel.add(FlexPanelEigene);
		
		FlexTablePanel.setSpacing(30);
		// Dieses VerticalPanel wird dem ScrollPanel zugeordnet
		sp.add(FlexTablePanel);

		// Groesse des ScrollPanels anpassen
		sp.setSize("900px", "400px");

		BtnPanel.setSpacing(20);

		// TextBoxen fuer die Anzeige der Gmail Adresse welche beim Login angegeben wurde
		gmailTb.setText(nutzer.getEmailAddress());
		gmailTb.setEnabled(false);

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		// Abbruch Button wird der Kontakterstellungs-Ansicht hinzugefuegt
		BtnPanel.add(quitBtn);
		// ClickHandler fuer den Abbruch-Button
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		// Weiter-Button wird der Kontakterstellungs-Ansicht hinzugefuegt
		// Dient zur Weiterleitung in die Kontakteigenschafts-Ansicht, hier werden die
		// Eigenschaften fuer den erstellen Kontakt dann angegeben
		BtnPanel.add(weiterBtn);
		// ClickHandler der die "Weiterleitung" zur Kontakteigenschaftsansicht durchfï¿½hrt
		weiterBtn.addClickHandler(new KontaktErstellenClickHandler());

		auswahlEigenschaftenTable.setCellPadding(35);
		eigeneEigenschaftenTable.setCellPadding(35);

		KontaktinfoTable.setWidget(0, 0, gmail);
		KontaktinfoTable.setWidget(0, 1, gmailTb);
		KontaktinfoTable.setWidget(1, 0, vorname);
		KontaktinfoTable.setWidget(1, 1, vornameTxtBox);
		KontaktinfoTable.setWidget(2, 0, nachname);
		KontaktinfoTable.setWidget(2, 1, nachnameTxtBox);
		KontaktinfoTable.setCellPadding(35);

		// Hauptpanel mit dem ButtonPanel verknï¿½pfen (Kontakterstellungs-Ansicht)
		hauptPanel.add(BtnPanel);
		hauptPanel.add(KontaktinfoTable);
		hauptPanel.setSpacing(20);
		this.add(hauptPanel);

	}

	/**
	 * ClickHandler zum Speichern der Kontakteigenschaften
	 */

	private class EigenschaftenSpeichern implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			/**
			 * Speichern der selbst definierten Eigenschafte und Auspraegungen
			 */
			for (int i = 0; i < eigeneEigenschaftenTable.getRowCount(); i++) {

				Widget w = eigeneEigenschaftenTable.getWidget(i, 0);

				if (w instanceof TextBox) {
					String bez = ((TextBox) w).getText();

					Widget v = eigeneEigenschaftenTable.getWidget(i, 1);
					if (v instanceof TextBox) {
						if (!((TextBox) v).getValue().isEmpty()) {
							a2.setWert(((TextBox) v).getValue());

							ev.createAuspraegungForNewEigenschaft(bez, a2.getWert(), k, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									// Nothing to do here..

								}

								@Override
								public void onSuccess(Void result) {
									Window.alert("Succeed");

								}

							});

						}

					}
				}

			}

			/**
			 * Speichern der Auswahleigenschaften die der Nutzer beliebig oft genereien bwz.
			 * auswï¿½hlen und befï¿½llen kann
			 */

			for (int i = 0; i <= auswahlEigenschaftenTable.getRowCount(); i++) {

				Widget w = auswahlEigenschaftenTable.getWidget(i, 0);
				if (w instanceof ListBox) {
					if (!((ListBox) w).getSelectedItemText().isEmpty()) {
						String bez = ((ListBox) w).getSelectedItemText();

						Widget v = auswahlEigenschaftenTable.getWidget(i, 1);
						if (v instanceof TextBox) {
							if (!((TextBox) v).getValue().isEmpty()) {
								a.setWert(((TextBox) v).getValue());

								for (int j = 0; j < eigenschaften.size(); j++) {
									if (eigenschaften.elementAt(j).getBezeichnung() == bez) {
										e = eigenschaften.elementAt(j);

									}

								}

							}

						}

					}

				}

				ev.createAuspraegung(a.getWert(), e.getId(), k.getId(), new AsyncCallback<Auspraegung>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());

					}

					@Override
					public void onSuccess(Auspraegung result) {
						// Nothing to do here...

					}
				});
				Window.Location.reload();
			}

		}
	}

	/**
	 * ClickHandler zum Generieren von weiteren Auswahleigenschaften.
	 */

	private class AuswahleigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			ListBox eigenschaftBox = new ListBox();
			TextBox wertBox = new TextBox();

			ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage();

				}

				@Override
				public void onSuccess(Vector<Eigenschaft> result) {
					if (result != null) {

						for (int i = 0; i < result.size(); i++) {
							eigenschaftBox.addItem(result.elementAt(i).getBezeichnung());

						}

					}

				}
			});

			int count = auswahlEigenschaftenTable.getRowCount();
			auswahlEigenschaftenTable.setWidget(count, 0, eigenschaftBox);
			auswahlEigenschaftenTable.setWidget(count, 1, wertBox);
			count++;

		}

	}

	/**
	 * ClickHandler zum Erzeugen von neuen Eigenschafts-Angaben
	 */
	private class EigeneEigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TextBox bezTb = new TextBox();
			TextBox wertTb = new TextBox();

			bezTb.getElement().setPropertyString("placeholder", "Eingabe...");
			wertTb.getElement().setPropertyString("placeholder", "Eingabe...");

			int count = eigeneEigenschaftenTable.getRowCount();
			eigeneEigenschaftenTable.setWidget(count, 0, bezTb);
			eigeneEigenschaftenTable.setWidget(count, 1, wertTb);
			count++;

		}

	}

	/**
	 * ClickHandler zum Erstellen eines neu angelegten Kontakts
	 */
	public class KontaktErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (vornameTxtBox.getText().isEmpty() || nachnameTxtBox.getText().isEmpty()) {
				MessageBox.alertWidget("Benachrichtigung", "Bitte mindestens Vor- und Nachname angeben");
			} else {
				ev.createKontaktRegistrierung(vornameTxtBox.getText(), nachnameTxtBox.getText(), n,
						new AsyncCallback<Kontakt>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();

							}

							@Override
							public void onSuccess(Kontakt result) {
								k = result;
								RootPanel.get("content").clear();
								
								RootPanel.get("contentHeader").clear();
								RootPanel.get("contentHeader").add(new HTML("Kontakteigenschaften angeben"));

								HorizontalPanel BtnPanel = new HorizontalPanel();
								Button addEigenschaftBtn = new Button("Weitere Auswahleigenschaften");

								Button speichernBtn = new Button("speichern");
								Button createEigenschaftBtn = new Button("Eigenschaft erstellen");
								createEigenschaftBtn.setTitle("Eigene Eigenschaft definieren");
								BtnPanel.add(addEigenschaftBtn);
								BtnPanel.add(createEigenschaftBtn);
								BtnPanel.add(speichernBtn);
								addEigenschaftBtn.setTitle("Weitere Auswahl hinzufügen");
								addEigenschaftBtn.addClickHandler(new AuswahleigenschaftClickHandler());
								createEigenschaftBtn.addClickHandler(new EigeneEigenschaftClickHandler());
								speichernBtn.addClickHandler(new EigenschaftenSpeichern());

								BtnPanel.setSpacing(25);
								hauptPanel2.add(BtnPanel);
								hauptPanel2.add(sp);
								hauptPanel2.setSpacing(35);
								
								RootPanel.get("content").add(hauptPanel2);

								ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {

									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage();

									}

									@Override
									public void onSuccess(Vector<Eigenschaft> result) {

										eigenschaften = result;

										if (result != null) {

											ListBox eigenschaftListBox1 = new ListBox();
											ListBox eigenschaftListBox2 = new ListBox();
											ListBox eigenschaftListBox3 = new ListBox();

											for (int i = 0; i < result.size(); i++) {
												eigenschaftListBox1.addItem(result.elementAt(i).getBezeichnung());
												eigenschaftListBox2.addItem(result.elementAt(i).getBezeichnung());
												eigenschaftListBox3.addItem(result.elementAt(i).getBezeichnung());

												TextBox wert1 = new TextBox();
												TextBox wert2 = new TextBox();
												TextBox wert3 = new TextBox();

												wert1.getElement().setPropertyString("placeholder", "Eingabe...");
												wert2.getElement().setPropertyString("placeholder", "Eingabe...");
												wert3.getElement().setPropertyString("placeholder", "Eingabe...");

												auswahlEigenschaftenTable.setWidget(0, 0, eigenschaftListBox1);
												auswahlEigenschaftenTable.setWidget(0, 1, wert1);
												auswahlEigenschaftenTable.setWidget(1, 0, eigenschaftListBox2);
												auswahlEigenschaftenTable.setWidget(1, 1, wert2);
												auswahlEigenschaftenTable.setWidget(2, 0, eigenschaftListBox3);
												auswahlEigenschaftenTable.setWidget(2, 1, wert3);

											}

										}

									}
								});

							}

						});

			}

		}
	}

}
