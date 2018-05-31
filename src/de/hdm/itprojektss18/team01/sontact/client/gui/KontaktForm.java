package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
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

import de.hdm.itprojektss18.team01.sontact.client.ClientsideSettings;
import de.hdm.itprojektss18.team01.sontact.shared.EditorServiceAsync;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Auspraegung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontaktliste;
import de.hdm.itprojektss18.team01.sontact.shared.bo.KontaktlisteKontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;

/**
 * Klasse welche Formulare f�r Kontaktd darstellt, diese erlauben
 * Interaktionsm�glichkeiten um Kontakte Anzuzeigen, zu Bearbeiten, zu L�schen
 * oder Neuanzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontakt selectedKontakt = null;

	SontactTreeViewModel sontactTree = null;

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();

	Label erstellungsdatum = new Label();
	Label modifikationsdatum = new Label();

	FlexTable KontaktProfilFelx = new FlexTable();

	public KontaktForm() {
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt bereits vorhanden ist.
	 * 
	 * @param Kontakt
	 */
	public KontaktForm(Kontakt k) {

		this.selectedKontakt = k;
		
		RootPanel.get("content").clear();

		ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontakt result) {
				selectedKontakt = result;

				HorizontalPanel headerPanel = new HorizontalPanel();
				HorizontalPanel BtnPanel = new HorizontalPanel();
				VerticalPanel vp = new VerticalPanel();

				headerPanel.add(new HTML("<h2>Kontakt: <em>" + selectedKontakt.getVorname() + " "
						+ selectedKontakt.getNachname() + "</em></h2>"));

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button(
						"<image src='/images/user.png' width='20px' height='20px' align='center' /> bearbeiten");

				// ClickHandler f�r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());
				BtnPanel.add(editKontaktBtn);
				
				// Update-Button intanziieren und dem Panel zuweisen
				Button deleteBtn = new Button("<image src='/images/user.png' width='20px' height='20px' align='center' /> löschen");

				// ClickHandler f�r das Updaten eines Kontakts
				deleteBtn.addClickHandler(new deleteKontaktFromKontaktlisteClickHandler());
				BtnPanel.add(deleteBtn);

				// Panel fuer das Erstellungs- und Modifikationsdatum
				VerticalPanel datePanel = new VerticalPanel();

				DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
				erstellungsdatum.setText("Erstellungsdatum : " + dateFormat.format(selectedKontakt.getErstellDat()));
				modifikationsdatum.setText("Modifikationsdatum : " + dateFormat.format(selectedKontakt.getModDat()));

				datePanel.add(erstellungsdatum);
				datePanel.add(modifikationsdatum);

				int id = selectedKontakt.getId();
				ev.getAllAuspraegungenByKontakt(id, new AsyncCallback<Vector<Auspraegung>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Vector<Auspraegung> result) {
						Vector<Auspraegung> auspraegungen = new Vector<>();
						auspraegungen = result;

						for (int i = 0; i < auspraegungen.size(); i++) {

							Label auspraegungLabel = new Label();
							auspraegungLabel.setText(auspraegungen.elementAt(i).getWert());

							ev.getEigenschaftForAuspraegung(auspraegungen.elementAt(i).getEigenschaftId(),
									new AsyncCallback<Eigenschaft>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.getMessage().toString();

										}

										@Override
										public void onSuccess(Eigenschaft result) {

											Label eigenschaftLabel = new Label();
											eigenschaftLabel.setText(result.getBezeichnung());
											int count = KontaktProfilFelx.getRowCount();
											KontaktProfilFelx.setWidget(count, 0, eigenschaftLabel);
											KontaktProfilFelx.setWidget(count, 1, auspraegungLabel);
											int count2 = KontaktProfilFelx.getRowCount();
											count = count2 + 1;

										}
									});

						}

					}

				});

				vp.add(headerPanel);
				vp.add(KontaktProfilFelx);
				vp.add(BtnPanel);
				vp.add(datePanel);
				RootPanel.get("content").add(vp);
			}
		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt neu erstellt wird
	 */
	public KontaktForm(final Nutzer n) {
		// RootPanel leeren damit neuer Content geladen werden kann.
		RootPanel.get("content").clear();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML("<h2>Neuen Kontakt erstellen</h2>"));

		HorizontalPanel BtnPanel = new HorizontalPanel();

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		BtnPanel.add(quitBtn);

		Button saveBtn = new Button("erstellen");
		saveBtn.addClickHandler(new speichernKontaktClickHandler());

		BtnPanel.add(saveBtn);

		VerticalPanel vp = new VerticalPanel();

		vp.add(headerPanel);
		vp.add(new Label("Name des Kontakts:"));
		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		vp.add(vornameTxtBox);
		vp.add(nachnameTxtBox);
		vp.add(BtnPanel);

		vp.setSpacing(20);
		BtnPanel.setSpacing(20);

		RootPanel.get("content").add(vp);

	}

	// ClickHandler
	
	public class deleteKontaktFromKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		
	
			
			// Nutzer Cookies setzen und dann per Nutzer holen.
			Nutzer n = new Nutzer();
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
			Window.confirm("Das löschen hier löscht den Kontakt aus allen Kontaktlisten. Sind Sie sicher?");
			/*
			 * Wenn der KontaktOwner ungleich der Nutzer Id ist kann der Nutzer die
			 * Berechtigung dafür auslesen und entfernen.
			 */
			if (selectedKontakt.getOwnerId() != n.getId()) {
				Window.alert("Teilhaberschaft wird entfernt und Kontakt wird aus ihrer Kontaktliste entfernt..");
				ev.getABerechtigungByReceiver(n, new AsyncCallback<Berechtigung>() {
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Hoppala" + caught.toString());
					}

					@Override
					public void onSuccess(Berechtigung result) {
						Berechtigung b = result;

						b.setReceiverId(n.getId());
						b.setType('k');
						b.setObjectId(selectedKontakt.getId());

						// Berechtigungs-Objekt übergeben.
						ev.deleteBerechtigung(b, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();
							}

							@Override
							public void onSuccess(Void result) {

								/*
								 * Kontaktliste anhand der KontaktlisteId des selektierten Kontaktes wird
								 * herausgesucht.
								 */
								ev.findKontaktlisteByTitel(n, "Alle Kontakte", new AsyncCallback<Kontaktliste>() {

									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage().toString();

									}

									@Override
									public void onSuccess(Kontaktliste result) {

										// Kontakt wird aus der Kontaktliste entfernt.
										ev.removeKontaktFromKontaktliste(result, selectedKontakt, new AsyncCallback<Void>() {

											@Override
											public void onFailure(Throwable caught) {
												caught.getMessage().toString();

											}

											@Override
											public void onSuccess(Void result) {
												/*
												 * Ab hier wurde die Berechtigung entfernt und der gewählte Kontakt
												 * aus der Kontaktliste entfernt.
												 */
												Window.Location.reload();

											}

										});

									}

								});

							}

						});
					}
				});
			} else {
				/*
				 * Wenn Nutzer Id == OwnerId des Kontaktes entspricht darf man den Kontakt
				 * permanent entfernen. Und der Kontakt wird auch bei allen Nutzern aus den
				 * Kontaktlisten entfernt.
				 */
				ev.getKontaktlisteById(selectedKontakt.getKontaktlisteId(), new AsyncCallback<Kontaktliste>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();
						
					}

					@Override
					public void onSuccess(Kontaktliste result) {
						
						//Kontak
						ev.removeKontaktFromKontaktliste(result, selectedKontakt, new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable caught) {
								caught.getMessage().toString();
							}

							@Override
							public void onSuccess(Void result) {
								Window.alert("EHy");
								Window.Location.reload();
								
							}
						});
					}
					
				});
				
			}	
		}		
	}
	
	
	
	
	/**
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class speichernKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();

			// Cookies des Nutzers holen.

			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (vornameTxtBox.getText() == "" || nachnameTxtBox.getText() == "") {
				MessageBox.alertWidget("Bitte geben Sie alle Felder an",
						"Vorname: " + vornameTxtBox.getText() + "Nachname: " + nachnameTxtBox.getText());
			} else {

				ev.createKontakt(vornameTxtBox.getText(), nachnameTxtBox.getText(), n, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Void result) {
						RootPanel.get("content").add(new KontaktForm());

						// Refresh der Seite für die Aktualisierug des Baumes.
						Window.Location.reload();

					}

				});
			}
		}

	}

	/**
	 * ClickHandler f�r das Updaten eines Kontakts
	 * 
	 * @author Batista
	 *
	 */
	private class updateKontaktClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("content").clear();

			HorizontalPanel BtnPanel = new HorizontalPanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			headerPanel.add(new HTML("<h2>Kontakt:  <em>" + selectedKontakt.getVorname() + " "
					+ selectedKontakt.getNachname() + "</em> bearbeiten</h2>"));

			Button cancelBtn = new Button("abbrechen");

			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new KontaktForm(selectedKontakt));
				}
			});

			BtnPanel.add(cancelBtn);

			// Instanziierung Button zum Speichern der �nderungen an des selektierten
			// Kontakts
			Button saveBtn = new Button("speichern");
			// ClickHandler f�r das Speichern
			saveBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					selectedKontakt.setVorname(vornameTxtBox.getText());
					selectedKontakt.setNachname(nachnameTxtBox.getText());

					ev.saveKontakt(selectedKontakt, new AsyncCallback<Kontakt>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Kontakt result) {
							RootPanel.get("content").add(new KontaktForm(selectedKontakt));
							Window.Location.reload();

						}

					});

				}
			});

			BtnPanel.add(saveBtn);

			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hpVorname = new HorizontalPanel();
			HorizontalPanel hpNachname = new HorizontalPanel();
			VerticalPanel vpName = new VerticalPanel();

			vp.add(headerPanel);
			hpVorname.add(new Label("Vorname: "));
			hpVorname.add(vornameTxtBox);

			hpNachname.add(new Label("Nachname: "));
			hpNachname.add(nachnameTxtBox);

			vpName.add(hpVorname);
			vpName.add(hpNachname);
			vpName.add(BtnPanel);
			RootPanel.get("content").add(vp);

			RootPanel.get("content").add(vpName);
			RootPanel.get("content").add(vpName);
			selectedKontakt.setVorname(vornameTxtBox.getText());
			selectedKontakt.setNachname(nachnameTxtBox.getText());

			BtnPanel.setSpacing(20);
			vp.setSpacing(20);

		}

	}

	public void setSelectedKontakt(Kontakt k) {
		if (k != null) {
			selectedKontakt = k;
			vornameTxtBox.setText(selectedKontakt.getVorname());
			nachnameTxtBox.setText(selectedKontakt.getNachname());

		} else {

			vornameTxtBox.setText("");
			nachnameTxtBox.setText("");

		}

	}

	public void setSontactTreeViewModel(SontactTreeViewModel sontactTreeViewModel) {
		sontactTree = sontactTreeViewModel;
	}
}
