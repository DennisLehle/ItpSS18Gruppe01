package de.hdm.itprojektss18.team01.sontact.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * RegistrierungsFormular Klasse die beim Login aufgerufen wird, wenn der Nutzer
 * noch keinen Kontakt in der Datenbank gespeichert hat.
 * 
 * 
 * @author Dennis Lehle
 */
public class RegistrierungsForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	// TextBoxen
	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();
	DateBox geburtsdatum = new DateBox();
	TextBox plz = new TextBox();
	TextBox wohnort = new TextBox();
	TextBox emailadresse = new TextBox();

	// Labels
	Label gmail = new Label("G-Mail");
	Label vorname = new Label("Vorname");
	Label nachname = new Label("Nachname");
	Label geburtsdatumlb = new Label("Geburtsdatum");
	Label plzlb = new Label("Postleitzahl");
	Label wohnortlb = new Label("Wohnort");
	Label emailadresselb = new Label("Email");

	Label auswahleigenschaften = new Label("Auswahleigenschaft");
	Label freitexteigenschaften = new Label("Freitexteigenschaft");

	Button speichern = new Button(
			"<image src='/images/user.png' width='20px' height='20px' align='center' /> Jetzt registrieren");

	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();

	// Hauptpanel fuer die Ansicht der Kontakterstellung
	VerticalPanel hauptPanel = new VerticalPanel();
	// Hauptpanel fuer die Ansicht der Kontakteigenschaftsangaben
	VerticalPanel hauptPanel2 = new VerticalPanel();

	FlexTable kontaktTable = new FlexTable();
	FlexTable infoTable = new FlexTable();

	public RegistrierungsForm(Nutzer n) {

		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		// Ueberschrift anzeigen
		RootPanel.get("contentHeader").add(new HTML("Kontakt Registrierung"));
		this.add(speichern);

		onLoad(n);

	}

	protected void onLoad(Nutzer n) {

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		geburtsdatum.setFormat(new DateBox.DefaultFormat(dateFormat));

		speichern.setStylePrimaryName("regButton");

		gmailTb.setText(n.getEmailAddress());
		gmailTb.setEnabled(false);

		gmail.setStylePrimaryName("label");
		vorname.setStylePrimaryName("label");
		nachname.setStylePrimaryName("label");
		geburtsdatumlb.setStylePrimaryName("label");
		plzlb.setStylePrimaryName("label");
		wohnortlb.setStylePrimaryName("label");
		emailadresselb.setStylePrimaryName("label");

		gmailTb.setStylePrimaryName("tbRundung");
		vornameTxtBox.setStylePrimaryName("tbRundung");
		nachnameTxtBox.setStylePrimaryName("tbRundung");
		geburtsdatum.setStylePrimaryName("tbRundung");
		plz.setStylePrimaryName("tbRundung");
		wohnort.setStylePrimaryName("tbRundung");
		emailadresse.setStylePrimaryName("tbRundung");

		kontaktTable.setWidget(1, 0, vorname);
		kontaktTable.setWidget(1, 1, vornameTxtBox);
		kontaktTable.setWidget(2, 0, nachname);
		kontaktTable.setWidget(2, 1, nachnameTxtBox);
		kontaktTable.setWidget(3, 0, gmail);
		kontaktTable.setWidget(3, 1, gmailTb);

		infoTable.setWidget(1, 0, geburtsdatumlb);
		infoTable.setWidget(1, 1, geburtsdatum);
		infoTable.setWidget(2, 0, plzlb);
		infoTable.setWidget(2, 1, plz);
		infoTable.setWidget(3, 0, wohnortlb);
		infoTable.setWidget(3, 1, wohnort);
		infoTable.setWidget(4, 0, emailadresselb);
		infoTable.setWidget(4, 1, emailadresse);

		kontaktTable.setStylePrimaryName("infoTable");
		infoTable.setStylePrimaryName("infoTable");

		hauptPanel.add(kontaktTable);
		hauptPanel2.add(infoTable);
		hauptPanel2.add(speichern);

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");
		geburtsdatum.getElement().setPropertyString("placeholder", "Geburtsdatum des Kontakts");
		plz.getElement().setPropertyString("placeholder", "Postleitzahl des Kontakts");
		wohnort.getElement().setPropertyString("placeholder", "Wohnort des Kontakts");
		emailadresse.getElement().setPropertyString("placeholder", "Email des Kontakts");

		RootPanel.get("content").add(hauptPanel);
		RootPanel.get("content").add(hauptPanel2);

		// Speichern des zu registrierenden Kontakts und seinen Ausprägungen
		speichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (vornameTxtBox.getValue() != "" && nachnameTxtBox.getValue() != "") {
					ev.createKontaktRegistrierung(vornameTxtBox.getValue(), nachnameTxtBox.getValue(), n,
							new AsyncCallback<Kontakt>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Kontakt result2) {

									if (geburtsdatumlb.getText() != "") {
										ev.getEigenschaftByBezeichnung(geburtsdatumlb.getText(),
												new AsyncCallback<Eigenschaft>() {

													@Override
													public void onFailure(Throwable caught) {
														caught.getMessage().toString();

													}

													@Override
													public void onSuccess(Eigenschaft result) {
														ev.createAuspraegung(geburtsdatum.getValue().toString(),
																result.getId(), result2.getId(),
																new AsyncCallback<Auspraegung>() {

																	@Override
																	public void onFailure(Throwable caught) {
																		caught.getMessage().toString();

																	}

																	@Override
																	public void onSuccess(Auspraegung result) {
																		Window.Location.reload();
																	}

																});
													}
												});
									}

									if (plzlb.getText() != "") {
										ev.getEigenschaftByBezeichnung(plzlb.getText(),
												new AsyncCallback<Eigenschaft>() {

													@Override
													public void onFailure(Throwable caught) {
														caught.getMessage().toString();

													}

													@Override
													public void onSuccess(Eigenschaft result) {
														ev.createAuspraegung(plz.getText(), result.getId(),
																result2.getId(), new AsyncCallback<Auspraegung>() {

																	@Override
																	public void onFailure(Throwable caught) {
																		caught.getMessage().toString();

																	}

																	@Override
																	public void onSuccess(Auspraegung result) {
																		Window.Location.reload();
																	}

																});
													}
												});
									}

									if (wohnortlb.getText() != "") {
										ev.getEigenschaftByBezeichnung(wohnortlb.getText(),
												new AsyncCallback<Eigenschaft>() {

													@Override
													public void onFailure(Throwable caught) {
														caught.getMessage().toString();

													}

													@Override
													public void onSuccess(Eigenschaft result) {
														ev.createAuspraegung(wohnort.getText(), result.getId(),
																result2.getId(), new AsyncCallback<Auspraegung>() {

																	@Override
																	public void onFailure(Throwable caught) {
																		caught.getMessage().toString();

																	}

																	@Override
																	public void onSuccess(Auspraegung result) {
																		Window.Location.reload();
																	}
																});

													}
												});
									}

									if (emailadresselb.getText() != "") {
										ev.getEigenschaftByBezeichnung(emailadresselb.getText(),
												new AsyncCallback<Eigenschaft>() {

													@Override
													public void onFailure(Throwable caught) {
														caught.getMessage().toString();

													}

													@Override
													public void onSuccess(Eigenschaft result) {
														ev.createAuspraegung(emailadresse.getText(), result.getId(),
																result2.getId(), new AsyncCallback<Auspraegung>() {

																	@Override
																	public void onFailure(Throwable caught) {
																		caught.getMessage().toString();

																	}

																	@Override
																	public void onSuccess(Auspraegung result) {
																		Window.Location.reload();

																	}

																});
													}
												});
									}

								}
							});

				} else {
					MessageBox.alertWidget("Hinweis ", "Bitte füllen Sie die markierten Pflichtfelder aus.");
					vornameTxtBox.getElement().getStyle().setBorderColor("red");
					nachnameTxtBox.getElement().getStyle().setBorderColor("red");

				}

			}

		});

		/// ---------Abschnitt-für-KeyDownHandler-derTextBoxen-----------------///

		vornameTxtBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					speichern.click();

				}
			}

		});

		nachnameTxtBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					speichern.click();

				}
			}

		});

		plz.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					speichern.click();

				}
			}

		});

		wohnort.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					speichern.click();

				}
			}

		});

		emailadresse.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					speichern.click();

				}
			}

		});

	}
}