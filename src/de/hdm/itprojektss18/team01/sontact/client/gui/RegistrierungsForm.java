package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
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

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Die Klasse RegistrierungsFormular wird beim Login aufgerufen, wenn der Nutzer
 * noch keinen Kontakt in der Datenbank gespeichert hat, und sich somit zum Ersten Mal in der 
 * Applikation anmeldet.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class RegistrierungsForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	// Erstellung der TextBoxen für die Eigenschaften
	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox gmailTb = new TextBox();
	DateBox geburtsdatum = new DateBox();
	TextBox plz = new TextBox();
	TextBox wohnort = new TextBox();
	TextBox emailadresse = new TextBox();
	TextBox anschrift = new TextBox();

	// Erstellung der Labels für die Eigenschaften
	Label gmail = new Label("Google-Mail");
	Label vorname = new Label("Vorname");
	Label nachname = new Label("Nachname");
	Label geburtsdatumlb = new Label("Geburtsdatum");
	Label plzlb = new Label("Postleitzahl");
	Label wohnortlb = new Label("Ort");
	Label emailadresselb = new Label("Email");
	Label anschriftlb = new Label("Anschrift");

	Label auswahleigenschaften = new Label("Auswahleigenschaft");
	Label freitexteigenschaften = new Label("Freitexteigenschaft");

	// Erstellung der Speichern-Buttons
	Button speichern = new Button(
			"<image src='/images/user.png' width='20px' height='20px' align='center' /> Jetzt registrieren");
	
	// Erstellung der HorizontalPanels
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();

	// Hauptpanel fuer die Ansicht der Kontakterstellung
	VerticalPanel hauptPanel = new VerticalPanel();
	// Hauptpanel fuer die Ansicht der Kontakteigenschaftsangaben
	VerticalPanel hauptPanel2 = new VerticalPanel();

	// Erstellung der FlexTables fuer die Tabellenstruktur
	FlexTable kontaktTable = new FlexTable();
	FlexTable infoTable = new FlexTable();

	/**
	 * Konstruktor fuer die RegistrierungsForm-Klasse.
	 */
	public RegistrierungsForm(Nutzer n) {
		
		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		
		// Hinzufuegen der Ueberschrift in den ContentHeader
		RootPanel.get("contentHeader").add(new HTML("Registrierung"));
		
		// Speichern-Button wird dem VerticalPanel hinzugefuegt
		this.add(speichern);

		onLoad(n);
	}

	protected void onLoad(Nutzer n) {

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		geburtsdatum.setFormat(new DateBox.DefaultFormat(dateFormat));
		
		// Hier wird der Speichern-Button gestylt
		speichern.setStylePrimaryName("regButton");

		gmailTb.setText(n.getEmailAddress());
		gmailTb.setEnabled(false);
		
		// Hier werden die Labels fuer die Registrierung gestylt 
		gmail.setStylePrimaryName("label");
		vorname.setStylePrimaryName("label");
		nachname.setStylePrimaryName("label");
		geburtsdatumlb.setStylePrimaryName("label");
		plzlb.setStylePrimaryName("label");
		wohnortlb.setStylePrimaryName("label");
		emailadresselb.setStylePrimaryName("label");
		anschriftlb.setStylePrimaryName("label");
		
		// Hier werden die TextBoxen fuer die Registrierung gestylt
		gmailTb.setStylePrimaryName("tbRundung");
		vornameTxtBox.setStylePrimaryName("tbRundung");
		nachnameTxtBox.setStylePrimaryName("tbRundung");
		geburtsdatum.setStylePrimaryName("tbRundung");
		plz.setStylePrimaryName("tbRundung");
		wohnort.setStylePrimaryName("tbRundung");
		emailadresse.setStylePrimaryName("tbRundung");
		anschrift.setStylePrimaryName("tbRundung");
		
		// Anordnung der ersten FlexTable
		kontaktTable.setWidget(1, 0, vorname);
		kontaktTable.setWidget(1, 1, vornameTxtBox);
		kontaktTable.setWidget(2, 0, nachname);
		kontaktTable.setWidget(2, 1, nachnameTxtBox);
		kontaktTable.setWidget(3, 0, gmail);
		kontaktTable.setWidget(3, 1, gmailTb);

		// Anordnung der zweiten FlexTable
		infoTable.setWidget(1, 0, geburtsdatumlb);
		infoTable.setWidget(1, 1, geburtsdatum);
		infoTable.setWidget(2, 0, plzlb);
		infoTable.setWidget(2, 1, plz);
		infoTable.setWidget(3, 0, wohnortlb);
		infoTable.setWidget(3, 1, wohnort);
		infoTable.setWidget(4, 0, emailadresselb);
		infoTable.setWidget(4, 1, emailadresse);
		infoTable.setWidget(5, 0, anschriftlb);
		infoTable.setWidget(5, 1, anschrift);

		// Stylen der FlexTables
		kontaktTable.setStylePrimaryName("infoTable");
		infoTable.setStylePrimaryName("infoTable");

		hauptPanel.add(kontaktTable);
		hauptPanel2.add(infoTable);
		hauptPanel2.add(speichern);
		
		// Hinzufuegen derPlaceholder fuer die TextBoxen 
		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");
		geburtsdatum.getElement().setPropertyString("placeholder", "Geburtsdatum des Kontakts");
		plz.getElement().setPropertyString("placeholder", "Postleitzahl des Kontakts");
		wohnort.getElement().setPropertyString("placeholder", "Wohnort des Kontakts");
		emailadresse.getElement().setPropertyString("placeholder", "Email des Kontakts");
		anschrift.getElement().setPropertyString("placeholder", "Anschrift des Kontakts");

		RootPanel.get("content").add(hauptPanel);
		RootPanel.get("content").add(hauptPanel2);

		// Speichern des zu registrierenden Kontakts und seinen Auspraegungen
		speichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if (vornameTxtBox.getValue() != "" && nachnameTxtBox.getValue() != "" && 
						geburtsdatum.getValue().toString() != "" && plz.getText() != "" && wohnort.getText() != "" &&
						anschrift.getText() != "" && emailadresse.getText() != "") {
					
					// Erstellung des eigenen Kontakts bei der Registrierung
					ev.createKontaktRegistrierung(vornameTxtBox.getValue(), nachnameTxtBox.getValue(), n,
							new AsyncCallback<Kontakt>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();
								}

								@Override
								public void onSuccess(Kontakt result2) {
									
									// Die Eigenschaften werden per Bezeichnung geholt
									ev.getEigenschaftByBezeichnung(geburtsdatumlb.getText(),
											new AsyncCallback<Eigenschaft>() {

												@Override
												public void onFailure(Throwable caught) {
													caught.getMessage().toString();

												}

												@Override
												public void onSuccess(Eigenschaft result) {
													
												Date datum = DateTimeFormat.getFormat("yyyyMMdd").parse(DateTimeFormat.getFormat("yyyyMMdd").format(geburtsdatum.getValue()));
													
												// Erstellung der Auspraegungen fuer den Kontakt
												ev.createAuspraegung(datum.toString(),
															result.getId(), result2.getId(), n.getId(),
															new AsyncCallback<Auspraegung>() {

																	@Override
																	public void onFailure(Throwable caught) {
																		caught.getMessage().toString();

																	}

																	@Override
																	public void onSuccess(Auspraegung result) {
																		
																			// Die Eigenschaft Postleizahl wird gelesen
																			ev.getEigenschaftByBezeichnung(plzlb.getText(),
																					new AsyncCallback<Eigenschaft>() {

																						@Override
																						public void onFailure(Throwable caught) {
																							caught.getMessage().toString();

																						}

																						@Override
																						public void onSuccess(Eigenschaft result) {
																							
																							// Erstellung der Auspraegung (Postleitzahl) fuer den Kontakt
																							ev.createAuspraegung(plz.getText(), result.getId(),
																									result2.getId(), n.getId(), new AsyncCallback<Auspraegung>() {

																										@Override
																										public void onFailure(Throwable caught) {
																											caught.getMessage().toString();

																										}

																										@Override
																										public void onSuccess(Auspraegung result) {
																											
																													// Die Eigenschaft Wohnort wird gelesen
																													ev.getEigenschaftByBezeichnung(wohnortlb.getText(),
																															new AsyncCallback<Eigenschaft>() {

																																@Override
																																public void onFailure(Throwable caught) {
																																	caught.getMessage().toString();

																																}

																																@Override
																																public void onSuccess(Eigenschaft result) {
																																	
																																	// Erstellung der Auspraegung (Wohnort) fuer den Kontakt
																																	ev.createAuspraegung(wohnort.getText(), result.getId(),
																																			result2.getId(), n.getId(), new AsyncCallback<Auspraegung>() {

																																				@Override
																																				public void onFailure(Throwable caught) {
																																					caught.getMessage().toString();

																																				}

																																				@Override
																																				public void onSuccess(Auspraegung result) {
																																						
																																						// Die Eigenschaft Anschrift wird gelesen
																																						ev.getEigenschaftByBezeichnung(anschriftlb.getText(),
																																								new AsyncCallback<Eigenschaft>() {

																																									@Override
																																									public void onFailure(Throwable caught) {
																																										caught.getMessage().toString();

																																									}

																																									@Override
																																									public void onSuccess(Eigenschaft result) {
																																										
																																										// Erstellung der Auspraegung (Anschrift) fuer den Kontakt
																																										ev.createAuspraegung(anschrift.getText(), result.getId(),
																																												result2.getId(), n.getId(), new AsyncCallback<Auspraegung>() {

																																													@Override
																																													public void onFailure(Throwable caught) {
																																														caught.getMessage().toString();

																																													}

																																													@Override
																																													public void onSuccess(Auspraegung result) {
																																														
																																														// Die Eigenschaft Email-Adresse wird gelesen
																																														ev.getEigenschaftByBezeichnung(emailadresselb.getText(), new AsyncCallback <Eigenschaft>() {

																																															@Override
																																															public void onFailure(Throwable caught) {
																																																caught.getMessage().toString();
																																																
																																															}

																																															@Override
																																															public void onSuccess(Eigenschaft result) {
																																																	
																																																// Erstellung der Auspraegung (Email-Adresse) fuer den Kontakt
																																																ev.createAuspraegung(emailadresse.getValue(), result.getId(), result2.getId(), n.getId(), new AsyncCallback<Auspraegung>() {

																																																	@Override
																																																	public void onFailure(Throwable caught) {
																																																		caught.getMessage().toString();
																																																		
																																																	}

																																																	@Override
																																																	public void onSuccess(Auspraegung result) {
																																																			
																																																		
																																																		// ContentHeader und Content divs werden geleert 
																																																		RootPanel.get("contentHeader").clear();
																																																		RootPanel.get("content").clear();
																																																		
																																																		// Fenster neu laden.
																																																		// Weiterleitung in die Start Ansicht des Programms.
																																																		Window.Location.reload();
																																																		
																																																	}
																																																	
																																																});
																																																
																																															}
																																															
																																														});

																																													}
																																												});
																																										
																																									}

																																								});
																																						}
																																				
																																			});
																																	}
																																
																															});
																												}
																										
																									});
																							}
																						
																					});
																		}
																	
																});
														}
													
												});
									
								
								}
							});
					
				}  else {
					MessageBox.alertWidget("Hinweis ", "Bitte füllen Sie die markierten Felder alle aus.");
					if(plz.getText() == "") {
						plz.getElement().getStyle().setBorderColor("red");
					}
					if(wohnort.getText() == "") {
						wohnort.getElement().getStyle().setBorderColor("red");
					}
					if(anschrift.getText() == "") {
						anschrift.getElement().getStyle().setBorderColor("red");
					}
					if(emailadresse.getText() =="") {
						emailadresse.getElement().getStyle().setBorderColor("red");
					}
					if(geburtsdatum.getTextBox().toString() == "") {
						geburtsdatum.getElement().getStyle().setBorderColor("red");
					}
					if(vornameTxtBox.getText() =="") {
						vornameTxtBox.getElement().getStyle().setBorderColor("red");
					}
					if(nachnameTxtBox.getText() == "") {
					nachnameTxtBox.getElement().getStyle().setBorderColor("red");
					}
				}
			
			}
		});

		/// ---------Abschnitt-für-KeyDownHandler-der TextBoxen-----------------///

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