package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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
 * Wird aufgerufen wenn Nutzer noch kein eigenes Profil im System besitzt.
 * 
 * @author Ugur Bayrak, Kevin Batista, Dennis Lehle
 *
 */

public class RegistrierungsForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();

	Kontakt k = new Kontakt();
	Nutzer n = new Nutzer();

	FlexTable kontaktFlex = new FlexTable();

	ScrollPanel sp = new ScrollPanel();

	public RegistrierungsForm(Nutzer nutzer) {

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Kontakt erstellen</h2>"));

		// Groesse des ScrollPanels anpassen
		sp.setSize("900px", "400px");

		HorizontalPanel BtnPanel = new HorizontalPanel();

		gmailTb.setText(nutzer.getEmailAddress());
		gmailTb.setEnabled(false);

		Button addEigenschaftBtn = new Button();

		// Button f�r den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		Button weiterBtn = new Button("Weiter");
		weiterBtn.addClickHandler(new kontaktErstellenClickHandler());

		BtnPanel.add(weiterBtn);
		BtnPanel.add(quitBtn);

		this.add(headerPanel);

		this.add(new Label("Name des Kontakts:"));

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		this.add(gmailTb);
		this.add(vornameTxtBox);
		this.add(nachnameTxtBox);

		sp.add(kontaktFlex);
		this.add(sp);

		this.setSpacing(20);
		BtnPanel.setSpacing(20);

		this.add(BtnPanel);

	}

	/**
	 * ClickHandler zum Speichern der Kontakteigenschaften
	 */

	private class EigenschaftenSpeichern implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			for (int i = 0; i < kontaktFlex.getRowCount(); i++) {

				Eigenschaft e = new Eigenschaft();
				Widget w = kontaktFlex.getWidget(i, 0);
				if (w instanceof TextBox) {
					if (!((TextBox) w).getValue().isEmpty()) {
						e.setBezeichnung(((TextBox) w).getValue());
					}
					;
				}

				Auspraegung a = new Auspraegung();
				Widget v = kontaktFlex.getWidget(i, 1);
				if (v instanceof TextBox) {
					if (!((TextBox) v).getValue().isEmpty()) {
						a.setWert(((TextBox) v).getValue());
					}
					;

				}

				ev.createAuspraegungForNewEigenschaft(e.getBezeichnung(), a.getWert(), k, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("passt");

					}
				});

			}

		}

	}

	/**
	 * ClickHandler zum Generieren von weiteren Auswahleigenschaften.
	 */

	private class auswahleigenschaftClickHandler implements ClickHandler {

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

			int count = kontaktFlex.getRowCount();
			kontaktFlex.setWidget(count + 1, 0, eigenschaftBox);
			kontaktFlex.setWidget(count + 1, 1, wertBox);

		}

	}

	/**
	 * ClickHandler zum Erzeugen von neuen Eigenschafts-Angaben
	 */
	private class eigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			final DialogBox db = new DialogBox();
			Label bezlb = new Label("Bezeichnung:");
			Label wertlbl = new Label("Wert:");
			Button ok = new Button("OK");
			TextBox bez = new TextBox();
			TextBox wert = new TextBox();
			FlexTable ft = new FlexTable();
			ft.setWidget(0, 0, bezlb);
			ft.setWidget(0, 1, wertlbl);
			ft.setWidget(1, 0, bez);
			ft.setWidget(1, 1, wert);
			ft.setWidget(2, 2, ok);
			db.add(ft);

			db.center();
			db.setAnimationEnabled(true);
			db.setAutoHideEnabled(true);
			db.show();

			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (bez.getValue().isEmpty() || wert.getValue().isEmpty()) {
						Window.alert("Bitte beide Felder ausfüllen");
					} else {
						db.hide();
						Eigenschaft eig = new Eigenschaft();
						Auspraegung aus = new Auspraegung();

						eig.setBezeichnung(bez.getValue());
						aus.setWert(wert.getValue());

						kontaktFlex.setWidget(kontaktFlex.getRowCount(), 0, bez);
						kontaktFlex.setWidget(kontaktFlex.getRowCount() - 1, 1, wert);

					}
				}
			});

			RootPanel.get("content").add(kontaktFlex);

		}

	}

	/**
	 * ClickHandler zum Erstellen eines neu angelegten Kontakts
	 */
	public class kontaktErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Cookies des Nutzers holen.
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (vornameTxtBox.getText().isEmpty() && nachnameTxtBox.getText().isEmpty()) {
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
								HorizontalPanel headerPanel = new HorizontalPanel();
								headerPanel.add(new HTML("<h2>Kontakteigenschaften angeben</h2>"));

								HorizontalPanel BtnPanel = new HorizontalPanel();

								Button addEigenschaftBtn = new Button("Weitere Auswahleigenschaften");
								Button createEigenschaftBtn = new Button("Eigenschaft erstellen");

								addEigenschaftBtn.addClickHandler(new auswahleigenschaftClickHandler());
								createEigenschaftBtn.addClickHandler(new eigenschaftClickHandler());

								BtnPanel.add(addEigenschaftBtn);
								BtnPanel.add(createEigenschaftBtn);

								Button speichernBtn = new Button("speichern");
								speichernBtn.addClickHandler(new EigenschaftenSpeichern());
								BtnPanel.add(speichernBtn);

								ListBox eigenschaftBox = new ListBox();
								eigenschaftBox.getElement().setPropertyString("placeholder", "Auswahl");
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

								kontaktFlex.setWidget(0, 0, eigenschaftBox);
								kontaktFlex.setWidget(0, 1, wertBox);

								kontaktFlex.setWidget(1, 0, eigenschaftBox);
								kontaktFlex.setWidget(1, 1, wertBox);

								kontaktFlex.setWidget(2, 0, eigenschaftBox);
								kontaktFlex.setWidget(2, 1, wertBox);

								sp.add(kontaktFlex);
								RootPanel.get("content").add(sp);
								RootPanel.get("content").add(headerPanel);
								RootPanel.get("content").add(BtnPanel);

							}

						});

			}

		}
	}

}
