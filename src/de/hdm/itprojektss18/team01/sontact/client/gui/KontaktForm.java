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

				// L�sch-Button instanziieren und dem Panel zuweisen
				Button deleteKontaktBtn = new Button("Kontakt löschen");
				BtnPanel.add(deleteKontaktBtn);

				// ClickHandler f�r das L�schen eines Kontakts
				deleteKontaktBtn.addClickHandler(new deleteClickHandler());
				BtnPanel.add(deleteKontaktBtn);

				// L�sch-Button instanziieren und dem Panel zuweisen
				Button deleteKontaktFromKlBtn = new Button("Aus der Kontaktliste löschen");
				BtnPanel.add(deleteKontaktFromKlBtn);

				// ClickHandler f�r das L�schen eines Kontakts
				deleteKontaktFromKlBtn.addClickHandler(new deleteFromKontaktlisteClickHandler());
				BtnPanel.add(deleteKontaktFromKlBtn);

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button("Kontakt bearbeiten");

				// ClickHandler f�r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());
				BtnPanel.add(editKontaktBtn);

				// Panel fuer das Erstellungs- und Modifikationsdatum
				VerticalPanel datePanel = new VerticalPanel();

				DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
				erstellungsdatum.setText("Erstellungsdatum : " + dateFormat.format(selectedKontakt.getErstellDat()));
				modifikationsdatum.setText("Modifikationsdatum : " + dateFormat.format(selectedKontakt.getModDat()));

				datePanel.add(erstellungsdatum);
				datePanel.add(modifikationsdatum);

				FlexTable auspraegungFlex = new FlexTable();

				ev.getAllAuspraegungenByKontakt(selectedKontakt.getId(), new AsyncCallback<Vector<Auspraegung>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage();

					}

					@Override
					public void onSuccess(Vector<Auspraegung> result) {

						TextBox eTextBox = new TextBox();
						TextBox aTextBox = new TextBox();
						Vector<Auspraegung> av = new Vector<>();
						av = result;

						int count = auspraegungFlex.getRowCount();

						for (int i = 0; i < av.size(); i++) {
							if (av != null) {
								ev.getEigenschaftForAuspraegung(av.elementAt(i).getEigenschaftId(),
										new AsyncCallback<String>() {

											@Override
											public void onFailure(Throwable arg0) {
												// TODO Auto-generated method stub

											}

											@Override
											public void onSuccess(String result) {
												eTextBox.setText(result);
												auspraegungFlex.setWidget(count + 1, 0, eTextBox);

											}
										});
								aTextBox.setText(av.elementAt(i).getWert());
								auspraegungFlex.setWidget(count + 1, 1, aTextBox);

							}

						}
					}
				});

				vp.add(headerPanel);
				vp.add(auspraegungFlex);
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

	/**
	 * ClickHandler f�r das L�schen eines Kontakts
	 * 
	 * @author Batista
	 *
	 */
	private class deleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Check, ob Kontakte in der Liste enthalten sind
			ev.getKontaktById(selectedKontakt.getId(), new AsyncCallback<Kontakt>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();
				}

				@Override
				public void onSuccess(Kontakt result) {
					// Wenn Kontakte vorhanden sind...
					Window.confirm("Kontakt: " + selectedKontakt.getVorname() + selectedKontakt.getNachname()
							+ "unwiderruflich löschen?");
					{
						loescheKontakt();
					}
				}

				public void loescheKontakt() {
					ev.removeKontakt(selectedKontakt, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable error) {
							error.getMessage().toString();

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Kontakt wurde gelöscht");
							Window.Location.reload();

						}

					});

				}
			});

		}

	}

	// Zum löschen eines Kontaktes aus einer speziellen Kontaktliste
	private class deleteFromKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Check, ob Kontakte in der Liste enthalten sind
			ev.getKontaktById(selectedKontakt.getId(), new AsyncCallback<Kontakt>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();
				}

				@Override
				public void onSuccess(Kontakt result) {
					// Wenn Kontakte vorhanden sind...
					Window.confirm("Kontakt: " + selectedKontakt.getVorname() + selectedKontakt.getNachname()
							+ "unwiderruflich aus der Kontaktliste löschen?");
					{
						loescheKontaktAusKontaktliste();
					}
				}

				public void loescheKontaktAusKontaktliste() {
			
					Kontaktliste kl = sontactTree.getSelectedKontaktliste();
					
					ev.removeKontaktFromKontaktliste(kl, selectedKontakt, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable error) {
									error.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {
									Window.alert("hey");
									Window.alert("Kontakt wurde aus der Kontaktliste gelöscht");
									Window.Location.reload();
								}
							
				});
			}
		});
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
