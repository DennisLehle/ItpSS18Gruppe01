package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.dom.client.Style.Display;
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
 * Klasse welche Formulare f�r Kontaktd darstellt, diese erlauben
 * Interaktionsm�glichkeiten um Kontakte Anzuzeigen, zu Bearbeiten, zu
 * L�schen oder Neuanzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings cs = new ClientsideSettings();

	Kontakt k = new Kontakt();
	Auspraegung a = new Auspraegung();
	Auspraegung a2 = new Auspraegung();
	Eigenschaft e = new Eigenschaft();

	Kontakt selectedKontakt = null;
	Vector<Eigenschaft> eigenschaften = new Vector<>();
	ListBox auswahlEigenschaftenListBox1 = new ListBox();
	ListBox auswahlEigenschaftenListBox2 = new ListBox();
	ListBox auswahlEigenschaftenListBox3 = new ListBox();

	SontactTreeViewModel sontactTree = null;

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox auspraegungTxtBx1 = new TextBox();
	TextBox auspraegungTxtBx2 = new TextBox();
	TextBox auspraegungTxtBx3 = new TextBox();

	Label erstellungsdatum = new Label();
	Label modifikationsdatum = new Label();

	FlexTable KontaktProfilFelx = new FlexTable();

	// Flextables welche f�r das Anlegen eines neuen Kontakts ben�tigt werden
	FlexTable kontaktInfoTable = new FlexTable();
	FlexTable eigeneEigenschaftenTable = new FlexTable();

	// Panels fuer die Anordnung der zwei FlexTables
	HorizontalPanel FlexTablePanel = new HorizontalPanel();
	VerticalPanel flexPanelNeueEig = new VerticalPanel();
	VerticalPanel flexPanelKontaktInfo = new VerticalPanel();
	
	VerticalPanel vp = new VerticalPanel();
	
	HorizontalPanel btnPanelTop = new HorizontalPanel();
	HorizontalPanel btnPanelBottom = new HorizontalPanel();
	ScrollPanel sp = new ScrollPanel();

	// Labels
	Label vorname = new Label("Vorname:");
	Label nachname = new Label("Nachname:");

	public KontaktForm() {
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt bereits vorhanden ist.
	 * 
	 * @param Kontakt
	 */
	public KontaktForm(Kontakt k) {
		RootPanel.get("contentHeader").clear();
		this.selectedKontakt = k;

		// Nutzer Cookies holen.
		Nutzer n = new Nutzer();
		n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		n.setEmailAddress(Cookies.getCookie("nutzerGMail"));

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
				headerPanel.setBorderWidth(10);
				headerPanel.setWidth("70%");
				HorizontalPanel BtnPanel = new HorizontalPanel();
				BtnPanel.setBorderWidth(10);
				BtnPanel.getElement().getStyle().setDisplay(Display.BLOCK);
				VerticalPanel vp = new VerticalPanel();
				Label ownerLb = new Label();

				headerPanel.add(new HTML("<h2>Kontakt: <em>" + selectedKontakt.getVorname() + " "
						+ selectedKontakt.getNachname() + "</em></h2>"));

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button(
						"<image src='/images/user.png' width='20px' height='20px' align='center' /> bearbeiten");

				// ClickHandler f�r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());

				BtnPanel.add(editKontaktBtn);

				// ClickHandler zum teilen von Kontakten
				Button shareBtn = new Button(
						"<image src='/images/share.png' width='30px' height='30px' align='center' /> teilen");

				shareBtn.addClickHandler(new shareKontaktlisteClickHandler());
				BtnPanel.add(shareBtn);


				// Abfrage wer der Owner des Kontaktes ist.
				if (k.getOwnerId() != n.getId()) {
					ev.findNutzerById(k.getOwnerId(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Nutzer result) {
							ownerLb.setText("Eigentümer: " + result.getEmailAddress());

						}

					});
				}

				// Panel fuer das Erstellungs- und Modifikationsdatum
				VerticalPanel datePanel = new VerticalPanel();
				datePanel.setSpacing(20);

				DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
				erstellungsdatum.setText("Erstellungsdatum : " + dateFormat.format(selectedKontakt.getErstellDat()));
				modifikationsdatum.setText("Modifikationsdatum : " + dateFormat.format(selectedKontakt.getModDat()));

				datePanel.add(erstellungsdatum);
				datePanel.add(modifikationsdatum);

				
		
				//Überprüft Status eines Objekts ob es geteilt wurde.
				ev.getStatusForObject(k.getId(),k.getType(), new AsyncCallback<Boolean>() {


					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == true) {
							HTML shared = new HTML(
									"<image src='/images/share.png' width='15px' height='15px' align='center' />");
							headerPanel.add(shared);
						}
					}

				});

				// Überprüfung ob Kontakt den Nutzer repräsentiert für Löschung aus dem
				// System.
				if (k.getOwnerId() == n.getId() && k.getIdentifier() == 'r') {

					// Button für die Löschung erstellen und ClickHandler zuweisen.
					Button deleteNutzer = new Button(
							"<image src='/images/trash.png' width='15px' height='15px' align='center' />");
					deleteNutzer.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// Abfrage ob Nutzer sich wirklich löschen will.
							Window.confirm("Wollen Sie sich wirklich unwiderruflich von uns verabschieden?");

							ev.deleteNutzer(n, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									caught.getMessage().toString();

								}

								@Override
								public void onSuccess(Void result) {
									// Nutzer wurde gelöscht und wird auf die Startseite verwiesen.
									Window.Location.replace("Sontact.html");

								}

							});

						}
					});
					deleteNutzer.setStyleName("deleteNutzer");
					RootPanel.get("content").add(deleteNutzer);

				}

				vp.add(headerPanel);
				vp.add(ownerLb);

				RootPanel.get("content").add(vp);
				RootPanel.get("content").add(new ShowEigenschaften(n, selectedKontakt));
				RootPanel.get("content").add(BtnPanel);

				RootPanel.get("content").add(datePanel);

			}
		});
	}

	/**
	 * Konstruktor der zum Einsatz kommt, wenn ein Kontakt neu erstellt wird
	 */
	public KontaktForm(final Nutzer n) {
		RootPanel.get("contentHeader").clear();
		RootPanel.get("contentHeader").add(new HTML("Kontakt anlegen"));

		btnPanelBottom.setSpacing(15);
		btnPanelTop.setSpacing(15);
		
		FlexTablePanel.setSpacing(25);
		kontaktInfoTable.setCellPadding(20);
		eigeneEigenschaftenTable.setCellPadding(20);
		
		flexPanelKontaktInfo.add(kontaktInfoTable);
		flexPanelNeueEig.add(eigeneEigenschaftenTable);
		FlexTablePanel.add(flexPanelKontaktInfo);
		FlexTablePanel.add(flexPanelNeueEig);
		sp.add(FlexTablePanel);

		// Groesse des ScrollPanels anpassen
		sp.setSize("900px", "400px");
		

		// Wir holen �ber einen Server-Request die Eigenschaften aus der DB um diese
		// bereit zu halten
		ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getMessage();

			}

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {
				eigenschaften = result;
				/*
				 * Es wird eine ListBox mit den Eigenschaften die zur Auswahl stehen befuellt
				 * Diese wird verwendet um von vorne rein drei feste ListBoxen zur Auswahl
				 * bereitzustellen
				 */
				for (int i = 0; i < eigenschaften.size(); i++) {
					auswahlEigenschaftenListBox1.addItem(eigenschaften.elementAt(i).getBezeichnung());
					auswahlEigenschaftenListBox2.addItem(eigenschaften.elementAt(i).getBezeichnung());
					auswahlEigenschaftenListBox3.addItem(eigenschaften.elementAt(i).getBezeichnung());
				}

			}
		});

		Button addEigenschaftBtn = new Button("Weitere Auswahleigenschaft");
		addEigenschaftBtn.addClickHandler(new AuswahlEigenschaftenClickHandler());

		Button createEigenschaftBtn = new Button("Eigene Eigenschaft definieren");
		createEigenschaftBtn.addClickHandler(new EigeneEigenschaftClickHandler());

		btnPanelBottom.add(addEigenschaftBtn);
		btnPanelBottom.add(createEigenschaftBtn);

	

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("Abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Methode zum Refresh der aktuellen Anzeige im Browser aufrufen
				Window.Location.reload();

			}
		});

		btnPanelTop.add(quitBtn);

		Button saveBtn = new Button("erstellen");
		saveBtn.addClickHandler(new speichernKontaktClickHandler());

		btnPanelTop.add(saveBtn);

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");

		kontaktInfoTable.setWidget(0, 0, vorname);
		kontaktInfoTable.setWidget(0, 1, vornameTxtBox);
		kontaktInfoTable.setWidget(1, 0, nachname);
		kontaktInfoTable.setWidget(1, 1, nachnameTxtBox);
		kontaktInfoTable.setWidget(2, 0, auswahlEigenschaftenListBox1);
		kontaktInfoTable.setWidget(2, 1, auspraegungTxtBx1);
		kontaktInfoTable.setWidget(3, 0, auswahlEigenschaftenListBox2);
		kontaktInfoTable.setWidget(3, 1, auspraegungTxtBx2);
		kontaktInfoTable.setWidget(4, 0, auswahlEigenschaftenListBox3);
		kontaktInfoTable.setWidget(4, 1, auspraegungTxtBx3);
		kontaktInfoTable.setCellPadding(35);
		
		vp.add(btnPanelTop);
		vp.add(sp);
		vp.add(btnPanelBottom);
		vp.setSpacing(20);
		
		this.add(vp);

	}

	/**
	 * ClickHandler zum Erzeugen von weiteren Auswahleigenschaften (ListBoxen)
	 */

	private class AuswahlEigenschaftenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			ListBox eigenschaftBox = new ListBox();
			TextBox wertBox = new TextBox();
			wertBox.getElement().setPropertyString("placeholder", "Wert eingeben..");

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

			int count = kontaktInfoTable.getRowCount();
			kontaktInfoTable.setWidget(count, 0, eigenschaftBox);
			kontaktInfoTable.setWidget(count, 1, wertBox);
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
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class speichernKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();
			// Cookies des Nutzers holen.
			n.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			n.setEmailAddress(Cookies.getCookie("nutzerGMail"));
			
			// Anlegen des Kontakts mit den Mindestangaben Vor- und Nachname
			ev.createKontakt(vornameTxtBox.getText(), nachnameTxtBox.getText(), n, new AsyncCallback<Kontakt>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage();

				}

				@Override
				public void onSuccess(Kontakt result) {
					k = result;
					/*
					 * Nun speichern wir die Auswahleigenschaften und die vom Nutzer angegebenen Auspraegungen
					 */
					for (int i = 2; i < kontaktInfoTable.getRowCount(); i++) {
						Widget w = kontaktInfoTable.getWidget(i, 0);
						if (w instanceof ListBox) {
							if (!((ListBox) w).getSelectedItemText().isEmpty()) {
								String bez = ((ListBox) w).getSelectedItemText();

								Widget v = kontaktInfoTable.getWidget(i, 1);
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
					

							}
						});

					}

				}
			});


			
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

							ev.createAuspraegungForNewEigenschaft(bez, a2.getWert(), k,
									new AsyncCallback<Void>() {

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

		}
	}

	/**
	 * ClickHandler zum teilen von Kontakten.
	 * 
	 * @author Dennis Lehle
	 *
	 */
	private class shareKontaktlisteClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			Kontakt k = selectedKontakt;

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
			HorizontalPanel InfoPanel = new HorizontalPanel();
			headerPanel.add(new HTML("<h2>Kontakt:  <em>" + selectedKontakt.getVorname() + " "
					+ selectedKontakt.getNachname() + "</em> bearbeiten</h2>"));
			InfoPanel.add(new HTML("<h4>Kontaktinformationen:</h4>"));

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

							// RootPanel.get("content").add(new KontaktForm(selectedKontakt));
							// Window.Location.reload();

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
			vp.add(InfoPanel);
			hpVorname.add(new Label("Vorname: "));
			hpVorname.add(vornameTxtBox);

			hpNachname.add(new Label("Nachname: "));
			hpNachname.add(nachnameTxtBox);

			ev.getAllAuspraegungenByKontakt(selectedKontakt.getId(), new AsyncCallback<Vector<Auspraegung>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.getMessage().toString();

				}

				@Override
				public void onSuccess(Vector<Auspraegung> result) {
					Vector<Auspraegung> auspraegungen = new Vector<>();
					auspraegungen = result;

					for (int i = 0; i < auspraegungen.size(); i++) {

						TextBox auspraegung = new TextBox();
						auspraegung.setText(auspraegungen.elementAt(i).getWert());

						ev.getEigenschaftForAuspraegung(auspraegungen.elementAt(i).getEigenschaftId(),
								new AsyncCallback<Eigenschaft>() {

									@Override
									public void onFailure(Throwable caught) {
										caught.getMessage().toString();

									}

									@Override
									public void onSuccess(Eigenschaft result) {

										TextBox eigenschafttb = new TextBox();
										eigenschafttb.setText(result.getBezeichnung());
										int count = KontaktProfilFelx.getRowCount();
										KontaktProfilFelx.setWidget(count, 0, eigenschafttb);
										KontaktProfilFelx.setWidget(count, 1, auspraegung);
										int count2 = KontaktProfilFelx.getRowCount();
										count = count2 + 1;

									}
								});

					}

				}

			});

			vpName.add(hpVorname);
			vpName.add(hpNachname);

			vp.add(KontaktProfilFelx);
			vp.add(BtnPanel);

			RootPanel.get("content").add(vpName);
			RootPanel.get("content").add(vpName);
			RootPanel.get("content").add(vp);

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
