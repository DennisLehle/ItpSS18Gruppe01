package de.hdm.itprojektss18.team01.sontact.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
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
import de.hdm.itprojektss18.team01.sontact.shared.bo.Berechtigung;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Eigenschaft;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Kontakt;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Nutzer;
import de.hdm.itprojektss18.team01.sontact.shared.bo.Relatable;

/**
 * Klasse welche Formulare f�r Kontaktd darstellt, diese erlauben
 * Interaktionsm�glichkeiten um Kontakte Anzuzeigen, zu Bearbeiten, zu L�schen
 * oder Neuanzulegen.
 * 
 * @author Kevin Batista, Dennis Lehle, Ugur Bayrak
 */

public class KontaktForm extends VerticalPanel {

	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();


	Kontakt k = new Kontakt();
	Auspraegung a = new Auspraegung();
	Auspraegung a2 = new Auspraegung();
	Eigenschaft e = new Eigenschaft();
	Eigenschaft e2 = new Eigenschaft();
	Auspraegung updatedAuspraegung = new Auspraegung();
	Eigenschaft updatedEigenschaft = new Eigenschaft();
	Nutzer nutzer = new Nutzer();

	Kontakt selectedKontakt = null;
	Vector<Eigenschaft> kontakteigenschaften = new Vector<>();
	Vector<Relatable> kontaktauspraegungen = new Vector<>();

	// Freie Eigenschaft
	Vector<String> eig = new Vector<String>();
	// Auswahl Eigenschaft
	Vector<String> auswahl = new Vector<String>();

	// Vector für freie Eigenschaft
	Vector<String> aus = new Vector<String>();
	// Vector für auswahl Eigenschaft
	Vector<String> aus1 = new Vector<String>();
	Vector<Eigenschaft> auswahlE = new Vector<Eigenschaft>();
	Vector<Berechtigung> receiver = new Vector<Berechtigung>();
	ListBox auswahlEigenschaftenListBox1 = new ListBox();

	SontactTreeViewModel sontactTree = null;

	TextBox vornameTxtBox = new TextBox();
	TextBox nachnameTxtBox = new TextBox();
	TextBox auspraegungTxtBx1 = new TextBox();

	Label erstellungsdatum = new Label();
	Label modifikationsdatum = new Label();
	Label infoLb = new Label();

	// Flextables welche f�r das Anlegen eines neuen Kontakts ben�tigt werden
	FlexTable kontaktInfoTable = new FlexTable();
	FlexTable eigeneEigenschaftenTable = new FlexTable();
	FlexTable auswahlEigenschaftenTable = new FlexTable();

	// Flextables welche fuer das Bearbeiten eines Kontakts benoetigt werden
	FlexTable eigeneEigenschaftBearbeitungTable = new FlexTable();
	FlexTable vorhandeneAuspraegungenTable = new FlexTable();

	// Panels fuer die Anordnung der zwei FlexTables
	HorizontalPanel FlexTablePanel = new HorizontalPanel();
	VerticalPanel flexPanelNeueEig = new VerticalPanel();
	VerticalPanel flexPanelKontaktInfo = new VerticalPanel();

	VerticalPanel vp = new VerticalPanel();

	HorizontalPanel btnPanelTop = new HorizontalPanel();
	HorizontalPanel kontaktinfo = new HorizontalPanel();
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
		eigeneEigenschaftenTable.setStylePrimaryName("eigeneEigTable");
		eigeneEigenschaftBearbeitungTable.setStylePrimaryName("eigeneEigTable");
		this.selectedKontakt = k;
		
		vorname.setStylePrimaryName("label");
		nachname.setStylePrimaryName("label");

		// Nutzer Cookies holen.
		
		nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
		nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

		

		ev.getKontaktById(k.getId(), new AsyncCallback<Kontakt>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Hopala");
			}

			@Override
			public void onSuccess(Kontakt result) {

				selectedKontakt = result;

				HorizontalPanel headerPanel = new HorizontalPanel();

				HorizontalPanel btnPanel = new HorizontalPanel();
				VerticalPanel vp = new VerticalPanel();
				Label ownerLb = new Label();

				// Update-Button intanziieren und dem Panel zuweisen
				Button editKontaktBtn = new Button(
						"<image src='/images/user.png' width='20px' height='20px' align='center' /> bearbeiten");
				editKontaktBtn.setStylePrimaryName("bearbeitenButton");
				editKontaktBtn.setTitle("Kontakt Name oder Eigenschaften bearbeiten");

				// ClickHandler f�r das Updaten eines Kontakts
				editKontaktBtn.addClickHandler(new updateKontaktClickHandler());
				btnPanel.add(editKontaktBtn);

				// ClickHandler zum teilen von Kontakten
				Button sharedeleteBtn = new Button(
						"<image src='/images/share.png' width='20px' height='20px' align='center' /> löschen");
				sharedeleteBtn.setStylePrimaryName("teilunsDeleteButton");
				sharedeleteBtn.setTitle("Löschen von Teilhaberschaften an einen Kontakt");

				sharedeleteBtn.addClickHandler(new shareKontaktlisteClickHandler());
				btnPanel.add(sharedeleteBtn);

				infoLb.setText("Kontakt Interaktion");
				infoLb.setStylePrimaryName("infoLabel");
				btnPanel.add(infoLb);

				// Abfrage wer der Owner des Kontaktes ist.
				if (k.getOwnerId() != nutzer.getId()) {
					ev.getNutzerById(k.getOwnerId(), new AsyncCallback<Nutzer>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.getMessage().toString();

						}

						@Override
						public void onSuccess(Nutzer result) {
						//	ownerLb.setStylePrimaryName("label");
							ownerLb.setText("Eigentümer: " + result.getEmailAddress());
							RootPanel.get("content").add(ownerLb);

						}

					});
				}

				// Panel fuer das Erstellungs- und Modifikationsdatum
				VerticalPanel datePanel = new VerticalPanel();

				DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
				erstellungsdatum.setText("Erstellungsdatum : " + dateFormat.format(selectedKontakt.getErstellDat()));
				modifikationsdatum.setText("Modifikationsdatum : " + dateFormat.format(selectedKontakt.getModDat()));

				datePanel.add(erstellungsdatum);
				datePanel.add(modifikationsdatum);

				// Überprüft Status eines Objekts ob es geteilt wurde.
				ev.getStatusForObject(k.getId(), k.getType(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage().toString();

					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == true) {

							HTML shared = new HTML("<image src='/images/sharecontact.png' width='24px' height='24px' />");
							shared.setTitle("Geteilter Kontakt");
							RootPanel.get("contentHeader").add(shared);

						}
					}

				});

				// Überprüfung ob Kontakt den Nutzer repräsentiert für Löschung aus dem
				// System.
				if (k.getOwnerId() == nutzer.getId() && k.getIdentifier() == 'r') {

					// Button für die Löschung erstellen und ClickHandler zuweisen.
					Button deleteNutzer = new Button(
							"<image src='/images/trash.png' width='15px' height='15px' align='center' />");
					deleteNutzer.setStylePrimaryName("deleteNutzer");
					deleteNutzer.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// Abfrage ob Nutzer sich wirklich löschen will.
							boolean x = Window.confirm("Wollen Sie sich wirklich unwiderruflich von uns verabschieden?");
						
							if(x == true) {
							ev.deleteNutzer(nutzer, new AsyncCallback<Void>() {

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
						}
					});
					
					btnPanel.add(deleteNutzer);
				}

				vp.add(headerPanel);
			
				

				RootPanel.get("content").add(vp);
				RootPanel.get("content").add(new ShowEigenschaften(nutzer, k));
				RootPanel.get("content").add(btnPanel);

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
		eigeneEigenschaftenTable.setStylePrimaryName("infoTable");

		
		FlexTablePanel.setStylePrimaryName("infoTable");
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
				kontakteigenschaften = result;
				/*
				 * Es wird eine ListBox mit den Eigenschaften die zur Auswahl stehen befuellt
				 * Diese wird verwendet um von vorne rein drei feste ListBoxen zur Auswahl
				 * bereitzustellen
				 */
				for (int i = 0; i < kontakteigenschaften.size(); i++) {
					auswahlEigenschaftenListBox1.addItem(kontakteigenschaften.elementAt(i).getBezeichnung());

				}

			}
		});

//		Button addEigenschaftBtn = new Button("Weitere Auswahleigenschaft");
//		addEigenschaftBtn.addClickHandler(new AuswahlEigenschaftenClickHandler());

		Button createEigenschaftBtn = new Button("Eigenschaft definieren");
		createEigenschaftBtn.setStylePrimaryName("eigeneEigButton");
		createEigenschaftBtn.addClickHandler(new EigeneEigenschaftClickHandler());
		
		Button deleteEigenschaftBtn = new Button("<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Eigenschaft");
	
		deleteEigenschaftBtn.addClickHandler(new DeleteEigenschaftClickHandler());
		deleteEigenschaftBtn.setStylePrimaryName("eigeneEigEntfernenButton");
		btnPanelTop.add(deleteEigenschaftBtn);

//		btnPanelBottom.add(addEigenschaftBtn);
		btnPanelBottom.add(createEigenschaftBtn);

		// Button für den Abbruch der Erstellung.
		Button quitBtn = new Button("<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Abbrechen");
		quitBtn.setStylePrimaryName("cancelKButton");
		quitBtn.setTitle("Kontakterstellung abbrechen");
		quitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//Wenn abgerbochen wird, wird man auf die Startseite weitergeleitet.
				RootPanel.get("content").clear();
				RootPanel.get("contentHeader").clear();
				RootPanel.get("content").add(new ShowKontakte(n));

			}
		});

		btnPanelTop.add(quitBtn);

		Button saveBtn = new Button("<image src='/images/save.png' width='20px' height='20px' align='center'/> Speichern");
		saveBtn.setStylePrimaryName("bearbeitungKSave");
		saveBtn.setTitle("Neuen Kontakt speichern");
		saveBtn.addClickHandler(new SpeichernKontaktClickHandler());

		btnPanelTop.add(saveBtn);

		vornameTxtBox.getElement().setPropertyString("placeholder", "Vorname des Kontakts");
		nachnameTxtBox.getElement().setPropertyString("placeholder", "Nachname des Kontakts");
		
		vorname.setStylePrimaryName("label");
		nachname.setStylePrimaryName("label");
	

		kontaktInfoTable.setWidget(0, 0, vorname);
		kontaktInfoTable.setWidget(0, 1, vornameTxtBox);
		kontaktInfoTable.setWidget(1, 0, nachname);
		kontaktInfoTable.setWidget(1, 1, nachnameTxtBox);
		
		kontaktInfoTable.setStylePrimaryName("infoTable");

		kontaktinfo.add(kontaktInfoTable);
		vp.add(kontaktinfo);
		vp.add(btnPanelTop);
		vp.add(sp);
		vp.add(btnPanelBottom);

		this.add(vp);

	}

	/**
	 * ClickHandler zum Erzeugen von weiteren Auswahleigenschaften (ListBoxen)
	 */

//	private class AuswahlEigenschaftenClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//
//			ListBox eigenschaftBox = new ListBox();
//			TextBox wertBox = new TextBox();
//			
//			wertBox.setStylePrimaryName("tbRundung");
//			wertBox.getElement().setPropertyString("placeholder", "Wert eingeben..");
//
//			ev.getEigenschaftAuswahl(new AsyncCallback<Vector<Eigenschaft>>() {
//
//				@Override
//				public void onFailure(Throwable caught) {
//					caught.getMessage();
//
//				}
//
//				@Override
//				public void onSuccess(Vector<Eigenschaft> result) {
//					
//					if (result != null) {
//
//						for (int i = 0; i < result.size(); i++) {
//							eigenschaftBox.addItem(result.elementAt(i).getBezeichnung());
//
//						}
//
//					}
//
//				}
//			});
//
//			int count = kontaktInfoTable.getRowCount();
//			kontaktInfoTable.setWidget(count, 0, eigenschaftBox);
//			kontaktInfoTable.setWidget(count, 1, wertBox);
//			count++;
//
//		}
//
//	}

	/**
	 * ClickHandler zum Erzeugen von neuen Eigenschafts-Angaben
	 */
	private class EigeneEigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TextBox bezTb = new TextBox();
			TextBox wertTb = new TextBox();
			

			bezTb.setStylePrimaryName("tbRundung2");
			wertTb.setStylePrimaryName("tbRundung2");

			bezTb.getElement().setPropertyString("placeholder", "Eigenschaft...");
			wertTb.getElement().setPropertyString("placeholder", "Auspraegung...");
	

			int count = eigeneEigenschaftenTable.getRowCount();
			eigeneEigenschaftenTable.setWidget(count, 0, bezTb);
			eigeneEigenschaftenTable.setWidget(count, 1, wertTb);
			count++;

		}

	}

	/**
	 * ClickHandler zum Speichern eines neu angelegten Kontakts
	 */
	public class SpeichernKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		
			// Cookies des Nutzers holen.
			nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));

			if (vornameTxtBox.getText().isEmpty()) {
				vornameTxtBox.getElement().getStyle().setBorderColor("red");
				MessageBox.alertWidget("Hinweis", "Bitte geben Sie Ihrem Kontakt mindestens einen Vornamen.");
			} else {
				// Anlegen des Kontakts mit den Mindestangaben Vor- und Nachname
				ev.createKontakt(vornameTxtBox.getText(), nachnameTxtBox.getText(), nutzer, new AsyncCallback<Kontakt>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.getMessage();

					}

					@Override
					public void onSuccess(Kontakt result) {
						k = result;

						// Angegebene Eigenschaften werden dem String-Vector hinzugefuegt
						for (int i = 0; i < eigeneEigenschaftenTable.getRowCount(); i++) {

							Widget v = eigeneEigenschaftenTable.getWidget(i, 0);
							if (v instanceof TextBox) {
								if (!((TextBox) v).getValue().isEmpty()) {
									String bez = (((TextBox) v).getText());

									eig.add(bez);
								}

							}
						}
						// Angegebene Auspraegungen werden dem String-Vector hinzugefuegt
						for (int j = 0; j < eigeneEigenschaftenTable.getRowCount(); j++) {

							Widget v = eigeneEigenschaftenTable.getWidget(j, 1);
							if (v instanceof TextBox) {
								if (!((TextBox) v).getValue().isEmpty()) {
									String wert = (((TextBox) v).getText());

									aus.add(wert);

								}

							}
						}

						ev.createAuspraegungForNewEigenschaft(eig, aus, k, nutzer.getId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable error) {
								error.getMessage().toString();
							}

							@Override
							public void onSuccess(Void result) {
								//Leeren aller divs und dann den erstellten Kontakt einblenden.
								RootPanel.get("navigator").clear();
								RootPanel.get("navigator").add(new Navigation(nutzer));
								RootPanel.get("content").clear();
								RootPanel.get("contentHeader").clear();
								setSontactTreeViewModel(sontactTree);
								MessageBox.alertWidget("Glückwunsch", "Sie haben den Kontakt "+ k.getVorname() + " " + k.getNachname() + " erfolgreich erstellt.");
								RootPanel.get("content").add(new KontaktForm(k));
								

							}
						});
						
					}
				});

			}

		}
	}
	
	//ClickHandler zum löschen von definierten Eigenschaften.
	private class DeleteEigenschaftClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			//ClickHandler zum entfernen von hinzugefügten Eigenschaften.
			eigeneEigenschaftenTable.removeRow(0);
			eigeneEigenschaftenTable.removeRow(1);
				

		}

	}
		

	/**
	 * ClickHandler zum löschen einer Teilung.
	 * 
	 * @author Dennis Lehle
	 *
	 */
	private class shareKontaktlisteClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			MessageBox.deleteTeilhaber("Teilhaberschaft entfernen",
					"Wählen sie für die Löschung einer Teilhaberschaft eine EMail Adresse aus.", null, selectedKontakt);

		}

	}

	/**
	 * ClickHandler fuer das Updaten eines Kontakts
	 * 
	 * @author Batista
	 *
	 */
	private class updateKontaktClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("contentHeader").clear();
			RootPanel.get("content").clear();

			// Nutzer Cookies holen.
			
			nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
			nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));
			/*
			 * Tabelle fuer das Editieren vorbereiten
			 */
			vornameTxtBox.setText(selectedKontakt.getVorname());
			nachnameTxtBox.setText(selectedKontakt.getNachname());
			
			kontaktInfoTable.setWidget(0, 0, vorname);
			kontaktInfoTable.setWidget(0, 1, vornameTxtBox);
			kontaktInfoTable.setWidget(1, 0, nachname);
			kontaktInfoTable.setWidget(1, 1, nachnameTxtBox);

			/*
			 * Tabelle mit den Eigenschaften und den Auspraegungen des Nutzers befuellen
			 */
			// Holen der Auspraegungen des selektierten Kontakts
			if(selectedKontakt.getOwnerId() == nutzer.getId()) {
			ev.getAllAuspraegungenByKontaktRelatable(selectedKontakt.getId(), new AsyncCallback<Vector<Relatable>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.toString());

				}

				@Override
				public void onSuccess(Vector<Relatable> result) {
					kontaktauspraegungen = result;

				Window.alert("Hey Eigentümer");
				
			
					for (int i = 0; i < kontaktauspraegungen.size(); i++) {
						
						TextBox auspraegung = new TextBox();
						auspraegung.setStylePrimaryName("tbRundung");
						auspraegung.setText(kontaktauspraegungen.elementAt(i).getWert());

						Label eigenschaftLabel = new Label();
						eigenschaftLabel.setStylePrimaryName("label");
						
						eigenschaftLabel.setText(kontaktauspraegungen.elementAt(i).getBezeichnung());
						int count = vorhandeneAuspraegungenTable.getRowCount();
						vorhandeneAuspraegungenTable.setWidget(count, 0, eigenschaftLabel);
						vorhandeneAuspraegungenTable.setWidget(count, 1, auspraegung);
						int count2 = vorhandeneAuspraegungenTable.getRowCount();
						count = count2 + 1;
						
						}
					
				
					Window.alert(vorhandeneAuspraegungenTable.toString() + "Vorhandene AUsp");
				}
			});
		}	
			else {
			ev.getAllSharedAuspraegungenByKontaktAndNutzer(selectedKontakt, nutzer,  new AsyncCallback<Vector<Relatable>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.toString());

				}

				@Override
				public void onSuccess(Vector<Relatable> result) {
					
					Window.alert("Hey Teilhaber");
				
					
					for (int i = 0; i < result.size(); i++) {
						if(kontaktauspraegungen.contains(result.elementAt(i))) {
							result.remove(i);
						} else {
							kontaktauspraegungen.add(result.elementAt(i));
						}
					}
					Window.alert(kontaktauspraegungen.toString()+ "Kontaktausp");
					
					for (int i = 0; i < kontaktauspraegungen.size(); i++) {
					
						
						
						TextBox auspraegung = new TextBox();
						auspraegung.setStylePrimaryName("tbRundung");
						auspraegung.setText(kontaktauspraegungen.elementAt(i).getWert());

						Label eigenschaftLabel = new Label();
						eigenschaftLabel.setStylePrimaryName("label");
						
						eigenschaftLabel.setText(kontaktauspraegungen.elementAt(i).getBezeichnung());
						int count = vorhandeneAuspraegungenTable.getRowCount();
						vorhandeneAuspraegungenTable.setWidget(count, 0, eigenschaftLabel);
						vorhandeneAuspraegungenTable.setWidget(count, 1, auspraegung);
						int count2 = vorhandeneAuspraegungenTable.getRowCount();
						count = count2 + 1;
						}
					
						
					Window.alert(kontaktauspraegungen.toString()+ "Vorhandene Ausp");
				

				}
			});
			}

			/*
			 * Elemente zum Aufbau der Bearbeitungansicht
			 */
			HorizontalPanel BtnPanel = new HorizontalPanel();
			HorizontalPanel headerPanel = new HorizontalPanel();
			ScrollPanel scc = new ScrollPanel();
		
			RootPanel.get("contentHeader").add(new HTML("<h2>Kontaktinformationen bearbeiten <image src='/images/edit.png' width='30px' height='30px' align='center'/></h2>"));

			
			Button cancelBtn = new Button("<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Abbrechen");
			cancelBtn.setStylePrimaryName("cancelKButton");
			cancelBtn.setTitle("Abbrechen der Bearbeitung");
			cancelBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("content").clear();
					RootPanel.get("contentHeader").clear();
					RootPanel.get("content").add(new KontaktForm(selectedKontakt));
				}
			});

			BtnPanel.add(cancelBtn);
			//ClickHandler zum entfernen von hinzugefügten Eigenschaften.
			Button eigenschaftLoeschen = new Button("<image src='/images/abbrechen.png' width='20px' height='20px' align='center'/> Eigenschaft");
			eigenschaftLoeschen.setStylePrimaryName("eigeneEigEntfernenButton");
			eigenschaftLoeschen.setTitle("Erstellte Eigenschaft entfernen");
			eigenschaftLoeschen.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
				
					eigeneEigenschaftBearbeitungTable.removeRow(0);
					eigeneEigenschaftBearbeitungTable.removeRow(1);
					
				}
			});
			
			BtnPanel.add(eigenschaftLoeschen);

			
			Button freieEigenschaft = new Button("Eigenschaft definieren");
			freieEigenschaft.setTitle("Selbstdefinierte Eigenschaften anlegen");
			freieEigenschaft.setStylePrimaryName("eigeneEigButton");

			freieEigenschaft.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					TextBox bezTb = new TextBox();
					TextBox wertTb = new TextBox();
					

					bezTb.getElement().setPropertyString("placeholder", "Eigenschaft...");
					wertTb.getElement().setPropertyString("placeholder", "Auspraegung...");
					bezTb.setStylePrimaryName("tbRundung2");
					wertTb.setStylePrimaryName("tbRundung2");
					
					eigeneEigenschaftBearbeitungTable.setStylePrimaryName("infoTable");
					
					int count = eigeneEigenschaftBearbeitungTable.getRowCount();
					eigeneEigenschaftBearbeitungTable.setWidget(count, 0, bezTb);
					eigeneEigenschaftBearbeitungTable.setWidget(count, 1, wertTb);
					count++;

				}

			});

			BtnPanel.add(freieEigenschaft);

			// Instanziierung Button zum Speichern der Aenderungen an dem selektierten
			// Kontakts
			Button saveBtn = new Button("<image src='/images/save.png' width='20px' height='20px' align='center'/> Speichern");
			saveBtn.setStylePrimaryName("bearbeitungKSave");
			saveBtn.setTitle("Speichern der Änderungen");
			/*
			 * Nun werden die Aenderungen uebernommen bzw. gespeichert
			 */
			saveBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					TextBox txtboxvorname = (TextBox) kontaktInfoTable.getWidget(0, 1);
					TextBox txtboxnachname = (TextBox) kontaktInfoTable.getWidget(1, 1);
					
					nutzer.setId(Integer.valueOf(Cookies.getCookie("nutzerID")));
					nutzer.setEmailAddress(Cookies.getCookie("nutzerGMail"));
					
					
					if(txtboxvorname.getText() == "") {
						txtboxvorname.getElement().getStyle().setBorderColor("red");
						MessageBox.alertWidget("Hinweis", "Bitte geben Sie dem Kontakt mindestens einen Vornamen");
					} else {
					selectedKontakt.setVorname(txtboxvorname.getText());
					selectedKontakt.setNachname(txtboxnachname.getText());

					ev.saveKontakt(selectedKontakt, new AsyncCallback<Kontakt>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(caught.toString());

						}

						@Override
						public void onSuccess(Kontakt result) {
							eig.removeAllElements();
							aus.removeAllElements();

							// Angegebene Eigenschaften werden dem String-Vector hinzugefuegt
							for (int i = 0; i < eigeneEigenschaftBearbeitungTable.getRowCount(); i++) {

								Widget v = eigeneEigenschaftBearbeitungTable.getWidget(i, 0);
								if (v instanceof TextBox) {
									if (!((TextBox) v).getValue().isEmpty()) {
										String bez = (((TextBox) v).getText());

										eig.add(bez);
									}

								}
							}
							// Angegebene Auspraegungen werden dem String-Vector hinzugefuegt
							for (int j = 0; j < eigeneEigenschaftBearbeitungTable.getRowCount(); j++) {

								Widget v = eigeneEigenschaftBearbeitungTable.getWidget(j, 1);
								if (v instanceof TextBox) {
									if (!((TextBox) v).getValue().isEmpty()) {
										String wert = (((TextBox) v).getText());

										aus.add(wert);

									}

								}
							}

							ev.createAuspraegungForNewEigenschaft(eig, aus, result, nutzer.getId(), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable error) {
									error.getMessage().toString();
								}

								@Override
								public void onSuccess(Void result) {
									
								}
							});

							// Updaten der vorhandenen Auspraegungen
							aus.removeAllElements();
							for (int j = 0; j < vorhandeneAuspraegungenTable.getRowCount(); j++) {

								Widget v = vorhandeneAuspraegungenTable.getWidget(j, 1);
								if (v instanceof TextBox) {
									if (!((TextBox) v).getValue().isEmpty()) {
										String wert = (((TextBox) v).getText());
										aus.add(wert);
										

									}

								}
							}
							

							Vector<Auspraegung> neueAuspraegungen = new Vector<>();
							for (int i = 0; i < kontaktauspraegungen.size(); i++) {
								
								Auspraegung a = new Auspraegung();
								a.setWert(aus.elementAt(i));
								a.setId(kontaktauspraegungen.elementAt(i).getId());
								neueAuspraegungen.add(a);
								


							}
								
						
								ev.saveAuspraegung(neueAuspraegungen, new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Void result) {
									//Alle Panels leere und die KontaktForm mit dem
									//aktuellen Kontakt wieder aufrufen.
									RootPanel.get("content").clear();
									RootPanel.get("contentHeader").clear();
									RootPanel.get("content").add(new KontaktForm(selectedKontakt));
									MessageBox.alertWidget("Hinweis", "Der Kontakt " + selectedKontakt.getVorname() + " " +  selectedKontakt.getNachname() + " wurde erfolgreich aktualisiert." );
									}

								});

						}
					});
					}

				}

			});

			kontaktInfoTable.setStylePrimaryName("infoTable");
			vorhandeneAuspraegungenTable.setStylePrimaryName("infoTable");
			eigeneEigenschaftBearbeitungTable.setStylePrimaryName("infoTable");
			scc.setSize("900px", "400px");
			BtnPanel.add(saveBtn);
			
			vp.add(kontaktInfoTable);
			headerPanel.add(vorhandeneAuspraegungenTable);
			headerPanel.add(eigeneEigenschaftBearbeitungTable);
			scc.add(headerPanel);
			vp.add(scc);
			vp.add(BtnPanel);

			RootPanel.get("content").add(vp);

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
